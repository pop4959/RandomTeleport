package me.darkeyedragon.randomtp.bukkit.world;

public interface QueueListener<T> {
    void onAdd(T element);
    void onRemove(T element);
}