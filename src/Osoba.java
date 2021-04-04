import java.util.List;

public abstract class Osoba {
	private String jmeno;
	private String prijmeni;
	private int rok;
	
	public Osoba(String jmeno, String prijmeni, int rok) {
		this.jmeno=jmeno;
		this.prijmeni=prijmeni;
		this.rok=rok;
	}

	//******************************************************* Sekce Gettery a Settery
	public String getJmeno() {
		return jmeno;
	}

	public void setJmeno(String jmeno) {
		this.jmeno = jmeno;
	}

	public String getPrijmeni() {
		return prijmeni;
	}

	public void setPrijmeni(String prijmeni) {
		this.prijmeni = prijmeni;
	}

	public int getRok() {
		return rok;
	}

	public void setRok(int rok) {
		this.rok = rok;
	}
	//*******************************************************

	protected abstract List<Integer> vypisOsob();
	protected abstract void smazaniOsobZListu(int id);
}
