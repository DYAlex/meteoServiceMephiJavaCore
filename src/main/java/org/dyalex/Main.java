package org.dyalex;

import org.dyalex.util.NumberParser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String apiKey = "ВСТАВЬТЕ_ВАШ_КЛЮЧ_YANDEX_API_ЗДЕСЬ";
        WeatherService weatherService = new WeatherService(apiKey);

        if (args.length >= 3) {
            try {
                Double lat = NumberParser.parseDouble(args[0]);
                Double lon = NumberParser.parseDouble(args[1]);
                Integer limit = NumberParser.parseInt(args[2]);

                if (lat != null && lon != null) {
                    String weatherResponse = weatherService.getWeatherByCoordinates(lat, lon);
                    System.out.println("Ответ сервиса (JSON): " + weatherResponse);

                    double temp = weatherService.getCurrentTemperature(weatherResponse);
                    System.out.println("Температура сейчас: " + temp + "°C");

                    double averageTemp = weatherService.getAverageTemperature(lat, lon, limit);
                    System.out.println("Средняя температура на " + limit + " дней: " + averageTemp + "°C");
                }

            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        } else {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Введите широту (lat): ");
            Double lat = NumberParser.parseDouble(scanner.nextLine());

            System.out.print("Введите долготу (lon): ");
            Double lon = NumberParser.parseDouble(scanner.nextLine());

            System.out.print("Введите количество дней для расчета средней температуры (limit): ");
            Integer limit = NumberParser.parseInt(scanner.nextLine());

            if (lat != null && lon != null) {
                String weatherResponse = weatherService.getWeatherByCoordinates(lat, lon);
                System.out.println("Ответ сервиса (JSON): " + weatherResponse);

                double temp = weatherService.getCurrentTemperature(weatherResponse);
                System.out.println("Температура сейчас: " + temp + "°C");

                double averageTemp = weatherService.getAverageTemperature(lat, lon, limit);
                System.out.println("Средняя температура на " + limit + " дней: " + averageTemp + "°C");
            } else {
                System.out.println("Произошла неизвестная ошибка. Проверьте правильность введенных данных");
            }

            scanner.close();
        }
    }
}