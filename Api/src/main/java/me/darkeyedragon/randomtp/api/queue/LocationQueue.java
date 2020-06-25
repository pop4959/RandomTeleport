package me.darkeyedragon.randomtp.api.queue;


import me.darkeyedragon.randomtp.api.location.LocationSearcher;
import me.darkeyedragon.randomtp.api.location.RandomLocation;
import me.darkeyedragon.randomtp.api.location.WorldConfigSection;

public class LocationQueue extends ObservableQueue<RandomLocation> {
    private final LocationSearcher locationSearcher;

    public LocationQueue(int capacity, LocationSearcher locationSearcher) {
        super(capacity);
        this.locationSearcher = locationSearcher;
    }

    public boolean offer(RandomLocation location) {
        return super.offer(location);
    }

    public RandomLocation poll() {
        return super.poll();
    }

    public void generate(WorldConfigSection worldConfigSection) {
        generate(worldConfigSection, super.remainingCapacity());
    }

    public void generate(WorldConfigSection worldConfigSection, int amount) {
        for (int i = 0; i < amount; i++) {
            locationSearcher.getRandomLocation(worldConfigSection).thenAccept(this::offer);
        }
    }
}
