package com.github.DUJA.SteelHorizon.steelhorizon;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;


public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();




    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(Identifier.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {

    }
}
