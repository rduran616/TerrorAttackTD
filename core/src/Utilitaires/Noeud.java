package Utilitaires;


public final class Noeud 
{
	int g=0; //distance depart a courant
	int h=0; //distance noeud courant ->noeud arrivé (1 case)
	int f=0; //g+h = poid total
	
	Noeud parent=null; //parent
	int case_=0; //case 
	boolean walkable=false;
	
	public Noeud()
	{
		g=0; 
		h=0; 
		f=0; 
		
		parent=null; 
		case_=0;
		walkable=false;
	} 
}