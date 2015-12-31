package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import units.TowerType;

public class TdJeu extends StateJeu
{

	GlobalValues values_;
	StateJeuEnum selection_;
	SpriteBatch sb_;
	
	public TdJeu()
	{
		//initialisation des variables
		selection_ = StateJeuEnum.JEU;
		values_ = GlobalValues.getInstance();
		
		sb_ = new SpriteBatch();
	}
	
	
	@Override
	public StateJeuEnum exectute() 
	{

		//mise à jour des ia
		
		
		//dessin des images
		values_.camera_Update();
		if(sb_!=null)
		{ 
			TowerType t;
			
			sb_.begin();
			sb_.setProjectionMatrix(values_.camera().combined);//mise à jour de la matrice de projection du batch pour redimentionnement des sprites
			//dessin des tours
			for(int i=0;i < values_.tower().size();i++)
			{
				try
				{
					t = values_.tower().get(i); //Recuperation de la tour
					
					if(values_.tower_sprite(i) == null)
						System.err.println("null");
					
					values_.tower_sprite(t.num_Texture()).setPosition(t.position().x,t.position().y);			
					values_.tower_sprite(t.num_Texture()).draw(sb_);
				}
				catch(Exception e)
				{
					System.err.println(e);
				}
			}
			sb_.end();
		}
		
		//mise à jour des projectiles
		
		
		//Changer de menu
		if(selection_ != StateJeuEnum.JEU)
		{
			StateJeuEnum tps = selection_;
			selection_ = StateJeuEnum.JEU;
			return  tps;
		}
		else
			return selection_;
	}

}
