package presentation.Admin;


import business.impl.CourseService;
import model.Course;
import presentation.ConsoleTable;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CourseView {
    private static final Scanner sc = new Scanner(System.in);
    private static final CourseService courseService = new CourseService();
    public static void courseView() {

        while (true) {

            System.out.println("\n========== QUẢN LÝ KHÓA HỌC ==========");
            System.out.println("1. Hiển thị danh sách khóa học");
            System.out.println("2. Thêm khóa học");
            System.out.println("3. Tìm kiếm khóa học");
            System.out.println("4. Sắp xếp khóa học");
            System.out.println("0. Quay lại");

            System.out.print("Nhập lựa chọn: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    showAllStudents();
                    break;
                case 2:
                    break;
                case 3:
                    getCourse();
                    break;
                case 4:
                    sortCourse();
                    break;
                case 0:
                    return;

                default:
                    System.out.println("Đang phát triển...");
            }
        }
    }
    private static void showAllStudents() {
        while (true) {
            System.out.println("\n===== DANH SÁCH HỌC VIÊN =====");
            List<Course> courses = courseService.getAllCourse();
            ConsoleTable.printLine(111);

            System.out.printf("| %-4s | %-25s | %-12s | %-25s | %-5s |%n",
                    "ID", "Tên khóa học", "Giảng viên", "Thời lượng", "Ngày tạo");

            ConsoleTable.printLine(110);

            for (Course course : courses) {
                System.out.printf("| %-4d | %-25s | %-12s | %-25s | %-5s |%n",
                        course.getId(),
                        course.getName(),
                        course.getInstruction(),
                        course.getDuration(),
                        course.getCreate_at());
            }
            ConsoleTable.printLine(111);
            System.out.print("Chỉnh sửa khóa học 1.Y/0.N: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
                continue;
            }
            if (choice == 1) {
                listUpdateDelete();
            }
            else
            {
                break;
            }
        }
        pause();
    }
    private static void  listUpdateDelete() {
        while (true) {
            try {
                System.out.print("Nhập id khóa học muốn thao tác: ");
                int id = Integer.parseInt(sc.nextLine()) ;
                if (id == 0){
                    return;
                }
                Course course = courseService.getCourseById(id);
                if (course == null) {
                    System.out.println("Khóa học không tồn tại");
                    continue;
                }
                System.out.println("1. Chỉnh sửa khóa học");
                System.out.println("2. Xóa khóa học");
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
                        updateCourse(course);
                        System.out.println("Cập nhật thành công");
                        break;

                    case 2:
                        int choice2 ;
                        while (true) {
                            try {
                                System.out.println("Bạn có chắc chắn muốn xóa khóa học "+ id +" 1.Y/0.N/: ");
                                choice2 = Integer.parseInt(sc.nextLine());
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Vui lòng nhập số!");
                            }
                        }

                        if (choice2 == 1) {
                            deleteStudent(id);
                            return;
                        }
                        break;
                    case 3:

                        break;
                    case 0:
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
    private static void updateCourse(Course course) {
        while (true) {
            System.out.println("\n===== CHỈNH SỬA KHÓA HỌC =====");
            System.out.println("1. Tên khóa học");
            System.out.println("2. Giảng viên");
            System.out.println("3. Thời lượng");
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
                    System.out.print("Tên khóa học: ");
                    String name = sc.nextLine();
                    while (name == null || name.isEmpty()) {
                        System.out.println("Dữ liệu không đươc để trống");
                        name = sc.nextLine();
                    }
                    course.setName(name);
                    if (courseService.updateCourse(course)) {
                        System.out.println("Sửa thành công");
                    }else
                    {
                        System.out.println("Sửa không thành công");
                    }

                    break;
                case 2:
                    System.out.print("Giảng viên: ");
                    String giangvien = sc.nextLine();
                    while (giangvien == null || giangvien.isEmpty()) {
                        System.out.println("Dữ liệu không đươc để trống");
                        giangvien = sc.nextLine();
                    }
                    course.setInstruction(giangvien);
                    if (courseService.updateCourse(course)){
                        System.out.println("Sửa thành công");
                    }else
                    {
                        System.out.println("Sửa không thành công");
                    }
                    break;
                case 3:
                    System.out.print("Thời lượng: ");
                    int thoiluong;
                    try {
                        thoiluong = Integer.parseInt(sc.nextLine());
                        while (thoiluong <= 0) {
                            System.out.println("Thời lượng không được dưới 0");
                            thoiluong = Integer.parseInt(sc.nextLine());
                            course.setDuration(thoiluong);
                        }
                    }catch (NumberFormatException e) {
                        System.out.println("Vui lòng nhập số");
                    }
                    if (courseService.updateCourse(course)){
                        System.out.println("Sửa thành công");
                    }else
                    {
                        System.out.println("Sửa không thành công");
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    private static void deleteStudent(int id) {
        System.out.println("\n===== XÓA KHÓA HỌC =====");
        if (courseService.deleteCourse(id))
        {
            System.out.println("Xóa thành công");
        }
        else {
            System.out.println("Xóa không thành công");
        }
        pause();
    }
    private static void getCourse() {

        while (true) {

            System.out.println("\n===== TÌM KIẾM KHÓA HỌC =====");
            System.out.println("1. Theo ID");
            System.out.println("2. Theo tên");
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
                    System.out.print("Nhập id muốn tìm kiếm: ");
                    int getid  = Integer.parseInt(sc.nextLine());
                    ConsoleTable.printLine(111);

                    System.out.printf("| %-4s | %-25s | %-12s | %-25s | %-5s | %-12s | %-8s |%n",
                            "ID", "Họ tên", "Ngày sinh", "Email", "GT", "Điện thoại", "Role");

                    ConsoleTable.printLine(110);

                    Course course = courseService.getCourseById(getid);
                    System.out.printf("| %-4d | %-25s | %-12s | %-25s | %-5s |%n",
                            course.getId(),
                            course.getName(),
                            course.getInstruction(),
                            course.getDuration(),
                            course.getCreate_at());

                    ConsoleTable.printLine(111);
                    pause();
                    break;
                case 2:
                    System.out.print("Nhập tên muốn tìm kiếm: ");
                    String name  = sc.nextLine();
                    ConsoleTable.printLine(111);

                    System.out.printf("| %-4s | %-25s | %-12s | %-25s | %-5s | %-12s | %-8s |%n",
                            "ID", "Họ tên", "Ngày sinh", "Email", "GT", "Điện thoại", "Role");

                    ConsoleTable.printLine(110);

                    List<Course> courses = courseService.getCourseByName(name);
                    for (Course c : courses) {
                        System.out.printf("| %-4d | %-25s | %-12s | %-25s | %-5s |%n",
                                c.getId(),
                                c.getName(),
                                c.getInstruction(),
                                c.getDuration(),
                                c.getCreate_at());
                    }

                    ConsoleTable.printLine(111);
                    pause();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void sortCourse() {
        List<Course> courses = courseService.getAllCourse();

        while (true) {

            System.out.println("\n===== SẮP XẾP KHÓA HỌC =====");
            System.out.println("1. ID tăng dần");
            System.out.println("2. ID giảm dần");
            System.out.println("3. Tên A -> Z");
            System.out.println("4. Tên Z -> A");
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
                    ConsoleTable.printLine(111);

                    System.out.printf("| %-4s | %-25s | %-12s | %-25s | %-5s | %-12s | %-8s |%n",
                            "ID", "Họ tên", "Ngày sinh", "Email", "GT", "Điện thoại", "Role");

                    ConsoleTable.printLine(110);
                    courses.stream().sorted(Comparator.comparing(Course::getId)).forEach(c -> System.out.printf("| %-4d | %-25s | %-12s | %-25s | %-5s |%n",
                            c.getId(),
                            c.getName(),
                            c.getInstruction(),
                            c.getDuration(),
                            c.getCreate_at()));
                    ConsoleTable.printLine(111);
                    pause();
                    break;
                case 2:
                    ConsoleTable.printLine(111);

                    System.out.printf("| %-4s | %-25s | %-12s | %-25s | %-5s | %-12s | %-8s |%n",
                            "ID", "Họ tên", "Ngày sinh", "Email", "GT", "Điện thoại", "Role");

                    ConsoleTable.printLine(110);
                    courses.stream().sorted(Comparator.comparing(Course::getId).reversed()).forEach(c -> System.out.printf("| %-4d | %-25s | %-12s | %-25s | %-5s |%n",
                            c.getId(),
                            c.getName(),
                            c.getInstruction(),
                            c.getDuration(),
                            c.getCreate_at()));
                    ConsoleTable.printLine(111);
                    pause();
                    break;
                case 3:
                    ConsoleTable.printLine(111);

                    System.out.printf("| %-4s | %-25s | %-12s | %-25s | %-5s | %-12s | %-8s |%n",
                            "ID", "Họ tên", "Ngày sinh", "Email", "GT", "Điện thoại", "Role");

                    ConsoleTable.printLine(110);
                    courses.stream().sorted(Comparator.comparing(Course::getName)).forEach(c -> System.out.printf("| %-4d | %-25s | %-12s | %-25s | %-5s |%n",
                            c.getId(),
                            c.getName(),
                            c.getInstruction(),
                            c.getDuration(),
                            c.getCreate_at()));
                    ConsoleTable.printLine(111);
                    pause();
                    break;
                case 4:
                    ConsoleTable.printLine(111);

                    System.out.printf("| %-4s | %-25s | %-12s | %-25s | %-5s | %-12s | %-8s |%n",
                            "ID", "Họ tên", "Ngày sinh", "Email", "GT", "Điện thoại", "Role");

                    ConsoleTable.printLine(110);
                    courses.stream().sorted(Comparator.comparing(Course::getName).reversed()).forEach(c -> System.out.printf("| %-4d | %-25s | %-12s | %-25s | %-5s |%n",
                            c.getId(),
                            c.getName(),
                            c.getInstruction(),
                            c.getDuration(),
                            c.getCreate_at()));
                    ConsoleTable.printLine(111);
                    pause();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    private static void pause() {
        System.out.println("\nNhấn Enter để tiếp tục...");
        sc.nextLine();
    }
}
