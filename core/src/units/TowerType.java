package units;

import com.badlogic.gdx.math.Vector2;

public class TowerType {
	
	private String texture; //lien vers l'image
	private String bulletTexture;
	public final Vector2 bulletSize;
	public String nom;
	int cout;
	float attspeed;
	float range;
	int damage;
	float dps;
	float dot;
	int slow;
	float zone;
	boolean air;
	
	public TowerType(String texture, String bulletTexture, Vector2 bulletSize, String nom, int cout, float attspeed,float range, int damage, float dps, float dot, int slow, float zone, boolean air)
	{
		this.texture = texture; //lien vers l'image
		this.bulletTexture = bulletTexture;
		this.bulletSize = bulletSize;
		this.nom = nom;
		this.cout = cout;
		this.attspeed=attspeed;
		this.range = range;
		this.damage = damage;
		this.dps = dps;
		this.dot = dot;
		this.slow = slow;
		this.zone = zone;
		this.air = air;
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
}
