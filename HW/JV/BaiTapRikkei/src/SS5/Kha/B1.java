package SS5.Kha;

import HeThong.ISS;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class B1 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run() {
        System.out.print("Nhập email của bạn: ");
        String email = sc.nextLine();
        String regex = "^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)+$";
        System.out.println(email.trim().matches(regex) ? "Email hợp lệ" : "Email không hợp lệ");
    }
}
