package com.vex.theblackfog.registry;

import com.vex.theblackfog.TheBlackFog;
import com.vex.theblackfog.item.FogLanternItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TheBlackFog.MOD_ID);

    public static final RegistryObject<Item> FOG_LANTERN = ITEMS.register("fog_lantern",
            () -> new FogLanternItem(new Item.Properties().stacksTo(1)));

    private ModItems() {}
}