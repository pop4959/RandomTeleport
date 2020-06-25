package me.darkeyedragon.randomtp.api.config.data;

public interface ConfigQueue {

    ConfigQueue size(int size);

    ConfigQueue initDelay(int initDelay);

    int getSize();

    int getInitDelay();
}
