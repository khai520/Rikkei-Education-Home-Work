package presentation;

import model.Course;
import model.Student;
import model.dto.EnrollmentDTO;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

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
                c.getInstructor(),
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
    public static void enrollmentHeader() {

        printLine(110);

        System.out.printf("| %-4s | %-25s | %-25s | %-20s | %-10s |%n",
                "ID",
                "Học viên",
                "Khóa học",
                "Ngày đăng ký",
                "Trạng thái");

        printLine(110);
    }

    public static void enrollmentRow(EnrollmentDTO e) {

        System.out.printf("| %-4d | %-25s | %-25s | %-20s | %-10s |%n",
                e.id,
                e.studentName,
                e.courseName,
                e.registered_at,
                e.status);
    }


    public static void Footer() {
        printLine(110);
    }


    public static void pause() {
        System.out.print("\nNhấn Enter để tiếp tục...");
        sc.nextLine();
    }
    public static <T> void paginate(
            List<T> list,
            int pageSize,
            Consumer<T> printer,
            Runnable header,
            Runnable footer) {

        if (list == null || list.isEmpty()) {
            System.out.println("Không có dữ liệu!");
            pause();
            return;
        }

        int currentPage = 1;
        int totalPage = (int) Math.ceil((double) list.size() / pageSize);

        if (totalPage == 1) {
            header.run();
            list.forEach(printer);
            footer.run();
            return;
        }

        while (true) {

            int from = (currentPage - 1) * pageSize;
            int to = Math.min(from + pageSize, list.size());

            header.run();
            list.subList(from, to).forEach(printer);
            footer.run();

            System.out.printf("Trang %d/%d%n", currentPage, totalPage);
            System.out.println("[P] Trang trước");
            System.out.println("[N] Trang sau");
            System.out.println("[0] Quay lại");
            System.out.print("Chọn: ");

            String input = sc.nextLine().trim().toUpperCase();

            switch (input) {
                case "P":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("Đây là trang đầu!");
                        pause();
                    }
                    break;

                case "N":
                    if (currentPage < totalPage) {
                        currentPage++;
                    } else {
                        System.out.println("Đây là trang cuối!");
                        pause();
                    }
                    break;

                case "0":
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    pause();
            }
        }
    }
}