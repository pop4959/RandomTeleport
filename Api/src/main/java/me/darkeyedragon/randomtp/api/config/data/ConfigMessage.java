package me.darkeyedragon.randomtp.api.config.data;

import me.darkeyedragon.randomtp.api.world.RandomWorld;

public interface ConfigMessage {

    ConfigMessage init(String init);

    ConfigMessage initTeleportDelay(String initTeleportDelay);

    ConfigMessage teleportCanceled(String teleportCanceled);

    ConfigMessage teleport(String teleport);

    ConfigMessage depletedQueue(String depletedQueue);

    ConfigMessage countdown(String countdown);

    ConfigMessage noWorldPermission(String noWorldPermission);

    ConfigMessage emptyQueue(String emptyQueue);

    Economy getEconomy();

    String getInit();

    String getTeleport();

    String getInitTeleportDelay(long millis);

    String getTeleportCanceled();

    String getDepletedQueue();

    String getCountdown(long remainingTime);

    String getNoWorldPermission(RandomWorld randomWorld);

    String getEmptyQueue();

    interface Economy {

        Economy insufficientFunds(String insufficientFunds);

        Economy payment(String payment);

        String getInsufficientFunds();

        String getPayment();
    }
}
