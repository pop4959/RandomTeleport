package me.darkeyedragon.randomtp.api.config.data;

import me.darkeyedragon.randomtp.api.location.WorldConfigSection;
import me.darkeyedragon.randomtp.api.queue.LocationQueue;
import me.darkeyedragon.randomtp.api.world.RandomWorld;

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
