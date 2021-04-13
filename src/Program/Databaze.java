package Program;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import SQLdb.DB_Connection;
import SQLdb.DB_Delete;
import SQLdb.DB_Load;
import SQLdb.DB_Save;

public class Databaze{
	private HashMap<Integer, Osoba> databaze;
	
	public Databaze(){
		databaze=new HashMap<Integer, Osoba>();
	}

	public void setUcitel(String jmeno, String prijmeni, int rok) {
		databaze.put(Osoba.getKeyID(), new Ucitel(jmeno,prijmeni,rok));
	}
	
	public void setStudent(String jmeno, String prijmeni, int rok, List<Integer> prirazeniUcitelu) {
		databaze.put(Osoba.getKeyID(), new Student(jmeno,prijmeni,rok, prirazeniUcitelu));
		int tempid=Osoba.getKeyID()-1;
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
	
	//Vyber ucitelu pro studenta
	public int prirazeniUcitelu() {
		int pocetUcitelu = 0;
		System.out.println("Aktualni ucitele v databazi: ");
		for(Integer i: databaze.keySet()) {
			if(databaze.get(i) instanceof Ucitel) {
				System.out.println("ID: "+i+", prijmeni: "+databaze.get(i).getPrijmeni());
				pocetUcitelu++;
			}
		}
		System.out.println();
		return pocetUcitelu;
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
			int tempStipendium = ((Student)databaze.get(id)).getStipendium();
			((Student)databaze.get(id)).setZnamky(znamka);
			System.out.println("Zapis znamky uspesne proveden.");
			for(Integer i : databaze.keySet()) {
				if(databaze.get(i) instanceof Ucitel) {
					if(((Ucitel)databaze.get(i)).vypisOsob().contains(id)) {
						int a;
						if (((Student)databaze.get(id)).getStipendium() > 0 && tempStipendium > 0) {
							continue;
						} else if (((Student)databaze.get(id)).getStipendium() != 0 && tempStipendium == 0) {
							a = 1;
							((Ucitel)databaze.get(i)).setPocetStudentuSeStipendiem(a);
						} else if (((Student)databaze.get(id)).getStipendium() == 0 && tempStipendium > 0) {
							a=-1;
							((Ucitel)databaze.get(i)).setPocetStudentuSeStipendiem(a);
						}
					}
						
				}
			}
		} else {
			System.out.println("Student s timto ID neexistuje.");
		}
	}
	
	// Ziskani znamek studenta
	public void ziskaniZnamek(int id) {
		if(databaze.containsKey(id) && databaze.get(id) instanceof Student) {
			if (((Student)databaze.get(id)).znamky.size()>0){
				((Student)databaze.get(id)).getZnamky();
				((Student)databaze.get(id)).getPrumer();
			} else
				System.out.println("Tomuto studentovi zatim zadne znamky zadany nebyly.");
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
		if(databaze.containsKey(iduc) && databaze.containsKey(idst) && databaze.get(iduc) instanceof Ucitel && databaze.get(idst) instanceof Student) {
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
		if(databaze.containsKey(iduc) && databaze.containsKey(idst) && databaze.get(iduc) instanceof Ucitel && databaze.get(idst) instanceof Student) {
			if(((Ucitel)databaze.get(iduc)).vypisOsob().contains(idst)) {
				((Ucitel)databaze.get(iduc)).smazaniOsobZListu(idst);
				System.out.println("Smazani studenta uspesne provedeno.");
			} else
				System.out.println("Student se v seznamu ucitele nenachazi.");
			if(((Student)databaze.get(idst)).vypisOsob().contains(iduc))
				((Student)databaze.get(idst)).smazaniOsobZListu(iduc);
			else
				System.out.println("Ucitel se nenachazi v seznamu studenta.");
		} else {
			System.out.println("Problem s ID ucitele nebo studenta.");
		}
	}
	
	// Vypis ucitelu razenych podle aktualniho poctu studentu
	public void vypisUcitelu() {
		ArrayList<Ucitel> arraylist = new ArrayList<Ucitel>();
		for (Integer i : databaze.keySet()) {
			if (databaze.get(i) instanceof Ucitel) {
				arraylist.add(((Ucitel)databaze.get(i)));
			}
		}
		/*System.out.println("Pred setrizenim: ");
		for(Ucitel asd: arraylist){
			System.out.println("ID: "+asd.getID()+", pocet studentu: "+asd.vypisOsob().size());
		}
		System.out.println("\nPo setrizeni: ");*/
		Collections.sort(arraylist);
		for(Ucitel str: arraylist){
			System.out.println("ID: "+str.getID()+", pocet studentu: "+str.vypisOsob().size());
	   }
	}
	
	// Vypis osob v kategoriich podle abecedy
	public void vypisOsob() {
		if(databaze.size() > 0) {
			List<Osoba> osoby = new ArrayList<Osoba>();
			Set <Integer> klice=databaze.keySet();
			for (Integer klic:klice)
				osoby.add(databaze.get(klic));
			Collections.sort(osoby,new Comparator<Osoba>(){
				@Override
				public int compare(Osoba o1, Osoba o2) {
					// TODO Auto-generated method stub
					return o1.getPrijmeni().compareTo(o2.getPrijmeni());
				}
			});
			System.out.println("****Ucitele****");
			for(Osoba number: osoby) {
				if(number instanceof Ucitel)
					System.out.println(number);
			}
			System.out.println("\n****Studenti****");
			for(Osoba number: osoby) {
				if(number instanceof Student)
					System.out.println(number);
			}
		} else
			System.out.println("Zadna osoba zatim nebyla zadana.");
	}
	
	// Ziskani informaci o konkretni osobe podle ID
	public void informaceOsoby(int id) {
		if (databaze.containsKey(id)) {
			System.out.println("Jmeno: "+databaze.get(id).getJmeno()+"\n"
							 + "Prijmeni: "+databaze.get(id).getPrijmeni()+"\n"
							 + "Rok narozeni: "+databaze.get(id).getRok());
			if(databaze.get(id) instanceof Student)
				System.out.println("Financni ohodnoceni (stipendium): "+((Student)databaze.get(id)).getStipendium()+" CZK");
			else		
				System.out.println("Financni ohodnoceni (cista mzda): "+((Ucitel)databaze.get(id)).getCistaMzda()+" CZK");
		}
	}
	
	// Vypis studentu konkretniho ucitele razenych podle prumeru
	public void vypisStudentu(int id) {
		if (databaze.containsKey(id) && databaze.get(id) instanceof Ucitel) {
			ArrayList<Integer> tempArID = new ArrayList<Integer>();
			ArrayList<Student> studenti = new ArrayList<Student>();
			tempArID.addAll(((Ucitel)databaze.get(id)).vypisOsob());
			for(int i = 0; i < tempArID.size(); i++) {
				studenti.add(((Student)databaze.get(tempArID.get(i))));
			}
			Collections.sort(studenti);
			for(Student asd: studenti)
				System.out.println("ID: "+asd.getID()+", prumer studenta: "+asd.getPrumer());
		} else
			System.out.println("Neco se pokazilo");
	}
	
	// Celkove financni prostredky na pokryti jednoho mesice
	public void financniProstredky() {
		int hrubeMzdy = 0;
		int stipendia = 0;
		for(Integer i : databaze.keySet()) {
			if(databaze.get(i) instanceof Ucitel) {
				hrubeMzdy+=((Ucitel)databaze.get(i)).vypocetHrubeMzdy();
			} else {
				stipendia+=((Student)databaze.get(i)).getStipendium();
			}
		}
		System.out.println("Celkove financni prostredky na hrube mzdy jsou "+hrubeMzdy+" CZK.");
		System.out.println("Celkove financni prostredky na stipendia jsou "+stipendia+" CZK.");
	}
	
	//************************************************ SQL cast
	public void pripojeniDatabaze(String nazevDatabaze) {
		DB_Connection.PripojeniKDatabazi(nazevDatabaze);
		DB_Connection.OdpojeniOdDatabaze();
	}
	
	public void ulozeniDatabaze(String nazevDatabaze) {
		DB_Save.vytvoreniHlavniTabulky(nazevDatabaze);
		DB_Save.vytvoreniSkupin(nazevDatabaze);
		DB_Save zaznam = new DB_Save();
		int pocet = 0;
		for(Integer i : databaze.keySet()) {
			if(databaze.get(i) instanceof Student) {
				zaznam.vlozeniUzivatele(databaze.get(i).getID(), databaze.get(i).getJmeno(), databaze.get(i).getPrijmeni(), databaze.get(i).getRok(), ((Student)databaze.get(i)).getStipendium(), 1, nazevDatabaze);
				if(((Student)databaze.get(i)).getZnamkyList().size()>0) {
					DB_Save.vlozeniListuZnamek(databaze.get(i).getID(), nazevDatabaze);
					DB_Save.pridaniZnamekDoListu(databaze.get(i).getID(), ((Student)databaze.get(i)).getZnamkyList(), nazevDatabaze);
				}
				if(databaze.get(i).vypisOsob().size() > 0) {
					DB_Save.vlozeniListuOsobStudenta(databaze.get(i).getID(), "Student", nazevDatabaze);
					DB_Save.pridaniOsobDoListuStudenta(databaze.get(i).getID(), "Student", databaze.get(i).vypisOsob(), nazevDatabaze);
				}
			} else {
				zaznam.vlozeniUzivatele(databaze.get(i).getID(), databaze.get(i).getJmeno(), databaze.get(i).getPrijmeni(), databaze.get(i).getRok(), ((Ucitel)databaze.get(i)).getCistaMzda(), 2, nazevDatabaze);
				if(databaze.get(i).vypisOsob().size() > 0) {
					DB_Save.vlozeniListuOsobUcitele(databaze.get(i).getID(), "Ucitel", nazevDatabaze);
					DB_Save.pridaniOsobDoListuUcitele(databaze.get(i).getID(), "Ucitel", databaze.get(i).vypisOsob(), nazevDatabaze);
				}
			}
			pocet++;
		}
		System.out.println("Zapis do databaze uspesne proveden, celkove zapsano "+pocet+" uzivatelu.");
	}
	
	public Databaze nacteniDatabaze(Databaze db, String nazevSouboru) {
		Osoba.setKeyID(1);
		db=null;
		db=DB_Load.nacteniUzivatelu(nazevSouboru);
		return db;
	}
	
	public void smazaniOsobyZDatabaze(int id, String nazevSouboru) {
		DB_Delete.smazaniOsoby(id, nazevSouboru);
	}
	//************************************************
	
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
