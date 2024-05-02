package net.redmarbles.redpoison;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class DamageTypes {
    public static final RegistryKey<DamageType> OVEREXPOSURE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(RedstonePoisoning.MOD_ID, "overexposure"));
    public static final RegistryKey<DamageType> IRRITATION= RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(RedstonePoisoning.MOD_ID, "water_irritated"));
    public static DamageSource of(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }
}
