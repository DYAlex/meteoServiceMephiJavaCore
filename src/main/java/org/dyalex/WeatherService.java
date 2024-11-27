package org.dyalex;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class WeatherService {
    private static final String BASE_URL = "https://api.weather.yandex.ru/v2/forecast";
    private final String YANDEX_KEY;
    private final ObjectMapper mapper = new ObjectMapper();

    public WeatherService(String apiKey) {
        this.YANDEX_KEY = apiKey;
    }

    public String getWeatherByCoordinates(double lat, double lon) {
        String uriString = String.format("%s?lat=%s&lon=%s", BASE_URL, lat, lon);

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uriString))
                    .header("X-Yandex-API-Key", YANDEX_KEY)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Object jsonString = mapper.readValue(response.body(), Object.class);
            return mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonString);
        } catch (Exception e) {
            System.out.printf("Произошла ошибка при получении данных: %s\n", e.getMessage());
        }
        return null;
    }

    public double getCurrentTemperature(String jsonResponse) {
        try {
            JsonNode rootNode = mapper.readTree(jsonResponse);
            return rootNode.findValue("fact").findValue("temp").asDouble();
        } catch (Exception e) {
            System.out.println("Произошла ошибка при получении текущей температуры");
            System.out.println(e.getMessage());
        }
        return Integer.MIN_VALUE;
    }

    public double getAverageTemperature(double lat, double lon, Integer limit) {
        String weatherResponse = getWeatherByCoordinates(lat, lon);
        try {
            JsonNode rootNode = mapper.readTree(weatherResponse);
            List<Double> values = rootNode.findValue("forecasts").findValues("temp_avg")
                    .stream()
                    .limit(limit != null && limit > 0 && limit < 8 ? limit : 5)
                    .map(JsonNode::asDouble)
                    .toList();
            double sumTemp = values.stream()
                    .reduce(0.0, Double::sum);
            return sumTemp / values.size();
        } catch (Exception e) {
            System.out.println("Произошла ошибка при вычислении средней температуры");
            System.out.println(e.getMessage());
        }

        return Integer.MIN_VALUE;
    }
}
