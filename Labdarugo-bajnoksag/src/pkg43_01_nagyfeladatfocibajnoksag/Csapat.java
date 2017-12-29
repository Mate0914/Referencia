package pkg43_01_nagyfeladatfocibajnoksag;

public class Csapat {

    private int ID;
    private String csapatnev;
    private int lottGol;
    private int kapottGol;
    private int pont;

    public Csapat(){
        
    }
    
    public Csapat(int ID, String csapatnev, int lottGol, int kapottGol, int pont) {
        this.ID = ID;
        this.csapatnev = csapatnev;
        this.lottGol = lottGol;
        this.kapottGol = kapottGol;
        this.pont = pont;
    }

    public int getID() {
        return this.ID;
    }

    public void setCsapatnev(String csapatnev) {
        this.csapatnev = csapatnev;
    }

    public String getCsapatnev() {
        return this.csapatnev;
    }

    public void setLottGol(int lottGol) {
        this.lottGol = lottGol;
    }

    public int getLottGol() {
        return this.lottGol;
    }

    public void setKapottGol(int kapottGol) {
        this.kapottGol = kapottGol;
    }

    public int getKapottGol() {
        return this.kapottGol;
    }

    public void setPont(int pont) {
        this.pont = pont;
    }

    public int getPont() {
        return this.pont;
    }
    
}
