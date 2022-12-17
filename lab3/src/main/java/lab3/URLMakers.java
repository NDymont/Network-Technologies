package lab3;

public class URLMakers {

    private static final String locationsKey = "1e3baf5f-d434-4619-aa95-2316520083fe";
    private static final String weatherKey = "bc1f3bee055a959a510c1cad22b42bab&units=metric";
    private static final String attractionsKey = "5ae2e3f221c38a28845f05b6dddea2c24f199baccf4d571c6fd3d74f";

    public static String makeWeatherURL(Point point) {
        return "http://api.openweathermap.org/data/2.5/weather?lat=" + point.getLatitude() + "&lon=" + point.longitude +
                "&appid=" + weatherKey;
    }

    public static String makeAttractionsURL(Point point) {
        return "http://api.opentripmap.com/0.1/ru/places/radius?lon=" + point.getLongitude() +
                "&lat=" + point.getLatitude() +
                "&radius=90&format=geojson&apikey=" + attractionsKey;
    }

    public static String makeAttractionsURL(Point point, int radius) {
        return "http://api.opentripmap.com/0.1/ru/places/radius?lon=" + point.getLongitude() +
                "&lat=" + point.getLatitude() +
                "&radius=" + radius + "&format=geojson&apikey=" + attractionsKey;
    }

    public static String makeAttractionURL(Attraction attraction) {
        return "http://api.opentripmap.com/0.1/ru/places/xid/" + attraction.getXid() +
                "?apikey=" + attractionsKey;
    }

    public static String makeLocationsURL(String name) {
        return "https://graphhopper.com/api/1/geocode?q=" + name +
                "&locale=de&key=" + locationsKey;
    }
}
