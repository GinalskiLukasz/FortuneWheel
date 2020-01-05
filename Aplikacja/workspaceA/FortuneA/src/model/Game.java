package model;


/**
* 
* Klasa przechowuj�ca dane dotycz�ce gry. 
* Gromadzi informacje o punktach zdobytych przez graczy oraz zdobytych nagrodach. 
* Dostarcza dane do wy�wietlania aktualnych wynik�w oraz obliczania kto ostatecznie wygra� gr�. 
* Dodatkowo przechowuje iformacj� o aktualnej rundzie gry.

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
	 * @param activePlayer gracz aktualnie wykonuj�cy ruch. Typ danych String.
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
	 * Zwraca nazw� gracza wykonuj�cego ruch 
	 * @return gracz aktualnie wykonuj�cy ruch. Typ danych String.
	 */
	public String getActivePlayer() {
		return activePlayer;
	}
	/** 
	 * Ustawia gracza, kt�ry ma teraz ruch
	 * @param activePlayer gracz, kt�ry ma teraz ruch. Typ danych String. 
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
	
		
	/** Zwraca tymczasow� sum� pieni�dzy. Tymczasowa suma jest ustawiana zgodnie z warto�ci�,
	 * kt�ra zosta�a wylosowana na kole. W przypadku udzielania prawid�owej odpowiedzi przez gracza, tymczasowa 
	 * suma jest dodawana do puli punkt�w zdobytych przez gracza w danej rundzie
	 * @return tymczasowa suma.  Typ danych int.
	 */
	public int getTemporaryCash() {
		return temporaryCash;
	}
	/** Ustawia tymczasow� sum�. Tymczasowa suma jest ustawiana zgodnie z warto�ci�,
	 * kt�ra zosta�a wylosowana na kole. W przypadku udzielania prawid�owej odpowiedzi przez gracza, tymczasowa 
	 * suma jest dodawana do puli punkt�w zdobytych przez gracza w danej rundzie.
	 * @param temporaryCash tymczasowa suma. Typ danych int.
	 */
	public void setTemporaryCash(int temporaryCash) {
		this.temporaryCash = temporaryCash;
	}
	/**
	 * Zwraca ��czn� liczb� punkt�w gracza A zdobytych w ca�ej grze.
	 * @return ��czna liczba punkt�w gracza A zdobytych w ca�ej grze. Typ danych int.
	 */
	public int getPlayerApointsTotal() {
		return playerApointsTotal;
	}
	/**
	 * Ustawia ��czn� liczb� punkt�w gracza A zdobytych w ca�ej grze.
	 * @param playerApointsTotal ��czna liczba punkt�w gracza A zdobytych w ca�ej grze. Typ danych int.
	 */
	public void setPlayerApointsTotal(int playerApointsTotal) {
		this.playerApointsTotal = playerApointsTotal;
	}
	/**
	 * Zwraca ��czn� liczb� punkt�w gracza B zdobytych w ca�ej grze.
	 * @return ��czna liczba punkt�w gracza B zdobytych w ca�ej grze. Typ danych int
	 */
	public int getPlayerBpointsTotal() {
		return playerBpointsTotal;
	}
	
	/**
	 * Ustawia ��czn� liczb� punkt�w gracza B zdobytych w ca�ej grze.
	 * @param playerBpointsTotal ��czna liczba punkt�w gracza B zdobytych w ca�ej grze. Typ danych int
	 */
	public void setPlayerBpointsTotal(int playerBpointsTotal) {
		this.playerBpointsTotal = playerBpointsTotal;
	}
	/** 
	 * Sprawdza czy gracz posiada ju� nagrod� skuter.
	 * @return Zwraca "true" gdy gracz posiada skuter. Zwraca "false" gdy nie posiada.
	 */
	public boolean isSkuter() {
		return isSkuter;
	}
	/** 
	 * Okre�la czy gracz posiada nagrod� skuter.
	 * @param isSkuter Warto�� "True" oznacza, �e gracz posiada skuter. Warto�� "false", �e nie posiada.
	 */
	public void setSkuter(boolean isSkuter) {
		this.isSkuter = isSkuter;
	}
	/** 
	 * Sprawdza czy gracz posiada ju� nagrod� agd.
	 * @return Zwraca "true" gdy gracz posiada agd. Zwraca "false" gdy nie posiada.
	 */
	public boolean isAgd() {
		return isAgd;
	}
	/** 
	 * Okre�la czy gracz posiada nagrod� agd.
	 * @param isAgd Warto�� "True" oznacza, �e gracz posiada agd. Warto�� "false", �e nie posiada.
	 */
	public void setAgd(boolean isAgd) {
		this.isAgd = isAgd;
	}
	/** 
	 * Sprawdza czy gracz posiada ju� nagrod� wycieczk�.
	 * @return Zwraca "true" gdy gracz posiada wycieczk�. Zwraca "false" gdy nie posiada.
	 */
	public boolean isSummer() {
		return isSummer;
	}
	/** 
	 * Okre�la czy gracz posiada nagrod� wycieczk�.
	 * @param isSummer Warto�� "True" oznacza, �e gracz posiada wycieczk�. Warto�� "false", �e nie posiada.
	 */
	public void setSummer(boolean isSummer) {
		this.isSummer = isSummer;
	}

	/** 
	 * Sprawdza czy gracz posiada ju� nagrod�.
	 * @return Zwraca "true" gdy gracz posiada nagrod�. Zwraca "false" gdy nie posiada.
	 */
	public boolean isNagroda() {
		return isNagroda;
	}

	/** 
	 * Okre�la czy gracz posiada nagrod�.
	 * @param isNagroda Warto�� "True" oznacza, �e gracz posiada nagrod�. Warto�� "false", �e nie posiada.
	 */
	public void setNagroda(boolean isNagroda) {
		this.isNagroda = isNagroda;
	}
	
	

	
}
