import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.collections.*;
import java.util.ArrayList;
import javax.swing.*;
public class VentanaSecundaria extends Application{
	private ObservableList<Entidad> entidades;
	private ObservableList<Relacion> relaciones;
	private Stage stage;
	private ListView<Entidad> listaEntidades;
	private ListView<Relacion> listaRelaciones;
	private Button btnAddR,btnDelR,btnEditar,btnOK;
	private TextField txtNombreR,txtCardinalidad,txtParticipacion;
	private ComboBox<String> cbCardinalidad,cbParticipacion;
	private RadioButton rbRecursiva,rbBinaria,rbMayor;
	private Label lblEntidades,lblRelaciones,lblAdd,lblGrado;
	private String stylesheet;
	public VentanaSecundaria(ObservableList<Entidad> entidades,String stylesheet){
		this.entidades=entidades;
		this.stylesheet=stylesheet;
	}
	@Override
	public void start(Stage primaryStage){
		stage = primaryStage;
		initComp();
		stage.setTitle("Relaciones");
		stage.setResizable(false);
		stage.show();
	}
	public void initComp(){
		listaEntidades = new ListView<Entidad>();
		listaEntidades.setItems(entidades);
		listaEntidades.setMaxWidth(150);
		listaEntidades.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lblEntidades = new Label("Entidades");
		listaRelaciones = new ListView<Relacion>();
		listaRelaciones.setMaxWidth(150);
		relaciones=listaRelaciones.getItems();
		lblRelaciones = new Label("Relaciones");
		lblAdd = new Label("Agregar relacion");
		txtNombreR = new TextField();
		txtNombreR.setPromptText("Nombre de la relacion");
		txtCardinalidad = new TextField();
		txtCardinalidad.setPromptText("1:N:...");
		cbCardinalidad = new ComboBox<String>();
		cbCardinalidad.getItems().addAll("1:1","1:N","N:1","M:N");
		txtParticipacion = new TextField();
		txtParticipacion.setPromptText("T:P:..");
		cbParticipacion = new ComboBox<String>();
		cbParticipacion.getItems().addAll("P:P","P:T","T:P","T:T");
		btnAddR = new Button("Agregar");
		btnDelR = new Button("Borrar");
		btnDelR.setDisable(true);
		btnEditar = new Button("Editar");
		btnEditar.setOnAction(e->btnEditarClick());
		btnEditar.setDisable(true);
		btnOK = new Button("OK");
		btnOK.setOnAction(e->btnOKClick());
		lblGrado = new Label("Grado");
		lblGrado.setMinWidth(100);
		rbRecursiva = new RadioButton("Recursiva");
		rbRecursiva.setOnAction(e->{
			listaEntidades.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			cbParticipacion.setDisable(false);
			cbCardinalidad.setDisable(false);
		});
		rbBinaria = new RadioButton("Binaria");
		rbBinaria.setOnAction(e->{
			listaEntidades.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			cbParticipacion.setDisable(false);
			cbCardinalidad.setDisable(false);
		});
		rbMayor = new RadioButton("Mayor a 2");
		rbMayor.setOnAction(e->{
			listaEntidades.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			cbParticipacion.setDisable(true);
			cbCardinalidad.setDisable(true);
		});
		rbBinaria.setSelected(true);
		ToggleGroup groupGrado = new ToggleGroup();
		rbRecursiva.setToggleGroup(groupGrado);
		rbBinaria.setToggleGroup(groupGrado);
		rbMayor.setToggleGroup(groupGrado);
		cbCardinalidad.setValue("M:N");
		cbParticipacion.setValue("T:T");
		rbRecursiva.setMinWidth(100);
		rbBinaria.setMinWidth(100);
		rbMayor.setMinWidth(100);
		btnAddR.setOnAction(e->btnAddRClick());
		btnDelR.setOnAction(e->btnDelRClick());
		listaRelaciones.getSelectionModel().selectedItemProperty().addListener(
			(valor,viejoValor,nuevoValor)->listaRelacionesClick(nuevoValor)
		);
		VBox paneEntidades = new VBox(10,lblEntidades,listaEntidades);
		VBox paneRelaciones = new VBox(10,lblRelaciones,listaRelaciones);
		FlowPane paneAdd = new FlowPane(10,10,lblAdd,txtNombreR,cbParticipacion,lblGrado,rbRecursiva,rbBinaria,
		rbMayor,cbCardinalidad,btnAddR,btnDelR,btnEditar,btnOK);
		paneAdd.setPrefWrapLength(100);
		HBox paneCentral = new HBox(10,paneEntidades,paneRelaciones,paneAdd);
		BorderPane panePrincipal = new BorderPane();
		panePrincipal.setPadding(new Insets(10));
		panePrincipal.setCenter(paneCentral);
		Scene scenePrincipal = new Scene(panePrincipal);
		scenePrincipal.getStylesheets().add(stylesheet);
		stage.setScene(scenePrincipal);
	}
	public void deshabilitaBtn(boolean deshabilita){
		if(deshabilita){
			btnDelR.setDisable(true);
			btnEditar.setDisable(true);
		}else{
			btnDelR.setDisable(false);
			btnEditar.setDisable(false);
		}
	}
	public void btnAddRClick(){
		ArrayList<Entidad> aux = Metodos.convierteOL(listaEntidades.getSelectionModel().getSelectedItems());
		String cardinalidad = cbCardinalidad.getSelectionModel().getSelectedItem().replace(":","");
		String participacion = cbParticipacion.getSelectionModel().getSelectedItem().replace(":","");
		String nombre = txtNombreR.getText().trim();
		boolean errorC = false;
		if(Metodos.verificaNombre(nombre,true)){
			Relacion relacion = new Relacion(nombre,cardinalidad,"",aux);
			relacion.setParticipacion(participacion);
			if(rbRecursiva.isSelected()&&aux.size()==1){
				relacion.setTipoRel("recursiva");
				ArrayList<Entidad> aux2=new ArrayList<Entidad>();
				aux2.add(aux.get(0));
				aux2.add(aux2.get(0));
				relacion.setEntidades(aux2);
			}else if(rbBinaria.isSelected()&&aux.size()==2){
				relacion.setTipoRel("binaria");
				relacion.setEntidades(aux);
			}else if(rbMayor.isSelected()&&aux.size()>2){
				relacion.setTipoRel("mayor");
				relacion.setEntidades(aux);
			}else{
				errorC = true;
			}
			if(!errorC){
				if(!relaciones.contains(relacion)&&!entidades.contains(relacion)){
					relaciones.add(relacion);
					listaRelaciones.setItems(relaciones);
				}else{
					MessageBox.show("Esta entidad ya existe","Error");
				}
			}else{
				MessageBox.show("Error en las cantidades de seleccion","Error");
			}
		}else{
			MessageBox.show("Nombre no valido","Error");
		}
		txtNombreR.clear();
		txtCardinalidad.clear();
		txtParticipacion.clear();
	}
	public void btnDelRClick(){
		Relacion relacion = listaRelaciones.getSelectionModel().getSelectedItem();
		relaciones.remove(relacion);
		listaRelaciones.setItems(relaciones);
		if(relaciones.size()==0){
			deshabilitaBtn(true);
		}else{
			deshabilitaBtn(false);
		}
	}
	public void btnEditarClick(){
		listaRelaciones.getSelectionModel().getSelectedItem().editarEntidad(stylesheet);
	}
	public void listaRelacionesClick(Relacion relacion){
		if(relacion!=null){
			deshabilitaBtn(false);
		}else{
			deshabilitaBtn(true);
		}
	}
	public void btnOKClick(){
		if(relaciones.size()>0){
			if(ConfirmationBox.show("Terminar definicion de relaciones","Aceptar","Si","No")){
				Convertidor c1 = new Convertidor(Metodos.convierteOL(entidades),Metodos.convierteOLR(relaciones));
				Convertidor c2 = new Convertidor(Metodos.convierteOL(entidades),Metodos.convierteOLR(relaciones));
				MessageBox.showDoubleArea(c1.convierteAString(),c2.convierteBString(),"Resultados","Opcion 1","Opcion 2",stylesheet);
			}
		}else{
			MessageBox.show("No hay relaciones","Error");
		}
	}
}