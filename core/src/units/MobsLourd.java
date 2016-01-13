package units;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import Utilitaires.CollisionBox;

public class MobsLourd extends Mobs{
	

	public MobsLourd(int vie, int vitesse, int money, int degat, int h, int w,int air,int num_txt, String nom, CollisionBox box )
	{
		super( vie,  vitesse,  money,  degat,  h,  w, air, num_txt,  nom,  box );
	}

	
	public MobsLourd(MobsLourd model) 
	{
		super(model);
	}
	

	@Override
	public void execute() 
	{
	}


	@Override
	public void destruction() {
		// TODO Auto-generated method stub
		
	}
}
