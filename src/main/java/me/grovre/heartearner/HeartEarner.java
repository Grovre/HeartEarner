package me.grovre.heartearner;

import me.grovre.heartearner.commands.CommandAutoCompleteMap;
import me.grovre.heartearner.commands.SetHealthCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class HeartEarner extends JavaPlugin {

    public static HeartEarner plugin;

    public static HeartEarner getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.saveDefaultConfig();
        dbUtil.loadData();
        CommandAutoCompleteMap.loadTabCompletion();

        Objects.requireNonNull(getServer().getPluginCommand("heartearner")).setExecutor(new SetHealthCommand());
        Objects.requireNonNull(getServer().getPluginCommand("heartearner")).setTabCompleter(new SetHealthCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        dbUtil.saveData();
    }
}
