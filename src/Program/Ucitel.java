package Program;
import java.util.ArrayList;
import java.util.List;

public class Ucitel extends Osoba implements Comparable<Ucitel>{
	final int studentPenize = 2000;
	final int stipendiumOdmena = 500;
	private int pocetStudentuSeStipendiem = 0;
	private float cistaMzda = 0;
	List<Integer> studenti;
	
	public Ucitel(String jmeno, String prijmeni, int rok) {
		super(jmeno, prijmeni, rok);
		// TODO Auto-generated constructor stub	
		studenti = new ArrayList<Integer>();
	}
	
	public int vypocetHrubeMzdy() {
		int pocetStudentu=0;
		int hrubaMzda = 0;
		for (int i = 0; i < studenti.size(); i++)
			pocetStudentu++;
		hrubaMzda = (pocetStudentu*studentPenize)+pocetStudentuSeStipendiem*stipendiumOdmena;
		return hrubaMzda;
	}
	
	public void vypocetCisteMzdy() {
		int hrubaMzda = vypocetHrubeMzdy();
		this.cistaMzda = (float)(hrubaMzda - ((hrubaMzda*0.065)+(hrubaMzda*0.045)));
		double dan = hrubaMzda*0.15;
		if (dan <= 2320)
			dan = 0;
		this.cistaMzda-=dan;
		if(hrubaMzda*0.15 > 2320)
			this.cistaMzda+=2320;
	}
	
	public int getCistaMzda() {
		//vypocetCisteMzdy();
		return (int)cistaMzda;
	}
	

	public void setPocetStudentuSeStipendiem(int a) {
		this.pocetStudentuSeStipendiem+=a;
	}

	@Override
	protected List<Integer> vypisOsob() {
		// TODO Auto-generated method stub
		return studenti;
	}

	public void setStudenti(int id) {
		studenti.add(id);
	}
	
	@Override
	protected void smazaniOsobZListu(int id) {
		// TODO Auto-generated method stub
		int index = studenti.indexOf(id);
		studenti.remove(index);
	}

	@Override
	public int compareTo(Ucitel uc) {
		// TODO Auto-generated method stub
		int porovnani = uc.studenti.size();
		return porovnani-this.studenti.size();
	}
	
	@Override
    public String toString() {
		return "ID: "+getID()+", prijmeni: "+getPrijmeni()+", jmeno: "+getJmeno()+", rok narozeni: "+getRok()+", cista mzda: "+getCistaMzda()+" CZK";
    }
}
