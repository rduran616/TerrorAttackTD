package units;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

public class MobsAir extends Mobs
{
	
	private static Sprite sprite;

	public MobsAir(int life, int speed, int money, int degat) 
	{
		super(life,speed,money,degat);
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
