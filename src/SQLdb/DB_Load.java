package SQLdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Program.Databaze;
import Program.Student;

public class DB_Load {
	
public static Databaze nacteniUzivatelu(String nazevDatabaze) {
		
		String uzivatele = "SELECT id, jmeno, prijmeni, rok, `financni_ohodnoceni[CZK]`, skupina_id FROM uzivatele";
		Databaze databazeNew=new Databaze();
		try {
			Connection conn = DB_Connection.PripojeniKDatabazi(nazevDatabaze);
			Statement stmt = conn.createStatement();
			Statement stmt2 = conn.createStatement();
			ResultSet data = stmt.executeQuery(uzivatele);
			while (data.next()) {
                if(data.getInt("skupina_id")==2) {
                	int tempid = data.getInt("id");
                	List<Integer> tempStudenti = new ArrayList<Integer>();
                	String dataStudenti = "SELECT id_studenta FROM Ucitel"+tempid;
                	ResultSet studenti = stmt2.executeQuery(dataStudenti);
                	while (studenti.next())
                		tempStudenti.add(studenti.getInt("id_studenta"));
                	databazeNew.setUcitel(data.getString("jmeno"), data.getString("prijmeni"), data.getInt("rok"));
                } else {
                	int tempid = data.getInt("id");
                	List<Integer> tempUcitele = new ArrayList<Integer>();
                	String dataUcitele = "SELECT id_ucitele FROM Student"+tempid;
                	ResultSet studenti = stmt2.executeQuery(dataUcitele);
                	while (studenti.next())
                		tempUcitele.add(studenti.getInt("id_ucitele"));
                	databazeNew.setStudent(data.getString("jmeno"), data.getString("prijmeni"), data.getInt("rok"), tempUcitele);
                }
            }
			
		} catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
        	DB_Connection.OdpojeniOdDatabaze();
        }
		return databazeNew;
	}
}
