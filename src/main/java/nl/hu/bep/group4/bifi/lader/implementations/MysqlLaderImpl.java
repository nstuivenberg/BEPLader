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
    private String username;
    private String password;
    private String url;
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    private void initMysqlDatabaseConnection() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        setConfigVariables();
        con = DriverManager.getConnection(url, username, password);
        stmt = con.createStatement();
        rs = null;
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
        initMysqlDatabaseConnection();
        List<Adres> adressen = new ArrayList<>();
        
        try {
            String query = "select * from Adres where KlantID = " + klantId + " AND Type <> '" + ADRESFACTUURTYPE + "'";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String straat = rs.getString("Straat");
                String huisnummer = rs.getString("huisnummer");
                String postcode = rs.getString("postcode");
                String plaats = rs.getString("plaats");
                String biC = rs.getString("BIC");
                Adres adres = new Adres(straat, huisnummer, postcode, plaats, biC);
                adressen.add(adres);
            }
        }
        catch (SQLException e) {
        	closeEverything();
        	throw e;
        }
        finally {
        	closeEverything();
        }
        return adressen;
    }

    @Override
    public Klant getKlant(int klantId) throws SQLException, ClassNotFoundException, IOException {
        initMysqlDatabaseConnection();
        Klant klant = null;
        
        try {
        	String query = "select * from Klant where KlantID = " + klantId;
        	rs = stmt.executeQuery(query);
        	
        	while (rs.next()) {
                String bedrijfsnaam = rs.getString("Bedrijfsnaam");
                String rechtsvorm = rs.getString("rechtsvorm");
                String vAT = rs.getString("VAT");
                String bankrekeningNummer = rs.getString("BankRek");
                String giroNummer = rs.getString("Giro");
                String biC = rs.getString("BiK");
                List<Persoon> contactPersonen = new ArrayList<>();
                List<Adres> adres = new ArrayList<>();
                Adres factuurAdres = null;
                klant = new Klant(klantId, bedrijfsnaam, rechtsvorm, vAT, bankrekeningNummer, giroNummer, biC, contactPersonen, adres, factuurAdres);
            }
        }
        catch (SQLException e) {
        	closeEverything();
        	throw e;
        }
        finally {
        	closeEverything();
        }
        return klant;
    }

    @Override
    public List<Persoon> getPersonen(int klantId) throws SQLException, ClassNotFoundException, IOException {
        initMysqlDatabaseConnection();
        List<Persoon> personen = new ArrayList<>();

        try {
            String query = "select * from Persoon where KlantID = " + klantId;
            rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                personen.add(convertRowToPersoon(rs));
            }
        }
        catch (SQLException e) {
        	closeEverything();
        	throw e;
        }
        finally {
        	closeEverything();
        }
        return personen;
    }

    @Override
    public Adres getFactuurAdres(int klantId) throws SQLException, ClassNotFoundException, GarbageDataException, IOException {
        initMysqlDatabaseConnection();
        Adres factuurAdres = null;
        
        try {
            String query = "select * from Adres where KlantID = " + klantId + " AND type = '" + ADRESFACTUURTYPE + "'";
            rs = stmt.executeQuery(query);
            
            if (rs.getFetchSize() > 1) {
                throw new GarbageDataException("Meer dan 1 factuuradres voor klant " + klantId);
            }
            
            while (rs.next()) {
                String straat = rs.getString("Straat");
                String huisnummer = rs.getString("huisnummer");
                String postcode = rs.getString("postcode");
                String plaats = rs.getString("plaats");
                String biC = rs.getString("BIC");
                factuurAdres = new Adres(straat, huisnummer, postcode, plaats, biC);
            }
        }
        catch (SQLException e) {
        	closeEverything();
        	throw e;
        }
        finally {
        	closeEverything();
        }
        return factuurAdres;
    }

	private void closeEverything() throws SQLException {
		rs.close();
		stmt.close();
		con.close();
	}

    @Override
    public Persoon getPersoon(int persoonId) throws SQLException, ClassNotFoundException, GarbageDataException, IOException {
        initMysqlDatabaseConnection();
        Persoon persoon = null;

        try {
            String query = "select * from Persoon where PersoonID = " + persoonId;
            rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                persoon = convertRowToPersoon(rs);
            }
        }
        catch (SQLException e) {
        	closeEverything();
        	throw e;
        }
        finally {
        	closeEverything();
        }
        return persoon;
    }

	private Persoon convertRowToPersoon(ResultSet rs) throws SQLException {
		Persoon persoon;
		int persoonsId = rs.getInt("PersoonID");
		String voornaam = rs.getString("Voornaam");
		String tussenvoegsel = rs.getString("Tussenvoegsel");
		String achternaam = rs.getString("Achternaam");
		String telefoon = rs.getString("Telefoon");
		String fax = rs.getString("Fax");
		String geslacht = rs.getString("Geslacht");
		Persoon.Geslacht convertedSex = Persoon.Geslacht.VROUW;

		if (("0").equals(geslacht) || ("m").equalsIgnoreCase(geslacht)) {
		    convertedSex = Persoon.Geslacht.MAN;
		}
		
		persoon = new Persoon(persoonsId, voornaam, tussenvoegsel, achternaam, telefoon, fax, convertedSex);
		return persoon;
	}
}