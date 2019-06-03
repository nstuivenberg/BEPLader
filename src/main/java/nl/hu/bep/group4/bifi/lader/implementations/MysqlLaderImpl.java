package nl.hu.bep.group4.bifi.lader.implementations;

import nl.hu.bep.group4.bifi.exceptions.GarbageDataException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    private Connection con = null;


    private Connection connectToMySQLDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Properties props = new Properties();
        InputStream input = null;
        try {
            props.load(input = new FileInputStream("src/config/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = props.getProperty("MYSQLUrl");
        String username =  props.getProperty("MySQLUsername");
        String password =  props.getProperty("MySQLPassword");
        con = DriverManager.getConnection(url,username,password);
        return con;
    }

    @Override
    public List<Adres> getAdressen(int klantId) throws SQLException, ClassNotFoundException {
        connectToMySQLDatabase();
        Statement stmt;
        ResultSet resultSet;
        String query = "select * from Adres where KlantID = " + klantId + " AND Type <> '" + ADRESFACTUURTYPE + "'";
        List<Adres> adressen = new ArrayList<>();

        stmt = con.createStatement();
        resultSet = stmt.executeQuery(query);

        while (resultSet.next()) {
            String straat = resultSet.getString("Straat");
            String huisnummer = resultSet.getString("huisnummer");
            String postcode = resultSet.getString("postcode");
            String plaats = resultSet.getString("plaats");
            String BiC = resultSet.getString("BIC");

            Adres adres = new Adres(straat, huisnummer, postcode, plaats, BiC);
            adressen.add(adres);
        }
        con.close();
        return adressen;

    }

    @Override
    public Klant getKlant(int klantId) throws SQLException, ClassNotFoundException {
        connectToMySQLDatabase();
        Statement stmt;
        ResultSet resultSet = null;
        Klant klant = null;
        String query = "select * from Klant where KlantID = " + klantId;

        stmt = con.createStatement();
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
        con.close();
        return klant;
    }

    @Override
    public List<Persoon> getPersonen(int klantId) throws SQLException, ClassNotFoundException {
        connectToMySQLDatabase();
        Statement stmt;
        ResultSet resultSet = null;
        String query = "select * from Persoon where KlantID = " + klantId;
        List<Persoon> personen = new ArrayList<>();

        stmt = con.createStatement();
        resultSet = stmt.executeQuery(query);

        while (resultSet.next()) {
            Integer persoonId = resultSet.getInt("PersoonID");
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

            Persoon persoon = new Persoon(persoonId, voornaam, achternaam, tussenvoegsel, telefoon, fax, convertedSex);
            personen.add(persoon);
        }
        con.close();
        return personen;
    }

    @Override
    public Adres getFactuurAdres(int klantId) throws SQLException, ClassNotFoundException, GarbageDataException {
        connectToMySQLDatabase();
        Statement stmt;
        ResultSet resultSet = null;

        String query = "select * from Adres where KlantID = " + klantId + " AND type = '" + ADRESFACTUURTYPE + "'";

        Adres factuurAdres = null;

        stmt = con.createStatement();
        resultSet = stmt.executeQuery(query);

        if (resultSet.getFetchSize() > 1) {
            throw new GarbageDataException("Meer dan 1 factuuradres voor klant " + klantId);
        }

        while (resultSet.next()) {
            String straat = resultSet.getString("Straat");
            String huisnummer = resultSet.getString("huisnummer");
            String postcode = resultSet.getString("postcode");
            String plaats = resultSet.getString("plaats");
            String BiC = resultSet.getString("BIC");

            factuurAdres = new Adres(straat, huisnummer, postcode, plaats, BiC);
        }
        con.close();
        return factuurAdres;
    }

    @Override
    public Persoon getPersoon(int persoonId) throws SQLException, ClassNotFoundException, GarbageDataException {
        connectToMySQLDatabase();
        Statement stmt;
        ResultSet resultSet = null;
        String query = "select * from Persoon where PersoonID = " + persoonId;

        stmt = con.createStatement();
        resultSet = stmt.executeQuery(query);
        Persoon persoon = null;
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
            persoon = new Persoon(persoonsId, voornaam, achternaam, tussenvoegsel, telefoon, fax, convertedSex);
        }
        return persoon;
    }
}