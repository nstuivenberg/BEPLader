package nl.hu.bep.group4.bifi.lader.implementations;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nl.hu.bep.group4.bifi.lader.MysqlLader;
import nl.hu.bep.group4.bifi.model.Adres;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class MysqlLaderImpl implements MysqlLader {

    private static final String ADRESFACTUURTYPE = "F";
    Persoon persoon = null;

    private Connection con = null;
    private String username;
    private String password;
    private String url;

    private Connection connectToMySQLDatabase() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        setConfigVariables();
        con = DriverManager.getConnection(url, username, password);
        return con;
    }

    private void setConfigVariables() throws IOException {
        if (System.getenv("BEP_MySQLUsername") != null) {
            this.username = System.getenv("BEP_MySQLUsername");
            this.password = System.getenv("BEP_MySQLPassword");
            this.url = System.getenv("BEP_MYSQLUrl");
        } else {
            Properties props = new Properties();
            props.load(new FileInputStream("src/config/config.properties"));
            
            this.url = props.getProperty("MYSQLUrl");
            this.username = props.getProperty("MySQLUsername");
            this.password = props.getProperty("MySQLPassword");
        }
    }

    @Override
    public List<Adres> getAdressen(int klantId) throws SQLException, ClassNotFoundException, IOException {
        connectToMySQLDatabase();
        Statement stmt;
        ResultSet resultSet;
        List<Adres> adressen = new ArrayList<>();
        
        try {
        	stmt = con.createStatement();
            String query = "select * from Adres where KlantID = " + klantId + " AND Type <> '" + ADRESFACTUURTYPE + "'";
            resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                String straat = resultSet.getString("Straat");
                String huisnummer = resultSet.getString("huisnummer");
                String postcode = resultSet.getString("postcode");
                String plaats = resultSet.getString("plaats");
                String biC = resultSet.getString("BIC");
                Adres adres = new Adres(straat, huisnummer, postcode, plaats, biC);
                adressen.add(adres);
            }
        }
        catch (SQLException e) {
        	throw e;
        }
        finally {
        	if (con != null) {
                con.close();
        	}
        }
        return adressen;
    }

    @Override
    public Klant getKlant(int klantId) throws SQLException, ClassNotFoundException, IOException {
        connectToMySQLDatabase();
        Statement stmt;
        ResultSet resultSet = null;
        Klant klant = null;
        
        try {
        	stmt = con.createStatement();
        	String query = "select * from Klant where KlantID = " + klantId;
        	resultSet = stmt.executeQuery(query);
        	
        	while (resultSet.next()) {
                String bedrijfsnaam = resultSet.getString("Bedrijfsnaam");
                String rechtsvorm = resultSet.getString("rechtsvorm");
                String vAT = resultSet.getString("VAT");
                String bankrekeningNummer = resultSet.getString("BankRek");
                String giroNummer = resultSet.getString("Giro");
                String biC = resultSet.getString("BiK");
                List<Persoon> contactPersonen = new ArrayList<>();
                List<Adres> adres = new ArrayList<>();
                Adres factuurAdres = null;
                klant = new Klant(klantId, bedrijfsnaam, rechtsvorm, vAT, bankrekeningNummer, giroNummer, biC, contactPersonen, adres, factuurAdres);
            }
        }
        catch (SQLException e) {
        	throw e;
        }
        finally {
        	if (con != null) {
                con.close();
        	}
        }
        return klant;
    }

    @Override
    public List<Persoon> getPersonen(int klantId) throws SQLException, ClassNotFoundException, IOException {
        connectToMySQLDatabase();
        Statement stmt;
        ResultSet resultSet = null;
        List<Persoon> personen = new ArrayList<>();

        try {
        	stmt = con.createStatement();
            String query = "select * from Persoon where KlantID = " + klantId;
            resultSet = stmt.executeQuery(query);
            
            while (resultSet.next()) {
                int persoonsId = resultSet.getInt("PersoonID");
                String voornaam = resultSet.getString("Voornaam");
                String tussenvoegsel = resultSet.getString("Tussenvoegsel");
                String achternaam = resultSet.getString("Achternaam");
                String telefoon = resultSet.getString("Telefoon");
                String fax = resultSet.getString("Fax");
                String geslacht = resultSet.getString("Geslacht");
                Persoon.Geslacht convertedSex = Persoon.Geslacht.VROUW;

                if (("0").equals(geslacht) || ("m").equalsIgnoreCase(geslacht)) {
                    convertedSex = Persoon.Geslacht.MAN;
                }

                Persoon persoon = new Persoon(persoonsId, voornaam, tussenvoegsel, achternaam, telefoon, fax, convertedSex);
                personen.add(persoon);
            }
        }
        catch (SQLException e) {
        	throw e;
        }
        finally {
        	if (con != null) {
                con.close();
        	}
        }
        return personen;
    }

    @Override
    public Adres getFactuurAdres(int klantId) throws SQLException, ClassNotFoundException, GarbageDataException, IOException {
        connectToMySQLDatabase();
        Statement stmt;
        ResultSet resultSet = null;
        Adres factuurAdres = null;
        
        try {
        	stmt = con.createStatement();
            String query = "select * from Adres where KlantID = " + klantId + " AND type = '" + ADRESFACTUURTYPE + "'";
            resultSet = stmt.executeQuery(query);
            
            if (resultSet.getFetchSize() > 1) {
                throw new GarbageDataException("Meer dan 1 factuuradres voor klant " + klantId);
            }
            
            while (resultSet.next()) {
                String straat = resultSet.getString("Straat");
                String huisnummer = resultSet.getString("huisnummer");
                String postcode = resultSet.getString("postcode");
                String plaats = resultSet.getString("plaats");
                String biC = resultSet.getString("BIC");
                factuurAdres = new Adres(straat, huisnummer, postcode, plaats, biC);
            }
        }
        catch (SQLException e) {
        	throw e;
        }
        finally {
        	if (con != null) {
                con.close();
        	}
        }
        return factuurAdres;
    }

    @Override
    public Persoon getPersoon(int persoonId) throws SQLException, ClassNotFoundException, GarbageDataException, IOException {
        connectToMySQLDatabase();
        Statement stmt;
        ResultSet resultSet = null;

        try {
        	stmt = con.createStatement();
            String query = "select * from Persoon where PersoonID = " + persoonId;
            resultSet = stmt.executeQuery(query);
            
            if (resultSet.getFetchSize() > 1) {
                throw new GarbageDataException("Meer dan 1 Persoon voor PersoonId" + persoonId);
            }
            
            while (resultSet.next()) {
                int persoonsId = resultSet.getInt("PersoonID");
                String voornaam = resultSet.getString("Voornaam");
                String tussenvoegsel = resultSet.getString("Tussenvoegsel");
                String achternaam = resultSet.getString("Achternaam");
                String telefoon = resultSet.getString("Telefoon");
                String fax = resultSet.getString("Fax");
                String geslacht = resultSet.getString("Geslacht");
                Persoon.Geslacht convertedSex = Persoon.Geslacht.VROUW;

                if (("0").equals(geslacht) || ("m").equalsIgnoreCase(geslacht)) {
                    convertedSex = Persoon.Geslacht.MAN;
                }
                
                persoon = new Persoon(persoonsId, voornaam, tussenvoegsel, achternaam, telefoon, fax, convertedSex);
            }
        }
        catch (SQLException e) {
        	throw e;
        }
        finally {
        	if (con != null) {
                con.close();
        	}
        }
        return persoon;
    }
}