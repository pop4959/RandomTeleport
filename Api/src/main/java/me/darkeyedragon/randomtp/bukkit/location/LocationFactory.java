package me.darkeyedragon.randomtp.bukkit.location;


import me.darkeyedragon.randomtp.bukkit.world.RandomWorld;

public interface LocationFactory {

    WorldConfigSection getWorldConfigSection(RandomWorld randomWorld);
}
