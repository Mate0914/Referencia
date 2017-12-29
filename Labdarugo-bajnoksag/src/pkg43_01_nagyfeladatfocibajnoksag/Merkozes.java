package pkg43_01_nagyfeladatfocibajnoksag;

import java.sql.Date;

public class Merkozes {

    private final int ID;
    private Date datum;
    private final int hazai_ID;
    private final int vendeg_ID;
    private int hazaiGol;
    private int vendegGol;
    private char gyozelem;
    private final Csapat hazaiCsapat;
    private final Csapat vednegCsapat;

    public Merkozes(int ID, Date datum, int hazai_ID, int vendeg_ID, int hazaiGol, int vendegGol, Csapat hazaiCsapat, Csapat vendegCsapat) {
        this.ID = ID;
        this.datum = datum;
        this.hazai_ID = hazai_ID;
        this.vendeg_ID = vendeg_ID;
        this.hazaiGol = hazaiGol;
        this.vendegGol = vendegGol;
        if (hazaiGol > vendegGol) {
            this.gyozelem = 'H';
        } else if (hazaiGol < vendegGol) {
            this.gyozelem = 'V';
        } else {
            this.gyozelem = 'D';
        }
        this.hazaiCsapat = hazaiCsapat;
        this.vednegCsapat = vendegCsapat;
    }

    public int getID() {
        return this.ID;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Date getDatum() {
        return this.datum;
    }

    public int getHazai_ID() {
        return this.hazai_ID;
    }

    public int getVendeg_ID() {
        return this.vendeg_ID;
    }

    public void setHazaiGol(int hazaiGol) {
        if (hazaiGol > this.vendegGol) {
            this.gyozelem = 'H';
        } else if (hazaiGol < this.vendegGol) {
            gyozelem = 'V';
        } else {
            gyozelem = 'D';
        }
        this.hazaiGol = hazaiGol;
    }

    public int getHazaiGol() {
        return this.hazaiGol;
    }

    public void setVendegGol(int vendegGol) {
        if (this.hazaiGol > vendegGol) {
            this.gyozelem = 'H';
        } else if (this.hazaiGol < vendegGol) {
            gyozelem = 'V';
        } else {
            gyozelem = 'D';
        }
        this.vendegGol = vendegGol;
    }

    public int getVendegGol() {
        return this.vendegGol;
    }

    public char getGyozelem() {
        return this.gyozelem;
    }

    public Csapat getHazaiCsapat() {
        return this.hazaiCsapat;
    }
    
    public Csapat getVendegCsapat() {
        return this.vednegCsapat;
    }
}
