package units;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Mobs {

	int _life;
	Sprite mobSprite;
	int _speed;
	Vector2 mat;
	Vector2 currentPosition;
	int _money;
	
	public Mobs(int life, Sprite sprite, int speed, Vector2 mat2, int money)
	{
		this._life=life;
		this.mobSprite=sprite;
		this._speed=speed;
		this.mat=mat2;
		this._money=money;
		
	}
	
	public void CreateBody(Vector2 position)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.type= BodyType.KinematicBody;
		bodyDef.position.set(position);
		mobSprite.setPosition(position.x, position.y);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("coucou");
		//MobsAir a = new MobsAir(100, 2, new Matrix3(), 10000);
	//	System.out.println(a._life);
	//	System.out.println(a.getSprite());
	}
}
