package lab3;

import org.json.JSONObject;

public class Place {
    String name;
    Point point;

    Place(JSONObject jsonObject) {
        name = jsonObject.getString("name");
        point = new Point((JSONObject) jsonObject.get("point"));
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", point=" + point +
                '}';
    }

    public String getName() {
        return name;
    }
}
