package main;

import com.google.gson.JsonArray;
import commands.commands;
import core.logger;
import event.eventlistener;
import handler.timer;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

import static function.dataupdate.DataUpdate;
import static main.whes1015.VersionCode;

public class main extends JavaPlugin implements Listener {

    public static Integer Support=22031;

    public static File folder;
    public static JsonArray BlockArray = new JsonArray();

    @Override
    public void onEnable() {
        if(VersionCode>=Support) {
            folder = getDataFolder();
            Objects.requireNonNull(getCommand("dr")).setExecutor(new commands(this));
            getServer().getPluginManager().registerEvents(new eventlistener(), this);
            timer.main();
            logger.log("INFO", "DataRecord_onEnable", "Loading Success! - Designed by ExpTech.tw!");
        }else {
            logger.log("WARN","DataRecord_onEnable","Please update your Core version");
            Bukkit.getPluginManager().disablePlugins();
        }
    }

    @Override
    public void onDisable(){
        DataUpdate();
        logger.log("INFO","DataRecord_onDisable","Closing! Version: "+getDescription().getVersion());
    }
}
