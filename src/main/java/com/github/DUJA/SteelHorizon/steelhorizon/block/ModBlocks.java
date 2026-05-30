package com.github.DUJA.SteelHorizon.steelhorizon.block;

import com.github.DUJA.SteelHorizon.steelhorizon.Item.ModItems;
import com.github.DUJA.SteelHorizon.steelhorizon.Steelhorizon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;


public class ModBlocks {
     public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Steelhorizon.MODID);

     public static final DeferredBlock<Block> steel_block = registerBlock("steel_block",
             properties -> new Block(properties.strength(5f)
                     .requiresCorrectToolForDrops().sound(SoundType.IRON)
                     .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Steelhorizon.MODID,"steel_block")))));

     public static final DeferredBlock<Block> basic_conveyor = registerBlock("basic_conveyor", properties ->
             new TieredConveyorBlock(ConveyorTier.BASIC.tierNumber,ConveyorTier.BASIC.speed,properties.strength(5f).requiresCorrectToolForDrops()
                     .setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath(Steelhorizon.MODID,"basic_conveyor")))));

     private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties,T> function){
         DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
         registerBlockItem(name, toReturn);
         return toReturn;
     }

     private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
         ModItems.ITEMS.registerItem(name, _ -> new BlockItem(block.get(), new Item.Properties()
                 .useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Steelhorizon.MODID,name)))));

     }

     public static void register(IEventBus eventBus){
         BLOCKS.register(eventBus);
     }
}
