package SS2.XuatSac;

import HeThong.ISS;

import java.util.Scanner;

public class B1 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run() {
        System.out.print("Nhập số: ");
        int a = sc.nextInt() , b = a, dem = 0 , c;
        String tengoi = "";
        if (a > 999 || a < 100){
            System.out.println("Số nhập vào không hợp lệ");
            return;
        }
        do {
            dem++;
            String  tam = "";
            c = b % 10;
            b = (b - (b % 10)) / 10;
            switch (c){
                case 0:
                    if(dem == 2){
                        tam = " lẻ";
                    }
                    break;
                case 1:
                    if (b % 10 > 1 && dem == 1){
                        tam = " mốt";
                    } else if (a % 10 >= 0 && dem == 2){
                        tam = " mười";
                    } else if (b == 0) {
                        tam = "Một";
                    } else {
                        tam = " một";
                    }
                    break;
                case 2:

                    if (b == 0) {
                        tam = "Hai";
                    } else if (a % 10 >= 0 && dem == 2){
                        tam = " hai mươi";
                    } else {
                        tam = " hai";
                    }
                    break;
                case 3:
                    if (b == 0) {
                        tam = "Ba";
                    } else if (a % 10 >= 0 && dem == 2){
                        tam = " ba mươi";
                    } else {
                        tam = " ba";
                    }
                    break;
                case 4:
                    if (dem == 1 && b % 10 > 1){
                        tam = " tư";
                    } else if (a % 10 >= 0 && dem == 2){
                        tam = " bốn mươi";
                    } else if (b == 0) {
                        tam = "Bốn";
                    } else {
                        tam = " bốn";
                    }
                    break;
                case 5:
                    if (dem == 1 && b % 10 > 0){
                        tam = " lăm";
                    } else if (a % 10 >= 0 && dem == 2){
                        tam = " năm mươi";
                    } else if (b == 0) {
                        tam = "Năm";
                    } else {
                        tam = " năm";
                    }
                    break;
                case 6:
                    if (b == 0) {
                        tam = "Sáu";
                    } else if (a % 10 >= 0 && dem == 2){
                        tam = " sáu mươi";
                    } else {
                        tam = " sáu";
                    }
                    break;
                case 7:
                    if (b == 0) {
                        tam = "Bảy";
                    } else if (a % 10 >= 0 && dem == 2){
                        tam = " bảy mươi";
                    } else {
                        tam = " bảy";
                    }
                    break;
                case 8:
                    if (b == 0) {
                        tam = "Tám";
                    } else if (a % 10 >= 0 && dem == 2){
                        tam = " tám mươi";
                    } else {
                        tam = " tám";
                    }
                    break;
                case 9:
                    if (b == 0) {
                        tam = "Chín";
                    } else if (a % 10 >= 0 && dem == 2){
                        tam = " chín mươi";
                    } else {
                        tam = " chín";
                    }
                    break;
            }
            if (dem == 2 ){
                tam = " trăm".concat(tam);
            }
            tengoi = tam.concat(tengoi) ;
            if (b == 0){
                break;
            }
        }while (true);
        System.out.println(tengoi);
    }
}
