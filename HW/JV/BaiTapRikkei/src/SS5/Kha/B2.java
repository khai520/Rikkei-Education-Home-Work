package SS5.Kha;

import HeThong.ISS;

public class B2 implements ISS {
    @Override
    public void Run() {
        String sb0 = "Hello";
        StringBuffer sb1 = new StringBuffer("Hello");
        StringBuilder sb2 = new StringBuilder("Hello");
        long starttime , endtime;

        starttime = System.currentTimeMillis();
        for(int i = 0 ; i < 1000000; i++){
            sb0 += "World";
        }
        endtime = System.currentTimeMillis() ;
        System.out.println("Thời gian thực hiện với String: " + (endtime - starttime) + "ms");

        starttime = System.currentTimeMillis();
        for(int i = 0 ; i < 1000000; i++){
            sb1.append("World");
        }
        endtime = System.currentTimeMillis() ;
        System.out.println("Thời gian thưc hiện với StringBuffer: " + (endtime - starttime)+ "ms");

        starttime = System.currentTimeMillis();
        for(int i = 0 ; i < 1000000; i++){
            sb2.append("World");
        }
        endtime = System.currentTimeMillis() ;
        System.out.println("Thời gian thưc hiện với StringBuilder: " + (endtime - starttime)+ "ms");

        System.out.println("Nhận xét: ");
        System.out.println("- String: Không hiệu quả cho phép nối chuỗi nhiều lần do tạo ra nhiều đối tượng mới.");
        System.out.println("- StringBuilder: Hiệu quả và nhanh chóng, thích hợp cho nhiều thao tác nối chuỗi trong một luồng.");
        System.out.println("- StringBuffer: Tương tự như StringBuilder nhưng an toàn với đa luồng, có thể chậm hơn một chút do đồng bộ hóa.");
    }
}
