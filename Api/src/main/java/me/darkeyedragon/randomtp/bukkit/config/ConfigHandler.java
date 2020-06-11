package me.darkeyedragon.randomtp.bukkit.config;

import me.darkeyedragon.randomtp.config.data.*;

public interface ConfigHandler {

    public void reload();

    public void populateConfigMessage();

    public void populateConfigQueue();

    public void populateWorldConfigSection();

    public void populateConfigTeleport();

    public void populateConfigDebug();

    public void populateConfigPlugins();

    public void populateConfigEconomy();
}
