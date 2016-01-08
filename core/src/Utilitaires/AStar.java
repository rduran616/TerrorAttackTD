package Utilitaires;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import com.mygdx.game.CellMap;

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
		
		open.add(courant);
		while(!open.isEmpty())
		{
			//recuperation d plus petit noeud dans la lsite ouverte
			courant = meilleur_noeud();
			//si arrivé on quit 
			if( courant == arrivee )
		          break;
			//basculer courant dans la liste fermée
			addToCloseList(courant); 
			//recherche des voisins v dans g
			ArrayList<Noeud> voisin = getNeighbours(courant,map,h,w);		
			//pour chaque voisin v de courant du graphe faire:
			for(int i=0; i<voisin.size();i++)
			{
				Noeud v = voisin.get(i); //recuperation du voisin  v
				//si noeud nn walkable ou dans fermer -> ne rien faire
				if(isOnCloseList(v) ||  walkable(v,map))
					continue;
				
				//si v case vide alors  calcul des nouveau couts
			
				 //on calcule le nouveau g = g du parent + distance entre parent et noeud
		         int newG = v.parent.g + NODE_DISTANCE_VALUE; 
		         //on calcule le nouveau h  = distance entre arrivé et courant : distance manhattan
		         int newH = ( Math.abs( map[arrivee.case_].getN_() -  map[v.case_].getN_() ) + Math.abs( map[arrivee.case_].getM_() - map[v.case_].getM_() ) ) * NODE_DISTANCE_VALUE;
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
			return null;
		
		//chemin trouvé, on l'enregistre et le retourne
		reconstituerChemin(depart, arrivee);
		
		
		return chemin;
		
	}
	
	public static void init_CellMap(CellMap[] map)
	{
		Noeud n = new Noeud();
		for(int i=0; i<map.length;i++)
			map[i].noeud(n);
	}
	
	private static void reconstituerChemin(Noeud depart, Noeud arrivee) 
	{
		//l'arrivé est le dernier de  la liste close
		Noeud tmp = arrivee;
		while(tmp!=depart)
		{
			chemin.add(tmp);
			tmp= tmp.parent;
		}
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
	      if( open.get(i) == v )
	        return true;
	    }
	    return false;
	}
	
	private static boolean isOnCloseList(Noeud v)
	{
		int maximum = close.size();
	 
	    for( int i = 0; i < maximum; i++ )
	    {
	      if( close.get(i) == v )
	        return true;
	    }
	    return false;
	}
	
	
	//si le tableau de tour ou d'obstacle est rempli, on est pas walkable
	private static boolean walkable(Noeud v, CellMap[] map)
	{
		if(map[v.case_].getObstacle_size_()<=0 	&& map[v.case_].getUnits_size_()<=0)
			return true;
		
		return false;
	}


	//recherche des voisin du noeud courant ( retourne la case correspondantes
	private static ArrayList<Noeud> getNeighbours(Noeud c,CellMap[] map,int h, int w) 
	{
		ArrayList<Noeud> voisin = new ArrayList<Noeud>();

		//calcul indice des noeuds adjacents
		int indice_haut	 = c.case_ - h;
		int indice_bas = c.case_ + h;
		int indice_droite  = c.case_ + 1;
		int indice_gauche= c.case_ - 1;

		//vérification existance des voisins
		if(indice_haut>= 0 && indice_haut<h*w)
			voisin.add(map[indice_haut].noeud());
		if(indice_bas>= 0 && indice_bas<h*w)
			voisin.add(map[indice_bas].noeud());
		if(indice_droite>= 0 && indice_droite<h*w)
			voisin.add(map[indice_droite].noeud());
		if(indice_gauche>= 0 && indice_gauche<h*w)
			voisin.add(map[indice_gauche].noeud());
		
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

}
