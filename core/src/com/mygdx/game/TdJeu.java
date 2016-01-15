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
		
		/*********************cr�taion de l'ia***************************************/
		
		//Cr�ation des mobs
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
			
			//calcul position de d�part
			Vector2  position = new Vector2(values_.carte()[depart.case_()].centre());
			//creation et placement		
			switch(m)
			{
				//air
				case 0:
					//verification de la disponibilit�
					if(values_.pile_Mobs_().isEmpty()==true) //si non disponible le copier
					{
						//copie
						MobsAir mob = new MobsAir(values_.m_air_modele_());
						//placement dans la map
						mob.setPosition_(position);
						mob.index_chemin_= chemin.size()-1;
						//placement dans la liste
						//values_.mobs().add(mob);
						values_.carte()[depart.case_()].addMob(mob);
						
					}
					else //sinon le prendre de la file
					{
						//d�pile
						//init
						//placement dans la map
						//placement dans la liste
					}
					
				break;
				
				//basic
				case 1:
					//verification de la disponibilit�
					if(values_.pile_Mobs_().isEmpty()==true) //si non disponible le copier
					{
						//copie
						MobsBasic mob = new MobsBasic(values_.m_basic_modele_());
						//placement dans la map
						mob.setPosition_(position);
						mob.index_chemin_= chemin.size()-1;
						//placement dans la liste
						//values_.mobs().add(mob);
						values_.carte()[depart.case_()].addMob(mob);
					}
					else //sinon le prendre de la file
					{
						//d�pile
						//init
						//placement dans la map
						//placement dans la liste
					}
				break;
				
				//lourd
				case 2:
					//verification de la disponibilit�
					if(values_.pile_Mobs_().isEmpty()==true) //si non disponible le copier
					{
						//copie
						MobsBoss mob = new MobsBoss(values_.m_boss_modele_());
						//placement dans la map
						mob.setPosition_(position);
						mob.index_chemin_= chemin.size()-1;
						//placement dans la liste
						//values_.mobs().add(mob);
						values_.carte()[depart.case_()].addMob(mob);
					}
					else //sinon le prendre de la file
					{
						//d�pile
						//init
						//placement dans la map
						//placement dans la liste
					}
				break;
				
				//boss
				case 3:
					//verification de la disponibilit�
					if(values_.pile_Mobs_().isEmpty() == true) //si non disponible le copier
					{
						//copie
						MobsLourd mob = new MobsLourd(values_.m_lourd_modele_());
						//placement dans la map
						mob.setPosition_(position);
						mob.index_chemin_= chemin.size()-1;
						//placement dans la liste
						//values_.mobs().add(mob);
						values_.carte()[depart.case_()].addMob(mob);
					}
					else //sinon le prendre de la file
					{
						//d�pile
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
		
		
		/***********************************mise � jour de l'ia***************************************/
		
			//Recalcul du chemin des mobs
			if(values_.recalculerChemin_()==true)
			{
				AStar.init_CellMap(values_.carte());
				AStar.cheminPlusCourt(values_.carte(), depart, arrivee, values_.size_m(),values_.size_n());
				values_.recalculerChemin_(false);
			}
				
			//ia ennemis = deplacement en suivant le chemin calcul� ou recalcul� en focntion du placement des tours
		    for(int i =0; i< values_.carte().length;i++) //pour chaque mob dans mob[] faire
		    {
		    	for(int j = 0; j <values_.carte()[i].getMobs_size_();j++ )
		    	{
			    	Mobs m = values_.carte()[i].getMobs_().get(j);
			    	Vector2 position = new Vector2();
			    	
			    	//position:
	
			    	//Case actu
			    	int index = m.index_chemin_-1;
			    	if(index == chemin.size())
			    		index --;
			    	//case suivante
			    	int case_suivante=chemin.get(index).case_();
			    	//position actuel
			    	Vector2 pos = m.getPosition_();
			    	//position cible
			    	Vector2 pos2 =  values_.carte()[case_suivante].centre();
			    	//vecteur de d�placement 
			    	Vector2 pos3 = new Vector2(pos2.x - pos.x, pos2.y - pos.y);
			    	//deplacement avec application de la vitesse
			    	position.x=(pos.x+pos3.x/m.getSpeed_()*Gdx.graphics.getDeltaTime());
			    	position.y=(pos.y+pos3.y/m.getSpeed_()*Gdx.graphics.getDeltaTime());
			    	
			    	//mise a jour position
			    	m.setNum_direction_(0);
			    	m.setPosition_(position);

			    	//enregsitrement sur la carte
			    	int cell = values_.get_Index_Cellule((int)pos.x, (int)pos.y);
			    	if(values_.get_Index_Cellule((int)position.x, (int)position.y) == case_suivante) // si on change de case
			    	{
			    		if(values_.get_Index_Cellule((int)position.x, (int)position.y) != arrivee.case_()) //si pas arriv�
			    		{	    			
			    			m.index_chemin_--;
					    	values_.carte()[cell].getMobs_().remove(m);
					    	values_.carte()[cell].setMobs_size_(values_.carte()[cell].getMobs_size_()-1);
					    	values_.carte()[case_suivante].addMob(m);
			    		}
			    		else // on est arriv�
			    		{
			    			//si arriv� destructionp
			    			//m.execute();
			    		}
			    	}
		    	}
		    }
		    
		    
	    /*********************************** Affichage ***************************************/
			
		
		//dessin des images
		values_.camera_Update();
		if(sb_!=null)
		{ 
			TowerType t;
			Mobs mob;
			
			sb_.begin();
			sb_.setProjectionMatrix(values_.camera().combined);//mise � jour de la matrice de projection du batch pour redimentionnement des sprites

						
			//dessin des tours -> parcours toutes la carte n*m
			for(int i=0;i < values_.carte().length;i++)//pour chaque tour faire...
			{
			//	try
			//	{
					//pour chaque mob de la case faire...
					for(int k=0; k < values_.carte()[i].getMobs_size_();k++)
					{
						//Recuperation de la tour
						mob = values_.carte()[i].getMobs_().get(k); 
						//dessin	
						mob.add_Time(Gdx.graphics.getDeltaTime());
						TextureRegion currentFrame = values_.mob_sprite_anime().get_Animation(mob.getNum_texture_(),mob.getNum_direction_()).getKeyFrame(mob.getTime_(), true);
						//dessin	
						sb_.draw(currentFrame,mob.getPosition_().x, mob.getPosition_().y);
					}
					
					//pour chaque tour de la case faire...
					for(int j=0; j < values_.carte()[i].getUnits_().size();j++)
					{
						//Recuperation de la tour
						t = values_.carte()[i].getUnits_().get(j); 
						//tir + rotation
						t.onExecute();
						//dessin	
						values_.tower_sprite(t.num_Texture()).setPosition(t.position().x,t.position().y);			
						values_.tower_sprite(t.num_Texture()).draw(sb_);
					}	
				/*}
				catch(Exception e)
				{
					System.err.println("tour dessin "+e);
				}*/
			}
						
			//Dessin des tir
			for(int i=0;i < values_.shots().size();i++)
			{
				//System.err.println("mob size = "+values_.mobs().size());
				try
				{
					//recuperation du mob
					Tir s = values_.shots().get(i); //Recuperation du mob
					//animation
					s.add_Time(Gdx.graphics.getDeltaTime());
					TextureRegion currentFrame = values_.mob_sprite_anime().get_Animation(s.num_Texture(),0).getKeyFrame(s.time(), true);
					//placement + dessin	
					sb_.draw(currentFrame,s.position().x, s.position().y);
				}
				catch(Exception e)
				{
					System.err.println("tir dessin "+e);
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
