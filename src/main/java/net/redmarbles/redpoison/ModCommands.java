package net.redmarbles.redpoison;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.text.Text;
import static net.minecraft.server.command.CommandManager.*;
import com.mojang.brigadier.arguments.IntegerArgumentType;

public class ModCommands {
    public static void RegisterModCommands(){

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("setholdtime")
				.requires(source -> source.hasPermissionLevel(2))
                .then(argument("target", EntityArgumentType.player())
				    .then(argument("value", IntegerArgumentType.integer())
				        .executes(context -> {
                            double val = IntegerArgumentType.getInteger(context, "value");
                            String plr = EntityArgumentType.getPlayer(context, "target").getName().toString();
                            PlayerData state = StateSaverAndLoader.getPlayerState(EntityArgumentType.getPlayer(context, "target"));
                            state.CurrentHoldTime = val;
                            if(val>72000){
                                state.Stage1Triggered = true;
                            }
                            if(val>144000){
                                state.Stage2Triggered = true;
                            }
                            if(val>216000){
                                state.Stage3Triggered = true;
                            }
					        context.getSource().sendFeedback(() -> Text.literal("Set hold time for player "+ plr + "to " + val), true);

					    return 1;
				}
                )))));

        RedstonePoisoning.LOGGER.info("Commands registered for mod" + RedstonePoisoning.MOD_ID);
    }
}
