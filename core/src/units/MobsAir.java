package units;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

public class MobsAir extends Mobs{
	
	private static Sprite sprite;

	public MobsAir(int life, int speed, Vector2 mat, int money) {
		super(life, sprite, speed, mat, money);
		// TODO Auto-generated constructor stub
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
}
