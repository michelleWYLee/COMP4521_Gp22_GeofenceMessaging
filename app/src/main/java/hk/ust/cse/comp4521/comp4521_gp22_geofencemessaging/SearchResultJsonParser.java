package hk.ust.cse.comp4521.comp4521_gp22_geofencemessaging;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultJsonParser {
    private ItemDataJsonParser itemDataParser = new ItemDataJsonParser();

    //parse the root result Json object into a list of results

    public List<ItemData> parseResults(JSONObject jsonObject){

        if(jsonObject == null){
            return null;
        }

        List<ItemData> results = new ArrayList<>();

        JSONArray hits = jsonObject.optJSONArray("hits");
        if(hits == null){
            return null;
        }

        for(int i =0; i<hits.length();i++){
            JSONObject hit = hits.optJSONObject(i);
            if(hit == null) continue;


            ItemData itemData = itemDataParser.parse(hit);
            results.add(itemData);
            Log.v("app","successfully add the data");

        }


        return results;
    }

}
