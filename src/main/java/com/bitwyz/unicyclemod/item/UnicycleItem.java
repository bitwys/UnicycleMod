package com.bitwyz.unicyclemod.item;

import com.bitwyz.unicyclemod.entity.Unicycle;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public class UnicycleItem extends Item {

    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

    public UnicycleItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        HitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);

        if (hit.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        }

        Vec3 viewVec = player.getViewVector(1.0f);

        List<Entity> entities = level.getEntities(player, player.getBoundingBox().expandTowards(viewVec.scale(5)).inflate(1), ENTITY_PREDICATE);
        if (!entities.isEmpty()) {
            Vec3 eyePos = player.getEyePosition();

            for (Entity entity: entities) {
                AABB aabb = entity.getBoundingBox();
                if (aabb.contains(eyePos)) {
                    return InteractionResultHolder.pass(itemStack);
                }
            }
        }

        if (hit.getType() == HitResult.Type.BLOCK) {
            Unicycle unicycle = new Unicycle(level, hit.getLocation());
            unicycle.setYRot(player.getYRot());

            if (!level.noCollision(unicycle, unicycle.getBoundingBox())) {
                return InteractionResultHolder.fail(itemStack);
            }

            if (!level.isClientSide()) {
                level.addFreshEntity(unicycle);
                level.gameEvent(player, GameEvent.ENTITY_PLACE, hit.getLocation());

                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        }

        return InteractionResultHolder.pass(itemStack);
    }
}
