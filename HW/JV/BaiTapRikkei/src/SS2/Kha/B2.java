package SS2.Kha;

import HeThong.ISS;

import java.util.Scanner;

public class B2 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run() {
        System.out.print("Nhập số tính tổng: ");
        int thang = sc.nextInt();
        switch (thang){
            case 1 , 3, 5, 7 , 8 , 10 , 12:
                System.out.printf("Tháng %d có 31 ngày.%n", thang);
                break;
            case 2:
                System.out.printf("Tháng %d có 28 hoặc 29 ngày.%n", thang);
                break;
            case 4 , 6 , 9 , 11:
                System.out.printf("Tháng %d có 30 ngày.%n", thang);
                break;
            default:
                System.out.println("Tháng không hợp lệ");
                break;
        }
    }
}
