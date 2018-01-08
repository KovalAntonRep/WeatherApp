package service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.City;

public class CityWeatherParser {
    private JsonParser parser = new JsonParser();

    /**
     * Converting from hPa to MOM requires multiplying the number by 0.750061
     * To make the number more convenient to be read it is rounded to the form XXX.XX
     */
    private double convertPressureToCGS(double hPaPressure) {
        return Math.round(hPaPressure * 75.0061) / 100;
    }

    public City convertJsonToCity(String jsonString) {
        JsonObject allDataJsonObject = parser.parse(jsonString).getAsJsonObject();
        JsonObject mainDataJsonObject = allDataJsonObject.getAsJsonObject("main");
        JsonObject windDataJsonObject = allDataJsonObject.getAsJsonObject("wind");

        String cityName = allDataJsonObject.get("name").getAsString();
        double temperature = mainDataJsonObject.get("temp").getAsDouble();
        double pressure = convertPressureToCGS(mainDataJsonObject.get("pressure").getAsDouble());
        double humidity = mainDataJsonObject.get("humidity").getAsDouble();
        double windSpeed = windDataJsonObject.get("speed").getAsDouble();
        double windDirection = windDataJsonObject.get("deg").getAsDouble();

        City result = new City(cityName);
        result.setData(temperature, humidity, pressure, windSpeed, windDirection);

        return result;
    }
}
