package HeThong;


import SS1.SS1Run;
import SS2.SS2Run;
import SS3.SS3Run;
import SS4.SS4Run;
import SS5.SS5Run;

public  class Run  implements IRun {
    @Override
    public void runService(int ss, int cap , int choice){
        switch (ss){
            case 1:
                SS1Run ss1 = new SS1Run();
                ss1.runService(ss, cap , choice);
                break;
            case 2:
                SS2Run ss2 = new SS2Run();
                ss2.runService(ss , cap , choice);
                break;
            case 3:
                SS3Run ss3 = new SS3Run();
                ss3.runService(ss , cap , choice);
                break;
            case 4:
                SS4Run ss4 = new SS4Run();
                ss4.runService(ss , cap , choice);
                break;
            case 5:
                SS5Run ss5 = new SS5Run();
                ss5.runService(ss , cap , choice);
                break;
            default:
                System.out.println("Đang chờ update!");
                break;
        }
    }

}
