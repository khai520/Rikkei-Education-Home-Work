package presentation;


import business.impl.StudentService;
import model.Student;
import presentation.Admin.ViewAdmin;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainView {
    static Scanner sc = new Scanner(System.in);
    static StudentService studentService = new StudentService();
    public static void mainView() {
        while (true) {
            System.out.println();
            System.out.println("==================================================");
            System.out.println("             COURSE MANAGEMENT SYSTEM");
            System.out.println("==================================================");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký học viên");
            System.out.println("0. Thoát");
            System.out.println("------------------------------------------------");
            System.out.print("Chọn chức năng: ");
            int choice;
            try {
                choice = sc.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Vui lòng nhập số");
                continue;
            }

            switch (choice) {
                case 1:
                    loginView();
                    break;

                case 2:
                    System.out.println(registerView());
                    break;
                case 0:
                    System.out.println("Thoát chương trình...");
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }

    }
    private static void loginView() {
        System.out.println("\n================== ĐĂNG NHẬP ===================");
        System.out.print("Email: ");
        String email = sc.next();
        System.out.print("Mật khẩu: ");
        String password = sc.next();
        int id = studentService.login(email, password);

        Student student = studentService.getStudentById(id , null);

        if (student != null && student.getRole() == Student.Role.ADMIN) {
            ViewAdmin.adminView();
        } else if(student != null && student.getRole() == Student.Role.STUDENT) {
            ViewStudent.studentView();
        } else{
            System.out.println("Tên đăng nhập hoặc mật khẩu sai!");
        }
    }
    private static String registerView() {
        System.out.println("\n============== ĐĂNG KÝ TÀI KHOẢN ===============");
        sc.nextLine();

        System.out.print("Email: ");
        String registerEmail = sc.nextLine();
        String regex = "^[\\w-.]+@[\\w-]+(\\.[\\w-]+)+$";
        while (!registerEmail.trim().matches(regex)){
            System.out.println("Định dạng email sai vui lòng nhập lại!");
            registerEmail = sc.nextLine();
        }

        if (studentService.checkTk(registerEmail) ) {
            System.out.println("Tài khoản mới tạo vui lòng cập nhật mật khẩu mới!");
            String regexPassword =
                    "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!%]).{8,}$";

            String registerPassword;

            while (true) {
                System.out.print("Mật khẩu: ");
                registerPassword = sc.nextLine();

                if (registerPassword.matches(regexPassword)) {
                    int checkId = studentService.getStudentByEmail(registerEmail, "STUDENT").getId();

                    return studentService.updatePassword(checkId , registerPassword) ? "Đổi mật khẩu thành công" : "Đổi mật khẩu không thành công";
                }
                System.out.println(
                        "Mật khẩu phải có ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt!");
            }

        }
        else if (studentService.getStudentByEmail(registerEmail , null) != null)
        {
            return "Email đã tạo tài khoản!";
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

        Student student = new Student();

        student.setName(name);
        student.setDob(dob);
        student.setEmail(registerEmail);
        student.setSex(sex);
        student.setPhone(phone);
        student.setRole(Student.Role.STUDENT);
        student.setPassword(registerPassword);
        student.setCreate_at(LocalDate.now());


        return studentService.register(student) ? "\nĐăng ký thành công!" : "\nĐăng ký thất bại";
    }
}
