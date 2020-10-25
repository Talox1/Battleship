package battleship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	public static String [] arguments ;
	public static void main(String[] args) {
		arguments = args;
		
		launch(args);
	}
	
	
	@Override
	public void start(Stage primary) throws Exception {
		// TODO Auto-generated method stub
		
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/BattleshipView.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		Scene scene = new Scene(root);
		primary.setScene(scene);
		primary.show();
	}
}