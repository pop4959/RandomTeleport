package me.darkeyedragon.randomtp.api.location;

import me.darkeyedragon.randomtp.api.world.RandomWorld;

public interface WorldConfigSection {

    int getX();

    int getZ();

    int getRadius();

    RandomWorld getRandomWorld();

    boolean useWorldBorder();

    boolean needsWorldPermission();
}
