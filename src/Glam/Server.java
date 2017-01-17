package Glam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	public static void main(String[] args) {
		String nomeRicevuto;
		String cognomeRicevuto;
		String doRicevuto;
		String sql;
		ArrayList<Utente> elenco = new ArrayList<Utente>();
		ConnectionMySql con = new ConnectionMySql();

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
							"Nome : " + nomeRicevuto + "Cognome : " + cognomeRicevuto + "Data : " + doRicevuto);

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
