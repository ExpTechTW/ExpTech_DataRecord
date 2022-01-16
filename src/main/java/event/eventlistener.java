package event;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import core.logger;
import core.network;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Objects;

import static main.main.BlockArray;
import static main.whes1015.DATA;

public class eventlistener implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        JsonElement Data= JsonParser.parseString(DATA.toString());
        JsonObject data=Data.getAsJsonObject();
        data.addProperty("Type","PlayerJoinEvent");
        data.addProperty("FormatVersion", 1);
        data.addProperty("Player",player.getName());
        data.addProperty("Uuid", String.valueOf(player.getUniqueId()));
        data.addProperty("Ip", String.valueOf(player.getAddress()));
        data.addProperty("Op",player.isOp());
        JsonObject jsonObject=network.Post(data);
        if (jsonObject.get("response").getAsJsonObject().get("response").getAsString().equals("Unable to confirm")){
            event.getPlayer().sendMessage(ChatColor.RED+"\u7121\u6cd5\u78ba\u8a8d\u4f60\u7684\u5e33\u865f\u6578\u64da\uff0c\u6578\u64da\u6709\u907a\u5931\u98a8\u96aa\uff0c\u5efa\u8b70\u76e1\u5feb\u7d81\u5b9a\u5e33\u865f");
        }
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        if(event.isCancelled()) return;
        if(event.getPlayer().getGameMode()!= GameMode.SURVIVAL) return;
        JsonArray Array =BlockArray.getAsJsonArray();
        int Find=-1;
        for (int i = 0; i < Array.size(); i++) {
            if(event.getPlayer().getName().contains(".")){
                if(Objects.equals(Array.get(i).getAsJsonObject().get("name").getAsString(), event.getPlayer().getName())){
                    Find=i;
                    break;
                }
            }else {
                if(Objects.equals(Array.get(i).getAsJsonObject().get("uuid").getAsString(), String.valueOf(event.getPlayer().getUniqueId()))){
                    Find=i;
                    break;
                }
            }
        }
        if(Find==-1){
            JsonArray PlayerArray = new JsonArray();
            JsonObject PlayerObject = new JsonObject();
            PlayerObject.addProperty("uuid",String.valueOf(event.getPlayer().getUniqueId()));
            PlayerObject.addProperty("name",event.getPlayer().getName());
            JsonObject blockObject = new JsonObject();
            blockObject.addProperty("name", event.getBlock().getBlockData().getAsString());
            blockObject.addProperty("value",1);
            PlayerArray.add(blockObject);
            PlayerObject.add("value",PlayerArray);
            Array.add(PlayerObject);
        }else {
            JsonArray array = Array.get(Find).getAsJsonObject().get("value").getAsJsonArray();
            int find = -1;
            for (int i = 0; i < array.size(); i++) {
                if (Objects.equals(array.get(i).getAsJsonObject().get("name").getAsString(), event.getBlock().getBlockData().getAsString())) {
                    array.get(i).getAsJsonObject().addProperty("value", array.get(i).getAsJsonObject().get("value").getAsInt() + 1);
                    find = i;
                    break;
                }
            }
            if (find == -1) {
                JsonObject blockObject = new JsonObject();
                blockObject.addProperty("name", event.getBlock().getBlockData().getAsString());
                blockObject.addProperty("value",1);
                array.add(blockObject);
                Array.get(Find).getAsJsonObject().add("value",array);
            }
        }
    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        JsonElement Data= JsonParser.parseString(DATA.toString());
        JsonObject data=Data.getAsJsonObject();
        data.addProperty("Type","PlayerQuitEvent");
        data.addProperty("FormatVersion", 1);
        data.addProperty("Uuid", String.valueOf(player.getUniqueId()));
        network.Post(data);
    }

    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        JsonElement Data= JsonParser.parseString(DATA.toString());
        JsonObject data=Data.getAsJsonObject();
        data.addProperty("Type","PlayerRespawnEvent");
        data.addProperty("FormatVersion", 1);
        data.addProperty("Uuid", String.valueOf(player.getUniqueId()));
        network.Post(data);
    }

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event){

    }
}