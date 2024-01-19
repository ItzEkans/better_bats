package cd.ekans.betterbats.item;

import cd.ekans.betterbats.BetterBats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BetterBats.MODID);

    public static final RegistryObject<Item> RAW_BAT = ITEMS.register("raw_bat",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(Foods.RAW_BAT)));
    public static final RegistryObject<Item> COOKED_BAT = ITEMS.register("cooked_bat",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(Foods.COOKED_BAT)));

    public static class Foods {
        public static final FoodProperties RAW_BAT = new FoodProperties.Builder()
                .nutrition(2)
                .saturationMod(0.2f)
                .meat()
                .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 300, 0), 1)
                .effect(() -> new MobEffectInstance(MobEffects.POISON, 100, 0), 1)
                .build();
        public static final FoodProperties COOKED_BAT = new FoodProperties.Builder()
                .nutrition(6)
                .saturationMod(0.6f)
                .meat()
                .build();
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
