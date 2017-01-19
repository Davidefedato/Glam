package Glam;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class GraficaServer {

	protected Shell shell;
	List list;
	Server s;
	private Text txtOra;
	ConnectionMySql con = new ConnectionMySql();
	String sql;
	ArrayList<Utente> elenco = new ArrayList<Utente>();
	ArrayList<Utente> ufiltro = new ArrayList<Utente>();

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GraficaServer window = new GraficaServer();
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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		Thread t = new Thread(new Server(this));
		t.start();
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");

		list = new List(shell, SWT.BORDER);

		list.setBounds(10, 10, 201, 242);

		System.out.println(elenco.size());

		Label lblData = new Label(shell, SWT.NONE);
		lblData.setAlignment(SWT.RIGHT);
		lblData.setBounds(272, 14, 41, 15);
		lblData.setText("Data:");

		DateTime dateTime = new DateTime(shell, SWT.BORDER);
		dateTime.setBounds(320, 10, 80, 24);
		


		Label lblOra = new Label(shell, SWT.NONE);
		lblOra.setAlignment(SWT.RIGHT);
		lblOra.setBounds(283, 43, 30, 15);
		lblOra.setText("Ora:");

		txtOra = new Text(shell, SWT.BORDER);
		txtOra.setBounds(320, 40, 80, 21);
		
		Button btnRecupera = new Button(shell, SWT.NONE);
		btnRecupera.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				list.removeAll();
				String data;
				data = dateTime.getYear() + "-" + dateTime.getMonth() + "-" + dateTime.getDay() + " "
						+ txtOra.getText();
				System.out.println(data);
				sql = " SELECT * FROM `utente` WHERE `Data` < '" + data + "';";
				try {
					con.filtroOra(sql);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				ufiltro = con.ufiltro;
				for (int i = 0; i < ufiltro.size(); i++) {
					list.add(ufiltro.get(i).nome + " " + ufiltro.get(i).cognome);
				}
			}
		});
		btnRecupera.setBounds(325, 81, 75, 25);
		btnRecupera.setText("Recupera");	

	}

	public void updateElenco(ArrayList<Utente> a) {
		elenco = a;
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				list.removeAll();
				for (int i = 0; i < elenco.size(); i++) {
					list.add(elenco.get(i).nome + " " + elenco.get(i).cognome);
				}
			}
		});
	}
}
