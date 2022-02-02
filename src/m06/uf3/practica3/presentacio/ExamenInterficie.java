package m06.uf3.practica3.presentacio;

public class ExamenInterficie {
	
	private String fullList;

	public void menu() {
		System.out.println("M06 Exam");
		System.out.println("1. Consulta BD incrustada");
		System.out.println("2. Consulta a BD en servidor");
		System.out.println("3. Exit");
	}
	
	public void setFullList(String fullList) {
		this.fullList = fullList;
		showFullList();
	}
	
	public void showFullList() {
		System.out.println(this.fullList);
	}
	
	public void showStep() {
		System.out.println("Introdueix el nom d'una carrera: ");
	}
	
	public void show(String print) {
		System.out.println(print);
	}
	
	public void notExist() {
		System.out.println("La carrera no existe");
	}
}
