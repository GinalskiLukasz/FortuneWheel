package model;



import java.util.Comparator;

/**
 * Klasa sortuj�ca graczy wg liczby punkt�w malej�co
 */
public class Sorting implements Comparator<Player> 

{

	
	/**
	* Metoda sortuje graczy wg liczby punkt�w malej�co
	* @param p1 pierwszy gracz do por�wnania
	* @param p2 drugi gracz do por�wnania
	*/
	public int compare(Player p1, Player p2)
	{
		if (p1 == null) return -1;
		if(Integer.parseInt(p1.getPoints()) < Integer.parseInt(p2.getPoints())) return 1;
			else if(Integer.parseInt(p1.getPoints()) > Integer.parseInt(p2.getPoints())) return -1;
		
			else return 0;
		

	}	
	
	
}