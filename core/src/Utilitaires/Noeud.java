package Utilitaires;


public final class Noeud 
{
	int g=0; //distance depart a courant
	int h=0; //distance noeud courant ->noeud arrivé (1 case)
	int f=0; //g+h = poid total
	
	Noeud parent=null; //parent
	private int case_=0; //case 
	boolean walkable=false;
	
	public Noeud()
	{
		g=0; 
		h=0; 
		f=0; 
		
		parent=null; 
		case_(0);
		walkable=false;
	} 
	
	public void set_Case(int c){case_(c);}

	public int case_() {
		return case_;
	}

	public void case_(int case_) {
		this.case_ = case_;
	}
}