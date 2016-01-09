package Utilitaires;

import java.util.ArrayList;

import com.mygdx.game.CellMap;
import com.mygdx.game.GlobalValues;

/**
 * 
 * Exclusif a notre system de donnée CellMAp[]
 * 
 * @author Florian
 *
 */


public final class AStar 
{
	
	private static final int NODE_DISTANCE_VALUE = 100;
	static ArrayList<Noeud> open;
	static ArrayList<Noeud> close;
	static ArrayList<Noeud> chemin;

	
	//h,w = propriété height, width de la carte
	public static ArrayList<Noeud> cheminPlusCourt(CellMap[] map, Noeud depart, Noeud arrivee, int h, int w)
	{
		open 	= new ArrayList<Noeud>();
		close 	= new ArrayList<Noeud>();
		chemin 	= new ArrayList<Noeud>();
		
		Noeud courant = depart;
		System.err.println("depart ="+courant.case_());
		System.err.println("arrivée ="+arrivee.case_());
		
		
		open.add(courant);
		while(!open.isEmpty())
		{
			//recuperation d plus petit noeud dans la lsite ouverte
			courant = meilleur_noeud();
			//System.err.println("meilleur noeud ="+courant.case_);
			//si arrivé on quit 
			if( courant.case_() == arrivee.case_() )
		          break;
			
			//basculer courant dans la liste fermée
			addToCloseList(courant); 
			//recherche des voisins v dans g
			ArrayList<Noeud> voisin = getNeighbours(courant,map,h,w);	
			
			//pour chaque voisin v de courant du graphe faire:
			for(int i=0; i<voisin.size();i++)
			{
				Noeud v = voisin.get(i); //recuperation du voisin  v
				
				//System.err.println(v.case_);
				
				//si noeud nn walkable ou dans fermer -> ne rien faire
				if(isOnCloseList(v) == true ||  walkable(v,map) == false)
				{
					//System.err.println("nn walkable");
					continue;
				}
					
				
				//si v case vide alors  calcul des nouveau couts
				
				//on fait de current le parent de v
				 v.parent = courant;
				 //on calcule le nouveau g = g du parent + distance entre parent et noeud
		         int newG = v.parent.g + NODE_DISTANCE_VALUE; 
		         //on calcule le nouveau h  = distance entre arrivé et courant : distance manhattan
		         int newH = ( Math.abs( map[arrivee.case_()].getN_() -  map[v.case_()].getN_() ) + Math.abs( map[arrivee.case_()].getM_() - map[v.case_()].getM_() ) ) * NODE_DISTANCE_VALUE;
		         //on calcule le nouveau F
		         int newF = newH + newG;
		         
		         //ajouter dans openlist 
		         if(isOnOpenList(v)) //si deja dans liste, on recalcul les couts
		         {
		        	 if( newG < v.g )
		             {
		               v.parent = courant;
		               v.g = newG;
		               v.h = newH;
		               v.f = newF;
		             }
		         }
		         else //sinon on ajoute
		         {
		        	   v.parent = courant;
		               v.g = newG;
		               v.h = newH;
		               v.f = newF;
		        	   addToOpenList(v);
		         }      
			}
		}
		
		//on est sortie ! :fete: -> on peut tracer le chemin
		
		if(open.isEmpty()==true) //aucun chemin trouvé
		{
			System.err.println("Astar complete : pas de chemin");
			return null;
		}
		
		//chemin trouvé, on l'enregistre et le retourne
		reconstituerChemin(depart, courant);
		
		System.err.println("Astar complete: chemin trouvé");
		return chemin;
		
	}
	
	public static void init_CellMap(CellMap[] map)
	{
		if(map == null)
		{
			GlobalValues.getInstance().carte_Init();
		}

		Noeud n = new Noeud();
		for(int i=0; i<map.length;i++)
			map[i].noeud(n);
		
	}
	
	private static void reconstituerChemin(Noeud depart, Noeud arrivee) 
	{
		//l'arrivé est le dernier de  la liste close
		Noeud tmp = arrivee;
		//int cpt=0;
		while(tmp.equals(depart) == false)
		{
			//cpt++;
			//System.err.println(cpt);
			System.out.println(tmp.case_());
			chemin.add(tmp);
		//	System.err.println(tmp.parent);
			tmp=tmp.parent;
			
			if(tmp == null)
				System.err.println("erreur tmp = null");
		}
		
		chemin.add(depart);
		System.out.println(chemin.get(chemin.size()-1).case_());
	}

	private static void addToOpenList(Noeud n) 
	{
		//remove de close list
		removeFromCloseList( n );
		//ajout dans openliste
		open.add(n);
	}


	private static void removeFromCloseList(Noeud n) 
	{
		ArrayList<Noeud> tmp = new 	ArrayList<Noeud>();
		for(int i=0; i < close.size();i++)
		{
			if(n != close.get(i))
				tmp.add(close.get(i));
		}
		
		close = tmp;
		close.add(n);
	}


	private static boolean isOnOpenList(Noeud v)
	{
		int maximum = open.size();
		 
	    for( int i = 0; i < maximum; i++ )
	    {
	      if( open.get(i).equals(v) )
	        return true;
	    }
	    return false;
	}
	
	private static boolean isOnCloseList(Noeud v)
	{
		int maximum = close.size();
	 
	    for( int i = 0; i < maximum; i++ )
	    {
	      if( close.get(i).equals(v) )
	        return true;
	    }
	    return false;
	}
	
	
	//si le tableau de tour ou d'obstacle est rempli, on est pas walkable
	private static boolean walkable(Noeud v, CellMap[] map)
	{
		if(map[v.case_()].getObstacle_size_()<=0 	&&  map[v.case_()].getUnits_size_()<=0)
			return true;
		
		return false;
	}


	//recherche des voisin du noeud courant ( retourne la case correspondantes
	private static ArrayList<Noeud> getNeighbours(Noeud c,CellMap[] map,int h, int w) 
	{
		ArrayList<Noeud> voisin = new ArrayList<Noeud>();

		//calcul indice des noeuds adjacents
		int indice_haut	 = c.case_() - h;
		int indice_bas = c.case_() + h;
		int indice_droite  = c.case_() + 1;
		int indice_gauche= c.case_() - 1;

		//vérification existance des voisins
		try
		{
			if(indice_haut>= 0 && indice_haut<h*w)
				voisin.add(map[indice_haut].noeud());
			if(indice_bas>= 0 && indice_bas<h*w)
				voisin.add(map[indice_bas].noeud());
			if(indice_droite>= 0 && indice_droite<h*w && indice_droite%h !=0)
				voisin.add(map[indice_droite].noeud());
			if(indice_gauche>= 0 && indice_gauche<h*w && indice_gauche%h !=h-1)
				voisin.add(map[indice_gauche].noeud());
		}
		catch(Exception e)
		{
			System.err.println("Astar voisins: "+e);
		}
		
		return voisin;
	}


	//passage du noeud courant de la lsite open a close
	private static void addToCloseList(Noeud courant)
	{
		ArrayList<Noeud> tmp = new 	ArrayList<Noeud>();
		for(int i=0; i < open.size();i++)
		{
			if(courant != open.get(i))
				tmp.add(open.get(i));
		}
		
		open = tmp;
		close.add(courant);
	}


	//choix du meilleur noeud ( plus petit F)
	private static Noeud meilleur_noeud() 
	{
		Noeud current = null;
		int min_f=10000000;
		int max_size = open.size();

		for(int i=0; i<max_size;i++)
		{
			Noeud n = open.get(i);
			if(n.f<min_f)
			{
				min_f=n.f;
				current = n;
			}
		}
		
		return current;
	}
	
	
	public static void main(String [ ] args)
	{
		CellMap carte_[] = null;
					
		
		int h=8;
		int w=10;
		
		Noeud depart;
		Noeud arrivee;
		ArrayList<Noeud> chemin;
		
		depart = new Noeud();
		depart.set_Case(0);
		arrivee= new Noeud();
		arrivee.set_Case(79);
		
		
		//init carte
		carte_ = new CellMap[w * h ];//0->79 = 80
		for(int i =0;i<w;i++)//0->9 = 10
		{	
			for(int j =0;j<h;j++)//0->7 =8
			{
				carte_[i*h+j] = new CellMap(i*h+j,i,j, 32, null, null, null, null); // 32 = taille px h et w on suppose carré
			}
		}
		
		//ajout d'obstacle et d'unité a evité
		carte_[9].add_Obstacle(0);
		carte_[10].add_Obstacle(0);
		carte_[11].add_Obstacle(0);
		carte_[17].add_Obstacle(0);
		carte_[25].add_Obstacle(0);
		carte_[51].add_Obstacle(0);
		carte_[67].add_Obstacle(0);
		carte_[60].add_Obstacle(0);
		carte_[61].add_Obstacle(0);
		carte_[46].add_Obstacle(0);
		carte_[47].add_Obstacle(0);
		carte_[39].add_Obstacle(0);
		carte_[77].add_Obstacle(0);
		
		//dessin de la carte
		for(int i=0;i<carte_.length;i++)
		{
			int value=0;
			if(carte_[i].getObstacle_size_()>0)
				value=1;
			else if(carte_[i].getUnits_size_()>0)
				value=2;
			else if(carte_[i].getNum_case_() == depart.case_() )
				value=3;
			else if(carte_[i].getNum_case_() == arrivee.case_() )
				value=4;
			else
				value=0;
			
			
			if(i%h == h-1)
			{
				System.out.println(value+" ");
			}
			else
			{
				System.out.print(value+" ");
			}
		}
		
		for(int i=0;i<carte_.length;i++)
		{
			
			if(i%h == h-1)
			{
				System.out.println(i+" ");
			}
			else
			{
				System.out.print(i+" ");
			}
		}
		

		//tracer chemin
		chemin = AStar.cheminPlusCourt(carte_, depart, arrivee, h, w);
		
		
	}

}
