import java.util.ArrayList;
import java.util.List;

public class Ucitel extends Osoba implements Comparable<Ucitel>{
	List<Integer> studenti;
	public Ucitel(String jmeno, String prijmeni, int rok) {
		super(jmeno, prijmeni, rok);
		// TODO Auto-generated constructor stub	
		studenti = new ArrayList<Integer>();
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
		int porovnani = ((Ucitel)uc).studenti.size();
		return porovnani-this.studenti.size();
	}	
	@Override
    public String toString() {
		return "ID: "+getID()+", prijmeni: "+getPrijmeni()+", jmeno: "+getJmeno()+", rok narozeni: "+getRok();
    }
}
