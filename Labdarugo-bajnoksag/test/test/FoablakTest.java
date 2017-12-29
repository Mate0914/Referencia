package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pkg43_01_nagyfeladatfocibajnoksag.Foablak;
import pkg43_01_nagyfeladatfocibajnoksag.Merkozes;
import pkg43_01_nagyfeladatfocibajnoksag.Csapat;

public class FoablakTest {

    Foablak ablak;

    public FoablakTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ablak = new Foablak();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void tabellaRendezTest() {
        List<Csapat> tabella = new ArrayList<>();
        tabella.add(new Csapat(1, "Csopak", 10, 4, 9));
        tabella.add(new Csapat(2, "Tihany", 10, 3, 9));
        tabella.add(new Csapat(3, "Balatonfüred", 9, 3, 12));
        tabella = ablak.tabellaRendez(tabella);
        assertEquals(3, tabella.get(0).getID());
        assertEquals(2, tabella.get(1).getID());
        assertEquals(1, tabella.get(2).getID());
    }

    @Test
    public void meccsRendezTest() throws ParseException {
        Csapat csapat = new Csapat();
        List<Merkozes> meccs = new ArrayList<>();
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-02");
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        meccs.add(new Merkozes(1, sqlDate, 1, 2, 1, 1, csapat, csapat));
        java.util.Date utilDate2 = new SimpleDateFormat("yyyy-MM-dd").parse("2013-01-01");
        java.sql.Date sqlDate2 = new java.sql.Date(utilDate.getTime());
        meccs.add(new Merkozes(2, sqlDate2, 2, 1, 1, 1, csapat, csapat));
        meccs.add(new Merkozes(3, sqlDate, 2, 1, 1, 1, csapat, csapat));
        ablak.meccsRendez(meccs);
        assertEquals(1, meccs.get(0).getID());
        assertEquals(2, meccs.get(1).getID());
    }

    @Test
    public void tabellaModositUjMeccsTest() throws ParseException {
        Csapat csapat = new Csapat();
        List<Csapat> tabella = new ArrayList<>();
        tabella.add(new Csapat(1, "Balatonfüred", 1, 2, 0));
        tabella.add(new Csapat(2, "Tihany", 2, 1, 3));
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-02");
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        Merkozes meccsModosit = new Merkozes(1, sqlDate, 1, 2, 4, 1, csapat, csapat);
        ablak.setTabella(tabella);
        ablak.tabellaModositUjMeccs(meccsModosit);
        assertEquals(3, ablak.getTabella().get(0).getPont());
        assertEquals(3, ablak.getTabella().get(1).getPont());
        assertEquals(5, ablak.getTabella().get(0).getLottGol());
        assertEquals(3, ablak.getTabella().get(1).getLottGol());
        assertEquals(3, ablak.getTabella().get(0).getKapottGol());
        assertEquals(5, ablak.getTabella().get(1).getKapottGol());
    }

    @Test
    public void datumEllenorizTest() {
        assertEquals(true, ablak.datumEllenoriz("2017-01-01"));
        assertEquals(false, ablak.datumEllenoriz(""));
        assertEquals(false, ablak.datumEllenoriz("2017-01-"));
        assertEquals(false, ablak.datumEllenoriz("asd"));
    }

    @Test
    public void numberFormatEllenorizTest() {
        assertEquals(true, ablak.numberFormatEllenoriz("1"));
        assertEquals(true, ablak.numberFormatEllenoriz("-1"));
        assertEquals(false, ablak.numberFormatEllenoriz(""));
        assertEquals(false, ablak.numberFormatEllenoriz("asd"));
    }

    @Test
    public void golokEllenorizTest() {
        assertEquals(true, ablak.golokEllenoriz("1", "0"));
        assertEquals(false, ablak.golokEllenoriz("-1", "0"));
        assertEquals(false, ablak.golokEllenoriz("0", "-1"));
        assertEquals(false, ablak.golokEllenoriz("a", "12"));
        assertEquals(false, ablak.golokEllenoriz("12", "b"));
    }

    @Test
    public void ujMeccsIdEllenorizTest() throws ParseException {
        Csapat csapat = new Csapat();
        List<Merkozes> meccsek = new ArrayList<>();
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-02");
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        meccsek.add(new Merkozes(1, sqlDate, 1, 2, 2, 2, csapat, csapat));
        ablak.setMeccsek(meccsek);
        List<Csapat> tabella = new ArrayList<>();
        tabella.add(new Csapat(1, "Balatonfüred", 2, 2, 1));
        tabella.add(new Csapat(2, "Tihany", 2, 2, 1));
        ablak.setTabella(tabella);
        assertEquals(true, ablak.ujMeccsIdEllenoriz("2", "1"));
        assertEquals(false, ablak.ujMeccsIdEllenoriz("1", "2"));
        assertEquals(false, ablak.ujMeccsIdEllenoriz("1", "1"));
        assertEquals(false, ablak.ujMeccsIdEllenoriz("5", "1"));
        assertEquals(false, ablak.ujMeccsIdEllenoriz("2", "6"));
    }

    @Test
    public void torolModositMeccsIdEllenorizTest() throws ParseException {
        Csapat csapat = new Csapat();
        List<Merkozes> meccsek = new ArrayList<>();
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-02");
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        meccsek.add(new Merkozes(1, sqlDate, 1, 2, 2, 2, csapat, csapat));
        ablak.setMeccsek(meccsek);
        assertEquals(true, ablak.torolModositMeccsIdEllenoriz("1", "2"));
        assertEquals(false, ablak.torolModositMeccsIdEllenoriz("2", "2"));
        assertEquals(false, ablak.torolModositMeccsIdEllenoriz("4", "2"));
    }
}
