package com.github.DUJA.SteelHorizon.steelhorizon.datagen;

import com.github.DUJA.SteelHorizon.steelhorizon.Item.ModItems;
import com.github.DUJA.SteelHorizon.steelhorizon.Steelhorizon;
import com.github.DUJA.SteelHorizon.steelhorizon.block.ModBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.fml.common.Mod;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, Steelhorizon.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.Steel_Ingot.get(), ModelTemplates.FLAT_ITEM);
        blockModels.createTrivialCube(ModBlocks.steel_block.get());
        blockModels.createHorizontallyRotatedBlock(ModBlocks.basic_conveyor.get(), TexturedModel.ORIENTABLE_ONLY_TOP);
        itemModels.generateFlatItem(ModBlocks.basic_conveyor.get().asItem(), ModelTemplates.FLAT_ITEM);
    }

    }
