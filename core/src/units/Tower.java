package units;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;




public class Tower extends ApplicationAdapter implements InputProcessor {

	public static final int STATUT_VALIDER = 1;
	public static final int STATUT_ANNULER = 0;
	
	
	Texture img;
    TiledMap tiledMap;
    //OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    SpriteBatch sb;
    Texture texture;
    Sprite sprite;
	int statut;
	boolean clic = false; // pour la gestion des clics en passage true/false
	
	
	 @Override public void create () {
	        float w = Gdx.graphics.getWidth();
	        float h = Gdx.graphics.getHeight();

	        //camera = new OrthographicCamera();
	       // camera.setToOrtho(false,w,h);
	       // camera.update();
	        tiledMap = new TmxMapLoader().load("faut trouver ici un tiledmap lol");
	        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	        Gdx.input.setInputProcessor(this);

	        sb = new SpriteBatch();
	        texture = new Texture(Gdx.files.internal("pik.png"));
	        sprite = new Sprite(texture);
	    }

	 public void render () {
		    Gdx.gl.glClearColor(1, 0, 0, 1);
		    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		 //   camera.update();
		 //   tiledMapRenderer.setView(camera);
		  //  tiledMapRenderer.render();
		 //   sb.setProjectionMatrix(camera.combined);
		    sb.begin();
		    sprite.draw(sb);
		    sb.end();
		}

	
	public boolean handle(Event event) {
		if(event != null){
		return true;}
		else{
		return false;}
		
		//gestion d'events de selection de tours, à voir
	}
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	    Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
	 //   Vector3 position = camera.unproject(clickCoordinates);
	//    sprite.setPosition(position.x, position.y);
	    return true;
	}


	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}


}
