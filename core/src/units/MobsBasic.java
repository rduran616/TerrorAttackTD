package units;



import Utilitaires.CollisionBox;

public class MobsBasic extends Mobs{
	


	public MobsBasic(int vie, int vitesse, int money, int degat, int h, int w,int air,int num_txt, String nom, CollisionBox box )
	{
		super( vie,  vitesse,  money,  degat,  h,  w, air, num_txt,  nom,  box );
	}

	public MobsBasic(MobsBasic model) 
	{
		super(model);
	}


	@Override
	public void execute() 
	{
		//System.err.println("execute basic");		
	}

	@Override
	public void destruction() {
		// TODO Auto-generated method stub
		
	}
}
