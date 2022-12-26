package lab3;

import org.json.JSONException;
import org.json.JSONObject;

public class DescriptionAttraction {

    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[36m";

    private String description;
    private String name;

    DescriptionAttraction(String jsonString) {

        JSONObject jsonObject = new JSONObject(jsonString);
        try {
            name = jsonObject.getString("name");
        } catch (JSONException ex) {
            System.out.println(jsonString);
            name = null;
            return;
        }
        try {
            description = jsonObject.getJSONObject("wikipedia_extracts").getString("text");
        } catch (JSONException ex) {
            description = "No description found";
        }

    }

    public void PrintDescription() {
        if (name != null) {
            int sizeBuf = 200;
            String[] arr = description.split(" ");
            int countSymbolsInLine = 0;

            StringBuilder textToPrint = new StringBuilder();
            for (String it : arr) {
                int wordLength = it.length();
                if (countSymbolsInLine + wordLength < sizeBuf) {
                    textToPrint.append(it).append(" ");
                    countSymbolsInLine += wordLength + 1;
                } else {
                    textToPrint.append("\n").append(it).append(" ");
                    countSymbolsInLine = wordLength + 1;
                }
            }
            System.out.println(ANSI_BLUE + "\t" + name + "\n" + ANSI_RED + textToPrint);
        }
    }
}
