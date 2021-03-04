import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import java.util.ArrayList;
public class MessageBox{
	public static void show(String message,String title) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(250);
		Label lblmsg = new Label(message);
		Button btnOk = new Button("OK");
		btnOk.setOnAction(e->stage.close());
		VBox pane = new VBox(20);
		pane.getChildren().addAll(lblmsg,btnOk);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(5));
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.showAndWait();
	}
	public static void showArea(ArrayList<String> cadenas,String title,String style){
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(250);
		TextArea lista = new TextArea("");
		for(String aux: cadenas){
			lista.setText(lista.getText()+aux+"\n");
		}
		Button btnOk = new Button("OK");
		btnOk.setOnAction(e->stage.close());
		VBox pane = new VBox(20,lista,btnOk);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(5));
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		scene.getStylesheets().add(style);
		stage.setResizable(false);
		stage.showAndWait();
	}
	public static void showDoubleArea(ArrayList<String> cadenas1,ArrayList<String> cadenas2,String title,String cad1,String cad2,String style){
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(500);
		TextArea lista1 = new TextArea("");
		TextArea lista2 = new TextArea("");
		lista1.setMaxWidth(230);
		lista1.setMaxHeight(200);
		lista2.setMaxWidth(230);
		lista2.setMaxHeight(200);
		Label lbllista1 = new Label(cad1);
		Label lbllista2 = new Label(cad2);
		for(String aux: cadenas1){
			lista1.setText(lista1.getText()+aux+"\n");
		}
		for(String aux: cadenas2){
			lista2.setText(lista2.getText()+aux+"\n");
		}
		Button btnOk = new Button("OK");
		btnOk.setOnAction(e->stage.close());
		VBox pane1 = new VBox(10,lbllista1,lista1);
		pane1.setAlignment(Pos.CENTER);
		VBox pane2 = new VBox(10,lbllista2,lista2);
		pane2.setAlignment(Pos.CENTER);
		FlowPane paneDob = new FlowPane(10,10,pane1,pane2,btnOk);
		paneDob.setPrefWrapLength(480);
		paneDob.setAlignment(Pos.CENTER);
		paneDob.setPadding(new Insets(5));
		Scene scene = new Scene(paneDob);
		stage.setScene(scene);
		stage.setResizable(false);
		scene.getStylesheets().add(style);
		stage.showAndWait();
	}
}