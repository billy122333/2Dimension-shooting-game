package Final_Project;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Controller_key_Event implements Initializable, EventHandler<KeyEvent> {
	@FXML
	public Pane _field;
	@FXML
	public Label _plane;
	@FXML
	public Label _plane1;
	@FXML
	public Label _bullet;
	@FXML
	public Label _bullet1;

	LinkedList<Label> _bullets = new LinkedList<>();
	LinkedList<Label> _bullets1 = new LinkedList<>();

	public void handle(KeyEvent e) {
		if (e.getCode() == KeyCode.E) {
			Timeline barrel_roll = new Timeline(new KeyFrame(Duration.millis(100), (d) -> {
				_plane.setRotate(_plane.getRotate() + 1);
			}));
			barrel_roll.setCycleCount(360);
			barrel_roll.play();
		}
		if (e.getCode() == KeyCode.END) {
			Timeline barrel_roll1 = new Timeline(new KeyFrame(Duration.millis(0.1), (d) -> {
				_plane1.setRotate(_plane1.getRotate() + 1);
			}));
			barrel_roll1.setCycleCount(360);
			barrel_roll1.play();
		}
		if (e.getCode() == KeyCode.W) {
			_plane.setLayoutY(_plane.getLayoutY() - 20);
			System.out.println("W");
		}
		if (e.getCode() == KeyCode.S) {
			_plane.setLayoutY(_plane.getLayoutY() + 20);
		}
		if (e.getCode() == KeyCode.A) {
			_plane.setLayoutX(_plane.getLayoutX() - 20);
		}
		if (e.getCode() == KeyCode.D) {
			_plane.setLayoutX(_plane.getLayoutX() + 20);
		}
		if (e.getCode() == KeyCode.UP) {
			_plane1.setLayoutY(_plane1.getLayoutY() - 20);
			System.out.println("UP");
		}
		if (e.getCode() == KeyCode.DOWN) {
			_plane1.setLayoutY(_plane1.getLayoutY() + 20);
		}
		if (e.getCode() == KeyCode.LEFT) {
			_plane1.setLayoutX(_plane1.getLayoutX() - 20);
		}
		if (e.getCode() == KeyCode.RIGHT) {
			_plane1.setLayoutX(_plane1.getLayoutX() + 20);
		}
		if (e.getCode() == KeyCode.SPACE) {
			Label newBullet = new Label(_bullet.getText());
			newBullet.setLayoutX(_plane.getLayoutX() + _plane.getWidth());
			newBullet.setLayoutY(_plane.getLayoutY() + _plane.getHeight() / 2 - _bullet.getHeight() / 2);
			_bullets.push(newBullet);
			_field.getChildren().add(newBullet);
		}
		if (e.getCode() == KeyCode.NUMPAD0) {
			Label newBullet1 = new Label(_bullet1.getText());
			newBullet1.setLayoutX(_plane1.getLayoutX());
			newBullet1.setLayoutY(_plane1.getLayoutY() - _plane1.getHeight() / 2 + _bullet1.getHeight() / 2);
			_bullets1.push(newBullet1);
			_field.getChildren().add(newBullet1);
		}
	};

	public void initialize(URL arg0, ResourceBundle arg1) {
		Timeline fps = new Timeline(new KeyFrame(Duration.millis(1000 / 60), (e) -> {
			ArrayList<Label> tBullets = new ArrayList<Label>(_bullets);
			for (var b : tBullets) {
				b.setLayoutX(b.getLayoutX() + 10);
				if (b.getLayoutX() > _field.getWidth()) {
					_bullets.remove(b);
					_field.getChildren().remove(b);
				}
			}
		}));
		fps.setCycleCount(Timeline.INDEFINITE);
		fps.play();
		Timeline fps1 = new Timeline(new KeyFrame(Duration.millis(1000 / 60), (e) -> {
			ArrayList<Label> tBullets1 = new ArrayList<Label>(_bullets1);
			for (var b : tBullets1) {
				b.setLayoutX(b.getLayoutX() - 10);
				if (b.getLayoutX() < 0) {
					_bullets1.remove(b);
					_field.getChildren().remove(b);
				}
			}
		}));
		fps1.setCycleCount(Timeline.INDEFINITE);
		fps1.play();
	}
}