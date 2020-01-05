package model;

import controller.GameWindowController;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Klasa odpowiadaj�ca za zachowanie ko�a fortuny.
 */
public class Wheel {
	
	
	private final String[] fieldValues = {"200", "100", "WYCIECZKA", "150", "250", "300", "AGD", "200", "BANKRUT", "1500", "350", "?500",
							"SKUTER", "150", "200", "NAGRODA", "250", "500", "BANKRUT", "400", "?500", "250", "STOP", "400"};
	
	private ImageView image;
	private Label resultLabel;
    private boolean animationRunningFlag=false;
    private Animation animation;
    private RotateTransition transition;
    private Button giveButton;
    private boolean yourTurn;
    private GameWindowController gameWindowController;
    
    
	FieldValuesReader reader = new FieldValuesReader(this);
    
    
    /**
     * Metoda uruchamiaj�ca animacj� kr�cenia ko�em.
     * @param wheelVelocity - si�a kr�cenia
     */    
    public void wheelRotate(double wheelVelocity) {
    	
    	transition = new RotateTransition();
		transition.setNode(image);
		transition.setDuration(Duration.seconds(wheelVelocity/100));
		transition.setByAngle(wheelVelocity);
		transition.setCycleCount(1);
		transition.setAutoReverse(false);
		transition.setInterpolator(new Interpolator() {

			@Override
			protected double curve(double t) {
	
				return 1-Math.pow(2.0, -8*t);

			}
			
		});
		animation=transition;
		animation.play();
		animationRunningFlag=true;
		image.setDisable(true);
		
		animation.setOnFinished(e->{
			image.setDisable(false);
			animationRunningFlag=false;
			if (yourTurn) 
			{
//				giveButton.setDisable(false);
				Platform.runLater(
						  () -> {
							  									  
							  try {
								Thread.sleep(500);
								gameWindowController.give();
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						  }
						);
			}
			

		});		
		
		FieldValuesReader reader = new FieldValuesReader(this);
		resultLabel.textProperty().bind(reader.messageProperty());
		
		new Thread(reader).start();
		
    }
 
	/**
	 * Metoda zatrzymuj�ca animacj� kr�cenia ko�em.
	 */
	public void stopRotate() {
		animation.stop();
		image.setDisable(false);
		animationRunningFlag=false;
	}
	
	/**
	 * Metoda zamieniaj�ca u�ytkownika, kt�rego tura aktualnie trwa.
	 */
	public void switchTurn() {
		if (yourTurn==true) setYourTurn(false);
		else setYourTurn(true);
	}
	
	/**
	 * Metoda zwracaj�ca tre�� wiadomo�ci z etykiety rezultatu.
	 * @return - zwracana warto��
	 */
	public Label getResultLabel() {
		return resultLabel;
	}


	/**
	 * Metoda ustawiaj�ca tre�� wiadomo�ci w etykiecie rezultatu.
	 * @param resultLabel - ustawiana warto��
	 */
	public void setResultLabel(Label resultLabel) {
		this.resultLabel = resultLabel;
	}


	/**
	 * Metoda zwracaj�ca obraz ko�a.
	 * @return - zwracany obraz
	 */
	public ImageView getImage() {
		return image;
	}

	/**
	 * Metoda ustawiaj�ca obraz ko�a.
	 * @param image - ustawiany obraz
	 */
	public void setImage(ImageView image) {
		this.image = image;
	}

	/**
	 * Metoda zwracaj�ca stan flagi informuj�cej o stanie animacji kr�cenia ko�em.
	 * @return - zwracana warto��
	 */
	public  boolean isAnimationRunningFlag() {
		return animationRunningFlag;
	}

	/**
	 * Metoda ustawiaj�ca stan flagi informuj�cej o stanie animacji kr�cenia ko�em.
	 * @param animationRunningFlag - ustawiana warto��
	 */
	public  void setAnimationRunningFlag(boolean animationRunningFlag) {
		this.animationRunningFlag = animationRunningFlag;
	}


	/**
	 * Metoda zwracaj�ca warto�� pola na kole o podanym numerze
	 * @param fieldNumber - numer pola
	 * @return - zwracana warto��
	 */
	public String getFieldValue(int fieldNumber) {
		
		return fieldValues[fieldNumber];
		
	}

	/**
	 * Metoda zwracaj�ca warto�� pozycji k�towej ko�a.
	 * @return - zwracana warto��
	 */
	public double getRotate() {
		return image.getRotate();
	}
	
	/**
	 * Metoda ustawiaj�ca warto�� pozycji k�towej ko�a.
	 * @param rotate - ustawiana warto��
	 */
	public void setRotate(double rotate) {
		this.image.setRotate(rotate);
	}	
	
	/**
	 * Metoda zwracaj�ca przycisk podawania samog�oski.
	 * @return - zwracany przycisk
	 */
	public Button getGiveButton() {
		return giveButton;
	}

	/**
	 * Metoda ustawiaj�ca przycisk podawania samog�oski.
	 * @param giveButton - ustawiany przycisk
	 */
	public void setGiveButton(Button giveButton) {
		this.giveButton = giveButton;
	}
	
	/**
	 * Metoda zwracaj�ca warto�� flagi informuj�cej, kt�rego gracza tura aktualnie trwa.
	 * @return - zwracana warto��
	 */
	public boolean isYourTurn() {
		return yourTurn;
	}

	/**
	 * Metoda ustawiaj�ca warto�� flagi informuj�cej, kt�rego gracza tura aktualnie trwa.
	 * @param yourTurn - ustawiana warto��
	 */
	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
		System.out.println("B: Your turn set to "+yourTurn);
	}
	
	/**
	 * Metoda zwracaj�ca obiekt klasy kontrolera g�ownego okna aplikacji. 
	 * @return - zwracany obiekt
	 */
	public GameWindowController getGameWindowController() {
		return gameWindowController;
	}

	/**
	 * Metoda ustawiaj�ca obiekt klasy kontrolera g��wnego okna aplikacji.
	 * @param gameWindowController - ustawiany obiekt
	 */
	public void setGameWindowController(GameWindowController gameWindowController) {
		this.gameWindowController = gameWindowController;
	}
	
}
