package units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import Utilitaires.CollisionBox;


/*
 * classe mère de toutes les tours!
 * 
 * Gestion des principale caracteristiques de chaque tour
 */

public abstract class TowerType 
{
	//attribut commun à chaque tour
	private Vector2 position_;  	//position dans le monde
	private CollisionBox  bbox_; 	//boite de collision
	private int size_h_;		 	//taille du sprite en height
	private int size_w_; 			//taille du sprite en width
	
	private String _nom; 		//nom de la tour
	protected int _cout; 			//son cout
	protected float _attspeed; 	//vitesse d'attaque
	protected float _range; 		//rayon d'action
	protected int _damage; 		//domage
	protected boolean _air;		 		// tire sur les ennemies volant?
	
	//attributs non exploités
	//private String texture_;	  //l'image est stocker dans un tableau de modèle (une image en memoire, des milliers de tour affiché)
	//private String bulletTexture_;//idem
	//public  Vector2 bulletSize_; //n'a rien à faire la
	
	public TowerType()
	{
		//status_ = Status.NON_POSITIONNE;
		position_= new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
		bbox_ = new CollisionBox();
	}
	
	
	public TowerType(int cout, int att_speed,int damage, int range,int h, int w,int air, String nom, CollisionBox box )
	{
		position_= new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
		
		if(h>=0)
			size_h_=h;
		else
			size_h_=0;
		
		if(w>=0)
			size_w_=w;
		else
			size_w_=0;
			
		if(nom!=null)
			_nom=nom;
		else
			_nom="undefined";
		
		if(cout >=0)
			_cout = cout;
		else
			_cout =0;
		
		if(att_speed >=0)
			_attspeed = att_speed;
		else
			_attspeed =0;
			
		if(range >=0)
			_range = range;
		else
			_range =0;
		
		if(damage >=0)
			_damage = damage;
		else
			_damage =0;

		if(box != null)
			bbox_ = box;
		else
			bbox_ = new CollisionBox();
		
		if(air == 1)
			_air = true;
		else
			_air = false;
	}
	
	
	
	//methode appeler pour tirer sur les mobs (implémenté par les classes filles)
	public abstract void onExecute();

	//ascesseurs (get et set)
	public int size_H(){return size_h_;}
	public int size_W(){return size_w_;}
	public void size_H(int h){size_h_ = h;}
	public void size_W(int w){size_w_ = w;}
	
	public Vector2 position(){return position_;}
	public Vector3 position3(){return new Vector3(position_.x,position_.y,0);}
	public void position(Vector2 position){position_=position; maj_box();}
	public void position(float x, float y){position_=new Vector2(x,y);maj_box();}
	public void position(int x, int y){position_=new Vector2(x,y);maj_box();}
	public void position_add(int x, int y){position_.x+=x;position_.y+=y;maj_box();} 
	public void position(Vector3 pos){position_.x=pos.x; position_.y=pos.y;}
	
	private void maj_box()
	{
		bbox_.set_X((int)position_.x);
		bbox_.set_Y((int)position_.y);
	}
	
	public CollisionBox box(){return bbox_;}
	public void CollisionBox (CollisionBox  s){bbox_ = s;}
	public boolean collision(int x, int y){return bbox_.collision(x, y);}
	public boolean collision(CollisionBox  b){return bbox_.collision(b);}
	
	public String nom() 
	{	
		return _nom;
	}
	public void nom(String n) { _nom=n;}
		
		
	
}
