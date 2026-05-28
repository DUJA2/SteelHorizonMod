package com.github.DUJA.SteelHorizon.steelhorizon.ConveyorNetwork;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConveyorNetwork {
    private final Set<BlockPos> member = new HashSet<>();
    public void addBlock(BlockPos pos){
        member.add(pos);
    }
    public boolean contains(BlockPos pos){
        return member.contains(pos);
    }
    public boolean isNeighboring(BlockPos pos){
        for(BlockPos member: member){
            if(member.closerThan(pos, 1.99)){
                return true;
            }
        }
        return false;
    }
    public int getMemberCount(){
        return member.size();
    }

    public Set<BlockPos> getMember() {
        return member;
    }
}
