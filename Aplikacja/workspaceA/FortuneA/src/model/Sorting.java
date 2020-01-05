package model;



import java.util.Comparator;

/**
 * Klasa sortuj¹ca graczy wg liczby punktów malej¹co
 */
public class Sorting implements Comparator<Player> 

{

	
	/**
	* Metoda sortuje graczy wg liczby punktów malej¹co
	* @param p1 pierwszy gracz do porównania
	* @param p2 drugi gracz do porównania
	*/
	public int compare(Player p1, Player p2)
	{
		if (p1 == null) return -1;
		if(Integer.parseInt(p1.getPoints()) < Integer.parseInt(p2.getPoints())) return 1;
			else if(Integer.parseInt(p1.getPoints()) > Integer.parseInt(p2.getPoints())) return -1;
		
			else return 0;
		

	}	
	
	
}