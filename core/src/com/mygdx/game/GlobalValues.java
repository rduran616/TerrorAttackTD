package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.viewport.Viewport;

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
	int height_	= 0; 			//resolution en w et h en px
	int width_	= 0;			
	int size_px_ = 32;			//Taille en px d'un carreaux de la carte
	
	//decors	
	Skin skin_bouton_;			//peau des boutons
	
	//carte
	int ennemi_max_=1000;		//nombre max d'ennemi
	int size_n_ = 20;			//nombre de carreaux de la carte en width
	int size_m_ = 30;			//nombre de carreaux de la carte en height
	TypeObjet carte_[];			//une carte ( à enlever? )
	Tower liste_tours[];		//liste des tours placées
	Mobs liste_mobs[];			//liste des mobs à afficher
	String carte_name_;			//nom ou chemin de la carte
	
	Texture img_;  						//texture carte
    TiledMap tiledMap_; 				//carte
    TiledMapRenderer tiledMapRenderer_;	//rendu de la carte
    MapProperties prop_;				//propriétés de la carte
    
    //gestion de la caméra
    OrthographicCamera camera_;			//camera principale
    double zoom_max_ =1000;				//control du zoom max
    double zoom_min_ = 0;				//control du zoom min

		
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

	
	public ErrorEnum camera_Update()
	{
		if(camera_ != null)
		{
			camera_.update();
			return ErrorEnum.OK;
		}
		else
			return ErrorEnum.UNINITIALIZED;
		
	}
	
	public ErrorEnum tiled_Map_View()
	{
		if(tiledMapRenderer_ != null && camera_ != null)
		{
			tiledMapRenderer_.setView(camera_);
			return ErrorEnum.OK;
		}
		else
			return ErrorEnum.UNINITIALIZED;
	}
	
	public ErrorEnum tiled_Map_Render()
	{
		if(tiledMapRenderer_ != null)
		{
			tiledMapRenderer_.render();
			return ErrorEnum.OK;
		}
		else
			return ErrorEnum.UNINITIALIZED;
	}

	public ErrorEnum init_tile_map(String name)
	{
		tiledMap_ = new TmxMapLoader().load(name);
        tiledMapRenderer_ = new OrthogonalTiledMapRenderer(tiledMap_);
        
        if(tiledMap_ != null)
        	prop_ = tiledMap_.getProperties();
        else
        	return ErrorEnum.UNINITIALIZED;
        
        return ErrorEnum.OK;
	}
	
	public void init_tile_map()
	{
		//float aspectRatio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
		
        try 
        {
        	tiledMap_ = new TmxMapLoader().load(carte_name_);
            tiledMapRenderer_ = new OrthogonalTiledMapRenderer(tiledMap_);
            
            		
    		camera_ = new OrthographicCamera();
        	//camera_ = new OrthographicCamera(25 * aspectRatio ,25);
    		camera_.setToOrtho(false,width_,height_);
    		//camera_.position.set(960/2,640/2,0);
    		camera_.translate(camera_.viewportWidth/2,camera_.viewportHeight/2); //se mettre au milieu de la map
            camera_.update();
            
            
        }catch(SerializationException e) 
        {
        	System.err.println("Erreur: carte non valide");
        }

	}
	
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
	
	public void camera_Init(float w, float h)
	{
		camera_ = new OrthographicCamera();
		camera_.setToOrtho(false,w,h);
        camera_.update();
	}
	
	public void camera_Position(float x, float y)
	{
		camera_.position.set(x,y,0);
	}
	
	public OrthographicCamera camera(){return camera_;} 
	

	public ErrorEnum camera_Translate(float x, float y)
	{
		if(camera_!=null)
		{
			camera_.translate(x, y);
			
			return ErrorEnum.OK;
		}
		
		return ErrorEnum.UNINITIALIZED;
	}

	public ErrorEnum zoom(double d)
	{
		if(camera_!=null)
		{
			if((camera_.zoom + d ) < zoom_max_ && (camera_.zoom + d) > zoom_min_)
				camera_.zoom+=d;
			
			return ErrorEnum.OK;
		}
		else
		{
			System.err.println("Camera non instancier");
			return ErrorEnum.UNINITIALIZED;
		}
	}

	public MapProperties map_Properties(){ return prop_;}
	

	public void size_Px(int px){size_px_=px;}
	public void size_n(int n){size_n_=n;}
	public void size_m(int m){size_m_=m;}
	public int size_Px(){return size_px_;}
	public int size_n(){return size_n_;}
	public int size_m(){return size_m_;}
	
}
