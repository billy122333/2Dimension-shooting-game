package Final_Project;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;

import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.swing.*;

public class Story_Mode_Controller implements Initializable, EventHandler<KeyEvent> {

	@FXML
	public Pane _field;
	@FXML
	public ImageView _Gunslinger;
	@FXML
	public ImageView _bullet;
	@FXML
	public Rectangle HP;
	@FXML
	public Label _Loser;
	@FXML
	public ImageView _Monster1;
	@FXML
	public ImageView _Monster2;
	@FXML
	public ImageView _Boss;
	@FXML
	public Text Player_Money;
	@FXML
	public Text attack_pt;
	@FXML
	public Text _Building;

	LinkedList<ImageView> _bullets = new LinkedList<>();
	LinkedList<ImageView> _Monsters = new LinkedList<>();
//	TranslateTransition translation_up ;
//	TranslateTransition translation_down ;
	Timeline jumping;
	Timeline move;
	Timeline HP_Control;
	Double HP_Long;
	Double HP_per;
	boolean jump_bool = true; // prevent double jumping
	double count = 0;
	int Boss = 0;
	boolean mayorExist = false;
	Parent Game_over = null;
	Parent Win = null;

	Enemy BMonster;
	int Money = 100;
	Hero Player;
	int counter = 0;
	int Mon_HP = 3;
	int building = 2;

	public void Jump() {
		jumping = new Timeline();
		KeyFrame start = new KeyFrame(Duration.ZERO, new KeyValue(_Gunslinger.layoutYProperty(), 300));
		KeyFrame mid = new KeyFrame(Duration.millis(300), new KeyValue(_Gunslinger.layoutYProperty(), 150));
		KeyFrame end = new KeyFrame(Duration.millis(600), new KeyValue(_Gunslinger.layoutYProperty(), 300));

		jumping.getKeyFrames().addAll(start, mid, end);
//		jumping.setCycleCount(10);
		jumping.play();
	}

//		TranslateTransition translation_up = new TranslateTransition(Duration.millis(300), _Gunslinger);
//		translation_up.setAutoReverse(true);
//		translation=new Timeline(new KeyFrame(Duration.millis(50),(s)-> {
//			_Gunslinger.setLayoutY(_Gunslinger.getLayoutY()-100);}));
	public void handle(KeyEvent e) {
		if (jump_bool && e.getCode() == KeyCode.W) {
			jump_bool = false;
			Jump();
			jumping.setOnFinished((evt) -> {
				jump_bool = true;
			});

		}
		if (e.getCode() == KeyCode.SPACE) {
			ImageView newBullet = new ImageView(_bullet.getImage());
			newBullet.setLayoutX(_Gunslinger.getLayoutX() + _Gunslinger.getFitWidth() - 85);
			newBullet.setLayoutY(_Gunslinger.getLayoutY() + _Gunslinger.getFitHeight() / 4 - 10);
			_bullets.push(newBullet);
			_field.getChildren().add(newBullet);

		}
		if (e.getCode() == KeyCode.D) {
			move = new Timeline();
			KeyFrame start = new KeyFrame(Duration.ZERO,
					new KeyValue(_Gunslinger.layoutXProperty(), _Gunslinger.getLayoutX()));
			KeyFrame end = new KeyFrame(Duration.millis(300),
					new KeyValue(_Gunslinger.layoutXProperty(), _Gunslinger.getLayoutX() + 100));
//			_Gunslinger.setLayoutX(_Gunslinger.getLayoutX() + 20);
			move.getKeyFrames().addAll(start, end);
			move.play();
		}
		if (e.getCode() == KeyCode.A) {
			move = new Timeline();
			KeyFrame start = new KeyFrame(Duration.ZERO,
					new KeyValue(_Gunslinger.layoutXProperty(), _Gunslinger.getLayoutX())); // set()
			KeyFrame end = new KeyFrame(Duration.millis(300),
					new KeyValue(_Gunslinger.layoutXProperty(), _Gunslinger.getLayoutX() - 100));
//			_Gunslinger.setLayoutX(_Gunslinger.getLayoutX() + 20);
			move.getKeyFrames().addAll(start, end);
			move.play();
		}
	}

	public void increase_attack_point() { // upgrade attack point
		if (Money > 100) {
			Player_Money.setText("" + (Money - 100));
			Money -= 100;
			Player.setatk_pt(Player.getatk_pt() + 1);
			attack_pt.setText("·í«e§ðÀ»¤O:" + Player.getatk_pt());
			_field.requestFocus();
		} else {
			System.out.println("Don't have enough money");
			_field.requestFocus();
		}
	}

	public void buy_bullet() {

	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		Enemy AMonster = new Enemy("AMonster", Mon_HP + 1, Mon_HP + 8);
		Enemy BMonster = new Enemy("BMonster", Mon_HP, Mon_HP + 12); // Name HP Money
		Enemy mayor = new Enemy("mayor Hen", 69, 30000);

		HP_Long = HP.getWidth();
		HP_per = HP_Long / 10;
		// create Hero
		Player = new Hero("Jack");
		Player.setatk_pt(1);
		Player.setHP(10);

		// add monster in list

		Timeline fps = new Timeline(new KeyFrame(Duration.millis(1000 / 60), (e) -> {
			// bullet move
			ArrayList<ImageView> tBullets = new ArrayList<ImageView>(_bullets);
			for (var b : tBullets) {
				b.setLayoutX(b.getLayoutX() + 10);
				// bullet out of bound
				if (b.getLayoutX() > _field.getWidth()) {
					_bullets.remove(b);
					_field.getChildren().remove(b);
				}
			}
		}));
		// Monster move
		Timeline Monster_Move = new Timeline(new KeyFrame(Duration.millis(200), (e) -> {
			ArrayList<ImageView> tMonsters = new ArrayList<ImageView>(_Monsters);
			for (var b : tMonsters) {
				b.setLayoutX(b.getLayoutX() - 30);
				if (_field.getWidth() - b.getLayoutX() >= _field.getWidth()) {
					_Monsters.remove(b);
					_field.getChildren().remove(b);
				}
				// hit flying monster

				if (b.getLayoutX() + 10 <= _Gunslinger.getLayoutX() + _Gunslinger.getFitWidth()
						&& _Gunslinger.getLayoutY() < b.getLayoutY() + b.getFitHeight()
						&& b.getLayoutY() + b.getFitHeight() < _Gunslinger.getLayoutY() + _Gunslinger.getFitHeight()) {
					// set Player HP
					HP.setFill(Color.BLUE);
					Player.setHP(Player.getHP() - 1);
					HP_Control = new Timeline();

					KeyFrame start = new KeyFrame(Duration.ZERO, new KeyValue(HP.widthProperty(), HP_Long));
					KeyFrame end = new KeyFrame(Duration.millis(300),
							new KeyValue(HP.widthProperty(), HP_Long - HP_per));
					HP_Control.getKeyFrames().addAll(start, end);
					HP_Control.setOnFinished((evt) -> {
						HP.setFill(Color.RED);
					});
					HP_Control.play();
					HP_Long = HP_Long - HP_per;

					// if monster hit player,monster remove
					_Monsters.remove(b);
					_field.getChildren().remove(b);
					if (Player.getHP() == 0) {
						try {
							Game_over = FXMLLoader.load(getClass().getResource("Gameover.fxml"));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						Scene gv = new Scene(Game_over);
						gv.getRoot().requestFocus();
						Final_Project.currentStage.setScene(gv);
					}
				}
				// Lose 1
				else if (b.getLayoutX() <= 102) {
					building -= 1;
					_Monsters.remove(b);
					_field.getChildren().remove(b);
					_Building.setText("HP = " + building);
					if (building <= 0) {

						try {
							Game_over = FXMLLoader.load(getClass().getResource("Gameover.fxml"));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						Scene gv = new Scene(Game_over);
						gv.getRoot().requestFocus();
						Final_Project.currentStage.setScene(gv);
					}

				}
				// hit ground monster
				if (b.getLayoutX() + 10 <= _Gunslinger.getLayoutX() + _Gunslinger.getFitWidth()
						&& _Gunslinger.getLayoutY() < b.getLayoutY() + b.getFitHeight()
						&& b.getLayoutY() < _Gunslinger.getLayoutY() + _Gunslinger.getFitHeight()) {
					// set Player HP
					HP.setFill(Color.BLUE);
					Player.setHP(Player.getHP() - 1);
					HP_Control = new Timeline();

					KeyFrame start = new KeyFrame(Duration.ZERO, new KeyValue(HP.widthProperty(), HP_Long));
					KeyFrame end = new KeyFrame(Duration.millis(300),
							new KeyValue(HP.widthProperty(), HP_Long - HP_per));
					HP_Control.getKeyFrames().addAll(start, end);
					HP_Control.setOnFinished((evt) -> {
						HP.setFill(Color.RED);
					});
					HP_Control.play();
					HP_Long = HP_Long - HP_per;

					// if monster hit player,monster remove
					_Monsters.remove(b);
					_field.getChildren().remove(b);
					if (Player.getHP() == 0) {
						try {
							Game_over = FXMLLoader.load(getClass().getResource("Gameover.fxml"));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						Scene gv = new Scene(Game_over);
						gv.getRoot().requestFocus();
						Final_Project.currentStage.setScene(gv);
					}
				}
				// hit enemy
				for (var c : _bullets) {
					if (b.getLayoutY() < c.getLayoutY() && c.getLayoutY() < b.getLayoutY() + b.getFitHeight()
							&& b.getLayoutX() - 100 < c.getLayoutX() + c.getFitWidth()) {
						if (c.getLayoutY() < 250 && c.getLayoutY() > 100 && !mayorExist) {
							// BMonster
							BMonster.setHP(BMonster.getHP() - Player.attack_point);
							_bullets.remove(c);
							_field.getChildren().remove(c);
							if (BMonster.getHP() <= 0) {
								_bullets.remove(c);
								Money += BMonster.getDropMoney();
								Player_Money.setText("" + Money);
								_Monsters.remove(b);
								_field.getChildren().remove(b);
								_field.getChildren().remove(c);
								BMonster.reset();
							}
						} else if (c.getLayoutY() > 270 && !mayorExist) {

							AMonster.setHP(AMonster.getHP() - Player.attack_point);
							_bullets.remove(c);
							_field.getChildren().remove(c);
							if (AMonster.getHP() <= 0) {
								_bullets.remove(c);
								Money += AMonster.getDropMoney();
								Player_Money.setText("" + Money);
								_Monsters.remove(b);
								_field.getChildren().remove(b);
								_field.getChildren().remove(c);
								AMonster.reset();
							}
						} else if (mayorExist) {
							mayor.setHP(mayor.getHP() - Player.attack_point);
							_bullets.remove(c);
							_field.getChildren().remove(c);
							if (mayor.getHP() <= 0) {
																try {
									Win = FXMLLoader.load(getClass().getResource("Win.fxml"));
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								Scene won = new Scene(Win);
								won.getRoot().requestFocus();
								Final_Project.currentStage.setScene(won);
								_bullets.remove(c);
								Money += mayor.getDropMoney();
								Player_Money.setText("" + Money);
								_Monsters.remove(b);
								_field.getChildren().remove(b);
								_field.getChildren().remove(c);


							}
						}
					}
				}
			}
		}));

		// Monster get in the scene

		Timeline Monster_access = new Timeline();

		KeyFrame start = new KeyFrame(Duration.seconds(2), (e) -> {
			ImageView newMonster = new ImageView(_Monster1.getImage());
			ImageView newMonster2 = new ImageView(_Monster2.getImage());
			ImageView BossHen = new ImageView(_Boss.getImage());

			// set monster size
			newMonster.setFitHeight(180);
			newMonster.setFitWidth(200);
			newMonster2.setFitHeight(150);
			newMonster2.setFitWidth(200);
			newMonster2.setRotate(-15);
			BossHen.setFitHeight(400);
			BossHen.setFitWidth(400);
			count = Math.random() * 10;
			System.out.println(count);
			if (counter > 30) {
				counter = 0;
				Mon_HP += 3;
			}
			if (Boss != 20) {
				if (count > 5) {
					counter++;
					Boss++;
					_Monsters.push(newMonster);
					_field.getChildren().add(newMonster);
					newMonster.setLayoutY(270);
					newMonster.setLayoutX(_field.getWidth());
					count = 0;
					System.out.println(AMonster.getHP());
				} else if (count < 5) {
					Boss++;
					counter++; // use to count monsters
					_Monsters.push(newMonster2);
					_field.getChildren().add(newMonster2);
					newMonster2.setLayoutY(100);
					newMonster2.setLayoutX(_field.getWidth());
					count = 0;
					System.out.println(BMonster.getHP());
				}
			} else {
				mayorExist = true;
				mayor.reset();
				_Monsters.push(BossHen);
				_field.getChildren().add(BossHen);
				BossHen.setLayoutY(56);
				BossHen.setLayoutX(_field.getWidth());
				Monster_access.stop();
				Boss = 0;
			}
		});
//		new KeyValue(newMonster.xProperty(),_field.getWidth()));

//		KeyFrame end= new KeyFrame(Duration.millis(3000),(e)-> {
//			_Monsters.push(newMonster);
//			_field.getChildren().add(newMonster);
//			newMonster.setLayoutY(250);
//		});
		Monster_access.getKeyFrames().addAll(start);
		/*
		 * new KeyFrame(Duration.seconds(Math.random() * 3), (e) -> { ImageView
		 * newMonster = new ImageView(_Monster1.getImage()); _Monsters.push(newMonster);
		 * _field.getChildren().add(newMonster);
		 * newMonster.setLayoutX(_field.getWidth()); newMonster.setLayoutY(250); }));
		 */

		Monster_Move.setCycleCount(Timeline.INDEFINITE);
		Monster_Move.play();
		Monster_access.setCycleCount(Timeline.INDEFINITE);
		Monster_access.play();
		fps.setCycleCount(Timeline.INDEFINITE);
		fps.play();
	}
}
