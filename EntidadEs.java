import java.util.ArrayList;
public class EntidadEs extends Entidad{
	protected boolean tipoE;//traslape false
	protected boolean participacion;//Total false
	protected String nombrePadre;
	protected ArrayList<EntidadEs> especialidades2 = new ArrayList<EntidadEs>();
	public EntidadEs(String nombre){
		super(nombre,"esp");
		this.tipoE=false;
		this.participacion=false;
	}
	public EntidadEs(String nombre,boolean tipoE){
		super(nombre,"esp");
		this.tipoE=tipoE;
	}
	public EntidadEs(String nombre,boolean tipoE,boolean participacion){
		super(nombre,"esp");
		this.tipoE=tipoE;
		this.participacion=participacion;
	}
	public EntidadEs(String nombre,String tipo,boolean tipoE,boolean participacion){
		super(nombre,tipo);
		this.tipoE=tipoE;
		this.participacion=participacion;
	}
	public EntidadEs(EntidadEs entidad){
		super(entidad);
		this.tipoE=entidad.tipoE;
		this.participacion=entidad.participacion;
		this.nombrePadre=entidad.nombrePadre;
		this.especialidades2=new ArrayList<EntidadEs>(entidad.especialidades2);
	}
	public void setNombreP(String nombrePadre){
		this.nombrePadre=nombrePadre;
	}
	public String getNombreP(){
		return nombrePadre;
	}
	@Override
	public String toString(){
		return nombre+"  ->"+getNombreP();
	}
	public void setTipoE(boolean tipoE){
		this.tipoE=tipoE;
	}
	public void setParticipacion(boolean participacion){
		this.participacion=participacion;
	}
	public boolean getTipoE(){
		return tipoE;
	}
	public boolean getParticipacion(){
		return participacion;
	}
	public void setEspecialidades2(ArrayList<EntidadEs> especialidades2){
		this.especialidades2=new ArrayList<EntidadEs> (especialidades2);
	}
	public ArrayList<EntidadEs> getEspecialidades2(){
		return especialidades2;
	}
	public boolean agregaEspecialidad2(EntidadEs entidad){
		if(!especialidades2.contains(entidad)){
			especialidades2.add(entidad);
			return true;
		}else return false;
	}
}