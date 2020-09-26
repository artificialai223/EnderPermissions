

package me.TechsCode.EnderPermissions.tpl.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.Arrays;
import org.bukkit.block.BlockFace;
import java.util.ArrayList;
import org.bukkit.block.Block;
import org.bukkit.Location;

public class BlockUtils
{
    public static Block[] getBlocksInRadius(final Location location, final int n) {
        final ArrayList<Block> list = new ArrayList<Block>();
        for (double n2 = location.getX() - n; n2 <= location.getX() + n; ++n2) {
            for (double n3 = location.getY() - n; n3 <= location.getY() + n; ++n3) {
                for (double n4 = location.getZ() - n; n4 <= location.getZ() + n; ++n4) {
                    list.add(new Location(location.getWorld(), n2, n3, n4).getBlock());
                }
            }
        }
        return list.toArray(new Block[0]);
    }
    
    public static BlockFace[] getBlockSides() {
        return new BlockFace[] { BlockFace.DOWN, BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
    }
    
    public static Block[] getAttachedBlocks(final Block block) {
        return Arrays.stream(getBlockSides()).map((Function<? super BlockFace, ?>)block::getRelative).toArray(Block[]::new);
    }
    
    public static Set<Block> getBlocksInSphere(final Location location, final int n, final boolean b) {
        final HashSet<Block> set = new HashSet<Block>();
        final int blockX = location.getBlockX();
        final int blockY = location.getBlockY();
        final int blockZ = location.getBlockZ();
        for (int i = blockX - n; i <= blockX + n; ++i) {
            for (int j = blockY - n; j <= blockY + n; ++j) {
                for (int k = blockZ - n; k <= blockZ + n; ++k) {
                    final double n2 = (blockX - i) * (blockX - i) + (blockZ - k) * (blockZ - k) + (blockY - j) * (blockY - j);
                    if (n2 < n * n && (!b || n2 >= (n - 1) * (n - 1))) {
                        set.add(new Location(location.getWorld(), (double)i, (double)j, (double)k).getBlock());
                    }
                }
            }
        }
        return set;
    }
}
