package ADF2_Exam;

import JV2_Assignment6.Connector;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Scanner;


public class Main {
    public static final int ACTIVE = 1;
    public static final int DEACTIVE = 0;

    public static void main(String args[]) {
        try {
            Connector connector = Connector.getInstance();
            // khai bao driver
            Class.forName("com.mysql.jdbc.Driver");

            String URL = "jdbc:mysql://localhost:3306/student";

            Connection conn = DriverManager.getConnection(URL, "ADF2", "123456");

            boolean start = true;
            Scanner sc = new Scanner(System.in);
            while (start) {
                System.out.println("1: Add student records");
                System.out.println("2: Display student records");
                System.out.println("3: save");
                System.out.println("4: Exit");
                System.out.println("nhap vao lua chon: ");
                int menu = sc.hasNextInt() ? sc.nextInt() : 0;
                switch (menu) {
                    case 1:
                        addStudent(connector);
                        break;
                    case 2:
                        disPlay(connector);
                        break;
                    case 3:
                        save(connector);
                        break;
                    case 4:
                        ;
                        start = false;
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addStudent(Connector connector) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("NHAP NAME: ");
        String name = sc.nextLine();
        System.out.println("NHAP Email: ");
        String address = sc.nextLine();
        System.out.println("Nhap PHONE: ");
        int phone = sc.nextInt();

        String sql = "INSERT INTO adf2(name,address,phone) VALUES('" + name + "','" + address + "','" + phone + "')";
        connector.updateQuery(sql);
        if (connector.updateQuery(sql) > ACTIVE) {
            System.out.println("Đăng kí thành công");
            return;
        }
        if (connector.updateQuery(sql) < DEACTIVE) {
            System.out.println("Tài khoản chưa được tạo");
            return;
        }
        System.out.println("chua tao duoc tai khoan");

    }

    public static void disPlay(Connector connector) throws Exception {

        String sql = "SELECT * FROM adf2";

        ResultSet rs = connector.showExecuteQuery(sql);
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("name: " + rs.getString("name"));
            System.out.println("address: " + rs.getString("address"));
            System.out.println("phone: " + rs.getString("phone"));
            System.out.println(".....................");
        }
    }

    public static void save(Connector connector) throws Exception {
        String sql = "SELECT * FROM ADF2";
        ResultSet rs = connector.showExecuteQuery(sql);
        Writer writer = null;

        try {

            while (rs.next()) {
                writer.write("id: "+rs.getInt("id")+ " name: " + rs.getString("name")+" phone: "
                        + rs.getString("phone"));
            }
        } catch (IOException ex) {
            // Report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }
}