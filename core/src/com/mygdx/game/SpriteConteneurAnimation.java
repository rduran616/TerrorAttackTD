package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import Utilitaires.TypeFlag;


/**
 * Contient les animation de chaque sprite
 * @author Florian
 *
 * En faite le sprite chargé est une TextureRegion 
 * -> cette classe la convertie en texture region
 */

public class SpriteConteneurAnimation extends SpriteConteneur
{
	private Animation[][] animation_;
	private float frame_time_;

	public SpriteConteneurAnimation(String file, String mainNode, String value, TypeFlag flag, int fram_col, int fram_row, float frame_time) 
	{
		super(file, mainNode, value, flag);
		
		TextureRegion[] region;
		int nb_anim= this.sprites().size;
		
		//tableau contenant l'animation de tout les sprites
		animation_ = new Animation[nb_anim][fram_row];
		frame_time_ =frame_time;
				
		
		for(int i =0; i<nb_anim;i++) //pour chaque sprite faire x animation
		{
			TextureRegion[][] tmp = TextureRegion.split(this.sprite(i).getTexture(), (int)(this.sprite(i).getWidth()/fram_col),(int)( this.sprite(i).getHeight()/fram_row));
			for(int j=0;j<fram_row;j++)//pour chaque animation faire
			{
				//init textureregion
				region = new  TextureRegion[fram_col];//creation d'un tableau de toutes les régions
				for(int k=0;k<fram_col;k++)
				{
					region[k]=tmp[j][k];
				}
				
				animation_[i][j] = new Animation(frame_time_,region);
			}
		}
		
	}
	
	public Animation[][] get_Animations(){return animation_;}
	public Animation[] get_Animations(int i){return animation_[i];}
	public Animation get_Animation(int i, int j){return animation_[i][j];}
	public float frame_Time_() {return frame_time_;}
	public void frame_Time_(float frame_time_) {this.frame_time_ = frame_time_;}

}
