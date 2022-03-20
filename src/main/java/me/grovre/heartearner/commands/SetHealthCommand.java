package me.grovre.heartearner.commands;

import me.grovre.heartearner.dbUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.UUID;

public class SetHealthCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, @NonNull String[] args) {

        Player sender = s instanceof Player ? (Player) s : null;

        if(args[0].equalsIgnoreCase("emergency")) {
            for(UUID uuid : dbUtil.playerHealths.keySet()) {
                dbUtil.playerHealths.put(uuid, 20D);
            }
            return true;
        }

        if(args.length != 3) {
            if(sender != null) {
                sender.sendMessage(ChatColor.RED + "You need to use the command like /heartearner set player 10");
            }
            System.out.println("You need to use the command like /heartearner set player 10");
            return true;
        }

        if(!args[0].equalsIgnoreCase("set") &&
                !args[0].equalsIgnoreCase("add") &&
                !args[0].equalsIgnoreCase("remove")) {
            if(sender != null) {
                sender.sendMessage(ChatColor.RED + "You need to use a valid command. set/add/remove");
            }
            System.out.println("A valid command was not used. set/add/remove are valid commands.");
            return true;
        }

        Player player = Bukkit.getPlayer(args[1]);
        if(player == null) {
            if(sender != null) {
                sender.sendMessage(ChatColor.RED + "That player doesn't exist or needs to be online!");
            }
            System.out.println("That player doesn't exist or needs to be online!");
            return true;
        }
        PlayerHealthInfo playerHealthInfo = new PlayerHealthInfo(player);

        double health = Double.parseDouble(args[2]);
        dbUtil.playerHealths.put(player.getUniqueId(), health);
        dbUtil.saveData();
        if(args[0].equalsIgnoreCase("set")) {
            playerHealthInfo.setMaxHealth(health).setHealthScale(health);
        } else if(args[0].equalsIgnoreCase("add")) {
            playerHealthInfo.addToMaxHealth(health).addToScale(health);
        } else if(args[0].equalsIgnoreCase("remove")) {
            playerHealthInfo.removeFromMaxHealth(health).removeFromScale(health);
        }
        playerHealthInfo.pushUpdate();


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return CommandAutoCompleteMap.commands.get(args.length);
    }
}
