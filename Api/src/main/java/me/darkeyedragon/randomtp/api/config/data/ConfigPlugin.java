package me.darkeyedragon.randomtp.api.config.data;

import java.util.Collection;
import java.util.Set;

public interface ConfigPlugin {

    void add(String plugin);

    ConfigPlugin addAll(Collection<String> collection);

    Set<String> getPlugins();
}
