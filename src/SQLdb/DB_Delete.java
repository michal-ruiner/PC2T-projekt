package SQLdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Delete {

	public static void smazaniOsoby(int id, String nazevSouboru) {
		try {
			Connection conn = DB_Connection.PripojeniKDatabazi(nazevSouboru);	
			int exist = existenceUzivatele(id, nazevSouboru);
			if(exist == 1) {
				int skupina = urceniSkupinyUzivatele(id, nazevSouboru);
				if(skupina == 1) {
					System.out.println("Je to student");
					smazaniOsobyZUzivatelu(id, conn);
					smazaniListuOsob(id, "Student", conn);
					smazaniListuZnamek(id, conn);
				} else {
					System.out.println("Je to ucitel");
					smazaniOsobyZUzivatelu(id, conn);
					smazaniListuOsob(id, "Ucitel", conn);
				}
			} else {
				System.out.println("Zadany uzivatel v DB neexistuje.");
			}
		} finally {
        	DB_Connection.OdpojeniOdDatabaze();
        }
	}
	
	public static int existenceUzivatele(int id, String nazevSouboru) {
		int cislo = 0;
		String uzivatele = "SELECT count(*) FROM uzivatele WHERE id='"+id+"';";
		try {
			Connection conn = DB_Connection.PripojeniKDatabazi(nazevSouboru);
			Statement stmt = conn.createStatement();
			ResultSet exist = stmt.executeQuery(uzivatele);
			cislo = exist.getInt(1);
		} catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
        	DB_Connection.OdpojeniOdDatabaze();
        }
		return cislo;
	}
	
	public static int urceniSkupinyUzivatele(int id, String nazevSouboru) {
		String uzivatel = "SELECT * FROM uzivatele WHERE id='"+id+"';";
		int skupina = 0;
		try {
			Connection conn = DB_Connection.PripojeniKDatabazi(nazevSouboru);
			Statement stmt = conn.createStatement();
			ResultSet data = stmt.executeQuery(uzivatel);
			skupina = data.getInt("skupina_id");			
		} catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
        	DB_Connection.OdpojeniOdDatabaze();
        }
		return skupina;
	}
	
	public static void smazaniOsobyZUzivatelu(int id, Connection conn) {
		String uzivatel = "DELETE FROM uzivatele WHERE id="+id+";";
		try {
			PreparedStatement prStmt = conn.prepareStatement(uzivatel);
		    prStmt.executeUpdate();
		} catch (SQLException e) {
		      e.printStackTrace();
		}
	}
	
	public static void smazaniListuOsob(int id, String skupina, Connection conn) {
		String listOsob = "DROP TABLE IF EXISTS '"+skupina+id+"';";
		try {
			PreparedStatement prStmt = conn.prepareStatement(listOsob);
		    prStmt.executeUpdate();
		} catch (SQLException e) {
		      e.printStackTrace();
		}
	}
	
	public static void smazaniListuZnamek(int id, Connection conn) {
		String listOsob = "DROP TABLE IF EXISTS 'ZnamkyStudenta"+id+"';";
		try {
			PreparedStatement prStmt = conn.prepareStatement(listOsob);
		    prStmt.executeUpdate();
		} catch (SQLException e) {
		      e.printStackTrace();
		}
	}
}
