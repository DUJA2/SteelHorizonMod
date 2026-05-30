package com.github.DUJA.SteelHorizon.steelhorizon.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TieredConveyorBlock extends AbstractConveyorBlock{

    private final int tier;
    private final double speed;
   public static final MapCodec<TieredConveyorBlock > CODEC = RecordCodecBuilder.mapCodec(instance ->
           instance.group(Codec.INT.fieldOf("tier").forGetter(TieredConveyorBlock::getTier),
                   Codec.DOUBLE.fieldOf("speed").forGetter(TieredConveyorBlock::getSpeed),
                   propertiesCodec()
           ).apply(instance, TieredConveyorBlock::new)
   );

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
    public  TieredConveyorBlock(int Tier,double speed, BlockBehaviour.Properties properties) {
        super(properties);
        this.tier = Tier;
        this.speed= speed;
    }
    public double getSpeed(){return this.speed;}
    public int getTier(){return this.tier;}
}
