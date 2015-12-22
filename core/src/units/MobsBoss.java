package units;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix3;

public class MobsBoss extends Mobs{
	
	private static Sprite sprite;

	public MobsBoss(int life, int speed, Matrix3 mat, int money) {
		super(life, sprite, speed, mat, money);
		// TODO Auto-generated constructor stub
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
}
