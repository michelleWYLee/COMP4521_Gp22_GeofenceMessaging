package hk.ust.cse.comp4521.comp4521_gp22_geofencemessaging;

import android.util.Log;

import org.json.JSONObject;

public class ItemDataJsonParser {
    // parse a single item record
    final String TAG = "ItemDataJsonParser";

    public ItemData parse(JSONObject jsonObject){


        if(jsonObject == null){
            Log.v("app","jsonObject is null");
            return null;
        }


        String topic = jsonObject.optString("topic");
        Log.v(TAG, topic);
        String name = jsonObject.optString("name");
        Log.v(TAG, name);
        String content = jsonObject.optString("content");
        Log.v(TAG, content);
        if(topic != null && name != null && content != null){
            return new ItemData(topic,name,content);
        }

        return null;
    }
}
