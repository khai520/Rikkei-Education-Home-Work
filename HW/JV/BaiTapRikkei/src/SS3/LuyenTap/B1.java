package SS3.LuyenTap;

import HeThong.ISS;

import java.util.Scanner;

public class B1 implements ISS {
    Scanner sc = new Scanner(System.in);
    @Override
    public void Run() {
        System.out.println("========= NHẬP THÔNG TIN HÓA ĐƠN =========");
        System.out.print("Nhập tên khách hàng: ");
        String hoTen =  sc.nextLine();
        System.out.print("Nhập tên sản phẩm: ");
        String sp = sc.nextLine();
        System.out.print("Nhập giá sản phẩm: ");
        float gia = sc.nextFloat();
        System.out.print("Nhập số lượng mua: ");
        float sl = sc.nextFloat();
        System.out.print("Khách có thẻ thành viên? (true/false): ");
        boolean tv = sc.nextBoolean();
        float thanhtien = gia * sl, giamgia = tv ? (float) ((thanhtien) * 0.10) : 0, VAT = (float) (thanhtien* 0.8) , tongtien = giamgia + VAT + thanhtien;
        System.out.println("================ HÓA ĐƠN ================");
        System.out.println("Khách hàng: " + hoTen);
        System.out.println("Sản phẩm  : " + sp);
        System.out.println("Số lượng  : " + sl);
        System.out.println("Đơn giá   : " + gia);
        System.out.println("Thành tiền: " + gia * sl);
        System.out.println("Giảm giá thành viên (10%): " + giamgia);
        System.out.println("Tiền VAT (8%): " + VAT);
        System.out.println("Tổng tiền thanh toán: " + tongtien);
    }
}
