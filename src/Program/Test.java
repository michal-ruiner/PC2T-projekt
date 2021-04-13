package Program;
import java.io.File;
import java.io.FileNotFoundException;
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
	
	public static int zadaniUcitelu(Scanner sc, int pocet) {
		int pocetUcitelu = 0;
		while(!sc.hasNextInt()) { // kontrola pro cele cislo
            System.out.println("Zadejte prosim cele cislo pro pocet ucitelu:");
            sc.next();
		}
		pocetUcitelu=sc.nextInt();
		while(pocetUcitelu < 1 || pocetUcitelu > pocet) { // Pocet ucitelu musi byt minimalne 1 a maximalne pocet ucitelu z databaze
			System.out.println("\nNeplatny pocet ucitelu!");
			sc.nextLine();
			pocetUcitelu = zadaniUcitelu(sc, pocet);
		}
		return pocetUcitelu;
	}
	
	public static String korektniJmeno(Scanner sc) { // Metoda pro zadani spravneho jmena
		while (!sc.hasNext("[A-Za-z]*")) { // Jmeno muze obsahovat pouze pismena abecedy
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
		while(rok < 1955 || rok > datum.getYear()) { // Dokud nebude spravne zvolen realny rok narozeni, program bude stale zadat hodnoty rok narozeni
			System.out.println("\nNeplatny rok narozeni! Rok musi byt v rozmezi 1955-"+datum.getYear());
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
		databazeOsob.setUcitel("ucitelc", "ucitelc", 1978);
		databazeOsob.setUcitel("ucitela", "ucitela", 1984);
		databazeOsob.setUcitel("ucitelb", "ucitelb", 1978);
		List<Integer> listUcitelu1 = new ArrayList<Integer>();
		List<Integer> listUcitelu2 = new ArrayList<Integer>();
		listUcitelu1.add(3);
		listUcitelu2.add(1);
		//listUcitelu2.add(3);
		databazeOsob.setStudent("studentb", "studentb", 2015, listUcitelu2);
		databazeOsob.setStudent("studenta", "studenta", 2000, listUcitelu1);
		databazeOsob.setStudent("studentc", "studentc", 1994, listUcitelu1);
		databazeOsob.setStudent("student", "studentb", 2015, listUcitelu2);
		databazeOsob.zadaniZnamek(4, 1);
		databazeOsob.zadaniZnamek(4, 3);
		databazeOsob.zadaniZnamek(6, 1);
		databazeOsob.zadaniZnamek(6, 1);
		databazeOsob.zadaniZnamek(6, 2);
		//******************************************************************
		 
		while(run) {
			System.out.println("\n****Zvolte prosim moznost****");
			System.out.println("1) Pridani nove osoby \n"
							 + "2) Zadat studentovi znamku\n"
							 + "3) Smazani osoby z databaze\n"
							 + "4) Vypis vsech ucitelu studenta\n"
							 + "5) Prirazeni nebo odebrani studenta vyucujicimu\n"
							 + "6) Vypis informaci o jedne osobe\n"
							 + "7) Vypis ucitelu\n"
							 + "8) Vypis studentu ucitele\n"
							 + "9) Vypis osob v kategoriich abecedne (podle prijmeni)\n"
							 + "10) Potrebne financni prostredky\n"
							 + "11) Pripojeni k SQL databazi\n"
							 + "12) Nacteni udaju z databaze\n"
							 + "13) Ulozeni udaju do databaze\n"
							 + "14) Vymazani osoby z databaze\n"
							 //+ "15)\n"
							 + "16) Ukonceni aplikace\n"
							 + "*************Testovaci funkce*************\n"
							 + "17) Vypis databaze\n"
							 + "18) Vypis znamek");
			volba=pouzeCelaCisla(sc);
			switch(volba) {
				case 1:
					int role = 0;
					sc=new Scanner(System.in);
					boolean podminka = true;
					System.out.println("Zvolte roli osoby (nebo 0 pro navrat do hlavniho menu): \n"
							 + "1 - student \n"
							 + "2 - ucitel");
					while(podminka) { // Dokud nebude spravne zvolena role, program bude stale zadat hodnoty pro roli
						role=pouzeCelaCisla(sc);
						if(role == 1 || role == 2 || role == 0)
							podminka = false;
						else
							System.out.println("\nNevybral jste spravnou roli.");
					}
					if (role != 0 && role == 1) {
						int pocet=databazeOsob.prirazeniUcitelu();
						if (pocet > 0) {
							System.out.println("Zadejte jmeno, prijmeni, rok narozeni: ");
							String jmeno = korektniJmeno(sc);
							String prijmeni = korektniJmeno(sc);
							int rok = korektniRok(sc);
							List<Integer> listUcitelu = new ArrayList<Integer>();
							System.out.println("\nKolika ucitelum bude student prirazen: ");
							int pocetUcitelu = zadaniUcitelu(sc, pocet);
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
						} else {
							System.out.println("Zadny ucitel zatim nebyl zadan, student musi mit alespon 1 ucitele.");
						}
					} else if (role != 0 && role == 2) {
						System.out.println("Zadejte jmeno, prijmeni, rok narozeni: ");
						String jmeno = korektniJmeno(sc);
						String prijmeni = korektniJmeno(sc);
						int rok = korektniRok(sc);
						databazeOsob.setUcitel(jmeno, prijmeni, rok);
					}
					break;
				case 2:
					System.out.println("Zadejte ID studenta (nebo 0 pro navrat do hlavniho menu): ");
					id = pouzeCelaCisla(sc);
					if(id != 0) {
						while(!databazeOsob.dbObsahujeStudenta(id) && id != 0) {
							System.out.println("Student s timto ID neexistuje, zadejte prosim nove id (nebo 0): ");
							id=pouzeCelaCisla(sc);
						}
						if (id != 0) {
							System.out.println("Zadejte znamku studenta: ");
							int znamka=pouzeCelaCisla(sc);
							while(znamka < 1 || znamka >5) {
								System.out.println("Zadejte znamku z rozsahu 1-5: ");
								znamka=pouzeCelaCisla(sc);
							}
							databazeOsob.zadaniZnamek(id, znamka);
						}
					}
					break;
				case 3:
					System.out.println("Zadejte ID uzivatele (nebo 0 pro navrat do hlavniho menu): ");
					id = pouzeCelaCisla(sc);
					if(id != 0) {
						while(!databazeOsob.dbObsahujeUzivatele(id) && id != 0) {
							System.out.println("Uzivatel s timto ID neexistuje, zadejte prosim nove id: ");
							id=pouzeCelaCisla(sc);
						}
						if(id != 0)
							databazeOsob.smazaniOsoby(id);
					}
					break;
				case 4:
					System.out.println("Zadejte ID studenta (nebo 0 pro navrat do hlavniho menu): ");
					id = pouzeCelaCisla(sc);
					if(id != 0) {
						while(!databazeOsob.dbObsahujeStudenta(id) && id != 0) {
							System.out.println("Student s timto ID neexistuje, zadejte prosim nove id: ");
							id=pouzeCelaCisla(sc);
						}
						if(id != 0)
							databazeOsob.getUcitele(id);
					}
					break;
				case 5:
					podminka = true;
					while(podminka) {
						System.out.println("Zvolte prislusnou operaci:\n"
								+ "p - pridat studenta\n"
								+ "o - odebrat studenta\n"
								+ "q - navrat do hlavniho menu");
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
						} else if (vyber.contains("q")) {
							break;
						} else {
							System.out.println("Zvolte prosim 'p' pro pridani studenta nebo 'o' pro odebrani studenta nebo q pro navrat do hlavniho menu");
						}
					}
					break;
				case 6:
					System.out.println("Zadejte ID uzivatele (nebo 0 pro navrat do hlavniho menu): ");
					id = pouzeCelaCisla(sc);
					if(id != 0) {
						while(!databazeOsob.dbObsahujeUzivatele(id) && id != 0) {
							System.out.println("Uzivatel s timto ID neexistuje, zadejte prosim nove id: ");
							id=pouzeCelaCisla(sc);
						}
						if (id != 0)
							databazeOsob.informaceOsoby(id);
					}
					break;
				case 7:
					databazeOsob.vypisUcitelu();
					break;
				case 8:
					System.out.println("Zadejte ID ucitele (nebo 0 pro navrat do hlavniho menu): ");
					id = pouzeCelaCisla(sc);
					if (id != 0) {
						while(!databazeOsob.dbObsahujeUcitele(id) && id != 0) {
							System.out.println("Ucitel s timto ID neexistuje, zadejte prosim nove id: ");
							id=pouzeCelaCisla(sc);
						}
						if(id != 0)
							databazeOsob.vypisStudentu(id);
					}
					break;
				case 9:
					databazeOsob.vypisOsob();
					break;
				case 10:
					databazeOsob.financniProstredky();
					break;
				case 11:
					try {
					      File soubor = new File("name.txt");
					      Scanner cteniSouboru = new Scanner(soubor);
					      String nazevSouboru = cteniSouboru.nextLine();
					      cteniSouboru.close();
					      databazeOsob.pripojeniDatabaze(nazevSouboru);
					    } catch (FileNotFoundException e) {
					      System.out.println("Doslo k chybe.");
					      e.printStackTrace();
					    }
					break;
				case 12:
					try {
					      File soubor = new File("name.txt");
					      Scanner cteniSouboru = new Scanner(soubor);
					      String nazevSouboru = cteniSouboru.nextLine();
					      cteniSouboru.close();
					      databazeOsob=databazeOsob.nacteniDatabaze(databazeOsob, nazevSouboru);
					    } catch (FileNotFoundException e) {
					      System.out.println("Doslo k chybe.");
					      e.printStackTrace();
					    }
					break;
				case 13:
					try {
					      File soubor = new File("name.txt");
					      Scanner cteniSouboru = new Scanner(soubor);
					      String nazevSouboru = cteniSouboru.nextLine();
					      cteniSouboru.close();
					      File databaze = new File("database\\"+nazevSouboru);
					      if(databaze.exists())
					    	  databaze.delete();
					      databazeOsob.ulozeniDatabaze(nazevSouboru);
					    } catch (FileNotFoundException e) {
					      System.out.println("Doslo k chybe.");
					      e.printStackTrace();
					    }
					break;
				case 14:
					try {
					      File soubor = new File("name.txt");
					      Scanner cteniSouboru = new Scanner(soubor);
					      String nazevSouboru = cteniSouboru.nextLine();
					      cteniSouboru.close();
					      System.out.println("Zadejte ID uzivatele: ");
							id = pouzeCelaCisla(sc);
							databazeOsob.smazaniOsobyZDatabaze(id,nazevSouboru);
					    } catch (FileNotFoundException e) {
					      System.out.println("Doslo k chybe.");
					      e.printStackTrace();
					}
					break;
				case 16:
					run = false;
					break;
				case 17:
					databazeOsob.vypisDatabaze();
					break;
				case 18:
					System.out.println("Zadejte ID studenta: ");
					id = pouzeCelaCisla(sc);
					while(!databazeOsob.dbObsahujeStudenta(id)) {
						System.out.println("Student s timto ID neexistuje, zadejte prosim nove id: ");
						id=pouzeCelaCisla(sc);
					}
					databazeOsob.ziskaniZnamek(id);
					break;
			}
		}
	}

}
