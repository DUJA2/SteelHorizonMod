package com.github.DUJA.SteelHorizon.steelhorizon.ModCommands;

import com.github.DUJA.SteelHorizon.steelhorizon.ConveyorNetwork.ConveyorNetwork;
import com.github.DUJA.SteelHorizon.steelhorizon.ConveyorNetwork.ConveyorNetworkManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NetworksCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("networks").then(Commands.literal("size").executes(NetworksCommand::size))
                .then(Commands.literal("list").executes(NetworksCommand::list)));
    }
    private static int size(CommandContext<CommandSourceStack> command) {
        ServerLevel level = command.getSource().getLevel();
        if(command.getSource().getEntity() instanceof Player player) {
            if (!level.isClientSide()) {
            player.sendSystemMessage(Component.literal(String.valueOf(ConveyorNetworkManager.get(level).getNumNetworks())));}
        }

        return Command.SINGLE_SUCCESS;
    }
    private static int list(CommandContext<CommandSourceStack> command) {
        ServerLevel level = command.getSource().getLevel();
        if(command.getSource().getEntity() instanceof Player player) {
            if (!level.isClientSide()) {
                ArrayList<List<BlockPos>> pos = new ArrayList<>();
                for(ConveyorNetwork network : ConveyorNetworkManager.get(level).getNetworks()) {
                    List<BlockPos> list = network.getMemberList();
                    pos.add(list);
                }
                player.sendSystemMessage(Component.literal(Arrays.toString(pos.toArray())));}
        }

        return Command.SINGLE_SUCCESS;
    }
    }
