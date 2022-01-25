package main;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import commands.commands;
import core.logger;
import event.eventlistener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static main.whes1015.VersionCode;

public class main extends JavaPlugin implements Listener {

    public static Integer Support=220500;

    public static File folder;
    public static JsonArray Json=new JsonArray();
    public static JsonArray statistic=new JsonArray();
    public static JsonArray statisticComplex=new JsonArray();

    @Override
    public void onEnable() {
        if(VersionCode>=Support) {
            Json();
            folder = getDataFolder();
            Objects.requireNonNull(getCommand("dr")).setExecutor(new commands(this));
            getServer().getPluginManager().registerEvents(new eventlistener(), this);
            logger.log("INFO", "DataRecord_onEnable", "Loading Success! - Designed by ExpTech.tw!");
        }else {
            logger.log("WARN","DataRecord_onEnable","Please update your Core version");
            Bukkit.getPluginManager().disablePlugins();
        }
    }

    @Override
    public void onDisable(){
        logger.log("INFO","DataRecord_onDisable","Closing! Version: "+getDescription().getVersion());
    }

    public void Json(){
        String webPage = "https://raw.githubusercontent.com/ExpTechTW/API/%E4%B8%BB%E8%A6%81%E7%9A%84-(main)/Json/server/block.json";
        InputStream is;
        try {
            is = new URL(webPage).openStream();
        } catch (IOException e) {
            logger.log("ERROR","Core_Update",e.getMessage());
            return;
        }
        assert is != null;
        Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
        JsonElement jsonElement = JsonParser.parseReader(reader);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Json=jsonObject.get("ID").getAsJsonArray();
        statistic=jsonObject.get("Statistic").getAsJsonArray();
        statisticComplex=jsonObject.get("StatisticComplex").getAsJsonArray();
    }
}
