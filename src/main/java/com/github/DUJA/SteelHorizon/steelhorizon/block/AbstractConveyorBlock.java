package com.github.DUJA.SteelHorizon.steelhorizon.block;

import com.github.DUJA.SteelHorizon.steelhorizon.ConveyorNetwork.ConveyorNetworkManager;
import com.github.DUJA.SteelHorizon.steelhorizon.Steelhorizon;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;

public abstract class AbstractConveyorBlock extends HorizontalDirectionalBlock {
    public AbstractConveyorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if(level instanceof ServerLevel serverLevel) {
            Steelhorizon.LOGGER.info("Placing conveyor");
            Direction facing = state.getValue(FACING);
            BlockPos facingPos = pos.relative(facing);
            ConveyorNetworkManager.get(serverLevel).onBlockPlaced(serverLevel,pos,facingPos);

    }}

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
        if(level instanceof ServerLevel serverLevel) {
            ConveyorNetworkManager.get(serverLevel).onBlockRemoved(serverLevel,pos);
        }


        super.affectNeighborsAfterRemoval(state, level, pos, movedByPiston);
    }
}
