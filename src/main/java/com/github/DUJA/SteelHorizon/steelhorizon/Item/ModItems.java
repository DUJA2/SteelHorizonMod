package com.github.DUJA.SteelHorizon.steelhorizon.Item;

import com.github.DUJA.SteelHorizon.steelhorizon.Steelhorizon;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Steelhorizon.MODID);

    public static final DeferredItem<Item> Steel_Ingot = ITEMS.registerSimpleItem("steel_ingot");

    public static void Register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
