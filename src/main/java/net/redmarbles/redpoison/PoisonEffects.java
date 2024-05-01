package net.redmarbles.redpoison;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.LightType;


public class PoisonEffects {
    static int waterdamagecooldownst2 = 20;
    static int lightdmgcooldown = 600;
    static int healthremoved = 0;
    static int healthremovecd = 1200;
    public static void Stage1Effects(ServerPlayerEntity player){
        if (player.getWorld().getRandom().nextFloat() < 0.01) { // Oh wow probability calculation
            // Apply nausea effect for 10 seconds (200 ticks)
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200));
        }

        if (player.getWorld().getRandom().nextFloat() < 0.03) { // Adjust probability as needed
            // Randomly reduce player's health(Yes this has a custom damage source cuz why not)
            player.damage(DamageTypes.of(player.getWorld(),DamageTypes.OVEREXPOSURE), 1.0F);
            }
        }

    public static void Stage2Effects(ServerPlayerEntity player){
        PlayerData playerState = StateSaverAndLoader.getPlayerState(player);

        if (player.isTouchingWaterOrRain()){
            if (waterdamagecooldownst2 == 0 && playerState.CurrentHoldTime < 180000) {
                player.damage(DamageTypes.of(player.getWorld(), DamageTypes.IRRITATION), 1.0F);
            } else if (waterdamagecooldownst2 == 0 && playerState.CurrentHoldTime > 180000) {
                player.damage(DamageTypes.of(player.getWorld(), DamageTypes.IRRITATION), 2.0F);
            } else {
                waterdamagecooldownst2--;
                if (waterdamagecooldownst2 < 0){
                    waterdamagecooldownst2 = 1800;
                }
            }
        }
        if (player.getWorld().getRandom().nextFloat() < 0.02) {
            // Apply speed effect, amplifier and duration changes on hold time
            if (playerState.CurrentHoldTime < 180000){
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1));
            } else {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 300, 2));
            }
        }
        if (player.getWorld().getRandom().nextFloat() < 0.03) {
            // Apply slowness effect, amplifier and duration changes on hold time
            if (playerState.CurrentHoldTime < 180000){
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 1));
            } else {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 300, 2));
            }
        }

        //LIGHT DAMAGEEEEEEEE(yeah the player is a vampire now)
        BlockPos playerPos = player.getBlockPos().up(); // Get the position of the block the player is standing on
        int skyLightLevel = player.getWorld().getLightLevel(LightType.SKY, playerPos); // Get the sky light level at the player's position

        if (skyLightLevel >= 10){
            if (lightdmgcooldown == 600) {
                player.damage(player.getServerWorld().getDamageSources().onFire(), 1.0F);
                lightdmgcooldown = 0;
            } else {
                lightdmgcooldown++;
            }
        }
    }

    public static void Stage3Effects(ServerPlayerEntity player){
        PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
        //Stage 1 Effects, BUT INTENSE(yeah i multiplied it by 2)
        if (player.getWorld().getRandom().nextFloat() < 0.01) { // Oh wow probability calculation
            // Apply nausea effect for 10 seconds (200 ticks)
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 2));
        }

        if (player.getWorld().getRandom().nextFloat() < 0.03) {
            // Randomly reduce player's health(Yes this has a custom damage source cuz why not)
            player.damage(DamageTypes.of(player.getWorld(),DamageTypes.OVEREXPOSURE), 2.0F);
        }

        //Light damage, BUT MORE
        BlockPos playerPos = player.getBlockPos().up(); // Get the position of the block the player is standing on(again)
        int skyLightLevel = player.getWorld().getLightLevel(LightType.SKY, playerPos); // Get the sky light level at the player's position(again)

        if (skyLightLevel >= 10){
            healthremovecd--;
            if (lightdmgcooldown == 600) {
                player.damage(player.getServerWorld().getDamageSources().onFire(), 2.0F);
                lightdmgcooldown = 0;
            } else {
                lightdmgcooldown++;
            }
        }

        //Same effect code from stage 2 but with a few changes
        if (player.getWorld().getRandom().nextFloat() < 0.02) {
            // Apply speed effect, amplifier and duration changes on hold time
            if (playerState.CurrentHoldTime < 252000){
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 2));
            } else {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 300, 3));
            }
        }
        if (player.getWorld().getRandom().nextFloat() < 0.03) {
            // Apply slowness effect, amplifier and duration changes on hold time
            if (playerState.CurrentHoldTime < 252000){
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 3));
            } else {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 300, 4));
            }
        }

        //Stage 2 water touching but WORSE
        if (player.isTouchingWaterOrRain()){
            if (waterdamagecooldownst2 == 0 && playerState.CurrentHoldTime < 252000) {
                player.damage(DamageTypes.of(player.getWorld(), DamageTypes.IRRITATION), 3.0F);
            } else if (waterdamagecooldownst2 == 0 && playerState.CurrentHoldTime > 252000) {
                player.damage(DamageTypes.of(player.getWorld(), DamageTypes.IRRITATION), 4.0F);
            } else {
                waterdamagecooldownst2--;
                if (waterdamagecooldownst2 < 0){
                    waterdamagecooldownst2 = 1800;
                }
            }

        }

        //Decrease player hp PERMANENTLY
        if (healthremovecd == 0 && healthremoved <= 10 ) {
            player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(new EntityAttributeModifier("Oreo", -1, EntityAttributeModifier.Operation.ADDITION));
        }
    }

}



