import java.util.HashMap;

public class Databaze {
	private HashMap<Integer, Osoba> databaze;
	private int index = 1;
	
	public Databaze(){
		databaze=new HashMap<Integer, Osoba>();
	}
	
	public void setUcitel(String jmeno, String prijmeni, int rok) {
		databaze.put(index++, new Ucitel(jmeno,prijmeni,rok));
	}
	
	public void setStudent(String jmeno, String prijmeni, int rok) {
		databaze.put(index++, new Student(jmeno,prijmeni,rok));
	}
}
