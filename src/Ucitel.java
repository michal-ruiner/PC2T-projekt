import java.util.ArrayList;
import java.util.List;

public class Ucitel extends Osoba{
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

	@Override
	protected void smazaniOsobZListu(int id) {
		// TODO Auto-generated method stub
	}

	public void setStudenti(int id) {
		studenti.add(id);
	}
	
	
}
