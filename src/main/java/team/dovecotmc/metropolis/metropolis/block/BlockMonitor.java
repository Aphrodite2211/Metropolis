package team.dovecotmc.metropolis.metropolis.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import team.dovecotmc.metropolis.metropolis.block.entity.BlockEntityMonitor;
import team.dovecotmc.metropolis.metropolis.block.entity.BlockEntityTicketMachine;
import team.dovecotmc.metropolis.metropolis.item.MetroItems;

/**
 * @author Arrokoth
 * @project Metropolis
 * @copyright Copyright © 2024 Arrokoth All Rights Reserved.
 */
public class BlockMonitor extends BlockWithEntity {
    public static final IntProperty ROTATION;

    static {
        ROTATION = Properties.ROTATION;
    }

    public BlockMonitor() {
        super(Settings.of(Material.METAL, MapColor.GRAY));
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(ROTATION, 0));
    }

    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(ROTATION, MathHelper.floor((double)(ctx.getPlayerYaw() * 16.0F / 360.0F) + 0.5) & 15);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(ROTATION, rotation.rotate((Integer)state.get(ROTATION), 16));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return (BlockState)state.with(ROTATION, mirror.mirror((Integer)state.get(ROTATION), 16));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{ROTATION});
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BlockEntityMonitor(pos, state);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }
}
