package me.darkeyedragon.randomtp.api.location;

import me.darkeyedragon.randomtp.api.world.RandomChunk;

import java.util.concurrent.CompletableFuture;

public interface LocationSearcher {


    /* This is the final method that will be called from the other end, to get a location */
    CompletableFuture<RandomLocation> getRandomLocation(WorldConfigSection worldConfigSection);

    /*Pick a random location based on chunks*/
    CompletableFuture<RandomLocation> pickRandomLocation(WorldConfigSection worldConfigSection);

    /* Will search through the chunk to find a location that is safe, returning null if none is found. */
    RandomLocation getRandomLocationFromChunk(RandomChunk chunk);

    CompletableFuture<RandomChunk> getRandomChunk(WorldConfigSection worldConfigSection);

    CompletableFuture<RandomChunk> getRandomChunkAsync(WorldConfigSection worldConfigSection);

    boolean isSafeLocation(RandomLocation loc);

    boolean isSafeChunk(RandomChunk chunk);

    boolean isUseWorldBorder();

    void setUseWorldBorder(boolean useWorldBorder);
}
