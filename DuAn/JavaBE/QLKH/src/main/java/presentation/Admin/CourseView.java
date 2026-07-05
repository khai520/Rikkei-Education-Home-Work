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
                    showAllCourse();
                    break;
                case 2:
                    addCourse();
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
    private static void showAllCourse() {
        while (true) {
            System.out.println("\n===== DANH SÁCH KHÓA HỌC =====");
            List<Course> courses = courseService.getAllCourse();

            ConsoleTable.courseHeader();
            courses.forEach(ConsoleTable::courseRow);
            ConsoleTable.Footer();

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
        ConsoleTable.pause();
    }

    private static void addCourse() {
        System.out.print("Tên khóa học: ");
        String name = sc.nextLine();
        while (name == null || name.isEmpty()) {
            System.out.println("Dữ liệu không đươc để trống");
            name = sc.nextLine();
        }

        System.out.print("Giảng viên: ");
        String giangvien = sc.nextLine();
        while (giangvien == null || giangvien.isEmpty()) {
            System.out.println("Dữ liệu không đươc để trống");
            giangvien = sc.nextLine();
        }

        System.out.print("Thời lượng: ");
        int thoiluong = Integer.parseInt(sc.nextLine());
        while (thoiluong <= 0) {
            System.out.println("Thời lượng không được = 0 hoặc bé hơn 0");
            thoiluong = Integer.parseInt(sc.nextLine());
        }
        Course course = new Course();
        course.setName(name);
        course.setInstruction(giangvien);
        course.setDuration(thoiluong);
        if (courseService.addCourse(course))
        {
            System.out.println("Thêm thành công");
        }
        else {
            System.out.println("Thêm thất bại");
        }
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
                            deleteCourse(id);
                            return;
                        }
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
    private static void deleteCourse(int id) {
        System.out.println("\n===== XÓA KHÓA HỌC =====");
        if (courseService.deleteCourse(id))
        {
            System.out.println("Xóa thành công");
        }
        else {
            System.out.println("Xóa không thành công");
        }
        ConsoleTable.pause();
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
                    Course course = courseService.getCourseById(getid);

                    ConsoleTable.courseHeader();
                    ConsoleTable.courseRow(course);
                    ConsoleTable.Footer();

                    ConsoleTable.pause();
                    break;
                case 2:
                    System.out.print("Nhập tên muốn tìm kiếm: ");
                    String name  = sc.nextLine();
                    List<Course> courses = courseService.getAllCourse().stream().filter(c -> c.getName().equals(name)).toList();

                    ConsoleTable.courseHeader();
                    courses.forEach(ConsoleTable::courseRow);
                    ConsoleTable.Footer();

                    ConsoleTable.pause();
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
                    ConsoleTable.courseHeader();
                    courses.stream().sorted(Comparator.comparing(Course::getId)).forEach(ConsoleTable::courseRow);
                    ConsoleTable.Footer();
                    ConsoleTable.pause();
                    break;
                case 2:
                    ConsoleTable.courseHeader();
                    courses.stream().sorted(Comparator.comparing(Course::getId).reversed()).forEach(ConsoleTable::courseRow);
                    ConsoleTable.Footer();
                    ConsoleTable.pause();
                    break;
                case 3:
                    ConsoleTable.courseHeader();
                    courses.stream().sorted(Comparator.comparing(Course::getName)).forEach(ConsoleTable::courseRow);
                    ConsoleTable.Footer();
                    ConsoleTable.pause();
                    break;
                case 4:
                    ConsoleTable.courseHeader();
                    courses.stream().sorted(Comparator.comparing(Course::getName).reversed()).forEach(ConsoleTable::courseRow);
                    ConsoleTable.Footer();
                    ConsoleTable.pause();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
