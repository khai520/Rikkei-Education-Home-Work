package presentation;

import business.impl.CourseService;
import business.impl.EnrollmentService;
import business.impl.StudentService;
import model.Course;
import model.Enrollment;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ViewStudent {
    static Scanner sc = new Scanner(System.in);
    private static final CourseService courseService = new CourseService();
    private static final StudentService studentService = new StudentService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    private static int id;
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
                    break;


                case 2:
                    myCourseView();
                    break;

                case 3:
                    cancelEnrollmentView();
                    break;

                case 4:
                    changePasswordView();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
            sc = new Scanner(System.in);
        }
    }
    private static void allcourseView() {

        List<Integer> registeredCourseIds = enrollmentService
                .getAll(Enrollment.Status.CONFIRM)
                .stream()
                .filter(x -> x.getStudent_id() == id)
                .map(Enrollment::getCourse_id)
                .toList();

        Map<Integer, Long> totalStudent = enrollmentService
                .getAll(Enrollment.Status.CONFIRM)
                .stream()
                .collect(Collectors.groupingBy(
                        Enrollment::getCourse_id,
                        Collectors.counting()
                ));

        System.out.println("\n========================================= KHÓA HỌC ĐỀ XUẤT ===================================================");

        List<Course> recommend = courseService.getAllCourse()
                .stream()
                .filter(c -> !registeredCourseIds.contains(c.getId()))
                .sorted((a, b) -> Long.compare(
                        totalStudent.getOrDefault(b.getId(), 0L),
                        totalStudent.getOrDefault(a.getId(), 0L)))
                .limit(5)
                .toList();

        if (recommend.isEmpty()) {
            System.out.println("Bạn đã đăng ký tất cả khóa học!");
        } else {
            ConsoleTable.courseHeader();
            recommend.forEach(ConsoleTable::courseRow);
            ConsoleTable.Footer();
        }

        System.out.println("\n========== DANH SÁCH KHÓA HỌC ==========");

        ConsoleTable.paginate(
                courseService.getAllCourse(),
                5,
                ConsoleTable::courseRow,
                ConsoleTable::courseHeader,
                ConsoleTable::Footer
        );

        if (!courseService.getAllCourse().isEmpty()) {
            courseView();
        }
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

                    final String search = keyword.toLowerCase();
                    ConsoleTable.paginate(
                            courseService.getAllCourse()
                                    .stream()
                                    .filter(x -> x.getName().toLowerCase().contains(search)).toList(),
                            5,
                            ConsoleTable::courseRow,
                            ConsoleTable::courseHeader,
                            ConsoleTable::Footer
                    );
                    break;
                case 2:
                    registerCourseView();
                    ConsoleTable.paginate(
                            courseService.getAllCourse(),
                            5,
                            ConsoleTable::courseRow,
                            ConsoleTable::courseHeader,
                            ConsoleTable::Footer
                    );
                    break;
                case 0:
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    private static void registerCourseView() {
        int courseId;
        while (true) {
            System.out.print("Nhập ID khóa học (0 để quay lại): ");
            try {
                courseId = Integer.parseInt(sc.nextLine());
                if (courseId == 0) return;
                if (courseService.getCourseById(courseId) == null) {
                    System.out.println("Khóa học không tồn tại");
                    continue;
                }
                if (enrollmentService.checkIC(id , courseId , Enrollment.Status.WAITING) || enrollmentService.checkIC(id , courseId , Enrollment.Status.CONFIRM)){
                    System.out.println("Khóa học này đã đăng ký");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID không hợp lệ!");
                continue;
            }
            break;
        }
        if (enrollmentService.checkIC(id , courseId , Enrollment.Status.CANCEL)) {
            System.out.print("Khóa học đã bị hủy bạn có muốn đăng ký lại 1.Y/0.N: ");
            int choice;
            while (true) {
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Vui lòng nhập số!");
                }
            }
            if (choice == 1) {
                final int finalCourseId = courseId;

                Enrollment enrollment = enrollmentService.getAll(Enrollment.Status.CANCEL)
                        .stream()
                        .filter(x -> x.getStudent_id() == id
                                && x.getCourse_id() == finalCourseId)
                        .findFirst()
                        .orElse(null);

                assert enrollment != null;
                if (enrollmentService.updateStatus(enrollment.getId(), Enrollment.Status.WAITING)){
                    System.out.println("Đăng ký lại thành công");
                }
                else
                {
                    System.out.println("Đăng ký lại thất bại");
                }

                return;
            }
        }
        if(enrollmentService.addEnrollment(id, courseId)){
            System.out.println("Đăng ký thành công!");

        }
        else {
            System.out.println("Đăng ký không thành công!");
        }
    }
    private static void myCourseView() {
        List<Course> courses = enrollmentService.getCourseByStudentId(id , Enrollment.Status.CONFIRM);
        while (true) {
            System.out.println("========== KHÓA HỌC ĐÃ ĐĂNG KÝ ==========");
            ConsoleTable.paginate(
                    courses,
                    5,
                    ConsoleTable::courseRow,
                    ConsoleTable::courseHeader,
                    ConsoleTable::Footer
            );
            if (courses.isEmpty()) {
                break;
            }
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
                    ConsoleTable.paginate(
                            courses.stream().sorted(Comparator.comparing(Course::getName)).toList(),
                            5,
                            ConsoleTable::courseRow,
                            ConsoleTable::courseHeader,
                            ConsoleTable::Footer
                    );
                    break;

                case 2:
                    ConsoleTable.paginate(
                            courses.stream().sorted(Comparator.comparing(Course::getName).reversed()).toList(),
                            5,
                            ConsoleTable::courseRow,
                            ConsoleTable::courseHeader,
                            ConsoleTable::Footer
                    );
                    break;

                case 3:
                    ConsoleTable.paginate(
                            courses.stream().sorted(Comparator.comparing(Course::getCreate_at)).toList(),
                            5,
                            ConsoleTable::courseRow,
                            ConsoleTable::courseHeader,
                            ConsoleTable::Footer
                    );
                    break;

                case 4:
                    ConsoleTable.paginate(
                            courses.stream().sorted(Comparator.comparing(Course::getCreate_at).reversed()).toList(),
                            5,
                            ConsoleTable::courseRow,
                            ConsoleTable::courseHeader,
                            ConsoleTable::Footer
                    );
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
            ConsoleTable.paginate(
                    enrollmentService.getCourseByStudentId(id , Enrollment.Status.WAITING),
                    5,
                    ConsoleTable::courseRow,
                    ConsoleTable::courseHeader,
                    ConsoleTable::Footer
            );
            if (enrollmentService.getCourseByStudentId(id , Enrollment.Status.WAITING ) == null){
                return;
            }
            System.out.print("Nhập ID khóa học muốn hủy (0 để quay lại): ");

            int idkh;

            try {
                idkh = Integer.parseInt(sc.nextLine());
                if (idkh == 0)
                    return;
                if (!enrollmentService.checkIC(id , idkh , Enrollment.Status.WAITING)) {
                    System.out.println("Id không tồn tại");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID không hợp lệ!");
                continue;
            }

            System.out.print("Bạn có chắc chắn? (1.Có / 0.Không): ");

            int confirm;

            try {
                confirm = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }

            if (confirm == 1) {
                final int finalCourseId = idkh;
                Enrollment enrollment = enrollmentService.getAll(Enrollment.Status.WAITING)
                        .stream()
                        .filter(x -> x.getStudent_id() == id
                                && x.getCourse_id() == finalCourseId)
                        .findFirst()
                        .orElse(null);
                assert enrollment != null;
                if (enrollmentService.updateStatus(enrollment.getId() , Enrollment.Status.CANCEL)) {
                    System.out.println("Hủy thành công.");
                } else {
                    System.out.println("Thao tác thất bại.");
                }
            }
        }
    }

    private static void changePasswordView() {

        System.out.println("\n========== ĐỔI MẬT KHẨU ==========");

        String oldPassword;
        while (true) {
            System.out.print("Mật khẩu cũ: ");
            oldPassword = sc.nextLine();

            if (oldPassword.isBlank()) {
                System.out.println("Mật khẩu không được để trống!");
                continue;
            }

            if (studentService.ktmkc(id, oldPassword)) {
                break;
            }
            System.out.println("Mật khẩu cũ không đúng!");
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
