package presentation;


import business.impl.StudentService;
import model.Student;
import presentation.Admin.ViewAdmin;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MainView {
    static Scanner sc = new Scanner(System.in);
    static StudentService studentService = new StudentService();
    public static void mainView() {
        while (true) {
            System.out.println("==================================================");
            System.out.println("             COURSE MANAGEMENT SYSTEM");
            System.out.println("==================================================");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký học viên");
            System.out.println("0. Thoát");
            System.out.println("--------------------------------------------------");
            System.out.print("Chọn chức năng: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
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
            sc = new Scanner(System.in);
        }
    }
    private static void loginView() {
        System.out.println("\n================== ĐĂNG NHẬP ===================");
        System.out.print("Email: ");
        String email = sc.nextLine();
        while (email == null || email.isEmpty()) {
            System.out.println("Dữ liệu không đươc để trống");
            System.out.print("Email: ");
            email = sc.nextLine();
        }
        System.out.print("Mật khẩu: ");
        String password = sc.nextLine();
        while (password == null || password.isEmpty()) {
            System.out.println("Dữ liệu không đươc để trống");
            System.out.print("Mật khẩu: ");
            password = sc.nextLine();
        }
        int id = studentService.login(email, password);

        Student student = studentService.getStudentById(id , null);

        if (student != null) {

            if (student.getRole()==Student.Role.ADMIN) {

                ViewAdmin.adminView();

                return;
            }

            if(student.getRole()==Student.Role.STUDENT) {

                ViewStudent.studentView(id);

                return;
            }

        }

        System.out.println("Sai tài khoản");
    }
    private static String registerView() {
        System.out.println("\n============== ĐĂNG KÝ TÀI KHOẢN ===============");

        System.out.print("Email: ");
        String registerEmail = sc.nextLine();
        String regex = "^[\\w-.]+@[\\w-]+(\\.[\\w-]+)+$";

        while (!registerEmail.trim().matches(regex)){
            System.out.println("Định dạng email sai vui lòng nhập lại!");
            System.out.print("Email: ");
            registerEmail = sc.nextLine();
        }

        if (studentService.checkTk(registerEmail) ) {
            System.out.println("Tài khoản mới tạo vui lòng cập nhật mật khẩu mới!");
            String regexPassword =
                    "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!%]).{8,}$";

            String registerPassword;

            while (true) {
                try {
                    System.out.print("Mật khẩu: ");
                    registerPassword = sc.nextLine();

                    if (registerPassword.matches(regexPassword)) {
                        Student student =
                                studentService.getStudentByEmail(registerEmail, "ADMIN");

                        if (student == null) {
                            return "Không tìm thấy tài khoản";
                        }

                        return studentService.updatePassword(student.getId(), registerPassword)
                                ? "Đổi mật khẩu thành công"
                                : "Đổi mật khẩu thất bại";
                    }
                    System.out.println(
                            "Mật khẩu phải có ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt!");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
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
