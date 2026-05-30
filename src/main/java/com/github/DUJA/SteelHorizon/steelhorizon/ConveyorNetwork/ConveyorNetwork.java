package com.github.DUJA.SteelHorizon.steelhorizon.ConveyorNetwork;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;

import java.util.*;

public class ConveyorNetwork {

    public ConveyorNetwork(List<BlockPos> members) {
        this.member = new HashSet<>(members);
    }


    public static final Codec<ConveyorNetwork> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    BlockPos.CODEC.listOf()
                            .fieldOf("members")
                            .forGetter(ConveyorNetwork::getMemberList)
            ).apply(instance, ConveyorNetwork::new));


    private final Set<BlockPos> member;
    public void addBlock(BlockPos pos){
        member.add(pos);
    }
    public boolean contains(BlockPos pos){
        return member.contains(pos);
    }

    public int getMemberCount(){
        return member.size();
    }

    public List<BlockPos> getMemberList() {
        return new ArrayList<>(member);
    }

    public Set<BlockPos> getMemberSet() {
        return member;
    }
    public void removeBlock(BlockPos pos){
        member.remove(pos);
    }
}
