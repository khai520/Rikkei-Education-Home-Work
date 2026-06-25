package SS5.XuatSac;

import HeThong.ISS;

import java.util.Scanner;

public class B1 implements ISS {
    @Override
    public void Run() {
        Scanner sc = new Scanner(System.in);
        int choice;
        String chuoi = "";
        do {
            System.out.println("\n********************* MENU *********************");
            System.out.println("1. Nhập chuỗi");
            System.out.println("2. Đếm số ký tự thường, hoa, số, đặc biệt");
            System.out.println("3. Đảo ngược chuỗi");
            System.out.println("4. Kiểm tra Palindrome");
            System.out.println("5. Chuẩn hóa chuỗi");
            System.out.println("6. Thoát");
            System.out.println("************************************************");

            System.out.print("Lựa chọn của bạn: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    chuoi = sc.nextLine();
                    break;

                case 2:
                    if (chuoi.isEmpty())
                    {
                        System.out.println("Chuỗi rỗng hoặc null");
                    }
                    else {
                        int thuong = 0;
                        int hoa = 0;
                        int so = 0;
                        int dacBiet = 0;

                        for (int i = 0; i < chuoi.length(); i++) {
                            char c = chuoi.charAt(i);

                            if (Character.isLowerCase(c)) {
                                thuong++;
                            } else if (Character.isUpperCase(c)) {
                                hoa++;
                            } else if (Character.isDigit(c)) {
                                so++;
                            } else {
                                dacBiet++;
                            }
                        }

                        System.out.println("Số ký tự thường: " + thuong);
                        System.out.println("Số ký tự hoa: " + hoa);
                        System.out.println("Số chữ số: " + so);
                        System.out.println("Số ký tự đặc biệt: " + dacBiet);
                    }
                    break;

                case 3:
                    if (chuoi.isEmpty())
                    {
                        System.out.println("Chuỗi rỗng hoặc null");
                    }
                    else {
                        System.out.println("Chuỗi đảo ngược: " + new StringBuilder(chuoi).reverse());
                    }
                    break;

                case 4:
                    if (chuoi.isEmpty())
                    {
                        System.out.println("Chuỗi rỗng hoặc null");
                    }
                    else {
                        System.out.println(chuoi.equalsIgnoreCase(new StringBuilder(chuoi).reverse().toString())? "Chuỗi không phải Palindrome" : "Chuỗi Palindrome");
                    }
                    break;

                case 5:
                    if (chuoi.isEmpty())
                    {
                        System.out.println("Chuỗi rỗng hoặc null");
                    }
                    else {
                        System.out.println("Chuỗi sau khi chuẩn hóa: " + Character.toUpperCase(chuoi.charAt(0)) + chuoi.substring(1).toLowerCase());
                    }
                    break;

                case 6:
                    System.out.println("Thoát chương trình...");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }

        } while (choice != 6);
    }
}
