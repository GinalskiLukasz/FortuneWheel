package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;

import model.Sorting;

/**
 * Klasa odpowiedzialna za pobranie z bazy danych graczy oraz ich wynikow punktowych 
 */
public class Player {

static Sorting sorting = new Sorting();
private String player;
private String points;
static ArrayList<Player> players = new ArrayList<Player>();
static int licznik = 0;


/**
 * @param player nazwa gracza 
 * @param points liczba punktów gracza
 */
public Player(String player, String points) {
	
	this.player = player;
	this.points = points;
}

/**Zwraca liczbê punktów
 * @return liczba punktów
 */

public String getPoints() {
	return points;
}



/**Zwraca nazwê gracza
 * @return nazwa gracza
 */
public String getPlayer() {
	return player;
}

/**
 * £¹czy siê z baz¹ danych, pobiera ranking graczy i zapisuje w liœcie 
 */
public  static void rankUpdate()
{
	
	try {
		
		String url = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
		String uzytkownik = "lginalsk";
		String haslo = "lginalsk";
					
		String nazwaSterownika = "oracle.jdbc.driver.OracleDriver";
		Class c = Class.forName(nazwaSterownika);
		System.out.println("Pakiet		: " + c.getPackage());
		System.out.println("Nazwa klasy	: " + c.getName());
		
		Connection polaczenie = DriverManager.getConnection(url, uzytkownik, haslo);
		Statement polecenie = polaczenie.createStatement();
		System.out.println("Autocommit: " + polaczenie.getAutoCommit());
		
		ResultSet rs = polecenie.executeQuery("SELECT * FROM ranking");
		
		if (licznik <20)
		{
		while (rs.next()) 
		{
			Player x = new Player (rs.getString(2), rs.getString(1));
			players.add(x);
			
			
			licznik++;
			
		}
		}
		players.sort(sorting);
		rs.close();
		}
	catch (Exception e)
		{
			System.out.println("Blad programu");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return;
		}
		
		
	}
	
/**
 * Zwraca gracza o podanym indeksie
 * @param index indeks gracza z listy 
 * @return gracz
 */
public static Player  getPlayerList(int index)
{
	
	
	return players.get(index);
	
	
}


}
