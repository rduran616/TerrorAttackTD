package units;

import com.badlogic.gdx.math.Vector2;
import Utilitaires.CollisionBox;

public class TowerAir extends TowerType 
{


	public TowerAir(int cout, int att_speed,int damage, int range,int h, int w, String nom, CollisionBox box )
	{
		this.size_H(h);
		this.size_W(w);
		this.nom(nom);
		_cout = cout;
		_attspeed = att_speed;
		_range = range;
		_damage = damage;
		_air = true;
		this.CollisionBox(box);
		
	}
	
	public TowerAir(String texture, String bulletTexture, Vector2 bulletSize, int x, int y) 
	{
		super(texture, bulletTexture, bulletSize, x, y);
	}
	
	
	 public TowerAir(TowerAir another) 
	 {
		 this.nom(another.nom());
		 this._cout =  another._cout;
		 this._attspeed =  another._attspeed;
		 this._range =  another._range;
		 this._damage =  another._damage;
		 this._air =  another._air;
	 }
	 
	 public int cout(){return _cout;}
	 
	
}
