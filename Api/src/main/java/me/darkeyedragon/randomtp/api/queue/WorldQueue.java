package me.darkeyedragon.randomtp.api.queue;

import me.darkeyedragon.randomtp.api.location.LocationSearcher;
import me.darkeyedragon.randomtp.api.location.RandomLocation;
import me.darkeyedragon.randomtp.api.world.RandomWorld;

import java.util.HashMap;
import java.util.Map;


public abstract class WorldQueue {

    private final Map<RandomWorld, LocationQueue> worldQueueMap;
    private final LocationSearcher locationSearcher;

    public WorldQueue(LocationSearcher locationSearcher) {
        this.worldQueueMap = new HashMap<>();
        this.locationSearcher = locationSearcher;
    }

    public LocationQueue put(RandomWorld world, LocationQueue locationQueue) {
        return worldQueueMap.put(world, locationQueue);
    }

    public LocationQueue remove(RandomWorld world) {
        return worldQueueMap.remove(world);
    }

    public LocationQueue get(RandomWorld world) {
        return worldQueueMap.get(world);
    }

    public void clear() {
        worldQueueMap.clear();
    }

    public RandomLocation popLocation(RandomWorld world) {
        LocationQueue locationQueue = get(world);
        if (locationQueue == null) {
            return null;
        }
        return locationQueue.poll();
    }

}
