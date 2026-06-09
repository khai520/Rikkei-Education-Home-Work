package SS1.Kha;

import HeThong.ISS;

import java.util.Scanner;

import static HeThong.Config.pi;

public class B1 implements ISS {
    @Override
    public void Run(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Vui lòng nhập bán kính:");
        double r = sc.nextDouble();
        System.out.println("Diện tích hình trong là: " + r * r * pi);

    }
}
