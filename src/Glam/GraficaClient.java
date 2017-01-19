package Glam;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class GraficaClient {

	protected Shell shlIscrizione;
	private Text txtNome;
	private Text txtCognome;
	private String nome;
	private String cognome;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GraficaClient window = new GraficaClient();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlIscrizione.open();
		shlIscrizione.layout();
		while (!shlIscrizione.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlIscrizione = new Shell();
		shlIscrizione.setSize(431, 152);
		shlIscrizione.setText("Iscrizione");

		txtNome = new Text(shlIscrizione, SWT.BORDER);
		txtNome.setBounds(10, 31, 112, 21);

		txtCognome = new Text(shlIscrizione, SWT.BORDER);
		txtCognome.setBounds(170, 31, 112, 21);

		Label lblNome = new Label(shlIscrizione, SWT.NONE);
		lblNome.setBounds(10, 10, 55, 15);
		lblNome.setText("Nome : ");

		Label lblCognome = new Label(shlIscrizione, SWT.NONE);
		lblCognome.setBounds(170, 10, 67, 15);
		lblCognome.setText("Cognome :");

		Label lblNewLabel = new Label(shlIscrizione, SWT.NONE);
		lblNewLabel.setBounds(29, 78, 253, 15);

		Button btnIscrivi = new Button(shlIscrizione, SWT.NONE);
		btnIscrivi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!txtNome.getText().isEmpty() || !txtCognome.getText().isEmpty()){
					try {
						Socket s = new Socket("localhost", 9999);

						nome = txtNome.getText();
						cognome = txtCognome.getText();
						String data;
						String messaggio = "";
						data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

						PrintWriter out = new PrintWriter(s.getOutputStream(), true); // manda
																						// al
																						// server

						InputStreamReader isr = new InputStreamReader(s.getInputStream());
						BufferedReader in = new BufferedReader(isr);

						out.println(nome);
						out.println(cognome);
						out.println(data);

						messaggio = in.readLine();
						lblNewLabel.setText(messaggio);

						if (messaggio.equals("Iscrizione riuscita!")) {
							txtNome.setText("");
							txtCognome.setText("");
						}

						s.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					  JOptionPane.showMessageDialog(null, "Campi obbligatori non compilati!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnIscrivi.setBounds(327, 29, 75, 25);
		btnIscrivi.setText("Iscrivi");

	}
}
