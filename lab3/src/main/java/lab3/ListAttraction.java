package lab3;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ListAttraction implements Iterable<Attraction> {
    private ArrayList<Attraction> placeArray = new ArrayList<>();
    public static final String ANSI_GREEN = "\u001B[32m";

    ListAttraction(String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = (JSONArray) jsonObject.get("features");
        for (int i = 0; i < jsonArray.length(); ++i) {
            try {
                placeArray.add(new Attraction(jsonArray.getJSONObject(i)));
            } catch (Exception ex){
//                System.out.println(ex.getMessage());
            }
        }

    }

    public Iterator<Attraction> iterator() {
        return placeArray.iterator();
    }

    public void printListPlaces() {
        for (int i = 0; i < placeArray.toArray().length; ++i) {
            System.out.println(ANSI_GREEN + (i + 1) + ") " +
                    placeArray.get(i).getName());
        }
    }

    int getSize() {
        return placeArray.size();
    }
}


