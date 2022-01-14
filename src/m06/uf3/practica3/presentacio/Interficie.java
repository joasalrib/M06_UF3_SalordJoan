package m06.uf3.practica3.presentacio;

import java.util.ArrayList;
import java.util.Scanner;

import org.basex.core.BaseXException;
import org.basex.core.cmd.XQuery;
import org.basex.query.QueryException;


public class Interficie {
	
	private String query;
	private String userQuery;
	private String fullList;

	public void presentation() {
		System.out.println("#######################################################");
		System.out.println("**********CONSULTING APP FOR \"Factbook.xml\"**********");
		System.out.println("#######################################################");
	}
	
	public void setFullList(String fullList) {
		this.fullList = fullList;
		showFullList();
	}
	
	public void showFullList() {
		System.out.println(this.fullList);
	}
	
	public void showMenu() {
		System.out.println("\nIntroduce your desired country | Or press 1 to see the country list again.");
		userQuery();
	}
	
	public void userQuery() {
		Scanner sc = new Scanner(System.in);
		query = sc.nextLine();
		String a = query.substring(0,1);
		query = query.substring(1, query.length());
		query = a.toUpperCase() + query;
		
		if(query.equals("1")) {				
			//interficie.showFullList(new XQuery("for $pais in //country return data($pais/name)").execute(context));
			//getQuery();
			showFullList();
			showMenu();
		} else {
			userQuery = query;
			//query(query);
		}
	}
	
	public String getUserQuery() {
		return userQuery;
	}
	
	public void countryNotExist() {
		System.out.println("The country does not exist, try again.");
	}
	
	public void showSelectedCountry(ArrayList<String> result) {
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i));
		}
	}
}
