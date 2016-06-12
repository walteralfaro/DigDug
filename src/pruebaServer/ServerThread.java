package pruebaServer;

import java.util.ArrayList;

public class ServerThread extends Thread {

	private static final int LOGINREQUESTID = 1;
	private static final int CREATEGAMEREQUESTID = 2;
	private static final int PLAYERJOINREQUESTID = 3;
	private static final int STARTGAMEREQUESTID = 4;
	private static final int CATEGORYREQUESTID = 5;
	private static final int ADDQUESTIONREQUESTID = 6;
	private static final int POINTSTABLEREQUESTID = 7;
	private static final int ENDCONECTIONREQUESTID = 8;
	private static final int QUESTIONSREQUESTID = 9;
	private static final int ANSWERQUESTIONREQUESTID = 10;
	private static final int PLAYERDISCONNECTREQUESTID = 16;
	private Integer userId;
	private String userName = null;
	
	public ServerThread(Integer userId) {
		this.userId = userId;
	}

	public void run() {
		Boolean endConection = false;
		Game game = null;
		
		try {
			UserConnection userConnectionInstance = UserConnection.getInstance();
			
			while (!endConection) {
				Package packageOut = null;
				Package packageIn = userConnectionInstance.readPackage(userId);
				userConnectionInstance.blockSocket(userId);
				
				switch (packageIn.getPackageID()) {
				case LOGINREQUESTID: // Pedido de logeo del cliente
					UserLoginPackage userLogin = (UserLoginPackage) packageIn;
					int loginStatus; // 0: Usuario Valido, -1: Usuario Invalido, -2: Usuario ya estaba loggeado
					String userLoginName = userLogin.getUser();
					Logger.info("Solicitud de loggeo recibida.");
					
					loginStatus = validateClient(userLogin);
					if(loginStatus != -1) { 
						if(!userConnectionInstance.userIsLogged(userLoginName)) {
							userName = userLoginName;
							userConnectionInstance.getUser(userId).setName(userName);
							Logger.info("Se ha conectado el usuario " + userName);
						} else {
							loginStatus = -2;
							Logger.warn("Se esta intentando ingresar con un usuario que ya esta loggeado. Nombre de usuario: " + userLoginName);
						}
					} else {
						Logger.info("Los datos ingresados por el cliente " + userId + " no se encuentra en la base de datos. Nombre de usuario: " + userLoginName);
					}
					
					userLogin.setUserType(loginStatus);
					packageOut = userLogin; 

					break;
				case QUESTIONSREQUESTID: // Devuelve las preguntas por categoria  
					QuestionsByCategoryPackage questionsByCategory = (QuestionsByCategoryPackage) packageIn;

					ArrayList<Question> questions = getQuestionByCategory(questionsByCategory.getCategory());
					questionsByCategory.setQuestions(questions);
					packageOut = questionsByCategory;
					
					break;
				case CREATEGAMEREQUESTID: // Creacion de partida
					CreateGamePackage gameRequest = (CreateGamePackage) packageIn;
					Boolean gameCreated = true;
					game = Game.getGameInstance();
					Logger.info("Creando partida...");
					
					if(!game.isCreated()) {
						game.createGame(gameRequest.getGameName(), gameRequest.getMaxPlayers(), gameRequest.getQuestionsID());
						Logger.info("Partida creada correctamente.");
						game.addPlayer(userId, userName); //Al crear la partida el administrador se une.
					} else {
						gameCreated = false;
						Logger.warn("La partida ya estaba creada");
					}
					
					packageOut = new CreateGamePackage(gameCreated);
					break;
				case PLAYERJOINREQUESTID: // Jugador uniendose a partida
					int joinStatus; //-2: Ya estaba dentro el jugador, -1: No existe la partida
									// 0: La partida está llena. 1: El jugador se puede unir
									//-3: Partida ya iniciada.
					game = Game.getGameInstance();
					Logger.info(userName + " solicita ingresar a la partida.");
					
					if(!game.isCreated()) {
						joinStatus = -1;
						Logger.warn("La partida no existe.");
					} else if(game.playerInGame(userId)) {
						joinStatus = -2;
						Logger.warn("El jugador ya se encuentra dentro de la partida.");
					} else if(game.isStarted()) {
						joinStatus = -3;
						Logger.info("La partida ya comenzo. El jugador " + userName + " no se pudo unir.");
					} else if(game.gameIsFull()) {
						joinStatus = 0;
						Logger.info("La partida está llena. El jugador " + userName + " no se pudo unir.");
					} else {
						Integer adminId = game.getAdminPlayer().getId();
						game.addPlayer(userId, userName);
						userConnectionInstance.blockSocket(adminId);
						userConnectionInstance.sendPackage(adminId, new NotifyPlayerJoinToAdminPackage(userName));
						userConnectionInstance.releaseSocket(adminId);
						joinStatus = 1;
					}
					
					packageOut = new PlayerJoinPackage(joinStatus);
					break;
					
				case STARTGAMEREQUESTID: // Comenzar partida
					Boolean canStartGame = false; 
					game = Game.getGameInstance();
					Logger.info("Iniciando partida...");
					
					if(game.isCreated() && game.canStartGame()) {
						canStartGame = true;
						game.startGame();
						Logger.info("La partida se ha iniciado correctamente.");
					} else if(!game.isCreated()) {
						canStartGame = false;
						Logger.warn("Se intenta comenzar una partida que no fue creada");
					} else {
						canStartGame = false;
						Logger.warn("No se puede iniciar la partida por falta de jugadores");
					}
					
					packageOut = new StartGamePackage(canStartGame);
					break;
				case CATEGORYREQUESTID:
					Logger.info("El usuario " + userName + " ha solicitado las categorias.");
					CategoryPackage categoryrequest = (CategoryPackage) packageIn;
					ArrayList<Category> categories = getAllCategories(); 
					packageOut = new CategoryPackage(categories, categoryrequest.getIdScreen());
					break;
				case POINTSTABLEREQUESTID:
					Logger.info("El usuario " + userName + " ha solicitado la tabla de puntuacion historica.");
					ArrayList<User> topTen = getTopTenUsers();
					packageOut = new TopTenUserPackage(topTen);
					break;
				case ADDQUESTIONREQUESTID: // Agregar pregunta
					Question question = (Question) packageIn;
					Logger.info("Insertando pregunta a la base de datos...");

					addQuestionToDB(question);
					packageOut = new AddQuestionConfirmationPackage(true);
					
					Logger.info("Pregunta insertada correctamente.");
					break;
				case ANSWERQUESTIONREQUESTID: // Respuesta del jugador ante una pregunta 
					game = Game.getGameInstance();
					Logger.info(userName + " ha enviado su respuesta.");
					
					if(game.isCreated() && game.getWaitingAnswer()) {
						AnswerQuestionPackage answerQuestion = (AnswerQuestionPackage) packageIn;
						game.setAnswer(userId, answerQuestion.getAnswer());
						Logger.info("Respuesta recibida correctamente del cliente " + userName);
					} else if(!game.isCreated()) {
						Logger.warn("El cliente " + userName + " envio una respuesta cuando no existe la partida. ClientId: " + userId);
					} else {
						Logger.warn("Respuesta recibida fuera del tiempo permitido del cliente " + userName);
					}
					
					break;
				case PLAYERDISCONNECTREQUESTID:
					if(userId.equals(game.getAdminPlayer().getId())) {
						Logger.info("El administrador ha cancelado la partida");
						if(!game.empty())
							game.cancelGame();
						else
							game.resetGame();
						Logger.info("Partida cancelada correctamente");
					} else {
						if(game.isCreated()) {
							Integer adminId = game.getAdminPlayer().getId();
							game.removePlayer(userId);
							userConnectionInstance.blockSocket(adminId);
							userConnectionInstance.sendPackage(adminId, new PlayerDisconnectPackage(userName, false));
							userConnectionInstance.releaseSocket(adminId);
							Logger.info("Se le ha notificado al administrador que el usuario " + userName + " ha abandonado la partida." );
						}
					}
					break;
				case ENDCONECTIONREQUESTID: // Fin conexion
					game = Game.getGameInstance();
					Logger.info("Finalizando conexion con el cliente " + userId);

					if(game.isCreated())
						game.removePlayer(userId);
					
					packageOut = new EndClientConnectionPackage();
					endConection = true;
				}

				if(packageOut != null) userConnectionInstance.sendPackage(userId, packageOut);
				userConnectionInstance.releaseSocket(userId);
			}
			if(userName != null)
				Logger.info("El usuario " + userName + " se ha desconectado.");

			userConnectionInstance.closeOutputStream(userId);
			userConnectionInstance.closeInputStream(userId);
			userConnectionInstance.freeUser(userId);
			
			Logger.info("Conexion finalizada correctamente con el cliente: " + userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Integer validateClient(UserLoginPackage client) {
		DataBaseUtil db = new DataBaseUtil();
		UserLoginPackage user = db.getUserDB(client.getUser());
		if(user != null && user.getPassword().equals(client.getPassword()))
			return user.getUserType();
		return -1;
	}
	
	private void addQuestionToDB(Question question) {
		DataBaseUtil db = new DataBaseUtil();
		db.setQuestionDB(question);
	}
	
	private ArrayList<Question> getQuestionByCategory(String category) {
		DataBaseUtil db = new DataBaseUtil();
		return db.getQuestionByCategoryDB(category);
	}
	
	private ArrayList<Category> getAllCategories() {
		DataBaseUtil db = new DataBaseUtil();
		return db.getCategoryDB();
	}
	
	private ArrayList<User> getTopTenUsers() {
		DataBaseUtil db = new DataBaseUtil();
		return db.getTopTenUsersDB();
	}

}
