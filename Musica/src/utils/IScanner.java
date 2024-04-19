package utils;

import java.util.Scanner;

public interface IScanner {

    static <T> T genericScanner(Class<T> classe, String msg) {
        Scanner scanner = new Scanner(System.in);
        T input = null;

        if (classe.equals(String.class)) {
            System.out.println(msg);
            String userInput = scanner.nextLine();
            input = classe.cast(userInput);
        } else if (classe.equals(Integer.class)) {
            System.out.println(msg);
            int userInput = Integer.parseInt(scanner.nextLine());
            input = classe.cast(userInput);
        } else if (classe.equals(Double.class)) {
            System.out.println(msg);
            double userInput = Double.parseDouble(scanner.nextLine());
            input = classe.cast(userInput);
        }
        return input;
    }
}
