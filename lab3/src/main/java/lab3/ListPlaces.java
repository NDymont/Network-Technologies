package lab3;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListPlaces {

    public static final String ANSI_GREEN = "\u001B[32m";

    private ArrayList<Place> placeArray = new ArrayList<>();

    ListPlaces(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = (JSONArray) jsonObject.get("hits");
        for (int i = 0; i < jsonArray.length(); ++i) {
            placeArray.add(new Place(jsonArray.getJSONObject(i)));
        }
    }

    public void printListPlaces() {
        for (int i = 0; i < placeArray.toArray().length; ++i) {
            System.out.println(ANSI_GREEN +(i + 1) + ") " + placeArray.get(i).getName());
        }
    }

    Place get(int index) {
        return placeArray.get(index);
    }
}
