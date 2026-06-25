package SS4.Kha;

import HeThong.ISS;
import SS4.BubbleSort;

import java.util.Scanner;

public class B1 implements ISS {
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
        BubbleSort.bubbleSortGiam(phantus);
        System.out.println("Mảng sau khi sắp xếp : ");
        for (int num : phantus){
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
