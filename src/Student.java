import java.util.ArrayList;
import java.util.List;

public class Student extends Osoba {
	private float prumer;
	final float hraniceStipendia = 1.5f;
	List<Integer> ucitele;

	public Student(String jmeno, String prijmeni, int rok, List prirazeniUcitelu) {
		super(jmeno, prijmeni, rok);
		// TODO Auto-generated constructor stub
		ucitele = new ArrayList<Integer>();	
		ucitele.addAll(prirazeniUcitelu);
	}

	public void pridaniUcitele(int id) {
		ucitele.add(id);
	}
	
	@Override
	public void vypisOsob() {
		for (int i = 0; i < ucitele.size(); i++) {
		      System.out.println("ID ucitele: "+ucitele.get(i)+", Prijmeni: ");
		    }
	}
}
