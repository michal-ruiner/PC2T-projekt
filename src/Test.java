import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
	
	public static int pouzeCelaCisla(Scanner sc) { // Metoda pro overeni zadani celeho cisla
		while(!sc.hasNextInt()) {
            System.out.println("Zadejte prosim cele cislo z nabidky:");
            sc.next();
		}
		int cislo = sc.nextInt();
		return cislo;
	}
	
	public static String korektniJmeno(Scanner sc) { // Metoda pro zadani spravneho jmena
		while (!sc.hasNext("[A-Za-z]*")) {
	          System.out.println("Jmeno muze obsahovat pouze pismena: ");
	          sc.next();
	      }
		String jmenoUzivatele = sc.next();
		return jmenoUzivatele;
	}
	
	public static int korektniRok(Scanner sc) {
		LocalDate datum = LocalDate.now();
		int rok = 0;
		while(!sc.hasNextInt()) { // kontrola pro cele cislo
            System.out.println("Zadejte prosim cele cislo pro rok narozeni:");
            sc.next();
		}
		rok=sc.nextInt();
		while(rok < 1900 || rok > datum.getYear()) { // Dokud nebude spravne zvolen realny rok narozeni, program bude stale zadat hodnoty rok narozeni
			System.out.println("\nNeplatny rok narozeni!");
			sc.nextLine();
			rok = korektniRok(sc);
		}
		return rok;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		boolean run = true;
		int volba;
		Databaze databazeOsob=new Databaze();
		while(run) {
			System.out.println("****Zvolte prosim moznost****");
			System.out.println("1) Pridani nove osoby \n"
							 + "2)\n"
							 + "3)\n"
							 + "4) Vypis vsech ucitelu studenta\n"
							 + "5)\n"
							 + "6)\n"
							 + "7)\n"
							 + "8)\n"
							 + "9)\n"
							 + "10)\n"
							 + "11)\n"
							 + "12)\n"
							 + "13)\n"
							 + "14)\n"
							 + "15)\n"
							 + "16) Ukonceni aplikace\n"
							 + "*************Testovaci funkce*************\n"
							 + "17) Vypis databaze\n"
							 + "18) Vypis ID");
			volba=pouzeCelaCisla(sc);
			switch(volba) {
				case 1:
					int role = 0;
					sc=new Scanner(System.in);
					boolean podminka = true;
					System.out.println("Zvolte roli osoby: \n"
							 + "1 - student \n"
							 + "2 - ucitel");
					while(podminka) { // Dokud nebude spravne zvolena role, program bude stale zadat hodnoty pro roli
						role=pouzeCelaCisla(sc);
						if(role == 1 || role == 2)
							podminka = false;
						else
							System.out.println("\nNevybral jste spravnou roli.");
					}
					System.out.println("Zadejte jmeno, prijmeni, rok narozeni: ");
					String jmeno = korektniJmeno(sc);
					String prijmeni = korektniJmeno(sc);
					int rok = korektniRok(sc);
					if(role == 1) {
						// Prideleni vyucujicich studentovi
						List<Integer> listUcitelu = new ArrayList<Integer>();
						System.out.println("Kolika ucitelum bude student prirazen: ");
						int pocetUcitelu = pouzeCelaCisla(sc);
						for(int i = 0; i < pocetUcitelu; i++) {
							System.out.println("Zadejte ID ucitele: ");
							int id = pouzeCelaCisla(sc);
							while(!databazeOsob.dbObsahujeUcitele(id)) {
								System.out.println("Ucitel s timto ID neexistuje, zadejte prosim nove id: ");
								id=pouzeCelaCisla(sc);
							}		
							listUcitelu.add(id);	
						}
						databazeOsob.setStudent(jmeno, prijmeni, rok, listUcitelu);
					} else
						databazeOsob.setUcitel(jmeno, prijmeni, rok);
					break;
				case 4:
					System.out.println("Zadejte ID studenta: ");
					int id = pouzeCelaCisla(sc);
					databazeOsob.getUcitele(id);
					break;
				case 16:
					run = false;
					break;
				case 17:
					databazeOsob.vypisDatabaze();
					break;
				case 18:
					databazeOsob.vypisID();
					break;
			}
		}
	}

}
