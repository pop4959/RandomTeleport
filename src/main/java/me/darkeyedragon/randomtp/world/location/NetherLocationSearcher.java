package me.darkeyedragon.randomtp.world.location;

import me.darkeyedragon.randomtp.RandomTeleport;
import me.darkeyedragon.randomtp.validator.ChunkValidator;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.EnumSet;

public class NetherLocationSearcher extends LocationSearcher {

    private final EnumSet<Material> blacklistMaterial;
    private final int MAX_HEIGHT = 120; //Everything above this is nether ceiling

    /**
     * A simple utility class to help with {@link org.bukkit.Location}
     *
     * @param plugin
     */
    public NetherLocationSearcher(RandomTeleport plugin) {
        super(plugin);
        blacklistMaterial = EnumSet.of(
                Material.LAVA,
                Material.BEDROCK,
                Material.FIRE,
                Material.TRIPWIRE,
                Material.ACACIA_PRESSURE_PLATE,
                Material.BIRCH_PRESSURE_PLATE,
                Material.JUNGLE_PRESSURE_PLATE,
                Material.OAK_PRESSURE_PLATE,
                Material.SPRUCE_PRESSURE_PLATE,
                Material.STONE_PRESSURE_PLATE,
                Material.DARK_OAK_PRESSURE_PLATE,
                Material.HEAVY_WEIGHTED_PRESSURE_PLATE,
                Material.LIGHT_WEIGHTED_PRESSURE_PLATE
        );
    }

    /* Will search through the chunk to find a location that is safe, returning null if none is found. */
    @Override
    public Location getRandomLocationFromChunk(Chunk chunk) {
        for (int x = 8; x < 16; x++) {
            for (int z = 8; z < 16; z++) {
                for (int y = 0; y < MAX_HEIGHT; y++) {
                    Block block = chunk.getWorld().getBlockAt((chunk.getX() << 4) + x, y, (chunk.getZ() << 4) + z);
                    if (isSafeLocation(block.getLocation())) {
                        return block.getLocation();
                    }
                }
            }
        }

        return null;
    }

    @Override
    public boolean isSafeLocation(Location loc) {
        final World world = loc.getWorld();
        if (world == null) return false;
        if (loc.getBlock().getType() == Material.AIR) return false;
        if (blacklistMaterial.contains(loc.getBlock().getType())) return false;
        final Location clone = loc.clone();
        if (clone.add(0, 1, 0).getBlock().getType() != Material.AIR) return false;
        if (clone.add(0, 1, 0).getBlock().getType() != Material.AIR) return false;
        for (ChunkValidator validator : super.getPlugin().getValidatorList()) {
            if (!validator.isValid(loc)) {
                return false;
            }
        }
        Block block = loc.getBlock();
        Block offsetDown = loc.getBlock().getRelative(BlockFace.DOWN);
        Block offsetUp = loc.getBlock().getRelative(BlockFace.UP);
        if (block.getType() == Material.LAVA || offsetDown.getType() == Material.LAVA || offsetUp.getType() == Material.LAVA) {
            Bukkit.getLogger().warning("========================================START===========================================");
            Bukkit.getLogger().warning("Block: " + block.getBiome().name() + ": " + block.getType().name());
            Bukkit.getLogger().warning("Block Down: " + offsetDown.getBiome().name() + ": " + offsetDown.getType().name());
            Bukkit.getLogger().warning("Block Up: " + offsetUp.getBiome().name() + ": " + offsetUp.getType().name());
            Bukkit.getLogger().warning("========================================END===========================================");
        }
        return true;
    }
}
