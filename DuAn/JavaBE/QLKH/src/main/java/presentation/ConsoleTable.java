package presentation;

import model.Course;
import model.Student;

import java.util.Scanner;

public class ConsoleTable {

    static Scanner sc = new Scanner(System.in);

    public static void printLine(int length) {
        System.out.println("-".repeat(length));
    }


    public static void studentHeader() {
        printLine(110);
        System.out.printf("| %-4s | %-25s | %-12s | %-25s | %-5s | %-12s | %-8s |%n",
                "ID", "Họ tên", "Ngày sinh", "Email", "GT", "Điện thoại", "Role");
        printLine(110);
    }

    public static void studentRow(Student s) {
        System.out.printf("| %-4d | %-25s | %-12s | %-25s | %-5s | %-12s | %-8s |%n",
                s.getId(),
                s.getName(),
                s.getDob(),
                s.getEmail(),
                s.getSex() ? "Nam" : "Nữ",
                s.getPhone(),
                s.getRole());
    }

    public static void courseHeader() {
        printLine(110);

        System.out.printf("| %-4s | %-30s | %-25s | %-10s | %-15s |%n",
                "ID", "Tên khóa học", "Giảng viên", "Thời lượng", "Ngày tạo");

        printLine(110);
    }

    public static void courseRow(Course c) {

        System.out.printf("| %-4d | %-30s | %-25s | %-10d | %-15s |%n",
                c.getId(),
                c.getName(),
                c.getInstruction(),
                c.getDuration(),
                c.getCreate_at());
    }


    public static void courseStatisticHeader() {

        printLine(60);

        System.out.printf("| %-4s | %-35s | %-12s |%n",
                "ID",
                "Tên khóa học",
                "Học viên");

        printLine(60);
    }

    public static void courseStatisticRow(int id,
                                          String name,
                                          Long totalStudent) {

        System.out.printf("| %-4d | %-35s | %-12d |%n",
                id,
                name,
                totalStudent);
    }


    public static void Footer() {
        printLine(110);
    }


    public static void pause() {
        System.out.print("\nNhấn Enter để tiếp tục...");
        sc.nextLine();
    }
}