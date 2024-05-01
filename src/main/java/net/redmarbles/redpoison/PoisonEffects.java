package net.redmarbles.redpoison;

import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.LightType;


public class PoisonEffects {
    static int waterdamagecooldownst2 = 20;
    static int lightdmgcooldown = 600;
    public static void Stage1Effects(ServerPlayerEntity player){
        if (player.getWorld().getRandom().nextFloat() < 0.01) { // Adjust probability as needed
            // Apply nausea effect for 10 seconds (200 ticks)
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200));
        }

        if (player.getWorld().getRandom().nextFloat() < 0.03) { // Adjust probability as needed
            // Randomly reduce player's health
            player.damage(DamageTypes.of(player.getWorld(),DamageTypes.OVEREXPOSURE), 1.0F);
            }
        }

    public static void Stage2Effects(ServerPlayerEntity player){
        PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
        Stage1Effects(player);
        if (player.isTouchingWaterOrRain()){
            if (waterdamagecooldownst2 == 0 && playerState.CurrentHoldTime < 50400) {
                player.damage(DamageTypes.of(player.getWorld(), DamageTypes.IRRITATION), 1.0F);
            } else if (waterdamagecooldownst2 == 0 && playerState.CurrentHoldTime > 50400) {
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
            if (playerState.CurrentHoldTime < 50400){
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 1));
            } else {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 300, 2));
            }
        }
        if (player.getWorld().getRandom().nextFloat() < 0.03) {
            // Apply slowness effect, amplifier and duration changes on hold time
            if (playerState.CurrentHoldTime < 50400){
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 1));
            } else {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 300, 2));
            }
        }

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

}


