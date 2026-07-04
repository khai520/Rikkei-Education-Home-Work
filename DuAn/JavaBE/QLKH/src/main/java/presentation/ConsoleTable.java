package presentation;

public class ConsoleTable {

    public static void printLine(int length) {
        System.out.println("-".repeat(length));
    }

    public static void printTitle(String title, int length) {
        printLine(length);
        System.out.printf("|%" + ((length - 2 + title.length()) / 2) + "s%" +
                ((length - 2 - title.length()) / 2) + "s|%n", title, "");
        printLine(length);
    }

    public static void pause() {
        System.out.println();
        System.out.println("0. Quay lại");
    }
}