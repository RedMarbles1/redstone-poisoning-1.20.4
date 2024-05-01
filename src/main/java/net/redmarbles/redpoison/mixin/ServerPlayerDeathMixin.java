package net.redmarbles.redpoison.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.redmarbles.redpoison.PlayerData;
import net.redmarbles.redpoison.StateSaverAndLoader;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(ServerPlayerEntity.class)
public class ServerPlayerDeathMixin {
    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onDeath(CallbackInfo info) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        PlayerData playerState = StateSaverAndLoader.getPlayerState(player);
        playerState.CurrentHoldTime = 0;
        playerState.Stage1Triggered = false;
        playerState.Stage2Triggered = false;
        playerState.Stage3Triggered = false;
        LOGGER.info("Current hold time set to" + playerState.CurrentHoldTime);
        LOGGER.info("Stage 1 triggered set to" + playerState.Stage1Triggered);
        LOGGER.info("Stage 2 triggered set to" + playerState.Stage2Triggered);
        LOGGER.info("Stage 3 triggered set to" + playerState.Stage3Triggered);
    }
}
