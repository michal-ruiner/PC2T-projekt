package Program;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class hrubaMzdaTest {

	@Test
	void test() {
		Ucitel testUcitel = new Ucitel("Pavel", "Novak", 1999);
		testUcitel.setStudenti(1);
		testUcitel.setStudenti(2);
		testUcitel.setStudenti(3);
		testUcitel.setPocetStudentuSeStipendiem(1);
		int testHrubaMzda = testUcitel.vypocetHrubeMzdy();
		assertEquals(6500, testHrubaMzda);
	}

}
