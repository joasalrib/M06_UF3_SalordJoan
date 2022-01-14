package m06.uf3.practica3.negoci;

import java.util.ArrayList;
import java.util.Scanner;

import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.InfoDB;
import org.basex.core.cmd.Open;
import org.basex.core.cmd.XQuery;
import org.basex.query.QueryException;

import m06.uf3.practica3.presentacio.Interficie;

public class Practica3 {
	private static Interficie interficie = new Interficie();
	private static Context context = new Context();
	private static String query;
	
	public static void main (String args[]) throws BaseXException, QueryException {
		
		interficie.presentation();
		
		new Open("factbook").execute(context);
		
		interficie.showFullList(new XQuery("for $pais in //country return data($pais/name)").execute(context));

		getQuery();

		context.close();
	}
	
	private static void getQuery() {
		interficie.showMenu();
		Scanner sc = new Scanner(System.in);
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
		sc.close();
	}
	
	private static void query(String query) throws QueryException, BaseXException{
		
		ArrayList<String> result = new ArrayList<String>();
		
		if (new XQuery("for $pais in //country where $pais/name=\"" + query + "\" return $pais/name/text()").execute(context) == "") {
			interficie.countryNotExist();
			getQuery();
		}
		
		result.add(query);
		
		result.add("Name: " + new XQuery("for $pais in //country where $pais/name=\"" + query + "\" return $pais/name/text()").execute(context));
		
		if (new XQuery("for $pais in //country where $pais/name=\"" + query + "\" return data($pais/@inflation)").execute(context) != "") {
			result.add("La inflació és del: " + new XQuery("for $pais in //country where $pais/name=\"" + query + "\" return data($pais/@inflation)").execute(context) + "%");
		}
		result.add("The official religions of the country are: \n" + new XQuery("for $pais in //country where $pais/name=\"" + query + "\" return data($pais/religions)").execute(context));
		interficie.showSelectedCountry(result);
		
		getQuery();
		
	}

}
