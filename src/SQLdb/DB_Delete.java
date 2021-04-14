package SQLdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DB_Delete {

	public static void smazaniOsoby(int id, String nazevSouboru) {
		int exist = existenceUzivatele(id, nazevSouboru);
		int skupina = urceniSkupinyUzivatele(id, nazevSouboru);
		try {
			Connection conn = DB_Connection.PripojeniKDatabazi(nazevSouboru);	
			if(exist == 1) {
				if(skupina == 1) {
					System.out.println("Je to student");
					smazaniOsobyZUzivatelu(id, conn);
					smazaniListuOsob(id, "Student", conn);
					smazaniListuZnamek(id, conn);
					smazaniZListuUcitelu(id, conn);
				} else {
					System.out.println("Je to ucitel");
					smazaniOsobyZUzivatelu(id, conn);
					smazaniListuOsob(id, "Ucitel", conn);
					smazaniZListuStudentu(id, conn);
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
	
	public static void smazaniZListuUcitelu(int id, Connection conn) {
		List<String> ucitele = new ArrayList<String>();
		String listUcitelu = "SELECT name FROM sqlite_master WHERE name LIKE 'Ucitel%';";
		try {
			Statement stmt = conn.createStatement();
			ResultSet data = stmt.executeQuery(listUcitelu);
			while (data.next()) {;
				ucitele.add(data.getString("name"));
			}
			for(int i=0; i<ucitele.size(); i++) {
				String studentVListu = "DELETE FROM "+ucitele.get(i)+" WHERE id_studenta = "+id+";";
				PreparedStatement prStmt = conn.prepareStatement(studentVListu);
				prStmt.executeUpdate();
			}
		} catch (SQLException e) {
		      e.printStackTrace();
		}
	}
	
	public static void smazaniZListuStudentu(int id, Connection conn) {
		List<String> studenti = new ArrayList<String>();
		String listStudentu = "SELECT name FROM sqlite_master WHERE name LIKE 'Student%';";
		try {
			Statement stmt = conn.createStatement();
			ResultSet data = stmt.executeQuery(listStudentu);
			while (data.next()) {;
				studenti.add(data.getString("name"));
			}
			for(int i=0; i<studenti.size(); i++) {
				String ucitelVListu = "DELETE FROM "+studenti.get(i)+" WHERE id_ucitele = "+id+";";
				PreparedStatement prStmt = conn.prepareStatement(ucitelVListu);
				prStmt.executeUpdate();
			}
		} catch (SQLException e) {
		      e.printStackTrace();
		}
	}
	
	
}
