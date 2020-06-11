package me.darkeyedragon.randomtp.bukkit.location;

import me.darkeyedragon.randomtp.bukkit.world.RandomWorld;

public class WorldConfigSection {

    private final int x;
    private final int z;
    private final int radius;
    private final RandomWorld randomWorld;
    private final boolean useWorldBorder;
    private final boolean needsWorldPermission;

    public WorldConfigSection(int x, int z, int radius, RandomWorld randomWorld, boolean useWorldBorder,boolean needsWorldPermission) {
        this.x = x;
        this.z = z;
        this.radius = radius;
        this.randomWorld = randomWorld;
        this.useWorldBorder = useWorldBorder;
        this.needsWorldPermission = needsWorldPermission;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public int getRadius() {
        return radius;
    }

    public RandomWorld getRandomWorld() {
        return randomWorld;
    }

    public boolean useWorldBorder() {
        return useWorldBorder;
    }

    public boolean needsWorldPermission() {
        return needsWorldPermission;
    }
}
