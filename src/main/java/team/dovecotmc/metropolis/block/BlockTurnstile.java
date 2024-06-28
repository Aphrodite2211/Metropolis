package team.dovecotmc.metropolis.block;

import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;

/**
 * @author Arrokoth
 * @project Metropolis
 * @copyright Copyright © 2024 Arrokoth All Rights Reserved.
 */
public class BlockTurnstile extends HorizontalFacingBlock {
    protected BlockTurnstile() {
        super(Settings.of(Material.METAL));
    }
}
