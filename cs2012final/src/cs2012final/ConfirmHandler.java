package cs2012final;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ConfirmHandler implements EventHandler<ActionEvent> {
	private TextField UserInputX;
	private TextField UserInputY;
	private TextField UserInput;
	private static int ammo;
	
	public ConfirmHandler(TextField UserInputX,TextField UserInputY, TextField UserInput, int ammo) {
		this.UserInput = UserInput;
		this.UserInputX = UserInputX;
		this.UserInputY = UserInputY;
		this.ammo = ammo;
	}
	@Override
	public void handle(ActionEvent arg0) {
		String savedUserValue = this.UserInput.getText();
		GridPane playableWindow = new GridPane();
		HBox Box = new HBox();
		ammo = 2;
		
		//Rectangle grid = new Rectangle(5.0,5.0,Paint.valueOf("BLACK"));
		int x = Integer.parseInt(this.UserInputX.getText());
		int y = Integer.parseInt(this.UserInputY.getText());
		Random r1 = new Random();
		int randomlyGeneratedX = r1.nextInt(x);
		int randomlyGeneratedY = r1.nextInt(y);
		char[][] itemArray = new char[x][y];
		boolean[][] pathArray = new boolean[x][y];
		populateArray(pathArray,itemArray,x,y);
		drawGrid(playableWindow,x,y);
		drawPlayer(itemArray,playableWindow,x,y);
		
		Button debugButton = new Button("Debug");
		Button hideButton = new Button("Hide");
		Label ammoCounter = new Label("Exploding Pies: " + ammo);
		VBox debugBox = new VBox();
		HBox boxh = new HBox();
		GridPane fullPane = new GridPane();
		debugBox.getChildren().add(debugButton);
		debugBox.getChildren().add(hideButton);
		debugBox.getChildren().add(ammoCounter);
		
		hideButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				drawGrid(playableWindow, x, y);
				reDrawPath(pathArray, playableWindow, x, y);
				drawPlayer(itemArray, playableWindow, x, y);
			}			
		});
		debugButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				drawOther(itemArray, playableWindow, x, y);			
			}		
		});
		Box.getChildren().addAll(playableWindow,debugBox);		
		fullPane.getChildren().add(Box);
		
		Scene playScene = new Scene(fullPane);
		Stage playStage = new Stage();
		playStage.setScene(playScene);
		playStage.show();
		checkForAll(itemArray, x, y,playStage,ammoCounter,debugBox);
		playScene.setOnKeyPressed(e ->{
			if(e.getCode() == KeyCode.W) {
				int currentX = findPlayerX(itemArray, x, y);
				int currentY = findPlayerY(itemArray,x,y);
				
				if(itemArray[currentX][currentY-1] == 'c') {
					playerPath(itemArray, playableWindow, x, y);
					while (true) {
						int sendPlayertoRandomX = r1.nextInt(x);
						int sendPlayertoRandomY	= r1.nextInt(y);
						if(itemArray[sendPlayertoRandomX][sendPlayertoRandomY] == '\u0000') {
							itemArray[currentX][currentY] = '\u0000';
							itemArray[sendPlayertoRandomX][sendPlayertoRandomY] = 'p';
							drawPlayer(itemArray, playableWindow, x, y);
							checkForAll(itemArray, x, y,playStage,ammoCounter,debugBox);
							pathArray[findPlayerX(itemArray, x, y)][findPlayerY(itemArray,x,y)] = true;
							break;
					}
				}
			}
				else if(itemArray[currentX][currentY-1] == 't') {				
					Rectangle backLabel = new Rectangle(1000.00,500.00);
					Label confusionLabel = new Label("You accidently got too close to the orb of confusion and now you are mindlessly staring at it til the end of time");
					confusionLabel.setTextFill(Color.WHITE);
					StackPane confusedPane = new StackPane();
					StackPane.setAlignment(confusionLabel, Pos.CENTER);
					confusedPane.getChildren().add(backLabel);
					confusedPane.getChildren().add(confusionLabel);
					Scene confuseScene = new Scene(confusedPane);
					Stage confusedStage = new Stage();
					playStage.setScene(confuseScene);
						drawPlayer(itemArray, playableWindow, x, y);
						pathArray[findPlayerX(itemArray, x, y)][findPlayerY(itemArray,x,y)] = true;
					
				}
				else if(itemArray[currentX][currentY-1] == 'm') {
					pathArray[currentX][currentY-1] = true;
					Rectangle MythicalLabel = new Rectangle(500.00,250.00);
					Label MythicalText = new Label("You have woken up ManRay and now he has decintegrated you");
					MythicalText.setTextFill(Color.WHITE);
					StackPane monsterPane = new StackPane();
					monsterPane.getChildren().add(MythicalLabel);
					monsterPane.getChildren().add(MythicalText);
					Scene monsterScene = new Scene(monsterPane);		
				   playStage.setScene(monsterScene);					
				}
				else if(itemArray[currentX][currentY-1] == 'a') {
					pathArray[currentX][currentY-1] = true;
					Rectangle ammoLabel = new Rectangle(500.00,250.00);
					Label ammoText = new Label("You have found a extra exploding pie laying around");
					ammoText.setTextFill(Color.WHITE);
					StackPane ammoPane = new StackPane();
					ammoPane.getChildren().add(ammoLabel);
					ammoPane.getChildren().add(ammoText);
					Scene ammoScene = new Scene(ammoPane);
					Stage ammoStage = new Stage();
					ammoStage.setScene(ammoScene);
					ammoStage.show();					
					moveUp(itemArray, playableWindow, x, y);
					checkForAll(itemArray, x, y,playStage,ammoCounter,debugBox);
					++ammo;
					updateLabel(ammoCounter, ammo);
				}
				
				else {
					moveUp(itemArray, playableWindow, x, y);
					checkForAll(itemArray, x, y,playStage,ammoCounter,debugBox);
					pathArray[findPlayerX(itemArray, x, y)][findPlayerY(itemArray,x,y)] = true;
				}
			


			

			}
			if(e.getCode() == KeyCode.S) {
				int currentX = findPlayerX(itemArray, x, y);
				int currentY = findPlayerY(itemArray,x,y);
				if(itemArray[currentX][currentY+1] == 'c') {
					playerPath(itemArray, playableWindow, x, y);
					while (true) {
						int sendPlayertoRandomX = r1.nextInt(x);
						int sendPlayertoRandomY	= r1.nextInt(y);
						if(itemArray[sendPlayertoRandomX][sendPlayertoRandomY] == '\u0000') {
							itemArray[currentX][currentY] = '\u0000';
							itemArray[sendPlayertoRandomX][sendPlayertoRandomY] = 'p';
							drawPlayer(itemArray, playableWindow, x, y);
							checkForAll(itemArray, x, y,playStage,ammoCounter,debugBox);
							pathArray[findPlayerX(itemArray, x, y)][findPlayerY(itemArray,x,y)] = true;
							
							break;
					}
				}
			}
				else if(itemArray[currentX][currentY+1] == 't') {
						Rectangle backLabel = new Rectangle(1000.00,500.00);
						Label confusionLabel = new Label("You accidently got too close to the orb of confusion and now you are mindlessly staring at it til the end of time");
						confusionLabel.setTextFill(Color.WHITE);
						StackPane confusedPane = new StackPane();
						StackPane.setAlignment(confusionLabel, Pos.CENTER);
						confusedPane.getChildren().add(backLabel);
						confusedPane.getChildren().add(confusionLabel);
						Scene confuseScene = new Scene(confusedPane);
						Stage confusedStage = new Stage();
						playStage.setScene(confuseScene);
							drawPlayer(itemArray, playableWindow, x, y);
							pathArray[findPlayerX(itemArray, x, y)][findPlayerY(itemArray,x,y)] = true;
							
				}
				else if(itemArray[currentX][currentY+1] == 'a') {
					pathArray[currentX][currentY+1] = true;
					Rectangle ammoLabel = new Rectangle(500.00,250.00);
					Label ammoText = new Label("You have found a extra exploding pie laying around");
					ammoText.setTextFill(Color.WHITE);
					StackPane ammoPane = new StackPane();
					ammoPane.getChildren().add(ammoLabel);
					ammoPane.getChildren().add(ammoText);
					Scene ammoScene = new Scene(ammoPane);
					Stage ammoStage = new Stage();
					ammoStage.setScene(ammoScene);
					ammoStage.show();					
					moveDown(itemArray, playableWindow, x, y);
					checkForAll(itemArray, x, y,playStage,ammoCounter,debugBox);
					++ammo;
					updateLabel(ammoCounter, ammo);
				}
				else if(itemArray[currentX][currentY+1] == 'm') {
					pathArray[currentX][currentY+1] = true;
					Rectangle MythicalLabel = new Rectangle(500.00,250.00);
					Label MythicalText = new Label("You have woken up ManRay and now he has decintegrated you");
					MythicalText.setTextFill(Color.WHITE);
					StackPane monsterPane = new StackPane();
					monsterPane.getChildren().add(MythicalLabel);
					monsterPane.getChildren().add(MythicalText);
					Scene monsterScene = new Scene(monsterPane);		
				   playStage.setScene(monsterScene);					
				}
				else {
				moveDown(itemArray, playableWindow, x, y);
				checkForAll(itemArray, x, y,playStage,ammoCounter,debugBox);
				pathArray[findPlayerX(itemArray, x, y)][findPlayerY(itemArray,x,y)] = true;
				}
			}
			if(e.getCode() == KeyCode.D) {
				int currentX = findPlayerX(itemArray, x, y);
				int currentY = findPlayerY(itemArray,x,y);
				if(itemArray[currentX+1][currentY] == 'c') {
					playerPath(itemArray, playableWindow, x, y);
					while (true) {
						int sendPlayertoRandomX = r1.nextInt(x);
						int sendPlayertoRandomY	= r1.nextInt(y);
						if(itemArray[sendPlayertoRandomX][sendPlayertoRandomY] == '\u0000') {
							itemArray[currentX][currentY] = '\u0000';
							itemArray[sendPlayertoRandomX][sendPlayertoRandomY] = 'p';
							drawPlayer(itemArray, playableWindow, x, y);
							checkForAll(itemArray, x, y,playStage,ammoCounter,debugBox);
							pathArray[findPlayerX(itemArray, x, y)][findPlayerY(itemArray,x,y)] = true;
							break;
							
					}
				}
			}
				else if(itemArray[currentX+1][currentY] == 't') {
					Rectangle backLabel = new Rectangle(1000.00,500.00);
					Label confusionLabel = new Label("You accidently got too close to the orb of confusion and now you are mindlessly staring at it til the end of time");
					confusionLabel.setTextFill(Color.WHITE);
					StackPane confusedPane = new StackPane();
					StackPane.setAlignment(confusionLabel, Pos.CENTER);
					confusedPane.getChildren().add(backLabel);
					confusedPane.getChildren().add(confusionLabel);
					Scene confuseScene = new Scene(confusedPane);
					Stage confusedStage = new Stage();
					playStage.setScene(confuseScene);
						drawPlayer(itemArray, playableWindow, x, y);
						pathArray[findPlayerX(itemArray, x, y)][findPlayerY(itemArray,x,y)] = true;
					
				}
				else if(itemArray[currentX+1][currentY] == 'a') {
					pathArray[currentX+1][currentY] = true;
					Rectangle ammoLabel = new Rectangle(500.00,250.00);
					Label ammoText = new Label("You have found a extra exploding pie laying around");
					ammoText.setTextFill(Color.WHITE);
					StackPane ammoPane = new StackPane();
					ammoPane.getChildren().add(ammoLabel);
					ammoPane.getChildren().add(ammoText);
					Scene ammoScene = new Scene(ammoPane);
					Stage ammoStage = new Stage();
					ammoStage.setScene(ammoScene);
					ammoStage.show();					
					moveRight(itemArray, playableWindow, x, y);
					checkForAll(itemArray, x, y,playStage,ammoCounter,debugBox);
					++ammo;
					updateLabel(ammoCounter, ammo);
				}
				else if(itemArray[currentX+1][currentY] == 'm') {
					pathArray[currentX+1][currentY] = true;
					Rectangle MythicalLabel = new Rectangle(500.00,250.00);
					Label MythicalText = new Label("You have woken up ManRay and now he has decintegrated you");
					MythicalText.setTextFill(Color.WHITE);
					StackPane monsterPane = new StackPane();
					monsterPane.getChildren().add(MythicalLabel);
					monsterPane.getChildren().add(MythicalText);
					Scene monsterScene = new Scene(monsterPane);		
				   playStage.setScene(monsterScene);					
				}
				else {
				moveRight(itemArray, playableWindow, x, y);
				checkForAll(itemArray, x, y,playStage,ammoCounter,debugBox);
				pathArray[findPlayerX(itemArray, x, y)][findPlayerY(itemArray,x,y)] = true;
				}
			}
			if(e.getCode() == KeyCode.A) {
				int currentX = findPlayerX(itemArray, x, y);
				int currentY = findPlayerY(itemArray,x,y);
				if(itemArray[currentX-1][currentY] == 'c') {
					playerPath(itemArray, playableWindow, x, y);
					while (true) {
						int sendPlayertoRandomX = r1.nextInt(x);
						int sendPlayertoRandomY	= r1.nextInt(y);
						if(itemArray[sendPlayertoRandomX][sendPlayertoRandomY] == '\u0000') {
							itemArray[currentX][currentY] = '\u0000';
							itemArray[sendPlayertoRandomX][sendPlayertoRandomY] = 'p';
							drawPlayer(itemArray, playableWindow, x, y);
							checkForAll(itemArray, x, y,playStage,ammoCounter,debugBox);
							pathArray[findPlayerX(itemArray, x, y)][findPlayerY(itemArray,x,y)] = true;
							break;
					}
				}
			}
				else if(itemArray[currentX-1][currentY] == 't') {
					Rectangle backLabel = new Rectangle(1000.00,500.00);
					Label confusionLabel = new Label("You accidently got too close to the orb of confusion and now you are mindlessly staring at it til the end of time");
					confusionLabel.setTextFill(Color.WHITE);
					StackPane confusedPane = new StackPane();
					StackPane.setAlignment(confusionLabel, Pos.CENTER);
					confusedPane.getChildren().add(backLabel);
					confusedPane.getChildren().add(confusionLabel);
					Scene confuseScene = new Scene(confusedPane);
					Stage confusedStage = new Stage();
					playStage.setScene(confuseScene);
						drawPlayer(itemArray, playableWindow, x, y);
						pathArray[findPlayerX(itemArray, x, y)][findPlayerY(itemArray,x,y)] = true;
				}
				else if(itemArray[currentX-1][currentY] == 'a') {
					pathArray[currentX-1][currentY] = true;
					Rectangle ammoLabel = new Rectangle(500.00,250.00);
					Label ammoText = new Label("You have found a extra exploding pie laying around");
					ammoText.setTextFill(Color.WHITE);
					StackPane ammoPane = new StackPane();
					ammoPane.getChildren().add(ammoLabel);
					ammoPane.getChildren().add(ammoText);
					Scene ammoScene = new Scene(ammoPane);
					Stage ammoStage = new Stage();
					ammoStage.setScene(ammoScene);
					ammoStage.show();					
					moveLeft(itemArray, playableWindow, x, y);
					checkForAll(itemArray, x, y,playStage,ammoCounter,debugBox);
					++ammo;
					updateLabel(ammoCounter, ammo);
				}
				else if(itemArray[currentX-1][currentY] == 'm') {
					pathArray[currentX-1][currentY] = true;
					Rectangle MythicalLabel = new Rectangle(500.00,250.00);
					Label MythicalText = new Label("You have woken up ManRay and now he has decintegrated you");
					MythicalText.setTextFill(Color.WHITE);
					StackPane monsterPane = new StackPane();
					monsterPane.getChildren().add(MythicalLabel);
					monsterPane.getChildren().add(MythicalText);
					Scene monsterScene = new Scene(monsterPane);		
				   playStage.setScene(monsterScene);					
				}
				else {
				moveLeft(itemArray, playableWindow, x, y);
				checkForAll(itemArray, x, y,playStage,ammoCounter,debugBox);
				pathArray[findPlayerX(itemArray, x, y)][findPlayerY(itemArray,x,y)] = true;
				}
			}
			
		});


		}
	public static char[][] populateArray(boolean[][] pathArray, char[][]itemArray ,int x,int y) {
		//limit values that will be used for each Item
		Random r1 = new Random();
				int playerLimit = 0;
				int creatureLimit =0;
				int trapLimit = 0;
				int ammoLimit = 0;
				int mythicalCreatureLimit = 0;
				int maxItems = 0;
				//While loop to populate 2d Array with Appropriate chars
				while(maxItems<1) {
						int RandomX = r1.nextInt(x);
						int RandomY = r1.nextInt(y); 				
						if(isNull(itemArray[RandomX][RandomY])){
							if(playerLimit <1) {
								//p is for Player
								itemArray[RandomX][RandomY] = 'p';
								pathArray[RandomX][RandomY] = true;
								playerLimit++;
							}
							else if(creatureLimit < 3) {
								//c is for Creature
								itemArray[RandomX][RandomY] = 'c';
								creatureLimit++;
							}
							else if(trapLimit < 3) {
								//t is for Trap
								itemArray[RandomX][RandomY] = 't';
								trapLimit++;
							}
							else if(ammoLimit < 2) {
								//a is for Ammo
								itemArray[RandomX][RandomY] = 'a';
								ammoLimit++;
							}
							else if(mythicalCreatureLimit < 1) {
								//m is for Mythical Creature
								itemArray[RandomX][RandomY] = 'm';
								mythicalCreatureLimit++;
								maxItems++;
							}
						}
				}
				return itemArray;
	}
	public static boolean isNull(char c) {
		return c == '\u0000';		
	}
	public static void drawGrid(GridPane playableWindow, int x, int y) {
		for (int i = 0; i<y;i++) {
			for(int j = 0; j<x;j++) {
			Rectangle grid = new Rectangle(40.0,40.0);
			grid.setFill(Color.BLACK);
			grid.setStroke(Color.WHITE);
			grid.setStrokeWidth(2);
			playableWindow.add(grid, j, i);
			}
		}
	}
	public static void drawPlayer(char[][] itemArray,GridPane playableWindow, int x, int y) {
		for(int i = 0; i< y;i++) {
			for(int j = 0; j<x;j++) {
				if(itemArray[j][i]=='p') {
					Rectangle grid = new Rectangle(40.0,40.0);
					grid.setFill(Color.YELLOW);
					grid.setStroke(Color.BEIGE);
					grid.setStrokeWidth(2);
					playableWindow.add(grid, j, i);
				}
			}
		}
	}
	public static void playerPath(char[][] itemArray,GridPane playableWindow, int x, int y) {
		for(int i = 0; i< y;i++) {
			for(int j = 0; j<x;j++) {
				if(itemArray[j][i]=='p') {
					Rectangle grid = new Rectangle(40.0,40.0);
					grid.setFill(Color.LIGHTGREY);
					grid.setStroke(Color.BEIGE);
					grid.setStrokeWidth(2);
					playableWindow.add(grid, j, i);
				}
			}
		}
	}
	public static void reDrawPath(boolean[][] pathArray,GridPane playableWindow, int x ,int y) {
		for(int i = 0; i<y;i++) {
			for(int j = 0; j<x;j++) {
				if(pathArray[j][i]==true) {
					Rectangle grid = new Rectangle(40.0,40.0);
					grid.setFill(Color.LIGHTGREY);
					grid.setStroke(Color.BEIGE);
					grid.setStrokeWidth(2);
					playableWindow.add(grid, j, i);
				}
			}
		}
	}
	public static void drawOther(char[][] itemArray,GridPane playableWindow, int x, int y) {
		for(int i = 0; i< y;i++) {
			for(int j = 0; j<x;j++) {
				if(itemArray[j][i] == 'a') {
					Rectangle grid = new Rectangle(40.0,40.0);
					grid.setStroke(Color.BLUE);
					grid.setFill(Color.GREEN);
					grid.setStrokeWidth(2);
					playableWindow.add(grid, j, i);
				}
				if(itemArray[j][i] == 'c') {
					Rectangle grid = new Rectangle(40.0,40.0);
					grid.setFill(Color.PURPLE);
					grid.setStroke(Color.AQUA);
					grid.setStrokeWidth(2);
					playableWindow.add(grid, j, i);
				}
				if(itemArray[j][i] == 'm') {
					Rectangle grid = new Rectangle(40.0,40.0);
					grid.setFill(Color.BLUEVIOLET);
					grid.setStroke(Color.CORAL);
					grid.setStrokeWidth(2);
					playableWindow.add(grid, j, i);
				}
				if(itemArray[j][i]=='t') {
					Rectangle grid = new Rectangle(40.0,40.0);
					grid.setFill(Color.GREY);
					grid.setStroke(Color.BLACK);
					grid.setStrokeWidth(2);
					playableWindow.add(grid, j, i);
				}
			
				
			}
		}
	}
	public static int findPlayerX(char[][] itemArray, int x, int y) {
		int playerX = 0;
		for(int i = 0; i< y;i++) {
			for(int j = 0; j<x;j++) {
				if(itemArray[j][i]=='p') {
					playerX = j;
				}
			}
		}
		return playerX;
	}
	public static int findPlayerY(char[][] itemArray, int x, int y) {
		int playerY = 0;
		for(int i = 0; i< y;i++) {
			for(int j = 0; j<x;j++) {
				if(itemArray[j][i]=='p') {
					playerY = i;
				}
			}
		}
		return playerY;
	}
	public static void moveUp(char[][] itemArray,GridPane playableWindow, int x, int y) {
		playerPath(itemArray, playableWindow, x, y);
		int currentX = findPlayerX(itemArray, x, y);
		int currentY = findPlayerY(itemArray,x,y);
		itemArray[currentX][currentY] = '\u0000';
		itemArray[currentX][currentY-1] = 'p';
		drawPlayer(itemArray, playableWindow, x, y);
	}
	public static void moveDown(char[][] itemArray,GridPane playableWindow, int x, int y) {
		playerPath(itemArray, playableWindow, x, y);
		int currentX = findPlayerX(itemArray, x, y);
		int currentY = findPlayerY(itemArray,x,y);
		itemArray[currentX][currentY] = '\u0000';
		itemArray[currentX][currentY+1] = 'p';
		drawPlayer(itemArray, playableWindow, x, y);
	}
	public static void moveLeft(char[][] itemArray,GridPane playableWindow, int x, int y) {
		playerPath(itemArray, playableWindow, x, y);
		int currentX = findPlayerX(itemArray, x, y);
		int currentY = findPlayerY(itemArray,x,y);
		itemArray[currentX][currentY] = '\u0000';
		itemArray[currentX-1][currentY] = 'p';
		drawPlayer(itemArray, playableWindow, x, y);
	}
	public static void moveRight(char[][] itemArray,GridPane playableWindow, int x, int y) {
		playerPath(itemArray, playableWindow, x, y);
		int currentX = findPlayerX(itemArray, x, y);
		int currentY = findPlayerY(itemArray,x,y);
		itemArray[currentX][currentY] = '\u0000';
		itemArray[currentX+1][currentY] = 'p';
		drawPlayer(itemArray, playableWindow, x, y);
	}
	public static void showTrapWarning() {
		Label trapWarning = new Label("I am getting a little confused");							
		Pane labelPane = new Pane();
		labelPane.getChildren().add(trapWarning);
		Scene labelScene = new Scene(labelPane);
		Stage label = new Stage();
		label.setScene(labelScene);
		label.show();	
	}
	public static void showCreatureWarning() {
		Label Warning = new Label("I smell I stinky bubble near");							
		Pane CreaturePane = new Pane();
		CreaturePane.getChildren().add(Warning);
		Scene creaturelabelScene = new Scene(CreaturePane);
		Stage creaturelabel = new Stage();
		creaturelabel.setScene(creaturelabelScene);
		creaturelabel.show();	
	}
	public static void showManRayWarning(char[][] itemArray,int x , int y, Stage playStage, Label ammoCounter, VBox debugBox) {
		Label Warning = new Label("I think my wallet is nearby\n"
				+ "If you would like to throw a exploding pie to the Cave next to you press\n"
				+ "\"Q\" for the left \n"
				+ "\"E\" for the right\n"
				+ "\"Z\" for the bottom\n"
				+ "\"F\" for the top");		
		StackPane CreaturePane = new StackPane();
		CreaturePane.getChildren().add(Warning);
		Scene creaturelabelScene = new Scene(CreaturePane);
		Stage creaturelabel = new Stage();
		creaturelabel.setScene(creaturelabelScene);
		creaturelabel.show();
		creaturelabelScene.setOnKeyPressed(f ->{
			if(ammo > 0){
			if(f.getCode() == KeyCode.Q) {
				int currentX = findPlayerX(itemArray, x, y);
				int currentY = findPlayerY(itemArray,x,y);
				try {
					if(itemArray[currentX-1][currentY]=='m') {
						StackPane endPane = new StackPane();
						Label endMessage = new Label("congratulations you hit Man Ray with a exploding pie and now you can escape the cave");
						Rectangle endBackGround = new Rectangle(1000.00,500.00);
						endBackGround.setFill(Color.VIOLET);
						endMessage.setTextFill(Color.BLACK);
						endPane.getChildren().add(endBackGround);
						endPane.getChildren().add(endMessage);
						Scene endScene = new Scene(endPane);
						playStage.setScene(endScene);
						--ammo;
						ammoCounter.setText("Exploding Pies: " + ammo);
					}
					else {
						StackPane missedPane = new StackPane();
						Label missedMessage = new Label("You missed your shot, you lost one pie");
						Rectangle missedBackGround = new Rectangle(1000.00,500.00);
						missedPane.getChildren().add(missedBackGround);
						missedPane.getChildren().add(missedMessage);
						Scene missedScene = new Scene(missedPane);
						creaturelabel.setScene(missedScene);
						--ammo;
						ammoCounter.setText("Exploding Pies: " + ammo);
					}
				}
				catch(Exception e) {
					StackPane missedPane = new StackPane();
					Label missedMessage = new Label("You missed your shot, you lost one pie");
					Rectangle missedBackGround = new Rectangle(1000.00,500.00);
					missedPane.getChildren().add(missedBackGround);
					missedPane.getChildren().add(missedMessage);
					Scene missedScene = new Scene(missedPane);
					creaturelabel.setScene(missedScene);
					--ammo;
					ammoCounter.setText("Exploding Pies: " + ammo);
				}

			}
			if(f.getCode() == KeyCode.E) {
				int currentX = findPlayerX(itemArray, x, y);
				int currentY = findPlayerY(itemArray,x,y);
				try {
					if(itemArray[currentX+1][currentY]=='m') {
						StackPane endPane = new StackPane();
						Label endMessage = new Label("congratulations you hit Man Ray with a exploding pie and now you can escape the cave");
						Rectangle endBackGround = new Rectangle(1000.00,500.00);
						endBackGround.setFill(Color.VIOLET);
						endMessage.setTextFill(Color.BLACK);
						endPane.getChildren().add(endBackGround);
						endPane.getChildren().add(endMessage);
						Scene endScene = new Scene(endPane);
						playStage.setScene(endScene);
						--ammo;
						ammoCounter.setText("Exploding Pies: " + ammo);
					}
					else {
						StackPane missedPane = new StackPane();
						Label missedMessage = new Label("You missed your shot, you lost one pie");
						Rectangle missedBackGround = new Rectangle(1000.00,500.00);
						missedBackGround.setFill(Color.AQUA);
						missedMessage.setTextFill(Color.WHITE);
						missedPane.getChildren().add(missedBackGround);
						missedPane.getChildren().add(missedMessage);
						Scene missedScene = new Scene(missedPane);
						creaturelabel.setScene(missedScene);
						--ammo;
						ammoCounter.setText("Exploding Pies: " + ammo);
					}
				}
				catch(Exception e) {
					StackPane missedPane = new StackPane();
					Label missedMessage = new Label("You missed your shot, you lost one pie");
					Rectangle missedBackGround = new Rectangle(1000.00,500.00);
					missedBackGround.setFill(Color.AQUA);
					missedMessage.setTextFill(Color.WHITE);
					missedPane.getChildren().add(missedBackGround);
					missedPane.getChildren().add(missedMessage);
					Scene missedScene = new Scene(missedPane);
					creaturelabel.setScene(missedScene);
					--ammo;
					ammoCounter.setText("Exploding Pies: " + ammo);
				}
			}
			if(f.getCode() == KeyCode.Z) {
				int currentX = findPlayerX(itemArray, x, y);
				int currentY = findPlayerY(itemArray,x,y);
				try {
					if(itemArray[currentX][currentY+1]=='m') {
						StackPane endPane = new StackPane();
						Label endMessage = new Label("congratulations you hit Man Ray with a exploding pie and now you can escape the cave");
						Rectangle endBackGround = new Rectangle(1000.00,500.00);
						endBackGround.setFill(Color.VIOLET);
						endMessage.setTextFill(Color.BLACK);
						endPane.getChildren().add(endBackGround);
						endPane.getChildren().add(endMessage);
						Scene endScene = new Scene(endPane);
						playStage.setScene(endScene);
						--ammo;
						ammoCounter.setText("Exploding Pies: " + ammo);
					}
					else {
						StackPane missedPane = new StackPane();
						Label missedMessage = new Label("You missed your shot, you lost one pie");
						Rectangle missedBackGround = new Rectangle(1000.00,500.00);
						missedBackGround.setFill(Color.AQUA);
						missedMessage.setTextFill(Color.WHITE);
						missedPane.getChildren().add(missedBackGround);
						missedPane.getChildren().add(missedMessage);
						Scene missedScene = new Scene(missedPane);
						creaturelabel.setScene(missedScene);
						--ammo;
						ammoCounter.setText("Exploding Pies: " + ammo);
					}
				}
				catch(Exception e) {
					System.out.println("cannot check what is next to the player because the player is out of bounds");
					StackPane missedPane = new StackPane();
					Label missedMessage = new Label("You missed your shot, you lost one pie");
					Rectangle missedBackGround = new Rectangle(1000.00,500.00);
					missedBackGround.setFill(Color.AQUA);
					missedMessage.setTextFill(Color.WHITE);
					missedPane.getChildren().add(missedBackGround);
					missedPane.getChildren().add(missedMessage);
					Scene missedScene = new Scene(missedPane);
					creaturelabel.setScene(missedScene);
					--ammo;
					ammoCounter.setText("Exploding Pies: " + ammo);
				}
			}
			if(f.getCode() == KeyCode.F) {
				int currentX = findPlayerX(itemArray, x, y);
				int currentY = findPlayerY(itemArray,x,y);
				try {
					if(itemArray[currentX][currentY-1]=='m') {
						StackPane endPane = new StackPane();
						Label endMessage = new Label("congratulations you hit Man Ray with a exploding pie and now you can escape the cave");
						Rectangle endBackGround = new Rectangle(1000.00,500.00);
						endBackGround.setFill(Color.VIOLET);
						endMessage.setTextFill(Color.BLACK);
						endPane.getChildren().add(endBackGround);
						endPane.getChildren().add(endMessage);
						Scene endScene = new Scene(endPane);
						playStage.setScene(endScene);
						--ammo;
						ammoCounter.setText("Exploding Pies: " + ammo);
					}
					else {
						StackPane missedPane = new StackPane();
						Label missedMessage = new Label("You missed your shot, you lost one pie");
						Rectangle missedBackGround = new Rectangle(1000.00,500.00);
						missedBackGround.setFill(Color.AQUA);
						missedMessage.setTextFill(Color.WHITE);
						missedPane.getChildren().add(missedBackGround);
						missedPane.getChildren().add(missedMessage);
						Scene missedScene = new Scene(missedPane);
						creaturelabel.setScene(missedScene);
						--ammo;
						ammoCounter.setText("Exploding Pies: " + ammo);
					}
				}
				catch(Exception e) {
					StackPane missedPane = new StackPane();
					Label missedMessage = new Label("You missed your shot, you lost one pie");
					Rectangle missedBackGround = new Rectangle(1000.00,500.00);
					missedBackGround.setFill(Color.AQUA);
					missedMessage.setTextFill(Color.WHITE);
					missedPane.getChildren().add(missedBackGround);
					missedPane.getChildren().add(missedMessage);
					Scene missedScene = new Scene(missedPane);
					creaturelabel.setScene(missedScene);
					--ammo;
					ammoCounter.setText("Exploding Pies: " + ammo);
				}
			}
			
		}
			else if(ammo == 0) {
				StackPane noAmmoPane = new StackPane();
				Label noAmmo = new Label("You ran out of ammo");
				Rectangle missedBackGround = new Rectangle(1000.00,500.00);
				noAmmo.setTextFill(Color.BROWN);
				missedBackGround.setFill(Color.BLUE);
				noAmmoPane.getChildren().add(missedBackGround);
				noAmmoPane.getChildren().add(noAmmo);
				Scene noAmmoScene = new Scene(noAmmoPane);
				playStage.setScene(noAmmoScene);
			}

		});
	}
	public static void addButtons(char[][] itemArray,GridPane playableWindow,int x, int y) {
//		Button debugButton = new Button("Debug");
//		Button hideButton = new Button("Hide");
//		GridPane debugPane = new GridPane();
//	
//		GridPane.setHalignment(debugButton, HPos.RIGHT);
//		debugPane.getChildren().add(debugButton);
//		debugPane.getChildren().add(hideButton);
//		Scene creaturelabelScene = new Scene(debugPane);
//		Stage creaturelabel = new Stage();
//		creaturelabel.setTitle("Debug Stage");
//		creaturelabel.setScene(creaturelabelScene);
//		creaturelabel.show();
//		hideButton.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent arg0) {
//				drawGrid(playableWindow, x, y);
//				drawPlayer(itemArray, playableWindow, x, y);
//			}			
//		});
//		debugButton.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent arg0) {
//				drawOther(itemArray, playableWindow, x, y);			
//			}		
//		});
//		
	}
	public static void checkForAll(char[][]itemArray,int x,int y,Stage playStage, Label ammoCounter, VBox debugBox) {
		for (int i = 0; i<y;i++) {
			for(int j = 0; j<x;j++) {
				if(itemArray[j][i] == 'p') {
					try {
						if(itemArray[j-1][i] == 't') {
						showTrapWarning();								
					}
						}
					catch(ArrayIndexOutOfBoundsException e) {
						System.out.println("The Player is towards the edge so we can not check what is to the left of the player");
					}
					try {
						if(itemArray[j+1][i] == 't') {
						showTrapWarning();								
					}
						}
					catch(ArrayIndexOutOfBoundsException e) {
						System.out.println("The Player is towards the edge so we can not check what is to the right of the player");
					}
					try {
						if(itemArray[j][i+1] == 't') {
						showTrapWarning();
					}
						}
					catch(ArrayIndexOutOfBoundsException e) {
						System.out.println("The Player is towards the edge so we can not check what is to the Bottom of the player");
					}
					try {
						if(itemArray[j][i-1] == 't') {
							showTrapWarning();
						}
					}
					catch(ArrayIndexOutOfBoundsException e) {
						System.out.println("The Player is towards the edge so we can not check what is on top of the player");
					}
					try {
						if(itemArray[j-1][i] == 'c') {
							showCreatureWarning();
						}
					}
					catch(ArrayIndexOutOfBoundsException e) {
						//System.out.println("The Player is towards the edge so we can not check what is to the left of the player");
					}
					try {
						if(itemArray[j+1][i] == 'c') {
							showCreatureWarning();
						}
					}
					catch(ArrayIndexOutOfBoundsException e) {
						//System.out.println("The Player is towards the edge so we can not check what is to the right of the player");
					}
					try {
						if(itemArray[j][i-1] == 'c') {
							showCreatureWarning();
						}
					}
					catch(ArrayIndexOutOfBoundsException e) {
						//System.out.println("The Player is towards the edge so we can not check what is on top of the player");
					}
					try {
						if(itemArray[j][i+1] == 'c') {
							showCreatureWarning();
						}
					}
					catch(ArrayIndexOutOfBoundsException e) {
						//System.out.println("The Player is towards the edge so we can not check what is to the right of the player");
					}
					try {
						if(itemArray[j-1][i] == 'm') {
							showManRayWarning(itemArray, x, y,playStage, ammoCounter,debugBox);
						}
					}
					catch(ArrayIndexOutOfBoundsException e) {
						//System.out.println("The Player is towards the edge so we can not check what is to the left of the player");
					}
					try {
						if(itemArray[j+1][i] == 'm') {
							showManRayWarning(itemArray, x, y,playStage, ammoCounter,debugBox);
						}
					}
					catch(ArrayIndexOutOfBoundsException e) {
						//System.out.println("The Player is towards the edge so we can not check what is to the right of the player");
					}
					try {
						if(itemArray[j][i+1] == 'm') {
							showManRayWarning(itemArray, x, y,playStage, ammoCounter,debugBox);
					}
						}
					catch(ArrayIndexOutOfBoundsException e) {
						//System.out.println("The Player is towards the edge so we can not check what is to the Bottom of the player");
					}
					try {
						if(itemArray[j][i-1] == 'm') {
							showManRayWarning(itemArray, x, y,playStage, ammoCounter,debugBox);
						}
					}
					catch(ArrayIndexOutOfBoundsException e) {
						//System.out.println("The Player is towards the edge so we can not check what is on top of the player");
					}
					
				}
			}
		}
	}
	public static void updateLabel(Label label, int ammo) {
		label.setText("Exploding Pies: " + ammo);
	}
	public static int getAmmo(int ammo) {
		return ammo;
	}
	public static int increaseAmmo(int ammo) {
		return ammo++;
	}
	}



