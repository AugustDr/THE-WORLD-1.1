package com.august_dr.rotp_theworld.action.stand;

import com.github.standobyte.jojo.action.stand.TimeStop;
import com.github.standobyte.jojo.action.stand.TimeStopInstant;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class THEWORLDTimeStopInstant extends TimeStopInstant {

    public THEWORLDTimeStopInstant(Builder builder,
                                   @Nonnull RegistryObject<TimeStop> baseTimeStopAction, @Nonnull Supplier<SoundEvent> blinkSound) {
        super(builder, baseTimeStopAction, blinkSound);
    }

    @Override
    protected Vector3d getEntityTargetTeleportPos(Entity user, Entity target) {
        return target.position().subtract(target.getLookAngle().scale(target.getBbWidth() + user.getBbWidth()));
    }
}
