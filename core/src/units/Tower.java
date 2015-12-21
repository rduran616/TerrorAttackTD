package units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;



/*
 * pour le hud mais pour le moment osef ^^
 */







public class Tower extends Group implements EventListener {

	public static final int STATUT_VALIDER = 1;
	public static final int STATUT_ANNULER = 0;
	
	
	
	int statut;
	boolean clic = false; // pour la gestion des clics en passage true/false
	Image img; //une fois qu'on aura des images a gérer
	
	
	
	
	public Tower (int posX, int posY)
	{
		this.setPosition(posX, posY);
		
		// Pour l'image :
		Texture t = new Texture(Gdx.files.internal(""));
		TextureRegion region = new TextureRegion(t, 0, 0, 32, 32); // en fct de la taille de notre image tower
		img = new Image(region);
		img.setSize(32, 32); // a modifier en fct du dessus
		this.addActor(img);
		
		this.addListener(this); //permettra de récup les évents associés
		statut=STATUT_ANNULER;
		
	}
	
	
	public void setState(int statut)
	{
		statut=statut; //Ambigüe? mdr
	}
	
	@Override
	public void act(float delta)
	{
		switch (statut)
		{
			case STATUT_ANNULER : img.setVisible(false);
			break;
			case STATUT_VALIDER : img.setVisible(true);
			break;
		}
		super.act(delta);
	}
	@Override
	public boolean handle(Event event) {
		if(event != null){
		return true;}
		else{
		return false;}
		
		//gestion d'events de selection de tours, à voir
	}


}
