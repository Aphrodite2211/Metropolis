package team.dovecotmc.metropolis.metropolis.block;

import mtr.block.BlockDirectionalDoubleBlockBase;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.dovecotmc.metropolis.metropolis.Metropolis;
import team.dovecotmc.metropolis.metropolis.block.entity.BlockEntityTicketMachine;
import team.dovecotmc.metropolis.metropolis.item.ItemTicket;

/**
 * @author Arrokoth
 * @project Metropolis
 * @copyright Copyright © 2024 Arrokoth All Rights Reserved.
 */
public class BlockTicketMachine extends BlockDirectionalDoubleBlockBase implements BlockEntityProvider {
    public BlockTicketMachine() {
        super(Settings.of(Material.METAL).nonOpaque());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) {
            return ActionResult.PASS;
        }
//        CraftingScreenHandler

        if (hand == Hand.MAIN_HAND && player.getStackInHand(hand).getItem() instanceof ItemTicket) {
            System.out.println(player.getStackInHand(hand));

            int emeraldCount = 0;
            PacketByteBuf buf = PacketByteBufs.create().writeItemStack(player.getStackInHand(hand)).writeVarInt(player.getInventory().count(Items.EMERALD));
            ServerPlayNetworking.send((ServerPlayerEntity) player, Metropolis.ID_SCREEN_OPEN_TICKET_MACHINE, buf);
        }

        return ActionResult.SUCCESS;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BlockEntityTicketMachine(pos, state);
    }

    // Copied from BlockWithEntity
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity == null ? false : blockEntity.onSyncedBlockEvent(type, data);
    }

    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory)blockEntity : null;
    }
}
