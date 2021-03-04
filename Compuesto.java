import java.util.ArrayList;
public class Compuesto{
	private ArrayList<String> atributos;
	private String nombre;
	public Compuesto(String nombre){
		this.atributos=new ArrayList<String>();
		this.nombre=nombre;
	}
	public Compuesto(String nombre,ArrayList<String> atributos){
		this.atributos=atributos;
		this.nombre=nombre;
	}
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	public void setAtributos(ArrayList<String> atributos){
		this.atributos=atributos;
	}
	public String getNombre(){
		return nombre;
	}
	public ArrayList<String> getAtributos(){
		return atributos;
	}
	public boolean agregaAtributo(String atributo){
		boolean agregado=false;
		if(!atributos.contains(atributo)){
			agregado=atributos.add(atributo);
		}return agregado;
	}
	public boolean equals(Object objeto){
		if(objeto!=null&&objeto instanceof Compuesto){
			Compuesto aux = (Compuesto)objeto;
			return nombre.equals(aux.getNombre());
		}else return false;
	}
	public String toString(){
		String aux=nombre+":";
		int i;
		for(i=0;i<atributos.size();i++){
			aux+=atributos.get(i);
			if(i<atributos.size()-1){
				aux+=",";
			}
		}
		return aux;
	}
}