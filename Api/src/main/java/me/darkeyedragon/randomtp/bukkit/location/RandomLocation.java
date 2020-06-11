package me.darkeyedragon.randomtp.bukkit.location;

import me.darkeyedragon.randomtp.bukkit.world.RandomWorld;

public interface RandomLocation {

    int getBlockX();

    int getBlockY();

    int getBlockZ();

    RandomWorld getRandomWorld();
}
