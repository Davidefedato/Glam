package Glam;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConnectionMySql {
	static Connection cn;
	static Statement st;
	static ResultSet rs;
	static int ru;
	static String sql;
	public ArrayList<Utente> u = new ArrayList<Utente>();
	public ArrayList<Utente> uf = new ArrayList<Utente>();

	public void Connection() throws SQLException {
		u.clear();

		// ________________________________connessione
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		} // fine try-catch

		cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/glam?user=root&password=");

		// SELECT Soci

		sql = "SELECT * FROM `utente`";

		try {
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next() == true) {
				u.add(new Utente(rs.getString("Nome"), rs.getString("Cognome"), rs.getString("Data")));
			}
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
		}

	}

	public void filtroOra(String sql) throws IOException {
		Connection cn;
		Statement st;
		// ________________________________connessione
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		} // fine try-catch

		try {
			// Creo la connessione al database
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/glam?user=root&password=");

			System.out.println(sql);
			// ________________________________query

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			try {
				st = cn.createStatement();
				rs = st.executeQuery(sql);
				while (rs.next() == true) {
					uf.add(new Utente(rs.getString("Nome"), rs.getString("Cognome"), rs.getString("Data")));
				}
			} catch (SQLException e) {
				System.out.println("errore:" + e.getMessage());
			}
			cn.close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
		} // fine try-catch
	}

	public void nuovoUtente(String sql) throws IOException {
		Connection cn;
		Statement st;
		// ________________________________connessione
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		} // fine try-catch

		try {
			// Creo la connessione al database
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/glam?user=root&password=");

			System.out.println(sql);
			// ________________________________query

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			st.execute(sql); // faccio la query su uno statement
			cn.close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
		} // fine try-catch

	}

}
