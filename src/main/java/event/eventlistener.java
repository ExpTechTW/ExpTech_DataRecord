package event;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.logger;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

import static function.dataupdate.DataUpdate;
import static main.main.*;

public class eventlistener implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event){
        event.getPlayer().sendMessage("\u524d\u5f80 https://exptechweb.mywire.org/ \u53ef\u4ee5\u67e5\u8a62\u904a\u6232\u7d71\u8a08\u6578\u64da");
    }

    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent event) {
        DataUpdate(event.getPlayer(), main(event.getPlayer()));
    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent event) {
        DataUpdate(event.getPlayer(), main(event.getPlayer()));
    }

    public static JsonArray main(Player player) {
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < statisticComplex.size(); i++) {
            String Name = statisticComplex.get(i).getAsJsonObject().get("name").getAsString();
            JsonObject JsonObject = new JsonObject();
            JsonArray JsonArray = new JsonArray();
            for (int x = 0; x < Json.size(); x++) {
                JsonObject jsonObject = new JsonObject();
                String name = Json.get(x).getAsJsonObject().get("name").getAsString();
                String type=Json.get(x).getAsJsonObject().get("type").getAsJsonArray().toString();
               try {
                    if (type.contains(Name)) {
                        if (player.getStatistic(Statistic.valueOf(Name), Material.valueOf(name)) != 0) {
                            jsonObject.addProperty("name", name);
                            jsonObject.addProperty("value", player.getStatistic(Statistic.valueOf(Name), Material.valueOf(name)));
                            JsonArray.add(jsonObject);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    logger.log("TRACE","DataRecord_eventlistener" , "Name: "+Name+" name: "+name);
                    logger.log("ERROR", "DataRecord_eventlistener", e.getMessage());
                    return null;
                }
            }
            JsonObject.addProperty("name", Name);
            JsonObject.add("value", JsonArray);
            jsonArray.add(JsonObject);
        }

        for (int i = 0; i < statistic.size(); i++) {
            String name = statistic.get(i).getAsJsonObject().get("name").getAsString();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", name);
            jsonObject.addProperty("value", player.getStatistic(Statistic.valueOf(name)));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}