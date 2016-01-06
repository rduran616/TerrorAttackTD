package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import units.Mobs;
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

		//Création des mobs
		int num_vague;
		int rythme_creation_mobs;
		
		//mise à jour de l'ia
			//deplacement ennemis
			//rotation tour
			//tir des tours
			
			
		
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
						
					values_.tower_sprite(t.num_Texture()).setPosition(t.position().x,t.position().y);			
					values_.tower_sprite(t.num_Texture()).draw(sb_);
				}
				catch(Exception e)
				{
					System.err.println(e);
				}
			}
			
			//Dessin des mobs
			Mobs m;
			for(int i=0;i < values_.mobs().size();i++)
			{
				try
				{
					//Dessin du mob
					m = values_.mobs().get(i); //Recuperation du mob
					
					values_.mob_sprite(m.getNum_texture_()).setPosition(m.getPosition_().x,m.getPosition_().y);			
					values_.mob_sprite(m.getNum_texture_()).draw(sb_);
				}
				catch(Exception e)
				{
					System.err.println(e);
				}
			}

			//dessin des projectiles
			
			//play particules
			
			
			sb_.end();
		}

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
