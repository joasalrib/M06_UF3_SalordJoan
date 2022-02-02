package m06.uf3.practica3.negoci;

import java.io.IOException;
import java.util.Scanner;

import org.basex.api.client.ClientQuery;
import org.basex.api.client.ClientSession;
import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.Open;
import org.basex.core.cmd.XQuery;

import m06.uf3.practica3.presentacio.ExamenInterficie;

public class Examen {
	private static ExamenInterficie interficie = new ExamenInterficie();
	private static Context context = new Context();
	private static ClientSession session;
	
	public static void main (String args[]) throws IOException {
		menu();
	}
	
	public static void menu() throws IOException {
		interficie.menu();
		try {
			start();
		} catch (BaseXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void start() throws IOException {
		Scanner sc = new Scanner(System.in);
		String result = sc.nextLine();
		sc.close();
		
		switch (result) {
			case "1":
				try {
					new Open("universitats-v2").execute(context);
				} catch (BaseXException e) {
					e.printStackTrace();
				}
				getFullList();
			case "2":
				
				session = new ClientSession("localhost", 1984, "admin", "admin");
				session.execute(new Open("factbook"));
				
				getFullListServer();
				
			case "3":
				System.exit(0);
		}
	}
	
	public static void getFullListServer() throws IOException {
		interficie.setFullList(session.execute(new XQuery("//carrera/nombre/text()")));
		interficie.showStep();
		Scanner sc = new Scanner(System.in);
		String result = sc.nextLine();
		getQueryServer(result);
		menu();
	}
	
	public static void getQueryServer(String result) throws IOException {
		interficie.show(session.execute(new XQuery("//carrera[nombre='" + result + "']/centro/text()")));
		String bindingPrep = "declare variable $name external;"
				+ "f//carrera/$nombre/text()";
		ClientQuery test = session.query(bindingPrep);
		test.bind("$country", result);
		String executedQuery = test.execute();
		
		if (executedQuery == "") {
			interficie.notExist();
			menu();
		} else {
			interficie.show(executedQuery);
		}
	}
	
	public static void getFullList() throws IOException {
		interficie.setFullList(new XQuery("//carrera/nombre/text()").execute(context));
		interficie.showStep();
		Scanner sc = new Scanner(System.in);
		String result = sc.nextLine();
		getQuery(result);
		menu();
	}
	
	public static void getQuery(String result) throws IOException {
		String query = new XQuery("//carrera[nombre='" + result + "']/centro/text()").execute(context);
		if (query != "") {
			interficie.show(query);
		} else {
			getFullList();
		}
	}
}
