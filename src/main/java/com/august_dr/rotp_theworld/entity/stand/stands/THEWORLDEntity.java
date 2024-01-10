package com.august_dr.rotp_theworld.entity.stand.stands;

import com.august_dr.rotp_theworld.init.InitSounds;
import com.github.standobyte.jojo.capability.entity.PlayerUtilCapProvider;
import com.github.standobyte.jojo.capability.world.TimeStopHandler;
import com.github.standobyte.jojo.capability.world.WorldUtilCapProvider;
import com.github.standobyte.jojo.entity.itemprojectile.KnifeEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;

import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.item.KnifeItem;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class THEWORLDEntity extends StandEntity {

    public THEWORLDEntity(StandEntityType<THEWORLDEntity> type, World world) {
        super(type, world);
        unsummonOffset = getDefaultOffsetFromUser().copy();
    }

    private StandRelativeOffset offsetDefault = StandRelativeOffset.withYOffset(0.75, 0.2, -0.75);
    private StandRelativeOffset offsetDefaultArmsOnly = StandRelativeOffset.withYOffset(0, 0, 0.15);

    public StandRelativeOffset getDefaultOffsetFromUser() {
        return isArmsOnlyMode() ? offsetDefaultArmsOnly : offsetDefault;
    }

    @Override
    public void onKnivesThrow(World world, PlayerEntity playerUser, ItemStack knivesStack, int knivesThrown) {
        if (world.isClientSide()) {
            return;
        }

        if (knivesThrown == 1) {
            playerUser.getCapability(PlayerUtilCapProvider.CAPABILITY).ifPresent(cap -> {
                if (cap.knivesThrewTicks > 0) {
                    JojoModUtil.sayVoiceLine(playerUser, InitSounds.DIEGO_ONE_MORE.get());
                    cap.knivesThrewTicks = 0;
                }
            });
        }

        if (knivesThrown > 1 && TimeStopHandler.isTimeStopped(playerUser.level, playerUser.blockPosition())) {
            playerUser.getCapability(PlayerUtilCapProvider.CAPABILITY).ifPresent(cap -> {
                cap.knivesThrewTicks = 80;
            });
        }

        if (!playerUser.isShiftKeyDown() && knivesStack.getCount() > 0) {
            int standKnives = Math.min(knivesStack.getCount(), KnifeItem.MAX_KNIVES_THROW);

            for (int i = 0; i < standKnives; i++) {
                KnifeEntity knifeEntity = new KnifeEntity(world, playerUser);
                knifeEntity.setPos(this.getX(), this.getEyeY() - 0.1, this.getZ());
                knifeEntity.setTimeStopFlightTicks(5);
                knifeEntity.shootFromRotation(playerUser, 1.5F, i == 0 ? 1.0F : 16.0F);
                world.addFreshEntity(knifeEntity);
            }

            world.playSound(null, this.getX(), this.getY(), this.getZ(),
                    standKnives == 1 ? ModSounds.KNIFE_THROW.get() : ModSounds.KNIVES_THROW.get(),
                    SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

            if (!playerUser.abilities.instabuild) {
                knivesStack.shrink(standKnives);
            }



            world.getCapability(WorldUtilCapProvider.CAPABILITY).map(cap ->
                    cap.getTimeStopHandler().userStoppedTime(playerUser)).get().ifPresent(timeStop -> {
                if (timeStop.getStartingTicks() == 100 && timeStop.getTicksLeft() > 50 && timeStop.getTicksLeft() <= 80) {
                    JojoModUtil.sayVoiceLine(playerUser, InitSounds.DIEGO_5_SECONDS.get());
                }
            });
        }
    }
}
