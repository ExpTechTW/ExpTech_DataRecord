package function;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import core.logger;
import core.network;
import org.bukkit.entity.Player;

import static main.whes1015.DATA;

public class dataupdate {

    public static void DataUpdate(Player player, JsonArray jsonArray) {
        logger.log("TRACE", "DataUpdate", "null");
        JsonElement Data = JsonParser.parseString(DATA.toString());
        JsonObject data = Data.getAsJsonObject();
        data.addProperty("Type", "Statistic");
        data.addProperty("Uuid", String.valueOf(player.getUniqueId()));
        data.addProperty("FormatVersion", 1);
        data.add("Statistic", jsonArray);
        network.Post(data);
    }
}
