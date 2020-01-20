package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PeachController {
	
	@FXML
	private Button Save, start, Quit;
	
	
	public void Start(ActionEvent event) {
		 try {
	         Parent root = FXMLLoader.load(getClass().getResource("Peach2.fxml")); // 저번에 설명한 경로 땡겨오기
	         Scene s = new Scene(root);
	         Stage Stage = (Stage) start.getScene().getWindow();
	         Stage.setX(0);
	         
	         Stage.setScene(s);
	         //Stage.show();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	}
	public void window_cloes() {
		Stage Stage = (Stage) Quit.getScene().getWindow();
        Stage.close();
	}
	
	
	
}
