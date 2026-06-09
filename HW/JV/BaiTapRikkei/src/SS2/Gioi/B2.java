package SS2.Gioi;

import HeThong.ISS;

import java.util.Scanner;

public class B2 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run() {
        System.out.print("Nhập cạnh 1: ");
        int a = sc.nextInt();
        System.out.print("Nhập cạnh 2: ");
        int b = sc.nextInt();
        System.out.print("Nhập cạnh 3: ");
        int c = sc.nextInt();
        if (a + b <= c || a + c <= b || b + c <= a ){
            System.out.println("Ba cạnh không tạo thành tam giác.");
        }else {
            if (a == b && a == c){
                System.out.println("Đây là tam giác đều.");
            } else if ((a == c && a != b) || (a == b && a != c) || ( b == c && b != a)){
                System.out.println("Đây là tam giác cân.");
            } else if ((a * a == b * b + c * c ) || (b * b == a * a + c *c) || (c * c == a * a + b * b)) {
                System.out.println("Đây là tam giác vuông.");
            } else {
                System.out.println("Đây là tam giác thường.");
            }

        }
    }
}
