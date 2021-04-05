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
		int id = 0;
		Databaze databazeOsob=new Databaze();
		//****************************************************************** Testing users
		databazeOsob.setUcitel("ucitela", "ucitela", 1984);
		databazeOsob.setUcitel("ucitelb", "ucitelb", 1978);
		List<Integer> listUcitelu1 = new ArrayList<Integer>();
		List<Integer> listUcitelu2 = new ArrayList<Integer>();
		listUcitelu2.add(1);
		listUcitelu2.add(2);
		databazeOsob.setStudent("studenta", "studenta", 2000, listUcitelu1);
		databazeOsob.setStudent("studentb", "studentb", 2015, listUcitelu2);
		//******************************************************************
		while(run) {
			System.out.println("\n****Zvolte prosim moznost****");
			System.out.println("1) Pridani nove osoby \n"
							 + "2) Zadat studentovi znamku\n"
							 + "3) Smazani osoby z databaze\n"
							 + "4) Vypis vsech ucitelu studenta\n"
							 + "5) Prirazeni nebo odebrani studenta vyucujicimu\n"
							 /*+ "6)\n"
							 + "7)\n"
							 + "8)\n"
							 + "9)\n"
							 + "10)\n"
							 + "11)\n"
							 + "12)\n"
							 + "13)\n"
							 + "14)\n"
							 + "15)\n"*/
							 + "16) Ukonceni aplikace\n"
							 + "*************Testovaci funkce*************\n"
							 + "17) Vypis databaze\n"
							 + "18) Vypis ID\n"
							 + "19) testing purpose");
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
						List<Integer> listUcitelu = new ArrayList<Integer>();
						System.out.println("Kolika ucitelum bude student prirazen: ");
						int pocetUcitelu = pouzeCelaCisla(sc);
						for(int i = 0; i < pocetUcitelu; i++) {
							System.out.println("Zadejte ID ucitele: ");
							id = pouzeCelaCisla(sc);
							while(!databazeOsob.dbObsahujeUcitele(id)) {
								System.out.println("Ucitel s timto ID neexistuje, zadejte prosim nove id: ");
								id=pouzeCelaCisla(sc);
							}	
							if(listUcitelu.contains(id)) {
								System.out.println("Ucitel s timto ID je jiz prirazen.");
								i--;
							} else
								listUcitelu.add(id);	
						}
						databazeOsob.setStudent(jmeno, prijmeni, rok, listUcitelu);
					} else
						databazeOsob.setUcitel(jmeno, prijmeni, rok);
					break;
				case 2:
					System.out.println("Zadejte ID studenta: ");
					id = pouzeCelaCisla(sc);
					while(!databazeOsob.dbObsahujeStudenta(id)) {
						System.out.println("Student s timto ID neexistuje, zadejte prosim nove id: ");
						id=pouzeCelaCisla(sc);
					}
					System.out.println("Zadejte znamku studenta: ");
					int znamka=pouzeCelaCisla(sc);
					while(znamka < 0 || znamka >5) {
						System.out.println("Zadejte znamku z rozsahu 1-5: ");
						znamka=pouzeCelaCisla(sc);
					}
					databazeOsob.zadaniZnamek(id, znamka);
					break;
				case 3:
					System.out.println("Zadejte ID uzivatele: ");
					id = pouzeCelaCisla(sc);
					while(!databazeOsob.dbObsahujeUzivatele(id)) {
						System.out.println("Uzivatel s timto ID neexistuje, zadejte prosim nove id: ");
						id=pouzeCelaCisla(sc);
					}
					databazeOsob.smazaniOsoby(id);
					break;
				case 4:
					System.out.println("Zadejte ID studenta: ");
					id = pouzeCelaCisla(sc);
					while(!databazeOsob.dbObsahujeStudenta(id)) {
						System.out.println("Student s timto ID neexistuje, zadejte prosim nove id: ");
						id=pouzeCelaCisla(sc);
					}
					databazeOsob.getUcitele(id);
					break;
				case 5:
					podminka = true;
					while(podminka) {
						System.out.println("Zvolte prislusnou operaci:\n"
								+ "p - pridat studenta\n"
								+ "o - odebrat studenta\n");
						String vyber = sc.next();
						if(vyber.contains("p")) {
							System.out.println("Zadejte ID ucitele a studenta: ");
							id = pouzeCelaCisla(sc);
							int idst = pouzeCelaCisla(sc);
							while(!databazeOsob.dbObsahujeUcitele(id)) {
								System.out.println("Ucitel s timto ID neexistuje, zadejte prosim nove id: ");
								id=pouzeCelaCisla(sc);
							}
							while(!databazeOsob.dbObsahujeStudenta(idst)) {
								System.out.println("Student s timto ID neexistuje, zadejte prosim nove id: ");
								idst=pouzeCelaCisla(sc);
							}
							databazeOsob.zadaniStudentu(id,idst);
							podminka = false;
						} else if(vyber.contains("o")) {;
							System.out.println("Zadejte ID ucitele a studenta: ");
							id = pouzeCelaCisla(sc);
							int idst = pouzeCelaCisla(sc);
							while(!databazeOsob.dbObsahujeUcitele(id)) {
								System.out.println("Ucitel s timto ID neexistuje, zadejte prosim nove id: ");
								id=pouzeCelaCisla(sc);
							}
							while(!databazeOsob.dbObsahujeStudenta(idst)) {
								System.out.println("Student s timto ID neexistuje, zadejte prosim nove id: ");
								idst=pouzeCelaCisla(sc);
							}
							databazeOsob.smazaniStudenta(id,idst);
							podminka = false;
						} else {
							System.out.println("Zvolte prosim 'p' pro pridani studenta nebo 'o' pro odebrani studenta");
						}
					}
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
				case 19:
					System.out.println("Zadejte ID ucitele: ");
					id = pouzeCelaCisla(sc);
					while(!databazeOsob.dbObsahujeUcitele(id)) {
						System.out.println("Ucitel s timto ID neexistuje, zadejte prosim nove id: ");
						id=pouzeCelaCisla(sc);
					}
					databazeOsob.getStudenti(id);
					break;
			}
		}
	}

}
