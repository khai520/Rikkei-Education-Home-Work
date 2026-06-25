package SS5.Gioi;

import HeThong.ISS;

import java.util.Scanner;

public class B1 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run() {
        String regex =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!%]).{8,}$";
        System.out.print("Nhập mật khẩu: ");
        String password = sc.nextLine();

        if (password.matches(regex)) {
            System.out.println("Mật khẩu hợp lệ");
        } else {
            System.out.println("Mật khẩu không hợp lệ");
        }
    }
}
