package model;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Klasa odpowiedzialna za zachowanie wy�wietlanej tablicy z literiami.
 */
public class Board {
	
	
	private String currentPassword;
	private Label[] labels = new Label[56];
	private Rectangle[] rectangles = new Rectangle[56];
	private SimpleBooleanProperty animationInProgress= new SimpleBooleanProperty(false);
	public boolean checker = false;
	int counter;
	
	/**
	 * Metoda sprawdzaj�ca czy podane has�o jest poprawne.
	 * @param password - sprawdzane has�o
	 */
	public void checkPassword(String password) {
		setChecker(false);
		password=password.replaceAll("[ /]", "");
		String tempPassword=currentPassword.replaceAll("[ /]", "");
		
		if (password.equals(tempPassword))
		{
			showPassword();
			setChecker(true);			
		}
	}
	

	/**
	 * Metoda inicjuj�ca ods�oni�cie has�a na tablicy.
	 */
	public void showPassword() {
		
		for (int i=0; i<56; i++)
		{
			if (rectangles[i].getFill()==Color.DODGERBLUE)
			{
				showLetter(i,50);
			}
		}	
	}

	/**
	 * Metoda sprawdzaj�ca czy podana litera znajduje si� w ha�le.
	 * @param letter - sprawdzana litera
	 */
	public void checkLetter(String letter) {
		counter = 0;
		for (int i=0; i<56; i++) 
		{
			if (labels[i].getText().equals(letter.toUpperCase()) && rectangles[i].getFill()!=Color.IVORY)
			{
				rectangles[i].setFill(Color.DARKORANGE);
			}
		}
			
		for (int i=0; i<56; i++) 
		{
			if (rectangles[i].getFill()==Color.DARKORANGE)
			{
				showLetter(i,30);
				counter++;
				System.out.println(counter);
			}
		}
	}

	/**
	 * Metoda uruchamiaj�ca w�tek ods�aniaj�cy litery w ha�le.
	 * @param letterNumber - numer litery do ods�oni�cia
	 * @param sleepDuration - czas op�nienia pomi�dzy ods�oni�ciem kolejnych liter
	 */
	public void showLetter(int letterNumber, int sleepDuration) {
		
		new Thread(new LetterReverser(this,letterNumber,sleepDuration)).start();		
	}
	
	
	/**
	 * Metoda zwracaj�ca tablic� etykiet odpowiadaj�cych literom na wy�wietlanej tablicy.
	 * @return - zwracana tablica
	 */
	public Label[] getLabels() {
		return labels;
	}

	/**
	 * Metoda ustawiaj�ca tablic� etykiet odpowiadaj�cych literom na wy�wietlanej tablicy.
	 * @param labels - ustawiana tablica
	 */
	public void setLabels(Label[] labels) {
		this.labels = labels;
	}

	/**
	 * Metoda zwracaj�ca tablic� obiekt�w typu Rectangle powi�zanych z literami na wy�wietlanej tablicy.
	 * @return - zwracana tablica
	 */
	public Rectangle[] getRectangles() {
		return rectangles;
	}

	/**
	 * Metoda ustawiaj�ca tablic� obiekt�w typu Rectangle powi�zanych z literami na wy�wietlanej tablicy.
	 * @param rectangles - ustawiana tablica
	 */
	public void setRectangles(Rectangle[] rectangles) {
		this.rectangles = rectangles;
	}

	/**
	 * Metoda ustawiaj�ca pola klasy na etykiety i obiekty typu Rectangle pobrane z wy�wietlanej tablicy.
	 * @param boardChildren - lista obiekt�w typu Node (etykiety i obiekty typu Rectangle)
	 */
	public void setBoardChildren(ObservableList<Node> boardChildren) {
		for (int i=0; i<=55; i++)
		{
			this.labels[i]=(Label)boardChildren.get(i+56);
			this.rectangles[i]=(Rectangle)boardChildren.get(i);
		}
				
	}
	
	/**
	 * Metoda zwracaj�ca w�a�ciwo�� animationInProgress.
	 * @return - zwracana w�a�ciwo��
	 */
	public SimpleBooleanProperty getAnimationInProgress() {
		return animationInProgress;
	}



	/**
	 * Metoda ustawiaj�ca w�a�ciwo�� animationInProgress.
	 * @param animationInProgress - ustawiana w�a�ciwo��
	 */
	public void setAnimationInProgress(SimpleBooleanProperty animationInProgress) {
		this.animationInProgress = animationInProgress;
	}



	/**
	 * Metoda zwracaj�ca aktualnie ustawione has�o.
	 * @return - zwracane has�o
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}
	

	
	/**
	 * Metoda ustawiaj�ca has�o na tablicy.
	 * @param currentPassword - ustawiane has�o
	 */
	public void setCurrentPassword(String currentPassword) {
		
		for (int i=0; i<=55; i++)
		{
			rectangles[i].setFill(Color.DODGERBLUE);
		}
				
		this.currentPassword=currentPassword;
		String[] words = currentPassword.split("/");
		
		for (int i=0; i<words.length; i++)
		{
			for (int j=0; j<words[i].length(); j++)
			{
				String textToSet = words[i].substring(j,j+1).toUpperCase();
				
				if (textToSet.equals(" ")) 
				{
					rectangles[i*14+j].setFill(Color.GREY);					
				}
				else
				{
					labels[i*14+j].setVisible(false);
				}	
				labels[i*14+j].setText(textToSet);
			}
		}	
	}
	
	/**
	 * Metoda zwracaj�ca warto�� licznika
	 * @return - zwracany licznik
	 */
	public int getCounter() {
		return counter;
	}


	/**
	 * Metoda zwracaj�ca warto�� flagi checker.
	 * @return - zwracana flaga
	 */
	public boolean getChecker() {
		return checker;
	}


	/**
	 * Metoda ustawiaj�ca warto�� flagi checker.
	 * @param checker - ustawiana warto��
	 */
	public void setChecker(boolean checker) {
		this.checker = checker;
	}
	
	
}
