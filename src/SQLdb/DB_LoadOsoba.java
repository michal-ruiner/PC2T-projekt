package SQLdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Program.Osoba;
import Program.Student;
import Program.Ucitel;

public class DB_LoadOsoba {
		public static Osoba nacteniOsoby(int id, String nazevDatabaze) {
		String osobaDB = "SELECT id, jmeno, prijmeni, rok, skupina_id FROM uzivatele WHERE id='"+id+"';";
		Osoba osoba = null;
		try {
			Connection conn = DB_Connection.PripojeniKDatabazi(nazevDatabaze);
			Statement stmt = conn.createStatement();
			ResultSet data = stmt.executeQuery(osobaDB);
	           if(data.getInt("skupina_id")==2) {
	            	Osoba.setKeyID(data.getInt("id"));
	            	osoba = new Ucitel(data.getString("jmeno"), data.getString("prijmeni"), data.getInt("rok"));
	            	List<Integer> studenti = new ArrayList<Integer>(DB_Load.studentiUcitele(id, conn));
	            	for(int i=0; i<studenti.size(); i++)
	            		((Ucitel)osoba).setStudenti(studenti.get(i));
	           } else {
	            	Osoba.setKeyID(data.getInt("id"));
	                List<Integer> listUcitelu = new ArrayList<Integer>(DB_Load.uciteleStudenta(data.getInt("id"), conn));
	                if (!listUcitelu.isEmpty()) {
	                	osoba = new Student(data.getString("jmeno"), data.getString("prijmeni"), data.getInt("rok"), listUcitelu);
	                	if(DB_Exist.existujeTabulka("ZnamkyStudenta"+data.getInt("id"), conn)) {
	                		List<Integer> tempZnamky = new ArrayList<Integer>(DB_Load.znamkyStudenta(data.getInt("id"), conn)); 
	                		for(int i=0; i<tempZnamky.size(); i++) {
	                			((Student)osoba).setZnamky(tempZnamky.get(i));
	                		}
	                	}
	                } else
	                	System.out.println("Student s ID "+data.getInt("id")+" nemohl byt nacten, protoze v databazi nema prirazene ucitele.");
	           }
		} catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
        	DB_Connection.OdpojeniOdDatabaze();
        }
		System.out.println(osoba);
		return osoba;
	}
}
