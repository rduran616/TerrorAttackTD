package units;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Mobs
{

	private int _life;
	//Sprite mobSprite;
	private int _speed;
	private int _money;
	private int degat_;
	
	private Vector2 mat;
	private Vector2 currentPosition;

	//SpriteBatch batch;
    //Sprite sprite;
   // Texture img;
   // World world;
	private Body body;

	
	public Mobs(int vie, int vitesse, int money, int degat)
	{
		this._life=vie;
		//this.mobSprite=sprite;
		this._speed=vitesse;
		//this.mat=mat2;
		this._money=money;
		this.degat_ =degat;
		
	
		//batch = new SpriteBatch();
	   // img = new Texture("un fichier img de base qui ressemble a un petit personnage?? ^^ ");
	  //  mobSprite = new Sprite(img); // ici faudra adapter avec les sprite envoyés en parametre ou autrement
	   //
	   // sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2, Gdx.graphics.getHeight() / 2);
	   // world = new World(new Vector2(0, -98f), true);
	    // un mob centré sur la map, à tester

	 /*   BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyDef.BodyType.DynamicBody;
	    bodyDef.position.set(sprite.getX(), sprite.getY());*/
	
	    // pour l'afficher sur la map
	  //  body = world.createBody(bodyDef);
	
	    // paramétrage de la dimension de l'affichage
	/*    PolygonShape shape = new PolygonShape();
	    shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);
	    
	    // fixtdef c'est les propriétés physiques des images
	    FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = 1f;
	
	    Fixture fixture = body.createFixture(fixtureDef);
	
	    // Shape is the only disposable of the lot, so get rid of it
	    shape.dispose();*/
	}

}
