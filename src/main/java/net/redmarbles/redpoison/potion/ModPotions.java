package net.redmarbles.redpoison.potion;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.redmarbles.redpoison.RedstonePoisoning;
import net.redmarbles.redpoison.item.ModItems;

public class ModPotions {

    public static final Potion IMMUNE_STAGE_1 = registerPotion("immunity_stage_1", new Potion(new StatusEffectInstance(StatusEffects.GLOWING, 3600, 0)));
    private static Potion registerPotion(String name, Potion potion){
        return Registry.register(Registries.POTION, new Identifier(RedstonePoisoning.MOD_ID, name), potion);
    }

    public static void registerPotions(){

    }

    public static void registerPotionsRecipes(){
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, ModItems.WEAKREDSTONE, ModPotions.IMMUNE_STAGE_1);
    }
}
