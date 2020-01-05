package model;


/**
* 
* Klasa przechowuj¹ca dane dotycz¹ce gry. 
* Gromadzi informacje o punktach zdobytych przez graczy oraz zdobytych nagrodach. 
* Dostarcza dane do wyœwietlania aktualnych wyników oraz obliczania kto ostatecznie wygra³ grê. 
* Dodatkowo przechowuje iformacjê o aktualnej rundzie gry.

*/
public class Game {
	
	int round = 0;
	String activePlayer;
	int playerApoints = 0;
	int playerBpoints = 0;
	int playerApointsTotal = 0;
	int playerBpointsTotal = 0;
	
	int temporaryCash;
	
	boolean isSkuter = false;
	boolean isAgd = false;
	boolean isSummer = false;
	boolean isNagroda = false;
	

	/**
	 * @param round numer aktualnej rundy gry z zakresu od 1 do 5. Typ danych int.
	 * @param activePlayer gracz aktualnie wykonuj¹cy ruch. Typ danych String.
	 * @param playerApoints punkty gracza pierwszego zdobyte w danej rundzie. Typ danych int.
	 * @param playerBpoints punkty gracza drugiego zdobyte w danej rundzie. Typ danych int.
	 */
	public Game(int round, String activePlayer, int playerApoints, int playerBpoints) {

		this.round = round;
		this.activePlayer = activePlayer;
		this.playerApoints = playerApoints;
		this.playerBpoints = playerBpoints;
	}
	
	
	/**
	 * Zwraca numer aktualnej rundy z zakresu od 1 do 5. Gra posiada 5 rund.
	 * @return numer aktualnej rundy. Typ danych int.
	 */
	public int getRound() {
		return round;
	}
	/**
	 * Ustawia numer aktualnej rundy  z zakresu od 1 do 5.
	 * @param round numer aktualnej rundy. Typ danych int.
	 */
	public void setRound(int round) {
		this.round = round;
	}
	/**
	 * Zwraca nazwê gracza wykonuj¹cego ruch 
	 * @return gracz aktualnie wykonuj¹cy ruch. Typ danych String.
	 */
	public String getActivePlayer() {
		return activePlayer;
	}
	/** 
	 * Ustawia gracza, który ma teraz ruch
	 * @param activePlayer gracz, który ma teraz ruch. Typ danych String. 
	 */
	public void setActivePlayer(String activePlayer) {
		this.activePlayer = activePlayer;
	}
	/**
	 * Zwraca punkty z aktualnej tury gracza A
	 * @return punkty z aktualnej tury gracza A. Typ danych int.
	 */
	public int getPlayerApoints() {
		return playerApoints;
	}
	/**
	 * Zapisuje punkty z aktualnej tury gracza A
	 * @param playerApoints punkty z aktualnej tury gracza A. Typ danych int.
	 */
	public void setPlayerApoints(int playerApoints) {
		this.playerApoints = playerApoints;
	}
	/**
	 * Zwraca punkty z aktualnej tury gracza B
	 * @return punkty z aktualnej tury gracza B. Typ danych int.
	 */
	public int getPlayerBpoints() {
		return playerBpoints;
	}
	/**
	 * Zapisuje punkty z aktualnej tury gracza B
	 * @param playerBpoints punkty z aktualnej tury gracza B. Typ danych int.
	 */
	public void setPlayerBpoints(int playerBpoints) {
		this.playerBpoints = playerBpoints;
	}
	
		
	/** Zwraca tymczasow¹ sumê pieniêdzy. Tymczasowa suma jest ustawiana zgodnie z wartoœci¹,
	 * która zosta³a wylosowana na kole. W przypadku udzielania prawid³owej odpowiedzi przez gracza, tymczasowa 
	 * suma jest dodawana do puli punktów zdobytych przez gracza w danej rundzie
	 * @return tymczasowa suma.  Typ danych int.
	 */
	public int getTemporaryCash() {
		return temporaryCash;
	}
	/** Ustawia tymczasow¹ sumê. Tymczasowa suma jest ustawiana zgodnie z wartoœci¹,
	 * która zosta³a wylosowana na kole. W przypadku udzielania prawid³owej odpowiedzi przez gracza, tymczasowa 
	 * suma jest dodawana do puli punktów zdobytych przez gracza w danej rundzie.
	 * @param temporaryCash tymczasowa suma. Typ danych int.
	 */
	public void setTemporaryCash(int temporaryCash) {
		this.temporaryCash = temporaryCash;
	}
	/**
	 * Zwraca ³¹czn¹ liczbê punktów gracza A zdobytych w ca³ej grze.
	 * @return ³¹czna liczba punktów gracza A zdobytych w ca³ej grze. Typ danych int.
	 */
	public int getPlayerApointsTotal() {
		return playerApointsTotal;
	}
	/**
	 * Ustawia ³¹czn¹ liczbê punktów gracza A zdobytych w ca³ej grze.
	 * @param playerApointsTotal ³¹czna liczba punktów gracza A zdobytych w ca³ej grze. Typ danych int.
	 */
	public void setPlayerApointsTotal(int playerApointsTotal) {
		this.playerApointsTotal = playerApointsTotal;
	}
	/**
	 * Zwraca ³¹czn¹ liczbê punktów gracza B zdobytych w ca³ej grze.
	 * @return ³¹czna liczba punktów gracza B zdobytych w ca³ej grze. Typ danych int
	 */
	public int getPlayerBpointsTotal() {
		return playerBpointsTotal;
	}
	
	/**
	 * Ustawia ³¹czn¹ liczbê punktów gracza B zdobytych w ca³ej grze.
	 * @param playerBpointsTotal ³¹czna liczba punktów gracza B zdobytych w ca³ej grze. Typ danych int
	 */
	public void setPlayerBpointsTotal(int playerBpointsTotal) {
		this.playerBpointsTotal = playerBpointsTotal;
	}
	/** 
	 * Sprawdza czy gracz posiada ju¿ nagrodê skuter.
	 * @return Zwraca "true" gdy gracz posiada skuter. Zwraca "false" gdy nie posiada.
	 */
	public boolean isSkuter() {
		return isSkuter;
	}
	/** 
	 * Okreœla czy gracz posiada nagrodê skuter.
	 * @param isSkuter Wartoœæ "True" oznacza, ¿e gracz posiada skuter. Wartoœæ "false", ¿e nie posiada.
	 */
	public void setSkuter(boolean isSkuter) {
		this.isSkuter = isSkuter;
	}
	/** 
	 * Sprawdza czy gracz posiada ju¿ nagrodê agd.
	 * @return Zwraca "true" gdy gracz posiada agd. Zwraca "false" gdy nie posiada.
	 */
	public boolean isAgd() {
		return isAgd;
	}
	/** 
	 * Okreœla czy gracz posiada nagrodê agd.
	 * @param isAgd Wartoœæ "True" oznacza, ¿e gracz posiada agd. Wartoœæ "false", ¿e nie posiada.
	 */
	public void setAgd(boolean isAgd) {
		this.isAgd = isAgd;
	}
	/** 
	 * Sprawdza czy gracz posiada ju¿ nagrodê wycieczkê.
	 * @return Zwraca "true" gdy gracz posiada wycieczkê. Zwraca "false" gdy nie posiada.
	 */
	public boolean isSummer() {
		return isSummer;
	}
	/** 
	 * Okreœla czy gracz posiada nagrodê wycieczkê.
	 * @param isSummer Wartoœæ "True" oznacza, ¿e gracz posiada wycieczkê. Wartoœæ "false", ¿e nie posiada.
	 */
	public void setSummer(boolean isSummer) {
		this.isSummer = isSummer;
	}

	/** 
	 * Sprawdza czy gracz posiada ju¿ nagrodê.
	 * @return Zwraca "true" gdy gracz posiada nagrodê. Zwraca "false" gdy nie posiada.
	 */
	public boolean isNagroda() {
		return isNagroda;
	}

	/** 
	 * Okreœla czy gracz posiada nagrodê.
	 * @param isNagroda Wartoœæ "True" oznacza, ¿e gracz posiada nagrodê. Wartoœæ "false", ¿e nie posiada.
	 */
	public void setNagroda(boolean isNagroda) {
		this.isNagroda = isNagroda;
	}
	
	

	
}
