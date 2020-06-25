package me.darkeyedragon.randomtp.api.config;

public interface ConfigHandler {

    void reload();

    void populateConfigMessage();

    void populateConfigQueue();

    void populateWorldConfigSection();

    void populateConfigTeleport();

    void populateConfigDebug();

    void populateConfigPlugins();

    void populateConfigEconomy();
}
