package me.darkeyedragon.randomtp.api.config.data;

public interface ConfigEconomy {

    ConfigEconomy price(double price);

    double getPrice();

    /**
     * If the plugin is charging money to rtp.
     * NOTE: Will return false if price is not set
     *
     * @return true if price is greater than 0
     */
    boolean useEco();

}
