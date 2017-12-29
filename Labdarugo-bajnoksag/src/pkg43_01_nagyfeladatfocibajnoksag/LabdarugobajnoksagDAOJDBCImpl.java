package pkg43_01_nagyfeladatfocibajnoksag;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

public class LabdarugobajnoksagDAOJDBCImpl implements LabdarugobajnoksagDAO {

    @Override
    public boolean adatbazisLetrehoz(String adatbazisNev) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "1234");
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE labdarugas" + adatbazisNev);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean tablakLetrehoz(String adatbazisNev) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/labdarugas" + adatbazisNev, "root", "1234");
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE Tabella "
                    + "(ID INTEGER not NULL, "
                    + " Csapatnév VARCHAR(45), "
                    + " Lőtt INTEGER, "
                    + " Kapott INTEGER, "
                    + " Pont INTEGER, "
                    + " PRIMARY KEY ( ID ))");
            stmt.executeUpdate("CREATE TABLE Merkozes "
                    + "(ID INTEGER not NULL, "
                    + " Dátum DATE, "
                    + " HazaiID INTEGER, "
                    + " VendégID INTEGER, "
                    + " HazaiGól INTEGER, "
                    + " VendégGól INTEGER, "
                    + " Győzelem VARCHAR(255), "
                    + " PRIMARY KEY ( ID ), "
                    + " FOREIGN KEY (HazaiID) REFERENCES Tabella ( ID ), "
                    + "FOREIGN KEY (VendégID) REFERENCES Tabella ( ID ))");
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean tabellaFeltolt(String adatbazisNev, List<String> csapatok) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/labdarugas" + adatbazisNev, "root", "1234");
                Statement stmt = conn.createStatement()) {
            int id = 1;
            for (int i = 0; i < csapatok.size(); i++) {
                stmt.executeUpdate("INSERT INTO labdarugas" + adatbazisNev + ".tabella(ID, Csapatnév, Lőtt, Kapott, Pont) VALUES(" + id + ",'" + csapatok.get(i) + "',0,0,0)");
                id++;
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean adatbazisTorol(String adatbazisNev) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "1234");
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP DATABASE labdarugas" + adatbazisNev);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean adatbazisTabellaModosit(String adatbazisNev, List<Csapat> tabella) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/labdarugas" + adatbazisNev, "root", "1234");
                Statement stmt = conn.createStatement()) {

            for (Csapat tab : tabella) {
                stmt.executeUpdate("UPDATE tabella SET Lőtt = " + tab.getLottGol() + ", Kapott = " + tab.getKapottGol() + ", Pont = " + tab.getPont()
                        + " WHERE ID = " + tab.getID() + ";");
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean adatbazisMerkozesModosit(String adatbazisNev, List<Merkozes> meccs) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/labdarugas" + adatbazisNev, "root", "1234");
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM merkozes");
            int id = 1;
            for (Merkozes me : meccs) {
                String datum = me.getDatum().toString().replace("-", "");
                stmt.executeUpdate("INSERT INTO merkozes (ID, Dátum, HazaiID, VendégID, HazaiGól, VendégGól, Győzelem) VALUES (" + id + ", " + datum + ", " + me.getHazai_ID() + ", " + me.getVendeg_ID() + ", " + me.getHazaiGol() + ", " + me.getVendegGol() + ", '" + me.getGyozelem() + "')");
                id++;
            }
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public List<Csapat> tabellaBetolt(String adatbazisNev) {
        List<Csapat> tabellaList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/labdarugas" + adatbazisNev, "root", "1234");
                Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM tabella");
            while (rs.next()) {
                Csapat csapat = new Csapat(rs.getInt("ID"), rs.getString("Csapatnév"), rs.getInt("Lőtt"), rs.getInt("Kapott"), rs.getInt("Pont"));
                tabellaList.add(csapat);
            }
            return tabellaList;
        } catch (SQLException ex) {
            return tabellaList;
        }
    }

    @Override
    public List<Merkozes> merkozesBetolt(String adatbazisNev) {
        List<Merkozes> meccsList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/labdarugas" + adatbazisNev, "root", "1234");
                Statement stmt = conn.createStatement()) {
            
            List<Csapat> csapat = new ArrayList<>();
            csapat = tabellaBetolt(adatbazisNev);
            ResultSet rs = stmt.executeQuery("SELECT * FROM merkozes");
            while (rs.next()) {
                Csapat hazai = csapat.get(rs.getInt("HazaiID") - 1);
                Csapat vendeg = csapat.get(rs.getInt("VendégID") - 1);
                Merkozes meccs = new Merkozes(rs.getInt("ID"), rs.getDate("Dátum"), rs.getInt("HazaiID"), rs.getInt("VendégID"), rs.getInt("HazaiGól"), rs.getInt("VendégGól"), hazai, vendeg);
                meccsList.add(meccs);
            }
            return meccsList;
        } catch (SQLException ex) {
            return meccsList;
        }
    }

}
