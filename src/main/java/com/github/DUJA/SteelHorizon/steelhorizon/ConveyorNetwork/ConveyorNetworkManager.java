package com.github.DUJA.SteelHorizon.steelhorizon.ConveyorNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;


public class ConveyorNetworkManager {
    private static final Map<Level,ConveyorNetworkManager> INSTANCES = new WeakHashMap<>();
    private final List<ConveyorNetwork> networks = new ArrayList<>();

    public static ConveyorNetworkManager get(Level level){
        return INSTANCES.computeIfAbsent(level, k -> new ConveyorNetworkManager());
    }

    public void onBlockPlaced(Level level, BlockPos pos, BlockPos facing){
        ConveyorNetwork neighborNet = null;

        for(ConveyorNetwork net : networks){
            if (net.contains(facing) || net.isNeighboring(pos)) {
                neighborNet = net;
                break;
            }
        }
        if(neighborNet != null){
            neighborNet.addBlock(pos);
        } else{
            ConveyorNetwork newNet = new ConveyorNetwork();
            newNet.addBlock(pos);
            networks.add(newNet);
        }
    }
    public void onBlockRemoved(Level level, BlockPos pos){
        ConveyorNetwork targetNet = null;
        for(ConveyorNetwork net : networks){
            if(net.contains(pos)){
                targetNet = net;
                break;
            }
        }
        if(targetNet != null){
            networks.remove(targetNet);
            //add later dumbass
        }
    }
}
