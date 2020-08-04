package me.darkeyedragon.randomtp;

import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.PaperCommandManager;
import me.darkeyedragon.randomtp.command.TeleportCommand;
import me.darkeyedragon.randomtp.command.context.PlayerWorldContext;
import me.darkeyedragon.randomtp.config.ConfigHandler;
import me.darkeyedragon.randomtp.eco.EcoFactory;
import me.darkeyedragon.randomtp.eco.EcoHandler;
import me.darkeyedragon.randomtp.listener.CommandPreProcessListener;
import me.darkeyedragon.randomtp.listener.PluginLoadListener;
import me.darkeyedragon.randomtp.listener.WorldLoadListener;
import me.darkeyedragon.randomtp.validator.ChunkValidator;
import me.darkeyedragon.randomtp.validator.ValidatorFactory;
import me.darkeyedragon.randomtp.world.LocationQueue;
import me.darkeyedragon.randomtp.world.QueueListener;
import me.darkeyedragon.randomtp.world.WorldQueue;
import me.darkeyedragon.randomtp.world.location.LocationFactory;
import me.darkeyedragon.randomtp.world.location.search.LocationSearcherFactory;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class RandomTeleport extends JavaPlugin {

    private HashMap<UUID, Long> cooldowns;
    private PaperCommandManager manager;
    private List<ChunkValidator> validatorList;
    private WorldQueue worldQueue;
    private ConfigHandler configHandler;
    //private BaseLocationSearcher locationSearcher;
    private LocationFactory locationFactory;

    //Economy
    private Economy econ;
    private static EcoHandler ecoHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        manager = new PaperCommandManager(this);
        configHandler = new ConfigHandler(this);
        configHandler.reload();
        locationFactory = new LocationFactory(configHandler);
        //check if the first argument is a world or player
        worldQueue = new WorldQueue();
        manager.getCommandContexts().registerContext(PlayerWorldContext.class, c -> {
            String arg1 = c.popFirstArg();
            World world = Bukkit.getWorld(arg1);
            Player player = Bukkit.getPlayer(arg1);
            PlayerWorldContext context = new PlayerWorldContext();
            if (world != null) {
                context.setWorld(world);
                return context;
            } else if (player != null) {
                context.setPlayer(player);
                return context;
            } else {
                throw new InvalidCommandArgument(true);
            }
        });
        cooldowns = new HashMap<>();
        if (setupEconomy()) {
            getLogger().info("Vault found. Hooking into it.");
            EcoFactory.createDefault(econ);
        } else {
            getLogger().warning("Vault not found. Currency based options are disabled.");
        }
        manager.registerCommand(new TeleportCommand(this));
        getServer().getPluginManager().registerEvents(new WorldLoadListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandPreProcessListener(this), this);
        validatorList = new ArrayList<>();
        getLogger().info(ChatColor.AQUA + "======== [Loading validators] ========");
        configHandler.getConfigPlugin().getPlugins().forEach(s -> {
            if (getServer().getPluginManager().getPlugin(s) != null) {
                ChunkValidator validator = ValidatorFactory.createFrom(s);
                if (validator != null) {
                    if (validator.isLoaded()) {
                        getLogger().info(ChatColor.GREEN + s + " -- Successfully loaded");
                    } else {
                        getLogger().warning(ChatColor.RED + s + " is not loaded yet. Trying to fix by loading later...");
                    }
                    validatorList.add(validator);
                }
            } else {
                getLogger().warning(ChatColor.RED + s + " -- Not Found.");
            }
        });
        getServer().getPluginManager().registerEvents(new PluginLoadListener(this), this);
        getLogger().info(ChatColor.AQUA + "======================================");
        populateWorldQueue();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Unregistering commands...");
        manager.unregisterCommands();
        worldQueue.clear();
    }

    public void populateWorldQueue() {
        for (World world : configHandler.getConfigWorld().getWorlds()) {
            //Add a new world to the world queue and generate random locations
            LocationQueue locationQueue = new LocationQueue(configHandler.getConfigQueue().getSize(), LocationSearcherFactory.getLocationSearcher(world, this));
            //Subscribe to the locationqueue to be notified of changes
            subscribe(locationQueue, world);
            locationQueue.generate(getLocationFactory().getWorldConfigSection(world));
            getWorldQueue().put(world, locationQueue);

        }
    }

    public void subscribe(LocationQueue locationQueue, World world) {
        if (configHandler.getConfigDebug().isShowQueuePopulation()) {
            int size = configHandler.getConfigQueue().getSize();
            locationQueue.subscribe(new QueueListener<Location>() {
                @Override
                public void onAdd(Location element) {
                    getLogger().info("Safe location added for " + world.getName() + " (" + locationQueue.size() + "/" + size + ")");
                }

                @Override
                public void onRemove(Location element) {
                    getLogger().info("Safe location consumed for " + world.getName() + " (" + locationQueue.size() + "/" + size + ")");
                }
            });
        }
    }

    //Economy logic
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public HashMap<UUID, Long> getCooldowns() {
        return cooldowns;
    }

    public List<ChunkValidator> getValidatorList() {
        return validatorList;
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public WorldQueue getWorldQueue() {
        return worldQueue;
    }

    public LocationQueue getQueue(World world) {
        return worldQueue.get(world);
    }

    public PaperCommandManager getManager() {
        return manager;
    }

    public LocationFactory getLocationFactory() {
        return locationFactory;
    }

}
