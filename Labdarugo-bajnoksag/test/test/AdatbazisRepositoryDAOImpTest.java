package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pkg43_01_nagyfeladatfocibajnoksag.LabdarugobajnoksagDAOJDBCImpl;
import pkg43_01_nagyfeladatfocibajnoksag.Merkozes;
import pkg43_01_nagyfeladatfocibajnoksag.Csapat;
import pkg43_01_nagyfeladatfocibajnoksag.LabdarugobajnoksagDAO;

public class AdatbazisRepositoryDAOImpTest {

    private LabdarugobajnoksagDAO adatbazis;

    public AdatbazisRepositoryDAOImpTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        adatbazis = new LabdarugobajnoksagDAOJDBCImpl();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void adatbazisLetrehozTest() {
        String adatbazisNev = "2011";
        assertEquals(true, adatbazis.adatbazisLetrehoz(adatbazisNev));
        assertEquals(false, adatbazis.adatbazisLetrehoz(adatbazisNev));
        assertEquals(true, adatbazis.adatbazisTorol(adatbazisNev));
    }

    @Test
    public void tablakLetrehozTest() {
        String adatbazisNev = "2019";
        assertEquals(false, adatbazis.tablakLetrehoz(adatbazisNev));
        assertEquals(true, adatbazis.adatbazisLetrehoz(adatbazisNev));
        assertEquals(true, adatbazis.tablakLetrehoz(adatbazisNev));
        assertEquals(true, adatbazis.adatbazisTorol(adatbazisNev));
    }

    @Test
    public void tabellaFeltoltTest() {
        String adatbazisNev = "2015";
        List<String> lista = new ArrayList<>();
        lista.add("Tihany");
        lista.add("Balatonfüred");
        assertEquals(false, adatbazis.tabellaFeltolt(adatbazisNev, lista));
        assertEquals(true, adatbazis.adatbazisLetrehoz(adatbazisNev));
        assertEquals(true, adatbazis.tablakLetrehoz(adatbazisNev));
        assertEquals(true, adatbazis.tabellaFeltolt(adatbazisNev, lista));
        assertEquals(false, adatbazis.tabellaFeltolt(adatbazisNev, lista));
        assertEquals(true, adatbazis.adatbazisTorol(adatbazisNev));
    }

    @Test
    public void adatbazisTorolTest() {
        String adatbazisNev = "";
        assertEquals(false, adatbazis.adatbazisTorol(adatbazisNev));
    }

    @Test
    public void adatbazisTabellaModositTest() {
        String adatbazisNev = "2015";
        List<Csapat> tabella = new ArrayList<>();
        DefaultListModel<String> lista = new DefaultListModel<>();
        lista.addElement("Tihany");
        lista.addElement("Balatonfüred");
        assertEquals(true, adatbazis.adatbazisLetrehoz(adatbazisNev));
        assertEquals(true, adatbazis.tablakLetrehoz(adatbazisNev));
        assertEquals(true, adatbazis.adatbazisTabellaModosit(adatbazisNev, tabella));
        assertEquals(true, adatbazis.adatbazisTorol(adatbazisNev));
        adatbazisNev = "20123";
        assertEquals(false, adatbazis.adatbazisTabellaModosit(adatbazisNev, tabella));
    }

    @Test
    public void adatbazisMerkozesModositTest() {
        String adatbazisNev = "2015";
        List<Merkozes> meccs = new ArrayList<>();
        DefaultListModel<String> lista = new DefaultListModel<>();
        lista.addElement("Tihany");
        lista.addElement("Balatonfüred");
        assertEquals(true, adatbazis.adatbazisLetrehoz(adatbazisNev));
        assertEquals(true, adatbazis.tablakLetrehoz(adatbazisNev));
        assertEquals(true, adatbazis.adatbazisMerkozesModosit(adatbazisNev, meccs));
        assertEquals(true, adatbazis.adatbazisTorol(adatbazisNev));
        adatbazisNev = "20123";
        assertEquals(false, adatbazis.adatbazisMerkozesModosit(adatbazisNev, meccs));
    }

    @Test
    public void tabellaBetoltTest() {
        String adatbazisNev = "2012";
        List<Csapat> tabella = new ArrayList<>();
        assertEquals(tabella, adatbazis.tabellaBetolt(adatbazisNev));
        tabella.add(new Csapat(1, "Balatonfüred", 3, 0, 3));
        tabella.add(new Csapat(2, "Tihany", 0, 3, 0));
        List<String> lista = new ArrayList<>();
        lista.add("Balatonfüred");
        lista.add("Tihany");
        assertEquals(true, adatbazis.adatbazisLetrehoz(adatbazisNev));
        assertEquals(true, adatbazis.tablakLetrehoz(adatbazisNev));
        assertEquals(true, adatbazis.tabellaFeltolt(adatbazisNev, lista));
        assertEquals(true, adatbazis.adatbazisTabellaModosit(adatbazisNev, tabella));
        assertEquals(1, adatbazis.tabellaBetolt(adatbazisNev).get(0).getID());
        assertEquals(2, adatbazis.tabellaBetolt(adatbazisNev).get(1).getID());
        assertEquals("Balatonfüred", adatbazis.tabellaBetolt(adatbazisNev).get(0).getCsapatnev());
        assertEquals("Tihany", adatbazis.tabellaBetolt(adatbazisNev).get(1).getCsapatnev());
        assertEquals(3, adatbazis.tabellaBetolt(adatbazisNev).get(0).getLottGol());
        assertEquals(0, adatbazis.tabellaBetolt(adatbazisNev).get(1).getLottGol());
        assertEquals(0, adatbazis.tabellaBetolt(adatbazisNev).get(0).getKapottGol());
        assertEquals(3, adatbazis.tabellaBetolt(adatbazisNev).get(1).getKapottGol());
        assertEquals(3, adatbazis.tabellaBetolt(adatbazisNev).get(0).getPont());
        assertEquals(0, adatbazis.tabellaBetolt(adatbazisNev).get(1).getPont());
        assertEquals(true, adatbazis.adatbazisTorol(adatbazisNev));
    }

    @Test
    public void merkozesBetoltTest() {
        try {
            String adatbazisNev = "2013";
            List<Merkozes> meccs = new ArrayList<>();
            assertEquals(meccs, adatbazis.merkozesBetolt(adatbazisNev));
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("2013-01-01");
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            Csapat csapat = new Csapat();
            meccs.add(new Merkozes(1, sqlDate, 1, 2, 3, 0, csapat, csapat));
            java.util.Date utilDate2 = new SimpleDateFormat("yyyy-MM-dd").parse("2013-01-02");
            java.sql.Date sqlDate2 = new java.sql.Date(utilDate.getTime());
            meccs.add(new Merkozes(2, sqlDate2, 2, 1, 0, 3, csapat, csapat));
            List<String> lista = new ArrayList<>();
            lista.add("Balatonfüred");
            lista.add("Tihany");
            assertEquals(true, adatbazis.adatbazisLetrehoz(adatbazisNev));
            assertEquals(true, adatbazis.tablakLetrehoz(adatbazisNev));
            assertEquals(true, adatbazis.tabellaFeltolt(adatbazisNev, lista));
            assertEquals(true, adatbazis.adatbazisMerkozesModosit(adatbazisNev, meccs));
            assertEquals(1, adatbazis.merkozesBetolt(adatbazisNev).get(0).getID());
            assertEquals(2, adatbazis.merkozesBetolt(adatbazisNev).get(1).getID());
            assertEquals(sqlDate, adatbazis.merkozesBetolt(adatbazisNev).get(0).getDatum());
            assertEquals(sqlDate2, adatbazis.merkozesBetolt(adatbazisNev).get(1).getDatum());
            assertEquals(1, adatbazis.merkozesBetolt(adatbazisNev).get(0).getHazai_ID());
            assertEquals(2, adatbazis.merkozesBetolt(adatbazisNev).get(1).getHazai_ID());
            assertEquals(2, adatbazis.merkozesBetolt(adatbazisNev).get(0).getVendeg_ID());
            assertEquals(1, adatbazis.merkozesBetolt(adatbazisNev).get(1).getVendeg_ID());
            assertEquals(3, adatbazis.merkozesBetolt(adatbazisNev).get(0).getHazaiGol());
            assertEquals(0, adatbazis.merkozesBetolt(adatbazisNev).get(1).getHazaiGol());
            assertEquals(0, adatbazis.merkozesBetolt(adatbazisNev).get(0).getVendegGol());
            assertEquals(3, adatbazis.merkozesBetolt(adatbazisNev).get(1).getVendegGol());
            assertEquals(true, adatbazis.adatbazisTorol(adatbazisNev));
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
