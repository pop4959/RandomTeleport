package me.darkeyedragon.randomtp.automate;

import me.darkeyedragon.randomtp.RandomTeleport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.concurrent.atomic.AtomicInteger;

public class Timer {

    public void startTimer(RandomTeleport plugin) {
        AtomicInteger processed = new AtomicInteger();

        Bukkit.getScheduler().runTaskTimer(plugin, bukkitTask -> {
            World world = Bukkit.getWorld("world_nether");
            Location location = plugin.getWorldQueue().popLocation(world);
            if (location != null) {
                plugin.getWorldQueue().get(world).generate(plugin.getLocationFactory().getWorldConfigSection(world));
            }
            processed.set(processed.get() + 1);
            if (processed.get() % 10 == 0) {
                Bukkit.getLogger().info("Processed: " + processed.get());
            }
        }, 80L, 20L);
    }
}
