package event;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

import java.util.Objects;

import static function.dataupdate.DataUpdate;
import static main.main.*;

public class eventlistener implements Listener {

    @EventHandler
    public void PlayerStatisticIncrementEvent(PlayerStatisticIncrementEvent event) {
            DataUpdate(event.getPlayer(),main(event.getPlayer()));
    }

    public static JsonArray main(Player player) {
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < statisticComplex.size(); i++) {
            String Name=statisticComplex.get(i).getAsJsonObject().get("name").getAsString();
            JsonObject JsonObject = new JsonObject();
            JsonArray JsonArray = new JsonArray();
            for (int x = 0; x < Json.size(); x++) {
                JsonObject jsonObject = new JsonObject();
                String name = Json.get(x).getAsJsonObject().get("name").getAsString();
                String type=Json.get(x).getAsJsonObject().get("type").getAsString();
                if(Objects.equals(type, "block")&&!Objects.equals(Name, "ENTITY_KILLED_BY")&&!Objects.equals(Name, "KILL_ENTITY")) {
                    if (player.getStatistic(Statistic.valueOf(Name), Material.valueOf(name)) != 0) {
                        jsonObject.addProperty("name", name);
                        jsonObject.addProperty("value", player.getStatistic(Statistic.valueOf(Name), Material.valueOf(name)));
                        JsonArray.add(jsonObject);
                    }
                }else if(Objects.equals(type, "entity")){
                    if (player.getStatistic(Statistic.valueOf(Name), EntityType.valueOf(name)) != 0) {
                        jsonObject.addProperty("name", name);
                        jsonObject.addProperty("value", player.getStatistic(Statistic.valueOf(Name), EntityType.valueOf(name)));
                        JsonArray.add(jsonObject);
                    }
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