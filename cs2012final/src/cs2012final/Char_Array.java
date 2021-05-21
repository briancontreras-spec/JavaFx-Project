package cs2012final;

import java.util.Random;
import java.util.Scanner;

import cs2012final.ConfirmHandler;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Char_Array extends Application {

	public static void main(String[] args) {
		launch();
	}
		

	@Override
	public void start(Stage primaryStage) throws Exception {

		

		GridPane userInput = new GridPane();
		userInput.setAlignment(Pos.CENTER);
		userInput.setHgap(20);
		userInput.setVgap(10);
		userInput.setPadding(new Insets(11.5,12.5,13.5,14.5));
		TextField userInputName = new TextField();
		TextField userInputX = new TextField();
		TextField userInputY = new TextField();

		userInput.add(new Label("Please enter your name user:"), 0, 0);
		userInput.add(userInputName, 1, 0);
		userInput.add(new Label("Please enter your X value") , 0,1);
		userInput.add(userInputX, 1, 1);
		userInput.add(new Label("Please enter your Y value"), 0,2);
		userInput.add(userInputY, 1, 2); 
		Button confirmButton = new Button("Confirm");
		userInput.add(confirmButton, 1, 3);
		GridPane.setHalignment(confirmButton, HPos.RIGHT);
		
		confirmButton.setOnAction(new ConfirmHandler(userInputX,userInputY,userInputName,2));

		


		
		Scene userInputScene = new Scene(userInput);	
		
		primaryStage.setTitle("User input Title");
		primaryStage.setScene(userInputScene);
		primaryStage.show();

		
	}



	public static void printArray(char[][]itemArray, int x, int y) {
		for(int i = 0; i < x;i++) {
			for(int z = 0; z < y; z++) {
				System.out.print(itemArray[i][z]);
			}
			System.out.println("\n");
		}
	}

}

