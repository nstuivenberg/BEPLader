package nl.hu.bep.group4.bifi.lader.implementations;

import nl.hu.bep.group4.bifi.lader.MysqlLader;

import java.sql.* ;


public class MysqlLaderImpl implements MysqlLader {

    public void connectDatabase() {

        try {
            System.out.println("Class forname");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Trying to get connection");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7292801", "sql7292801", "n2jfwIMeEa");

            Statement stmt = con.createStatement();

            ResultSet rs=stmt.executeQuery("select * from Klant");
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }
}

