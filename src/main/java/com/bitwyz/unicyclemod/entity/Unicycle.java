package com.bitwyz.unicyclemod.entity;

import com.bitwyz.unicyclemod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Unicycle extends Boat {

  private static final float MOVE_INV_FRICTION_FAC = 0.5f;
  private static final float TURN_INV_FRICTION_FAC = 1.3f;

  private boolean inputLeft;
  private boolean inputRight;
  private boolean inputUp;
  private boolean inputDown;
  private float deltaRotation;
  private int lerpSteps;
  private double lerpX;
  private double lerpY;
  private double lerpZ;
  private double lerpXRot;
  private double lerpYRot;
  private float landFriction;
  private float wheelRotation;
  private Status status;

  public Unicycle(Level level, Vec3 pos) {
    this(ModEntities.UNICYCLE.get(), level);
    this.setPos(pos);
    this.xo = pos.x;
    this.yo = pos.y;
    this.zo = pos.z;
  }

  public Unicycle(EntityType<Unicycle> unicycleEntityType, Level level) {
    super(unicycleEntityType, level);
  }

  @Override
  public double getPassengersRidingOffset() {
    return 1.2D;
  }

  @Override
  public void lerpTo(
      double pX,
      double pY,
      double pZ,
      float pYaw,
      float pPitch,
      int pPosRotationIncrements,
      boolean pTeleport) {
    this.lerpX = pX;
    this.lerpY = pY;
    this.lerpZ = pZ;
    this.lerpYRot = pYaw;
    this.lerpXRot = pPitch;
    this.lerpSteps = 10;
  }

  @Override
  public void setInput(
      boolean pInputLeft, boolean pInputRight, boolean pInputUp, boolean pInputDown) {
    this.inputLeft = pInputLeft;
    this.inputRight = pInputRight;
    this.inputUp = pInputUp;
    this.inputDown = pInputDown;
  }

  @Override
  public boolean hurt(DamageSource source, float amount) {
    if (this.isInvulnerableTo(source)) {
      return false;
    }

    if (!this.level().isClientSide && !this.isRemoved()) {
      this.setHurtDir(-this.getHurtDir());
      this.setHurtTime(10);
      this.setDamage(this.getDamage() + amount * 10.0F);
      this.markHurt();
      this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
      boolean isSurvivalPlayer =
          (source.getEntity() instanceof Player)
              && !((Player) source.getEntity()).getAbilities().instabuild;

      if (isSurvivalPlayer || this.getDamage() > 40.0F) {
        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
          this.spawnAtLocation(this.getDropItem());
        }

        this.discard();
      }
    }

    return true;
  }

  @Override
  public @NotNull Item getDropItem() {
    return ModItems.UNICYCLE.get();
  }

  /** Called to update the entity's position/logic. */
  public void tick() {
    this.status = getStatus();

    if (this.getHurtTime() > 0) {
      this.setHurtTime(this.getHurtTime() - 1);
    }

    if (this.getDamage() > 0.0F) {
      this.setDamage(this.getDamage() - 1.0F);
    }

    super.tick();
    this.tickLerp();
    if (this.isControlledByLocalInstance()) {
      if (!(this.getFirstPassenger() instanceof Player)) {
        this.setPaddleState(false, false);
      }

      this.rideUnicycle();
      if (this.level().isClientSide) {
        if (status == Status.ON_LAND) {
          this.controlUnicycle();
        }
        this.level()
            .sendPacketToServer(
                new ServerboundPaddleBoatPacket(this.getPaddleState(0), this.getPaddleState(1)));
      }

      this.move(MoverType.SELF, this.getDeltaMovement());
    } else {
      this.setDeltaMovement(Vec3.ZERO);
    }

    this.checkInsideBlocks();
    List<Entity> list =
        this.level()
            .getEntities(
                this,
                this.getBoundingBox().inflate(0.2D, -0.01D, 0.2D),
                EntitySelector.pushableBy(this));
    if (!list.isEmpty()) {
      boolean flag =
          !this.level().isClientSide && !(this.getControllingPassenger() instanceof Player);

      for (Entity entity : list) {
        if (!entity.hasPassenger(this)) {
          if (flag
              && this.getPassengers().size() < 2
              && !entity.isPassenger()
              && entity.getBbWidth() < this.getBbWidth()
              && entity instanceof LivingEntity
              && !(entity instanceof WaterAnimal)
              && !(entity instanceof Player)) {
            entity.startRiding(this);
          } else {
            this.push(entity);
          }
        }
      }
    }

    Vec3 movement = new Vec3(this.getDeltaMovement().x, 0.0f, this.getDeltaMovement().z);
    this.wheelRotation -= movement.length();
  }

  public float getWheelRotation() {
    return this.wheelRotation;
  }

  @Nullable
  @Override
  protected SoundEvent getPaddleSound() {
    return null;
  }

  @Override
  protected boolean canAddPassenger(Entity pPassenger) {
    return this.getPassengers().size() < 1;
  }

  @Override
  protected void addPassenger(Entity passenger) {
    super.addPassenger(passenger);
    if (this.isControlledByLocalInstance() && this.lerpSteps > 0) {
      this.lerpSteps = 0;
      this.absMoveTo(
          this.lerpX, this.lerpY, this.lerpZ, (float) this.lerpYRot, (float) this.lerpXRot);
    }
  }

  /** Determines whether the unicycle is on land and can ride. */
  private Status getStatus() {
    AABB aabb = this.getBoundingBox();
    int i = Mth.floor(aabb.minX);
    int j = Mth.ceil(aabb.maxX);
    int k = Mth.floor(aabb.minY);
    int l = Mth.ceil(aabb.minY + 0.001D);
    int i1 = Mth.floor(aabb.minZ);
    int j1 = Mth.ceil(aabb.maxZ);
    BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

    for (int k1 = i; k1 < j; ++k1) {
      for (int l1 = k; l1 < l; ++l1) {
        for (int i2 = i1; i2 < j1; ++i2) {
          mutableBlockPos.set(k1, l1, i2);
          FluidState fluidstate = this.level().getFluidState(mutableBlockPos);
          if (!fluidstate.isEmpty()) {
            // Touching a fluid
            return Status.IN_WATER;
          }
        }
      }
    }

    float friction = getGroundFriction();
    if (friction > 0.0f) {
      this.landFriction = friction;
      return Status.ON_LAND;
    }

    return Status.IN_AIR;
  }

  private void tickLerp() {
    if (this.isControlledByLocalInstance()) {
      this.lerpSteps = 0;
      // this.setPacketCoordinates(this.getX(), this.getY(), this.getZ());
    }

    if (this.lerpSteps > 0) {
      double d0 = this.getX() + (this.lerpX - this.getX()) / (double) this.lerpSteps;
      double d1 = this.getY() + (this.lerpY - this.getY()) / (double) this.lerpSteps;
      double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double) this.lerpSteps;
      double d3 = Mth.wrapDegrees(this.lerpYRot - (double) this.getYRot());
      this.setYRot(this.getYRot() + (float) d3 / (float) this.lerpSteps);
      this.setXRot(
          this.getXRot()
              + (float) (this.lerpXRot - (double) this.getXRot()) / (float) this.lerpSteps);
      --this.lerpSteps;
      this.setPos(d0, d1, d2);
      this.setRot(this.getYRot(), this.getXRot());
    }
  }

  private void controlUnicycle() {
    if (this.isVehicle()) {
      float force = 0.0F;
      if (this.inputLeft) {
        --this.deltaRotation;
      }

      if (this.inputRight) {
        ++this.deltaRotation;
      }

      if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
        force += 0.005F;
      }

      this.setYRot(this.getYRot() + this.deltaRotation);
      if (this.inputUp) {
        force += 0.4F;
      }

      if (this.inputDown) {
        force -= 0.05F;
      }

      this.setDeltaMovement(
          this.getDeltaMovement()
              .add(
                  (Mth.sin(-this.getYRot() * ((float) Math.PI / 180F)) * force),
                  0.0D,
                  (Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * force)));
    }
  }

  /** Update the unicycle's speed, based on momentum. */
  private void rideUnicycle() {
    double gravAccel = this.isNoGravity() ? 0.0D : -0.04D;
    float moveFriction;
    float turnFriction;

    if (this.status == Status.ON_LAND) {
      moveFriction = this.landFriction * MOVE_INV_FRICTION_FAC;
      turnFriction = this.landFriction * TURN_INV_FRICTION_FAC;
    } else {
      moveFriction = 1.0F;
      turnFriction = 0.5f;
    }

    Vec3 vec3 = this.getDeltaMovement();
    this.setDeltaMovement(vec3.x * moveFriction, vec3.y + gravAccel, vec3.z * moveFriction);
    this.deltaRotation *= turnFriction;
  }

  public enum Status {
    IN_WATER,
    ON_LAND,
    IN_AIR
  }
}
