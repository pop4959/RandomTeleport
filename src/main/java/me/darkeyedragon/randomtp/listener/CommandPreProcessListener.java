package me.darkeyedragon.randomtp.listener;

import me.darkeyedragon.randomtp.RandomTeleport;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandPreProcessListener implements Listener {

    RandomTeleport plugin;

    public CommandPreProcessListener(RandomTeleport plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommandPreProcess(ServerCommandEvent event) {
        String commandStr = event.getCommand();
        World world = Bukkit.getWorlds().get(0);
        validate(commandStr, world);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerPreProcess(PlayerCommandPreprocessEvent event) {
        String commandStr = event.getMessage();
        World world = event.getPlayer().getWorld();
        validate(commandStr, world);
    }

    private void validate(String commandStr, World world) {
        if (commandStr.contains("worldborder ")) {
            String[] args = commandStr.split(" ");
            if (args.length < 2) return;
            if (!args[1].equalsIgnoreCase("add") && !args[1].equalsIgnoreCase("set") && !args[1].equalsIgnoreCase("center")) {
                return;
            }
            try {
                Integer.parseInt(args[2]);
            } catch (NumberFormatException ignored) {
                return;
            }
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                plugin.getWorldQueue().get(world).clear();
                plugin.getWorldQueue().get(world).generate(plugin.getLocationFactory().getWorldConfigSection(world), plugin.getConfigHandler().getConfigQueue().getSize());
            }, 1);
        }
    }
}
