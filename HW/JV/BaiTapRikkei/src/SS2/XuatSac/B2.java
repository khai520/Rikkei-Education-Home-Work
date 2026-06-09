package SS2.XuatSac;

import HeThong.ISS;

import java.util.Scanner;

public class B2 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run() {
        int n ;

        do {
            System.out.print("Nhập số nguyên dương: ");
            n = sc.nextInt();
            if (n < 0){
                System.out.println("Số nhập vào không hợp lệ");
            }
        }while (n < 0);
        for (int x = 0 ; x <= n ; x++){
            int soChuSo = String.valueOf(x).length();
            int i = x , tong = 0;
            do {
               tong += Math.pow((i % 10),soChuSo) ;

                i /= 10;
            }while (i > 0);
            if (tong == x){
                System.out.print(x + " ");
            }
        }
        System.out.println();
    }
}
