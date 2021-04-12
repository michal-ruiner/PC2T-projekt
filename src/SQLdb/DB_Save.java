package SQLdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DB_Save {
	public static void vytvoreniHlavniTabulky(String nazevDatabaze) {
		
		String tabulkaUzivatelu = "CREATE TABLE IF NOT EXISTS uzivatele (\n"
                + "id INTEGER PRIMARY KEY,\n"
                + "jmeno TEXT NOT NULL,\n"
                + "prijmeni TEXT NOT NULL,\n"
                + "rok INTEGER NOT NULL,\n"
                + "financni_ohodnoceni[CZK] INTEGER,\n"
                + "skupina_id INTEGER NOT NULL,\n"
                + "FOREIGN KEY (skupina_id) REFERENCES skupina_uzivatelu (skupina_id)"
                + ");";
		
		try {
			Connection conn = DB_Connection.PripojeniKDatabazi(nazevDatabaze);
			Statement stmt = conn.createStatement();
			stmt.execute(tabulkaUzivatelu);
		} catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
        	DB_Connection.OdpojeniOdDatabaze();
        }
	}
	
	public static void vytvoreniSkupin(String nazevDatabaze) {
		String tabulkaSkupin = "CREATE TABLE IF NOT EXISTS skupina_uzivatelu (\n"
				 + "skupina_id INTEGER PRIMARY KEY,\n"
				 + "nazev_skupiny TEXT NOT NULL"
				 + ");";

		String zadaniSkupin = "INSERT INTO skupina_uzivatelu (skupina_id,nazev_skupiny)\n"
						+ "VALUES (1,'Student'),(2,'Ucitel');";
		try {
			Connection conn = DB_Connection.PripojeniKDatabazi(nazevDatabaze);
			Statement stmt = conn.createStatement();
			stmt.execute(tabulkaSkupin);
			stmt.execute(zadaniSkupin);
		} catch (SQLException e) {
			System.out.println("Skupiny jiz existuji.");
        } finally {
        	DB_Connection.OdpojeniOdDatabaze();
        }
	}
	
	public void vlozeniUzivatele(int id, String jmeno, String prijmeni, int rok, int finance, int skupina, String nazevDatabaze) {
	    
		Connection conn = DB_Connection.PripojeniKDatabazi(nazevDatabaze);
	    String insertUser = "INSERT INTO uzivatele (id,jmeno,prijmeni,rok,financni_ohodnoceni,skupina_id) VALUES(?,?,?,?,?,?)";

	    try (PreparedStatement prStmt = conn.prepareStatement(insertUser)) {
	      prStmt.setInt(1, id);
	      prStmt.setString(2, jmeno);
	      prStmt.setString(3, prijmeni);
	      prStmt.setInt(4, rok);
	      prStmt.setInt(5, finance);
	      prStmt.setInt(6, skupina);

	      prStmt.executeUpdate();
	      System.out.println("Novy uzivatel byl vlozen do databaze!");
	    } catch (SQLException e) {
	      System.out.println("Uzivatel jix existuje.");
	      // e.printStackTrace();
	    } finally {
        	DB_Connection.OdpojeniOdDatabaze();
        }
	 }
	
	public static void vlozeniListuOsob(int id, String osoba, String nazevDatabaze) {
		Connection conn = DB_Connection.PripojeniKDatabazi(nazevDatabaze);
		
		String tabulkaOsob = "CREATE TABLE IF NOT EXISTS "+osoba+id+" (\n"
				 + "id_osoby INTEGER PRIMARY KEY,\n"
				 + "id_uzivatele INTEGER NOT NULL"
				 + ");";

	    try {
	      Statement stmt = conn.createStatement();
	      stmt.execute(tabulkaOsob);
	      System.out.println("Novy list osob byl vlozen do databaze!");
	    } catch (SQLException e) {
	      System.out.println("List osob uzivatele "+id+" jiz existuje");
	      //e.printStackTrace();
	    } finally {
        	DB_Connection.OdpojeniOdDatabaze();
        }
	}
	
	public static void pridaniOsobDoListu(int id, String osoba, List<Integer> listOsob, String nazevDatabaze) {
		Connection conn = DB_Connection.PripojeniKDatabazi(nazevDatabaze);
		
	    String insertList = "INSERT INTO "+osoba+id+" (id_osoby,id_uzivatele) VALUES(?,?)";

	    try {
	      PreparedStatement prStmt = conn.prepareStatement(insertList);
	      for (int i = 0; i < listOsob.size();i++) {
	    	  prStmt.setInt(1, listOsob.get(i));
	    	  prStmt.setInt(2, id);
	    	  
	    	  prStmt.executeUpdate();
	      }
	    } catch (SQLException e) {
	      System.out.println("Nebylo mozne zapsat osoby.");
	      //e.printStackTrace();
	    } finally {
        	DB_Connection.OdpojeniOdDatabaze();
        }
	}
	
	public static void vlozeniListuZnamek(int id, String nazevDatabaze) {
		Connection conn = DB_Connection.PripojeniKDatabazi(nazevDatabaze);
		
		String tabulkaZnamek = "CREATE TABLE IF NOT EXISTS ZnamkyStudenta"+id+" (\n"
				 + "znamka INTEGER NOT NULL\n"
				 + ");";

	    try {
	      Statement stmt = conn.createStatement();
	      stmt.execute(tabulkaZnamek);
	      System.out.println("Novy list znamek byl vlozen do databaze!");
	    } catch (SQLException e) {
	      System.out.println("List znamek uzivatele "+id+" jiz existuje");
	      //e.printStackTrace();
	    } finally {
        	DB_Connection.OdpojeniOdDatabaze();
        }
	}
	
	public static void pridaniZnamekDoListu(int id, List<Integer> listZnamek, String nazevDatabaze) {
		Connection conn = DB_Connection.PripojeniKDatabazi(nazevDatabaze);
		
	    String insertList = "INSERT INTO ZnamkyStudenta"+id+" (znamka) VALUES(?)";

	    try {
	      PreparedStatement prStmt = conn.prepareStatement(insertList);
	      for (int i = 0; i < listZnamek.size();i++) {
	    	  prStmt.setInt(1, listZnamek.get(i));
	    	  
	    	  prStmt.executeUpdate();
	      }
	    } catch (SQLException e) {
	      System.out.println("Nebylo mozne zapsat znamky.");
	      //e.printStackTrace();
	    } finally {
        	DB_Connection.OdpojeniOdDatabaze();
        }
	}
}
