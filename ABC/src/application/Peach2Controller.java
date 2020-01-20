package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Peach2Controller implements Initializable {

	@FXML
	private Button Back;
	@FXML
	private ImageView background1, background2;
	@FXML
	private Label check_label;

	@FXML
	private Group user;

	@FXML
	Rectangle rec1, rec2, rec3, rec4, rec5, rec6, rec7, rec8, rec9;

	private Rectangle[] Rec_arr;// = {rec1, rec2, rec3, rec4};

	private Task<Void> task;
	//private Task<Void> task2;

	private boolean jump_check = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		user.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				try {
					user_move(event);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		initializeGroupArray();

		gravity();
		//jump();
	}

	public void user_move(KeyEvent event) throws Exception{
		KeyCode keyCode = event.getCode();
		System.out.println(keyCode);
		

		check_label.setText(user.getLayoutY() + "" + jump_check);
		if (KeyCode.UP.equals(keyCode)) {
			if(!bottom_check()) jump_check = true;
		} else if (KeyCode.LEFT.equals(keyCode) && behind_check()) {
			user.setLayoutX(user.getLayoutX() - 10);
			user.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		} else if (KeyCode.RIGHT.equals(keyCode) && front_check()) {
			user.setLayoutX(user.getLayoutX() + 10);
			user.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
		}
		
	}

	public void initializeGroupArray() {
		Rec_arr = new Rectangle[9];
		Rec_arr[0] = rec1;
		Rec_arr[1] = rec2;
		Rec_arr[2] = rec3;
		Rec_arr[3] = rec4;
		Rec_arr[4] = rec5;
		Rec_arr[5] = rec6;
		Rec_arr[6] = rec7;
		Rec_arr[7] = rec8;
		Rec_arr[8] = rec9;
	}
	public boolean getjump_check() {
		return jump_check;
	}
	public void setjump_check(boolean jump_check) {
		this.jump_check = jump_check;
	}

	public void gravity() {
		task = new Task<Void>() {

			@Override
			public Void call() throws Exception {
				for (;;) {
					if (getjump_check()) {
						
						for (int i = -13, user_height =  (int)user.getLayoutY(); i <= 14; i++) {
							user.setLayoutY(user_height - (-(i * i) + 196));
							Thread.sleep(40);
							if(!bottom_check()) break;
							//System.out.println(1);
						}
						setjump_check(false);
					}
					if (bottom_check()) {
						user.setLayoutY(user.getLayoutY() + 9);}
					Thread.sleep(30);
					
				}
			}
		};

		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}


	public boolean bottom_check() {
	      boolean check = true;// false면 안떨어짐(이건 키보드 입력 쪽에)//true면 떨어짐
	      double[] img_side = { 
	            user.getLayoutX(), 
	            user.getLayoutX() + user.getLayoutBounds().getMaxX(),
	            user.getLayoutY() + user.getLayoutBounds().getMaxY() };
	      if (user.getLayoutY() > 520)
	         return false; // 아래로 더이상 안떨어지도록
	      
	      for (int j = 0; j < Rec_arr.length; j++) {
	         double[] Rec_side = { 
	               Rec_arr[j].getLayoutX(), // 최소 X좌표
	               Rec_arr[j].getLayoutBounds().getMaxX() + Rec_arr[j].getLayoutX(), // 최대 X좌표
	               Rec_arr[j].getLayoutY(),
	               Rec_arr[j].getLayoutBounds().getMaxY() + Rec_arr[j].getLayoutY()};
	         if(Rec_side[0] < img_side[1] && img_side[0] < Rec_side[1])
	            if(Rec_side[2] - 10 < img_side[2] && img_side[2] < Rec_side[2] + 10) {
	            	user.setLayoutY(Rec_side[2] - user.getLayoutBounds().getMaxY());
	            	check = false;
					return check;
	            }

	      }
	      return check;
	   }

	public boolean front_check() {
		boolean check = true;// false면 안떨어짐(이건 키보드 입력 쪽에)//true면 떨어짐

		double[] img_side = {
				user.getLayoutY(),
				user.getLayoutY() + user.getLayoutBounds().getMaxY(),
				user.getLayoutX() + user.getLayoutBounds().getMaxX()};
			for (int j = 0; j < Rec_arr.length; j++) {
				double[] Rec_side = { 
						Rec_arr[j].getLayoutY(), 
						Rec_arr[j].getLayoutBounds().getMaxY() + Rec_arr[j].getLayoutY(),
						Rec_arr[j].getLayoutX(),
						Rec_arr[j].getLayoutBounds().getMaxX() + Rec_arr[j].getLayoutX()};
				if (Rec_side[0] < img_side[1] && img_side[0] < Rec_side[1])
					if (Rec_side[2] <= img_side[2] && img_side[2] < Rec_side[2] + 20) {
						
						user.setLayoutX(Rec_side[2] - user.getLayoutBounds().getMaxX());
						check = false;
						return check;
					}

			}
		return check;
	}

	public boolean behind_check() {
		boolean check = true;// false면 안떨어짐(이건 키보드 입력 쪽에)//true면 떨어짐

		double[] img_side = {
				user.getLayoutY(),
				user.getLayoutY() + user.getLayoutBounds().getMaxY(),
				user.getLayoutX()};
			for (int j = 0; j < Rec_arr.length; j++) {
				double[] Rec_side = { 
						Rec_arr[j].getLayoutY(), 
						Rec_arr[j].getLayoutBounds().getMaxY() + Rec_arr[j].getLayoutY(),
						Rec_arr[j].getLayoutBounds().getMaxX() + Rec_arr[j].getLayoutX()};
				if (Rec_side[0] < img_side[1] && img_side[0] < Rec_side[1])
					if (Rec_side[2] - 20 < img_side[2] && img_side[2] <= Rec_side[2]) {
						
						user.setLayoutX(Rec_side[2]);
						check = false;
						return check;
					}

			}
		return check;
	}

	public void Back(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Peach.fxml")); // 저번에 설명한 경로 땡겨오기
			Scene s = new Scene(root);
			Stage Stage = (Stage) Back.getScene().getWindow();

			Stage.setScene(s);
			// Stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void Restart() {
		user.setLayoutX(rec1.getLayoutX());
		user.setLayoutY(rec1.getLayoutY() - user.getLayoutBounds().getMaxY());
	}

}
