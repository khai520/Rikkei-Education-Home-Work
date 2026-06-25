package SS4.XuatSac;

import HeThong.ISS;

import java.util.Scanner;

public class B2 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run() {
        int n , choice = 5 , sx = 0;
        do {
            System.out.print("Nhập số lượng nhân viên: ");
            n = sc.nextInt();
            if (n >= 0){
                break;
            }
            System.out.println("Số lượng nhân viên lớn hơn hoặc bằng 0!");
        } while (true);
        if (n == 0){
            return;
        }
        double[] nhanvien = new double[n];
        for (int i = 0 ; i < n ; i++){
            System.out.print("Nhập lương nhân viên " + (i + 1) + ": ");
            nhanvien[i] = sc.nextDouble();
        }
        do {
            System.out.println("--- QUẢN LÝ LƯƠNG SINH VIÊN ---");
            System.out.println("1. Xem tất cả lương");
            System.out.println("2. Sắp xếp lương");
            System.out.println("3. Tìm kiếm lương");
            System.out.println("4. Thống kê lương");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            choice = sc.nextInt();

            switch (choice){
                case 1:
                    System.out.println("Danh sách lương:");
                    for (double num : nhanvien){
                        int i = 1;
                        System.out.printf("Nhân viên %d: %.1f %n", i , num);
                    }
                    break;
                case 2:
                    System.out.print("Chon cách sắp xếp (1.Tăng dần/ 2.Giảm dần): ");
                    sx = sc.nextInt();
                    if (sx == 1){
                        bubbleSortTang(nhanvien);
                        System.out.println("Đã sắp xếp tăng dần");
                    }
                    else {
                        bubbleSortGiam(nhanvien);
                        System.out.println("Đã sắp xếp giảm dần");
                    }
                    break;
                case 3:
                    System.out.print("Nhập lương cần tìm: ");
                    double diem = sc.nextInt();
                    int check1 = sequentialSearch(nhanvien , diem),
                            check2 = sx == 0 ? -2 : sx == 1 ?  binarySearchTang(nhanvien, diem) : binarySearchGiam(nhanvien, diem);

                    System.out.println("Tìm kiếm tuyến tính: " + (check1 != -1 ? ("Điểm " + diem + " có tại vị trí " + check1) : "Điểm cần tìm không có trong danh sách"));
                    System.out.println("Tìm kiếm nhị phân: " + (check2 == -1 ? "Điểm cần tìm không có trong danh sách" : check2 == -2 ? "Mảng chưa được sắp xếp" : ("Điểm " + diem + " có tại vị trí " + check2) ));
                    break;
                case 4:
                    double luongtb = nhanvien[0], lcn  = nhanvien[0], ltn = nhanvien[0]  ;
                    int nvttb = 0;
                    for (int i = 1 ; i < nhanvien.length; i++){
                        luongtb += nhanvien[i];
                        if (lcn < nhanvien[i]){
                            lcn = nhanvien[i];
                        }
                        if (ltn > nhanvien[i]){
                            ltn = nhanvien[i];
                        }
                        if (nhanvien[i] > 5){
                            nvttb += 1;
                        }
                    }
                    System.out.println("Tổng lương: " + luongtb);
                    luongtb /= nhanvien.length;
                    System.out.println("Lương trung bình: " + luongtb);
                    System.out.println("Lương cao nhất: " + lcn);
                    System.out.println("Lương thâp nhất: " + ltn);
                    System.out.println("Số nhân viên có lương trên trung bình: " + nvttb);
                    break;
                case 5:
                    System.out.println("Thoát chương trình.");
                    break;
            }
        } while (choice != 5);
    }
    public static void bubbleSortGiam(double[] arr){
        int n = arr.length;
        for (int i = 0 ; i < n - 1 ; i++){
            boolean swpper = false;
            for (int j = 0 ; j < n - 1 - i; j++){
                if (arr[j] < arr[ j + 1]){
                    double temp = arr[j];
                    arr[j] = arr[ j + 1];
                    arr[j + 1 ] = temp;
                    swpper = true;
                }
            }
            if(!swpper) break;
        }
    }
    public static void bubbleSortTang(double[] arr){
        int n = arr.length;
        for (int i = 0 ; i < n - 1 ; i++){
            boolean swpper = false;
            for (int j = 0 ; j < n - 1 - i; j++){
                if (arr[j] > arr[ j + 1]){
                    double temp = arr[j];
                    arr[j] = arr[ j + 1];
                    arr[j + 1 ] = temp;
                    swpper = true;
                }
            }
            if(!swpper) break;
        }
    }
    public static int sequentialSearch(double[] arr, double key){
        for (int i = 0 ; i < arr.length; i++){
            if (arr[i] == key){
                return i;
            }
        }
        return -1;
    }
    public static int binarySearchTang(double[] arr , double key){
        int left= 0 , right =  arr.length - 1;
        while (left <= right){
            int mid = left + (right -left) /2;
            if(arr[mid] == key)
            {
                return mid;
            }
            if (arr[mid] < key){
                left = mid + 1;
            }
            else {
                right = mid -1;
            }
        }
        return -1;
    }
    public static int binarySearchGiam(double[] arr , double key){
        int left= 0 , right =  arr.length - 1;
        while (left <= right){
            int mid = left + (right -left) /2;
            if(arr[mid] == key)
            {
                return mid;
            }
            if (arr[mid] > key){
                left = mid + 1;
            }
            else {
                right = mid -1;
            }
        }
        return -1;
    }
}
