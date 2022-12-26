package lab3;

import org.json.JSONException;
import org.json.JSONObject;

public class Attraction {
    private String name;
    private String xid;

    public String getXid() {
        return xid;
    }

    public Attraction(JSONObject jsonObject) throws Exception {
        try {
            JSONObject properties = jsonObject.getJSONObject("properties");
            name = properties.getString("name");
            xid = properties.getString("xid");
        } catch (JSONException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public String getName() {
        return name;
    }
}