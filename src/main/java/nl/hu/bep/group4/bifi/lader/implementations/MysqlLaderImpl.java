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
        return adressen;

    }

	@Override
	public Klant getKlant(int klantId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Persoon> getPersoon(int klantId) {
		// TODO Auto-generated method stub
		return null;
	}


}


