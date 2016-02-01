package com.mygdx.game;


import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.SerializationException;

import Utilitaires.CollisionBox;
import Utilitaires.Localisation;
import Utilitaires.ReadXml;
import Utilitaires.TypeFlag;
import units.Mobs;
import units.MobsAir;
import units.MobsBasic;
import units.MobsBoss;
import units.MobsLourd;
import units.Status;
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
 *
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
		
		if(assets==null)
			assets = new AssetManager();
		
		load();
		
		if(pile_mobs_air_==null)
			pile_mobs_air_ =  new Stack<MobsAir>();				//pile contenant les mobs créés mais plus utilisé
		if(pile_mobs_base_==null)
			pile_mobs_base_ = new Stack<MobsBasic>();
		if(pile_mobs_lourd_==null)
			pile_mobs_lourd_ = new 	Stack<MobsLourd>();	
		if(pile_mobs_boss_==null)
			pile_mobs_boss_ = new Stack<MobsBoss>();	
		if(pile_shot_==null)
			pile_shot_ = new Stack<Tir>();
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
	
	public final static void disposeInstance() 
	{
	    if (GlobalValues.instance != null) 
	    {
	    	//dispose all
	    	
	    	
	    	
	    	GlobalValues.instance = null;
	    }
	}
	
	
	
	
	
	/**** Attributs *****/
	
	private AssetManager assets;
	
	//général
	private int height_	= 0; 			//resolution en w et h en px
	private int width_	= 0;			
	private int size_px_ = 32;			//Taille en px d'un carreaux de la carte
	
	//decors	
	private Skin skin_bouton_;			//peau des boutons
	
	//sprites
	private SpriteConteneurAnimation mobs_sprite_;		//images des mobs
	private SpriteConteneur tower_sprite_;				//images des tours
	private SpriteConteneurAnimation shot_sprites_;		//image des tirs
	private float time = 0.2f;

	//modele d'ennemi, tour et shoot
	private TowerAir t_air_modele_;
	private TowerBase t_base_modele_;
	private TowerZone t_zone_modele_;
	private TowerSlow t_slow_modele_;
	private TowerType t_temporaire_;
	
	private MobsAir m_air_modele_;
	private MobsBasic m_basic_modele_;
	private MobsBoss m_boss_modele_;
	private MobsLourd m_lourd_modele_;
	
	private Tir tir_modele_;
	private int num_tr_upgrade_ =-1;
	
	private int nb_unite_air_ =0;
	private int nb_unite_zone_ =0;
	private int nb_unite_slow_ =0;
	private int nb_unite_base_ =0;
	
	//carte
	private int ennemi_max_=1000;				//nombre max d'ennemi
	private int size_n_ = 32;					//nombre de carreaux de la carte en width
	private int size_m_ = 32;					//nombre de carreaux de la carte en height
	private CellMap carte_[];					//une carte qui contient la position des unité placées, des objets et des chemins
	private boolean shader_enable =true;
	
	//private ArrayList<TowerType> liste_tours;	//liste des tours placées
	private ArrayList<Mobs> liste_mobs;			//liste des mobs à afficher
	public Stack<MobsAir> pile_mobs_air_;				//pile contenant les mobs créés mais plus utilisé
	public Stack<MobsBasic> pile_mobs_base_;	
	public Stack<MobsLourd> pile_mobs_lourd_;	
	public Stack<MobsBoss> pile_mobs_boss_;	
	
	
	private Stack<Tir> pile_shot_;				//pile contenant les tirs créés mais plus utilisé
	private ArrayList<Tir> liste_shots_ = null;		//liste des balles tiré en cours
	private String carte_name_;					//nom ou chemin de la carte
	private int index_unit_selection_;
	private boolean recalculerChemin_ = false;
	
	//private Texture img_;  					//texture carte
	private TiledMap tiledMap_; 				//carte
	private OrthogonalTiledMapRenderer tiledMapRenderer_;	//rendu de la carte
	private MapProperties prop_;				//propriétés de la carte
    
    //gestion de la caméra
	private OrthographicCamera camera_;			//camera principale
	private double zoom_max_ = 1f;				//control du zoom max
	private double zoom_min_ = 0.1f;			//control du zoom min
	private SpriteBatch batch;
    
    //gestion de la boucle du jeu
	private int argent_;
	private int vie_;
	private Status status_; //status indiquant si on peux créé ou non un objet
	private float  vitesse_projectil_ = 1 ; //x case par sec

	
	//localisation
	private Localisation localisation;
	
	
	
		
	/**** Méthodes *****/
	
    public int argent(){return argent_;}
    public void argent(int argent){argent_=argent;}
    public int vie(){return vie_;}
    public void vie(int v){vie_=v;}
    public boolean is_Alive(){if(vie_>0 )return true; else return false;}
    
    public Skin get_Skin(){return skin_bouton_;}
    public void Set_Skin(Skin s){skin_bouton_ = s;}
    public void skin_Dispose(){skin_bouton_.dispose();}
    
    
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
	
	
	public void carte_Init()
	{
		carte_ = new CellMap[size_n_ * size_m_];//0->1023 = 1024
		for(int i =0;i<size_n_;i++)//0->31 = 32
		{	
			for(int j =0;j<size_m_;j++)//0->31 =32
			{
				carte_[i*size_n_+j] = new CellMap(size_n_,size_m_,i*size_n_+j,i,j, size_n_, null, null, null, null);
				//System.err.println(i*size_n_+j);
			}
		}
	}
		
	
	
	public ArrayList<Mobs> mobs(){return liste_mobs;}

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
	
	public TiledMap tiled_Map_(){return tiledMap_;}
	public OrthogonalTiledMapRenderer tiled_Map(){return tiledMapRenderer_;}
	public SpriteBatch batch(){return batch;}
	
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
	

	
	public ErrorEnum init_tile_map(String name, SpriteBatch b )
	{
		tiledMap_ = new TmxMapLoader().load(name);
		if(b == null)
		{  
			batch = new SpriteBatch();
			tiledMapRenderer_ = new OrthogonalTiledMapRenderer(tiledMap_, batch);
		}
		else
		{
			batch = new SpriteBatch();
			batch = b;
			tiledMapRenderer_ = new OrthogonalTiledMapRenderer(tiledMap_, batch);
		}
        
        if(tiledMap_ != null)
        	prop_ = tiledMap_.getProperties();
        else
        	return ErrorEnum.UNINITIALIZED;
        
        return ErrorEnum.OK;
	}
	
	public void init_tile_map()
	{
		//float aspectRatio = (float)Gdx.graphics.getWidth()/(float)Gdx.graphics.getHeight();
        try 
        {
        	if(batch == null)
    			batch = new SpriteBatch();
        	
        	
        	tiledMap_ = new TmxMapLoader().load(carte_name_);
            tiledMapRenderer_ = new OrthogonalTiledMapRenderer(tiledMap_,1,batch);

            size_px_	= tiledMap_.getProperties().get("tileheight",Integer.class);
    		size_n_		= tiledMap_.getProperties().get("height",Integer.class);
			size_m_		= tiledMap_.getProperties().get("width",Integer.class);
				
    		camera_Init();
            
        }catch(SerializationException e) 
        {
        	System.err.println("Erreur: carte non valide");
        }

	}
	
	
	public void camera_Init()
	{
		float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera_ = new OrthographicCamera(w,h);
		camera_.position.set(this.size_n()*this.size_Px() / 2f, this.size_m()*this.size_Px() / 2f, 0);

        camera_.update(); 
        
        batch = new SpriteBatch();
	}
	
	public void camera_Resize(int width, int height) {
	    camera_.viewportWidth = 30f;
	    camera_.viewportHeight = 30f * height/width;
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
			camera_.update();

			return ErrorEnum.OK;
		}
		
		return ErrorEnum.UNINITIALIZED;
	}

	public ErrorEnum zoom(double d, double z_mx, double z_min)
	{
		if(camera_!=null)
		{
			if((camera_.zoom + d ) < z_mx && (camera_.zoom + d) >= z_min)
				camera_.zoom+=d;
				System.err.println("zoom ="+camera_.zoom);
			
			return ErrorEnum.OK;
		}
		else
		{
			System.err.println("Camera non instancier");
			return ErrorEnum.UNINITIALIZED;
		}
	}

	public MapProperties map_Properties(){ return prop_;}

	//chargement/rechargement des ellements visuel du jeux
	public ErrorEnum reload_asset()
	{
		System.err.println("reload_asset");
		
		main_Loading();
		
		//creation d'un skin ( fond des boutons )
		skin_bouton_ = assets.get("uiskin.json",Skin.class); //valeur par defaut
		get_Units_Model();//recuperation des images
		
		localisation = new Localisation("Lang/MyBundle","","","");
		
		return ErrorEnum.OK;
	}
	
	
	public void main_Loading()
	{
		assets.load("uiskin.json", Skin.class);
		//while(!assets.update());
		
		assets.finishLoading();
		System.err.println("fin chargement");
	}
	
	
	public ErrorEnum load()
	{
		System.err.println("load");
		
		main_Loading();
		
		height_ = Gdx.graphics.getHeight();
		width_ 	= Gdx.graphics.getWidth();
			
		//creation d'un skin ( fond des boutons )
		//skin_bouton_= new Skin();
		skin_bouton_ = assets.get("uiskin.json",Skin.class);
		System.err.println("skin affecté");
		//skin_bouton_ = new Skin( Gdx.files.internal( "uiskin.json" )); //valeur par defaut
		
		liste_mobs = new ArrayList<Mobs>();//[ennemi_max_]; //nombre d'ennemi max en même temps sur la carte
		liste_shots_ = new ArrayList<Tir>();
		status_ = Status.POSITIONNE;
		
		localisation = new Localisation("Lang/MyBundle","","","");
		
		get_Units_Model();
		
		return ErrorEnum.OK;
	}
	

	public void reinit_modele(){get_Units_Model();}
	
	private void get_Units_Model()
	{

		if(Gdx.app.getType() == ApplicationType.Android)
		{			
			mobs_sprite_  = new SpriteConteneurAnimation("Config/units.xml", "mobs", "src_andro",TypeFlag.FILEHANDLE_INTERNAL,4,4,time);
			tower_sprite_ = new SpriteConteneur("Config/units.xml", "tower", "src_andro",TypeFlag.FILEHANDLE_INTERNAL);
			shot_sprites_ = new SpriteConteneurAnimation("Config/units.xml","tir","src_andro",TypeFlag.FILEHANDLE_INTERNAL,12,1,time);
			
			ReadInternalXML xml = new ReadInternalXML("Config/units.xml");
			CollisionBox bbox; 
			int nb = Integer.parseInt(xml.get_Attribute("mobs","value"));
			for(int i=0; i < nb; i++)
			{
				String n =xml.get_Child_Attribute("mobs", "name", i);
				int vit= Integer.parseInt(xml.get_Child_Attribute("mobs", "vit", i));
				int money=Integer.parseInt(xml.get_Child_Attribute("mobs", "money", i));
				int degat=Integer.parseInt(xml.get_Child_Attribute("mobs", "power", i));
				int vie=Integer.parseInt(xml.get_Child_Attribute("mobs", "vie", i));
				int h=Integer.parseInt(xml.get_Child_Attribute("mobs", "h",  i));
				int w=Integer.parseInt(xml.get_Child_Attribute("mobs", "w",  i));
				int air=Integer.parseInt(xml.get_Child_Attribute("mobs", "air",  i));
				int n_txt=Integer.parseInt(xml.get_Child_Attribute("mobs", "n_texture", i));
				
				bbox = new CollisionBox(0,0,w,h);

				if(n.equals("air"))
					m_air_modele_= new MobsAir(vie, vit, money, degat,h,w,air,n_txt,n,bbox);
				else if(n.equals("basic"))
					m_basic_modele_= new MobsBasic(vie, vit, money, degat,h,w,air,n_txt,n,bbox);
				else if(n.equals("boss"))
					m_lourd_modele_= new MobsLourd(vie, vit, money, degat,h,w,air,n_txt,n,bbox);
				else if(n.equals("lourd"))
					m_boss_modele_= new MobsBoss(vie, vit, money, degat,h,w,air,n_txt,n,bbox);
				
			} 
			
			nb = Integer.parseInt(xml.get_Attribute("tower","value"));
			for(int i=0; i < nb; i++)
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
					t_air_modele_ = new TowerAir(money,vit,power,range,h,w,air,n_txt,n,bbox);
				else if(n.equals("base"))
					t_base_modele_ = new TowerBase(money,vit,power,range,h,w,air,n_txt,n,bbox);
				else if(n.equals("slow"))
					t_slow_modele_ = new TowerSlow(money,vit,power,range,h,w,air,n_txt,n,bbox);
				else if(n.equals("zone"))
					t_zone_modele_ = new TowerZone(money,vit,power,range,h,w,air,n_txt,n,bbox);
				
			} 
			
			nb = Integer.parseInt(xml.get_Attribute("tir","value"));
			for(int i=0; i < nb; i++)
			{
				String n =xml.get_Child_Attribute("tir", "name", i);
				int h= Integer.parseInt(xml.get_Child_Attribute("tir", "h", i));
				int w= Integer.parseInt(xml.get_Child_Attribute("tir", "w", i));
				int n_txt=Integer.parseInt(xml.get_Child_Attribute("tir", "n_texture", i));
				int nb_anim=Integer.parseInt(xml.get_Child_Attribute("tir", "nb_anim", i));
				
				bbox = new CollisionBox(0,0,w,h);
				tir_modele_ = new Tir(n,h,w,n_txt,0,bbox,nb_anim);
			} 
		}
		else
		{
			ReadXml xml = new ReadXml("../android/assets/Config/units.xml");
			mobs_sprite_  = new SpriteConteneurAnimation("../android/assets/Config/units.xml", "mobs", "src_desk",TypeFlag.PATH,4,4,time);
			tower_sprite_ = new SpriteConteneur("../android/assets/Config/units.xml", "tower","src_desk",TypeFlag.PATH);
			shot_sprites_ = new SpriteConteneurAnimation("../android/assets/Config/units.xml","tir","src_desk",TypeFlag.FILEHANDLE_INTERNAL,12,1,time);
			
			CollisionBox bbox;

			for(int i=0; i < xml.node_Item_Child_Number("mobs"); i++)
			{
				String n =xml.get_Sub_Node_Item(i,"mobs", "name");
				int vit= Integer.parseInt(xml.get_Sub_Node_Item(i, "mobs", "vit"));
				int money=Integer.parseInt(xml.get_Sub_Node_Item(i,"mobs", "money"));
				int degat=Integer.parseInt(xml.get_Sub_Node_Item(i, "mobs", "power"));
				int vie=Integer.parseInt(xml.get_Sub_Node_Item(i, "mobs", "vie"));
				int h=Integer.parseInt(xml.get_Sub_Node_Item(i,"mobs", "h"));
				int w=Integer.parseInt(xml.get_Sub_Node_Item(i,"mobs", "w"));
				int air=Integer.parseInt(xml.get_Sub_Node_Item(i,"mobs", "air"));
				int n_txt=Integer.parseInt(xml.get_Sub_Node_Item(i,"mobs", "n_texture"));

				bbox = new CollisionBox(0,0,w,h);
				
				if(n.equals("air"))
					m_air_modele_= new MobsAir(vie, vit, money, degat,h,w,air,n_txt,n,bbox);
				else if(n.equals("basic"))
					m_basic_modele_= new MobsBasic(vie, vit, money, degat,h,w,air,n_txt,n,bbox);
				else if(n.equals("boss"))
					m_lourd_modele_= new MobsLourd(vie, vit, money, degat,h,w,air,n_txt,n,bbox);
				else if(n.equals("lourd"))
					m_boss_modele_= new MobsBoss(vie, vit, money, degat,h,w,air,n_txt,n,bbox);
				
				
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
					t_air_modele_ = new TowerAir(money,vit,power,range,h,w,air,n_txt,n,bbox);
				else if(n.equals("base"))
					t_base_modele_ = new TowerBase(money,vit,power,range,h,w,air,n_txt,n,bbox);
				else if(n.equals("slow"))
					t_slow_modele_ = new TowerSlow(money,vit,power,range,h,w,air,n_txt,n,bbox);
				else if(n.equals("zone"))
					t_zone_modele_ = new TowerZone(money,vit,power,range,h,w,air,n_txt,n,bbox);
				
			} 
			
			for(int i=0; i < xml.node_Item_Child_Number("tir"); i++)
			{
				String n =xml.get_Sub_Node_Item(i, "tir", "name");
				int h= Integer.parseInt(xml.get_Sub_Node_Item(i,"tir", "h"));
				int w= Integer.parseInt(xml.get_Sub_Node_Item(i,"tir", "w"));
				int n_txt=Integer.parseInt(xml.get_Sub_Node_Item(i,"tir", "n_texture"));
				int nb_anim=Integer.parseInt(xml.get_Sub_Node_Item(i,"tir", "nb_anim"));
				
				bbox = new CollisionBox(0,0,w,h);
				tir_modele_ = new Tir(n,h,w,n_txt,0,bbox,nb_anim);
			} 
		}
	}
	
	public Sprite tower_sprite(int index){return tower_sprite_.sprite(index);}
	public Sprite mob_sprite(int index){return mobs_sprite_.sprite(index);}
	public SpriteConteneurAnimation mob_sprite_anime(){return mobs_sprite_;}

	public TowerAir t_air_modele_(){return t_air_modele_;}
	public TowerBase t_base_modele_(){return t_base_modele_;}
	public TowerZone t_zone_modele_(){return t_zone_modele_;}
	public TowerSlow t_slow_modele_(){return t_slow_modele_;}
	
	public void t_air_modele_(TowerAir t){t_air_modele_ = t;}
	public void t_base_modele_(TowerBase t){t_base_modele_ = t;}
	public void t_zone_modele_(TowerZone t){t_zone_modele_ = t;}
	public void t_slow_modele_(TowerSlow t){t_slow_modele_= t;}
	
	
	public MobsAir m_air_modele_(){return m_air_modele_;}
	public MobsBasic m_basic_modele_(){return m_basic_modele_;}
	public MobsBoss m_boss_modele_(){return m_boss_modele_;}
	public MobsLourd m_lourd_modele_(){return m_lourd_modele_;}

	public Status status(){return status_;}
	public void status(Status s){status_=s;}
	

	
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
	
	
	public int get_Case(int ScrX, int ScrY)
	{
		int cell =-1;

		Vector3 pos = new Vector3(ScrX,ScrY,0);
		camera().unproject(pos);
		int x = (int) (pos.x / size_Px());
		int y = (int) (pos.y / size_Px());
		cell = ((x) * size_n()) +  y ; 
		
		return cell;
	}

	public int get_Index_Cellule(int x, int y)
	{
		//calcul coordonnées dans matrice n*m via les coord du monde en px
		int n = (int) (x / size_n_);
		int m = (int) (y / size_m_);
		
		return (n * size_n_) + m ; 
	}
	

	public Sprite mobs(int num_texture_) 
	{
		return null;
	}


	public int ennemi_max_() {
		return ennemi_max_;
	}


	public void ennemi_max_(int ennemi_max_) {
		this.ennemi_max_ = ennemi_max_;
	}


	public Stack<Mobs> pile_Mobs_() {
		return null;
	}


	public void pile_Mobs_(Stack<Mobs> pile_mobs_) {
		//this.pile_mobs_ = pile_mobs_;
	}


	public boolean recalculerChemin_() {
		return recalculerChemin_;
	}


	public void recalculerChemin_(boolean recalculerChemin_) {
		this.recalculerChemin_ = recalculerChemin_;
	}


	public TowerType getT_temporaire_() {
		return t_temporaire_;
	}


	public void setT_temporaire_(TowerType t_temporaire_) {
		this.t_temporaire_ = t_temporaire_;
	}


	public float vitesse_Projectil() {
		return vitesse_projectil_;
	}


	public void vitesse_Projectil(float vitesse_projectil_) {
		this.vitesse_projectil_ = vitesse_projectil_;
	}


	public Stack<Tir> getPile_shot_() {
		return pile_shot_;
	}


	public void setPile_shot_(Stack<Tir> pile_shot_) {
		this.pile_shot_ = pile_shot_;
	}


	public ArrayList<Tir> shots() 
	{
		if(liste_shots_ == null)
		{
			liste_shots_ = new ArrayList<Tir>();
		}
		
		return liste_shots_;
	}


	public void shots(ArrayList<Tir> liste_shots_) {
		this.liste_shots_ = liste_shots_;
	}


	public Tir tir_Modele_() {
		return tir_modele_;
	}


	public void tir_Modele_(Tir tir_modele_) {
		this.tir_modele_ = tir_modele_;
	}


	public SpriteConteneurAnimation shots_Sprite_() {
		return shot_sprites_;
	}


	public void shots_Sprite_(SpriteConteneurAnimation shots_sprite_) {
		this.shot_sprites_ = shots_sprite_;
	}


	public  void update_Image(SpriteConteneur model,int index, String path_new_img)
	{
		model.sprite(index).setTexture(new Texture(Gdx.files.internal(path_new_img)));
	}


	public int getNum_tr_upgrade_() {
		return num_tr_upgrade_;
	}


	public void setNum_tr_upgrade_(int num_tr_upgrade_) {
		this.num_tr_upgrade_ = num_tr_upgrade_;
	}


	public int getNb_unite_air_() 
	{
		return nb_unite_air_;
	}


	public void setNb_unite_air_(int nb_unite_air_) 
	{
		this.nb_unite_air_ = nb_unite_air_;
	}
	
	public void add_uAir(int nb)
	{
		this.nb_unite_air_+=nb;
	}
	
	public void sub_uAir(int nb)
	{
		
		this.nb_unite_air_-=nb;
		if(nb_unite_air_<0)
			nb_unite_air_=0;
	}


	public int getNb_unite_zone_() 
	{
		return nb_unite_zone_;
	}


	public void setNb_unite_zone_(int nb_unite_zone_) 
	{
		this.nb_unite_zone_ = nb_unite_zone_;
	}
	
	public void add_uZone(int nb)
	{
		this.nb_unite_zone_+=nb;
	}
	
	public void sub_uZone(int nb)
	{
		this.nb_unite_zone_-=nb;
		if(nb_unite_zone_<0)
			nb_unite_zone_=0;
	}


	public int getNb_unite_slow_() 
	{
		return nb_unite_slow_;
	}


	public void setNb_unite_slow_(int nb_unite_slow_) 
	{
		this.nb_unite_slow_ = nb_unite_slow_;
	}

	public void add_uSlow(int nb)
	{
		this.nb_unite_slow_+=nb;
	}
	
	public void sub_uSlow(int nb)
	{
		this.nb_unite_slow_-=nb;
		if(nb_unite_slow_<0)
			nb_unite_slow_=0;
	}

	public int getNb_unite_base_() 
	{
		return nb_unite_base_;
	}


	public void setNb_unite_base_(int nb_unite_base_) 
	{
		this.nb_unite_base_ = nb_unite_base_;
	}
	
	public void add_uBase(int nb)
	{
		this.nb_unite_base_+=nb;
	}
	
	public void sub_uBase(int nb)
	{
		this.nb_unite_base_-=nb;
		if(nb_unite_base_<0)
			nb_unite_base_=0;
	}
	

	public void argent_plus(int val){argent_+=val;}
	
	
	public boolean collision_Avec_Tour(TowerType t)
	{
		//la position dans le monde
	   	Vector3 pos = new Vector3(t.position().x,t.position().y,0); 
		//la case ou on place le bonhomme
			int cell = get_Index_Cellule((int)pos.x,(int)pos.y);

	   //vérification collision 
	   ArrayList<TowerType> list_tower = carte()[cell].getUnits_();
	   if(list_tower.size()>0)
	   {
		   for(int i=0; i < list_tower.size();i++)
		   {
			   CollisionBox b = t.box(); 
			   if(list_tower.get(i).collision(b))
			   {
				   return true; 
			   }
		   }
	   }
	   return false;
	}


	public Localisation localisation() {
		return localisation;
	}


	public void localisation(Localisation localisation) {
		this.localisation = localisation;
	}


	public Object tower_sprite() 
	{
		if(tower_sprite_ == null)
			return null;
		else
			return tower_sprite_;
	}


	public boolean isShader_enable() {
		return shader_enable;
	}


	public void setShader_enable(boolean shader_enable) {
		this.shader_enable = shader_enable;
	}


	public AssetManager getAssets() {
		return assets;
	}


	public void setAssets(AssetManager assets) {
		this.assets = assets;
	}
	
}
