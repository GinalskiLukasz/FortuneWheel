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
	 * Metoda okre�la obiekt Main
	 * @param main obiekt klasy main
	 */
	public void setMain (Main main){
		this.main=main;
	}

	/**
	 * Metoda okre�la obiekt PrimaryStage
	 * @param primaryStage obiekt klasy main primaryStage
	 */
	public void setPrimaryStage(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	
	/**
	 * Metoda obs�uguj�ca naci�ni�cie przycisku start gry.
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
			primaryStage.setTitle("Ko�o Fortuny - Gracz: " + nick);
		} 
		
		catch (IOException | InterruptedException e) {
//		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	/**Metoda okre�la nazw� gracza
	 * @return nazwa gracza 
	 */
	private String getUserName() {
		TextInputDialog textInputDialog = new TextInputDialog("Gracz A");
		textInputDialog.setTitle("Zaczynamy gr�!");
		textInputDialog.setHeaderText("Podaj sw�j nick");
		textInputDialog.setContentText("Nick: ");
		Optional<String> result = textInputDialog.showAndWait();
		nick = result.get();
		return result.orElse("Gracz A");
		}
	
	/**
	 * Metoda obs�uguj�ca naci�ni�cie przycisku koniec gry.
	 */
	public void exit (){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Na pewno chcesz zamkn�� gr�?");
		alert.setHeaderText("Potwierd�");
		alert.setContentText("Na pewno chcesz zamkn�� gr�?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    primaryStage.close();
		}
	}	
	public void showInstruction (){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Ko�o Fortuny");
		alert.setHeaderText("Zasady Gry");
		alert.setContentText(
				" - Gra sk�ada si� z 5 rund \n\n" + 
				" - W ka�dej rundzie losowane jest wsp�lne dla obu graczy has�o do odgadni�cia \n\n" +
				" - Gracz ropoczynaj�c swoj� rund� ma do wyboru: \n" + 
				" - 1) Kr�cenie ko�em \n" + 
				" - 2) Odgadni�cie has�a (zysk: 2000 PLN)\n" + 
				" - 3) Kupienie samog�oski (koszt: 1000 PLN) \n\n" + 
				" - Gdy gracz kr�ci ko�em mo�e wylosowa�: \n" + 
				" - 1) Kwot� pieni�dzy. Nast�pnie w przypadku gdy gracz poda znajduj�c� si� w ha�le sp�g�osk� otrzyma t� kwot� pomno�on� przez liczb� podanej litery w ha�le  \n" + 
				" - 2) Nagrod� (Skuter, Wycieczk�, AGD). Nagroda jest przyznawana graczowi, gdy poda liter� znajduj�c� si� w ha�le \n" + 
				" - 3) Bankrut. Gracz traci wszystkie zgromadzone �rodki. Zdobyte nagrody zostaj�. \n" + 
				" - 4) Stop. Gracz traci kolejk�. \n\n" + 
				" - Runda ko�czy si� w momencie gdy, kt�ry� z graczy odgadnie has�o. \n\n" + 
				" - Pieni�dze z rundy, gracza, kt�ry odgad� has�o przenoszone s� do puli ��cznej i przechodz� do kolejnej rundy \n\n" + 
				" - Pieni�dze z rundy, gracza, kt�ry nie odgad� has�a przepadaj� i nie przechodz� do kolejnej rundy \n\n" + 
				" - Gr� wygrywa gracz, kt�ry w 5 rundach uzbiera najwi�cej pieni�dzy. Tylko on zabierze pieni�dze do domu i zapisze si� w rankingu graczy   \n\n\n"  +
				" Powodzenia!   \n"  
				
				);

		alert.show();
		
		
	}	
	
	
}
