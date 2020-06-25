package me.darkeyedragon.randomtp.api.config.data;

public interface ConfigTeleport {

    ConfigTeleport cooldown(long cooldown);

    ConfigTeleport delay(long delay);

    ConfigTeleport cancelOnMove(boolean cancelOnMove);

    long getCooldown();

    long getDelay();

    boolean isCancelOnMove();
}
