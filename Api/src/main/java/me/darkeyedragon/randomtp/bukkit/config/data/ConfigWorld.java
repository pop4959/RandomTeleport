package me.darkeyedragon.randomtp.bukkit.config.data;

import me.darkeyedragon.randomtp.bukkit.location.WorldConfigSection;
import me.darkeyedragon.randomtp.bukkit.queue.LocationQueue;
import me.darkeyedragon.randomtp.bukkit.world.RandomWorld;

import java.util.Map;
import java.util.Set;


public interface ConfigWorld {

    ConfigWorld set(Map<RandomWorld, WorldConfigSection> worldConfigSectionMap);

    WorldConfigSection get(RandomWorld world);

    Map<RandomWorld, WorldConfigSection> getWorldConfigSectionMap();

    Set<RandomWorld> getWorlds();

    boolean contains(RandomWorld world);

    boolean contains(WorldConfigSection worldConfigSection);

    LocationQueue add(WorldConfigSection worldConfigSection);


    boolean remove(RandomWorld world);
}
