package model;

import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Klasa w¹tku odpowiadaj¹cego za animacjê ods³aniania litery na tablicy
 */
public class LetterReverser extends Task<Void>{
	
	
	Board board;
	int letterNumber;
	int sleepDuration;
	
	
	/**
	 * Konstruktor klasy LetterReverser.
	 * @param board - tablica z literami
	 * @param letterNumber - numer litery
	 * @param sleepDuration - czas opóŸnienia pomiêdzy ods³oniêciem kolejnych liter
	 */
	public LetterReverser(Board board, int letterNumber, int sleepDuration) {
		this.board=board;
		this.letterNumber=letterNumber;
		this.sleepDuration=sleepDuration;
	}
	

	/* (non-Javadoc)
	 * @see javafx.concurrent.Task#call()
	 * Metoda odpowiadaj¹ca za animacjê odwracania litery.
	 */
	@Override
	protected Void call() throws Exception {
		TimeUnit.MILLISECONDS.sleep(sleepDuration*letterNumber);
		ScaleTransition s1= new ScaleTransition();
		s1.setDuration(Duration.millis(300));
		s1.setFromX(1);
		s1.setToX(0);
		
		ScaleTransition s2= new ScaleTransition();
		s2.setDuration(Duration.millis(300));
		s2.setFromX(0);
		s2.setToX(1);
		
		ScaleTransition s3= new ScaleTransition();
		s3.setDuration(Duration.millis(300));
		s3.setFromX(0);
		s3.setToX(0);
		s3.setOnFinished(e->{
			board.getLabels()[letterNumber].setVisible(true);
			board.getRectangles()[letterNumber].setFill(Color.IVORY);
		});
		
		ScaleTransition s4= new ScaleTransition();
		s4.setDuration(Duration.millis(300));
		s4.setFromX(0);
		s4.setToX(1);

		SequentialTransition seq1 = new SequentialTransition(board.getRectangles()[letterNumber],s1,s2);
		SequentialTransition seq2 = new SequentialTransition(board.getLabels()[letterNumber],s3,s4);
		
		Animation animation=seq1;
		Animation animation2=seq2;
		
		
		animation.play();
		animation2.play();		
		
		return null;
	
	}
	
	
	
	
}
