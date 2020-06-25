package me.darkeyedragon.randomtp.bukkit.sponge;

import co.aikar.commands.SpongeCommandManager;
import com.google.inject.Inject;
import me.darkeyedragon.randomtp.bukkit.sponge.command.TeleportCommand;
import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

@Plugin(
        id = "sponge",
        name = "RandomTeleport",
        version = "1.0.0"
)
public class RandomTeleport {

    private final SpongeCommandManager manager;

    @Inject
    private PluginContainer pluginContainer;

    public RandomTeleport() {
        this.manager = new SpongeCommandManager(pluginContainer);
    }

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        manager.registerCommand(new TeleportCommand(this));

    }
}
