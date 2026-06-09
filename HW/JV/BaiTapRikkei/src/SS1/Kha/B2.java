package SS1.Kha;

import HeThong.ISS;

import java.util.Scanner;

public class B2 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run(){
        System.out.print("Nhập số thứ nhất (firstNumber): ");
        int a = sc.nextInt();
        System.out.print("Nhập số thứ hai (secondNumber): ");
        int b = sc.nextInt();
        System.out.println();
        System.out.println("--- Kết quả ---");
        System.out.println("firstNumber = " + a);
        System.out.println("secondNumber = " + b);
        System.out.println("Tổng = " + (a + b));
        System.out.println("Hiệu = " + (a - b));
        System.out.println("Tích = " + (a * b));
        System.out.println("Thương = " + (b == 0 ?  "Lỗi" : (a / b)));
        System.out.println("Phần dư = " + (b == 0 ?  "Lỗi" : (a % b)));
    }

}
