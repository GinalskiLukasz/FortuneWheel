package controller;
	
	
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * Klasa g³ówna. Uruchamia program oraz okno startowe
 */
public class Main extends Application {
	private Main main;
	private Stage primaryStage;
	
	
	/**
	 *Metoda okreœla stage g³ówny
	 */
	public void start(Stage primaryStage) throws Exception
	{
		this.primaryStage = primaryStage;   //tutaj okreslamy primary stage
		mainWindow();
		
			}
	
	/**
	 * Metoda wywo³uje okno startowe oraz okreœla kontroler tego okna
	 */
	public void mainWindow()
	{
		FXMLLoader loader = new FXMLLoader(
				Main.class.getResource("/view/StartWindow.fxml"));
		
		try {
			AnchorPane pane = loader.load();
			primaryStage.setMinWidth(500.0);
			primaryStage.setMinHeight(600.0);
			
			Scene scene = new Scene(pane);
			StartWindowController OknoStartController = loader.getController();
			OknoStartController.setMain(this);
			OknoStartController.setPrimaryStage(primaryStage);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Ko³o Fortuny");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}