package units;

import Utilitaires.CollisionBox;

public class MobsAir extends Mobs
{
	public MobsAir(int vie, int vitesse, int money, int degat, int h, int w,int air,int num_txt, String nom, CollisionBox box )
	{
		super( vie,  vitesse,  money,  degat,  h,  w, air, num_txt,  nom,  box );
	}


	public MobsAir(MobsAir model) 
	{
		super(model);
	}


	@Override
	public void execute()
	{
		//System.err.println("execute air");
		
	}
}
