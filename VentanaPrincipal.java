import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.collections.*;
import java.util.ArrayList;
public class VentanaPrincipal extends Application{
	private Stage stage;
	private TextField txtNombreE;
	private ListView<Entidad> listaEntidades;
	private RadioButton rbFuerte,rbDebil,rbEspecialidad,rbTraslape,rbDisyuncion,rbTotal,rbParcial;
	private Button btnAdd,btnDel,btnEditar,btnOK;
	private Label lblNombreEn,lblTipoEn,lblTipoEs,lblParticipacion;
	private ObservableList<Entidad> entidades;
	private String stylesheet;
	@Override
	public void start(Stage primaryStage){
		stage=primaryStage;
		iniciaComp();
		stage.setResizable(false);
		stage.setTitle("Entidades");
		stage.show();
	}
	private void iniciaComp(){
		txtNombreE = new TextField();
		txtNombreE.setPromptText("Nombre de la Entidad");
		btnAdd = new Button("Agregar entidad");
		btnAdd.setOnAction(e->btnAddClick());
		btnDel = new Button("Borrar entidad");
		btnDel.setOnAction(e->btnDelClick());
		btnDel.setDisable(true);
		btnEditar = new Button("Editar");
		btnEditar.setOnAction(e->btnEditarClick());
		btnEditar.setDisable(true);
		btnOK = new Button("OK");
		btnOK.setOnAction(e->btnOKClick());
		lblNombreEn = new Label("Nombre de la entidad");
		lblTipoEn = new Label("Tipo de entidad");
		lblTipoEs = new Label("Tipo de especialidad");
		lblParticipacion = new Label("Participacion");
		listaEntidades = new ListView<Entidad>();
		listaEntidades.setTooltip(new Tooltip("Selecciona una entidad"));
		listaEntidades.setMinWidth(270);
		listaEntidades.setMinHeight(250);
		listaEntidades.getSelectionModel().selectedItemProperty().addListener(
			(valor,viejoValor,nuevoValor)->listaEntidadesClick(nuevoValor)
		);
		entidades = listaEntidades.getItems();
		VBox paneNombre = new VBox(10,lblNombreEn,txtNombreE,btnAdd);
		paneNombre.setPadding(new Insets(10));
		paneNombre.setMargin(btnAdd,new Insets(20,0,0,0));
		HBox paneCentral = new HBox(10,listaEntidades,btnDel,btnEditar,btnOK);
		paneCentral.setPadding(new Insets(10));
		rbFuerte = new RadioButton("Fuerte");
		rbDebil = new RadioButton("Debil");
		rbEspecialidad = new RadioButton("Especialidad");
		rbEspecialidad.setTooltip(new Tooltip("Para crear una especialidad seleccione\nuna entidad padre valida"));
		rbEspecialidad.setDisable(true);
		ToggleGroup grupoTipo = new ToggleGroup();
		rbFuerte.setToggleGroup(grupoTipo);
		rbDebil.setToggleGroup(grupoTipo);
		rbEspecialidad.setToggleGroup(grupoTipo);
		rbFuerte.setSelected(true);
		rbEspecialidad.setSelected(false);
		rbFuerte.setOnAction(e->rbEspecialidadClick());
		rbDebil.setOnAction(e->rbEspecialidadClick());
		rbEspecialidad.setOnAction(e->rbEspecialidadClick());
		VBox paneTipoEn = new VBox(10,lblTipoEn,rbFuerte,rbDebil,rbEspecialidad);
		paneTipoEn.setPadding(new Insets(10));
		rbTraslape = new RadioButton("Traslape");
		rbDisyuncion = new RadioButton("Disyuncion");
		rbTotal = new RadioButton("Total");
		rbParcial = new RadioButton("Parcial");
		rbTraslape.setSelected(true);
		rbParcial.setSelected(true);
		ToggleGroup grupoTipoEs = new ToggleGroup();
		ToggleGroup gurpoParticipacion = new ToggleGroup();
		rbTotal.setToggleGroup(gurpoParticipacion);
		rbParcial.setToggleGroup(gurpoParticipacion);
		rbTraslape.setToggleGroup(grupoTipoEs);
		rbDisyuncion.setToggleGroup(grupoTipoEs);
		lblTipoEs.setDisable(true);
		rbTraslape.setDisable(true);
		rbDisyuncion.setDisable(true);
		lblParticipacion.setDisable(true);
		rbTotal.setDisable(true);
		rbParcial.setDisable(true);
		VBox paneTipoEs = new VBox(10,lblTipoEs,rbTraslape,rbDisyuncion);
		paneTipoEs.setPadding(new Insets(10));
		VBox paneParticipacion = new VBox(10,lblParticipacion,rbTotal,rbParcial);
		paneParticipacion.setPadding(new Insets(10));
		HBox paneTop = new HBox(10,paneNombre,paneTipoEn,paneTipoEs,paneParticipacion);
		BorderPane panePrincipal = new BorderPane();
		panePrincipal.setTop(paneTop);
		panePrincipal.setCenter(paneCentral);
		Scene scenePrincipal = new Scene(panePrincipal,520,400);
		scenePrincipal.getStylesheets().add(getClass().getResource("estilo.css").toExternalForm());
		stylesheet=scenePrincipal.getStylesheets().get(0);
		stage.setScene(scenePrincipal);
	}
	private void deshabilitaOpEsp(boolean deshabilitar){
		lblTipoEs.setDisable(deshabilitar);
		rbTraslape.setDisable(deshabilitar);
		rbDisyuncion.setDisable(deshabilitar);
		lblParticipacion.setDisable(deshabilitar);
		rbTotal.setDisable(deshabilitar);
		rbParcial.setDisable(deshabilitar);
	}
	private void deshabilitaEsp(boolean deshabilitar){
		rbEspecialidad.setDisable(deshabilitar);
		deshabilitaOpEsp(deshabilitar);
		if(deshabilitar){
			rbFuerte.setSelected(true);
		}
	}
	private void listaEntidadesClick(Entidad entidad){
		if(entidad!=null){
			btnDel.setDisable(false);
			btnEditar.setDisable(false);
			if(entidad.getTipo().equals("primeraesp")){
				deshabilitaEsp(true);
			}else{
				deshabilitaEsp(false);
			}
		}else{
			btnDel.setDisable(true);
			btnEditar.setDisable(true);
		}
	}
	public void rbEspecialidadClick(){
		if(rbEspecialidad.isSelected()){
			deshabilitaOpEsp(false);
		}else{
			deshabilitaOpEsp(true);
		}
	}
	public void capturaEspecialidad(){
		Entidad aux = listaEntidades.getSelectionModel().getSelectedItem();
		if(aux!=null){
			if(!aux.getTipo().equals("segundaesp")){
				String nombre = txtNombreE.getText();
				boolean tipo,participacion;
				if(rbTraslape.isSelected()){
					tipo=false;
				}else tipo=true;
				if(rbTotal.isSelected()){
					participacion=false;
				}else participacion=true;
				if(Metodos.verificaNombre(nombre,true)){
					EntidadEs aux2 = new EntidadEs(nombre,tipo,participacion);
					if(!entidades.contains(aux2)){
						if(!aux.getTipo().equals("primeraesp")){
							aux2.setTipo("primeraesp");
						}else{
							aux2.setTipo("segundaesp"); 
						}
						aux2.setNombreP(aux.getNombre());
						aux.agregaEspecialidad(aux2);
						listaEntidades.getItems().add(aux2);
					}else{
						MessageBox.show("Esta entidad ya existe","Error");	
					}
				}else{
					MessageBox.show("Nombre no valido","Error");
				}
			}
		}
		txtNombreE.clear();
	}
	private void capturaEntidad(String tipo){
		String nombre = txtNombreE.getText().trim();
		if(Metodos.verificaNombre(nombre,true)){
			Entidad entidad = new Entidad(nombre,tipo);
			if(!entidades.contains(entidad)){
				listaEntidades.getItems().add(entidad);
			}else{
				MessageBox.show("Esta entidad ya existe","Error");
			}
		}else{
			MessageBox.show("Nombre no valido","Error");
		}
		txtNombreE.clear();
	}
	private void btnAddClick(){
		if(rbFuerte.isSelected()){
			capturaEntidad("fuerte");
		}else if(rbDebil.isSelected()){
			capturaEntidad("debil");
		}else if(rbEspecialidad.isSelected()){
			capturaEspecialidad();
		}
	}
	private void btnDelClick(){
		Entidad aux = listaEntidades.getSelectionModel().getSelectedItem();
		if(aux!=null){
			listaEntidades.getItems().remove(aux);
			Entidad aux2 = listaEntidades.getSelectionModel().getSelectedItem();
			if(aux2==null){
				deshabilitaEsp(true);
			}else if(aux2.getTipo().equals("primeraesp")){
				deshabilitaEsp(true);
			}
		}
	}
	private void btnEditarClick(){
		listaEntidades.getSelectionModel().getSelectedItem().editarEntidad(stylesheet);
	}
	private void btnOKClick(){
		if(entidades.size()>0){
			if(ConfirmationBox.show("Terminar definicion de entidades","Aceptar","Si","No")){
				stage.close();
				VentanaSecundaria v2 = new VentanaSecundaria(entidades,stylesheet);
				v2.start(new Stage());
			}
		}else{
			MessageBox.show("No hay entidades","Error");
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
