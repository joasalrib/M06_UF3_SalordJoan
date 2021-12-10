package m06.uf3.practica3.negoci;

import java.util.Scanner;

import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.InfoDB;
import org.basex.core.cmd.Open;
import org.basex.core.cmd.XQuery;
import org.basex.query.QueryException;

public class Practica3 {
	private static Context context = new Context();
	private static String query;
	
	public static void main (String args[]) throws BaseXException, QueryException {
		
		System.out.println("#######################################################");
		System.out.println("**********CONSULTING APP FOR \"Factbook.xml\"**********");
		System.out.println("#######################################################");
		
		//System.out.println("Obre DB");
		//new CreateDB("factbook", "factbook.xml").execute(context);
		new Open("factbook").execute(context);
		//System.out.println(new InfoDB().execute(context));
		System.out.println(new XQuery("for $pais in //country return data($pais/name)").execute(context));


		getQuery();
		

		context.close();
	}
	
	private static void getQuery() {
		System.out.println("\nIntroduce your desired country | Or press 1 to see the country list again.");
		Scanner sc = new Scanner(System.in);
		query = sc.nextLine();
		
		
		//System.out.println("input; " + query);
		
		//System.out.println(query.equals("1"));
		
		try {
			if(query.equals("1")) {
				//System.out.println("yes");
				System.out.println(new XQuery("for $pais in //country return data($pais/name)").execute(context));
				getQuery();
			} else {
				query(query);
			}
		} catch (BaseXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.close();
	}
	
	private static void query(String query) throws QueryException, BaseXException{
		//System.out.println("query");
		
		//
		//context.close();
		
		if (new XQuery("for $pais in //country where $pais/name=\"" + query + "\" return $pais/name/text()").execute(context) == "") {
			System.out.println("The country does not exist, try again.");
			getQuery();
		}
		System.out.println("\n****This is your chosen country's data****");
		System.out.println("Name: " + new XQuery("for $pais in //country where $pais/name=\"" + query + "\" return $pais/name/text()").execute(context));
		
		if (new XQuery("for $pais in //country where $pais/name=\"" + query + "\" return data($pais/@inflation)").execute(context) != "") {
			System.out.println("La inflació és del: " + new XQuery("for $pais in //country where $pais/name=\"" + query + "\" return data($pais/@inflation)").execute(context) + "%");
		}
		System.out.println("The official religions of the country are: ");
		System.out.println(new XQuery("for $pais in //country where $pais/name=\"" + query + "\" return data($pais/religions)").execute(context));

		getQuery();
		
	}

}
