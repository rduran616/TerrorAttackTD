package com.mygdx.game;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import Utilitaires.CollisionBox;
import Utilitaires.FingerTransformMap;
import Utilitaires.TickHorloge;
import units.Status;
import units.TowerType;


public class Jeu extends StateMenu  implements InputProcessor
{ 
	//multiplexer de stage -> avoir plusieur stage en 1 ( interaction monde et hud)
	InputMultiplexer multiplexer = new InputMultiplexer();

	//var global
	GlobalValues values_;		
	StateMEnuEnum selection_;
	static Vector3 last_position_;
	
	//gestion etat du jeu
	StateJeu jeu_[];
	StateJeuEnum etat_jeu_;
	boolean touche_activer_;
	TickHorloge tick_;			//gestion des ticks pour attente passive

	//Zoom
	FingerTransformMap finger;
	
	//HUD
	
	//hud jeu
	HudGame hud_game_;
	
	//hud pause
	
	//hud intro
	
	//hud fin
	
	
	String simple_vertex_shader = null;
    String cartoon_pixel_shader = null;
    ShaderProgram cartoon_shader = null;
    
    Texture simple_texture;
    Texture draw_texture;
    Texture tv_texture;
    
    
    ShapeRenderer shapeRenderer;
	
	public Jeu()
	{
		simple_vertex_shader = Gdx.files.internal("shaders/vertexShaders/simpleVertex.glsl").readString();
		cartoon_pixel_shader =  Gdx.files.internal("shaders/pixelShaders/cartoonEffect.glsl").readString();
		cartoon_shader =  new ShaderProgram(simple_vertex_shader,cartoon_pixel_shader);
		tv_texture = new Texture(Gdx.files.internal("StaticSnow.png"));
		
		shapeRenderer = new ShapeRenderer();
		
		//initialisation des variables
		selection_ = StateMEnuEnum.JEU;
		values_ = GlobalValues.getInstance();
		last_position_ = new Vector3(-1,-1,0);
		
		//initialisation du jeu
		etat_jeu_ = StateJeuEnum.CHOIX;
		jeu_ = new StateJeu[5];
		touche_activer_ = false;
		
		if(jeu_ != null)
		{
			jeu_[0] = new TdChoixNiveau();
			jeu_[1] = new TdIntro();
			jeu_[2] = new TdPause();
			jeu_[3] = new TdJeu();
			jeu_[4] = new TdFin();
		}
		
		//initialisation des huds
		
		hud_game_ = new HudGame(10);
		multiplexer.addProcessor(hud_game_.stage());
		multiplexer.addProcessor(this);
		
		//boutton retour
		Gdx.input.setCatchBackKey(true);
		
		tick_ = new TickHorloge(30); //30fps max
		finger = new FingerTransformMap(0.01);
		finger.set_Range( values_.get_width()/2,values_.size_n()*values_.size_Px() - values_.get_width()/2, values_.get_height()/2,values_.size_m()*values_.size_Px() - values_.get_height()/2,10,10);
		
		//initialisation du jeu
		init();
	}

	@Override
	public StateMEnuEnum changer_Etat() 
	{
		
		Gdx.input.setCatchBackKey(true);

		//dessin du jeu
		if(etat_jeu_ == StateJeuEnum.JEU || etat_jeu_ == StateJeuEnum.PAUSE)
		{
			
			if(values_.isShader_enable())
				values_.tiled_Map().getBatch().setShader(cartoon_shader);
			else
				values_.tiled_Map().getBatch().setShader(null);
			
			//dessin de la carte
			if(values_.camera_Update() != ErrorEnum.OK)
			{	
				etat_jeu_ = StateJeuEnum.CHOIX;
				selection_ = StateMEnuEnum.JEU;
				System.err.println("Erreur camera update");
			}
			
			if(values_.tiled_Map_View()!= ErrorEnum.OK)
			{	
				etat_jeu_ = StateJeuEnum.CHOIX;
				selection_ = StateMEnuEnum.JEU;
				System.err.println("Erreur rendu carte");
			}
			
			if(values_.tiled_Map_Render()!= ErrorEnum.OK)
			{	
				etat_jeu_ = StateJeuEnum.CHOIX;
				selection_ = StateMEnuEnum.JEU;
				System.err.println("Erreur rendu carte");
			}

			
			if(values_.debug==true)
				draw_shape();
			
			
	        //dessins du reste
			 if(etat_jeu_ == StateJeuEnum.JEU )
			 {
				//dessin uid
				 hud_game_.argent();
				 hud_game_.vie();
			 }
			 else if(etat_jeu_ == StateJeuEnum.PAUSE)
			 {
				//dessin uid
			 }
			 else if(etat_jeu_ == StateJeuEnum.INTRO)
			 {
				//dessin uid
			 }
			 else if(etat_jeu_ == StateJeuEnum.FIN)
			 {
				 init();
			 }
			 
			 //Activation touche si pas deja fait
			 if(touche_activer_ == false)
			 {
				 Gdx.input.setInputProcessor(multiplexer);
				 touche_activer_ = true;
			 }
			 
		}
		else if(etat_jeu_ == StateJeuEnum.RETOUR)
		{
			etat_jeu_  = StateJeuEnum.CHOIX;
			selection_ = StateMEnuEnum.MENU;
		}

		// choix de l'etat et action en fonction de l'état du jeu
		 if(etat_jeu_ == StateJeuEnum.PAUSE )
		 {
			 etat_jeu_ = jeu_[etat_jeu_.ordinal()].exectute(); //execute fais les mise à jour des ia + dessin
			 if(etat_jeu_ == StateJeuEnum.JEU)
			 {
				 hud_game_.setEtat_jeu_(StateJeuEnum.JEU);
				 Gdx.input.setInputProcessor(multiplexer);
			 }
			 else if(etat_jeu_ == StateJeuEnum.RETOUR)
			 {
				 hud_game_.setEtat_jeu_(StateJeuEnum.JEU);
			 }
		 }
		 else
			 etat_jeu_ = jeu_[etat_jeu_.ordinal()].exectute(); //execute fais les mise à jour des ia + dessin
		 
		 
		 
		 
		 if(etat_jeu_ == StateJeuEnum.JEU )
		 {
			//dessin uid
			 hud_game_.argent();
			 hud_game_.vie();
			 
			 if(values_.vie()==0)
				 etat_jeu_ = StateJeuEnum.FIN;
			 
			 if(hud_game_.getEtat_jeu_() == StateJeuEnum.PAUSE)
				 etat_jeu_ = StateJeuEnum.PAUSE;
			 
			 hud_game_.stage().draw();
		 }

		
		//Changer de menu
		if(selection_ != StateMEnuEnum.JEU)
		{
			StateMEnuEnum tps = selection_;
			selection_ = StateMEnuEnum.JEU;
			touche_activer_ = false;
			return  tps;
		}
		else
			return selection_;
	}
	
	
	public void draw_shape()
	{
		shapeRenderer.setProjectionMatrix(values_.camera().combined);
		int length = values_.carte().length;
		for(int i=0; i < length;i++) //pour chaque case
		{
			//tracé les carreaux
				shapeRenderer.begin(ShapeType.Line);
				Color c = Color.RED;
	            shapeRenderer.setColor(c);
	            int a = (int)values_.carte()[i].getPosition().x* values_.size_Px() + 0;
	            int b = (int)values_.carte()[i].getPosition().y* values_.size_Px() + 0;
	            shapeRenderer.rect(a,b,values_.size_Px(),values_.size_Px());
	            shapeRenderer.end();            
			}
			
			
			shapeRenderer.begin(ShapeType.Line);
			Color c = Color.BLUE;
            shapeRenderer.setColor(c);

            values_.camera().zoom = MathUtils.clamp(values_.camera().zoom, 0.1f, (values_.size_m()*values_.size_n()*values_.size_Px())/values_.camera().viewportWidth);
            float effectiveViewportWidth =  values_.camera().viewportWidth *  values_.camera().zoom;
            float effectiveViewportHeight =  values_.camera().viewportHeight *  values_.camera().zoom;
 
            int a = (int) values_.camera().position.x-(int)effectiveViewportWidth/2;
            int b = (int) values_.camera().position.y-(int)effectiveViewportHeight/2;
            shapeRenderer.rect(a+32,b+32,effectiveViewportWidth-64,effectiveViewportHeight-64);
            shapeRenderer.end();   
	}
	
	public static Vector3 get_Last_Position(){return last_position_;}

	@Override
	public boolean keyDown(int keycode) 
	{
		if(keycode == Keys.BACK)
		{
			//System.err.println("test");
			etat_jeu_ = StateJeuEnum.CHOIX;
			selection_ = StateMEnuEnum.MENU;
	    }
		return false;
	}

	
	//only desktop
	@Override
	public boolean keyUp(int keycode) 
	{
		if(keycode == Input.Keys.LEFT)
            values_.camera_Translate(-32,0);
        if(keycode == Input.Keys.RIGHT)
        	values_.camera_Translate(32,0);
        if(keycode == Input.Keys.UP)
        	values_.camera_Translate(0,32);
        if(keycode == Input.Keys.DOWN)
        	values_.camera_Translate(0,-32);
        
        return false;
	}

	//only desktop
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) 
	{		
		finger.finger_Touch(screenX, screenY, pointer);
		
		
		
		Vector3 pos2 = new Vector3(screenX,screenY,0);
		values_.camera().unproject(pos2);
		int x2 = (int) (pos2.x / values_.size_Px()); //index lignes
		int y2= (int) (pos2.y / values_.size_Px());  //index colones
		int cellule2 = ((x2) * values_.size_m()) +  y2 ; 
		System.err.println(pos2+"  "+values_.size_m()+"  Sourisx ="+screenX+" Sourisy="+screenY+" index x ="+x2+" y="+y2+" cellule= "+cellule2); //position souris ecran
		
		
		
		if(values_.status() == Status.POSITIONNE)
		{
			Vector3 pos = new Vector3(screenX,screenY,0);
			values_.camera().unproject(pos);
			int x = (int) (pos.x / values_.size_Px());
			int y= (int) (pos.y / values_.size_Px());
			int cellule = ((x) * values_.size_m()) +  y ;
			//calcul cellule adj
			ArrayList<Integer> cell = new ArrayList<Integer>();
    	    cell.add(cellule);
    	    int indice_haut	 = cellule - values_.size_m();
  		    int indice_bas = cellule + values_.size_m();
  		    int indice_droite  = cellule + 1;
  		    int indice_gauche= cellule - 1;
  		  
	  		if(indice_haut>= 0 && indice_haut<values_.size_m() * values_.size_n())
	  			cell.add(indice_haut);
			if(indice_bas>= 0 && indice_bas<values_.size_m() * values_.size_n())
				cell.add(indice_bas);
			if(indice_droite>= 0 && indice_droite<values_.size_m() * values_.size_n() && indice_droite%values_.size_m() !=0)
				cell.add(indice_droite);
			if(indice_gauche>= 0 && indice_gauche<values_.size_m() * values_.size_n() && indice_gauche%values_.size_m() !=values_.size_m()-1)
				cell.add(indice_gauche);
              

			float zoom = values_.camera().zoom;
			CollisionBox box =new CollisionBox((int)pos.x-5*(int)zoom,(int)pos.y-5*(int)zoom,10*(int)zoom,10*(int)zoom);
			boolean trouve = false;
			
			for(int i=0;i<cell.size();i++)
			{
				int size = values_.carte()[cell.get(i)].getUnits_().size();
				for(int j=0 ; j < size; j++ )
				{
					TowerType tower =values_.carte()[cell.get(i)].getUnits_().get(j);
					if(tower.collision(box) == true)				
					{
						values_.status(Status.INFO);
						values_.setIndex_unit_selection_(cell.get(i)); //cellule qui contient la collision
						last_position_.x=pos.x;
						last_position_.y=pos.y;
						
						values_.setT_temporaire_(values_.carte()[cell.get(i)].getUnits_().get(j));
						trouve =true;
						break;
					}
				}
			}
		}
		else
		{
			finger.finger_Zoom(screenX, screenY, pointer);
			finger.finger_Move(screenX, screenY, pointer);
		}
		
		return false;
	}

	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) 
	{
		finger.finger_Up(screenX, screenY, pointer);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) 
	{
		if(values_.status()== Status.NON_POSITIONNE)
		{
			//repasse dans le  repere monde	
			Vector3 pos = new Vector3(screenX,screenY,0);
			values_.camera().unproject(pos);
			CollisionBox box = new CollisionBox((int)(pos.x)-5,(int)(pos.y)-5,10,10); //boite autour du clic
			
			if(values_.getT_temporaire_().collision(box) == true)
			{
				int Tx = Gdx.input.getDeltaX(0);
				int Ty = Gdx.input.getDeltaY(0);

				values_.getT_temporaire_().position_add(Tx, -Ty);
			}
		}
		else 
		{
			finger.finger_Zoom(screenX, screenY, pointer);
			finger.finger_Move(screenX, screenY, pointer);
		}
		
		return false;
	}

	//only desktop
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	//only desktop
	@Override
	public boolean scrolled(int amount)
	{
		values_.zoom(amount*0.1,3,1);
		return false;
	}

	
	public void init()
	{
		values_.vie(1000);
		values_.argent(100);
		etat_jeu_ = StateJeuEnum.CHOIX;
		selection_ = StateMEnuEnum.JEU;
		
		for(int i=0; i< jeu_.length;i++)
			jeu_[i].init();
		
		hud_game_.update_lang();
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
