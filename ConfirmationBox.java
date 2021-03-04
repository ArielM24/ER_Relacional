import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.event.*;

public class ConfirmationBox{
	static Stage stage;
	static boolean btnYesClicked;
	public static boolean show(String message,String title,String textYes,String textNo){
		btnYesClicked = false;
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(250);
		Label lbl = new Label(message);
		Button btnYes = new Button(textYes);
		btnYes.setOnAction(e->ClickedYes());
		Button btnNo = new Button(textNo);
		btnNo.setOnAction(e->ClickedNo());
		HBox panebtn = new HBox(20);
		panebtn.getChildren().addAll(btnYes,btnNo);
		panebtn.setPadding(new Insets(5));
		panebtn.setAlignment(Pos.CENTER);
		VBox pane = new VBox(20);
		pane.getChildren().addAll(lbl,panebtn);
		pane.setAlignment(Pos.CENTER);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.showAndWait();
		return btnYesClicked;
	}
	private static void ClickedYes(){
		stage.close();
		btnYesClicked=true;
	}
	private static void ClickedNo(){
		stage.close();
		btnYesClicked=false;
	}
}
