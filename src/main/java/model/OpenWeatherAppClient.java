package model;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import service.CityWeatherParser;
import service.DataSerializer;

import javax.management.RuntimeErrorException;
import java.io.IOException;

public class OpenWeatherAppClient {
    private CloseableHttpClient httpClient;
    private String appId = "d0a08cbe595f3ce972699377d10c1f15";

    public OpenWeatherAppClient() {
        try {
            httpClient = HttpClients.createDefault();
        } catch (Exception e) {
            throw new RuntimeErrorException(null);
        }
    }

    public City getInformationAboutCity(String cityId) {
        HttpGet httpGet = new HttpGet("http://api.openweathermap.org/data/2.5/weather?id=" + cityId
        + "&APPID=" + appId + "&units=metric");

        String responseJson = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            responseJson = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeErrorException(null);
        }

        DataSerializer.serialize(responseJson, cityId + ".txt");

        return new CityWeatherParser().convertJsonToCity(responseJson);
    }
}
