package m06.uf3.practica3.presentacio;

import java.util.ArrayList;

public class Interficie {

	public void presentation() {
		System.out.println("#######################################################");
		System.out.println("**********CONSULTING APP FOR \"Factbook.xml\"**********");
		System.out.println("#######################################################");
	}
	
	public void showFullList(String fullList) {
		System.out.println(fullList);
	}
	
	public void showMenu() {
		System.out.println("\nIntroduce your desired country | Or press 1 to see the country list again.");
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
