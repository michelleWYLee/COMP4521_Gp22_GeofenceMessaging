package hk.ust.cse.comp4521.comp4521_gp22_geofencemessaging;

import java.util.ArrayList;
import java.util.List;

//Data for RecyclerView
public class ItemData {
    private String topic,name,content;

    public ItemData(){}

    public ItemData(String topic,String name, String content){
        this.topic = topic;
        this.name = name;
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
