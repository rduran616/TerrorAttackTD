package units;

import com.badlogic.gdx.math.Vector2;

import Utilitaires.CollisionBox;

public class TowerZone extends TowerType 
{
	public TowerZone(){}
	
	public TowerZone(int cout, int att_speed,int damage, int range,int h, int w,int air,int n_txt, String nom, CollisionBox box )
	{
		super(cout,att_speed,damage,range,h,w,air,n_txt,nom,box);
	}
	
	/*public TowerZone(String texture, String bulletTexture, Vector2 bulletSize, int x, int y) {
		super(texture, bulletTexture, bulletSize, x, y);
		// TODO Auto-generated constructor stub
	}*/

	@Override
	public void onExecute() {
		// TODO Auto-generated method stub
		
	}
	
}