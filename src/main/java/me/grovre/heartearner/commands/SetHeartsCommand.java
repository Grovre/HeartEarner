package me.grovre.heartearner.commands;

import me.grovre.heartearner.ContainerHelper;
import me.grovre.heartearner.HeartEarner;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SetHeartsCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!"HeartEarner".equalsIgnoreCase(s)) {
            return false;
        }

        if (args.length < 2) {
            return false;
        }

        var player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            commandSender.sendMessage("Cannot find player with that name. Please try again");
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("get")) {
            if (!commandSender.hasPermission("heartearner.get")) {
                commandSender.sendMessage("You don't have permission to do this");
                return false;
            }
            commandSender.sendMessage(player.getName() + " has " + (ContainerHelper.getMaxHearts(player) / 2) + " max hearts available");
            return true;
        }

        if (args.length != 3)
            return false;

        if (args[0].equalsIgnoreCase("set")) {
            if (!commandSender.hasPermission("heartearner.set")) {
                commandSender.sendMessage("You don't have permission to do this");
                return false;
            }
            var setTo = Integer.parseInt(args[2]);
            HeartEarner.ChangeHeartsClampHealth(player, setTo * 2D);
            ContainerHelper.setMaxHearts(player, setTo * 2D);
            return true;
        }

        if (args[0].equalsIgnoreCase("addHearts")) {
            if (!commandSender.hasPermission("heartearner.add")) {
                commandSender.sendMessage("You don't have permission to do this");
                return false;
            }
            var toAdd = Integer.parseInt(args[2]) * 2D;
            var max = toAdd + ContainerHelper.getMaxHearts(player);
            HeartEarner.ChangeHeartsClampHealth(player, max);
            ContainerHelper.setMaxHearts(player, max);
            return true;
        }

        if (args[0].equalsIgnoreCase("removeHearts")) {
            if (!commandSender.hasPermission("heartearner.remove")) {
                commandSender.sendMessage("You don't have permission to do this");
                return false;
            }
            var toRemove = Integer.parseInt(args[2]) * 2D;
            var max = ContainerHelper.getMaxHearts(player);
            max -= toRemove;
            if (max <= 0) {
                commandSender.sendMessage("Cannot do this! Player would have <= 0 hearts left");
                return false;
            }
            HeartEarner.ChangeHeartsClampHealth(player, max);
            ContainerHelper.setMaxHearts(player, max);
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (!"HeartEarner".equalsIgnoreCase(s)) {
            return null;
        }

        if (args.length == 1)
            return List.of("get", "set", "addHearts", "removeHearts");
        if (args.length == 2)
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        if (args.length == 3)
            return IntStream.range(1, 10).mapToObj(Integer::toString).toList();
        return null;
    }
}
