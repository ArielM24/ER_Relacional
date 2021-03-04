import javafx.collections.*;
import java.util.ArrayList;
import javafx.scene.control.*;																																				
public class Metodos{
	public static boolean verificaNombre(String nombre,boolean simple){
		char comillaDoble=(char)34;
		char comillaSimple=(char)39;
		String nombre2 = nombre.replace(".","").replace(";","").replace(
			"(","").replace(")","").replace("{","").replace("}","").replace("<","").replace(">","").replace(
			"+","").replace("-","").replace("/","").replace("*","").replace("=","").replace("&","").replace(
			"|","").replace("#","").replace("^","").replace("%","").replace(""+comillaSimple,"").replace(
			""+comillaDoble,"");
		if(simple){
			nombre2 = nombre2.replace(":","").replace(",","");
		}
		if(nombre2.equals(nombre)&&nombre.length()>0){
			return true;
		}else return false;
	}
	public static String capturaNombre(String nombre){
		return nombre.toLowerCase().replace(" ","").replace(".","").replace(",","");
	}
	public static Compuesto capturaCompuesto(String valor){
		String aux="";
		int i,j;
		Compuesto aux2;
		for(i=0;i<valor.length();i++){
			if(valor.charAt(i)!=':'){
				aux+=valor.charAt(i);
			}else break;
		}
		aux2 = new Compuesto(aux);
		aux="";
		for(j=i+1;j<valor.length();j++){
			if(valor.charAt(j)!=','){
				aux+=valor.charAt(j);
			}else{
				aux2.agregaAtributo(aux);
				aux="";
			}
			if(j==valor.length()-1){
				aux2.agregaAtributo(aux);
			}
		}
		return aux2;
	}
	public static ArrayList<String> hojasEsp(Entidad entidad,boolean primer){
		ArrayList<String> hojas = new ArrayList<String>();
		for(EntidadEs aux: entidad.getEspecialidades()){
			hojas.addAll(aux.getSimples());
			for(Compuesto aux3: aux.getCompuestos()){
					hojas.addAll(aux3.getAtributos());
			}
			if(primer){
				for(EntidadEs aux2: aux.getEspecialidades()){
					hojas.addAll(aux2.getSimples());
					for(Compuesto aux3: aux2.getCompuestos()){
						hojas.addAll(aux3.getAtributos());
					}
				}
			}	
		}
		return hojas;
	}
	public static ArrayList<Entidad> convierteOL(ObservableList<Entidad> entidades){
		ArrayList<Entidad> aux = new ArrayList<Entidad>();
		for(Entidad aux2: entidades){
			aux.add(aux2);
		}
		return aux;
	}
	public static ArrayList<Relacion> convierteOLR(ObservableList<Relacion> relaciones){
		ArrayList<Relacion> aux = new ArrayList<Relacion>();
		for(Relacion aux2: relaciones){
			aux.add(aux2);
		}
		return aux;
	}
	public static boolean existeDebil(Relacion relacion){
		boolean debil = false;
		for(Entidad aux: relacion.getEntidades()){
			if(aux.getTipo().equals("debil")){
				debil = true;
				break;
			}
		}
		return debil;
	}
	public static ArrayList<Relacion> debilesConv(ArrayList<Relacion> relaciones){
		ArrayList<Relacion> aux = new ArrayList<Relacion>();
		for(Relacion aux2: relaciones){
			if(aux2.debilConvertible()){
				aux.add(aux2);
			}
		}
		return aux;
	}
	public static boolean mayorEntidad(Entidad uno,Entidad dos){
		boolean mayor=false;
	
		return mayor;
	}
	public static int valorTipo(Entidad entidad){
		if(entidad.getTipo().equals("fuerte")){
			return 3;
		}else if(entidad.getTipo().equals("debil")){
			return 0;
		}else if(entidad.getTipo().equals("primeraesp")){
			return 2;
		}else if(entidad.getTipo().equals("segundaesp")){
			return 1;
		}else return -1;
	}
	public static int valorPart(char part){
		if(part=='T'){
			return 1;
		}else return 0;
	}
}