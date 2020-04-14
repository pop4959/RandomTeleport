package me.darkeyedragon.randomtp.util;

import io.papermc.lib.PaperLib;
import me.darkeyedragon.randomtp.RandomTeleport;
import me.darkeyedragon.randomtp.validator.ChunkValidator;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class LocationSearcher {

    private static final String OCEAN = "ocean";
    private final EnumSet<Material> blacklistMaterial;
    private final Set<Biome> blacklistBiome;
    private final RandomTeleport plugin;
    private boolean useWorldBorder;

    /**
     * A simple utility class to help with {@link Location}
     */
    public LocationSearcher(RandomTeleport plugin, boolean useWorldBorder) {
        this.plugin = plugin;
        this.useWorldBorder = useWorldBorder;
        //Illegal material types
        blacklistMaterial = EnumSet.of(
                Material.LAVA,
                Material.CACTUS,
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
        blacklistBiome = new HashSet<>();

        //Illegal biomes
        for (Biome biome : Biome.values()) {
            if (biome.toString().toLowerCase().contains(OCEAN)) {
                blacklistBiome.add(biome);
            }
        }
    }

    /* This is the final method that will be called from the other end, to get a location */
    public CompletableFuture<Location> getRandomLocation(World world, int radius, int offsetX, int offsetZ) {
        CompletableFuture<Location> location = pickRandomLocation(world, radius, offsetX, offsetZ);
        return location.thenCompose((loc) -> {
            if (loc == null) {
                return getRandomLocation(world, radius, offsetX, offsetZ);
            } else return CompletableFuture.completedFuture(loc);
        });
    }

    /*Pick a random location based on chunks*/
    private CompletableFuture<Location> pickRandomLocation(World world, int radius, int offsetX, int offsetZ) {
        CompletableFuture<Chunk> chunk = getRandomChunk(world, radius, offsetX, offsetZ);
        return chunk.thenApply(this::getRandomLocationFromChunk);
    }

    /* Will search through the chunk to find a location that is safe, returning null if none is found. */
    private Location getRandomLocationFromChunk(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Block block = chunk.getWorld().getHighestBlockAt((chunk.getX() << 4) + x, (chunk.getZ() << 4) + z);
                if (isSafeLocation(block.getLocation())) {
                    return block.getLocation();
                }
            }
        }

        return null;
    }

    private CompletableFuture<Chunk> getRandomChunk(World world, int radius, int offsetX, int offsetZ) {
        var chunkFuture = getRandomChunkAsync(world, radius, offsetX, offsetZ);

        return chunkFuture.thenCompose((chunk) -> {
            boolean isSafe = isSafeChunk(chunk);
            if (!isSafe) {
                return getRandomChunk(world, radius, offsetX, offsetZ);
            } else return CompletableFuture.completedFuture(chunk);
        });
    }

    private CompletableFuture<Chunk> getRandomChunkAsync(World world, int radius, int offsetX, int offsetZ) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int chunkRadius = radius >> 16;
        int x = rnd.nextInt(-chunkRadius, chunkRadius);
        int z = rnd.nextInt(-chunkRadius, chunkRadius);
        return PaperLib.getChunkAtAsync(world, x + offsetX, z + offsetZ);
    }

    public boolean isSafeLocation(Location loc) {
        World world = loc.getWorld();
        if (world == null) return false;
        if (loc.add(0, 2, 0).getBlock().getType() != Material.AIR) return false;
        if (useWorldBorder) {
            if (!loc.getWorld().getWorldBorder().isInside(loc)) {
                return false;
            }
        }
        if (blacklistMaterial.contains(loc.getBlock().getType())) return false;
        for (ChunkValidator validator : plugin.getValidatorList()) {
            if (!validator.isValid(loc)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSafeChunk(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Block block = chunk.getWorld().getHighestBlockAt((chunk.getX() << 4) + x, (chunk.getZ() << 4) + z);
                if (blacklistBiome.contains(block.getBiome())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isUseWorldBorder() {
        return useWorldBorder;
    }

    public void setUseWorldBorder(boolean useWorldBorder) {
        this.useWorldBorder = useWorldBorder;
    }
}