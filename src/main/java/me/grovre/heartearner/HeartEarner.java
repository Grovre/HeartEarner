package me.grovre.heartearner;

import me.grovre.heartearner.commands.SetHeartsCommand;
import me.grovre.heartearner.listeners.OnPlayerJoin;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class HeartEarner extends JavaPlugin {

    public static HeartEarner plugin;
    public static double StartingHearts;
    public static boolean AllowPurchasingHearts;
    public static int PurchaseCost;

    public static HeartEarner getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.saveDefaultConfig();
        StartingHearts = this.getConfig().getDouble("startingHealth");
        AllowPurchasingHearts = this.getConfig().getBoolean("allowHealthPurchaseWithXp");
        PurchaseCost = this.getConfig().getInt("experienceCost");

        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        var completerAndExecutor = new SetHeartsCommand();
        getCommand("heartearner").setExecutor(completerAndExecutor);
        getCommand("heartearner").setTabCompleter(completerAndExecutor);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    public static void ChangeHeartsClampHealth(Attributable attributableEntity, double newHeartCount) {
        double health;
        if (attributableEntity instanceof LivingEntity livingEntity) {
            health = livingEntity.getHealth();
        } else {
            System.err.println("Attributable is not a LivingEntity, cannot change hearts");
            return;
        }

        var maxHealthAttribute = attributableEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealthAttribute == null) {
            System.err.println("Tried changing the max health of an entity without max health attribute");
            return;
        }

        maxHealthAttribute.setBaseValue(newHeartCount);
        if (attributableEntity instanceof Player p)
            System.out.println("Setting max health of " + p.getName() + " to " + newHeartCount);
        if (newHeartCount < health)
            livingEntity.setHealth(newHeartCount - 0.1);
    }
}

/* Perms:
heartearner.
get
set
add
remove
 */