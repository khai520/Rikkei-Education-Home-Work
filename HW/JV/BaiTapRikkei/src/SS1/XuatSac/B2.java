package SS1.XuatSac;

import HeThong.ISS;

import java.util.Scanner;

public class B2 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run(){
        System.out.print("Nhập vận tốc: ");
        float v = sc.nextFloat();
        System.out.print("Nhập thời gian: ");
        float t = sc.nextFloat();
        System.out.printf("Quãng đường: %.0f %n" , v * t);
    }
}
