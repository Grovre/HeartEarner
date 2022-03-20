package me.grovre.heartearner.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandAutoCompleteMap {

    public static final HashMap<Integer, List<String>> commands = new HashMap<>();

    public static HashMap<Integer, List<String>> loadTabCompletion() {
        commands.put(0, Arrays.asList("set", "add", "remove"));

        return commands;
    }

}
