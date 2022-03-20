package me.grovre.heartearner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class dbUtil {

    public static HashMap<UUID, Double> playerHealths;
    private static final File healthDataFile = new File(
            HeartEarner.plugin.getDataFolder().getAbsolutePath() + File.separator + "PlayerHealthData.json"
    );

    public static void loadData() {
        if(!healthDataFile.exists()) {
            try {
                healthDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Gson gson = new Gson();
        try {
            FileReader fr = new FileReader(healthDataFile);
            playerHealths = gson.fromJson(fr, new TypeToken<HashMap<UUID, Double>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(playerHealths == null) playerHealths = new HashMap<>();
    }

    public static void saveData() {
        final Map<UUID, Double> tempHealthData = Collections.unmodifiableMap(new HashMap<>(playerHealths));
        System.out.println("Saving health data" + tempHealthData);

        Bukkit.getScheduler().runTaskAsynchronously(HeartEarner.getPlugin(), () -> {
            if(!healthDataFile.exists()) {
                try {
                    healthDataFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Gson gson = new Gson();
            try {
                FileWriter fw = new FileWriter(healthDataFile, false);
                fw.write(gson.toJson(tempHealthData));
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
