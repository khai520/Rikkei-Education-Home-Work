package SS2.Gioi;

import HeThong.ISS;

import java.util.Scanner;

public class B1 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run(){
        System.out.print("Nhập số tính tổng: ");
        int a = sc.nextInt() ,c = Math.abs(a) ,tong = 0;
        do {
            tong += c % 10;
            if (c < 10){
                break;
            }
            c = (c - (c % 10)) / 10;
        }while (true);
        System.out.println("Tổng các chữ số là: " + tong);
    }
}
