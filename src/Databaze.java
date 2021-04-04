import java.util.ArrayList;
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
	
	public void setStudent(String jmeno, String prijmeni, int rok, List<Integer> prirazeniUcitelu) {
		databaze.put(index++, new Student(jmeno,prijmeni,rok, prirazeniUcitelu));
	}
	
	public boolean dbObsahujeUzivatele(int id) {
		if (databaze.containsKey(id)) {
			return true;
		} else
			return false;
	}
	
	public boolean dbObsahujeUcitele(int id) {
		if (databaze.containsKey(id) && databaze.get(id) instanceof Ucitel) {
			return true;
		} else
			return false;
	}
	
	public boolean dbObsahujeStudenta(int id) {
		if (databaze.containsKey(id) && databaze.get(id) instanceof Student) {
			return true;
		} else
			return false;
	}
	
	public void getUcitele(int id) {
		List<Integer> temp = new ArrayList<Integer>();
		if(databaze.containsKey(id)) {
			temp.addAll(databaze.get(id).vypisOsob());
			for(Integer i: temp)
				System.out.println("ID: "+i+", prijmeni: "+databaze.get(i).getPrijmeni());
		}else {
			System.out.println("Student s timto ID neexistuje.");
		}
	}
	
	// Zadani znamek studentovi
	public void zadaniZnamek(int id, int znamka) {
		if(databaze.containsKey(id) && databaze.get(id) instanceof Student) {
			((Student)databaze.get(id)).setZnamky(znamka);
			System.out.println("Zapis znamku uspesne proveden.");
		}else {
			System.out.println("Student s timto ID neexistuje.");
		}
	}
	
	// Ziskani znamek studenta
	public void ziskaniZnamek(int id) {
		if(databaze.containsKey(id) && databaze.get(id) instanceof Student) {
			((Student)databaze.get(id)).getZnamky();
		}else {
			System.out.println("Student s timto ID neexistuje.");
		}
	}
	
	public boolean smazaniOsoby(int id) {
		if(databaze.containsKey(id)){
			System.out.println("Uzivatel "+databaze.get(id).getPrijmeni()+" s ID "+id+" byl uspesne smazan.");
			for (Integer i : databaze.keySet()) {
				if(databaze.get(i) instanceof Student) {
					if(((Student)databaze.get(i)).vypisOsob().contains(id))
						((Student)databaze.get(i)).smazaniOsobZListu(id);
				}
			}
			databaze.remove(id);
			return true;
		} else
			System.out.println("Uzivatel s ID "+id+" nebyl v databazi nalezen.");
			return false;
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
