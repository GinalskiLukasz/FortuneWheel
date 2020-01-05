package model;

import javafx.concurrent.Task;
import javafx.scene.image.ImageView;

/**
 * Klasa w¹tku odczytuj¹cego wartoœæ aktualnego pola z ko³a fortuny
 */
public class FieldValuesReader extends Task<Void>{
	
	
	private ImageView image;
	private double position;
	private int spins, fieldNumber;
	private Wheel wheel;
	
	
	public FieldValuesReader(Wheel wheel) {
		this.wheel=wheel;
		this.image=wheel.getImage();
	}

	/* (non-Javadoc)
	 * @see javafx.concurrent.Task#call()
	 * Metoda odczytuj¹ca wartoœæ aktualnego pola z ko³a fortuny
	 *  
	 */
	@Override
	protected Void call() throws Exception {
		
		
		while (wheel.isAnimationRunningFlag())
		{

			position=image.rotateProperty().get();
			spins = (int)position/360;
			position= position- spins*360;
			fieldNumber = (int)position/15;
			String text = wheel.getFieldValue(23-fieldNumber);		
			updateMessage(text);			
		}		
		return null;	
	}
}
