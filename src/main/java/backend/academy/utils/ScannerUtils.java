package backend.academy.utils;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.function.Function;

@SuppressWarnings("RegexpSinglelineJava")
public class ScannerUtils {

    private static final Scanner SCANNER = new Scanner(System.in, StandardCharsets.UTF_8);

    public static <T> T getInput(String message, Function<String, T> parser) {
        T value;
        while (true) {
            System.out.printf(message);
            String input = SCANNER.nextLine();
            try {
                value = parser.apply(input);
                break;
            } catch (Exception e) {
                System.out.println("Ошибка: введено некорректное значение. Попробуйте снова.");
            }
        }
        return value;
    }

    public static void closeScanner() {
        SCANNER.close();
    }

    private ScannerUtils() {
    }
}
