package model;

import java.util.ArrayList;

/**
 * Klasa/baza danych hase³ wykorzystywanych w grze.
 */
public class PasswordDataBase {
	
	private String[] passwords = new String[10];
	private ArrayList<String> usedPasswords = new ArrayList<String>();
	
	/**
	 * Konstruktor klasy PasswordDataBase ustawiaj¹cy has³a.
	 */
	public PasswordDataBase() {
		
		passwords[0]="  Darowanemu  /koniowi w zêby/   siê nie    /   zagl¹da    ";
		passwords[1]="Nie chwal dnia/przed zachodem/    s³oñca    /              ";
		passwords[2]="Lepszy wróbel / w garœci ni¿ /go³¹b na dachu/              ";
		passwords[3]="  Co ciê nie  /  zabije to   /  ciê wzmocni /              ";
		passwords[4]=" Czego Jaœ siê/  nie nauczy  / tego Jan nie / bêdzie umia³ ";
		passwords[5]="  Gdzie dwóch /   siê bije   /  tam trzeci  /   korzysta   ";
		passwords[6]=" Kogut myœla³ /  o niedzieli /  a w sobotê  /  ³eb uciêli  ";
		passwords[7]="  Prawdziwych /  przyjació³  /  poznaje siê /   w biedzie  ";
		passwords[8]="   Wszystko   / dobre co siê /dobrze koñczy /              ";
		passwords[9]="   Wymieni³   /    stryjek   /   siekierkê  /   na kijek   ";
		
	}

	/**
	 * Metoda losuj¹ca i zwracaj¹ca wylosowane has³o.
	 * @return - zwracane has³o
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
	 * Metoda dopisuj¹ca u¿yte has³o do listy zu¿ytych hase³.
	 * @param usedPassword - dopisywane has³o
	 */
	public void addUsedPassword(String usedPassword)
	{
		usedPasswords.add(usedPassword);
	}
	

}
