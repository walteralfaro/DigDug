package pruebaCliente;

import javax.swing.JDialog;
import javax.swing.JFrame;

import pruebaServer.AddQuestionConfirmationPackage;
import pruebaServer.AnswerQuestionPackage;
import pruebaServer.CategoryPackage;
import pruebaServer.CreateGamePackage;
import pruebaServer.NotifyPlayerJoinToAdminPackage;
import pruebaServer.PlayerDisconnectPackage;
import pruebaServer.Question;
import pruebaServer.QuestionsByCategoryPackage;
import pruebaServer.ResultsGamePackage;
import pruebaServer.StartGamePackage;
import pruebaServer.TopTenUserPackage;
import pruebaServer.UserLoginPackage;

public class ClientThread extends Thread {
	
	private LoginScreen loginscreen;
	private Integer userType;
	private String userName;
	private static JDialog JDialogScreen;
	private static JFrame JFrameScreen;
	private static Package packageIn;
	private static final int LOGINRESPONSEID = 1;
	private static final int CREATEGAMERESPONSEID = 2;
	private static final int PLAYERJOINRESPONSEID = 3;
	private static final int STARTGAMERESPONSEID = 4;
	private static final int CATEGORYRESPONSEID = 5;
	private static final int GAMERUNNINGID = 6;
	private static final int POINTSTABLERESPONSEID = 7;
	private static final int ENDCONNECTIONRESPONSEID = 8;
	private static final int QUESTIONSRESPONSEID = 9;
	private static final int ANSWERQUESTIONRESPONSEID = 10;
	private static final int ADDQUESTIONREESPONSEID = 11;
	private static final int ENDTIMEID = 12;
	private static final int RESULTSGAMEID = 13;
	private static final int ENDSERVERPACKAGEID = 14;
	private static final int PLAYERJOINEDNOTIFICATIONID = 15;
	private static final int PLAYERDISCONNECTEDID = 16;
	private Boolean endConnection = false;
	private Connection connection = Connection.getInstance();
	
	public ClientThread(LoginScreen loginscreen){
		this.loginscreen = loginscreen;
		
	}
	
	public void run() {
		

		try {
			
			GameCreatedAdminScreen gamecreated = null;
			JoinPlayerGameWindow joinplayergamewindow = null;
			RoundGameScreen roundgamescreen = null;
			while (!endConnection) {

				packageIn = (Package) connection.recievePackage();

				switch (packageIn.getPackageID()) {
				case LOGINRESPONSEID: // Respuesta de logeo del servidor.

					UserLoginPackage loginresponse = (UserLoginPackage) packageIn;
					userType = loginresponse.getUserType();
					userName = loginresponse.getUser();
					loginscreen.actionLogin(loginresponse);

					break;
					
				case CATEGORYRESPONSEID:
					CategoryPackage categoryresponse = (CategoryPackage) packageIn;
					if(categoryresponse.getIdScreen() == 1)
						((ChooseQuestionWindow) JDialogScreen).setCategories(categoryresponse);
					else
						((AddQuestionScreen)JFrameScreen).setCategories(categoryresponse);
					break;

				case QUESTIONSRESPONSEID:  
					
					QuestionsByCategoryPackage questionsresponse = (QuestionsByCategoryPackage) packageIn;
					((ChooseQuestionWindow) JDialogScreen).showQuestions(questionsresponse);
					
	
					break;
				case CREATEGAMERESPONSEID: // Creacion de partida
					CreateGamePackage creategameresponse = (CreateGamePackage) packageIn;
					if(creategameresponse.gameCreated() == true){
						roundgamescreen = new RoundGameScreen();
						gamecreated = new GameCreatedAdminScreen(CreateGameScreen.getMaxPlayersInGame(), userName );
						gamecreated.setVisible(true);
					}
					else{
						GameAlreadyCreatedWindow gamealreadycreated = new GameAlreadyCreatedWindow();
						gamealreadycreated.setVisible(true);
					}
						
			
					break;
				case PLAYERJOINRESPONSEID: // Se pudo unir a la partida?
					PlayerJoinPackage playerjoinresponse = (PlayerJoinPackage) packageIn;
					roundgamescreen = new RoundGameScreen();
					joinplayergamewindow = new JoinPlayerGameWindow(userType);
					joinplayergamewindow.setLabelAndButton(playerjoinresponse.getJoinStatus());
					joinplayergamewindow.setVisible(true);
					
					break;
					
				case PLAYERJOINEDNOTIFICATIONID:
					NotifyPlayerJoinToAdminPackage notification = (NotifyPlayerJoinToAdminPackage) packageIn;
					gamecreated.playerHasJoined(notification.getUserName());
					break;
					
				case PLAYERDISCONNECTEDID:
					if(userType == 0){
						PlayerDisconnectPackage playerdisconnect = (PlayerDisconnectPackage) packageIn;
						gamecreated.playerHasLeft(playerdisconnect.getUserName());
					}
					else{
						joinplayergamewindow.setGameCanceledLabel();
					}
					break;
				case STARTGAMERESPONSEID: // Se pudo comenzar la partida?
					StartGamePackage startgameresponse = (StartGamePackage) packageIn;
					if(!startgameresponse.canStartGame()){
						gamecreated.setEnabled(false);
						CantStartGameWindow cantStart = new CantStartGameWindow(gamecreated);
						cantStart.setVisible(true);
					}
					else
						gamecreated.dispose();
					
					
					break;
				case POINTSTABLERESPONSEID:
					TopTenUserPackage toptenscore = (TopTenUserPackage) packageIn;
					AllTimeScoreTableScreen alltimescoretablescreen = new AllTimeScoreTableScreen(toptenscore.getTopTen(), userType);
					alltimescoretablescreen.setVisible(true);
					break;
					
				case GAMERUNNINGID:
					
					Question question = (Question) packageIn;
					
					if(joinplayergamewindow != null)
						joinplayergamewindow.setVisible(false);
					roundgamescreen.setVisible(false);
					roundgamescreen.enableButtonsAndRefreshComponents();
					roundgamescreen.setQuestionAndCategory(question);
					roundgamescreen.setVisible(true);
					roundgamescreen.startTimer();				
					break;
					
				case RESULTSGAMEID:
					ResultsGamePackage resultsGame = (ResultsGamePackage) packageIn;
					GameResultsWindow gameresultswindow = new GameResultsWindow();
					gameresultswindow.setLabelWinnerStatus(resultsGame.getPlayerWin(), resultsGame.getWinners().size());
					gameresultswindow.setScoreTableAndUserType(resultsGame.getScoreTable(), userType);
					gameresultswindow.setVisible(true);
					roundgamescreen.dispose();
					
					break;
				case ADDQUESTIONREESPONSEID: // Agregar pregunta
					AddQuestionConfirmationPackage addResponse = (AddQuestionConfirmationPackage) packageIn;
					if(addResponse.getValid() == true)
							((AddQuestionScreen)JFrameScreen).clearScreen();
					break;
					
				case ENDTIMEID:
					break;
					
					
				case ANSWERQUESTIONRESPONSEID:
					AnswerQuestionPackage answer = (AnswerQuestionPackage) packageIn;
					roundgamescreen.setLabelAnswer(answer.isCorrect());
					roundgamescreen.paintButtons(answer.isCorrect());
					
					
					break;
				case ENDSERVERPACKAGEID: // Error en el servidor
					ServerErrorWindow servererrorwindow = new ServerErrorWindow();
					servererrorwindow.setVisible(true);
					endConnection = true;
					break;
				
				case ENDCONNECTIONRESPONSEID: // Fin conexion
					endConnection = true;
					connection.endConnection();
				}

			}
			System.out.println("Conexion Finalizada");
		} catch (Exception 
				e) {
			e.printStackTrace();
		}
	}
	



	public static void recieveScreen(JDialog screen) {
		JDialogScreen = screen;
		
	}
	
	public static void recieveScreen(JFrame screen){
		JFrameScreen = screen;
	}
	


}
