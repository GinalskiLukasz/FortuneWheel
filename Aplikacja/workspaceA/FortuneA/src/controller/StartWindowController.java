package controller;

import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Player;

/**
 * Kontroler okna startowego.
 */
public class StartWindowController {
	private Main main;
	private Stage primaryStage;

	@FXML Button ButtonStart;
	@FXML Button instructionButton;
	@FXML Button exitButton;
	@FXML  TableView<Player> rankingTable;
	
	@FXML private TableColumn<Player, String> PlayerColumn;
	@FXML private TableColumn<Player, String> PointsColumn;
	String nick;
	private ObservableList<Player> ranking = FXCollections.observableArrayList();
	
	/**
	 * Metoda inicjuje kolumny tabeli 
	 */
	public void initialize()
	{
		PlayerColumn.setCellValueFactory(
				new PropertyValueFactory<Player, String>("player"));
	
		PointsColumn.setCellValueFactory(
				new PropertyValueFactory<Player, String>("points"));
	
		
	
	}
		
	/**
	 * Metoda okreœla obiekt Main
	 * @param main obiekt klasy main
	 */
	public void setMain (Main main){
		this.main=main;
	}

	/**
	 * Metoda okreœla obiekt PrimaryStage
	 * @param primaryStage obiekt klasy main primaryStage
	 */
	public void setPrimaryStage(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	
	/**
	 * Metoda obs³uguj¹ca naciœniêcie przycisku start gry.
	 */
	@FXML public void startAction() {
		
		FXMLLoader loader = new FXMLLoader(
				Main.class.getResource("/view/GameWindow.fxml"));
		
		try {
			AnchorPane pane = loader.load();
			primaryStage.setMinWidth(500.0);
			primaryStage.setMinHeight(600.0);
			
			Scene scene = new Scene(pane);
			GameWindowController gameWindowController = loader.getController();
			gameWindowController.setUserName(getUserName());
			gameWindowController.setPlayerName(nick);
			gameWindowController.setMain(main);
			gameWindowController.setPrimaryStage(primaryStage);
//			gameWindowController.setHost("192.168.1.97");
			gameWindowController.setHost("localhost");
			gameWindowController.run();
			Thread.sleep(10);
			gameWindowController.requestPassword();

			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Ko³o Fortuny - Gracz: " + nick);
		} 
		
		catch (IOException | InterruptedException e) {
//		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	/**Metoda okreœla nazwê gracza
	 * @return nazwa gracza 
	 */
	private String getUserName() {
		TextInputDialog textInputDialog = new TextInputDialog("Gracz A");
		textInputDialog.setTitle("Zaczynamy grê!");
		textInputDialog.setHeaderText("Podaj swój nick");
		textInputDialog.setContentText("Nick: ");
		Optional<String> result = textInputDialog.showAndWait();
		nick = result.get();
		return result.orElse("Gracz A");
		}
	
	/**
	 * Metoda obs³uguj¹ca naciœniêcie przycisku koniec gry.
	 */
	public void exit (){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Na pewno chcesz zamkn¹æ grê?");
		alert.setHeaderText("PotwierdŸ");
		alert.setContentText("Na pewno chcesz zamkn¹æ grê?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    primaryStage.close();
		}
	}	
	public void showInstruction (){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Ko³o Fortuny");
		alert.setHeaderText("Zasady Gry");
		alert.setContentText(
				" - Gra sk³ada siê z 5 rund \n\n" + 
				" - W ka¿dej rundzie losowane jest wspólne dla obu graczy has³o do odgadniêcia \n\n" +
				" - Gracz ropoczynaj¹c swoj¹ rundê ma do wyboru: \n" + 
				" - 1) Krêcenie ko³em \n" + 
				" - 2) Odgadniêcie has³a (zysk: 2000 PLN)\n" + 
				" - 3) Kupienie samog³oski (koszt: 1000 PLN) \n\n" + 
				" - Gdy gracz krêci ko³em mo¿e wylosowaæ: \n" + 
				" - 1) Kwotê pieniêdzy. Nastêpnie w przypadku gdy gracz poda znajduj¹c¹ siê w haœle spó³g³oskê otrzyma tê kwotê pomno¿on¹ przez liczbê podanej litery w haœle  \n" + 
				" - 2) Nagrodê (Skuter, Wycieczkê, AGD). Nagroda jest przyznawana graczowi, gdy poda literê znajduj¹c¹ siê w haœle \n" + 
				" - 3) Bankrut. Gracz traci wszystkie zgromadzone œrodki. Zdobyte nagrody zostaj¹. \n" + 
				" - 4) Stop. Gracz traci kolejkê. \n\n" + 
				" - Runda koñczy siê w momencie gdy, któryœ z graczy odgadnie has³o. \n\n" + 
				" - Pieni¹dze z rundy, gracza, który odgad³ has³o przenoszone s¹ do puli ³¹cznej i przechodz¹ do kolejnej rundy \n\n" + 
				" - Pieni¹dze z rundy, gracza, który nie odgad³ has³a przepadaj¹ i nie przechodz¹ do kolejnej rundy \n\n" + 
				" - Grê wygrywa gracz, który w 5 rundach uzbiera najwiêcej pieniêdzy. Tylko on zabierze pieni¹dze do domu i zapisze siê w rankingu graczy   \n\n\n"  +
				" Powodzenia!   \n"  
				
				);

		alert.show();
		
		
	}	
	
	
}
