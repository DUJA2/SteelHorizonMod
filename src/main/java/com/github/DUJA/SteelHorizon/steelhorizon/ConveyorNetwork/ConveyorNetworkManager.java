package com.github.DUJA.SteelHorizon.steelhorizon.ConveyorNetwork;

import java.util.*;

import com.github.DUJA.SteelHorizon.steelhorizon.Steelhorizon;
import com.github.DUJA.SteelHorizon.steelhorizon.block.AbstractConveyorBlock;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;


public class ConveyorNetworkManager extends SavedData {
    private int placed = 0;
    public static final SavedDataType<ConveyorNetworkManager> ID =
            new SavedDataType<>(
                    Identifier.fromNamespaceAndPath(Steelhorizon.MODID, "conveyor_network_manager"),
                    ConveyorNetworkManager::new,
                    RecordCodecBuilder.create(instance -> instance.group(
                            ConveyorNetwork.CODEC.listOf()
                                    .fieldOf("networks")
                                    .forGetter(ConveyorNetworkManager::getNetworks)
                    ).apply(instance, ConveyorNetworkManager::new))
            );


    public ConveyorNetworkManager() {
        Steelhorizon.LOGGER.info("Created empty manager");
        this.networks = new ArrayList<>();
    }

    public ConveyorNetworkManager(List<ConveyorNetwork> members) {
        Steelhorizon.LOGGER.info(
                "Loaded manager with {} networks",
                members.size()
        );
        this.networks = new ArrayList<>(members);
    }

    public List<ConveyorNetwork> getNetworks(){
        return networks;
    }


    private final List<ConveyorNetwork> networks;

    public static ConveyorNetworkManager get(ServerLevel serverLevel){
            return serverLevel.getDataStorage().computeIfAbsent(ID);

    }



    public int getNumNetworks(){
        return networks.size();
    }
    public void onBlockPlaced(ServerLevel level, BlockPos pos, BlockPos facing){
        placed++;
        Set<ConveyorNetwork> network = adjacentNetworks(pos,facing,level);
        Steelhorizon.LOGGER.info("Unifying a network of size {}", network.size());
        networks.add(unify(network,pos));
        for(ConveyorNetwork net : network){
            Steelhorizon.LOGGER.info("Adjacent network {}", net.getMemberSet());
            networks.remove(net);
        }


        Steelhorizon.LOGGER.info(placed +" Conveyors");
        setDirty();
    }
    public void onBlockRemoved(ServerLevel level, BlockPos pos){
        for(ConveyorNetwork net : networks){
            if(net.getMemberSet().contains(pos)){
            networks.remove(net);
            break;
            }
        }
        networks.addAll(split(pos, level));
        setDirty();
    }


    private Set<ConveyorNetwork> adjacentNetworks(BlockPos target, BlockPos facingPos, ServerLevel level){
        Set<ConveyorNetwork> adjacentNets = new HashSet<>();
        List<BlockPos> adjacentBlocks = new ArrayList<>();
        adjacentBlocks.add(facingPos);
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = target.relative(dir);
           // Steelhorizon.LOGGER.info(neighborPos.toShortString());
            BlockState state = level.getBlockState(neighborPos);
            if (state.getBlock() instanceof AbstractConveyorBlock) {

                Direction facing = state.getValue(AbstractConveyorBlock.FACING);
                if (facing == dir.getOpposite() && !neighborPos.equals(facingPos)) {
                    adjacentBlocks.add(neighborPos);
                }


            }
        }
        Steelhorizon.LOGGER.info("{} test {}", adjacentBlocks,  adjacentBlocks.size());
        for(ConveyorNetwork net : networks){
            for(BlockPos adjacentPos : adjacentBlocks){
                Steelhorizon.LOGGER.info("Net {} pos {}", net.getMemberSet(), adjacentPos);
                if(net.getMemberSet().contains(adjacentPos)){
                    adjacentNets.add(net);
                    break;
                }
            }
        }

        Steelhorizon.LOGGER.info("size {} BlockPos {}", adjacentNets.size(),  adjacentBlocks);
        return adjacentNets;
    }
    public ConveyorNetwork unify(Set<ConveyorNetwork> adjacentNets, BlockPos targetPos){
        List<BlockPos> adjacentBlocks = new ArrayList<>();
        adjacentBlocks.add(targetPos);

        for(ConveyorNetwork net : adjacentNets){
            Steelhorizon.LOGGER.info("");
            adjacentBlocks.addAll(net.getMemberSet());
        }
        return new ConveyorNetwork(adjacentBlocks);
    }


    private Set<ConveyorNetwork> split(BlockPos brokenBlock,ServerLevel level){
        Set<ConveyorNetwork> splitNets = new HashSet<>();
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = brokenBlock.relative(dir);
            BlockState state = level.getBlockState(neighborPos);
            if (state.getBlock() instanceof AbstractConveyorBlock) {
                boolean flag = false;
                for(ConveyorNetwork network : splitNets){
                    if(network.contains(neighborPos)){
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    splitNets.add(search(neighborPos, level));}
                }

        }



        return  splitNets;
    }
    private ConveyorNetwork search(BlockPos startPos, ServerLevel level){
        ConveyorNetwork searchNet = new ConveyorNetwork(List.of());
        Stack<BlockPos> stack = new Stack<>();
        stack.push(startPos);
        searchNet.addBlock(startPos);
        while(!stack.isEmpty()){
            BlockPos pos=stack.pop();
            for (Direction dir : Direction.values()) {
                BlockPos neighborPos = pos.relative(dir);
                BlockState state = level.getBlockState(neighborPos);
                if (state.getBlock() instanceof AbstractConveyorBlock) {

                    Direction facing = state.getValue(AbstractConveyorBlock.FACING);
                    if ((facing == dir.getOpposite() || neighborPos.equals(pos.relative(level.getBlockState(pos).getValue(AbstractConveyorBlock.FACING))))
                    && !searchNet.getMemberSet().contains(neighborPos)) {
                        stack.push(neighborPos);
                        searchNet.addBlock(neighborPos);
                    }


                }
            }
        }

        return searchNet;
    }



}
