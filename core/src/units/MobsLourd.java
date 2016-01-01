package units;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

public class MobsLourd extends Mobs{
	
	private static Sprite sprite;

	public MobsLourd(int life, int speed, int money, int degat) 
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
