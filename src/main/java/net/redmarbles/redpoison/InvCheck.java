package net.redmarbles.redpoison;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;


public class InvCheck {

    public static int MaxHoldTimeUntilStage1 = 72000;
    public static int MaxHoldTimeUntilStage2 = 144000;
    public static int MaxHoldTimeUntilStage3 = 196000;

    public static void initialize() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (world instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld) world;
                for (ServerPlayerEntity player : serverWorld.getPlayers()) {
                    checkInventory(player);
                }
            }
        });
    }

    private static void checkInventory(ServerPlayerEntity player) {
        PlayerData playerState = StateSaverAndLoader.getPlayerState(player);


        int DustCount = countItem(player, Items.REDSTONE);
        int BlockCount = countItem(player, Items.REDSTONE_BLOCK);

        // Check if any of the item counts are over the limit
        if (DustCount >= 64 || BlockCount >= 16) {
            //Increase hold time count
            playerState.CurrentHoldTime++;

            if (MaxHoldTimeUntilStage2 > playerState.CurrentHoldTime && playerState.CurrentHoldTime >= MaxHoldTimeUntilStage1){
                //Start stage 1 effects and make Stage1Triggered true
                PoisonEffects.Stage1Effects(player);
                playerState.Stage1Triggered = true;
            } else if (MaxHoldTimeUntilStage3 > playerState.CurrentHoldTime && playerState.CurrentHoldTime >= MaxHoldTimeUntilStage2) {
                //Start stage 2 effects and make Stage2Triggered true
                PoisonEffects.Stage2Effects(player);
                playerState.Stage2Triggered = true;
            }


        } else if (!playerState.Stage2Triggered) {
            //Check if player is submerged and cleanse the player appropiately
            if (player.isSubmergedInWater()) {
                playerState.CurrentHoldTime -= 6000;
            } else if (playerState.Stage3Triggered) {

            } else {
            playerState.CurrentHoldTime--;
            }
        }
        if (playerState.CurrentHoldTime < MaxHoldTimeUntilStage1) {
            playerState.Stage1Triggered = false;
        }
        if (playerState.CurrentHoldTime < 0) {
            playerState.CurrentHoldTime = 0;
        }
    }

    private static int countItem(PlayerEntity player, Item item) {
        int count = 0;
        for (ItemStack stack : player.getInventory().main) {
            if (stack.getItem() == item) {
                count += stack.getCount();
            }
        }
        return count;
    }
}

