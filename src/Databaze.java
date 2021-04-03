import java.util.HashMap;
import java.util.List;

public class Databaze {
	private HashMap<Integer, Osoba> databaze;
	private int index = 1;
	
	public Databaze(){
		databaze=new HashMap<Integer, Osoba>();
	}

	public void setUcitel(String jmeno, String prijmeni, int rok) {
		databaze.put(index++, new Ucitel(jmeno,prijmeni,rok));
	}
	
	public void setStudent(String jmeno, String prijmeni, int rok, List prirazeniUcitelu) {
		databaze.put(index++, new Student(jmeno,prijmeni,rok, prirazeniUcitelu));
	}
	
	public boolean dbObsahujeUcitele(int id) {
		if (databaze.containsKey(id) && databaze.get(id) instanceof Ucitel) {
			return true;
		} else
			return false;
	}
	
	public void getUcitele(int id) {
		if(databaze.containsKey(id))
			databaze.get(id).vypisOsob();
		else {
			System.out.println("Student s timto ID neexistuje.");
		}
	}
	
	//************************************************ Vypis databaze pro testovaci ucely
	public void vypisDatabaze(){
		if(databaze.size() > 0) {
			for (Integer i : databaze.keySet())
					System.out.println("ID: "+i+", Jmeno: "+databaze.get(i).getJmeno()+", Prijmeni: "+databaze.get(i).getPrijmeni()+", Rok: "+databaze.get(i).getRok());
			
			System.out.println("");
		} else {
			System.out.println("Zadny student zatim nebyl zadan.\n");
		}
		
	}
	
	public void vypisID(){
		for (Integer key : databaze.keySet())
			System.out.println("ID: "+key);	
	}
	
}
