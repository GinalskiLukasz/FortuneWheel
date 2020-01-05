
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.Board;
import model.ChatDecoder;
import model.Game;
import model.PasswordDataBase;
import model.Wheel;


/**
 * G��wna klasa gry. Kontroler okna g��wnego gry. Odpowiada za obs�ug� zdarze� wywo�ywanych przez graczy.
 */
public class GameWindowController {
	private Main main;
	private Stage primaryStage;
	@FXML	Label A_totalpoints;
	@FXML	Label A_roundpoints;
	@FXML	Label B_totalpoints;
	@FXML	Label B_roundpoints;
	@FXML	Pane inputPane;
	@FXML	Pane wheelPane;
	@FXML	Pane choosePane;
	@FXML	Label turnLabel;
	@FXML	Label activePlayer;
	@FXML	Label A_totalpoints_label;
	@FXML	Label A_roundpoints_label;
	@FXML	Label B_totalpoints_label;
	@FXML	Label B_roundpoints_label;
	@FXML	Button goWhellButton;
	@FXML	Button givePasswordButton;
	@FXML	Button buyButton;
	@FXML	Button giveButton;
	@FXML	Button newRound;
	@FXML	Label infoLabel;
	@FXML 	Button goDownButton;
	@FXML 	ImageView image;
	@FXML 	Button showPasswordButton;
	@FXML 	Button checkPasswordButton;
	@FXML 	Label resultLabel;
	@FXML 	Label label;
	@FXML 	Button letButton;
	@FXML 	Rectangle rectangle;
	@FXML 	GridPane gridPane;
	@FXML 	TextField letterText;
	@FXML 	TextField passwordText;
	@FXML 	Button stopButton;
	@FXML 	ImageView imageSkuter;
	@FXML 	ImageView imageAGD;
	@FXML 	ImageView imageSummer;
	@FXML	TextField messageTextField;
	@FXML	Label welcomeLabel;
	@FXML	WebView webViewMessages;

	@FXML	ImageView sendImageView;
	private String playerName;
	private double wheelPressedX, wheelPressedY;
	private double wheelPressedAngle;
	private double cursorOffset;
	private boolean dragBeginFlag = true;
	private double oldRot;
	private double newRot;	
	private Date oldTime;
	private Date newTime;
	private double wheelVelocity;
	private Wheel wheel;
	private Board board;
	private PasswordDataBase passwordDataBase;
	private boolean yourTurn=true;
	private String userName = "";
	private int UID;
	private String senderName;
	private int senderUID;
	private String host;
	private final int PORT = 6060;
	private Socket socket;
	private BufferedReader inputBufferedReader;
	private PrintWriter outputPrintWriter;
	private final int PROTOCOL_PREFIX_LENGTH = 3;
	private Document messagesLayout;
	
	Game game = new Game(1, getUserName(), 0,0);
	
	
	/**
	 * Okre�la Obiekt main 
	 * @param main obiekt klasy main
	 */
	public void setMain(Main main) {
		this.main = main;
	}

	/**
	 * Okre�la Obiekt PrimaryStage
	 * @param primaryStage obiekt klasy primaryStage
	 */
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	

	/**
	 * Metod� buduj�ca g��wne obiekty gry: ko�o, tablic�, chat, baz� danych hase�.
	 */
	@FXML
	private void initialize() {
		wheel = new Wheel();
		board = new Board();
		passwordDataBase = new PasswordDataBase(); 
		wheel.setImage(image);
		wheel.setResultLabel(resultLabel);
		wheel.setGiveButton(giveButton);
		wheel.setYourTurn(yourTurn);
		wheel.setGameWindowController(this);
		ObservableList<Node> boardChildren = gridPane.getChildren();
		board.setBoardChildren(boardChildren);
		board.setCurrentPassword(passwordDataBase.getPassword());
		String welcome = "Napisz co� do konkurenta!";
		messagesLayout = Jsoup.parse("<html><head><meta charset='UTF-8'>"
				+ "</head><body><ul><li class=\"welcome\"><div class=\"message\"><div class=\"content\">" + welcome
				+ "</div></div></li></ul></body></html>", "UTF-16", Parser.xmlParser());
		webViewMessages.getEngine().loadContent(messagesLayout.html());
		webViewMessages.getEngine().setUserStyleSheetLocation(getClass().getResource("application.css").toString());
	}

	
	
	/**
	 * Metoda zwraca nazwe gracza
	 * @return nazwa gracza
	 */
	
	public String getUserName() {
		return userName;
	}

	/**
	 * Metoda okre�la nazw� gracza
	 * @param userName nazwa gracza
	 */
	public void setUserName(String userName) {

		this.userName = userName;
		welcomeLabel.setText("Hello " + this.userName + "!");
//		Image myImage = new Image(new File("res/chatSymbol.png").toURI().toString());
//		ImagePattern pattern = new ImagePattern(myImage);
//		circleImage.setFill(pattern);
		playerName = userName;

	}

	/** 
	 * Metoda zwraca "hosta" gry
	 * @return host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Metoda okre�la "hosta" gry
	 * @param host host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Metoda zamykaj�ca socket
	 * @throws IOException obs�uga wyj�tku
	 */
	public void closeSocket() throws IOException {
		socket.close();
	}

	/**
	 * G��wna metoda obs�uguj�ca komunikaty wysy�ane mi�dzy graczami 
	 */
	public void run() {

		try {
			socket = new Socket(host, PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			inputBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			outputPrintWriter = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		Task<Void> task = new Task<Void>() {

			@Override
			public Void call() throws IOException {

				while (true) {
					String msg = inputBufferedReader.readLine();
					System.out.println(msg);
					if (msg.startsWith("RDY")) {
						outputPrintWriter.println(userName);

					} else if (msg.startsWith("UID")) {
						UID = Integer.parseInt(msg.substring(PROTOCOL_PREFIX_LENGTH));

					} else if (msg.startsWith("MSG")) {
						ChatDecoder decoder = new ChatDecoder(UID, senderUID, senderName);
						addMessage(decoder.toHTML(decoder.decodeUID(msg)));

					} else if (msg.startsWith("XBXRoundPoints")) {

						msg = msg.substring(14);
						game.setPlayerBpoints(Integer.parseInt(msg));
						
						Platform.runLater(
								  () -> {
									  									  
									  refreshData();
								  }
								);

					} else if (msg.startsWith("XBXTotalPoints")) {
						msg = msg.substring(14);
						game.setPlayerBpointsTotal(Integer.parseInt(msg));
						
						Platform.runLater(
								  () -> {
									  									  
									  refreshData();
								  }
								);
						
					}else if (msg.startsWith("XBXChangeTurn")) {
						
						Platform.runLater(
								  () -> {
									  									  
									  startTurn();
								  }
								);

					}else if (msg.startsWith("XBXRefreshBoard")) {
						String l = msg.substring(15);
						Platform.runLater(
								  () -> {
									  									  
									  board.checkLetter(l);
								  }
								);

					}
					else if (msg.startsWith("XBXSynchronizeWheel")) {
						String l = msg.substring(19);
						double wheelVelocity=Double.parseDouble(l);
						Platform.runLater(
								  () -> {
									  									  
									  wheel.wheelRotate(wheelVelocity);
								  }
								);

					}
					
					else if (msg.startsWith("XBXSynchronizeDraggedWheel")) {
						String l = msg.substring(26);
						double wheelRotate=Double.parseDouble(l);
						Platform.runLater(
								  () -> {
									  									  
									  wheel.setRotate(wheelRotate);
									  
								  }
								);

					}
					
					else if (msg.startsWith("XBXShowPassword")) {
						Platform.runLater(
								  () -> {
									  									  
									 board.showPassword();
									game.setRound(game.getRound()+1);
									game.setPlayerApoints(0);
									refreshData(); 
								  }
								);

					}else if (msg.startsWith("XBXRound")) {
						String r = msg.substring(8);
						game.setRound(Integer.parseInt(r));
						Platform.runLater(
								  () -> {
									  									  
									  refreshData(); 
								  }
								);

					}
					
					
					else if (msg.startsWith("XBXSynchronizePassword")) {
						String l = msg.substring(22);
						Platform.runLater(
								  () -> {
									  									  
									  board.setCurrentPassword(l);
								  }
								);

					}
					
					else if (msg.startsWith("XBXRequestPassword")) {
						
						outputPrintWriter.println("XAXSynchronizePassword"+board.getCurrentPassword());

					}
					
					else if (msg.startsWith("XBXSynchronizeUsedPasswords")) {
						String l = msg.substring(27);
						passwordDataBase.addUsedPassword(l);
					}
					
					else if (msg.startsWith("XBXINFO")) {
						String info = msg.substring(7);
						Platform.runLater(
								  () -> {
									  									  
									  infoLabel.setText(info);
									  rankUpdate();
								  }
								);
					}

				}

			}

		};
		new Thread(task).start();
		
	}
	/**
	 *  Metoda obs�uguj�ca chat. Naci�ni�cie entera
	 */
	@FXML
	private void sendImageView_MouseReleased()  {
		if (messageTextField.getLength() == 0)
			return;
		outputPrintWriter.println(messageTextField.getText());
		messageTextField.clear();
		
	}

	/**
	 *  Metoda obs�uguj�ca chat. Naci�ni�cie ikony wysy�ania wiadomo�ci.
	 */
	@FXML
	private void messageTextField_KeyPressed() {
			sendImageView_MouseReleased();
	}

	/**
	 * Metoda obs�uguj�ca chat. Buduje obiekt klasy Element
	 * @param message
	 */
	private void addMessage(Element message) {
		Element wrapper = messagesLayout.getElementsByTag("ul").first();
		wrapper.appendChild(message);
		Platform.runLater(new Runnable() {
			public void run() {
				webViewMessages.getEngine().loadContent(messagesLayout.html());

			}
		});
	}
	
	/**
	 * Metoda obs�uguj�ca chat. Przesuwanie tekstu w d�. 
	 */
	@FXML
	private void goDown()
	{
		webViewMessages.getEngine().executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

/**
 * Metoda obs�uguj�ca kr�cenie ko�em. Naci�ni�cie przycisku myszy
 * @param evt naci�ni�cie przycisku myszy
 */
public void wheelPressed(MouseEvent evt) {
		
	
		wheelPressedX = evt.getSceneX()-image.getFitWidth()/2-wheelPane.getLayoutX();
		wheelPressedY = evt.getSceneY()-image.getFitHeight()/2-wheelPane.getLayoutY();
		wheelPressedAngle = Math.toDegrees(Math.atan2(wheelPressedY, wheelPressedX));
		cursorOffset =  wheelPressedAngle - wheel.getRotate();		
	}
	
	/**
	 * Metoda obs�uguj�ca kr�cenie ko�em. Obliczenie pr�dko�ci ruchu. 
	 * @param evt Zwolnienie przycisku myszy.
	 */
	public void wheelDragged(MouseEvent evt) 
		{
		wheelPressedX = evt.getSceneX()-image.getFitWidth()/2-wheelPane.getLayoutX();
		wheelPressedY = evt.getSceneY()-image.getFitHeight()/2-wheelPane.getLayoutY();		
		wheelPressedAngle = Math.toDegrees(Math.atan2(wheelPressedY, wheelPressedX));
		wheel.setRotate(wheelPressedAngle-cursorOffset);
		synchronizeDraggedWheel(wheelPressedAngle-cursorOffset);
		newRot= wheel.getRotate();	
		newTime=new Date();	
		if (dragBeginFlag) 
			{
				oldRot=newRot;
				oldTime=newTime;
				dragBeginFlag=false;
			}	
		else 
			{
			double distance= Math.abs(oldRot-newRot);
			double time = newTime.getTime()-oldTime.getTime();
			this.wheelVelocity = 1000*distance/time;
			oldRot=newRot;
			oldTime=newTime;
			}
		}
		
				
	/**
	 * Metoda uruchamiaj�ca animacj� kr�cenia ko�em.
	 */
	public void wheelReleased() {
		
		
		if (wheelVelocity<200)
		{		
			infoLabel.setText("Zbyt wolno, zakr�� mocniej!");
		}
		else if (wheelVelocity==Double.POSITIVE_INFINITY || wheelVelocity>1600)
		{
			wheel.wheelRotate(1600);
			wheelPane.setDisable(true);
			image.setDisable(true);	
			synchronizeWheel(1600);
		}
		else
		{
			wheel.wheelRotate(wheelVelocity);
			wheelPane.setDisable(true);
			image.setDisable(true);	
			synchronizeWheel(wheelVelocity);			
		}
		
		
	}
	
	
	/**
	 * Motoda zatrzymuj�ca animacje. 
	 */
	public void stopAnimation() {
		wheel.stopRotate();
	}
	
	/**
	 * Motoda zatrzymuj�ca animacje przyciskiem.
	 * @param evt naci�ni�cie przycisku 
	 */
	public void stopAnimationByKey(KeyEvent evt) {	
		wheel.stopRotate();
	}	
	
	/**
	 * Metoda obs�uguj�c� opcj� podania sp�g�oski przez gracza 
	 * @param l sp�g�oska
	 * @throws InterruptedException obs�uga wyj�tku
	 */
	public void checkLetter(String l) throws InterruptedException  {
		
		String letter = l;					
		

					if (letter.length()>1) 
					{	
						showAlert("Poda�e� za du�o znak�w. Podaj SPӣG�OSK�!", "Za du�o znak�w");
						give();
								
					}
					
					else if (letter.length()==0)
					{
						showAlert("Nie poda�e� znaku. Podaj SPӣG�OSK�!", "Podaj znak");
						give();
					}
						
					else if (Character.isDigit(letter.charAt(0)))
					{
						showAlert("Poda�e� cyfr�. Podaj SPӣG�OSK�!", "To jest cyfra");
						give();
						
					}
						
					else if (!Character.isLetter(letter.charAt(0)))
					{
						showAlert("Poda�e� z�y znak. Podaj SPӣG�OSK�!", "To nie jest litera");
						give();
						
					}
					else		
					{
						
						
						if (letter.equals("a") || 
								letter.equals("�") ||	
								letter.equals("e") ||
								letter.equals("�") ||
								letter.equals("i") ||
								letter.equals("o") ||
								letter.equals("u") ||
								letter.equals("�") ||
								letter.equals("y"))		
						
						{
							showAlert("Poda�e� samog�osk�. Podaj SPӣG�OSK�!", "To nie jest sp�g�oska");
							give();
									
						}
						else
						{
							board.checkLetter(letter);
							if(board.getCounter() >0) {
								game.setPlayerApoints(game.getPlayerApoints()+board.getCounter()*game.getTemporaryCash());
								infoLabel.setText("Zarobi�e�: " + board.getCounter()*game.getTemporaryCash() + ". Tura przeciwnika.");
								refreshData();
								sendData();
								refreshBoard(letter);
								changeTurn();
								
							}
							else {
								infoLabel.setText("Nie ma tej litery w ha�le. Tura przeciwnika.");
								changeTurn();
							
							}
						}
						}	
				
		
	}
	
	/**
	 * Metoda obs�uguj�c� opcj� podania samog�oski przez gracza 
	 * @param l samog�oska
	 * @throws InterruptedException obs�uga wyj�tku 
	 */
public void checkLetter2(String l) throws InterruptedException {
		
		String letter = l;					
		
			
					if (letter.length()>1) 
					{	

						showAlert("Poda�e� za du�o znak�w. Podaj SAMOG�OSK�!", "Za du�o znak�w");
						buy();
				
					}
					
					else if (letter.length()==0)
					{
						showAlert("Nie poda�e� znaku. Podaj SAMOG�OSK�!", "Podaj znak");
						buy();
					}
						
					else if (Character.isDigit(letter.charAt(0)))
					{
						showAlert("Poda�e� cyfr�. Podaj SAMOG�OSK�!", "To jest cyfra");
						buy();
					}
						
					else if (!Character.isLetter(letter.charAt(0)) || letter.equals(""))
					{
						showAlert("Poda�e� z�y znak. Podaj SAMOG�OSK�!!", "To nie jest litera");
						buy();
					}
					else		
					{
						
						
						if (letter.equals("a") || 
								letter.equals("�") ||	
								letter.equals("e") ||
								letter.equals("�") ||
								letter.equals("i") ||
								letter.equals("o") ||
								letter.equals("u") ||
								letter.equals("�") ||
								letter.equals("y"))		
						
						{			
									game.setPlayerApoints((game.getPlayerApoints()) - 1000);
									board.checkLetter(letter);
									if(board.getCounter() >0) {
									
										goWhellButton.setDisable(true);
										givePasswordButton.setDisable(false);
										buyButton.setDisable(true);
										infoLabel.setText("Oto kupiona samog�oska. Podaj has�o.");
										refreshData();
										sendData();
										refreshBoard(letter);
										givePassword();
									}
									else {
										infoLabel.setText("Nie ma tej litery w ha�le. Tura przeciwnika.");
										refreshData();
										sendData();
										changeTurn();
									
									}
						}
						else
						{
							showAlert("Poda�e� sp�g�osk�. Podaj SAMOG�OSK�!", "To nie jest samog�oska");
							buy();
						}
						}	
				
		
	}
	
	/**
	 * Wy�wi�tla okno alarmowe. 
	 * @param text1 tre�� komunikatu
	 * @param text2 nag��wek komunikatu
	 */
	public void showAlert(String text1, String text2) {
		Alert alert = new Alert(AlertType.WARNING, text1);
		alert.setHeaderText(text2);
		alert.showAndWait();

	}
	
	/**
	 *  Wywo�uje odkrycie ca�ego has�a 
	 *  
	*/
	public void showPassword() {
		board.showPassword();
	}
	/**
	 * Ustawia nazw� gracza na tablicy z wynikami 
	 * @param playerName nazwa gracza
	 */
	public void setPlayerName(String playerName) {
		A_totalpoints_label.setText(playerName + " Punkty ��cznie");
		A_roundpoints_label.setText(playerName + " Punkty w rundzie");
	
	}

	/**
	 * Obs�uguje naci�ni�cie przycisku "Zakr�� ko�em"
	 */
	@FXML
	public   void goWhell() {
	
		
		infoLabel.setText("Zakr�� ko�em.");
		wheelPane.setDisable(false);
		image.setDisable(false);
		goWhellButton.setDisable(true);	
		givePasswordButton.setDisable(true);	
		buyButton.setDisable(true);	
		
	
	}
	
	/**
	 * Obs�uguje naci�ni�cie przycisku "Podaj has�o"
	 * @throws InterruptedException obs�uga wyj�tku
	 */
	@FXML
	public   void givePassword() throws InterruptedException {
		//checkPassword();
		infoLabel.setText("Podaj has�o.");
		TextInputDialog textInputDialog = new TextInputDialog("Podaj has�o");
		textInputDialog.setTitle("Podaj has�o");
		textInputDialog.setHeaderText("Podaj has�o");
		textInputDialog.setContentText("Podaj has�o");
		Optional<String> result = textInputDialog.showAndWait();
//		String password = result.get();
//		board.checkPassword(password);
		
		
		try 
		{
			String password = result.get();
			board.checkPassword(password);
		}
		catch (NoSuchElementException e)
		{
			givePassword();
		}
		
			if (board.getChecker()) 
			{
				infoLabel.setText("Odgad�e� has�o! Tura przeciwnika.");
				
				game.setPlayerApointsTotal(game.getPlayerApoints() + 2000);
				game.setPlayerApoints(0);
				game.setPlayerBpoints(0);
				refreshData();
				sendData();
				changeRound();
			}
			else 
			{
			infoLabel.setText("Has�o nierpawid�owe. Tura przeciwnika.");
			changeTurn();
			}
		
	}
	
	/**
	 * Obs�uguje naci�ni�cie przycisku "Kup samog�osk�"
	 * @throws InterruptedException obs�uga wyj�tku
	 */
	@FXML
	public void buy() throws InterruptedException {
		
		if (game.getPlayerApoints() >999)
		
		{
			infoLabel.setText("Podaj samog�osk�");
			TextInputDialog textInputDialog = new TextInputDialog("Podaj samog�osk�");
			textInputDialog.setTitle("Podaj samog�osk�");
			textInputDialog.setHeaderText("Podaj samog�osk�");
			textInputDialog.setContentText("Podaj samog�osk�");
			Optional<String> result = textInputDialog.showAndWait();
			
			try 
			{
				String letter = result.get();
				checkLetter2(letter);
				refreshData();
				sendData();
			}
			catch (NoSuchElementException e)
			{
				buy();
			}
		}
		
		else 
		{
			infoLabel.setText("Masz za ma�o pieni�dzy. Potrzebujesz 1000 PLN!");
			
		}
	}
	

	/**
	 * Obs�uguje opcj�  podawania sp�g�oski przez gracza.
	 * @throws InterruptedException obs�uga wyj�tku
	 */
	@FXML
	public void give() throws InterruptedException {  
			if (resultLabel.getText().equals("SKUTER") && game.isSkuter())
					{
						showAlert("Masz ju� tak� nagrod�, zakr�� jeszcze raz ko�em.", "Spr�buj ponownie");
						goWhell();
					}
			else if (resultLabel.getText().equals("AGD") && game.isAgd())
					{
						showAlert("Masz ju� tak� nagrod�, zakr�� jeszcze raz ko�em.", "Spr�buj ponownie");
						goWhell();
					}
			else if (resultLabel.getText().equals("WYCIECZKA") && game.isSummer())
					{
						showAlert("Masz ju� tak� nagrod�, zakr�� jeszcze raz ko�em.", "Spr�buj ponownie");
						goWhell();
				
					}
			
			else if (resultLabel.getText().equals("NAGRODA") && game.isNagroda())
			{
				showAlert("Masz ju� tak� nagrod�, zakr�� jeszcze raz ko�em.", "Spr�buj ponownie");
				goWhell();
		
			}
				
			else if (resultLabel.getText().equals("STOP")) {
				
				infoLabel.setText("Straci�e� kolejk�!");
				changeTurn();
			}
			else if (resultLabel.getText().equals("BANKRUT")) {
				infoLabel.setText("Bankrutujesz i straci�e� kolejk�!");
				game.setPlayerApoints(0);
				game.setPlayerApointsTotal(0);
				refreshData();
				sendData();
				changeTurn();
			}
			else if (resultLabel.getText().equals("WYCIECZKA") || resultLabel.getText().equals("AGD") || resultLabel.getText().equals("SKUTER") || resultLabel.getText().equals("NAGRODA")) 
			{
		
				infoLabel.setText("Wylosowa�e� nagrod�. Podaj sp�g�osk�.");
				TextInputDialog textInputDialog = new TextInputDialog("Podaj sp�g�osk�");
				textInputDialog.setTitle("Podaj sp�g�osk�");
				textInputDialog.setHeaderText("Podaj sp�g�osk�");
				textInputDialog.setContentText("Podaj sp�g�osk�");
				Optional<String> result = textInputDialog.showAndWait();
				
				try 
				{
					String letter = result.get();
					checkLetter(letter);
				}
				catch (NoSuchElementException e)
				{
					give();
				}
				
					if (board.getCounter() > 0)
					{
						
											infoLabel.setText("Nagroda jest Twoja! Tura przeciwnika.");
											
											if (resultLabel.getText().equals("SKUTER"))
														{
														
														imageSkuter.setVisible(true);	
														game.setSkuter(true);
														}
											
											else if (resultLabel.getText().equals("AGD")) 
												
														{
														imageAGD.setVisible(true);	
														game.setAgd(true);
														}
											else if (resultLabel.getText().equals("WYCIECZKA")) 
												
														{
														imageSummer.setVisible(true);	
														game.setSummer(true);
														}
											else if (resultLabel.getText().equals("NAGRODA")) 
												
											{
											game.setNagroda(true);	
											int temp = (int)Math.floor(Math.random()*3);
											if (temp==0)
											{
												if (game.isSkuter()) infoLabel.setText("Wygra�e� kolejny skuter!");
												else 
												{
													imageSkuter.setVisible(true);	
													game.setSkuter(true);
												}
												
											}
											else if (temp==1)
											{
												if (game.isAgd()) infoLabel.setText("Wygra�e� kolejny zestaw AGD!");
												else
												{
													imageAGD.setVisible(true);	
													game.setAgd(true);
												}
												
											}
											else
											{
												if (game.isSummer()) infoLabel.setText("Wygra�e� kolejn� wycieczk�!");
												else
												{
													imageSummer.setVisible(true);	
													game.setSummer(true);
												}
												
											}
											}
					}
					else
					{
					infoLabel.setText("Brak takiej sp�g�oski. Koniec tury.");
					}
			}
			else 
			{
				if (resultLabel.getText().equals("?500")) 
				{
					int temp =(int)Math.ceil(Math.random()*4);
					infoLabel.setText("Wylosowa�e� " + temp + "*500" + ", podaj sp�g�osk�.");
					game.setTemporaryCash(temp*500);
					
					TextInputDialog textInputDialog = new TextInputDialog("Podaj sp�g�osk�");
					textInputDialog.setTitle("Podaj sp�g�osk�");
					textInputDialog.setHeaderText("Podaj sp�g�osk�");
					textInputDialog.setContentText("Podaj sp�g�osk�");
					Optional<String> result = textInputDialog.showAndWait();
					try 
					{
						String letter = result.get();
						checkLetter(letter);
					}
					catch (NoSuchElementException e)
					{
						give();
					}
				}
				
				else
				{
					infoLabel.setText("Wylosowa�e� " + resultLabel.getText() + ", podaj sp�g�osk�.");
					game.setTemporaryCash(Integer.parseInt(resultLabel.getText()));
					
					TextInputDialog textInputDialog = new TextInputDialog("Podaj sp�g�osk�");
					textInputDialog.setTitle("Podaj sp�g�osk�");
					textInputDialog.setHeaderText("Podaj sp�g�osk�");
					textInputDialog.setContentText("Podaj sp�g�osk�");	
				
					Optional<String> result = textInputDialog.showAndWait();
					
					try 
					{
						String letter = result.get();
						checkLetter(letter);
					}
					catch (NoSuchElementException e)
					{
						give();
					}		
				}				
			}
	}
	
	/**
	 * Metoda rozpoczyna now� tur�. 
	 */
	public   void startTurn() {
		infoLabel.setText("Wybierz, co chcesz zrobi�.");
		goWhellButton.setDisable(false);
		givePasswordButton.setDisable(false);
		buyButton.setDisable(false);
		wheel.switchTurn();
		
	}
	
	/**
	 * Metoda aktualizuje dane wy�wietlane na tablicy z wynikami.  
	 */
	@FXML
	public  synchronized void refreshData() {
		A_roundpoints.setText(Integer.toString(game.getPlayerApoints()));
		A_totalpoints.setText(Integer.toString(game.getPlayerApointsTotal()));
		B_roundpoints.setText(Integer.toString(game.getPlayerBpoints()));
		B_totalpoints.setText(Integer.toString(game.getPlayerBpointsTotal()));
		turnLabel.setText(Integer.toString(game.getRound()));
	}
	
	/**
	 *  Metoda wysy�a informacj� o punktach zdobytych przez gracza A do gracza B 
	 */
	public  synchronized void sendData() {
		outputPrintWriter.println("XAXRoundPoints" + game.getPlayerApoints());
		outputPrintWriter.println("XAXTotalPoints" + game.getPlayerApointsTotal());
		outputPrintWriter.println("XAXRound" + game.getRound());
	}
	
	/**
	 * Metoda wywo�uj�ca zmian� tury 
	 */
	public  synchronized void changeTurn() {
		outputPrintWriter.println("XAXChangeTurn");
		wheel.switchTurn(); 
		goWhellButton.setDisable(true);
		givePasswordButton.setDisable(true);
		buyButton.setDisable(true);
		game.setTemporaryCash(0);
	}
	
		
	/**
	 * Metoda komunikacji. Wysy�a liter� podan� przez gracza A do gracza B i w synchronizowany spos�b uruchamia animacje w oknie gracza B
	 * @param letter podana litera 
	 */
	public  synchronized void refreshBoard(String letter) {
		outputPrintWriter.println("XAXRefreshBoard" + letter);
	}
	
	/**
	 * Metoda komunikacji. Wysy�a  si�� z jak� gracz A zakr�ci� ko�em i uruchamia animacj� kr�cenia ko�em  w oknie gracza B 
	 * @param wheelVelocity pr�dko�� obrotu ko�a
	 */
	public synchronized void synchronizeWheel(double wheelVelocity) {
		outputPrintWriter.println("XAXSynchronizeWheel"+ wheelVelocity);
	}
	
	/**
	 * Metoda komunikacji. Synchronizuje wy�wietlanie animacji u obu graczy
	 * @param wheelRotate naci�ni�cie myszy 
	 */
	public synchronized void synchronizeDraggedWheel(double wheelRotate) {
		outputPrintWriter.println("XAXSynchronizeDraggedWheel"+ wheelRotate);
	}
	
	/**
	 * Metoda wywo�uje zmian� rundy oraz w przypadku zako�czenia 5 rundy oblicza kto wygra� gr�. 
	 * @throws InterruptedException obs�uga wyj�tku
	 */
	public  synchronized void changeRound() throws InterruptedException {
		game.setRound(game.getRound() + 1);
		refreshData();
		if (game.getRound()<6)
		{
		outputPrintWriter.println("XAXShowPassword");
		newRound.setDisable(false);
		goWhellButton.setDisable(true);
		givePasswordButton.setDisable(true);
		buyButton.setDisable(true);
		}
		else
		{
			outputPrintWriter.println("XAXShowPassword");
			goWhellButton.setDisable(true);
			givePasswordButton.setDisable(true);
			buyButton.setDisable(true);	
			
					if (game.getPlayerApointsTotal() > game.getPlayerBpointsTotal())
					{
					infoLabel.setText("GRATULACJE, WYGRA�E� GR�!!! Pieni�dze i nagrody s� Twoje.");
					outputPrintWriter.println("XAXINFO"+ "PRZEGRYWASZ GR�. Przeciwnik odgad� ostatnie has�o.");
					rankUpdate();
					}
					else if (game.getPlayerApointsTotal() < game.getPlayerBpointsTotal())
					{
					infoLabel.setText("PRZEGRA�E� GR�:(");
					outputPrintWriter.println("XAXINFO"+ "Przeciwnik odgad� ostatnie has�o ale Ty WYGRYWASZ.");
					rankUpdate();
					}
					else
					{
					infoLabel.setText("REMIS! Dzielicie si� pieni�dzmi.");	
					outputPrintWriter.println("XAXINFO"+ "REMIS! Dzielicie si� pieni�dzmi.");
					rankUpdate();
					}
		}
	}
	
	/**
	 * Metoda wywo�uje rozpocz�cie nowej rundy gry
	 */
	@FXML
	public  synchronized void startNewRound()
	{
		infoLabel.setText("Zaczynamy kolejn� rund�. Zaczyna przeciwnik");
		passwordDataBase.addUsedPassword(board.getCurrentPassword());
		outputPrintWriter.println("XAXSynchronizeUsedPasswords"+board.getCurrentPassword());
		board.setCurrentPassword(passwordDataBase.getPassword());
		outputPrintWriter.println("XAXSynchronizePassword"+board.getCurrentPassword());
		changeTurn();
		newRound.setDisable(true);
		
	}
	/**
	 * Metoda powoduje zmian� has�a na nowe
	 */
	@FXML
	public  synchronized void changePassword()
	
	{
		board.setCurrentPassword(passwordDataBase.getPassword());			
	}
	
	/**
	 *  Metoda synchronizuje has�o u obu graczy 
	 */
	public  synchronized void requestPassword()
	
	{
		outputPrintWriter.println("XAXRequestPassword");
	}
	
	/**
	 * Metoda dodaje wynik zwyci�skiego gracza do bazy danych. 
	 */
	public  synchronized void rankUpdate()
	{
		
		try {
			
			String url = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
			String uzytkownik = "lginalsk";
			String haslo = "lginalsk";
			String nazwaSterownika = "oracle.jdbc.driver.OracleDriver";
			Class c = Class.forName(nazwaSterownika);
			System.out.println("Pakiet		: " + c.getPackage());
			System.out.println("Nazwa klasy	: " + c.getName());
			Connection polaczenie = DriverManager.getConnection(url, uzytkownik, haslo);
			Statement polecenie = polaczenie.createStatement();
			System.out.println("Autocommit: " + polaczenie.getAutoCommit());
			polecenie.execute("INSERT INTO ranking VALUES(" + game.getPlayerApointsTotal() + ", '" + playerName + "')");
			polaczenie.close();
			polecenie.close();
			}
		catch (Exception e)
			{
				System.out.println("B��d programu");
				System.out.println(e.getMessage());
				e.printStackTrace();
				return;
			}
			System.out.println("\n***Koniec programu***");
		}
}
