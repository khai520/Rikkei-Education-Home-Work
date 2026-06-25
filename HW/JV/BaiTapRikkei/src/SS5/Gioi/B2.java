package SS5.Gioi;

import HeThong.ISS;

import java.util.Scanner;

public class B2 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run() {
        System.out.print("Nhập độ dài: ");
        int n = sc.nextInt() ;
        StringBuilder ma = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 1; i <= n; i++)
        {
            int index = (int)(Math.random() * chars.length());
            ma.append(chars.charAt(index));
        }
        System.out.println(ma);
    }
}
