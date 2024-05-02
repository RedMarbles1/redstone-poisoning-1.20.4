package net.redmarbles.redpoison;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;
import net.redmarbles.redpoison.item.ModItems;
import net.redmarbles.redpoison.potion.ModPotions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class RedstonePoisoning implements ModInitializer {
	public static final String MOD_ID = "redstone-poisoning";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModPotions.registerPotions();
		ModPotions.registerPotionsRecipes();
		InvCheck.initialize();
		ModCommands.RegisterModCommands();


	}
}
