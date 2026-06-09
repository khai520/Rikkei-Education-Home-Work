package SS1.Gioi;

import HeThong.ISS;

import java.util.Scanner;

public class B2  implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run(){
        System.out.print("Nhập chiều dài: ");
        float width = sc.nextFloat();
        System.out.print("Nhập chiều rộng: ");
        float height = sc.nextFloat();
        float area = width * height , perimeter = (width + height) * 2;
        System.out.println("Diện tích: " + area);
        System.out.println("Chu vi: " + perimeter);
    }
}
