package me.darkeyedragon.randomtp.bukkit.queue;

public interface QueueListener<T> {
    void onAdd(T element);
    void onRemove(T element);
}