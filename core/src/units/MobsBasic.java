package units;



import Utilitaires.CollisionBox;

public class MobsBasic extends Mobs{
	


	public MobsBasic(int vie, int vitesse, int money, int degat, int h, int w,int air,int num_txt, String nom, CollisionBox box )
	{
		super( vie,  vitesse,  money,  degat,  h,  w, air, num_txt,  nom,  box );
	}



	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
