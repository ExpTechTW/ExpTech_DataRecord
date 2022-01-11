package function;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import core.logger;
import core.network;

import static main.main.BlockArray;
import static main.whes1015.DATA;

public class dataupdate {
    public static void DataUpdate(){
        if(BlockArray.size()!=0){
            logger.log("TRACE","DataUpdate","null");
            JsonElement Data= JsonParser.parseString(DATA.toString());
            JsonObject data=Data.getAsJsonObject();
            data.addProperty("Type","BlockValue");
            data.addProperty("FormatVersion", 2);
            data.add("BlockValue",BlockArray);
            BlockArray=new JsonArray();
            network.Post(data);
        }
    }
}
