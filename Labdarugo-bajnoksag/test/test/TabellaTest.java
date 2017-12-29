package test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pkg43_01_nagyfeladatfocibajnoksag.Csapat;

public class TabellaTest {

    public TabellaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void construktorTest() {
        Csapat tabella = new Csapat(1, "Balatonfüred", 5, 6, 3);
        assertEquals(1, tabella.getID());
        assertEquals("Balatonfüred", tabella.getCsapatnev());
        assertEquals(5, tabella.getLottGol());
        assertEquals(6, tabella.getKapottGol());
        assertEquals(3, tabella.getPont());

        Csapat tabella2 = new Csapat(2, "Tihany", 10, 4, 12);
        assertEquals(2, tabella2.getID());
        assertEquals("Tihany", tabella2.getCsapatnev());
        assertEquals(10, tabella2.getLottGol());
        assertEquals(4, tabella2.getKapottGol());
        assertEquals(12, tabella2.getPont());
    }

    @Test
    public void setterGetterTest() {
        Csapat tabella = new Csapat(1, "Balatonfred", 5, 6, 3);
        tabella.setCsapatnev("Balatonfüred");
        assertEquals("Balatonfüred", tabella.getCsapatnev());
        tabella.setLottGol(8);
        assertEquals(8, tabella.getLottGol());
        tabella.setKapottGol(10);
        assertEquals(10, tabella.getKapottGol());
        tabella.setPont(13);
        assertEquals(13, tabella.getPont());

        Csapat tabella2 = new Csapat(1, "Thany", 5, 6, 3);
        tabella2.setCsapatnev("Tihany");
        assertEquals("Tihany", tabella2.getCsapatnev());
        tabella2.setLottGol(2);
        assertEquals(2, tabella2.getLottGol());
        tabella2.setKapottGol(15);
        assertEquals(15, tabella2.getKapottGol());
        tabella2.setPont(1);
        assertEquals(1, tabella2.getPont());
    }
}
