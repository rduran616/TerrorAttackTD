package units;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix3;

public class Mobs {

	int life;
	Sprite sprite;
	int speed;
	Matrix3 mat;
	int money;
	
	public Mobs(int life, Sprite sprite, int speed, Matrix3 mat)
	{
		this.life=life;
		this.sprite=sprite;
		this.speed=speed;
		this.mat=mat;
		this.money=money;
		
	}
}
