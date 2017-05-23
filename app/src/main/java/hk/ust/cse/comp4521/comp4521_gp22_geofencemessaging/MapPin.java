package hk.ust.cse.comp4521.comp4521_gp22_geofencemessaging;


import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MapPin implements ClusterItem{
    public String topic;
    public LatLng mPosition;

    public MapPin(LatLng position, String topic){
        this.topic = topic;
        mPosition = position;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }


}
