package HeThong;


import SS1.SS1Run;
import SS2.SS2Run;

public  class Run implements IRun {
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
            default:
                System.out.println("Đang chờ update!");
                break;
        }
    }

}
