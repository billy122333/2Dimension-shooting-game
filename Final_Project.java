package Final_Project;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Final_Project extends Application {
	public static Stage currentStage;
	public static Scene scene;
	@Override
	public void start(Stage primaryStage) throws Exception {
		currentStage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
		currentStage.setTitle("ex-MAYOR,HEN'S AMBITION");
		scene = new Scene(root);
		scene.getRoot().requestFocus();
		currentStage.setScene(scene);
		currentStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
