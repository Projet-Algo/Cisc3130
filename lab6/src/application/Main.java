package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class Main extends Application {

	TextField tfNum1; // Practice Problems
	TextField tfNum2; // Project Labs
	TextField tfNum3; // Midterms
	TextField tfNum4; // Final
	TextField tfNum5; // Goal 
	Button btnDivide;
	Button btnMultiply;
	Button btnAddition;
	Button btnSubtraction;
	Button btnClear;
	Label lblAnswer;
	Label Practice;
	Label Labs;
	Label Midterms;
	Label Final;
	Label Goal;

	@Override
	public void start(Stage primaryStage) {
		try {

			tfNum1 = new TextField();
			tfNum2 = new TextField();
			tfNum3 = new TextField();
			tfNum4 = new TextField();
			tfNum5 = new TextField();

			btnAddition = new Button("+");
			btnClear = new Button("Clear");
			lblAnswer = new Label(" Result ");

			Practice = new Label(" Practice ");
			Labs = new Label(" Labs ");
			Midterms = new Label(" Midterms");
			Final = new Label("Final");
			Goal = new Label("    Goal  \"A - F\" ");


			lblAnswer.setAlignment(Pos.CENTER);
			lblAnswer.setStyle("-fx-border-color: #000; -fx-padding: 5px;");

			GridPane root = new GridPane();
			root.setAlignment(Pos.CENTER);

			root.setHgap(10);
			root.setVgap(10);

			root.add(btnAddition, 0, 0);
			root.add(Practice, 0, 1);
			root.add(tfNum1, 0, 2); //

			root.add(Labs, 1, 1);
			root.add(tfNum2, 1, 2); //

			root.add(Midterms, 0, 3);
			root.add(tfNum3, 0, 4);

			root.add(Final, 1, 3);
			root.add(tfNum4, 1, 4);

			root.add(Goal, 2, 2);
			root.add(tfNum5, 2, 3);

			root.add(btnClear, 0, 6, 2, 1);


			root.add(lblAnswer, 0, 5, 2, 1);

			setWidths();
			attachCode();


			Scene scene = new Scene(root, 400, 350);
			
			primaryStage.setTitle("CalculatorFX 1.0");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void setWidths() {
		tfNum1.setPrefWidth(70);
		tfNum2.setPrefWidth(70);
		tfNum3.setPrefWidth(70);
		tfNum4.setPrefWidth(70);
		tfNum5.setPrefWidth(80);
		btnAddition.setPrefWidth(70);
		btnClear.setPrefWidth(150);
		lblAnswer.setMinSize(150, 20);
		lblAnswer.autosize();
	}

	public void attachCode() {
		btnAddition.setOnAction(e -> btncode(e));
		btnClear.setOnAction(e -> btncode(e));
	}


	public void btncode(ActionEvent e) {
		int num1, num2,num3,num4,answer; String num5;
		//e tells us which button was clicked
		if (e.getSource() == btnClear) {
			tfNum1.setText("");
			tfNum2.setText("");
			tfNum3.setText("");
			tfNum4.setText("");
			tfNum5.setText("");
			lblAnswer.setText(" Result ");
			tfNum1.requestFocus(); // ?
			return;
		}
		num1 = Integer.parseInt(tfNum1.getText());
		num2 = Integer.parseInt(tfNum2.getText());
		num3 = Integer.parseInt(tfNum3.getText());
		num4 = Integer.parseInt(tfNum4.getText());
		num5 = tfNum5.getText();

		int value;

		if(Character.toLowerCase(num5.trim().charAt(0)) == 'a') {
			value = 90;
		}
		else if(Character.toLowerCase(num5.trim().charAt(0)) == 'b') {
			value = 80;
		}
		else if(Character.toLowerCase(num5.trim().charAt(0)) == 'c') {
			value = 70;
		}
		else if(Character.toLowerCase(num5.trim().charAt(0)) == 'd') {
			value = 60;
		}
		else if(Character.toLowerCase(num5.trim().charAt(0)) == 'f') {
			value = 59;
		} else value = -1;


		if (e.getSource() == btnAddition) {
			answer = num1 + num2 +num3+ num4;

			
			if(value == -1) {
				lblAnswer.setText(" Wrong Entry for the grades");
				
				return;
			}
			else {
				String result = "";
				
				if(value == -1) result=" Wrong Entry for the Goal; it must be a character between A to F except E";
				else if(answer >= value) result=" Congratulations, you achieve your goal ";
				else {
					result = " and you need a minimum of "+(value - answer)+" to achieve your goal. Good luck!";
				}
				
				lblAnswer.setText(" Your current grade is: "+answer+" "+result);
			}
		} 
		

	}



	public static void main(String[] args) {

		launch(args);
	}
}
