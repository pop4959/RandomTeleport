package me.darkeyedragon.randomtp.api.location;


import me.darkeyedragon.randomtp.api.world.RandomWorld;

public interface RandomLocation {

    int getBlockX();

    int getBlockY();

    int getBlockZ();

    RandomWorld getRandomWorld();
}
