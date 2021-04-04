import java.util.ArrayList;
import java.util.List;

public class Student extends Osoba {
	private float prumer;
	final float hraniceStipendia = 1.5f;
	List<Integer> ucitele;
	List<Integer> znamky;

	public Student(String jmeno, String prijmeni, int rok, List<Integer> prirazeniUcitelu) {
		super(jmeno, prijmeni, rok);
		// TODO Auto-generated constructor stub
		ucitele = new ArrayList<Integer>();	
		znamky = new ArrayList<Integer>();	
		ucitele.addAll(prirazeniUcitelu);
	}
	
	@Override
	public List<Integer> vypisOsob() {
		return ucitele;
	}
	
	public void setUcitele(int id){
		ucitele.add(id);
	}

	public void getZnamky() {
		System.out.println("Znamky studenta: "+znamky);
	}

	public void setZnamky(int znamka) {
		znamky.add(znamka);
	}

	@Override
	protected void smazaniOsobZListu(int id) {
		// TODO Auto-generated method stub
		int index = ucitele.indexOf(id);
		ucitele.remove(index); // list.remove potrebuje index hodnoty ke smazani
	}
}
