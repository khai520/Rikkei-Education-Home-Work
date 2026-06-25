import HeThong.Run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {
    public static final String USER_NAME = "postgres";
    public static final String PASSWORD = "123456";
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            return conn;
        }
        catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay driver");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static void main(String[] args) {
        Run run = new Run();
        Scanner sc = new Scanner(System.in);
        int check = 1 , ss = 0 , cap = 0 , bai = 0;
        do {
            if (check == 1){
                System.out.print("Nhập ss bạn muốn: ");
                ss = sc.nextInt();
                System.out.print("Nhập cấp độ bạn muốn ( 1/Khá , 2/Giỏi , 3/Xuất Xắc): ");
                cap = sc.nextInt();
                System.out.print("Nhập bài bạn muốn xem: ");
                bai = sc.nextInt();
            }
            run.runService(ss , cap ,bai);
            System.out.print("Bạn muốn tìm bài tiếp không (1:Yes , 2:Continue): ");
            check = sc.nextInt();
        } while (check == 1 || check == 2);

    }
}