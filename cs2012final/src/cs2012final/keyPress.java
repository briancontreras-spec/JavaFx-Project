package cs2012final;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class keyPress implements EventHandler<KeyEvent> {
	//data fields
	private char[][] itemArray;
	private GridPane playableWindow;
	private int x;
	private int y;
	
	public keyPress (char[][] itemArray,GridPane playableWindow,int x, int y) {
		this.itemArray = itemArray;
		this.playableWindow = playableWindow;
		this.x = x;
		this.y = y;
	}
	@Override
	public void handle(KeyEvent arg0) {
		this.itemArray = itemArray;
		this.playableWindow =  playableWindow;
		this.x = x;
		this.y = y;
	//if (arg0.getCode().equals(UP)) {
		
	}
		

	//}
	public static void drawGrid(GridPane playableWindow, int x, int y) {
		for (int i = 0; i<y;i++) {
			for(int j = 0; j<x;j++) {
			Rectangle grid = new Rectangle(40.0,40.0);
			grid.setFill(Color.BLACK);
			grid.setStroke(Color.BLACK);
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
	public int findPlayerX(char[][] itemArray, int x, int y) {
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
	public int findPlayerY(char[][] itemArray, int x, int y) {
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
}
