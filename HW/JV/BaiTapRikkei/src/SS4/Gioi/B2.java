package SS4.Gioi;

import HeThong.ISS;

import java.util.Scanner;

public class B2 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run() {
        int n;
        int[] phantus;
        do {
            System.out.print("Nhập số lượng phần tử của mảng: ");
            n = sc.nextInt();
            if (n >= 0){
                break;
            }
            System.out.println("Số lượng phần tử phải lớn hơn 0!");
        } while (true);
        if (n == 0){
            System.out.println("Mảng không có phần tử!");
            return;
        }
        phantus = new int[n];
        System.out.println("Nhập các phần tử của mảng: ");
        for (int i = 0 ; i < n ; i++){
            System.out.print("Phần tử thứ " + (i + 1) + ": ");
            phantus[i] = sc.nextInt();
        }
        System.out.println("Mảng ban đầu: ");
        for (int num : phantus){
            System.out.print(num + " ");
        }
        System.out.println();
        for (int i = 0 ; i < phantus.length - 1; i++)
        {
            boolean swapper = false;
            for ( int j = i + 1 ; j >= 1 ; j--){
                if(phantus[j] % 2 == 0 && phantus[j - 1] % 2 == 1){
                    int temp = phantus[j];
                    phantus[j] = phantus[j - 1];
                    phantus[j - 1] = temp;
                    swapper = true;
                }
                if(!swapper) break;
            }
        }
        System.out.println("Mảng sau khi sắp xếp : ");
        for (int num : phantus){
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
