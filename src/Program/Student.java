package Program;
import java.util.ArrayList;
import java.util.List;

public class Student extends Osoba implements Comparable<Student> {
	private float prumer = 0;
	final float hraniceStipendia = 1.5f;
	final int stipendium = 1500;
	private int stipendiumStudenta=0;
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
	
	public List<Integer> getZnamkyList() {
		return znamky;
	}

	public void setZnamky(int znamka) {
		znamky.add(znamka);
		prumer = 0;
		int tempSoucet=0;
		for (int i = 0; i < znamky.size(); i++) {
			tempSoucet+=znamky.get(i);
		}
		prumer = (float)tempSoucet/znamky.size();
		if(prumer < hraniceStipendia && prumer != 0) {
			this.stipendiumStudenta = stipendium;
		} else {
			stipendiumStudenta = 0;
		}
	}
	
	public float getPrumer() {
		return prumer;
	}
	
	public int getStipendium() {
		return stipendiumStudenta;
	}

	@Override
	protected void smazaniOsobZListu(int id) {
		// TODO Auto-generated method stub
		int index = ucitele.indexOf(id);
		ucitele.remove(index); // list.remove potrebuje index hodnoty ke smazani
	}
	
	@Override
    public String toString() {
        return "ID: "+getID()+", prijmeni: "+getPrijmeni()+", jmeno: "+getJmeno()+", rok narozeni: "+getRok()+", stipendium: "+stipendiumStudenta+" CZK";
    }

	@Override
	public int compareTo(Student o) {
		// TODO Auto-generated method stub
		return (Float.compare(this.getPrumer(), o.getPrumer()));
	}
}
