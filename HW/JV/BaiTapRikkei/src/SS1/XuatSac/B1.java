package SS1.XuatSac;

import HeThong.ISS;

import java.util.Scanner;

public class B1 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run(){
        System.out.print("Nhập cân nặng: ");
        float kg = sc.nextFloat();
        System.out.print("Nhập chiều cao: ");
        float m = sc.nextFloat();
        System.out.printf("Chỉ số BMI: %.2f %n" , kg / (m * m));
    }
}
