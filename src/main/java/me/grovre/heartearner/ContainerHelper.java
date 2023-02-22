package me.grovre.heartearner;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public final class ContainerHelper {

    private ContainerHelper() {

    }

    public static Double getMaxHearts(Player player) {
        var pdc = player.getPersistentDataContainer();
        return pdc.get(heartKey, PersistentDataType.DOUBLE);
    }

    public static void setMaxHearts(Player player, double maxHearts) {
        var pdc = player.getPersistentDataContainer();
        pdc.set(heartKey, PersistentDataType.DOUBLE, maxHearts);
    }

    public static final NamespacedKey heartKey = new NamespacedKey(HeartEarner.getPlugin(), "MaxHearts");
}
