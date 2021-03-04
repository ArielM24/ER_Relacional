import java.util.ArrayList;
import javafx.collections.*;
public class Relacion extends Entidad{
	protected ArrayList<Entidad> entidades;
	protected String cardinalidades;
	protected String particiacion;//1NM
	protected String tipoRel;//0 recursiva,1 binaria, 2 mayor
	public Relacion(String nombre){
		super(nombre);
	}
	public Relacion(String nombre,String cardinalidades,String tipoRel,ArrayList<Entidad> entidades){
		super(nombre,"relacion");
		this.entidades=new ArrayList<Entidad>(entidades);
		this.tipoRel=tipoRel;
		this.cardinalidades=cardinalidades;
	}
	public Relacion(String nombre,String cardinalidades,String tipoRel,ObservableList<Entidad> entidades){
		super(nombre,"relacion");
		this.entidades=Metodos.convierteOL(entidades);
		this.tipoRel=tipoRel;
		this.cardinalidades=cardinalidades;
	}
	public void setParticipacion(String particiacion){
		this.particiacion=particiacion;
	}
	public void setTipoRel(String tipoRel){
		this.tipoRel=tipoRel;
	}
	@Override
	public boolean equals(Object obj){
		if(obj!=null&&obj instanceof Relacion){
			Relacion aux=(Relacion)obj;
			return nombre.equals(aux.nombre);
		}else return false;
	}
	@Override 
	public String toString(){
		String nomb = nombre+":";
		for(Entidad aux:entidades){
			nomb+=aux.getNombre()+" ";
		}
		nomb+=" "+particiacion+"  "+cardinalidades;
		return nomb;
	}
	public boolean agregaEntidad(Entidad entidad){
		return entidades.add(entidad);
	}
	public void setEntidades(ArrayList<Entidad> entidades){
		this.entidades=new ArrayList<Entidad>(entidades);
	}
	public void setEntidades(ObservableList<Entidad> entidades){
		this.entidades=Metodos.convierteOL(entidades);
	}
	public ArrayList<Entidad> getEntidades(){
		ArrayList<Entidad> aux = new ArrayList<Entidad>();
		for(Entidad aux2: entidades){
			aux.add(aux2);
		}
		return aux;
	}
	public String getCardinalidades(){
		return cardinalidades;
	}
	public boolean debilConvertible(){
		boolean debil = false;
		boolean fuerte = false;
		if(tipoRel.equals("binaria")||tipoRel.equals("mayor")){
			for(Entidad aux: entidades){
				if(aux.getTipo().equals("debil")){
					debil=true;
				}else if(aux.getTipo().equals("fuerte")){
					fuerte=true;
				}
			}
		}
		return (debil&&fuerte);
	}
	public boolean modificableUno(){
		if(tipoRel.equals("recursiva")||tipoRel.equals("binaria")){
			if(cardinalidades.equals("11")){
				return true;
			}else return false;
		}else return false;
	}
	public boolean modificableN(){
		if(tipoRel.equals("recursiva")||tipoRel.equals("binaria")){
			if(cardinalidades.equals("1N")||cardinalidades.equals("N1")){
				return true;
			}else return false;
		}else return false;
	}
	public boolean convertibleMayor(){
		if(tipoRel.equals("binaria")){
			if(cardinalidades.equals("MN")){
				return true;
			}else return false;
		}else if(tipoRel.equals("mayor")){
			return true;
		}else return false;
	}
	public String getParticipacion(){
		return particiacion;
	}
	public Entidad mayorPeso(){
		Entidad mayor = entidades.get(0);
		char part = particiacion.charAt(0);
		int i=entidades.size(),j;
		for(j=0;j<i;j++){
			if((Metodos.valorTipo(entidades.get(j))>Metodos.valorTipo(mayor))&&
				(Metodos.valorPart(particiacion.charAt(j))>Metodos.valorPart(part))){
				mayor = new Entidad(entidades.get(j));
			}
		}
		return mayor;
	}
}