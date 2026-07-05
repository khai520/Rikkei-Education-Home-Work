package presentation;

import business.impl.CourseService;
import business.impl.EnrollmentService;
import business.impl.StudentService;
import model.Course;
import model.Enrollment;
import model.Student;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ViewStudent {
    static Scanner sc = new Scanner(System.in);
    private static final CourseService courseService = new CourseService();
    private static final StudentService studentService = new StudentService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    public static int id ;
    public static void studentView(int studentId) {
        id = studentId;
        while (true) {

            System.out.println("\n========== MENU HỌC VIÊN ==========");
            System.out.println("1. Xem danh sách khóa học");
            System.out.println("2. Xem khóa học đã đăng ký");
            System.out.println("3. Hủy đăng ký khóa học");
            System.out.println("4. Đổi mật khẩu");
            System.out.println("0. Đăng xuất");

            System.out.print("Lựa chọn: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
                continue;
            }

            switch (choice) {

                case 1:
                    allcourseView();
                    courseView();
                    break;


                case 2:
                    myCourseView();
                    break;

                case 3:
                    cancelEnrollmentView();
                    break;

                case 4:
                    changePasswordView(id);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    private  static void allcourseView() {
        System.out.println("\n========== DANH SÁCH KHÓA HỌC ==========");

        ConsoleTable.studentHeader();
        courseService.getAllCourse().forEach(ConsoleTable::courseRow);
        ConsoleTable.Footer();
    }
    private static void courseView() {

        while (true) {
            System.out.println("1. Tìm kiếm khóa học");
            System.out.println("2. Đăng ký khóa học");
            System.out.println("0. Quay lại");

            System.out.print("Lựa chọn: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
                continue;
            }

            switch (choice) {
                case 1:
                    String keyword;

                    while (true) {
                        System.out.print("Nhập khóa học muốn tìm kiếm: ");
                        keyword = sc.nextLine().trim();

                        if (!keyword.isEmpty()) {
                            break;
                        }


                        System.out.println("Không được để trống");
                    }

                    final String search = keyword;

                    ConsoleTable.courseHeader();
                    courseService.getAllCourse()
                            .stream()
                            .filter(x -> x.getName().equalsIgnoreCase(search))
                            .forEach(ConsoleTable::courseRow);
                    ConsoleTable.Footer();
                    break;
                case 2:
                    registerCourseView();
                    break;
                case 0:
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    private static void registerCourseView() {

        while (true) {
            System.out.print("\nNhập ID khóa học (0 để quay lại): ");

            int courseId;

            try {
                courseId = Integer.parseInt(sc.nextLine());
                if (courseService.getCourseById(courseId) == null) {
                    System.out.println("Khóa học không tồn tại");
                }
            } catch (NumberFormatException e) {
                System.out.println("ID không hợp lệ!");
                continue;
            }

            if (courseId == 0)
                return;

            if(enrollmentService.addEnrollment(id, courseId)){
                System.out.println("Đăng ký thành công!");
            }
            else {
                System.out.println("Đăng ký không thành công!");
            }


        }
    }
    private static void myCourseView() {
        List<Course> courses = enrollmentService.getCourseByStudentId(id , Enrollment.Status.CONFIRM);
        while (true) {

            System.out.println("\n========== KHÓA HỌC ĐÃ ĐĂNG KÝ ==========");
            ConsoleTable.courseHeader();
            courses.forEach(ConsoleTable::courseRow);
            ConsoleTable.Footer();

            System.out.println("1. Sắp xếp theo tên tăng dần");
            System.out.println("2. Sắp xếp theo tên giảm dần");
            System.out.println("3. Sắp xếp theo ngày đăng ký tăng dần");
            System.out.println("4. Sắp xếp theo ngày đăng ký giảm dần");
            System.out.println("0. Quay lại");

            System.out.print("Lựa chọn: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
                continue;
            }

            switch (choice) {

                case 1:
                    ConsoleTable.studentHeader();
                    courses.stream().sorted(Comparator.comparing(Course::getId)).forEach(ConsoleTable::courseRow);
                    ConsoleTable.Footer();
                    break;

                case 2:
                    ConsoleTable.studentHeader();
                    courses.stream().sorted(Comparator.comparing(Course::getId).reversed()).forEach(ConsoleTable::courseRow);
                    ConsoleTable.Footer();
                    break;

                case 3:
                    ConsoleTable.studentHeader();
                    courses.stream().sorted(Comparator.comparing(Course::getCreate_at)).forEach(ConsoleTable::courseRow);
                    ConsoleTable.Footer();
                    break;

                case 4:
                    ConsoleTable.studentHeader();
                    courses.stream().sorted(Comparator.comparing(Course::getCreate_at).reversed()).forEach(ConsoleTable::courseRow);
                    ConsoleTable.Footer();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    private static void cancelEnrollmentView() {

        while (true) {

            System.out.println("\n========== HỦY ĐĂNG KÝ ==========");
            ConsoleTable.courseHeader();
            enrollmentService.getCourseByStudentId(id , Enrollment.Status.WAITING).forEach(ConsoleTable::courseRow);
            ConsoleTable.Footer();
            System.out.print("Nhập ID khóa học muốn hủy (0 để quay lại): ");

            int id;

            try {
                id = Integer.parseInt(sc.nextLine());
                if (enrollmentService.checkId(id , Enrollment.Status.WAITING)) {
                    System.out.println("Id không tồn tại");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID không hợp lệ!");
                continue;
            }

            if (id == 0)
                return;

            System.out.print("Bạn có chắc chắn? (1.Có / 0.Không): ");

            int confirm;

            try {
                confirm = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }

            if (confirm == 1) {

                if (enrollmentService.updateStatus(id , Enrollment.Status.CANCEL)) {
                    System.out.println("Đã từ chối đăng ký.");
                } else {
                    System.out.println("Thao tác thất bại.");
                }

                System.out.println("Hủy thành công.");
            }
        }
    }

    private static void changePasswordView(int id) {

        System.out.println("\n========== ĐỔI MẬT KHẨU ==========");

        Student student = studentService.getStudentById(id, null);

        if (student == null) {
            System.out.println("Không tìm thấy tài khoản!");
            return;
        }

        String oldPassword;
        while (true) {
            System.out.print("Mật khẩu cũ: ");
            oldPassword = sc.nextLine();

            if (oldPassword.isBlank()) {
                System.out.println("Mật khẩu không được để trống!");
                continue;
            }

            if (!studentService.ktmkc(id, oldPassword)) {
                System.out.println("Mật khẩu cũ không đúng!");
                continue;
            }

            break;
        }

        String newPassword;
        while (true) {
            System.out.print("Mật khẩu mới: ");
            newPassword = sc.nextLine();

            if (newPassword.isBlank()) {
                System.out.println("Mật khẩu không được để trống!");
                continue;
            }

            if (oldPassword.equals(newPassword)) {
                System.out.println("Mật khẩu mới không được trùng mật khẩu cũ!");
                continue;
            }

            break;
        }

        while (true) {
            System.out.print("Nhập lại mật khẩu mới: ");
            String confirmPassword = sc.nextLine();

            if (confirmPassword.isBlank()) {
                System.out.println("Không được để trống!");
                continue;
            }

            if (!confirmPassword.equals(newPassword)) {
                System.out.println("Xác nhận mật khẩu không khớp!");
                continue;
            }

            break;
        }

        if (studentService.updatePassword(id, newPassword)) {
            System.out.println("Cập nhật mật khẩu thành công!");
        } else {
            System.out.println("Cập nhật mật khẩu thất bại!");
        }
    }
}
