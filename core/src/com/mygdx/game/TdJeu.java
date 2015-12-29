package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
		//mise a jour de lamatrice de projection du batch pour redimentionnement des sprites
		sb_.setProjectionMatrix(values_.camera().projection);
		for(int i=0;i < values_.tower().size();i++)
		{
			TowerType t = values_.tower().get(i);
			if(t.nom().equals("air"))
			{
				if(sb_!=null)
				{ 
					
					sb_.begin();
					//if(t.position()!=null)
					values_.tower_sprite(0).setPosition(t.position().x,t.position().y);
					//t.box().show();
					values_.tower_sprite(0).draw(sb_);
					sb_.end();
				}
			}
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
