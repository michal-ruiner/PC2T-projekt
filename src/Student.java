import java.util.ArrayList;
import java.util.List;

public class Student extends Osoba {
	private float prumer;
	final float hraniceStipendia = 1.5f;
	List<Integer> ucitele;

	public Student(String jmeno, String prijmeni, int rok) {
		super(jmeno, prijmeni, rok);
		// TODO Auto-generated constructor stub
		ucitele = new ArrayList<Integer>();		
	}

	public void pridaniUcitele(int id) {
		ucitele.add(id);
	}
}
