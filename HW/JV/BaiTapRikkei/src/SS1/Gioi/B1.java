package SS1.Gioi;

import HeThong.ISS;

import java.util.Scanner;

public class B1 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run(){
        System.out.print("Nhập số tử 1: ");
        int a = sc.nextInt();
        System.out.print("Nhập số mẫu 1: ");
        int b = sc.nextInt();
        System.out.print("Nhập số tử 2: ");
        int c = sc.nextInt();
        System.out.print("Nhập số mẫu 2: ");
        int d = sc.nextInt();
        System.out.println("Tổng hai phân số là: " + (a * d + b * c) + "/" + (b * d));
    }
}
