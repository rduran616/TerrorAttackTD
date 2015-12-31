package com.mygdx.game;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.SerializationException;

import Utilitaires.CollisionBox;
import Utilitaires.ReadXml;
import Utilitaires.TypeFlag;
import units.Mobs;
import units.MobsAir;
import units.MobsBasic;
import units.MobsBoss;
import units.MobsLourd;
import units.Status;
import units.Tower;
import units.TowerAir;
import units.TowerBase;
import units.TowerSlow;
import units.TowerType;
import units.TowerZone;

/*
 * Classe singleton contenant toutes les variables global et accessible par tous et partout
 * Possibilité d'ajouter des fonctions
 *
 *
 * A faire: implémenter un assetManager ( creer une classe ) pour le chargement des images et modifier la methode reload 
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
		reload();
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
	private int height_	= 0; 			//resolution en w et h en px
	private int width_	= 0;			
	private int size_px_ = 32;			//Taille en px d'un carreaux de la carte
	
	//decors	
	private Skin skin_bouton_;			//peau des boutons
	
	//sprites
	private SpriteConteneur mobs_sprite_;		//images des mobs
	private SpriteConteneur tower_sprite_;		//images des tours

	//modele d'ennemi et de tour
	private TowerAir t_air_modele_;
	private TowerBase t_base_modele_;
	private TowerZone t_zone_modele_;
	private TowerSlow t_slow_modele_;
	
	private MobsAir m_air_modele_;
	private MobsBasic m_basic_modele_;
	private MobsBoss m_boss_modele_;
	private MobsLourd m_lourd_modele_;
	
	
	//carte
	private int ennemi_max_=1000;				//nombre max d'ennemi
	private int size_n_ = 32;					//nombre de carreaux de la carte en width
	private int size_m_ = 32;					//nombre de carreaux de la carte en height
	private CellMap carte_[];					//une carte qui contient la position des unité placées, des objets et des chemins
	private ArrayList<TowerType> liste_tours;	//liste des tours placées
	private Mobs liste_mobs[];					//liste des mobs à afficher
	private String carte_name_;					//nom ou chemin de la carte
	private int index_unit_selection_;
	
	//private Texture img_;  						//texture carte
	private TiledMap tiledMap_; 				//carte
	private TiledMapRenderer tiledMapRenderer_;	//rendu de la carte
	private MapProperties prop_;				//propriétés de la carte
    
    //gestion de la caméra
	private OrthographicCamera camera_;			//camera principale
	private double zoom_max_ =1000;				//control du zoom max
	private double zoom_min_ = 0;				//control du zoom min
    
    //gestion de la boucle du jeu
	private int argent_;
	private int vie_;
	private Status status_; //status indiquant si on peux créé ou non un objet

		
	/**** Méthodes *****/
	
    public int argent(){return argent_;}
    public void argent(int argent){argent_=argent;}
    public int vie(){return vie_;}
    public void vie(int v){vie_=v;}
    public boolean is_Alive(){if(vie_>0 )return true; else return false;}
    
    public Skin get_Skin(){return skin_bouton_;}
	
    public int get_height(){return height_;}
    public int get_width(){return width_;}
    public void set_height(int h){height_ = h;}
    public void set_width(int w){ width_ = w;}
    public void map_name(String n){carte_name_=n;}
	public String map_name()
	{
		if(!carte_name_.isEmpty())
			return carte_name_; 
		else 
			return null;
	}
	
	public CellMap[] carte(){return carte_;}
	public ArrayList<TowerType> tower()
	{
		if(liste_tours == null)
			liste_tours = new ArrayList<TowerType>();
		
		return liste_tours;
	}
	public TowerType tower(int i)
	{
		return liste_tours.get(i);
	}
	
	public void carte_Init()
	{
		carte_ = new CellMap[size_n_ * size_m_];
		for(int i =0;i<size_n_ * size_m_;i++)
			carte_[i] = new CellMap(i, 0, size_n_, null, null, null);
	}
	public void placer_Object(int celulle,int index,TypeObjet type)
	{
		switch(type)
		{
			case ARRIVEE:
			break;
			
			case CHEMIN:
				carte_[celulle].add_Chemin(index);
			break;
			
			case DEPART:
			break;
			
			case ENNEMI:
			break;
			
			case OBSTACLE:
				carte_[celulle].add_Obstacle(index);
			break;
			
			case TOUR:
				carte_[celulle].add_Unit(index);
			break;
			
			case VIDE:
			break;
			
			default:
			break;
		
		}
	}
	
	
	
	
	public Mobs[] mobs(){return liste_mobs;}

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

            size_px_	= tiledMap_.getProperties().get("tileheight",Integer.class);
    		size_n_		= tiledMap_.getProperties().get("height",Integer.class);
			size_m_		= tiledMap_.getProperties().get("width",Integer.class);
	
    		camera_ = new OrthographicCamera(width_,height_);
        	//camera_ = new OrthographicCamera(25 * aspectRatio ,25);
    		camera_.setToOrtho(false,width_,height_);
            camera_.update();
            
        }catch(SerializationException e) 
        {
        	System.err.println("Erreur: carte non valide");
        }

	}
	
	/*public void print_carte() 
	{
		for(int i=0;i<carte_.length;i++)
		{
			if(i%size_n_ == size_n_-1)
				System.out.println(carte_[i].ordinal());
			else
				System.out.print(carte_[i].ordinal());
		}
	}*/
	
	public void camera_Init(float w, float h)
	{
		camera_ = new OrthographicCamera();
		camera_.setToOrtho(false,w,h);
        camera_.update(); 
	}
	
	public void camera_Position(float x, float y)
	{
		System.err.println("position");
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
	
	private void generation_Map_Tower()
	{
		
	}
	
	
	
	//chargement/rechargement des ellements visuel du jeux
	public ErrorEnum reload()
	{
		System.err.println("reload");
		
		height_ = Gdx.graphics.getHeight();
		width_ 	= Gdx.graphics.getWidth();
			
		//creation d'un skin ( fond des boutons )
		skin_bouton_ = new Skin( Gdx.files.internal( "uiskin.json" )); //valeur par defaut
		
		//creation carte de base et des conteneurs d'objets
	/*	if(size_n_ > 0 && size_m_ > 0 )
		{
			carte_ = new TypeObjet[size_n_ * size_m_]; //nombre de case total de la carte
			//liste_tours = new Tower[size_n_ * size_m_ - 2]; //nombre de tour possibles - 2 car point de dépar et arrivée 
		}*/
		
		liste_mobs = new Mobs[ennemi_max_]; //nombre d'ennemi max en même temps sur la carte

		argent_ = 100;
		vie_ = 100;
		status_ = Status.POSITIONNE;
		
		get_Units_Model();
		
		return ErrorEnum.OK;
	}

	private void get_Units_Model()
	{

		if(Gdx.app.getType() == ApplicationType.Android)
		{
			
			mobs_sprite_  = new SpriteConteneur("Config/units.xml", "mobs", "src_andro",TypeFlag.FILEHANDLE_INTERNAL);
			tower_sprite_ = new SpriteConteneur("Config/units.xml", "tower", "src_andro",TypeFlag.FILEHANDLE_INTERNAL);
			
			ReadInternalXML xml = new ReadInternalXML("Config/units.xml");
			CollisionBox bbox; 
			for(int i=0; i < xml.get_Number_Child("mobs"); i++)
			{
				String n =xml.get_Child_Attribute("mobs", "name", i);
				int vit= Integer.parseInt(xml.get_Child_Attribute("mobs", "vit", i));
				int money=Integer.parseInt(xml.get_Child_Attribute("mobs", "money", i));
				int power=Integer.parseInt(xml.get_Child_Attribute("mobs", "power", i));
				int vie=Integer.parseInt(xml.get_Child_Attribute("mobs", "vie", i));
				if(n.equals("air"))
					m_air_modele_= new MobsAir(vie, vit, money, power);
				else if(n.equals("basic"))
					m_basic_modele_= new MobsBasic(vie, vit, money, power);
				else if(n.equals("boss"))
					m_lourd_modele_= new MobsLourd(vie, vit, money, power);
				else if(n.equals("lourd"))
					m_boss_modele_= new MobsBoss(vie, vit, money, power);
				
			} 
			
			for(int i=0; i < xml.get_Number_Child("tower"); i++)
			{
				String n =xml.get_Child_Attribute("tower", "name", i);
				int vit= Integer.parseInt(xml.get_Child_Attribute("tower", "vit", i));
				int money=Integer.parseInt(xml.get_Child_Attribute("tower", "money",  i));
				int power=Integer.parseInt(xml.get_Child_Attribute("tower", "power",  i));
				int range=Integer.parseInt(xml.get_Child_Attribute("tower", "range",  i));
				int h=Integer.parseInt(xml.get_Child_Attribute("tower", "h",  i));
				int w=Integer.parseInt(xml.get_Child_Attribute("tower", "w",  i));
				int air=Integer.parseInt(xml.get_Child_Attribute("tower", "air",  i));
				int n_txt=Integer.parseInt(xml.get_Child_Attribute("tower", "n_texture", i));
				
				bbox = new CollisionBox(0,0,w,h);

				
				if(n.equals("air"))
					t_air_modele_ = new TowerAir(vit,money,power,range,h,w,air,n_txt,n,bbox);
				else if(n.equals("base"))
					t_base_modele_ = new TowerBase();
				else if(n.equals("slow"))
					t_slow_modele_ = new TowerSlow();
				else if(n.equals("zone"))
					t_zone_modele_ = new TowerZone();
				
			} 
		}
		else
		{
			ReadXml xml = new ReadXml("../android/assets/Config/units.xml");
			mobs_sprite_  = new SpriteConteneur("../android/assets/Config/units.xml", "mobs", "src_desk",TypeFlag.PATH);
			tower_sprite_ = new SpriteConteneur("../android/assets/Config/units.xml", "tower","src_desk",TypeFlag.PATH);
			CollisionBox bbox;

			for(int i=0; i < xml.node_Item_Child_Number("mobs"); i++)
			{
				String n =xml.get_Sub_Node_Item(i, "mobs", "name");
				int vit= Integer.parseInt(xml.get_Sub_Node_Item(i, "mobs", "vit"));
				int money=Integer.parseInt(xml.get_Sub_Node_Item(i, "mobs", "money"));
				int power=Integer.parseInt(xml.get_Sub_Node_Item(i, "mobs", "power"));
				int vie=Integer.parseInt(xml.get_Sub_Node_Item(i, "mobs", "vie"));
				if(n.equals("air"))
					m_air_modele_= new MobsAir(vie, vit, money, power);
				else if(n.equals("basic"))
					m_basic_modele_= new MobsBasic(vie, vit, money, power);
				else if(n.equals("boss"))
					m_lourd_modele_= new MobsLourd(vie, vit, money, power);
				else if(n.equals("lourd"))
					m_boss_modele_= new MobsBoss(vie, vit, money, power);
				
			} 
			
			for(int i=0; i < xml.node_Item_Child_Number("tower"); i++)
			{
				String n =xml.get_Sub_Node_Item(i, "tower", "name");
				int vit= Integer.parseInt(xml.get_Sub_Node_Item(i, "tower", "vit"));
				int money=Integer.parseInt(xml.get_Sub_Node_Item(i, "tower", "money"));
				int power=Integer.parseInt(xml.get_Sub_Node_Item(i, "tower", "power"));
				int range=Integer.parseInt(xml.get_Sub_Node_Item(i, "tower", "range"));
				int h=Integer.parseInt(xml.get_Sub_Node_Item(i, "tower", "h"));
				int w=Integer.parseInt(xml.get_Sub_Node_Item(i, "tower", "w"));
				int air=Integer.parseInt(xml.get_Sub_Node_Item(i, "tower", "air"));
				int n_txt=Integer.parseInt(xml.get_Sub_Node_Item(i, "tower", "n_texture"));
				
				bbox = new CollisionBox(0,0,w,h);
				
				if(n.equals("air"))
					t_air_modele_ = new TowerAir(vit,money,power,range,h,w,air,n_txt,n,bbox);
				else if(n.equals("base"))
					t_base_modele_ = new TowerBase();
				else if(n.equals("slow"))
					t_slow_modele_ = new TowerSlow();
				else if(n.equals("zone"))
					t_zone_modele_ = new TowerZone();
				
			} 
		}
	}
	
	public Sprite tower_sprite(int index){return tower_sprite_.sprite(index);}
	public Sprite mob_sprite(int index){return mobs_sprite_.sprite(index);}

	public TowerAir t_air_modele_(){return t_air_modele_;}
	public TowerBase t_base_modele_(){return t_base_modele_;}
	public TowerZone t_zone_modele_(){return t_zone_modele_;}
	public TowerSlow t_slow_modele_(){return t_slow_modele_;}
	
	public MobsAir m_air_modele_(){return m_air_modele_;}
	public MobsBasic m_basic_modele_(){return m_basic_modele_;}
	public MobsBoss m_boss_modele_(){return m_boss_modele_;}
	public MobsLourd m_lourd_modele_(){return m_lourd_modele_;}

	public Status status(){return status_;}
	public void status(Status s){status_=s;}
	
	public TowerType last_tower(){return liste_tours.get(liste_tours.size()-1);}
	
	public void size_Px(int px){size_px_=px;}
	public void size_n(int n){size_n_=n;}
	public void size_m(int m){size_m_=m;}
	public int size_Px(){return size_px_;}
	public int size_n(){return size_n_;}
	public int size_m(){return size_m_;}


	public int getIndex_unit_selection_() {
		return index_unit_selection_;
	}


	public void setIndex_unit_selection_(int index_unit_selection_) {
		this.index_unit_selection_ = index_unit_selection_;
	}
	
}
