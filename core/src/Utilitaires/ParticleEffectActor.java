package Utilitaires;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor 
{
	   ParticleEffect effect;

	   public ParticleEffectActor(ParticleEffect effect) {
	      this.effect = effect;
	   }

	   public void draw(SpriteBatch batch) {
	      effect.draw(batch); //define behavior when stage calls Actor.draw()
	   }

	   public void act(float delta, float x, float y) {
	      super.act(delta);
	      effect.setPosition(x, y); //set to whatever x/y you prefer
	      effect.update(delta); //update it
	                effect.start(); //need to start the particle spawning
	   }
	   
	   public void act(float delta, Vector2 pos) {
		      super.act(delta);
		      effect.setPosition(pos.x, pos.y); //set to whatever x/y you prefer
		      effect.update(delta); //update it
		                effect.start(); //need to start the particle spawning
		   }

	   public ParticleEffect getEffect() {
	      return effect;
	   }
	}
