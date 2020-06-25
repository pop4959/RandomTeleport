package me.darkeyedragon.randomtp.api.location;


import me.darkeyedragon.randomtp.api.world.RandomWorld;

public interface LocationFactory {

    WorldConfigSection getWorldConfigSection(RandomWorld randomWorld);
}
