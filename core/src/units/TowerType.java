package units;

import com.badlogic.gdx.math.Vector2;

public abstract class TowerType {
	
	/*
	 * classe mère de toutes les tours!
	 * 
	 * Gestion des images de chaque tour ainsi que des positions 
	 * + mise en place dans un tableau de coordonnées
	 */
	
	int a = 0;
	
	
	private String texture; //lien vers l'image
	private String bulletTexture;
	public  Vector2 bulletSize;
	int[][] coords;
	
	public TowerType(){}
	
	
	public TowerType(String texture, String bulletTexture, Vector2 bulletSize, int x, int y)
	{
		this.texture = texture; //lien vers l'image
		this.setBulletTexture(bulletTexture);
		this.bulletSize = bulletSize;
		coords[x][y]=1;
	}
	
	 public String getTexture() {
	        return texture;
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
			return bulletTexture;
		}

		public void setBulletTexture(String bulletTexture) {
			this.bulletTexture = bulletTexture;
		}

	
}
