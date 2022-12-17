package lab3;

import org.json.JSONObject;

public class Weather {
    public static final String ANSI_PURPLE = "\u001B[35m";

    private double temperatureReal;
    private double temperatureFeels;
    private double pressure;
    private double windSpeed;
    private double humidity;
    private double cloudiness;
    private String condition;

    Weather(String jsonString)
    {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject mainInfo = jsonObject.getJSONObject("main");
        temperatureReal = mainInfo.getDouble("temp");
        temperatureFeels = mainInfo.getDouble("feels_like");
        pressure = mainInfo.getDouble("pressure");
        humidity = mainInfo.getDouble("humidity");
        condition = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
        windSpeed = jsonObject.getJSONObject("wind").getDouble("speed");
        cloudiness = jsonObject.getJSONObject("clouds").getDouble("all");
    }

    @Override
    public String toString() {
        return ANSI_PURPLE + "\tWEATHER :\n" +
                "temperatureReal : " + temperatureReal +
                "\ntemperatureFeels : " + temperatureFeels +
                "\npressure : " + pressure +
                "\nwindSpeed : " + windSpeed +
                "\nhumidity : " + humidity +
                "\ncloudiness : " + cloudiness +
                "\ncondition : '" + condition + '\'';
    }
}
