package me.grovre.heartearner.listeners;

import me.grovre.heartearner.ContainerHelper;
import me.grovre.heartearner.HeartEarner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;

public class OnPlayerJoin implements Listener {
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        var player = event.getPlayer();
        var maxHearts = ContainerHelper.getMaxHearts(player);
        if (!player.hasPlayedBefore() || maxHearts == null) {
            ContainerHelper.setMaxHearts(player, HeartEarner.StartingHearts);
            HeartEarner.ChangeHeartsClampHealth(player, HeartEarner.StartingHearts);
            System.out.println("Player does not have contained heart count. Setting it to config default "
                    + "(" + HeartEarner.StartingHearts + ")");
            return;
        }

        HeartEarner.ChangeHeartsClampHealth(player, maxHearts);
    }
}
