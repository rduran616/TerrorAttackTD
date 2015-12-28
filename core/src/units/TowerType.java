package units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

import Utilitaires.CollisionBox;

import com.badlogic.gdx.physics.box2d.PolygonShape;
/*
 * classe mère de toutes les tours!
 * 
 * Gestion des images de chaque tour ainsi que des positions 
 */

public abstract class TowerType 
{
	private Vector2 position_;
	private CollisionBox  bbox_;
	
	
	int a = 0;
	private String _nom = "Tour Anti-Air";
	int _cout = 50;
	float _attspeed = 2;
	float _range = 5;
	int _damage = 10;
	boolean _air = true;
	
	//Status status_; //est positionnée ou n'est pas pas possitioner -> permettra de savoir si on doit la positioner ou pas
	
	private String texture_; //lien vers l'image
	private String bulletTexture_;
	public  Vector2 bulletSize_;
	
	public TowerType()
	{
		//status_ = Status.NON_POSITIONNE;
		position_= new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
		bbox_ = new CollisionBox();
	}
	
	
	public TowerType(String texture, String bulletTexture, Vector2 bulletSize, int x, int y)
	{
		//status_ = Status.NON_POSITIONNE;
		this.texture_ = texture; //lien vers l'image
		this.setBulletTexture(bulletTexture);
		this.bulletSize_ = bulletSize;
		position_= new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
	}
	
	 public String getTexture() 
	 {
	        return texture_;
	    }

	    public boolean onAttack(Mobs target, Tower turret) {
	        //ajoout du projectile
	        return true;
	    }

	    public void onImpact(Mobs target) {
	    	// a voir
	    }
	    
	    public void onExecute()
	    {
	    	// a faire
	    }

		public String getBulletTexture() {
			return bulletTexture_;
		}

		public void setBulletTexture(String bulletTexture) {
			this.bulletTexture_ = bulletTexture;
		}

		
		public Vector2 position(){return position_;}
		public void position(Vector2 position){position_=position;}
		public void position(float x, float y){position_=new Vector2(x,y);}
		public void position(int x, int y){position_=new Vector2(x,y);}

		public CollisionBox box(){return bbox_;}
		public void CollisionBox (CollisionBox  s){bbox_ = s;}
		
		public String nom() 
		{	
			return _nom;
		}
		public void nom(String n) 
		{	
			 _nom=n;
		}
		
		
	
}
