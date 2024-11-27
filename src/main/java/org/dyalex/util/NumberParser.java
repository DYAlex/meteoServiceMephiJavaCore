package org.dyalex.util;

public class NumberParser {
    public static Double parseDouble(String s) {
        if (s != null && !s.isEmpty()) {
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.printf("Ошибка при парсинге %s: %s\n", s, e.getMessage());
                return null;
            }
        }
        return null;
    }

    public static Integer parseInt(String s) {
        if (s != null && !s.isEmpty()) {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.printf("Ошибка при парсинге %s: %s\n", s, e.getMessage());
                return null;
            }
        }
        return null;
    }
}
