package units;

import com.badlogic.gdx.math.Vector2;
import Utilitaires.CollisionBox;

public class TowerAir extends TowerType 
{
	public TowerAir(int cout, int att_speed,int damage, int range,int h, int w,int air,int n_txt, String nom, CollisionBox box )
	{
		super(cout,att_speed,damage,range,h,w,air,n_txt,nom,box);
	}
	
	/*public TowerAir(String texture, String bulletTexture, Vector2 bulletSize, int x, int y) 
	{
		super(texture, bulletTexture, bulletSize, x, y);
	}*/
	
	
	 public TowerAir(TowerAir another) 
	 {
		 this.nom(another.nom());
		 this._cout =  another._cout;
		 this._attspeed =  another._attspeed;
		 this._range =  another._range;
		 this._damage =  another._damage;
		 this._air =  another._air;
		 this.collision(another.box()); 	
		 this.size_H(another.size_H());		 	
		 this.size_W(another.size_W());				
		 this.num_Texture(another.num_Texture());	 
	 }
	 
	 public int cout(){return _cout;}

	 
	 //ia
	 
	 public  boolean onExecute()
	{
		
		
		return false;
	}
	 
	
}
