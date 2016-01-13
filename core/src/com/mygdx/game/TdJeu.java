package com.mygdx.game;

import java.util.ArrayList;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import Utilitaires.AStar;
import Utilitaires.Noeud;
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
	double rythme_creation_mobs_ = 1000; //en msseconde
	VagueRand vague_;
	TickHorloge tick_;
	Noeud depart;
	Noeud arrivee;
	ArrayList<Noeud> chemin;

		
	public TdJeu()
	{
		//initialisation des variables
		selection_ = StateJeuEnum.JEU;
		values_ = GlobalValues.getInstance();
		
		sb_ = new SpriteBatch();
		num_vague_=1;
		rythme_creation_mobs_ =10000; //en msseconde
		
		tick_ = new TickHorloge(rythme_creation_mobs_);
		
		vague_ = VagueRand.get_Instance();
		
		int[] liste_mob = new int[4];
		liste_mob[0] = values_.m_air_modele_().getDegat_();
		liste_mob[1] = values_.m_basic_modele_().getDegat_();
		liste_mob[2] = values_.m_lourd_modele_().getDegat_();
		liste_mob[3] = values_.m_boss_modele_().getDegat_();
		
		vague_.init(200,liste_mob);
		values_.recalculerChemin_(true);
		
		//Init chemin
		depart = new Noeud();
		depart.set_Case(340);
		arrivee= new Noeud();
		arrivee.set_Case(320);
		//tracer chemin
	//	chemin = AStar.cheminPlusCourt(values_.carte(), depart, arrivee, values_.size_m(), values_.size_n());
		values_.recalculerChemin_(true);
	}
	
	
	@Override
	public StateJeuEnum exectute() 
	{
		
		/*********************crétaion de l'ia***************************************/
		
		//Création des mobs
		if(vague_.nb_Ennemis()<=0)
			vague_.new_Vague();
		
		if(values_.recalculerChemin_()==true)
		{	
			chemin = AStar.cheminPlusCourt(values_.carte(), depart, arrivee, values_.size_m(), values_.size_n());
			values_.recalculerChemin_(false);
		}
		
				
		//si on est dans le bon temps on peut creer un ennemi
		if(tick_.tick())
		{
			//choix de l'ennemi
			int m = vague_.get_Ennemi();
			
			//calcul position de départ
			Vector2  position = new Vector2(values_.carte()[depart.case_()].centre());
			//creation et placement		
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
						mob.index_chemin_= chemin.size()-1;
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
						mob.index_chemin_= chemin.size()-1;
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
						mob.index_chemin_= chemin.size()-1;
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
						mob.index_chemin_= chemin.size()-1;
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
		
		
		/***********************************mise à jour de l'ia***************************************/
		
			//Recalcul du chemin des mobs
			if(values_.recalculerChemin_()==true)
			{
				AStar.init_CellMap(values_.carte());
				AStar.cheminPlusCourt(values_.carte(), depart, arrivee, values_.size_m(),values_.size_n());
				values_.recalculerChemin_(false);
			}
				
			//ia ennemis = deplacement en suivant le chemin calculé ou recalculé en focntion du placement des tours
		    for(int i =0; i< values_.mobs().size();i++) 
		    {
		    	Mobs m = values_.mobs().get(i);
		    	Vector2 position = new Vector2();
		    	
		    	//position:

		    	//Case suivante
		    	int index = m.index_chemin_-1;
		    	if(index == chemin.size())
		    		index --;
		    	
		    	int case_suivante=chemin.get(index).case_();
		    	//position actuel
		    	Vector2 pos = m.getPosition_();
		    	//position cible
		    	Vector2 pos2 =  values_.carte()[case_suivante].centre();
		    	//vecteur de déplacement 
		    	Vector2 pos3 = new Vector2(pos2.x - pos.x, pos2.y - pos.y);
		    	//deplacement avec application de la vitesse
		    	position.x=(pos.x+pos3.x/m.getSpeed_()*Gdx.graphics.getDeltaTime());
		    	position.y=(pos.y+pos3.y/m.getSpeed_()*Gdx.graphics.getDeltaTime());
		    	
		    	//System.err.println("actu = "+pos+" cible ="+pos2+" deplacement= "+pos3+"  nouvelle coord ="+position);
		    	//System.err.println(position.x+ "   "+position.y);
		    	
		    	//mise a jour position*/
		    	values_.mobs().get(i).setNum_direction_(0);
		    	values_.mobs().get(i).setPosition_(position);
		    	
		    	if(values_.get_Index_Cellule((int)position.x, (int)position.y) == case_suivante)
		    	{
		    		if(values_.get_Index_Cellule((int)position.x, (int)position.y) != arrivee.case_())
		    		{
		    			m.index_chemin_--;
		    		}
		    		else // on est arrivé
		    		{
		    			//si arrivé destructionp
		    			//m.execute();
		    		}
		    	}

		    	
		    }
		    
		    
	    /*********************************** Affichage ***************************************/
			
		
		//dessin des images
		values_.camera_Update();
		if(sb_!=null)
		{ 
			TowerType t;
			
			sb_.begin();
			sb_.setProjectionMatrix(values_.camera().combined);//mise à jour de la matrice de projection du batch pour redimentionnement des sprites

						
			//dessin des tours -> parcours toutes la carte n*m
			for(int i=0;i < values_.carte().length;i++)//pour chaque tour faire...
			{
				try
				{
					//pour chaque tour de la case faire...
					for(int j=0; j < values_.carte()[i].getUnits_().size();j++)
					{
						//Recuperation de la tour
						t = values_.carte()[i].getUnits_().get(j); 
						//tir + rotation
				/*		int[] adj = t.adjacente(); //recuperation des cases adjacentes visibles par la tour
						for(int k =0; k < adj.length; k++)
						{
							boolean test = t.onExecute(values_.carte()[adj[k]].getMobs_());
							if(test == true) //si on a tirer dans cette case -> on sort
								break;
							
						}*/
						//dessin	
						values_.tower_sprite(t.num_Texture()).setPosition(t.position().x,t.position().y);			
						values_.tower_sprite(t.num_Texture()).draw(sb_);
					}
				}
				catch(Exception e)
				{
					System.err.println("tour dessin "+e);
				}
			}
			
			//Dessin des mobs
			for(int i=0;i < values_.mobs().size();i++)
			{
				//System.err.println("mob size = "+values_.mobs().size());
				try
				{
					//recuperation du mob
					Mobs m = values_.mobs().get(i); //Recuperation du mob
					//animation
					m.add_Time(Gdx.graphics.getDeltaTime());
					TextureRegion currentFrame = values_.mob_sprite_anime().get_Animation(m.getNum_texture_(),m.getNum_direction_()).getKeyFrame(m.getTime_(), true);
					//placement + dessin	
					sb_.draw(currentFrame,m.getPosition_().x, m.getPosition_().y);
				}
				catch(Exception e)
				{
					System.err.println("mob dessin "+e);
				}
			}
			
			
			//affichage de la tour en cours de placement
			t = values_.getT_temporaire_();
			if(t!=null)
			{
				values_.tower_sprite(t.num_Texture()).setPosition(t.position().x,t.position().y);			
				values_.tower_sprite(t.num_Texture()).draw(sb_);
			}
			

			//dessin des projectiles
			
			//dessin des particules
			
			
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
