package pkg43_01_nagyfeladatfocibajnoksag;

import java.util.List;

public interface LabdarugobajnoksagDAO {

    boolean adatbazisLetrehoz(String adatbazisNev);

    boolean tablakLetrehoz(String adatbazisNev);

    boolean tabellaFeltolt(String adatbazisNev, List<String> csapatok);

    boolean adatbazisTorol(String adatbazisNev);
    
    boolean adatbazisTabellaModosit(String adatbazisNev, List<Csapat> tabella);
    
    boolean adatbazisMerkozesModosit(String adatbazisNev, List<Merkozes> meccs);

    List<Csapat> tabellaBetolt(String adatbazisNev);

    List<Merkozes> merkozesBetolt(String adatbazisNev);

}
