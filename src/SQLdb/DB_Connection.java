package SQLdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {
	
	static Connection conn = null;
	
	public static Connection PripojeniKDatabazi(String nazevSouboru) {
        
		String url = "jdbc:sqlite:database\\" + nazevSouboru;
        try {
        	conn = DriverManager.getConnection(url);
            if (conn != null) {
                System.out.println("Uspesne pripojeno k databazi "+nazevSouboru);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	public static void OdpojeniOdDatabaze() {
		if (conn != null) {
            try {
				conn.close();
				System.out.println("Uspesne odpojeno od databaze.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
}
