package net.redmarbles.redpoison;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;
import net.redmarbles.redpoison.item.ModItems;
import net.redmarbles.redpoison.potion.ModPotions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.*;

public class RedstonePoisoning implements ModInitializer {
	public static final String MOD_ID = "redstone-poisoning";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModPotions.registerPotions();
		ModPotions.registerPotionsRecipes();
		InvCheck.initialize();
		//Command to change hold time from in game (IN PROGRESS)
		/*
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("setholdtime")
				.requires(source -> source.hasPermissionLevel(2))
				.then(argument("value", IntegerArgumentType.integer())
				.executes(context -> {

					context.getSource().sendFeedback(() -> Text.literal("Called /setholdtime with no arguments"), false);

					return 1;
				})))

		 */
	}
}
