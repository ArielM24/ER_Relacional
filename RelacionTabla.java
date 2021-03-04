import java.util.ArrayList;
public class RelacionTabla{
	protected String nombre;
	protected ArrayList<String> atributosSimples,atributosForaneos,SuperLlaveMinima;
	public RelacionTabla(){
		nombre = "";
		SuperLlaveMinima = new ArrayList<String>();
		atributosSimples = new ArrayList<String>();
		atributosForaneos = new ArrayList<String>();
	}
	public RelacionTabla(String nombre){
		this.nombre=nombre;
		SuperLlaveMinima = new ArrayList<String>();
		atributosSimples = new ArrayList<String>();
		atributosForaneos = new ArrayList<String>();
	}
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	public void setSuperLlaveMinima(ArrayList<String> SuperLlaveMinima){
		this.SuperLlaveMinima=SuperLlaveMinima;
	}
	public void setAtributosForaneos(ArrayList<String> atributosForaneos){
		this.atributosForaneos=atributosForaneos;
	}
	public void setAtributosSimples(ArrayList<String> atributosSimples){
		this.atributosSimples=atributosSimples;
	}
	public String getNombre(){
		return nombre;
	}
	public ArrayList<String> getSuperLlaveMinima(){
		return SuperLlaveMinima;
	}
	public ArrayList<String> getAtributosSimples(){
		return atributosSimples;
	}
	public ArrayList<String> getAtributosForaneos(){
		return atributosForaneos;
	}
	public String toString(){
		String aux=nombre+" = ";
		for(String aux2: SuperLlaveMinima){
			aux+=" | "+aux2+"PK | ";
		}
		for(String aux2: atributosSimples){
			aux+=" | "+aux2+" | ";
		}
		for(String aux2: atributosForaneos){
			aux+=" | "+aux2+"FK | ";
		}
		return aux;
	}
	public boolean equals(Object obj){
		if(obj!=null&& obj instanceof RelacionTabla){
			RelacionTabla aux = (RelacionTabla)obj;
			return nombre.equals(aux.nombre);
		}else return false;
	}
	public boolean agregaLlave(String valor){
		if(!SuperLlaveMinima.contains(valor)){
			return SuperLlaveMinima.add(valor);
		}else return false;
	}
	public boolean agregaSimple(String valor){
		if(!atributosSimples.contains(valor)){
			return atributosSimples.add(valor);
		}else return false;
	}
	public void agregaSimples(ArrayList<String> simples){
		atributosSimples.addAll(simples);
	}
	public void agregaForaneos(ArrayList<String> foraneos){
		atributosForaneos.addAll(foraneos);
	}
	public void agregaCompuestos(ArrayList<Compuesto> compuestos){
		for(Compuesto aux: compuestos){
			agregaSimples(aux.getAtributos());
		}
	}
	public boolean agregaForeneo(String valor){
		if(!atributosForaneos.contains(valor)){
			return atributosForaneos.add(valor);
		}else return false;
	}
	public static RelacionTabla convierteFuerte(Entidad fuerte){
		RelacionTabla aux = new RelacionTabla(fuerte.getNombre());
		ArrayList<String> llave = new ArrayList<String>(fuerte.getLlave().getAtributos());
		ArrayList<String> simples = new ArrayList<String>(fuerte.getSimples());
		ArrayList<String> compuestos = new ArrayList<String>();
		for(Compuesto aux2: fuerte.getCompuestos()){
			compuestos.addAll(aux2.getAtributos());
		}
		aux.setSuperLlaveMinima(llave);
		aux.setAtributosSimples(simples);
		aux.agregaSimples(compuestos);
		return aux;
	}
	public static RelacionTabla convierteDebil(Entidad debil,ArrayList<String> llaveFuerte){
		RelacionTabla aux = new RelacionTabla(debil.getNombre());
		ArrayList<String> llave = new ArrayList<String>(debil.getLlave().getAtributos());
		ArrayList<String> llave2 = new ArrayList<String>();
		ArrayList<String> simples = new ArrayList<String>(debil.getSimples());
		ArrayList<String> compuestos = new ArrayList<String>();
		for(Compuesto aux2: debil.getCompuestos()){
			compuestos.addAll(aux2.getAtributos());
		}
		for(String aux2: llaveFuerte){
			llave.add(aux2+" FK");
		}	
		aux.setSuperLlaveMinima(llave2);
		aux.setAtributosSimples(simples);
		aux.agregaSimples(compuestos);
		return aux;
	}
	public static RelacionTabla convierteMulti(Entidad entidad,String multi){
		RelacionTabla aux = new RelacionTabla(multi+"_"+entidad.getNombre());
		aux.agregaLlave(multi);
		ArrayList<String> llave = new ArrayList<String>(entidad.getLlave().getAtributos());
		for(String aux2: llave){
			aux.agregaLlave(aux2+" FK");
		}
		return aux;
	}
	public static RelacionTabla convierteRelacion(Relacion relacion){
		RelacionTabla aux = new RelacionTabla(relacion.getNombre());
		ArrayList<String> llave = new ArrayList<String>();
		ArrayList<String> simples = new ArrayList<String>(relacion.getSimples());
		ArrayList<String> compuestos = new ArrayList<String>();
		for(Entidad aux2: relacion.getEntidades()){
			for(String aux3: aux2.getLlave().getAtributos()){
				if(llave.contains(aux3)){
					llave.add(aux3+"_"+aux2.getNombre()+" FK");
				}else{
					llave.add(aux3+" FK");
				}
			}
		}
		for(Compuesto aux2: relacion.getCompuestos()){
			compuestos.addAll(aux2.getAtributos());
		}
		aux.setSuperLlaveMinima(llave);
		aux.setAtributosSimples(simples);
		aux.agregaSimples(compuestos);
		return aux;
	}
	public static RelacionTabla convierteEspecialidad(EntidadEs especialidad,ArrayList<String> llaveP){
		RelacionTabla aux = new RelacionTabla(especialidad.getNombre());
		ArrayList<String> llave = new ArrayList<String>();
		ArrayList<String> simples = new ArrayList<String>(especialidad.getSimples());
		ArrayList<String> compuestos = new ArrayList<String>();
		for(String aux2: llaveP){
			llave.add(aux2+" FK");
		}
		for(Compuesto aux2: especialidad.getCompuestos()){
			compuestos.addAll(aux2.getAtributos());
		}
		aux.setSuperLlaveMinima(llave);
		aux.setAtributosSimples(simples);
		aux.agregaSimples(compuestos);
		return aux;
	}
}