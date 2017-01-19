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
import java.util.Calendar;

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
		shlIscrizione.setSize(450, 300);
		shlIscrizione.setText("Iscrizione");
		
		txtNome = new Text(shlIscrizione, SWT.BORDER);
		txtNome.setBounds(10, 50, 112, 21);
		
		txtCognome = new Text(shlIscrizione, SWT.BORDER);
		txtCognome.setBounds(170, 50, 112, 21);
		
		Label lblNome = new Label(shlIscrizione, SWT.NONE);
		lblNome.setBounds(10, 27, 55, 15);
		lblNome.setText("Nome : ");
		
		Label lblCognome = new Label(shlIscrizione, SWT.NONE);
		lblCognome.setBounds(170, 29, 67, 15);
		lblCognome.setText("Cognome :");
		
		Label lblNewLabel = new Label(shlIscrizione, SWT.NONE);
		lblNewLabel.setBounds(29, 113, 253, 15);
		
		Button btnIscrivi = new Button(shlIscrizione, SWT.NONE);
		btnIscrivi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				nome = txtNome.getText();
				cognome = txtCognome.getText();
				String data;
				data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
				
				try{
					Socket s = new Socket("localhost", 9999);

					PrintWriter out = new PrintWriter(s.getOutputStream(), true); //manda al server
					
					InputStreamReader isr = new InputStreamReader(s.getInputStream());
					BufferedReader in = new BufferedReader(isr);
					
					out.println(nome);
					out.println(cognome);
					out.println(data);
					
					lblNewLabel.setText(in.readLine());
					
					s.close();
				}
				catch (IOException e1){
					e1.printStackTrace();
				}
			}
		});
		btnIscrivi.setBounds(326, 34, 75, 25);
		btnIscrivi.setText("Iscrivi");		

	}
}
