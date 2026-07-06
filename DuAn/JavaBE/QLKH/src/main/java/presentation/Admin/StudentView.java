package presentation.Admin;

import business.impl.StudentService;
import model.Student;
import presentation.ConsoleTable;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class StudentView {
    private static Scanner sc = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();
    public static void studentView() {
        while (true) {
            System.out.println("\n=================================================");
            System.out.println("             QUẢN LÝ HỌC VIÊN");
            System.out.println("=================================================");
            System.out.println("1. Hiển thị danh sách học viên");
            System.out.println("2. Thêm học viên");
            System.out.println("3. Tìm kiếm học viên");
            System.out.println("4. Sắp xếp học viên");
            System.out.println("0. Quay lại");
            System.out.println("=================================================");
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
                    showAllStudents();
                    break;

                case 2:
                    addStudent();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    sortStudent();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
            sc = new Scanner(System.in);
        }
    }
    private static void showAllStudents() {
        while (true) {
            System.out.println("\n===== DANH SÁCH HỌC VIÊN =====");
            List<Student> students = studentService.getAllStudents();
            ConsoleTable.paginate(
                    students,
                    5,
                    ConsoleTable::studentRow,
                    ConsoleTable::studentHeader,
                    ConsoleTable::Footer
            );
            if (students.isEmpty()) {
                break;
            }
            System.out.print("Chỉnh sửa sinh viên 1.Y/0.N: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
                continue;
            }
            if (choice == 1) {
                listUpdateDelete();
            }else
            {
                break;
            }
        }
        ConsoleTable.pause();
    }
    public static int id;
    private static void  listUpdateDelete() {
        int idsv;
        Student student = new Student();
        while (id == 0) {
            System.out.print("Nhập id học viên muốn thao tac: ");
            idsv = Integer.parseInt(sc.nextLine()) ;
            if (idsv == 0){
                return;
            }
            while (true) {
                student = studentService.getStudentById(idsv , "ADMIN");
                if (student != null) {
                    id = student.getId();
                    break;
                }
                System.out.println("Sinh viên không tồn tại");
                idsv = Integer.parseInt(sc.nextLine()) ;
            }
        }
        while (true) {
            try {
                System.out.println("1. Chỉnh sửa sinh viên");
                System.out.println("2. Xóa sinh viên");
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
                        updateStudent(student);
                        System.out.println("Cập nhật thành công");
                        break;

                    case 2:
                        int choice2 ;
                        while (true) {
                            try {
                                System.out.print("Bạn có chắc chắn muốn xóa sinh viên "+ id +" 1.Y/0.N/: ");
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
    private static void addStudent() {
        System.out.println("\n===== THÊM HỌC VIÊN =====");
        String regex = "^[\\w-.]+@[\\w-]+(\\.[\\w-]+)+$";
        String registerEmail;
        while (true){
            System.out.print("Email: ");
            registerEmail = sc.nextLine();
            if (!registerEmail.trim().matches(regex)){
                System.out.println("Định dạng email sai vui lòng nhập lại!");
                continue;
            }
            if (studentService.getStudentByEmail(registerEmail , null) != null)
            {
                System.out.println("Email đã tạo tài khoản!");
                continue;
            }
            break;
        }



        System.out.print("Họ và tên: ");
        String name = sc.nextLine();
        while (name == null || name.isEmpty()) {
            System.out.println("Dữ liệu không đươc để trống");
            name = sc.nextLine();
        }

        LocalDate dob;

        while (true) {
            try {
                System.out.print("Ngày sinh (yyyy-MM-dd): ");
                dob = LocalDate.parse(sc.nextLine());

                if (dob.isAfter(LocalDate.now().minusYears(16))) {
                    System.out.println("Tuổi phải từ 16 trở lên.");
                    continue;
                }

                break;
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng ngày không hợp lệ. Ví dụ: 2005-10-20");
            }
        }



        boolean sex;

        while (true) {
            System.out.print("Giới tính (1: Nam, 0: Nữ): ");
            String input = sc.nextLine();

            if (input.equals("1")) {
                sex = true;
                break;
            }

            if (input.equals("0")) {
                sex = false;
                break;
            }

            System.out.println("Chỉ được nhập 1 hoặc 0!");
        }

        String phone;

        while (true) {
            System.out.print("Số điện thoại: ");
            phone = sc.nextLine();

            if (phone.matches("^0\\d{9}$")) {
                break;
            }

            System.out.println("Số điện thoại không hợp lệ!");
        }

        Student student = new Student();

        student.setName(name);
        student.setDob(dob);
        student.setEmail(registerEmail);
        student.setSex(sex);
        student.setPhone(phone);
        student.setRole(Student.Role.STUDENT);

        studentService.register(student);

        ConsoleTable.pause();
    }

    private static void updateStudent(Student student) {
        while (true) {
            System.out.println("\n===== CHỈNH SỬA HỌC VIÊN =====");
            System.out.println("1. Họ và tên");
            System.out.println("2. Ngày sinh");
            System.out.println("3. Giới tính");
            System.out.println("4. Số điện thoại");
            System.out.println("5. Mật khẩu");
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
                    System.out.print("Họ và tên: ");
                    String name = sc.nextLine();
                    while (name == null || name.isEmpty()) {
                        System.out.println("Dữ liệu không đươc để trống");
                        name = sc.nextLine();
                    }
                    student.setName(name);
                    if (studentService.updateStudent(student)){
                        System.out.println("Sửa thành công");
                    }else
                    {
                        System.out.println("Sửa không thành công");
                    }

                    break;
                case 2:
                    LocalDate dob;

                    while (true) {
                        try {
                            System.out.print("Ngày sinh (yyyy-MM-dd): ");
                            dob = LocalDate.parse(sc.nextLine());

                            if (dob.isAfter(LocalDate.now().minusYears(16))) {
                                System.out.println("Tuổi phải từ 16 trở lên.");
                                continue;
                            }

                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Định dạng ngày không hợp lệ. Ví dụ: 2005-10-20");
                        }
                    }
                    student.setDob(dob);
                    if (studentService.updateStudent(student)){
                        System.out.println("Sửa thành công");
                    }else
                    {
                        System.out.println("Sửa không thành công");
                    }
                    break;
                case 3:
                    boolean sex;

                    while (true) {
                        System.out.print("Giới tính (1: Nam, 0: Nữ): ");
                        String input = sc.nextLine();

                        if (input.equals("1")) {
                            sex = true;
                            break;
                        }

                        if (input.equals("0")) {
                            sex = false;
                            break;
                        }

                        System.out.println("Chỉ được nhập 1 hoặc 0!");
                    }
                    student.setSex(sex);
                    if (studentService.updateStudent(student)){
                        System.out.println("Sửa thành công");
                    }else
                    {
                        System.out.println("Sửa không thành công");
                    }
                    break;
                case 4:
                    String phone;

                    while (true) {
                        System.out.print("Số điện thoại: ");
                        phone = sc.nextLine();

                        if (phone.matches("^0\\d{9}$")) {
                            break;
                        }

                        System.out.println("Số điện thoại không hợp lệ!");
                    }
                    student.setPhone(phone);
                    if (studentService.updateStudent(student)){
                        System.out.println("Sửa thành công");
                    }else
                    {
                        System.out.println("Sửa không thành công");
                    }
                    break;
                case 5:
                    String regexPassword =
                            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!%]).{8,}$";

                    String registerPassword;

                    while (true) {
                        System.out.print("Mật khẩu: ");
                        registerPassword = sc.nextLine();

                        if (registerPassword.matches(regexPassword)) {
                            break;
                        }

                        System.out.println(
                                "Mật khẩu phải có ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt!");
                    }
                    if (studentService.updatePassword(student.getId() , registerPassword)){
                        System.out.println("Sửa mật khẩu thành công");
                    }
                    else
                    {
                        System.out.println("Sửa mật khẩu không thành công");
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
        System.out.println("\n===== XÓA HỌC VIÊN =====");
        if (studentService.deleteStudent(id)){
            System.out.println("Xóa thành công");
        }
        else {
            System.out.println("Xóa không thành công");
        }
        ConsoleTable.pause();
    }

    private static void searchStudent() {

        while (true) {

            System.out.println("\n===== TÌM KIẾM HỌC VIÊN =====");
            System.out.println("1. Theo ID");
            System.out.println("2. Theo tên");
            System.out.println("3. Theo Email");
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
                    Student student = studentService.getStudentById(getid , "ADMIN");
                    ConsoleTable.studentHeader();
                    if (student != null){
                        ConsoleTable.studentRow(student);
                    }
                    ConsoleTable.Footer();
                    ConsoleTable.pause();
                    break;
                case 2:
                    System.out.print("Nhập tên muốn tìm kiếm: ");
                    String name  = sc.nextLine();
                    List<Student> searchname = studentService.getStudentByName(name , "ADMIN");
                    ConsoleTable.paginate(
                            searchname,
                            5,
                            ConsoleTable::studentRow,
                            ConsoleTable::studentHeader,
                            ConsoleTable::Footer
                    );
                    ConsoleTable.pause();
                    break;
                case 3:
                    System.out.print("Nhập email muốn tìm kiếm: ");
                    String email  = sc.nextLine();

                    Student getEmail = studentService.getStudentByEmail(email , "ADMIN");
                    ConsoleTable.studentHeader();
                    if(getEmail != null){
                        ConsoleTable.studentRow(getEmail);
                    }
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

    private static void sortStudent() {
        List<Student> students = studentService.getAllStudents();

        while (true) {

            System.out.println("\n===== SẮP XẾP HỌC VIÊN =====");
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
                    ConsoleTable.paginate(
                            students.stream().sorted(Comparator.comparing(Student::getId)).toList(),
                            5,
                            ConsoleTable::studentRow,
                            ConsoleTable::studentHeader,
                            ConsoleTable::Footer
                    );
                    ConsoleTable.pause();
                    break;
                case 2:
                    ConsoleTable.paginate(
                            students.stream().sorted(Comparator.comparing(Student::getId).reversed()).toList(),
                            5,
                            ConsoleTable::studentRow,
                            ConsoleTable::studentHeader,
                            ConsoleTable::Footer
                    );
                    ConsoleTable.pause();
                    break;
                case 3:
                    ConsoleTable.paginate(
                            students.stream().sorted(Comparator.comparing(Student::getName)).toList(),
                            5,
                            ConsoleTable::studentRow,
                            ConsoleTable::studentHeader,
                            ConsoleTable::Footer
                    );
                    ConsoleTable.pause();
                    break;
                case 4:
                    ConsoleTable.paginate(
                            students.stream().sorted(Comparator.comparing(Student::getName).reversed()).toList(),
                            5,
                            ConsoleTable::studentRow,
                            ConsoleTable::studentHeader,
                            ConsoleTable::Footer
                    );
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
