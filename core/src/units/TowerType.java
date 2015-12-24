package units;

import com.badlogic.gdx.math.Vector2;

public abstract class TowerType {
	
	/*
	 * classe m�re de toutes les tours!
	 * 
	 * Gestion des images de chaque tour ainsi que des positions 
	 * + mise en place dans un tableau de coordonn�es 
	 * 	-> hein? il sert � quoi ce tableau? 
	 */
	
	int a = 0;
	
	Status status_; //est positionn�e ou n'est pas pas possitioner -> permettra de savoir si on doit la positioner ou pas
	
	private String texture_; //lien vers l'image
	private String bulletTexture_;
	public  Vector2 bulletSize_;
	int[][] coords_; // a quoi il sert?
	
	public TowerType()
	{
		status_ = Status.NON_POSITIONNE;
	}
	
	
	public TowerType(String texture, String bulletTexture, Vector2 bulletSize, int x, int y)
	{
		status_ = Status.NON_POSITIONNE;
		this.texture_ = texture; //lien vers l'image
		this.setBulletTexture(bulletTexture);
		this.bulletSize_ = bulletSize;
		coords_[x][y]=1;
	}
	
	 public String getTexture() {
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

	
}
