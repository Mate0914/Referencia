package test;

import java.sql.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pkg43_01_nagyfeladatfocibajnoksag.Merkozes;
import pkg43_01_nagyfeladatfocibajnoksag.Csapat;

public class MeccsTest {

    public MeccsTest() {
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
        Csapat csapat = new Csapat();
        Date datum = Date.valueOf("2017-02-14");
        Merkozes meccs = new Merkozes(1, datum, 3, 4, 0, 2, csapat, csapat);
        assertEquals(1, meccs.getID());
        assertEquals(Date.valueOf("2017-02-14"), meccs.getDatum());
        assertEquals(3, meccs.getHazai_ID());
        assertEquals(4, meccs.getVendeg_ID());
        assertEquals(0, meccs.getHazaiGol());
        assertEquals(2, meccs.getVendegGol());
        assertEquals('V', meccs.getGyozelem());

        Date datum2 = Date.valueOf("2017-04-20");
        Merkozes meccs2 = new Merkozes(2, datum2, 4, 6, 2, 2, csapat, csapat);
        assertEquals(2, meccs2.getID());
        assertEquals(Date.valueOf("2017-04-20"), meccs2.getDatum());
        assertEquals(4, meccs2.getHazai_ID());
        assertEquals(6, meccs2.getVendeg_ID());
        assertEquals(2, meccs2.getHazaiGol());
        assertEquals(2, meccs2.getVendegGol());
        assertEquals('D', meccs2.getGyozelem());

        Merkozes meccs3 = new Merkozes(2, datum, 4, 6, 3, 2, csapat, csapat);
        assertEquals('H', meccs3.getGyozelem());
    }

    @Test
    public void setterGetterTest() {
        Date datum = Date.valueOf("2017-02-01");
        Date datum2 = Date.valueOf("2017-02-02");
        Date datum3 = Date.valueOf("2017-02-03");
        Csapat csapat = new Csapat();
        Merkozes meccs = new Merkozes(1, datum, 3, 4, 0, 2, csapat, csapat);
        meccs.setDatum(datum2);
        assertEquals(Date.valueOf("2017-02-02"), meccs.getDatum());
        meccs.setHazaiGol(2);
        assertEquals(2, meccs.getHazaiGol());
        meccs.setVendegGol(3);
        assertEquals(3, meccs.getVendegGol());
        assertEquals('V', meccs.getGyozelem());
        meccs.setHazaiGol(3);
        assertEquals('D', meccs.getGyozelem());
        meccs.setHazaiGol(4);
        assertEquals('H', meccs.getGyozelem());

        Merkozes meccs2 = new Merkozes(1, datum, 3, 4, 0, 2, csapat, csapat);
        meccs2.setDatum(datum3);
        assertEquals(Date.valueOf("2017-02-03"), meccs2.getDatum());
        meccs2.setHazaiGol(2);
        assertEquals(2, meccs2.getHazaiGol());
        meccs2.setVendegGol(3);
        assertEquals(3, meccs2.getVendegGol());
        assertEquals('V', meccs2.getGyozelem());
        meccs2.setHazaiGol(6);
        assertEquals('H', meccs2.getGyozelem());
        meccs2.setHazaiGol(3);
        assertEquals('D', meccs2.getGyozelem());
    }
}
