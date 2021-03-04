import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.event.*;
import java.util.ArrayList;
import javafx.collections.*;
public class EditarEntidad{
	private static Stage stage;
	private static Button btnOk,btnCancel,btnAddS,btnDelS,btnLlaveS,btnAddC,btnDelC,btnLlaveC;
	private static Button btnAddM,btnDelM,btnLlaveM;
	private static TextField txtNombreS,txtCompuesto,txtMulti;
	private static Label lblmgs,lblmgc,lblmgm;
	private static ListView<String> listaSimples,listaMulti;
	private static ListView<Compuesto> listaCompuestos;
	private static ArrayList<String> simplesCancela,multiCancela;
	private static ArrayList<Compuesto> compuestosCancela;
	public static void show(Entidad entidad,boolean btnLlave,String stylesheet){
		boolean btnllave=btnLlave;
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Definir: "+entidad.toString());
		simplesCancela = new ArrayList<String>(entidad.getSimples());
		compuestosCancela = new ArrayList<Compuesto>(entidad.getCompuestos());
		multiCancela = new ArrayList<String>(entidad.getMulti());
		lblmgs = new Label("Ingrese los atributos simples");
		txtNombreS = new TextField();
		txtNombreS.setTooltip(new Tooltip("Ingrese los atributos\n simples de la entidad"));
		txtNombreS.setPromptText("Atributos simples");
		txtNombreS.setMinWidth(200);
		txtNombreS.setMaxWidth(250);
		btnAddS = new Button("Agregar");
		btnAddS.setOnAction(e->btnAddSClick(entidad));
		btnDelS = new Button("Borrar");
		btnDelS.setOnAction(e->btnDelSClick(entidad));
		listaSimples = new ListView<String>();
		listaSimples.setMaxWidth(200);
		listaSimples.setMaxHeight(250);
		listaSimples.setItems(FXCollections.observableList(entidad.getSimples()));
		listaSimples.getSelectionModel().selectedItemProperty().addListener(
			(valor,viejoValor,nuevoValor)->listaSimplesClick(nuevoValor,btnllave)
		);
		btnOk = new Button("OK");
		btnOk.setOnAction(e->btnOkClick());
		btnCancel = new Button("Cancelar");
		btnCancel.setOnAction(e->btnCancelClick(entidad));
		HBox panelBtn = new HBox(10,btnOk,btnCancel);
		panelBtn.setAlignment(Pos.CENTER);
		panelBtn.setPadding(new Insets(10));
		HBox panelBtnS = new HBox(10,btnAddS,btnDelS);
		VBox panelSimple = new VBox(10,lblmgs,txtNombreS,panelBtnS,listaSimples);
		panelSimple.setPadding(new Insets(10));
		lblmgc = new Label("Ingrese atributos compuestos");
		btnAddC = new Button("Agregar");
		btnAddC.setOnAction(e->btnAddCClick(entidad));
		btnDelC = new Button("Borrar");
		btnDelC.setOnAction(e->btnDelCClick(entidad));
		txtCompuesto = new TextField();
		txtCompuesto.setPromptText("Atributos compuestos");
		HBox panelBtnC = new HBox(10,btnAddC,btnDelC);
		listaCompuestos = new ListView<Compuesto>();
		listaCompuestos.setMaxWidth(200);
		listaCompuestos.setMaxHeight(250);
		listaCompuestos.setItems(FXCollections.observableList(entidad.getCompuestos()));
		listaCompuestos.getSelectionModel().selectedItemProperty().addListener(
			(valor,viejoValor,nuevoValor)->listaCompuestosClick(nuevoValor,btnllave)
		);
		VBox panelCompuesto = new VBox(10,lblmgc,txtCompuesto,panelBtnC,listaCompuestos);
		panelCompuesto.setPadding(new Insets(10));
		lblmgm = new Label("Ingrese atributos multivalor");
		btnAddM = new Button("Agregar");
		btnAddM.setOnAction(e->btnAddMClick(entidad));
		btnDelM = new Button("Borrar");
		btnDelM.setOnAction(e->btnDelMClick(entidad));
		txtMulti = new TextField();
		txtMulti.setPromptText("Atributos multivalor");
		HBox panelBtnM = new HBox(10,btnAddM,btnDelM);
		listaMulti = new ListView<String>();
		listaMulti.setMaxWidth(200);
		listaMulti.setMaxHeight(250);
		listaMulti.setItems(FXCollections.observableList(entidad.getMulti()));
		listaMulti.getSelectionModel().selectedItemProperty().addListener(
			(valor,viejoValor,nuevoValor)->listaMultiClick(nuevoValor,btnllave)
		);
		if(btnllave){
			btnLlaveS = new Button("Definir llave");
			btnLlaveS.setOnAction(e->btnLlaveSClick(entidad,true));
			btnLlaveS.setDisable(true);
			panelBtnS.getChildren().add(btnLlaveS);
			btnLlaveC = new Button("Definir llave");
			btnLlaveC.setOnAction(e->btnLlaveCClick(entidad,true));
			btnLlaveC.setDisable(true);
			panelBtnC.getChildren().add(btnLlaveC);
			btnLlaveM = new Button("Definir llave");
			btnLlaveM.setOnAction(e->btnLlaveMClick(entidad,true));
			btnLlaveM.setDisable(true);
			panelBtnM.getChildren().add(btnLlaveM);
		}
		btnDelS.setDisable(true);
		btnDelM.setDisable(true);
		btnDelC.setDisable(true);
		VBox panelMulti = new VBox(10,lblmgm,txtMulti,panelBtnM,listaMulti);
		panelMulti.setPadding(new Insets(10));
		BorderPane panePrincipal = new BorderPane();
		HBox paneCentral = new HBox(10,panelSimple,panelCompuesto,panelMulti);
		panePrincipal.setCenter(paneCentral);
		panePrincipal.setBottom(panelBtn);
		Scene scene = new Scene(panePrincipal);
		scene.getStylesheets().add(stylesheet);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.showAndWait();
	}
	private static void btnAddSClick(Entidad entidad){
		String simple = txtNombreS.getText();
		if(Metodos.verificaNombre(simple,true)){
			if(entidad.agregaSimple(simple)){
				listaSimples.setItems(FXCollections.observableList(entidad.getSimples()));
			}else{
				MessageBox.show("Este atributo ya existe","Error");
			}
		}else{
			MessageBox.show("Nombre no valido","Error");
		}
		txtNombreS.clear();
	}
	private static void btnAddMClick(Entidad entidad){
		String multi = txtMulti.getText();
		if(Metodos.verificaNombre(multi,true)){
			if(entidad.agregaMulti(multi)){
				listaMulti.setItems(FXCollections.observableList(entidad.getMulti()));
			}else{
				MessageBox.show("Este atributo ya existe","Error");
			}
		}else{
			MessageBox.show("Nombre no valido","Error");
		}
		txtMulti.clear();
	}
	private static void btnAddCClick(Entidad entidad){
		String valor = txtCompuesto.getText();
		if(Metodos.verificaNombre(valor,false)){
			if(entidad.agregaCompuesto(Metodos.capturaCompuesto(valor))){
				listaCompuestos.setItems(FXCollections.observableList(entidad.getCompuestos()));
			}else{
				MessageBox.show("Este atributo ya existe","Error");
			}
		}else{
			MessageBox.show("Nombre no valido","Error");
		}
		txtCompuesto.clear();
	}
	private static void btnOkClick(){
		MessageBox.show("Atributos definidos","");
		stage.close();
	}
	private static void btnDelSClick(Entidad entidad){
		String aux = listaSimples.getSelectionModel().getSelectedItem();
		if(aux!=null){
			entidad.getSimples().remove(aux);
			listaSimples.setItems(FXCollections.observableList(entidad.getSimples()));	
		}
		if(entidad.getSimples().size()==0&&entidad.getCompuestos().size()==0&&
			entidad.getMulti().size()==0){
			entidad.setLlave("ID"+entidad.getNombre());
		}
		String aux2 = listaSimples.getSelectionModel().getSelectedItem();
		if(aux2==null){
			btnDelS.setDisable(true);
		}else{
			btnDelS.setDisable(false);
		}
	}
	private static void btnDelMClick(Entidad entidad){
		String aux = listaMulti.getSelectionModel().getSelectedItem();
		if(aux!=null){
			entidad.getMulti().remove(aux);
			listaMulti.setItems(FXCollections.observableList(entidad.getMulti()));	
		}
		if(entidad.getSimples().size()==0&&entidad.getCompuestos().size()==0&&
			entidad.getMulti().size()==0){
			entidad.setLlave("ID"+entidad.getNombre());
		}
		String aux2 = listaMulti.getSelectionModel().getSelectedItem();
		if(aux2==null){
			btnDelM.setDisable(true);
		}else{
			btnDelM.setDisable(false);
		}
	}
	private static void btnDelCClick(Entidad entidad){
		Compuesto aux = listaCompuestos.getSelectionModel().getSelectedItem();
		if(aux!=null){
			entidad.getCompuestos().remove(aux);
			listaCompuestos.setItems(FXCollections.observableList(entidad.getCompuestos()));	
		}
		if(entidad.getSimples().size()==0&&entidad.getCompuestos().size()==0&&
			entidad.getMulti().size()==0){
			entidad.setLlave("ID"+entidad.getNombre());
		}
		Compuesto aux2 = listaCompuestos.getSelectionModel().getSelectedItem();
		if(aux2==null){
			btnDelC.setDisable(true);
		}else{
			btnDelC.setDisable(false);
		}
	}
	private static void btnLlaveSClick(Entidad entidad,boolean btnllave){
		String aux = listaSimples.getSelectionModel().getSelectedItem();
		if(aux!=null&&btnllave){
			entidad.setLlave(aux);
			entidad.getSimples().remove(aux);
			MessageBox.show("Llave cambiada","Llave");
		}
	}
	private static void btnLlaveMClick(Entidad entidad,boolean btnllave){
		String aux = listaMulti.getSelectionModel().getSelectedItem();
		if(aux!=null&&btnllave){
			entidad.setLlave(aux);
			entidad.getSimples().remove(aux);
			MessageBox.show("Llave cambiada","Llave");
		}
	}
	private static void btnLlaveCClick(Entidad entidad,boolean btnllave){
		Compuesto aux = listaCompuestos.getSelectionModel().getSelectedItem();
		if(aux!=null&&btnllave){
			entidad.setLlave(aux);
			entidad.getSimples().remove(aux);
			MessageBox.show("Llave cambiada","Llave");
		}
	}
	private static void listaSimplesClick(String valor,boolean btnllave){
		if(valor!=null&&btnllave){
			btnLlaveS.setDisable(false);
			btnDelS.setDisable(false);
		}else if(btnllave){
			btnLlaveS.setDisable(true);
		}else if(valor!=null){
			btnDelS.setDisable(false);
		}else{
			btnDelS.setDisable(true);
		}
	}
	private static void listaMultiClick(String valor,boolean btnllave){
		if(valor!=null&&btnllave){
			btnLlaveM.setDisable(false);
		}else if(btnllave){
			btnLlaveM.setDisable(true);
		}if(valor!=null){
			btnDelM.setDisable(false);
		}else{
			btnDelM.setDisable(true);
		}
	}
	private static void listaCompuestosClick(Compuesto valor,boolean btnllave){
		if(valor!=null&&btnllave){
			btnLlaveC.setDisable(false);
		}else if(btnllave){
			btnLlaveC.setDisable(true);
		}if(valor!=null){
			btnDelC.setDisable(false);
		}else{
			btnDelC.setDisable(true);
		}
	}
	private static void btnCancelClick(Entidad entidad){
		if(ConfirmationBox.show("Desea cancelar","Cancelar","Si","No")){
			entidad.setSimples(simplesCancela);
			entidad.setCompuestos(compuestosCancela);
			entidad.setMulti(multiCancela);
			stage.close();
		}
	}
}