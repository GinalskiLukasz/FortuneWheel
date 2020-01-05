package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Board;
import model.Game;
import model.PasswordDataBase;
import model.Player;
import model.Sorting;
import model.Wheel;

/**
 * Klasa testuj�ca
 */
public class Tests {
	
	private Board board;
	private Wheel wheel;
	private PasswordDataBase passwordDataBase;
	private Game game;
	private Player playerA;
	private Player playerB;
	private Sorting sorter;
	

	
	/**
	 * Metoda buduje obiekty wykorzystane do testowania
	 */
	@Before	
	public void setUp() {
		board = new Board();
		wheel = new Wheel();
		passwordDataBase = new PasswordDataBase();
		game=new Game(2,"PlayerA",1500, 600);
		playerA = new Player("PlayerA", "1000");
		playerB = new Player("PlayerB", "2000");
		sorter = new Sorting();
		
	}
	
	/**
	 * Metoda testuj�ce zmian� tury w grze
	 */
	@Test
	public void testWheelSwitchTurn() {
		wheel.setYourTurn(true);
		wheel.switchTurn();
		Boolean expected = new Boolean(false);
		Boolean result = new Boolean(wheel.isYourTurn());	
		assertTrue(expected.equals(result));             		
	}
	
	
	/**
	 * Metoda testuj�ca dzia�anie ko�a 
	 */
	@Test	
	public void testIfWheelHasCorrectFieldsValues() {
		String expected = "WYCIECZKA";
		String result = wheel.getFieldValue(2);
		assertTrue(expected.equals(result));             		
	}
	
	
	/**
	 * Metoda testuj�ca dzia�anie bazy danych hase�
	 */
	@Test	
	public void testIfPasswordDataBaseUseAllThePasswords() {

		passwordDataBase.addUsedPassword("  Darowanemu  /koniowi w z�by/   si� nie    /   zagl�da    ");
		passwordDataBase.addUsedPassword("Nie chwal dnia/przed zachodem/    s�o�ca    /              ");
		passwordDataBase.addUsedPassword("  Co ci� nie  /  zabije to   /  ci� wzmocni /              ");
		passwordDataBase.addUsedPassword(" Czego Ja� si�/  nie nauczy  / tego Jan nie / b�dzie umia� ");
		passwordDataBase.addUsedPassword("  Gdzie dw�ch /   si� bije   /  tam trzeci  /   korzysta   ");
		passwordDataBase.addUsedPassword(" Kogut my�la� /  o niedzieli /  a w sobot�  /  �eb uci�li  ");
		passwordDataBase.addUsedPassword("  Prawdziwych /  przyjaci�  /  poznaje si� /   w biedzie  ");
		passwordDataBase.addUsedPassword("   Wszystko   / dobre co si� /dobrze ko�czy /              ");
		passwordDataBase.addUsedPassword("   Wymieni�   /    stryjek   /   siekierk�  /   na kijek   ");
		
		String expected = "Lepszy wr�bel / w gar�ci ni� /go��b na dachu/              ";
		String result = passwordDataBase.getPassword();
		
		assertTrue(expected.equals(result));
	}
	
	
	/**
	 * Metoda testuj�ca mechanizm okre�laj�cy gracza wykonuj�cego ruch 
	 */
	@Test
	public void testGameActivePlayer() {	
		String expected = "PlayerA";
		String result = game.getActivePlayer();
		assertTrue(expected.equals(result));
	}
	
	/**
	 * Metoda testuj�ca mechanizm przyznawania nagordy AGD
	 */
	@Test
	public void testGameAgdBoolean() {	
		game.setAgd(true);
		Boolean expected = new Boolean(true);
		Boolean result = game.isAgd();
		assertTrue(expected.equals(result));
	}
	
	/**
	 * Metoda testuj�ca sortowanie graczy wg zdobytych punkt�w
	 */
	@Test
	public void testSorting() {
		
		Integer expected = new Integer(1);
		Integer result = new Integer(sorter.compare(playerA, playerB));
		assertTrue(expected.equals(result));
	
	}
	
	/**
	 * Metoda testuj�ca mechanizm przyznawania i pobierania punkt�w gracza
	 */
	@Test
	public void testPlayerPointsSetterAndGetter() {
		
		game.setPlayerApoints(2500);
		Integer expected = new Integer(2500);
		Integer result = new Integer(game.getPlayerApoints());
		assertTrue(expected.equals(result));
	}
	/**
	 * Metoda testuj�ca mechanizm ustalania i pobierania tymczasowych punkt�w
	 */
	@Test
	public void testPlayerTemporaryCash() {
		
		game.setTemporaryCash(1700);
		Integer expected = new Integer(1700);
		Integer result = new Integer(game.getTemporaryCash());
		assertTrue(expected.equals(result));
	}
	/**
	 * Metoda testuj�ca mechanizm ustalania i pobierania obecnej rundy gry
	 */
	@Test	
	public void testGameRound() {
		
		game.setRound(2);
		Integer expected = new Integer(2);
		Integer result = new Integer(game.getRound());
		assertTrue(expected.equals(result));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
