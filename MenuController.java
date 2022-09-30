package Final_Project;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MenuController {
	@FXML
	public void onStartPressed() throws IOException {
		Parent maze = FXMLLoader.load(getClass().getResource("Computer.fxml"));
		Scene mazeScene = new Scene(maze);
		mazeScene.getRoot().requestFocus();
		Final_Project.currentStage.setScene(mazeScene);
	}

	@FXML
	public void onExitPressed() {
		Final_Project.currentStage.close();
	}
}
