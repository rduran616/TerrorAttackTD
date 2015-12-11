package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/*
 * Classe singleton contenant toutes les variables global et accessible par tous et partout
 * Possibilité d'ajouter des fonctions
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
	
	int height_	= 0; 
	int width_	= 0;
	Skin skin_bouton_;

	
	/**** Méthodes *****/
	
	Skin get_Skin(){return skin_bouton_;}
	
	int get_height(){return height_;}
	int get_width(){return width_;}
	void set_height(int h){height_ = h;}
	void set_width(int w){ width_ = w;}
	

}
