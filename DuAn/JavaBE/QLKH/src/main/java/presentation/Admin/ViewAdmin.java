package presentation.Admin;

import java.util.Scanner;

public class ViewAdmin {
    private static final Scanner sc = new Scanner(System.in);

    public static void adminView() {

        while (true) {
            System.out.println("\n==================================================");
            System.out.println("          HỆ THỐNG QUẢN LÝ KHÓA HỌC");
            System.out.println("==================================================");
            System.out.println("1. Quản lý khóa học");
            System.out.println("2. Quản lý học viên");
            System.out.println("3. Quản lý đăng ký khóa học");
            System.out.println("4. Thống kê");
            System.out.println("0. Đăng xuất");
            System.out.println("==================================================");
            System.out.print("Nhập lựa chọn: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    CourseView.courseView();
                    break;

                case 2:
                    StudentView.studentView();
                    break;

                case 3:
                    EnrollmentView.enrollmentView();
                    break;

                case 4:
                    StatisticView.statisticView();
                    break;

                case 0:
                    System.out.println("Đăng xuất thành công!");
                    return;

                default:
                    System.out.println("Vui lòng nhập từ 0 - 4.");
            }
        }
    }
}
