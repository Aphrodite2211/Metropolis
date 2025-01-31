package team.dovecotmc.metropolis.item;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Arrokoth
 * @project Metropolis
 * @copyright Copyright © 2024 Arrokoth All Rights Reserved.
 */
public class ItemBlockWithTooltipShowStationHUD extends BlockItem implements IItemShowStationHUD {
    public final Style style;
    public ItemBlockWithTooltipShowStationHUD(Block block, Item.Settings settings) {
        this(block, settings, Style.EMPTY);
    }

    public ItemBlockWithTooltipShowStationHUD(Block block, Item.Settings settings, Style style) {
        super(block, settings);
        this.style = style;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip." + Registry.ITEM.getId(this).toTranslationKey()).setStyle(this.style));
    }
}
