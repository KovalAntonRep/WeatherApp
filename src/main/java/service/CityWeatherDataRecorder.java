package service;

import model.City;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CityWeatherDataRecorder {
    private static String convertToPropertyString(City city) {
        String result = "city.name = \"" + city.getName() + "\"\nmain.temperature = " + city.getTemperature()
                + "\nmain.humidity = " + city.getHumidity() + "\nmain.pressure = " + city.getPressure()
                + "\nwind.speed = " + city.getWindSpeed() + "\nwind.direction = " + city.getWindDirection();

        return result;
    }

    public static void recordCity(City city, String destination) throws IOException {
        File outputFile = new File(destination + city.getName().toLowerCase() + ".properties");

        FileWriter writer = new FileWriter(outputFile);
        writer.write(convertToPropertyString(city));

        writer.close();
    }
}
