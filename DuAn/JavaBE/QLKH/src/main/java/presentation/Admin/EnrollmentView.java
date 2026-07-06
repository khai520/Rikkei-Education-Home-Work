package presentation.Admin;

import business.impl.CourseService;
import business.impl.EnrollmentService;

import model.Course;
import model.Enrollment;

import presentation.ConsoleTable;

import java.util.List;
import java.util.Scanner;

public class EnrollmentView {
    static Scanner sc = new Scanner(System.in);
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    public static void enrollmentView() {
        while (true) {
            System.out.println("=== QUẢN LÝ ĐĂNG KÝ ===");
            System.out.println("1. Hiển thị danh sách các khóa học");
            System.out.println("2. Duyệt sinh viên đăng ký");
            System.out.println("0. Quay lại");

            System.out.print("Nhập lựa chọn: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
                continue;
            }

            switch (choice) {
                case 1:
                    showAllCourse();
                    break;
                case 2:
                    approveEnrollmentView();
                    break;
                case 0:
                    return;

                default:
                    System.out.println("Đang phát triển...");
            }
            sc = new Scanner(System.in);
        }
    }
    private static void showAllCourse() {
        while (true) {
            System.out.println("\n===== DANH SÁCH KHÓA HỌC =====");
            List<Course> courses = courseService.getAllCourse();
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
            System.out.print("Xem danh sách sinh viên theo khóa học 1.Y / 0.N: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
                continue;
            }
            if (choice == 1) {
                listSinhVienTheoHoc();
            }
            else
            {
                break;
            }
        }
        ConsoleTable.pause();
    }
    private static int id ;
    private static void  listSinhVienTheoHoc() {
        int idkh;
        Course course;
        while (id == 0) {
            System.out.print("Nhập id khóa học muốn thao tác: ");
            idkh = Integer.parseInt(sc.nextLine()) ;
            if (idkh == 0){
                return;
            }
            while (true) {
                course = courseService.getCourseById(idkh);
                if (course != null) {
                    id = course.getId();
                    break;
                }
                System.out.println("Khóa học không tồn tại");
                idkh = Integer.parseInt(sc.nextLine()) ;
            }

        }
        while (true) {
            try {
                System.out.println("1. Hiển thị các sinh viên theo học: ");
                System.out.println("0. Quay lại");
                int choice ;
                try {
                    System.out.print("Nhập lựa chọn: ");
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Vui lòng nhập số!");
                    continue;
                }
                switch (choice) {
                    case 1:
                        ConsoleTable.paginate(
                                enrollmentService.getStudentByCourseId(id , Enrollment.Status.CONFIRM),
                                5,
                                ConsoleTable::studentRow,
                                ConsoleTable::studentHeader,
                                ConsoleTable::Footer
                        );
                        if (!enrollmentService.getStudentByCourseId(id , Enrollment.Status.CONFIRM).isEmpty()) {
                            deleteStudentFromCourse(id);
                        }
                        break;
                    case 0:
                        id = 0;
                        return;

                    default:
                        System.out.println("Đang update...");
                        break;

                }

            }catch (NumberFormatException e) {
                System.out.println("Nhập sai dữ liệu");
            }
        }
    }
    private static void approveEnrollmentView() {

        while (true) {

            System.out.println("\n========== DANH SÁCH ĐĂNG KÝ CHỜ DUYỆT ==========");
            ConsoleTable.paginate(
                    enrollmentService.getAllDTO(Enrollment.Status.WAITING),
                    5,
                    ConsoleTable::enrollmentRow,
                    ConsoleTable::enrollmentHeader,
                    ConsoleTable::Footer
            );
            if (enrollmentService.getAllDTO(Enrollment.Status.WAITING).isEmpty()) {
                break;
            }
            System.out.println("1. Duyệt đăng ký");
            System.out.println("2. Từ chối đăng ký");
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
                    approveEnrollment();
                    break;

                case 2:
                    denyEnrollment();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
        ConsoleTable.pause();
    }
    private static void approveEnrollment() {

        System.out.print("Nhập ID đăng ký: ");

        int enrollmentId;

        try {
            enrollmentId = Integer.parseInt(sc.nextLine());
            if (!enrollmentService.checkId(enrollmentId , Enrollment.Status.WAITING)) {
                System.out.println("Id không tồn tại");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ!");
            return;
        }

        if (enrollmentService.updateStatus(enrollmentId , Enrollment.Status.CONFIRM)) {
            System.out.println("Duyệt thành công.");
        } else {
            System.out.println("Duyệt thất bại.");
        }
    }
    private static void denyEnrollment() {

        System.out.print("Nhập ID đăng ký: ");

        int enrollmentId;

        try {
            enrollmentId = Integer.parseInt(sc.nextLine());
            if (!enrollmentService.checkId(enrollmentId , Enrollment.Status.WAITING)) {
                System.out.println("Id không tồn tại");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ!");
            return;
        }

        if (enrollmentService.updateStatus(enrollmentId , Enrollment.Status.CANCEL)) {
            System.out.println("Đã từ chối đăng ký.");
        } else {
            System.out.println("Thao tác thất bại.");
        }
    }
    private static void deleteStudentFromCourse(int courseId) {
        while (true) {
            System.out.print("\nNhập ID học viên muốn xóa (0 để quay lại): ");

            int studentId;

            try {
                studentId = Integer.parseInt(sc.nextLine());
                if (studentId == 0) {
                    return;
                }
                if (!enrollmentService.checkIC(studentId ,courseId , Enrollment.Status.CONFIRM)) {
                    System.out.println("Học viên không thuộc khóa học này.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID không hợp lệ!");
                continue;
            }



            while (true) {

                System.out.print("Bạn có chắc chắn muốn xóa học viên khỏi khóa học? (1.Có / 0.Không): ");

                int confirm;

                try {
                    confirm = Integer.parseInt(sc.nextLine());

                } catch (NumberFormatException e) {
                    System.out.println("Vui lòng nhập 1 hoặc 0!");
                    continue;
                }

                if (confirm == 1) {

                    if (enrollmentService.deleteEnrollment(studentId, courseId)) {
                        System.out.println("Xóa thành công!");
                    } else {
                        System.out.println("Xóa thất bại!");
                    }

                    break;
                }

                if (confirm == 0) {
                    break;
                }

                System.out.println("Vui lòng nhập 1 hoặc 0!");
            }
        }
    }
}
