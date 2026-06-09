package SS2.Kha;

import HeThong.ISS;

import java.util.Scanner;

public class B1 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run() {
        System.out.print("Nhập số tính tổng: ");
        int a = sc.nextInt();
        if (a <= 0){
            System.out.println("Số nhập vào không hợp lệ!");
        }
        else {
            int Tong = 0;
            for(int i = 0 ; i < a; i++){
                Tong += i;
            }
            System.out.println("Tổng số từ 1 đến" + a + "là: " + Tong);
        }
    }
}
