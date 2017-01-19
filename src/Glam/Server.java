package Glam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
	
	public static ArrayList<Utente> elenco = new ArrayList<Utente>();

	public static void main(String[] args) {
		String nomeRicevuto;
		String cognomeRicevuto;
		String doRicevuto;
		String sql;
		ConnectionMySql con = new ConnectionMySql();
		
		try {
			con.Connection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		elenco = con.u;

		// riceva una connessione

		try {
			ServerSocket ss = new ServerSocket(9999);
			while (true) {
				Socket s = ss.accept();

				PrintWriter out = new PrintWriter(s.getOutputStream(), true);

				InputStreamReader isr = new InputStreamReader(s.getInputStream());
				BufferedReader in = new BufferedReader(isr);

				nomeRicevuto = in.readLine();
				cognomeRicevuto = in.readLine();
				doRicevuto = in.readLine();

				boolean esiste = false;
				System.out.println(elenco.size());

				for (int i = 0; i < elenco.size(); i++) {
					if (elenco.get(i).nome.equals(nomeRicevuto)) {
						esiste = true;
					}
				}
				if (esiste == false) {
					elenco.add(new Utente(nomeRicevuto, cognomeRicevuto, doRicevuto));
					out.println("Iscrizione riuscita!");
					
					sql = "INSERT INTO `utente` (`ID`, `Nome`, `Cognome`, `Data`) VALUES (NULL, '" + nomeRicevuto
							+ "', '" + cognomeRicevuto + "', '" + doRicevuto + "');";
					con.nuovoUtente(sql);
					System.out.println(
							"Nome : " + nomeRicevuto + " Cognome : " + cognomeRicevuto + " Data : " + doRicevuto);

				} else {
					out.println("Già iscritto!");
				}

				s.close();
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
