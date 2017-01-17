package Glam;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.TableCursor;
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
	Server s = new Server();
	private Text txtOra;
	ConnectionMySql con = new ConnectionMySql();
	String sql;
	ArrayList<Utente> elenco = new ArrayList<Utente>();
	ArrayList<Utente> uf = new ArrayList<Utente>();

	/**
	 * Launch the application.
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
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		try {
			con.Connection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		list = new List(shell, SWT.BORDER);
		list.setBounds(10, 10, 201, 242);
		
		elenco = con.u;
		
		for(int i=0;i<elenco.size();i++){
			list.add(elenco.get(i).nome+ " " +elenco.get(i).cognome );
		}
		
		Label lblData = new Label(shell, SWT.NONE);
		lblData.setAlignment(SWT.RIGHT);
		lblData.setBounds(234, 14, 55, 15);
		lblData.setText("Data:");
		
		DateTime dateTime = new DateTime(shell, SWT.BORDER);
		dateTime.setBounds(295, 10, 80, 24);
		
		Label lblOra = new Label(shell, SWT.NONE);
		lblOra.setAlignment(SWT.RIGHT);
		lblOra.setBounds(234, 43, 55, 15);
		lblOra.setText("Ora:");
		
		txtOra = new Text(shell, SWT.BORDER);
		txtOra.setBounds(295, 40, 80, 21);
		
		Button btnGet = new Button(shell, SWT.NONE);
		btnGet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				list.removeAll();
				java.text.DateFormat dfin = new SimpleDateFormat("yyyy-MM-dd");
				String data;
				data = dateTime.getYear() + "-" + dateTime.getMonth() + "-" + dateTime.getDay()+" "+txtOra.getText();
				System.out.println(data);
				sql =" SELECT * FROM `utente` WHERE `Data` < '"+data+"';";
				try {
					con.filtroOra(sql);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				uf = con.uf;
				for(int i=0;i<uf.size();i++){
					list.add(uf.get(i).nome+ " " +uf.get(i).cognome);
				}
			}
		});
		btnGet.setBounds(283, 89, 75, 25);
		btnGet.setText("Get");		
		
	}
}
