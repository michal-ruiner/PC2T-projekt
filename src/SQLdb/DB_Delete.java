package SQLdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Delete {

	public static void smazaniOsoby(int id, String nazevSouboru) {
		String uzivatele = "SELECT count(*) FROM uzivatele WHERE id='"+id+"';";
		//String uzivatele = "DELETE * FROM uzivatele WHERE id="+id+";";
		try {
			Connection conn = DB_Connection.PripojeniKDatabazi(nazevSouboru);
			Statement stmt = conn.createStatement();
			int exist = stmt.executeQuery(uzivatele).getInt(1);
			if(exist == 1) {
				System.out.println("Zadany uzivatel v DB existuje.");
				urceniSkupinyUzivatele(id,conn);
			} else {
				System.out.println("Zadany uzivatel v DB neexistuje.");
			}
		} catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
        	DB_Connection.OdpojeniOdDatabaze();
        }
	}
	
	public static void urceniSkupinyUzivatele(int id, Connection conn) {
		String uzivatel = "SELECT * FROM uzivatele WHERE id='"+id+"';";
		try {
			Statement stmt = conn.createStatement();
			ResultSet data = stmt.executeQuery(uzivatel);
			if(data.getInt("skupina_id")==1)
				System.out.println("Je to student");
			else
				System.out.println("Je to ucitel");
		} catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
}
