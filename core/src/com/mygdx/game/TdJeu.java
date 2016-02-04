package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import Utilitaires.AStar;
import Utilitaires.Circle;
import Utilitaires.CollisionBox;
import Utilitaires.Noeud;
import Utilitaires.StructureEnnemi;
import Utilitaires.TickHorloge;
import units.Mobs;
import units.MobsAir;
import units.MobsBasic;
import units.MobsBoss;
import units.MobsLourd;
import units.Status;
import units.TowerType;
import units.VagueRand;

public class TdJeu extends StateJeu
{

	GlobalValues values_;
	StateJeuEnum selection_;
	SpriteBatch sb_;
	int num_vague_=1;
	double rythme_creation_mobs_min = 500; //en msseconde
	double rythme_creation_mobs_max= 1000; //en msseconde
	VagueRand vague_;
	TickHorloge tick_;
	Noeud depart;
	Noeud arrivee;
	ArrayList<Noeud> chemin;
	
	ParticleEffect particle_effect_sang;
	ParticleEffect particle_effect_fumee;
	ArrayList<ParticleEffect> actor1;
	ArrayList<ParticleEffect> actor2;
	
	
	CollisionBox box_viewport;
	
	String simple_vertex_shader = null;
	String vertex_shader_color = null;
    String green_pixel_shader = null;
    String red_pixel_shader = null;
    String color_pixel_shader = null;
    String cartoon_pixel_shader = null;
    String bruit_pixel_shader = null;
    ShaderProgram shaderProgram_placement_ok = null;
    ShaderProgram shaderProgram_placement_ko = null;
    ShaderProgram color_shader = null;
    ShaderProgram cartoon_shader = null;
    ShaderProgram bruit_shader = null;
    
    Texture simple_texture;
    Texture draw_texture;
    Texture bruit_texture;
	
	public TdJeu()
	{	
		//init des particules
		particle_effect_sang = new ParticleEffect();
		particle_effect_fumee = new ParticleEffect();
		
		try
		{
			particle_effect_sang.load(Gdx.files.internal("particle/sang_particle"), Gdx.files.internal("particle_img")); 
			//files.internal loads from the "assets" folder
			particle_effect_fumee.load(Gdx.files.internal("particle/explosion.p"), Gdx.files.internal("particle_img"));
		}
		catch(Exception e)
		{
			FileHandle file_effect;
			FileHandle file_effect1;
			FileHandle file_img;
			
			if(Gdx.app.getType() == ApplicationType.Android)
			{
				file_effect = new FileHandle("particle/sang_particle");
				file_effect1 = new FileHandle("particle/explosion.p");
				file_img = new FileHandle("particle_img");
			}
			else
			{
				file_effect = new FileHandle("../android/assets/particle/sang_particle");
				file_effect1 = new FileHandle("../android/assets/particle/explosion.p");
				file_img = new FileHandle("../android/assets/particle_img");
			}
			
			particle_effect_sang.load(file_effect, file_img); 
			//files.internal loads from the "assets" folder
			particle_effect_fumee.load(file_effect1, file_img);
		}
		
		actor1 = new ArrayList<ParticleEffect>();
		actor2 = new ArrayList<ParticleEffect>();
		
		//initialisation des variables
		selection_ = StateJeuEnum.JEU;
		values_ = GlobalValues.getInstance();
		
		sb_ = new SpriteBatch();
		num_vague_=1;
		//rythme_creation_mobs_ =5000; //en msseconde
		
		tick_ = new TickHorloge(rythme_creation_mobs_min);
		
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
		
		//shaders
		simple_vertex_shader = Gdx.files.internal("shaders/vertexShaders/simpleVertex.glsl").readString();
		vertex_shader_color = Gdx.files.internal("shaders/vertexShaders/vertex.glsl").readString();
		
		green_pixel_shader = Gdx.files.internal("shaders/pixelShaders/green.glsl").readString();
        red_pixel_shader =  Gdx.files.internal("shaders/pixelShaders/red.glsl").readString();
        color_pixel_shader =  Gdx.files.internal("shaders/pixelShaders/color.glsl").readString();
        cartoon_pixel_shader =  Gdx.files.internal("shaders/pixelShaders/cartoonEffect.glsl").readString();
        bruit_pixel_shader =  Gdx.files.internal("shaders/pixelShaders/tvBruit.glsl").readString();
        
        shaderProgram_placement_ok = new ShaderProgram(simple_vertex_shader,green_pixel_shader);
        shaderProgram_placement_ko = new ShaderProgram(simple_vertex_shader,red_pixel_shader);   
        color_shader =  new ShaderProgram(vertex_shader_color,color_pixel_shader);
        cartoon_shader =  new ShaderProgram(simple_vertex_shader,cartoon_pixel_shader);
        if(cartoon_shader.isCompiled() == false)
        	System.err.println("Erreur shader compilation:cartoon_shader "+cartoon_shader.getLog());
        

        simple_texture = new Texture(Gdx.files.internal("simpleTexture.png"));
        draw_texture = new Texture(Gdx.files.internal("drawTexture.jpg"));
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
					if(values_.pile_mobs_air_.isEmpty()==true) //si non disponible le copier
					{
						//copie
						MobsAir mob0 = new MobsAir(values_.m_air_modele_());
						//placement dans la map
						mob0.setPosition_(position);
						mob0.index_chemin_= chemin.size()-1;
						//placement dans la liste
						//values_.mobs().add(mob);
						values_.carte()[depart.case_()].addMob(mob0);
						
					}

				break;
				
				//basic
				case 1:
					//verification de la disponibilit�
					if(values_.pile_mobs_base_.isEmpty()==true) //si non disponible le copier
					{
						//copie
						MobsBasic mob1 = new MobsBasic(values_.m_basic_modele_());
						//placement dans la map
						mob1.setPosition_(position);
						mob1.index_chemin_= chemin.size()-1;
						//placement dans la liste
						//values_.mobs().add(mob);
						values_.carte()[depart.case_()].addMob(mob1);
					}

				break;
				
				//lourd
				case 2:
					//verification de la disponibilit�
					if(values_.pile_mobs_boss_.isEmpty()==true) //si non disponible le copier
					{
						//copie
						MobsBoss mob2 = new MobsBoss(values_.m_boss_modele_());
						//placement dans la map
						mob2.setPosition_(position);
						mob2.index_chemin_= chemin.size()-1;
						//placement dans la liste
						//values_.mobs().add(mob);
						values_.carte()[depart.case_()].addMob(mob2);
					}

				break;
				
				//boss
				case 3:
					//verification de la disponibilit�
					if(values_.pile_mobs_lourd_.isEmpty() == true) //si non disponible le copier
					{
						//copie
						MobsLourd mob3 = new MobsLourd(values_.m_lourd_modele_());
						//placement dans la map
						mob3.setPosition_(position);
						mob3.index_chemin_= chemin.size()-1;
						//placement dans la liste
						//values_.mobs().add(mob);
						values_.carte()[depart.case_()].addMob(mob3);
					}
				break;
				
				default:
					System.err.println(":(");
					break;
			}
		}
		else
		{
			double tps  = rythme_creation_mobs_min + Math.random()%rythme_creation_mobs_max;
			tick_.range(tps);
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
		    		//try{
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
			    			values_.vie(values_.vie()-m.getDegat_());
			    				
			    			if(values_.vie()<=0)
			    				selection_ = StateJeuEnum.FIN;
			    			
							values_.carte()[i].remove_Mobs(j);
			    		}
			    	}
		    	}
		    }
		    
		    
	    /*********************************** Affichage ***************************************/
	  
		
		//dessin des images
		if(sb_!=null)
		{ 
			TowerType t;
			Mobs mob;
			
			sb_ = (SpriteBatch) values_.tiled_Map().getBatch();
			sb_.begin();
			sb_.setProjectionMatrix(values_.camera().combined);//mise � jour de la matrice de projection du batch pour redimentionnement des sprites
			
			if(box_viewport==null)
		    {
		    	Vector3 vec = new Vector3(values_.camera().position);
		    	values_.camera().unproject(vec);
		    	box_viewport = new CollisionBox(0,0,1,1);
		    }
		    else
		    {
		    	float zoom = MathUtils.clamp(values_.camera().zoom, 0.1f, (values_.size_m()*values_.size_n()*values_.size_Px())/values_.camera().viewportWidth);
	            float effectiveViewportWidth =  values_.camera().viewportWidth * zoom;
	            float effectiveViewportHeight =  values_.camera().viewportHeight *  zoom;
	            
	            int a = (int) values_.camera().position.x-(int)effectiveViewportWidth/2;
	            int b = (int) values_.camera().position.y-(int)effectiveViewportHeight/2;
	            
		    	box_viewport.set_Collision_box(a,b,effectiveViewportWidth,effectiveViewportHeight);
		    }

			
			if(values_.isShader_enable())
			{
				cartoon_shader.setUniformf("coef0", 0.7f);
				cartoon_shader.setUniformf("coef1", 0.9f);
				cartoon_shader.setUniformf("coef3", 0.8f);
				cartoon_shader.setUniformf("coef2", 30f);
				cartoon_shader.setUniformf("ligthness", 80f);
				cartoon_shader.setUniformf("brightness", 2.5f);
				cartoon_shader.setUniformf("iGlobalTime", Gdx.graphics.getDeltaTime());	
				sb_.setShader(cartoon_shader);
			}
			else
				sb_.setShader(null);
			
			//dessin des tours -> parcours toutes la carte n*m
			int cpt1=0;
			int cpt2=0;
			for(int i=0;i < values_.carte().length;i++)//pour chaque tour faire...
			{
				
				sb_.setShader(null);
				//try
				//{
					//pour chaque mob de la case faire...
					for(int k=0; k < values_.carte()[i].getMobs_size_();k++)
					{
						cpt1++;
						//Recuperation de la tour
						mob = values_.carte()[i].getMobs_().get(k); 

						if(mob.subir_Degat(0)==false)
						{
							values_.argent(values_.argent()+mob.getMoney_());
							values_.carte()[i].remove_Mobs(k);
							
							break;
						}

						//maj animation
						mob.add_Time(Gdx.graphics.getDeltaTime());
						
						mob.getBbox_().set_X((int)mob.getPosition_().x);
						mob.getBbox_().set_Y((int)mob.getPosition_().y);
						if(box_viewport.collision(mob.getBbox_())==true)
						{
							cpt2++;
							TextureRegion currentFrame = values_.mob_sprite_anime().get_Animation(mob.getNum_texture_(),mob.getNum_direction_()).getKeyFrame(mob.getTime_(), true);
							//dessin	
							sb_.draw(currentFrame,mob.getPosition_().x, mob.getPosition_().y);
						}
					}
					
					

					//System.err.println(box_viewport.get_X()+" "+box_viewport.get_Y()+" "+box_viewport.get_W()+" "+box_viewport.get_H());
					
					//pour chaque tour de la case faire...
					for(int j=0; j < values_.carte()[i].getUnits_().size();j++)
					{
						//Recuperation de la tour
						t = values_.carte()[i].getUnits_().get(j); 

						//tir + rotation
						StructureEnnemi str = t.onExecute(2f);
						if(str!=null)
						{
							if(values_.getPile_shot_().size()>0)
							{
								//recuperation
								Tir tir = values_.getPile_shot_().pop();
								//initialisation
								tir.init(str.degat_, str.position_tour_, str.vecteur_vitesse_, 0.000002f, str.time_);
								//lancement
								values_.shots().add(tir);
							}
							else
							{
								//duplication
								Tir tir = new Tir(values_.tir_Modele_());
								//parametrage
								Vector2 vitesse2 = new Vector2(1,1);
								tir.init(str.degat_, str.position_tour_, str.vecteur_vitesse_, 0.000002f, str.time_);
								//lancement
								values_.shots().add(tir);
							}
						}
						
						//dessin	
						if(box_viewport.collision(t.box())==true)
						{
							values_.tower_sprite(t.num_Texture()).setPosition(t.position().x,t.position().y);			
							values_.tower_sprite(t.num_Texture()).draw(sb_);
						}
					}	
			/*	}
				catch(Exception e)
				{
					System.err.println("tour dessin "+e);
				}*/
			}
					
			
		//	System.err.println("cpt = "+cpt2+"/"+cpt1);
			
			//Dessin des tir
			int size_shot = values_.shots().size();
			for(int a=0;a < size_shot;a++)
			{
				//recuperation du tir
				Tir tir = new Tir();
				tir = values_.shots().get(a);
				
				
				int c = values_.get_Index_Cellule((int)tir.position().x,(int)tir.position().y);
				if(c<0 || c>= values_.size_m()*values_.size_n())
					continue;

				ArrayList<Mobs> case_mob =values_.carte()[c].getMobs_();
				int existePlus = tir.onExectute(case_mob);
				if(existePlus >= 0)//on suprime
				{
					//emission de particle
					if(existePlus==0) //fum�e
					{
						actor2.add( new ParticleEffect(particle_effect_fumee));
						actor2.get(actor2.size()-1).getEmitters().first().setPosition(tir.position().x, tir.position().y);
						actor2.get(actor2.size()-1).start();
					}
					else//sang
					{
						actor1.add( new ParticleEffect(particle_effect_sang));
						actor1.get(actor1.size()-1).getEmitters().first().setPosition(tir.position().x, tir.position().y);
						actor1.get(actor1.size()-1).start();
					}
						
					values_.shots().remove(a);
					tir.time(0);
					values_.getPile_shot_().push(tir);
					break;
				}

				//deplacement
				//animation
								
				tir.add_Time(Gdx.graphics.getDeltaTime()*2);
				TextureRegion currentFrame2 = values_.shots_Sprite_().get_Animation(tir.num_Texture(),0).getKeyFrame(tir.time(), false);
				//placement + dessin	
				sb_.draw(currentFrame2,tir.position().x, tir.position().y);
			}
			
			
			//affichage de la tour en cours de placement
			t = values_.getT_temporaire_();
			if(t!=null && values_.status() != Status.INFO_UPGRADE)
			{
				int pas = 32;
				Vector2 c  = new Vector2(t.position().x+t.size_W()/2,t.position().y+t.size_H()/2);
				float[] verts;
				boolean col = false;
				col = values_.collision_Avec_Tour(t);
				Color color;
				if(col == true)
					color = new Color(0.8f,0f,0f,0.3f);
				else
					color = new Color(0.0f,0.8f,0f,0.3f);
				
				verts = Circle.make_Circle_Float_Color(t._range*values_.size_Px(), c, pas,color);
				Mesh mesh = new Mesh( true, pas+1, pas*3,
		                new VertexAttribute( VertexAttributes.Usage.Position, 3, "a_position" ),
		                new VertexAttribute( VertexAttributes.Usage.ColorPacked, 4, "a_color" ),
		                new VertexAttribute( VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoord0"));


				//set configuration pour le bind de la texture
				Gdx.gl.glEnable(GL20.GL_BLEND);
			    Gdx.gl.glBlendFunc(GL20.GL_BLEND_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
			    //Selection du shader color ( attention: le mesh a besoin d'une texture )
				sb_.setShader(color_shader);
				//creation du mesh
			    mesh.setVertices(verts);
			    mesh.setIndices(Circle.get_Indice(pas));
			    //collage de la texture
			    simple_texture.bind();
			    //rendu
			    mesh.render(color_shader, GL20.GL_TRIANGLES);
			    //desactivation
			    Gdx.gl.glDisable(GL20.GL_BLEND);
			    
			    //utilisation d'un autre shader
			    if(col == false)
			    	sb_.setShader(shaderProgram_placement_ok);
			    else
			    	sb_.setShader(shaderProgram_placement_ko);
			    
			    
			    if(values_ != null)
			    if(values_.tower_sprite() != null && t != null && t.position() != null)
			    {
				    values_.tower_sprite(t.num_Texture()).setPosition(t.position().x,t.position().y);			
					values_.tower_sprite(t.num_Texture()).draw(sb_);
					
			    }else{System.err.println("tower_sprite = null pointeur ");}
				
				sb_.setShader(null);
			}

			//dessin des particules
			sb_.setShader(null);
	      	for(int i=0;i < actor1.size();i++)
			{
				actor1.get(i).update(Gdx.graphics.getDeltaTime());
				actor1.get(i).draw(sb_);
				if (actor1.get(i).isComplete())
				{
					actor1.get(i).reset();
					actor1.remove(i);
				}
			}

			for(int i=0;i < actor2.size();i++)
			{
				actor2.get(i).update(Gdx.graphics.getDeltaTime());
				actor2.get(i).draw(sb_);
				if (actor2.get(i).isComplete())
				{
					actor2.get(i).reset();
					actor2.remove(i);
				}
			}
			
			
			sb_.setShader(null);
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
	
	
	public void init()
	{
		selection_ = StateJeuEnum.JEU;
	}

	private float[] square_Vertices(float width, float height, float x, float y) 
	{
		float[] verts = new float[20];
		int i = 0;

		verts[i++] = 0+x; // x1
		verts[i++] = 0+y; // y1
		verts[i++] = 0;
		verts[i++] = 1f; // u1
		verts[i++] = 1f; // v1

		verts[i++] = 0+x; // x2
		verts[i++] = height+y; // y2
		verts[i++] = 0;
		verts[i++] = 0f; // u2
		verts[i++] = 1f; // v2

		verts[i++] = width+x; // x3
		verts[i++] = height+y; // y2
		verts[i++] = 0;
		verts[i++] = 0f; // u3
		verts[i++] = 0f; // v3

		verts[i++] = width+x; // x4
		verts[i++] = 0 +y; // y4
		verts[i++] = 0;
		verts[i++] = 1f; // u4
		verts[i++] = 0f; // v4

		return verts;
	}

	private short[] square_Index()
	{
		short[] tab = {0,1,2,2,3,0};	
		return tab;
	}


	
	@Override
	public void load() {
		// TODO Auto-generated method stub

		
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}


}
