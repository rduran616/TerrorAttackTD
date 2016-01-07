package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import Utilitaires.TickHorloge;
import units.Mobs;
import units.MobsAir;
import units.MobsBasic;
import units.MobsBoss;
import units.MobsLourd;
import units.TowerType;
import units.VagueRand;

public class TdJeu extends StateJeu
{

	GlobalValues values_;
	StateJeuEnum selection_;
	SpriteBatch sb_;
	int num_vague_=1;
	double rythme_creation_mobs_ = 1; //en seconde
	VagueRand vague_;
	TickHorloge tick_;
	
	//temporaire, juste pour test
	float stateTime=0;
	
	public TdJeu()
	{
		//initialisation des variables
		selection_ = StateJeuEnum.JEU;
		values_ = GlobalValues.getInstance();
		
		sb_ = new SpriteBatch();
		num_vague_=1;
		rythme_creation_mobs_ =1000; //en msseconde
		
		tick_ = new TickHorloge(rythme_creation_mobs_);
		
		vague_ = VagueRand.get_Instance();
		
		int[] liste_mob = new int[4];
		liste_mob[0] = values_.m_air_modele_().getDegat_();
		liste_mob[1] = values_.m_basic_modele_().getDegat_();
		liste_mob[2] = values_.m_lourd_modele_().getDegat_();
		liste_mob[3] = values_.m_boss_modele_().getDegat_();
		
		vague_.init(200,liste_mob);
	}
	
	
	@Override
	public StateJeuEnum exectute() 
	{
		//Création des mobs
		if(vague_.nb_Ennemis()<=0)
			vague_.new_Vague();
		
		//si on est dans le bon temps on peut creer un ennemi
		if(tick_.tick())
		{
			//choix de l'ennemi
			int m = vague_.get_Ennemi();
			
			//calcul position de départ
			Vector2  position = new Vector2(values_.get_width()/2,values_.get_height()/2);
			//creation et placement		
			System.err.println(values_.pile_Mobs_().isEmpty()+"  "+m);
			switch(m)
			{
				//air
				case 0:
					//verification de la disponibilité
					if(values_.pile_Mobs_().isEmpty()==true) //si non disponible le copier
					{
						//copie
						MobsAir mob = new MobsAir(values_.m_air_modele_());
						//placement dans la map
						mob.setPosition_(position);
						//placement dans la liste
						values_.mobs().add(mob);
					}
					else //sinon le prendre de la file
					{
						//dépile
						//init
						//placement dans la map
						//placement dans la liste
					}
					
				break;
				
				//basic
				case 1:
					//verification de la disponibilité
					if(values_.pile_Mobs_().isEmpty()==true) //si non disponible le copier
					{
						//copie
						MobsBasic mob = new MobsBasic(values_.m_basic_modele_());
						//placement dans la map
						mob.setPosition_(position);
						//placement dans la liste
						values_.mobs().add(mob);
					}
					else //sinon le prendre de la file
					{
						//dépile
						//init
						//placement dans la map
						//placement dans la liste
					}
				break;
				
				//lourd
				case 2:
					//verification de la disponibilité
					if(values_.pile_Mobs_().isEmpty()==true) //si non disponible le copier
					{
						//copie
						MobsBoss mob = new MobsBoss(values_.m_boss_modele_());
						//placement dans la map
						mob.setPosition_(position);
						//placement dans la liste
						values_.mobs().add(mob);
					}
					else //sinon le prendre de la file
					{
						//dépile
						//init
						//placement dans la map
						//placement dans la liste
					}
				break;
				
				//boss
				case 3:
					//verification de la disponibilité
					if(values_.pile_Mobs_().isEmpty() == true) //si non disponible le copier
					{
						//copie
						MobsLourd mob = new MobsLourd(values_.m_lourd_modele_());
						//placement dans la map
						mob.setPosition_(position);
						//placement dans la liste
						values_.mobs().add(mob);
					}
					else //sinon le prendre de la file
					{
						//dépile
						//init
						//placement dans la map
						//placement dans la liste
					}
				break;
				
				default:
					System.err.println(":(");
					break;
			}
			
		}
		
		
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
					System.err.println("tour dessin "+e);
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
					//animation
					stateTime += Gdx.graphics.getDeltaTime();  
					TextureRegion currentFrame = values_.mob_sprite_anime().get_Animation(i,0).getKeyFrame(stateTime, true);
					//placement + dessin	
					sb_.draw(currentFrame,m.getPosition_().x, m.getPosition_().y);
				}
				catch(Exception e)
				{
					System.err.println("mob dessin "+e);
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
