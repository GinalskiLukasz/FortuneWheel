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
 * Klasa odpowiadaj¹ca za zachowanie ko³a fortuny.
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
     * Metoda uruchamiaj¹ca animacjê krêcenia ko³em.
     * @param wheelVelocity - si³a krêcenia
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
	 * Metoda zatrzymuj¹ca animacjê krêcenia ko³em.
	 */
	public void stopRotate() {
		animation.stop();
		image.setDisable(false);
		animationRunningFlag=false;
	}
	
	/**
	 * Metoda zamieniaj¹ca u¿ytkownika, którego tura aktualnie trwa.
	 */
	public void switchTurn() {
		if (yourTurn==true) setYourTurn(false);
		else setYourTurn(true);
	}
	
	/**
	 * Metoda zwracaj¹ca treœæ wiadomoœci z etykiety rezultatu.
	 * @return - zwracana wartoœæ
	 */
	public Label getResultLabel() {
		return resultLabel;
	}


	/**
	 * Metoda ustawiaj¹ca treœæ wiadomoœci w etykiecie rezultatu.
	 * @param resultLabel - ustawiana wartoœæ
	 */
	public void setResultLabel(Label resultLabel) {
		this.resultLabel = resultLabel;
	}


	/**
	 * Metoda zwracaj¹ca obraz ko³a.
	 * @return - zwracany obraz
	 */
	public ImageView getImage() {
		return image;
	}

	/**
	 * Metoda ustawiaj¹ca obraz ko³a.
	 * @param image - ustawiany obraz
	 */
	public void setImage(ImageView image) {
		this.image = image;
	}

	/**
	 * Metoda zwracaj¹ca stan flagi informuj¹cej o stanie animacji krêcenia ko³em.
	 * @return - zwracana wartoœæ
	 */
	public  boolean isAnimationRunningFlag() {
		return animationRunningFlag;
	}

	/**
	 * Metoda ustawiaj¹ca stan flagi informuj¹cej o stanie animacji krêcenia ko³em.
	 * @param animationRunningFlag - ustawiana wartoœæ
	 */
	public  void setAnimationRunningFlag(boolean animationRunningFlag) {
		this.animationRunningFlag = animationRunningFlag;
	}


	/**
	 * Metoda zwracaj¹ca wartoœæ pola na kole o podanym numerze
	 * @param fieldNumber - numer pola
	 * @return - zwracana wartoœæ
	 */
	public String getFieldValue(int fieldNumber) {
		
		return fieldValues[fieldNumber];
		
	}

	/**
	 * Metoda zwracaj¹ca wartoœæ pozycji k¹towej ko³a.
	 * @return - zwracana wartoœæ
	 */
	public double getRotate() {
		return image.getRotate();
	}
	
	/**
	 * Metoda ustawiaj¹ca wartoœæ pozycji k¹towej ko³a.
	 * @param rotate - ustawiana wartoœæ
	 */
	public void setRotate(double rotate) {
		this.image.setRotate(rotate);
	}	
	
	/**
	 * Metoda zwracaj¹ca przycisk podawania samog³oski.
	 * @return - zwracany przycisk
	 */
	public Button getGiveButton() {
		return giveButton;
	}

	/**
	 * Metoda ustawiaj¹ca przycisk podawania samog³oski.
	 * @param giveButton - ustawiany przycisk
	 */
	public void setGiveButton(Button giveButton) {
		this.giveButton = giveButton;
	}
	
	/**
	 * Metoda zwracaj¹ca wartoœæ flagi informuj¹cej, którego gracza tura aktualnie trwa.
	 * @return - zwracana wartoœæ
	 */
	public boolean isYourTurn() {
		return yourTurn;
	}

	/**
	 * Metoda ustawiaj¹ca wartoœæ flagi informuj¹cej, którego gracza tura aktualnie trwa.
	 * @param yourTurn - ustawiana wartoœæ
	 */
	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
		System.out.println("B: Your turn set to "+yourTurn);
	}
	
	/**
	 * Metoda zwracaj¹ca obiekt klasy kontrolera g³ownego okna aplikacji. 
	 * @return - zwracany obiekt
	 */
	public GameWindowController getGameWindowController() {
		return gameWindowController;
	}

	/**
	 * Metoda ustawiaj¹ca obiekt klasy kontrolera g³ównego okna aplikacji.
	 * @param gameWindowController - ustawiany obiekt
	 */
	public void setGameWindowController(GameWindowController gameWindowController) {
		this.gameWindowController = gameWindowController;
	}
	
}
