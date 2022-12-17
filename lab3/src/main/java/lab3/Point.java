package lab3;

import com.sun.jdi.request.ClassPrepareRequest;
import org.json.JSONObject;

public class Point {
    String latitude;
    String longitude;

    public Point(JSONObject json) {
        latitude = json.get("lat").toString();
        longitude = json.get("lng").toString();
    }

    @Override
    public String toString() {
        return "Point{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

}
