package nl.hu.bep.group4.bifi.lader.implementations;

import nl.hu.bep.group4.bifi.lader.MysqlLader;
import nl.hu.bep.group4.bifi.model.Adres;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;

import java.sql.* ;
import java.util.ArrayList;
import java.util.List;


public class MysqlLaderImpl implements MysqlLader {
    private Connection con = null;

    public Connection connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7292801", "sql7292801", "n2jfwIMeEa");

        }catch(Exception e){
            System.out.println(e);
        }
        return con;
    }

    public void getAllKlanten() throws SQLException {

        connectDatabase();
        Statement stmt = con.createStatement();

        ResultSet rs=stmt.executeQuery("select * from Klant");
        while(rs.next())
            System.out.println(rs.getInt("KlantID") +  "\t" +
                    rs.getString("Bedrijfsnaam") + "\t" +
                    rs.getString("Rechtsvorm"));
        con.close();
    }

	@Override
	public List<Adres> getAdres(int klantId) throws SQLException {
        connectDatabase();
        Statement stmt;
        ResultSet rs= null;
        String query = "select * from Adres where KlantID = " + klantId;
        List<Adres> adressen = new ArrayList<>();

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while(rs.next()) {
            try {
               String straat = rs.getString("Straat");
               String huisnummer = rs.getString("huisnummer");
               String postcode = rs.getString("postcode");
               String plaats = rs.getString("plaats");
               String BiC = rs.getString("BIC");
//             String Type = rs.getString("Type");

                Adres adres = new Adres(straat,huisnummer,postcode,plaats,BiC);
                System.out.println(straat + " " + huisnummer + " " + postcode + " " + plaats + " " + BiC);
                adressen.add(adres);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        con.close();
        return adressen;

    }

	@Override
	public Klant getKlant(int klantId) {
        connectDatabase();
        Statement stmt;
        ResultSet rs= null;
        String query = "select * from Klant where KlantID = " + klantId;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while(rs.next()) {
            try {
                String bedrijfsnaam = rs.getString("bedrijfsnaam");
                String rechtsvorm = rs.getString("rechtsvorm");
                String vAT = rs.getString("vAT");
                String bankrekeningNummer = rs.getString("bankrekeningNummer");
                String giroNummer = rs.getString("giroNummer");
                String biC = rs.getString("biC");
//                private List<Persoon> contactPersonen;
//                private List<Adres> adres;
//                private Adres factuurAdres;
                Klant klant = new Persoon(bedrijfsnaam,rechtsvorm,vAT,bankrekeningNummer,giroNummer,biC);
                System.out.println(bedrijfsnaam + " " + rechtsvorm + " " + vAT + " " + bankrekeningNummer + " " + giroNummer + " " + biC);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        con.close();
        return klant;
	}

	// moet deze niet maar 1 persoon terug geven?
	@Override
	public List<Persoon> getPersoon(int klantId) throws SQLException {
        connectDatabase();
        Statement stmt;
        ResultSet rs= null;
        String query = "select * from Persoon where KlantID = " + klantId;
        List<Persoon> personen = new ArrayList<>();

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while(rs.next()) {
            try {
                String voornaam = rs.getString("Voornaam");
                String tussenvoegsel = rs.getString("Tussenvoegsel");
                String achternaam = rs.getString("Achternaam");
                String telefoon = rs.getString("Telefoon");
                String fax = rs.getString("Fax");
//              String Geslacht = rs.getString("Geslacht");

                Persoon persoon = new Persoon(voornaam,achternaam,tussenvoegsel,telefoon,fax);
                System.out.println(voornaam + " " + achternaam + " " + tussenvoegsel + " " + telefoon + " " + fax);
                personen.add(persoon);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        con.close();
        return personen;
    }

}


