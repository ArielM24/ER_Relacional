import javafx.collections.*;
import java.util.ArrayList;
public class Convertidor{
	private ArrayList<Entidad> entidades;
	private ArrayList<Relacion> relaciones;
	public Convertidor(ArrayList<Entidad> entidades,ArrayList<Relacion> relaciones){
		this.entidades=new ArrayList<Entidad>(entidades);
		this.relaciones=new ArrayList<Relacion>(relaciones);
	}
	public void setEntidades(ArrayList<Entidad> entidades){
		this.entidades=new ArrayList<Entidad>(entidades);
	}
	public void setRelaciones(ArrayList<Relacion> relaciones){
		this.relaciones=new ArrayList<Relacion>(relaciones);
	}
	public ArrayList<Entidad> getEntidades(){
		return entidades;
	}
	public ArrayList<Relacion> getRelaciones(){
		return relaciones;
	}
	public ArrayList<RelacionTabla> convierteFuertes(){
		ArrayList<RelacionTabla> entidadesFuertes = new ArrayList<RelacionTabla>();
		for(Entidad aux: entidades){
			if(aux.getTipo().equals("fuerte")){
				entidadesFuertes.add(RelacionTabla.convierteFuerte(aux));
			}
		}
		return entidadesFuertes;
	}
	public ArrayList<RelacionTabla> convierteDebiles(){
		ArrayList<RelacionTabla> entidadesDebiles = new ArrayList<RelacionTabla>();
		for(Relacion aux: relaciones){
			if(aux.debilConvertible()){
				Entidad mayor = aux.mayorPeso();
				for(Entidad aux2 : aux.getEntidades()){
					if(aux2.getTipo().equals("debil")&&
						!entidadesDebiles.contains(new RelacionTabla(aux2.getNombre()))){
						RelacionTabla debil = RelacionTabla.convierteDebil(aux2,mayor.getLlave().getAtributos());
						if(debil.getSuperLlaveMinima().size()==0){
							for(String llavedeb: aux2.getLlave().getAtributos()){
								debil.agregaLlave(llavedeb);
							}
							for(String llavefu: mayor.getLlave().getAtributos()){
								debil.agregaLlave(llavefu+" FK");
							}
						}
						entidadesDebiles.add(debil);
					}
				}
			}
		}
		return entidadesDebiles;
	}
	public ArrayList<RelacionTabla> convierteMultis(){
		ArrayList<RelacionTabla> entidadesMulti = new ArrayList<RelacionTabla>();
		for(Entidad aux: entidades){
			if(aux.multiConvertible()){
				for(String aux2: aux.getMulti()){
					entidadesMulti.add(RelacionTabla.convierteMulti(aux,aux2));
				}
			}
		}
		for(Relacion aux: relaciones){
			if(aux.multiConvertible()){
				for(String aux2: aux.getMulti()){
					entidadesMulti.add(RelacionTabla.convierteMulti(aux,aux2));
				}
			}
		}
		return entidadesMulti;
	}
	public ArrayList<RelacionTabla> convierteEspecialidades(){
		ArrayList<RelacionTabla> especialidades = new ArrayList<RelacionTabla>();
		for(Entidad aux: entidades){
			if(aux.especialidadConvertible()){
				for(EntidadEs aux2: aux.getEspecialidades()){
					especialidades.add(RelacionTabla.convierteEspecialidad(aux2,aux.getLlave().getAtributos()));
				}
			}
		}
		return especialidades;
	}
	public ArrayList<RelacionTabla> convierteRelaciones(){
		ArrayList<RelacionTabla> relacionesTabla = new ArrayList<RelacionTabla>();
		for(Relacion aux: relaciones){
			if(aux.convertibleMayor()){
				relacionesTabla.add(RelacionTabla.convierteRelacion(aux));
			}
		}
		return relacionesTabla;
	}
	public void modificaEspecialidades(){
		Entidad modificada;
		int indice;
		for(Entidad aux: entidades){
			if(aux.getEspecialidades().size()>0){
				for(EntidadEs aux2: aux.getEspecialidades()){
					if(aux2.getTipoE()){
						if(!aux.getSimples().contains("tipo")){
							aux.agregaSimple("tipo");
						}
					}else{
						aux.agregaSimple(aux2.getNombre());
					}
					aux.agregaSimples(aux2.getSimples());
					aux.agregaMultis(aux2.getMulti());
					for(Compuesto comp: aux2.getCompuestos()){
						aux.agregaSimples(comp.getAtributos());
					}
				}
			}
		}
	}
	public void modificaUno(){
		int indice;
		Entidad modificada;
		for(Relacion aux: relaciones){
			if(aux.modificableUno()){
				if(aux.getEntidades().get(0).getTipo().equals("debil")){
					modificada = new Entidad(aux.getEntidades().get(0));
					modificada.agregaSimples(aux.getSimples());
					modificada.agregaCompuestos(aux.getCompuestos());
				}else if(aux.getEntidades().get(1).getTipo().equals("debil")){
					modificada = new Entidad(aux.getEntidades().get(1));
					modificada.agregaSimples(aux.getSimples());
					modificada.agregaCompuestos(aux.getCompuestos());
				}else if(aux.getParticipacion().equals("PT")){
					modificada = new Entidad(aux.getEntidades().get(1));
					modificada.agregaForaneos(aux.getEntidades().get(0).getLlave().getAtributos());
					modificada.agregaSimples(aux.getSimples());
					modificada.agregaCompuestos(aux.getCompuestos());
				}else{
					modificada = new Entidad(aux.getEntidades().get(0));
					modificada.agregaForaneos(aux.getEntidades().get(1).getLlave().getAtributos());
					modificada.agregaSimples(aux.getSimples());
					modificada.agregaCompuestos(aux.getCompuestos());
				}
				if(modificada!=null){
					indice=entidades.indexOf(modificada);
					entidades.set(indice,modificada);
					indice=aux.getEntidades().indexOf(modificada);
					aux.getEntidades().set(indice,modificada);
				}
			}
		}
	}
	public void modificaN(){
		int indice;
		Entidad  modificada;
		for(Relacion aux: relaciones){
			if(aux.modificableN()){
				if(aux.getCardinalidades().equals("1N")){
					modificada = new Entidad(aux.getEntidades().get(1));
					modificada.agregaSimples(aux.getSimples());
					modificada.agregaCompuestos(aux.getCompuestos());
					modificada.agregaForaneos(aux.getEntidades().get(0).getLlave().getAtributos());
					for(String llavefu: aux.getEntidades().get(0).getLlave().getAtributos()){
						if(!modificada.getLlave().getAtributos().contains(llavefu+" FK")){
							modificada.getLlave().getAtributos().add(llavefu+" FK");
						}
					}
				}else{
					modificada = new Entidad(aux.getEntidades().get(0));
					modificada.agregaSimples(aux.getSimples());
					modificada.agregaCompuestos(aux.getCompuestos());
					modificada.agregaForaneos(aux.getEntidades().get(1).getLlave().getAtributos());
					for(String llavefu: aux.getEntidades().get(1).getLlave().getAtributos()){
						if(!modificada.getLlave().getAtributos().contains(llavefu+" FK")){
							modificada.getLlave().getAtributos().add(llavefu+" FK");
						}
					}
				}
				indice = entidades.indexOf(modificada);
				entidades.set(indice,modificada);
				indice = aux.getEntidades().indexOf(modificada);
				aux.getEntidades().set(indice,modificada);
			}
		}
	}
	public ArrayList<RelacionTabla> convierteA(){
		ArrayList<RelacionTabla> relacional = new ArrayList<RelacionTabla>();
		modificaN();
		modificaUno();
		relacional.addAll(convierteFuertes());
		relacional.addAll(convierteDebiles());
		relacional.addAll(convierteRelaciones());
		relacional.addAll(convierteMultis());
		relacional.addAll(convierteEspecialidades());
		return relacional;
	}
	public ArrayList<RelacionTabla> convierteB(){
		ArrayList<RelacionTabla> relacional = new ArrayList<RelacionTabla>();
		modificaN();
		modificaUno();
		modificaEspecialidades();
		relacional.addAll(convierteFuertes());
		relacional.addAll(convierteDebiles());
		relacional.addAll(convierteRelaciones());
		relacional.addAll(convierteMultis());
		return relacional;
	}
	public ArrayList<String> convierteAString(){
		ArrayList<String> cadenas = new ArrayList<String>();
		for(RelacionTabla aux: convierteA()){
			cadenas.add(aux.toString());
		}
		return cadenas;
	}
	public ArrayList<String> convierteBString(){
		ArrayList<String> cadenas = new ArrayList<String>();
		for(RelacionTabla aux: convierteB()){
			cadenas.add(aux.toString());
		}
		return cadenas;
	}
}