package model;

import java.util.ArrayList;

/**
 * Klasa/baza danych hase� wykorzystywanych w grze.
 */
public class PasswordDataBase {
	
	private String[] passwords = new String[10];
	private ArrayList<String> usedPasswords = new ArrayList<String>();
	
	/**
	 * Konstruktor klasy PasswordDataBase ustawiaj�cy has�a.
	 */
	public PasswordDataBase() {
		
		passwords[0]="  Darowanemu  /koniowi w z�by/   si� nie    /   zagl�da    ";
		passwords[1]="Nie chwal dnia/przed zachodem/    s�o�ca    /              ";
		passwords[2]="Lepszy wr�bel / w gar�ci ni� /go��b na dachu/              ";
		passwords[3]="  Co ci� nie  /  zabije to   /  ci� wzmocni /              ";
		passwords[4]=" Czego Ja� si�/  nie nauczy  / tego Jan nie / b�dzie umia� ";
		passwords[5]="  Gdzie dw�ch /   si� bije   /  tam trzeci  /   korzysta   ";
		passwords[6]=" Kogut my�la� /  o niedzieli /  a w sobot�  /  �eb uci�li  ";
		passwords[7]="  Prawdziwych /  przyjaci�  /  poznaje si� /   w biedzie  ";
		passwords[8]="   Wszystko   / dobre co si� /dobrze ko�czy /              ";
		passwords[9]="   Wymieni�   /    stryjek   /   siekierk�  /   na kijek   ";
		
	}

	/**
	 * Metoda losuj�ca i zwracaj�ca wylosowane has�o.
	 * @return - zwracane has�o
	 */
	public String getPassword() {
		
		int password;
		
		do
		{
			password = (int) Math.floor((Math.random()*10));
		}
		
		while (usedPasswords.contains(passwords[password]));
			
		return passwords[password];

	}

	/**
	 * Metoda dopisuj�ca u�yte has�o do listy zu�ytych hase�.
	 * @param usedPassword - dopisywane has�o
	 */
	public void addUsedPassword(String usedPassword)
	{
		usedPasswords.add(usedPassword);
	}
	

}
