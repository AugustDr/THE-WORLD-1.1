package com.august_dr.rotp_theworld.init;


import com.august_dr.rotp_theworld.RotpTHEWORLDAddon;
import com.august_dr.rotp_theworld.action.stand.*;
import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.action.stand.TimeResume;
import com.github.standobyte.jojo.action.stand.TimeStop;
import com.august_dr.rotp_theworld.entity.stand.stands.THEWORLDEntity;
import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.*;
import com.github.standobyte.jojo.action.stand.StandEntityAction.Phase;
import com.github.standobyte.jojo.action.stand.TimeStopInstant;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.TimeStopperStandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), RotpTHEWORLDAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), RotpTHEWORLDAddon.MOD_ID);
    
 // ======================================== THE WORLD ========================================


    public static final RegistryObject<StandEntityAction> THE_WORLD_PUNCH = ACTIONS.register("the_world_punch",
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
                    .standOffsetFromUser(0.75, 0.75, 0.2)
                    .punchSound(InitSounds.THE_WORLD_PUNCH_LIGHT)
                    .standSound(Phase.WINDUP, InitSounds.DIEGO_MUDA)));
    
    public static final RegistryObject<StandEntityAction> THE_WORLD_BARRAGE = ACTIONS.register("the_world_barrage",
            () -> new TheWorldBarrage(new StandEntityMeleeBarrage.Builder()
                    .barrageHitSound(InitSounds.THE_WORLD_BARRAGE)
                    .standSound(InitSounds.THE_WORLD_MUDA_MUDA_MUDA).shout(InitSounds.DIEGO_MUDA_MUDA), InitSounds.DIEGO_WRY));

    public static final RegistryObject<StandEntityHeavyAttack> THE_WORLD_KNOCKBACK_PUNCH = ACTIONS.register("the_world_knockback_punch",
            () -> new THEWORLDKnockbackPunch(new StandEntityHeavyAttack.Builder()
                    .standOffsetFromUser(0.75, 0.85, 0.2)
                    .resolveLevelToUnlock(1).isTrained()
                    .punchSound(InitSounds.THE_WORLD_PUNCH_HEAVY)
                    .standPose(StandPose.HEAVY_ATTACK_FINISHER)
                    .standSound(Phase.WINDUP, InitSounds.THE_WORLD_MUDA_LONG)
                    .partsRequired(StandPart.ARMS)));
    
    public static final RegistryObject<StandEntityHeavyAttack> THE_WORLD_HEAVY_PUNCH = ACTIONS.register("the_world_heavy_punch",
            () -> new THEWORLDHeavyPunch(new StandEntityHeavyAttack.Builder()
                    .standOffsetFromUser(0.75, 0.75, 0.2)
                    .punchSound(InitSounds.THE_WORLD_PUNCH_HEAVY)
                    .standPose(StandPose.HEAVY_ATTACK)
                    .shout(InitSounds.DIEGO_DIE)
                    .partsRequired(StandPart.ARMS)
                    .setFinisherVariation(THE_WORLD_KNOCKBACK_PUNCH)
                    .shiftVariationOf(THE_WORLD_PUNCH).shiftVariationOf(THE_WORLD_BARRAGE)));
    
    public static final RegistryObject<StandEntityAction> THE_WORLD_BLOCK = ACTIONS.register("the_world_block",
            () -> new StandEntityBlock());

    public static final RegistryObject<TimeStop> THE_WORLD_TIME_STOP = ACTIONS.register("the_world_time_stop",
            () -> new THEWORLDTimeStop(new TimeStop.Builder().holdToFire(20, false).staminaCost(225).staminaCostTick(7.5F).heldWalkSpeed(0.25f)
                    .resolveLevelToUnlock(2).isTrained()
                    .ignoresPerformerStun()
                    //.standPose(THEWORLDTimeStop.TIME_STOP)
                    .shout(InitSounds.DIEGO_THE_WORLD)
                    .partsRequired(StandPart.MAIN_BODY)
                    .voiceLineWithStandSummoned(InitSounds.DIEGO_TIME_STOP).timeStopSound(InitSounds.THE_WORLD_TIME_STOP)
                    .addTimeResumeVoiceLine(InitSounds.DIEGO_TIME_RESUMES, true).addTimeResumeVoiceLine(InitSounds.DIEGO_TIMES_UP, false)
                    .timeResumeSound(InitSounds.THE_WORLD_TIME_RESUME)
                    .shaderEffect(new ResourceLocation(RotpTHEWORLDAddon.MOD_ID, "shaders/post/time_stop_tw.json"), true)
                    .shaderEffect(new ResourceLocation(RotpTHEWORLDAddon.MOD_ID, "shaders/post/time_stop_tw_old.json"), false)));

    public static final RegistryObject<TimeResume> THE_WORLD_TIME_RESUME = ACTIONS.register("the_world_time_resume",
            () -> new TimeResume(new StandAction.Builder().shiftVariationOf(THE_WORLD_TIME_STOP)));
    public static final RegistryObject<TimeStopInstant> THE_WORLD_TIME_STOP_BLINK = ACTIONS.register("the_world_ts_blink",
            () -> new THEWORLDTimeStopInstant(new StandAction.Builder()
                    .resolveLevelToUnlock(2).isTrained()
                    .ignoresPerformerStun()
                    .partsRequired(StandPart.MAIN_BODY),
                    THE_WORLD_TIME_STOP, InitSounds.THE_WORLD_TIME_STOP_BLINK));

    //public static final RegistryObject<THEWORLDTSHeavyAttack> THE_WORLD_TS_PUNCH = ACTIONS.register("THE_WORLD_ts_punch",
    //       () -> new THEWORLDTSHeavyAttack(new THEWORLDTSHeavyAttack.Builder()
    //                .resolveLevelToUnlock(3).standUserWalkSpeed(0.8F)
    //               .standPose(StandPose.HEAVY_ATTACK)
    //               .standWindupDuration(5).cooldown(20)
    //              .partsRequired(StandPart.MAIN_BODY, StandPart.ARMS), THE_WORLD_HEAVY_PUNCH, THE_WORLD_TIME_STOP_BLINK));
    public static final EntityStandRegistryObject<EntityStandType<TimeStopperStandStats>, StandEntityType<THEWORLDEntity>> STAND_THE_WORLD =
            new EntityStandRegistryObject<>("the_world",
                    STANDS, 
                    () -> new EntityStandType<TimeStopperStandStats>(
                            0xF7F21A, ModStandsInit.PART_7_NAME,

                            new StandAction[] {
                                    THE_WORLD_PUNCH.get(), 
                                    THE_WORLD_BARRAGE.get()},
                            new StandAction[] {
                                    THE_WORLD_BLOCK.get(),
                                    THE_WORLD_TIME_STOP.get()},

                            TimeStopperStandStats.class, new TimeStopperStandStats.Builder()
                            .tier(6)
                            .power(16.0)
                            .speed(16.0)
                            .range(2.0, 10.0)
                            .durability(16.0)
                            .precision(12.0)
                            .build("THE WORLD"),

                            new StandType.StandTypeOptionals()
                            .addSummonShout(InitSounds.DIEGO_THE_WORLD)
                            .addOst(InitSounds.THE_WORLD_OST)),

                    InitEntities.ENTITIES, 
                    () -> new StandEntityType<THEWORLDEntity>(THEWORLDEntity::new, 0.7F, 2.15F)
                    .summonSound(InitSounds.THE_WORLD_SUMMON)
                    .unsummonSound(InitSounds.THE_WORLD_UNSUMMON))

            .withDefaultStandAttributes();
}
