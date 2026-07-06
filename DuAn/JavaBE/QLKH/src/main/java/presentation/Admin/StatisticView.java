package presentation.Admin;
import business.impl.CourseService;
import business.impl.EnrollmentService;
import business.impl.StudentService;
import model.Enrollment;
import model.Student;
import presentation.ConsoleTable;

import java.util.Map;
import java.util.stream.Collectors;

import java.util.Scanner;

public class StatisticView {
    private static Scanner sc = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    public static void statisticView() {

        while (true) {
            System.out.println("========== THỐNG KÊ ==========");
            System.out.println("1. Thống kê tổng số khóa học và học viên");
            System.out.println("2. Thống kê tổng số học viên theo từng khóa");
            System.out.println("3. Top 5 khóa học đông học viên nhất");
            System.out.println("4. Liệt kê khóa học có trên 10 học viên");
            System.out.println("0. Quay lại");
            System.out.print("Nhập chương trình: ");
            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
                continue;
            }

            switch (choice) {

                case 1:
                    totalStatistic();
                    break;

                case 2:
                    studentStatistic();
                    break;

                case 3:
                    top5Course();
                    break;

                case 4:
                    over10Student();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
            sc = new Scanner(System.in);
        }
    }
    private static void totalStatistic() {

        System.out.println("\n========== TỔNG QUAN ==========");

        System.out.println("Tổng số khóa học : "
                + courseService.getAllCourse().size());

        System.out.println("Tổng số học viên : "
                + studentService.getAllStudents()
                .stream()
                .filter(x -> x.getRole() == Student.Role.STUDENT)
                .count());

        ConsoleTable.pause();
    }
    private static void studentStatistic() {

        Map<String, Long> totalStudent = enrollmentService.getAllDTO(Enrollment.Status.CONFIRM)
                .stream()
                .collect(Collectors.groupingBy(
                        x -> x.courseName,
                        Collectors.counting()
                ));

        System.out.println("======================= THỐNG KÊ ===========================");

        ConsoleTable.courseStatisticHeader();

        totalStudent.forEach((courseName, total) ->
                ConsoleTable.courseStatisticRow(
                        0,
                        courseName,
                        total
                ));

        ConsoleTable.printLine(60);

        ConsoleTable.pause();
    }
    private static void top5Course() {

        Map<String, Long> totalStudent = enrollmentService.getAllDTO(Enrollment.Status.CONFIRM)
                .stream()
                .collect(Collectors.groupingBy(
                        x -> x.courseName,
                        Collectors.counting()
                ));

        System.out.println("========================= TOP 5 ==============================");

        ConsoleTable.courseStatisticHeader();

        courseService.getAllCourse()
                .stream()
                .sorted((a, b) -> Long.compare(
                        totalStudent.getOrDefault(b.getName(), 0L),
                        totalStudent.getOrDefault(a.getName(), 0L)
                ))
                .limit(5)
                .forEach(course ->
                        ConsoleTable.courseStatisticRow(
                                course.getId(),
                                course.getName(),
                                totalStudent.getOrDefault(course.getName(), 0L)
                        ));

        ConsoleTable.printLine(60);
        ConsoleTable.pause();
    }
    private static void over10Student() {
        System.out.println("==================== CÓ TRÊN 10 HỌC VIÊN =====================");

        Map<String, Long> totalStudent = enrollmentService.getAllDTO(Enrollment.Status.CONFIRM)
                .stream()
                .collect(Collectors.groupingBy(
                        x -> x.courseName,
                        Collectors.counting()
                ));

        ConsoleTable.courseStatisticHeader();

        courseService.getAllCourse().stream()
                .filter(course -> totalStudent.getOrDefault(course.getName(), 0L) >= 10)
                .forEach(course ->
                        ConsoleTable.courseStatisticRow(
                                course.getId(),
                                course.getName(),
                                totalStudent.getOrDefault(course.getName(), 0L)
                        ));

        ConsoleTable.printLine(60);

        ConsoleTable.pause();
    }
}
