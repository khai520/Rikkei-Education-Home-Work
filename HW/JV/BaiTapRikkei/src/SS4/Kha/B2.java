package SS4.Kha;

import HeThong.ISS;

import java.util.Scanner;

public class B2 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run() {
        int hang, cot ,tongchan = 0 ,tongle = 0;
        int[][] phantus;
        do {
            System.out.print("Nhập số hàng của mảng: ");
            hang = sc.nextInt();
            System.out.print("Nhập số cột của mảng: ");
            cot = sc.nextInt();
            if (hang > 0 && cot > 0){
                break;
            }
            System.out.println("Số lượng hàng hoặc cột phải lớn hơn 0!");
        } while (true);
        phantus = new int[hang][cot];
        System.out.println("Nhập các phần tử của mảng: ");
        for (int i = 0 ; i < hang ; i++){
            for (int j = 0; j < cot ; j++){
                System.out.print("Phần tử [" + (i + 1) + "] " + ( j + 1 ) + ": ");
                phantus[i][j] = sc.nextInt();
            }
        }
        System.out.println("Mảng ban đầu: ");
        for (int i = 0 ; i < hang ; i++){
            for (int j = 0; j < cot ; j++){
                System.out.print(phantus[i][j] + " ");
                if (phantus[i][j] % 2 == 0){
                    tongchan += phantus[i][j];
                }else {
                    tongle += phantus[i][j];
                }
            }
        }
        System.out.println("Tổng các số chẵn: " + tongchan);
        System.out.println("Tổng các số lẻ: " + tongle);
    }
}
