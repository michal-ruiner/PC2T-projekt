package SQLdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Program.Databaze;
import Program.Osoba;

public class DB_Load {
	
		public static Databaze nacteniUzivatelu(String nazevDatabaze) {
		
		String uzivatele = "SELECT id, jmeno, prijmeni, rok, `financni_ohodnoceni[CZK]`, skupina_id FROM uzivatele";
		Databaze databazeNew=new Databaze();
		try {
			Connection conn = DB_Connection.PripojeniKDatabazi(nazevDatabaze);
			Statement stmt = conn.createStatement();
			ResultSet data = stmt.executeQuery(uzivatele);
			while (data.next()) {
                if(data.getInt("skupina_id")==2) {
                	Osoba.setKeyID(data.getInt("id"));
                	databazeNew.setUcitel(data.getString("jmeno"), data.getString("prijmeni"), data.getInt("rok"));
                } else {
                	Osoba.setKeyID(data.getInt("id"));
                	List<Integer> listUcitelu = new ArrayList<Integer>(uciteleStudenta(data.getInt("id"), conn));
                	if (!listUcitelu.isEmpty()) {
                		databazeNew.setStudent(data.getString("jmeno"), data.getString("prijmeni"), data.getInt("rok"), listUcitelu);
                		if(DB_Exist.existujeTabulka("ZnamkyStudenta"+data.getInt("id"), conn)) {
                			List<Integer> tempZnamky = new ArrayList<Integer>(znamkyStudenta(data.getInt("id"), conn));
                			for(int i=0; i<tempZnamky.size();i++)
                				databazeNew.zadaniZnamek(data.getInt("id"), tempZnamky.get(i));
                		}
                	} else
                		System.out.println("Student s ID "+data.getInt("id")+" nemohl byt nacten, protoze v databazi nema prirazene ucitele.");
                }
            }
			
		} catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
        	DB_Connection.OdpojeniOdDatabaze();
        }
		return databazeNew;
		}
		
		public static List<Integer> uciteleStudenta(int id, Connection conn) {
			List<Integer> tempUcitele = new ArrayList<Integer>();
			String dataUcitele = "SELECT id_ucitele FROM Student"+id;
			try {
				Statement stmt = conn.createStatement();
				ResultSet studenti = stmt.executeQuery(dataUcitele);
				while (studenti.next())
            		tempUcitele.add(studenti.getInt("id_ucitele"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tempUcitele;
		}
		
		public static List<Integer> znamkyStudenta(int id, Connection conn){
			List<Integer> tempZnamky = new ArrayList<Integer>();
			String znamkyStudenta = "SELECT znamka FROM ZnamkyStudenta"+id;
			try {
				Statement stmt = conn.createStatement();
				ResultSet znamka = stmt.executeQuery(znamkyStudenta);
				while (znamka.next())
					tempZnamky.add(znamka.getInt("znamka"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tempZnamky;
		}
		
		/*public static List<Integer> studentiUcitele(int id, Connection conn) {
			List<Integer> tempStudenti = new ArrayList<Integer>();
        	String dataStudenti = "SELECT id_studenta FROM Ucitel"+id;
			try {
				Statement stmt = conn.createStatement();
				ResultSet studenti = stmt.executeQuery(dataStudenti);
				while (studenti.next())
            		tempStudenti.add(studenti.getInt("id_studenta"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tempStudenti;
		}*/
}
