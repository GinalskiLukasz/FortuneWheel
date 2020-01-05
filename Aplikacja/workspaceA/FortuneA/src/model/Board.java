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
 * Klasa odpowiedzialna za zachowanie wyœwietlanej tablicy z literiami.
 */
public class Board {
	
	
	private String currentPassword;
	private Label[] labels = new Label[56];
	private Rectangle[] rectangles = new Rectangle[56];
	private SimpleBooleanProperty animationInProgress= new SimpleBooleanProperty(false);
	public boolean checker = false;
	int counter;
	
	/**
	 * Metoda sprawdzaj¹ca czy podane has³o jest poprawne.
	 * @param password - sprawdzane has³o
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
	 * Metoda inicjuj¹ca ods³oniêcie has³a na tablicy.
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
	 * Metoda sprawdzaj¹ca czy podana litera znajduje siê w haœle.
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
	 * Metoda uruchamiaj¹ca w¹tek ods³aniaj¹cy litery w haœle.
	 * @param letterNumber - numer litery do ods³oniêcia
	 * @param sleepDuration - czas opóŸnienia pomiêdzy ods³oniêciem kolejnych liter
	 */
	public void showLetter(int letterNumber, int sleepDuration) {
		
		new Thread(new LetterReverser(this,letterNumber,sleepDuration)).start();		
	}
	
	
	/**
	 * Metoda zwracaj¹ca tablicê etykiet odpowiadaj¹cych literom na wyœwietlanej tablicy.
	 * @return - zwracana tablica
	 */
	public Label[] getLabels() {
		return labels;
	}

	/**
	 * Metoda ustawiaj¹ca tablicê etykiet odpowiadaj¹cych literom na wyœwietlanej tablicy.
	 * @param labels - ustawiana tablica
	 */
	public void setLabels(Label[] labels) {
		this.labels = labels;
	}

	/**
	 * Metoda zwracaj¹ca tablicê obiektów typu Rectangle powi¹zanych z literami na wyœwietlanej tablicy.
	 * @return - zwracana tablica
	 */
	public Rectangle[] getRectangles() {
		return rectangles;
	}

	/**
	 * Metoda ustawiaj¹ca tablicê obiektów typu Rectangle powi¹zanych z literami na wyœwietlanej tablicy.
	 * @param rectangles - ustawiana tablica
	 */
	public void setRectangles(Rectangle[] rectangles) {
		this.rectangles = rectangles;
	}

	/**
	 * Metoda ustawiaj¹ca pola klasy na etykiety i obiekty typu Rectangle pobrane z wyœwietlanej tablicy.
	 * @param boardChildren - lista obiektów typu Node (etykiety i obiekty typu Rectangle)
	 */
	public void setBoardChildren(ObservableList<Node> boardChildren) {
		for (int i=0; i<=55; i++)
		{
			this.labels[i]=(Label)boardChildren.get(i+56);
			this.rectangles[i]=(Rectangle)boardChildren.get(i);
		}
				
	}
	
	/**
	 * Metoda zwracaj¹ca w³aœciwoœæ animationInProgress.
	 * @return - zwracana w³aœciwoœæ
	 */
	public SimpleBooleanProperty getAnimationInProgress() {
		return animationInProgress;
	}



	/**
	 * Metoda ustawiaj¹ca w³aœciwoœæ animationInProgress.
	 * @param animationInProgress - ustawiana w³aœciwoœæ
	 */
	public void setAnimationInProgress(SimpleBooleanProperty animationInProgress) {
		this.animationInProgress = animationInProgress;
	}



	/**
	 * Metoda zwracaj¹ca aktualnie ustawione has³o.
	 * @return - zwracane has³o
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}
	

	
	/**
	 * Metoda ustawiaj¹ca has³o na tablicy.
	 * @param currentPassword - ustawiane has³o
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
	 * Metoda zwracaj¹ca wartoœæ licznika
	 * @return - zwracany licznik
	 */
	public int getCounter() {
		return counter;
	}


	/**
	 * Metoda zwracaj¹ca wartoœæ flagi checker.
	 * @return - zwracana flaga
	 */
	public boolean getChecker() {
		return checker;
	}


	/**
	 * Metoda ustawiaj¹ca wartoœæ flagi checker.
	 * @param checker - ustawiana wartoœæ
	 */
	public void setChecker(boolean checker) {
		this.checker = checker;
	}
	
	
}
