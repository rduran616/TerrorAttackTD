package units;

import com.badlogic.gdx.math.Vector2;

public class TowerBase extends TowerType {

	
	public String _nom = "Tour Rapide";
	int _cout = 10;
	float _attspeed = 2;
	float _range = 2;
	int _damage = 6;
	float _dps; // inutile mais on laisse?
	float _dot;
	int _slow = 0; // 0 = pas de slow, 1 slow a 25%, 2 50% ... 4 arrêt total momentané à voir
	float _zone = 1;  //(petite = 1, s'améliore aussi?)
	boolean _air = true;
	
	public TowerBase(){}
	
	public TowerBase(String texture, String bulletTexture, Vector2 bulletSize, int x, int y) {
		super(texture, bulletTexture, bulletSize, x, y);
		// TODO Auto-generated constructor stub
	}
	
}