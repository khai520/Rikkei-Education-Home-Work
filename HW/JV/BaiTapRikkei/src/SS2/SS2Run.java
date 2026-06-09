package SS2;

import HeThong.IRun;
import HeThong.ISS;
import SS1.XuatSac.B1;

public class SS2Run implements IRun {
    @Override
    public void runService(int ss , int cap , int choice) {
        switch (cap){
            case 1:
                switch (choice){
                    case 1:
                        ISS b1 = new SS2.Kha.B1();
                        b1.Run();
                        break;
                    case 2:
                        ISS b2 = new SS2.Kha.B2();
                        b2.Run();
                        break;
                    default:
                        System.out.println("Đang chờ update!");
                        break;
                }
                break;
            case 2:
                switch (choice){
                    case 1:
                        ISS b1 = new SS2.Gioi.B1();
                        b1.Run();
                        break;
                    case 2:
                        ISS b2 = new SS2.Gioi.B2();
                        b2.Run();
                        break;
                    default:
                        System.out.println("Đang chờ update!");
                        break;
                }
                break;
            case 3:
                switch (choice){
                    case 1:
                        ISS b1 = new SS2.XuatSac.B1();
                        b1.Run();
                        break;
                    case 2:
                        ISS b2 = new SS2.XuatSac.B2();
                        b2.Run();
                        break;
                    default:
                        System.out.println("Đang chờ update!");
                        break;
                }
                break;
        }

    }
}
