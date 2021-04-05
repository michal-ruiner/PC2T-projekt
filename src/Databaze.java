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
		int tempid=index-1;
		for(int i = 0; i<prirazeniUcitelu.size();i++) {
			zadaniStudentu(prirazeniUcitelu.get(i), tempid);
		}
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
	
	// Ziskani ucitelu studenta
	public void getUcitele(int id) {
		List<Integer> temp = new ArrayList<Integer>();
		if(databaze.containsKey(id) && databaze.get(id) instanceof Student) {
			temp.addAll(((Student)databaze.get(id)).vypisOsob());
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
			System.out.println("Zapis znamky uspesne proveden.");
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
	
	// Smazani osoby z databaze
	public boolean smazaniOsoby(int id) {
		if(databaze.containsKey(id)){
			System.out.println("Uzivatel "+databaze.get(id).getPrijmeni()+" s ID "+id+" byl uspesne smazan.");
			for (Integer i : databaze.keySet()) {
				if(databaze.get(i) instanceof Student) {
					if(((Student)databaze.get(i)).vypisOsob().contains(id))
						((Student)databaze.get(i)).smazaniOsobZListu(id);
				} else {
					if(((Ucitel)databaze.get(i)).vypisOsob().contains(id))
						((Ucitel)databaze.get(i)).smazaniOsobZListu(id);
				}
			}
			databaze.remove(id);
			return true;
		} else {
			System.out.println("Uzivatel s ID "+id+" nebyl v databazi nalezen.");
			return false;
		}
	}
	
	// Pridani studentu do listu ucitele a naopak
	public void zadaniStudentu(int iduc, int idst) {
		if(databaze.containsKey(iduc) && databaze.get(iduc) instanceof Ucitel && databaze.get(idst) instanceof Student) {
			if(!((Ucitel)databaze.get(iduc)).vypisOsob().contains(idst)) {
				((Ucitel)databaze.get(iduc)).setStudenti(idst);
				System.out.println("Zapis studenta uspesne proveden.");
			} else
				System.out.println("Student jiz existuje v seznamu ucitele.");
			if(!((Student)databaze.get(idst)).vypisOsob().contains(iduc))
				((Student)databaze.get(idst)).setUcitele(iduc);
			else
				System.out.println("Ucitel jiz existuje v seznamu studenta.");
		} else {
			System.out.println("Problem s ID ucitele nebo studenta.");
		}
	}
	
	// Odstraneni studenta z listu ucitele a naopak
	public void smazaniStudenta(int iduc, int idst) {
		if(databaze.containsKey(iduc) && databaze.get(iduc) instanceof Ucitel && databaze.get(idst) instanceof Student) {
			if(((Ucitel)databaze.get(iduc)).vypisOsob().contains(idst)) {
				((Ucitel)databaze.get(iduc)).smazaniOsobZListu(idst);
				System.out.println("Smazani studenta uspesne provedeno.");
			} else
				System.out.println("Student se v seznamu ucitele nenachazi.");
			if(((Student)databaze.get(idst)).vypisOsob().contains(iduc))
				((Student)databaze.get(idst)).smazaniOsobZListu(iduc);
			else
				System.out.println("Ucitel se nenachazel v seznamu studenta.");
		} else {
			System.out.println("Problem s ID ucitele nebo studenta.");
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
	
	public void getStudenti(int id) {
		List<Integer> temp = new ArrayList<Integer>();
		if(databaze.containsKey(id)) {
			temp.addAll(((Ucitel)databaze.get(id)).vypisOsob());
			for(Integer i: temp)
				System.out.println("ID: "+i+", prijmeni: "+databaze.get(i).getPrijmeni());
		}else {
			System.out.println("Ucitel s timto ID neexistuje.");
		}
	}
	
}
