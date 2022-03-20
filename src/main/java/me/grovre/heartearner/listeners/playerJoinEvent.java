package me.grovre.heartearner.listeners;

import me.grovre.heartearner.HeartEarner;
import me.grovre.heartearner.commands.PlayerHealthInfo;
import me.grovre.heartearner.dbUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerJoinEvent implements Listener {

    public void playerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(!player.hasPlayedBefore()) {
            Bukkit.getConsoleSender().sendMessage("heartearner set "
                    + player.getName()
                    + " "
                    + HeartEarner.plugin.getConfig().getDouble("startingHealth"));
        } else {
            double health = dbUtil.playerHealths.get(player.getUniqueId());
            new PlayerHealthInfo(player).setHealthScale(health).setMaxHealth(health).pushUpdate();
        }
    }
}
