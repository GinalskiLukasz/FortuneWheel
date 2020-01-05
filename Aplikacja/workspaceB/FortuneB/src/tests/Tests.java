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
 * Klasa testuj¹ca
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
	 * Metoda testuj¹ce zmianê tury w grze
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
	 * Metoda testuj¹ca dzia³anie ko³a 
	 */
	@Test	
	public void testIfWheelHasCorrectFieldsValues() {
		String expected = "WYCIECZKA";
		String result = wheel.getFieldValue(2);
		assertTrue(expected.equals(result));             		
	}
	
	
	/**
	 * Metoda testuj¹ca dzia³anie bazy danych hase³
	 */
	@Test	
	public void testIfPasswordDataBaseUseAllThePasswords() {

		passwordDataBase.addUsedPassword("  Darowanemu  /koniowi w zêby/   siê nie    /   zagl¹da    ");
		passwordDataBase.addUsedPassword("Nie chwal dnia/przed zachodem/    s³oñca    /              ");
		passwordDataBase.addUsedPassword("  Co ciê nie  /  zabije to   /  ciê wzmocni /              ");
		passwordDataBase.addUsedPassword(" Czego Jaœ siê/  nie nauczy  / tego Jan nie / bêdzie umia³ ");
		passwordDataBase.addUsedPassword("  Gdzie dwóch /   siê bije   /  tam trzeci  /   korzysta   ");
		passwordDataBase.addUsedPassword(" Kogut myœla³ /  o niedzieli /  a w sobotê  /  ³eb uciêli  ");
		passwordDataBase.addUsedPassword("  Prawdziwych /  przyjació³  /  poznaje siê /   w biedzie  ");
		passwordDataBase.addUsedPassword("   Wszystko   / dobre co siê /dobrze koñczy /              ");
		passwordDataBase.addUsedPassword("   Wymieni³   /    stryjek   /   siekierkê  /   na kijek   ");
		
		String expected = "Lepszy wróbel / w garœci ni¿ /go³¹b na dachu/              ";
		String result = passwordDataBase.getPassword();
		
		assertTrue(expected.equals(result));
	}
	
	
	/**
	 * Metoda testuj¹ca mechanizm okreœlaj¹cy gracza wykonuj¹cego ruch 
	 */
	@Test
	public void testGameActivePlayer() {	
		String expected = "PlayerA";
		String result = game.getActivePlayer();
		assertTrue(expected.equals(result));
	}
	
	/**
	 * Metoda testuj¹ca mechanizm przyznawania nagordy AGD
	 */
	@Test
	public void testGameAgdBoolean() {	
		game.setAgd(true);
		Boolean expected = new Boolean(true);
		Boolean result = game.isAgd();
		assertTrue(expected.equals(result));
	}
	
	/**
	 * Metoda testuj¹ca sortowanie graczy wg zdobytych punktów
	 */
	@Test
	public void testSorting() {
		
		Integer expected = new Integer(1);
		Integer result = new Integer(sorter.compare(playerA, playerB));
		assertTrue(expected.equals(result));
	
	}
	
	/**
	 * Metoda testuj¹ca mechanizm przyznawania i pobierania punktów gracza
	 */
	@Test
	public void testPlayerPointsSetterAndGetter() {
		
		game.setPlayerApoints(2500);
		Integer expected = new Integer(2500);
		Integer result = new Integer(game.getPlayerApoints());
		assertTrue(expected.equals(result));
	}
	/**
	 * Metoda testuj¹ca mechanizm ustalania i pobierania tymczasowych punktów
	 */
	@Test
	public void testPlayerTemporaryCash() {
		
		game.setTemporaryCash(1700);
		Integer expected = new Integer(1700);
		Integer result = new Integer(game.getTemporaryCash());
		assertTrue(expected.equals(result));
	}
	/**
	 * Metoda testuj¹ca mechanizm ustalania i pobierania obecnej rundy gry
	 */
	@Test	
	public void testGameRound() {
		
		game.setRound(2);
		Integer expected = new Integer(2);
		Integer result = new Integer(game.getRound());
		assertTrue(expected.equals(result));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
