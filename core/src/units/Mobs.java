package units;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix3;

public class Mobs {

	int _life;
	Sprite sprite;
	int _speed;
	Matrix3 mat;
	int _money;
	
	public Mobs(int life, Sprite sprite, int speed, Matrix3 mat, int money)
	{
		this._life=life;
		this.sprite=sprite;
		this._speed=speed;
		this.mat=mat;
		this._money=money;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("coucou");
		//MobsAir a = new MobsAir(100, 2, new Matrix3(), 10000);
	//	System.out.println(a._life);
	//	System.out.println(a.getSprite());
	}
}
