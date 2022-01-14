package m06.uf3.practica3.negoci;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.basex.api.client.ClientQuery;
import org.basex.api.client.ClientSession;
import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.InfoDB;
import org.basex.core.cmd.Open;
import org.basex.core.cmd.XQuery;
import org.basex.query.QueryException;

import m06.uf3.practica3.presentacio.Interficie;

public class Practica3_2 {
	private static Interficie interficie = new Interficie();
	private static Context context = new Context();
	private static String query;
	private static ClientSession session;
	
	public static void main (String args[]) throws QueryException, IOException {
		
		interficie.presentation();
		
		session = new ClientSession("localhost", 1984, "admin", "admin");
		
		session.execute(new Open("factbook"));
		
		getFullList();

		getQuery();

		context.close();
	}
	
	public static void getFullList() throws IOException {
		interficie.setFullList(session.execute(new XQuery("for $pais in //country return data($pais/name)")));
	}
	
	private static void getQuery() throws BaseXException, QueryException {
		interficie.showMenu();
		/*Scanner sc = new Scanner(System.in);
		query = sc.nextLine();
		String a = query.substring(0,1);
		query = query.substring(1, query.length());
		query = a.toUpperCase() + query;

		try {
			if(query.equals("1")) {				
				interficie.showFullList(new XQuery("for $pais in //country return data($pais/name)").execute(context));
				getQuery();
			} else {
				query(query);
			}
		} catch (BaseXException e) {
			e.printStackTrace();
		} catch (QueryException e) {
			e.printStackTrace();
		}
		sc.close();*/
		
		query = interficie.getUserQuery();
		try {
			query(query);
		} catch (QueryException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void query(String query) throws QueryException, IOException{
		
		String bindingPrep = "declare variable $country external;"
				+ "for $pais in //country where $pais/name= $country return $pais/name/text()";
		ClientQuery test = session.query(bindingPrep);
		//ClientQuery test = query.bind("$cuontry", query);
		test.bind("$country", query);
		ArrayList<String> result = new ArrayList<String>();
		
		//String pais = session.execute(new XQuery("for $pais in //country where $pais/name=\"" + query + "\" return $pais/name/text()"));
		String executedQuery = test.execute();
		if (executedQuery == "") {
			
			interficie.countryNotExist();
			getQuery();
		}
		
		result.add(query);
		
		//------------------------------------------------------------------------------------------
		
		bindingPrep = "declare variable $country external;"
				+ "for $pais in //country where $pais/name= $country return $pais/name/text()";
		test = session.query(bindingPrep);
		test.bind("$country", query);
		executedQuery = test.execute();

		result.add("Name: " + executedQuery);
		
		//------------------------------------------------------------------------------------------
		
		bindingPrep = "declare variable $country external;"
				+ "for $pais in //country where $pais/name= $country return data($pais/@inflation)";
		test = session.query(bindingPrep);
		test.bind("$country", query);
		executedQuery = test.execute();
		
		if (executedQuery != "") {
			result.add("La inflació és del: " + executedQuery + "%");
		}
		
		//------------------------------------------------------------------------------------------
		
		bindingPrep = "declare variable $country external;"
				+ "for $pais in //country where $pais/name= $country return data($pais/religions)";
		test = session.query(bindingPrep);
		test.bind("$country", query);
		executedQuery = test.execute();
		
		result.add("The official religions of the country are: \n" + executedQuery);
		interficie.showSelectedCountry(result);
		
		getQuery();
		
	}

}
