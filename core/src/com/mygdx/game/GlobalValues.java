package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import units.Mobs;
import units.Tower;

/*
 * Classe singleton contenant toutes les variables global et accessible par tous et partout
 * Possibilité d'ajouter des fonctions
 *
 *
 * Attention au chemin
 */


//final car pas d'héritier possible
public final class GlobalValues 
{
	/**** Singleton creation et instanciation*****/
	
	//volatile : evite probleme en cas de non instanciation 
	private static volatile GlobalValues instance = null;

	//constructeur privée, remplace le public, appeler par la methode getInstance() ( c'est elle qui vas initier la cosntruction ou la recupération)
	private GlobalValues() 
	{
		super();
		height_ = Gdx.graphics.getHeight();
		width_ 	= Gdx.graphics.getWidth();
		
		
		//creation d'un skin ( fond des boutons )
		skin_bouton_ = new Skin( Gdx.files.internal( "uiskin.json" )); //valeur par defaut
		
		//creation carte de base et des conteneurs d'objets
		if(size_n_ > 0 && size_m_ > 0 )
		{
			carte_ = new TypeObjet[size_n_ * size_m_]; //nombre de case total de la carte
			liste_tours = new Tower[size_n_ * size_m_ - 2]; //nombre de tour possibles - 2 car point de dépar et arrivée 
		}
		
		liste_mobs = new Mobs[ennemi_max_]; //nombre d'ennemi max en même temps sur la carte

	}

	
	public final static GlobalValues getInstance() 
	{
	    if (GlobalValues.instance == null) 
	    {
	       // Le mot-clé synchronized sur ce bloc empêche toute instanciation multiple même par différents "threads". 
	       synchronized(GlobalValues.class) 
	       {
		       if (GlobalValues.instance == null) 
		       {
	        	  GlobalValues.instance = new GlobalValues();
		       }
	       }
	    }
	    return GlobalValues.instance;
	}
	
	/**** Attributs *****/
	
	//général
	int height_	= 0; 
	int width_	= 0;
	int size_px_ = 32;
	
	//decors
	Skin skin_bouton_;
	
	//carte
	int ennemi_max_=1000;
	int size_n_ = 20;
	int size_m_ = 30;
	TypeObjet carte_[];
	Tower liste_tours[];
	Mobs liste_mobs[];
	String carte_name_;

		
	/**** Méthodes *****/
	
	Skin get_Skin(){return skin_bouton_;}
	
	int get_height(){return height_;}
	int get_width(){return width_;}
	void set_height(int h){height_ = h;}
	void set_width(int w){ width_ = w;}
	void map_name(String n){carte_name_=n;}
	String map_name()
	{
		if(!carte_name_.isEmpty())
			return carte_name_; 
		else 
			return null;
	}
	
	TypeObjet[] carte(){return carte_;}
	void carte_init()
	{
		String name = carte_name_;
		FileHandle file = Gdx.files.internal(name);
		String map = file.readString();
		int saut =0;
		for(int i=0;i<map.length();i++)
		{
			char aChar = map.charAt(i);
			switch (aChar)
			{
		 	  case '0':
				  carte_[i- saut]=TypeObjet.VIDE;
			  break;
			
			  case '3':
				  carte_[i- saut]=TypeObjet.OBSTACLE;
			  break;  
			  
			  case '4':
				  carte_[i- saut]=TypeObjet.DEPART;
			  break;
				  
			  case '5':
				  carte_[i- saut]=TypeObjet.ARRIVEE;
			  break;

			  default:
				  saut++;
				  
			}
		}
	}
	Tower[] tower(){return liste_tours;}
	Mobs[] mobs(){return liste_mobs;}


	public void print_carte() 
	{
		for(int i=0;i<carte_.length;i++)
		{
			if(i%size_n_ == size_n_-1)
				System.out.println(carte_[i].ordinal());
			else
				System.out.print(carte_[i].ordinal());
		}
	}
	

}
