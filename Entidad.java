import java.util.ArrayList;
public class Entidad{
	//0 Fuerte, 1 Debil, 2 Especializacion grado 1,3 Especializacion grado 2,4 relacion;
	protected String tipo; 
	protected Compuesto llave;
	protected String nombre;
	protected ArrayList<String> simples;
	protected ArrayList<Compuesto> compuestos;
	protected ArrayList<String> multi;
	protected ArrayList<EntidadEs> especialidades = new ArrayList<EntidadEs>();
	public Entidad(String nombre){
		this.tipo="";
		this.llave=new Compuesto("ID"+nombre);
		this.nombre=nombre;
		this.simples=new ArrayList<String>();
		this.compuestos=new ArrayList<Compuesto>();
		this.multi=new ArrayList<String>();
	}
	public Entidad(String nombre,String tipo){
		this.tipo=tipo;
		this.llave=new Compuesto("ID"+nombre);
		this.nombre=nombre;
		this.simples=new ArrayList<String>();
		this.compuestos=new ArrayList<Compuesto>();
		this.multi=new ArrayList<String>();
	}
	public Entidad(Entidad entidad){
		this.tipo=entidad.tipo;
		this.llave=entidad.llave;
		this.nombre=entidad.nombre;
		this.simples=new ArrayList<String>(entidad.simples);
		this.compuestos=new ArrayList<Compuesto>(entidad.compuestos);
		this.multi=new ArrayList<String>(entidad.multi);
		this.especialidades=new ArrayList<EntidadEs>(entidad.especialidades);
	}
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	public void setSimples(ArrayList<String> simples){
		this.simples=new ArrayList<String>(simples);
	}
	public void setCompuestos(ArrayList<Compuesto> compuestos){
		this.compuestos=new ArrayList<Compuesto>(compuestos);
	}
	public void setMulti(ArrayList<String> multi){
		this.multi=new ArrayList<String>(multi);
	}
	public void setEspecialidades(ArrayList<EntidadEs> especialidades){
		this.especialidades=new ArrayList<EntidadEs>(especialidades);
	}
	public String getTipo(){
		return tipo;
	}
	public String getNombre(){
		return nombre;
	}
	public ArrayList<String> getSimples(){
		return simples;
	}
	public ArrayList<Compuesto> getCompuestos(){
		return compuestos;
	}
	public ArrayList<String> getMulti(){
		return multi;
	}
	public ArrayList<EntidadEs> getEspecialidades(){
		return especialidades;
	}
	public void setLlave(String llave){
		this.llave=new Compuesto("llave");
		this.llave.agregaAtributo(llave);
	}
	public void setLlave(Compuesto llave){
		this.llave=llave;
	}
	public void agregaLlave(Compuesto llave){
		this.llave.getAtributos().addAll(llave.getAtributos());
	}
	public Compuesto getLlave(){
		return llave;
	}
	public String toString(){
		if(tipo.equals("fuerte"))
			return nombre+"   Fuerte";
		else return nombre+"   Debil";
	}
	public boolean agregaSimple(String atributo){
		if(!simples.contains(atributo)){
			simples.add(atributo);
			return true;
		}else return false;
	}
	public boolean agregaMulti(String atributo){
		if(!multi.contains(atributo)){
			multi.add(atributo);
			return true;
		}else return false;
	}
	public boolean agregaCompuesto(Compuesto compuesto){
		if(!compuestos.contains(compuesto)){
			compuestos.add(compuesto);
			return true;
		}else return false;
	}
	public boolean equals(Object obj){
		if(obj!=null&&obj instanceof Entidad){
			Entidad aux = (Entidad)obj;
			String nom1 = Metodos.capturaNombre(aux.getNombre());
			String nom2 = Metodos.capturaNombre(this.getNombre());
			return nom1.equals(nom2);
		}else return false;
	}
	public boolean agregaEspecialidad(EntidadEs entidad){
		if(!especialidades.contains(entidad)){
			especialidades.add(entidad);
			return true;
		}else return false;
	}
	public void editarEntidad(String stylesheet){
		if(tipo.equals("fuerte")||tipo.equals("debil")){
			EditarEntidad.show(this,true,stylesheet);
		}else {
			EditarEntidad.show(this,false,stylesheet);
		}
	}
	public boolean multiConvertible(){
		if(multi.size()>0){
			return true;
		}else return false;
	}
	public boolean especialidadConvertible(){
		if(especialidades.size()>0){
			return true;
		}else return false;
	}
	public boolean modificableEspecialidad(){
		return especialidadConvertible();
	}
	public void agregaSimples(ArrayList<String> valor){
		simples.addAll(valor);
	}
	public void agregaCompuestos(ArrayList<Compuesto> valor){
		compuestos.addAll(valor);
	}
	public void agregaMultis(ArrayList<String> valor){
		multi.addAll(valor);
	}
	public void agregaForaneos(ArrayList<String> foraneos){
		for(String aux: foraneos){
			simples.add(aux+" FK");
		}
	}
}