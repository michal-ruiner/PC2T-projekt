package SQLdb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Exist {
	public static boolean existujeTabulka(String nazevTabulky, Connection conn) {
		int exist = 0;
		String tabulka = "SELECT count(*) FROM sqlite_master WHERE type='table' AND name='"+nazevTabulky+"';";
		try {
			Statement stmt = conn.createStatement();
			exist = stmt.executeQuery(tabulka).getInt(1);       	
		} catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		if (exist == 0)
			return false;
		else
			return true;
	}
}
