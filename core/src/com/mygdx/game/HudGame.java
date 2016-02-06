package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Utilitaires.CollisionBox;
import Utilitaires.ConfigHud;
import Utilitaires.ReadXml;
import Utilitaires.Spirale;
import units.Status;
import units.TowerAir;
import units.TowerBase;
import units.TowerSlow;
import units.TowerType;
import units.TowerZone;


/**
 * 
 * @author Florian
 *
 *	Class spécialmenet prevu pour la creation du hud jeu, via un fichier xml en fonction de l'environement
 */

public class HudGame 
{
	private GlobalValues values_;
	private ReadXml xml_unit_file_;		//fichier xml avec toutes les unitées
	private ReadInternalXML xml_unit_file_internal_;
	
	//enemies:
	private int nb_mobs_; 	//nombre d'unité differente (typeenemi)
	private int nb_towers_; //nombre de tower differente (typetower)  utile pour le hud jeu
	private int nb_bonus_;	//nombre de bonus different
	private int pourcent_vente_ = 50;
	
	private StateJeuEnum etat_jeu_ = StateJeuEnum.JEU;
	
	private int size_hud_ = 20;				//valeur en % de la taille du hud par rapport a l'ecran
	
	private Stage stage_game_;				//stage du jeu qd on est spectateur
	private Table main_table_game_;			//layout principale du hud du jeu
	private Label label_tour_;				//label pour placement tour
	private Label label_bonus_;				//label pour placement tour
	private Label label_amelioration_;		//label pour amelioratin générale des tours
	private Label vie_;
	private Label argent_;
	private TextButton pause_;
	
//	private Stage stage_game_2;				//stage du jeu qd on place un objet
	private Table main_layout_game_2;		//layout du mode placement 
	private Label txt_info_;
	private Label txt_info_tour_;
	private TextButton validate_;
	private TextButton cancel_;
	
//	private Stage stage_game_3;				//stage du jeu qd on place un objet
	private Table main_layout_game_3;		//layout du mode placement 
	private Label txt_info_2;
	private Label txt_info_tour_vendre_;
	private TextButton validate_2;
	private TextButton cancel_2;
	
	//hud 4 validation achat upgrade 
	private Table main_layout_game_4;
	private TextButton validate_4;
	private TextButton cancel_4;
	private Label txt_info_4;
	private Label txt_info_achat_up_;
	
	private int argent_temp;
	private float pad_;	//pad des boutons
	
	
	public HudGame(float p)
	{
		pad_ = p;
		values_ = GlobalValues.getInstance();
		
		//recup nombre de type de tour existant pour creation des boutons
		if(Gdx.app.getType() == ApplicationType.Android) //test plateforme
		{
			xml_unit_file_internal_ = new ReadInternalXML("Config/units.xml"); 
			nb_towers_ = Integer.parseInt(xml_unit_file_internal_.get_Attribute("tower", "value"));
			nb_bonus_ = Integer.parseInt(xml_unit_file_internal_.get_Attribute("bonus", "value"));
			
			//System.err.println("nb_tower= "+nb_towers_);
		}
		else
		{
			xml_unit_file_ = new ReadXml("../android/assets/Config/units.xml");
			nb_towers_ 	= xml_unit_file_.node_Item_Child_Number("tower");
			nb_bonus_	= xml_unit_file_.node_Item_Child_Number("bonus");
		}
			
		
		
		//couleur jaune pour le background du hud
		Pixmap pm1 = new Pixmap(1, 1, Format.RGB565);
		pm1.setColor(Color.YELLOW);
		pm1.fill();
				
		main_table_game_ 	= new Table();
		main_table_game_.setSize(values_.get_width()*size_hud_/100,values_.get_height());
		//layout_table_tower_.setPosition(0,0);
		
		label_tour_				= new Label(values_.localisation().get("tour"), values_.get_Skin());
		label_bonus_			= new Label(values_.localisation().get("bonus"), values_.get_Skin());
		label_amelioration_		= new Label(values_.localisation().get("amelio"), values_.get_Skin());
		stage_game_				= new Stage(new ScreenViewport());
				
		//placement des boutons
		ConfigHud hud = new ConfigHud();
		hud.column(2);
		hud.height(20);
		hud.width(((values_.get_width()*size_hud_/100)/2) - 2*pad_ );
		hud.nb_button(nb_towers_);
		hud.pad(2);
		
		
		if(Gdx.app.getType() == ApplicationType.Android)
			hud.xml(xml_unit_file_internal_);
		else
			hud.xml(xml_unit_file_);
			
		hud.table(main_table_game_);
		
		pause_ = new TextButton(values_.localisation().get("pause"), values_.get_Skin());
		pause_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       { 
		    	   etat_jeu_ = StateJeuEnum.PAUSE;
		       }
		 });
		
		
		
		
		main_table_game_.add(pause_).row();
		main_table_game_.add(label_tour_);
		main_table_game_.row();
		hud.node("tower");
		creation_Hud_Objects(hud);
		main_table_game_.row();
		main_table_game_.add(label_amelioration_);
		main_table_game_.row();
		hud.node("upgrade");
		creation_Hud_Objects(hud);
		main_table_game_.row();
		main_table_game_.add(label_bonus_);
		main_table_game_.row();
		hud.node("bonus");
		hud.nb_button(nb_bonus_);
		creation_Hud_Objects(hud);
		
		//background jaune
		main_table_game_.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
		
		//autre informations
		vie_ = new Label(values_.localisation().get("vie"), values_.get_Skin());
		vie_.setPosition(values_.get_width()-vie_.getWidth(), values_.get_height()-20);
		argent_ = new Label(values_.localisation().get("argent"), values_.get_Skin());
		argent_.setPosition(values_.get_width()-argent_.getWidth(), values_.get_height()-40);
		
		
		//deuxieme hud	-> placment tour			
		main_layout_game_2 = new Table();		
		txt_info_ = new Label(values_.localisation().get("placement"), values_.get_Skin());
		cancel_ = new TextButton(values_.localisation().get("annuler"), values_.get_Skin());
		validate_ = new TextButton(values_.localisation().get("valider"), values_.get_Skin());
		txt_info_tour_ = new Label("", values_.get_Skin());
	
		cancel_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       { 
		    	   values_.argent(argent_temp);
		    	   values_.status(Status.POSITIONNE);
		    	   values_.setT_temporaire_(null);
		       }
		 });
		
		//placement definitif de la tour et mise en route de celle ci
		validate_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       {   

		    	   	//la position dans le monde
		    	   	Vector3 pos = new Vector3(values_.getT_temporaire_().position().x,values_.getT_temporaire_().position().y,0); 
					//la case ou on place le bonhomme
	   				int cell = values_.get_Index_Cellule(pos.x,pos.y);
	   				int case_ia = values_.get_Index_Cellule(pos.x, pos.y, 32, 32);
	   				
	   				System.err.println(cell);
	   				if(cell == values_.cell_Depart() || cell == values_.cell_Arrive())
   					{
   						System.err.println("depart ou arrivé");
   						return;
   					}
	   				
	   				
		    	   //vérification collision 
		    	   ArrayList<TowerType> list_tower = values_.carte()[cell].getUnits_();
		    	   boolean colision = false;
		    	   if(list_tower.size()>0) //est ce qu'il y a deja une tour dans la case?
		    	   {
		    		   colision = false;
		    		   for(int i=0; i < list_tower.size();i++)
		    		   {
		    			   CollisionBox b = values_.getT_temporaire_().box(); 
		    			   if(list_tower.get(i).collision(b))
		    			   {
		    				   colision =true; 
		    				   break;
		    			   }
		    		   }
		    		   
		    		   if(colision==false)
		    		   {
		    			   //System.err.println("positioné"); 
		    			   TowerType t = values_.getT_temporaire_();
		    			   
		    			   if(t.nom().equals("air"))
		    				   	values_.add_uAir(1);
	    					if(t.nom().equals("base"))
	    						values_.add_uBase(1);
	    					if(t.nom().equals("zone"))
	    						values_.add_uZone(1);
	    					if(t.nom().equals("slow"))
	    						values_.add_uSlow(1);
		    			   
		    			   t.setCases_adj(new ArrayList<Integer>( Spirale.adjacente2( values_.size_Px(), t.position(), values_.size_n(), values_.size_m(), (int)t.get_range())));
		    			   values_.carte()[cell].add_Unit(t);
		    			   values_.setT_temporaire_(null);
		    			   values_.status(Status.POSITIONNE);
		    			   values_.recalculerChemin_(true);
		    			   
		    			   values_.carte_Ia_setOccupe(case_ia, true);
		    			
		    		   }
		    	   }
		    	   else
		    	   {
		    		   TowerType t = values_.getT_temporaire_();
		    		   
		    		    if(t.nom().equals("air"))
	    				   	values_.add_uAir(1);
	   					if(t.nom().equals("base"))
	   						values_.add_uBase(1);
	   					if(t.nom().equals("zone"))
	   						values_.add_uZone(1);
	   					if(t.nom().equals("slow"))
	   						values_.add_uSlow(1);
		    		   
		    		   System.err.println("position cell = "+cell);
	    			   values_.carte()[cell].add_Unit(t);
	    			   values_.setT_temporaire_(null);
	    			   values_.status(Status.POSITIONNE); 
	    			   values_.recalculerChemin_(true);
	    			   
	    			  
	    			   values_.carte_Ia_setOccupe(case_ia, true);
	    			   
		    	   }
		       }
		 });

		
		main_layout_game_2.setSize(values_.get_width()*size_hud_/100,values_.get_height());
		main_layout_game_2.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
		main_layout_game_2.add(txt_info_).pad(pad_).row();
		main_layout_game_2.add(validate_).pad(pad_).row();
		main_layout_game_2.add(cancel_).pad(pad_).row();
		main_layout_game_2.add(txt_info_tour_).pad(pad_).row();
		

		//troisième hud		
		main_layout_game_3 = new Table();		
		txt_info_2 = new Label(values_.localisation().get("vendre"), values_.get_Skin());
		cancel_2 = new TextButton(values_.localisation().get("annuler"), values_.get_Skin());
		validate_2 = new TextButton(values_.localisation().get("valider"), values_.get_Skin());
		txt_info_tour_vendre_ = new Label("", values_.get_Skin());

		cancel_2.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y)
		       {
		    	  
		    	   values_.setT_temporaire_(null);
		    	   	values_.status(Status.POSITIONNE);
		       }
		 });
		
		//suppression definitif de la tour
		validate_2.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       { 
		    	 //enregistrement de la position dans le repere carte
		    	   
		    	 //suppression
		    	 try
		    	 {  
		    		 int cellule = values_.getIndex_unit_selection_(); //retourne la cellule ou la tour est
		    		 //suppression cellule
		    		 int size = values_.carte()[cellule].getUnits_size_();
		    		 float xScr = Jeu.get_Last_Position().x;
		    		 float yScr = Jeu.get_Last_Position().y;
		    		 CollisionBox box =new CollisionBox((int)xScr-5,(int)yScr-5,10,10);
		    		 
		    		 int case_ia;
		    		 
		    		 for(int i=0; i < size ;i++)
		    		 {
		    			 
	    				if(values_.carte()[cellule].getUnits_().get(i).collision(box) == true)				
						{
	    					Vector2 pos = new Vector2(values_.carte()[cellule].getUnits_().get(i).position());
	    					case_ia = values_.get_Index_Cellule(pos.x, pos.y, 32, 32);
	    					
	    					if(values_.getT_temporaire_().nom().equals("air"))
	    						values_.sub_uAir(1);
	    					if(values_.getT_temporaire_().nom().equals("base"))
	    						values_.sub_uBase(1);
	    					if(values_.getT_temporaire_().nom().equals("zone"))
	    						values_.sub_uZone(1);
	    					if(values_.getT_temporaire_().nom().equals("slow"))
	    						values_.sub_uSlow(1);
	    					
	    					
	    					values_.carte()[cellule].getUnits_().remove(i);
	    					values_.argent(values_.argent() + values_.getT_temporaire_().cout() *  pourcent_vente_/100);
	    					values_.setT_temporaire_(null);
	    					
	    					values_.carte_Ia_setOccupe(case_ia, false);
	    					
							break;
						}		
		    		 }

			    	  values_.recalculerChemin_(true);
			    	  values_.status(Status.POSITIONNE);
		    	 }
		    	 catch(Exception e)
		    	 {
		    		 System.err.println("hud game suppr erreur "+e);
		    	 }

		       }
		 });

		
		main_layout_game_3.setSize(values_.get_width()*size_hud_/100,values_.get_height());
		main_layout_game_3.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
		main_layout_game_3.add(txt_info_2).pad(pad_).row();
		main_layout_game_3.add(validate_2).pad(pad_).row();
		main_layout_game_3.add(cancel_2).pad(pad_).row();
		main_layout_game_3.add(txt_info_tour_vendre_).pad(pad_).row();
		
		
		//4eme hud : validation achat upgrade
		main_layout_game_4 = new Table();
		validate_4 	= new TextButton(values_.localisation().get("valider"), values_.get_Skin());
		cancel_4 	= new TextButton(values_.localisation().get("annuler"), values_.get_Skin());
		txt_info_4 = new Label(values_.localisation().get("amelio")+"?", values_.get_Skin());
		txt_info_achat_up_= new Label("", values_.get_Skin());
		
		cancel_4.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y)
		       {
		    	   values_.setT_temporaire_(null);
		    	   values_.status(Status.POSITIONNE);
		       }
		 });
		
		validate_4.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       { 
			    	 try
			    	 {   
			    		 int nb=0;
			    		if(values_.getT_temporaire_().nom().equals("air"))
						{
			    			nb=values_.getNb_unite_air_();
			    			if(values_.argent()- values_.getT_temporaire_().cout() * nb <0)
			    			{	values_.status(Status.POSITIONNE);
		    				return;}
			    			values_.t_air_modele_(new TowerAir((TowerAir) values_.t_air_modele_().update_Values(TowerType.ARGENT | TowerType.DAMAGE , TowerType.PLUS, 2)));
						}
	 					if(values_.getT_temporaire_().nom().equals("base"))
						{
	 						nb = values_.getNb_unite_base_();
	 						if(values_.argent()- values_.getT_temporaire_().cout() * nb <0)
	 						{	values_.status(Status.POSITIONNE);
		    				return;}
	 						
	 						values_.t_base_modele_(new TowerBase((TowerBase) values_.getT_temporaire_().update_Values(TowerType.ARGENT | TowerType.SPEED , TowerType.PLUS, 4)));
						}
	 					if(values_.getT_temporaire_().nom().equals("zone"))
						{
	 						nb= values_.getNb_unite_zone_();
	 						if(values_.argent()- values_.getT_temporaire_().cout() * nb <0)
	 						{	values_.status(Status.POSITIONNE);
		    				return;}
	 						
	 						values_.t_zone_modele_(new TowerZone((TowerZone) values_.getT_temporaire_().update_Values(TowerType.ARGENT | TowerType.DAMAGE | TowerType.RANGE, TowerType.PLUS, 1)));	
						}
	 					if(values_.getT_temporaire_().nom().equals("slow"))
						{
	 						nb = values_.getNb_unite_slow_();
	 						if(values_.argent()- values_.getT_temporaire_().cout() * nb <0)
	 						{	values_.status(Status.POSITIONNE);
			    				return;}
	 						values_.t_slow_modele_(new TowerSlow((TowerSlow) values_.getT_temporaire_().update_Values(TowerType.ARGENT | TowerType.DAMAGE , TowerType.FOIS, 3))); 						
						}
	 					
	 					//maj de toute les tours
			    		 for(int i =0; i < values_.carte().length;i++)
			    		 {
			    			 for(int j=0; j < values_.carte()[i].getUnits_size_();j++)
			    			 {
			    				 if(values_.carte()[i].getUnits_().get(j).nom().equals(values_.getT_temporaire_().nom()) && values_.getT_temporaire_().nom().equals("air"))
	    						 {
			    					values_.carte()[i].getUnits_().get(j).update_Values(TowerType.ARGENT | TowerType.DAMAGE , TowerType.PLUS, 2);
	    						 }
			    				 else if(values_.carte()[i].getUnits_().get(j).nom().equals(values_.getT_temporaire_().nom()) && values_.getT_temporaire_().nom().equals("base"))
	    						 {
			    					values_.carte()[i].getUnits_().get(j).update_Values(TowerType.ARGENT | TowerType.SPEED , TowerType.PLUS, 4);
	    						 }
			    				 else if(values_.carte()[i].getUnits_().get(j).nom().equals(values_.getT_temporaire_().nom()) && values_.getT_temporaire_().nom().equals("slow"))
	    						 {
			    					values_.carte()[i].getUnits_().get(j).update_Values(TowerType.ARGENT | TowerType.DAMAGE , TowerType.FOIS, 3);
	    						 }
			    				 else if(values_.carte()[i].getUnits_().get(j).nom().equals(values_.getT_temporaire_().nom()) && values_.getT_temporaire_().nom().equals("zone"))
	    						 {
			    					values_.carte()[i].getUnits_().get(j).update_Values(TowerType.ARGENT | TowerType.DAMAGE | TowerType.RANGE, TowerType.PLUS, 1);
	    						 }
			    			 }
			    		 }
	

		    			 values_.argent(values_.argent()- values_.getT_temporaire_().cout() * nb);
		    			 values_.setT_temporaire_(null);
		    			 values_.status(Status.POSITIONNE);
			    		 
			    	 }
			    	 catch(Exception e)
			    	 {
			    		 System.err.println("hud game upgrade erreur "+e);
			    	 }
		    	   }
		       
		 });

		
		
		main_layout_game_4.setSize(values_.get_width()*size_hud_/100,values_.get_height());
		main_layout_game_4.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
		main_layout_game_4.add(txt_info_4).pad(pad_).row();
		main_layout_game_4.add(validate_4).pad(pad_).row();
		main_layout_game_4.add(cancel_4).pad(pad_).row();
		main_layout_game_4.add(txt_info_achat_up_).pad(pad_).row();
		
		
		
		stage_game_.addActor(argent_);
		stage_game_.addActor(vie_);
		stage_game_.addActor(main_table_game_);
		stage_game_.addActor(main_layout_game_2);
		stage_game_.addActor(main_layout_game_3);
		stage_game_.addActor(main_layout_game_4);
	}
	
	

	
	public void argent()
	{
		argent_.setText(values_.localisation().get("argent")+": "+Integer.toString(values_.argent()));
		argent_.setPosition(values_.get_width()-argent_.getWidth()*2, values_.get_height()-40);
	}
	
	
	
	public void vie()
	{
		vie_.setText(values_.localisation().get("vie")+": "+Integer.toString(values_.vie()));
		vie_.setPosition(values_.get_width()-argent_.getWidth()*2, values_.get_height()-20);
	}
	
	
	public Stage stage()
	{
		if(values_.status() == Status.POSITIONNE)
		{
			main_table_game_.setVisible(true);
			main_layout_game_2.setVisible(false);
			main_layout_game_3.setVisible(false);
			main_layout_game_4.setVisible(false);
		}
		else if(values_.status() == Status.NON_POSITIONNE)
		{
			
			txt_info_tour_.setText(values_.localisation().get("tour")+": "+values_.getT_temporaire_().nom()+
						"\n"+values_.localisation().get("cout")+": "+values_.getT_temporaire_().cout()+
						"\n"+values_.localisation().get("degat")+": "+values_.getT_temporaire_()._damage+
						"\n"+values_.localisation().get("vitesse_att")+": "+values_.getT_temporaire_()._attspeed/1000+
						"\n"+values_.localisation().get("range")+": "+values_.getT_temporaire_()._range+
						"\n"+values_.localisation().get("aa")+": "+values_.getT_temporaire_()._air);
	
			
			main_table_game_.setVisible(false);
			main_layout_game_2.setVisible(true);
			main_layout_game_3.setVisible(false);
			main_layout_game_4.setVisible(false);
		}
		else if(values_.status() == Status.INFO_UPGRADE)
		{
			int nb =0;
			String nom =values_.getT_temporaire_().nom();
			
			if(nom.equals("air"))	
			{
				//values_.getT_temporaire_().update_Values(TowerType.ARGENT | TowerType.DAMAGE , TowerType.PLUS, 2);
				nb = values_.getNb_unite_air_();
			}
			if(nom.equals("slow"))	
			{
				//values_.getT_temporaire_().update_Values(TowerType.ARGENT | TowerType.DAMAGE , TowerType.FOIS, 3);
				nb = values_.getNb_unite_slow_();
			}
			if(nom.equals("zone"))	
			{
				//values_.getT_temporaire_().update_Values(TowerType.ARGENT | TowerType.DAMAGE | TowerType.RANGE, TowerType.PLUS, 1);
				nb = values_.getNb_unite_zone_();
			}
			if(nom.equals("base"))	
			{
				//values_.getT_temporaire_().update_Values(TowerType.ARGENT | TowerType.SPEED , TowerType.PLUS, 4);
				nb = values_.getNb_unite_base_();
			}

			System.err.println(nb);
			txt_info_achat_up_.setText("Tour: "+values_.getT_temporaire_().nom()+": "+
					"\n "+values_.localisation().get("cout")+": "+(values_.getT_temporaire_().cout()*nb)+
					"\n "+values_.localisation().get("degat")+": "+values_.getT_temporaire_()._damage+
					"\n "+values_.localisation().get("vitesse_att")+": "+values_.getT_temporaire_()._attspeed/1000+
					"\n "+values_.localisation().get("range")+": "+values_.getT_temporaire_()._range+
					"\n "+values_.localisation().get("aa")+": "+values_.getT_temporaire_()._air);
			
			main_table_game_.setVisible(false);
			main_layout_game_2.setVisible(false);
			main_layout_game_3.setVisible(false);
			main_layout_game_4.setVisible(true);

		}
		else
		{
			txt_info_tour_vendre_.setText(values_.localisation().get("tour")+": "+values_.getT_temporaire_().nom()+
					"\n"+values_.localisation().get("vendre")+": "+values_.getT_temporaire_().cout()*pourcent_vente_/100);
			
			
			main_table_game_.setVisible(false);
			main_layout_game_2.setVisible(false);
			main_layout_game_3.setVisible(true);
			main_layout_game_4.setVisible(false);
		}
			
			return stage_game_;
	} 

	
	public void update_lang()
	{
		vie_.setText(values_.localisation().get("vie"));
		vie_.setPosition(values_.get_width()-vie_.getWidth(), values_.get_height()-20);
		argent_.setText(values_.localisation().get("argent"));
		argent_.setPosition(values_.get_width()-argent_.getWidth(), values_.get_height()-40);
		
		
		label_tour_.setText(values_.localisation().get("tour"));
		label_bonus_.setText(values_.localisation().get("bonus"));
		label_amelioration_.setText(values_.localisation().get("amelio"));
		
		txt_info_.setText(values_.localisation().get("placement"));
		cancel_.setText(values_.localisation().get("annuler"));
		validate_.setText(values_.localisation().get("valider"));
		
		txt_info_2.setText(values_.localisation().get("vendre"));
		cancel_2.setText(values_.localisation().get("annuler"));
		validate_2.setText(values_.localisation().get("valider"));
		
		validate_4.setText(values_.localisation().get("valider"));
		cancel_4.setText(values_.localisation().get("annuler"));
		txt_info_4.setText(values_.localisation().get("amelio")+"?");
		
		pause_.setText(values_.localisation().get("pause"));
		
		Pixmap pm1 = new Pixmap(1, 1, Format.RGB565);
		pm1.setColor(Color.YELLOW);
		pm1.fill();
		main_table_game_.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
	}
	
	
	//création des boutons de l'ui principal
	private void creation_Hud_Objects(ConfigHud hud)
	{
		int cpt=0;
		hud.other(0);
		
		
		if(hud.node().equals("upgrade"))
		{
			for(int i=0; i <4;i++)
			{
				TextButton button = new TextButton(Integer.toString(i),values_.get_Skin());
				switch(i)
				{
					case 0:
						button.addListener(new ClickListener()
						{
						       @Override
						       public void clicked(InputEvent event, float x, float y) 
						       { 
						    	 
						    	   values_.setT_temporaire_(new TowerAir(values_.t_air_modele_()));		
						    	   values_.getT_temporaire_().update_Values(TowerType.ARGENT | TowerType.DAMAGE , TowerType.PLUS, 2);
						    	   values_.status(Status.INFO_UPGRADE);
						    	   values_.setNum_tr_upgrade_(0);
						       }
						 });
					break;
					
					case 1:
						button.addListener(new ClickListener()
						{
						       @Override
						       public void clicked(InputEvent event, float x, float y) 
						       { 
						    	   values_.setT_temporaire_(new TowerBase(values_.t_base_modele_()));
						    	   values_.getT_temporaire_().update_Values(TowerType.ARGENT | TowerType.SPEED , TowerType.PLUS, 4);
						    	   values_.status(Status.INFO_UPGRADE);
						    	   values_.setNum_tr_upgrade_(1);
						       }
						 });
					break;
					
					case 2:
						button.addListener(new ClickListener()
						{
						       @Override
						       public void clicked(InputEvent event, float x, float y) 
						       { 
						    	   values_.setT_temporaire_(new TowerSlow(values_.t_slow_modele_()));	
						    	   values_.getT_temporaire_().update_Values(TowerType.ARGENT | TowerType.DAMAGE , TowerType.FOIS, 3);
						    	   values_.status(Status.INFO_UPGRADE);
						    	   values_.setNum_tr_upgrade_(2);
						       }
						 });
					break;
					
					case 3:
						button.addListener(new ClickListener()
						{
						       @Override
						       public void clicked(InputEvent event, float x, float y) 
						       {
						    	   values_.setT_temporaire_(new TowerZone(values_.t_zone_modele_()));
						    	   values_.getT_temporaire_().update_Values(TowerType.ARGENT | TowerType.DAMAGE | TowerType.RANGE, TowerType.PLUS, 1);
						    	   values_.status(Status.INFO_UPGRADE);
						    	   values_.setNum_tr_upgrade_(3);
						       }
						 });
					break;
				}
				
				hud.table().add(button).pad(hud.pad()).height(hud.height()).width(hud.width());
				cpt++;
				hud.other(cpt);
				
				if(cpt%hud.column()==0)
					hud.table().row();
			}
		}
		else
		{
			for(int i=0;i<hud.nb_button();i++)
			{
				//recuperation des chemins (ou autre, à voir) d'image pour icone bouton
				TextButton button = new TextButton(Integer.toString(i),values_.get_Skin());
				if(true)
		    	{
					String name;
					if(hud.estInternal())
						name = hud.xml_Internal().get_Child_Attribute(hud.node(),"name",hud.other());
					else
						name = hud.xml().get_Sub_Node_Item(hud.other(), hud.node(),"name");
					
					// si on trouve dans le xml
					if(name!=null)
					{
						if(name.equals("air"))
						{
							button.addListener(new ClickListener()
							{
							       @Override
							       public void clicked(InputEvent event, float x, float y) 
							       { 
							    	   try
							    	   {	
							    		   TowerType t = new TowerAir(values_.t_air_modele_());
							    		   if(values_.status() != Status.NON_POSITIONNE && values_.argent() >= t.cout())
								    	   {
								    		   argent_temp = values_.argent();
								    		   values_.argent(values_.argent()-t.cout());
								    		   
								    		   Vector3 pos = new Vector3(values_.get_width()/2, values_.get_height()/2,0);//milieu de l'ecran
								    		   values_.camera().unproject(pos); //screen to world
								    		      
								    		   t.box().set_Collision_box((int)pos.x, (int)pos.x,t.size_H(),t.size_W());
								    		   t.position(pos.x, pos.y);
	
								    		   values_.setT_temporaire_(t);							    		
								    		   values_.status(Status.NON_POSITIONNE);
								    	   }
							    	   }catch(Exception e){System.err.println("hudgame listner erro: "+e);}
							       }
							 });
						}
						else if(name.equals("zone"))
						{
							button.addListener(new ClickListener()
							{
							       @Override
							       public void clicked(InputEvent event, float x, float y) 
							       { 
							    	   try
							    	   {
							    		   TowerType t = new TowerZone(values_.t_zone_modele_());
							    		   if(values_.status() != Status.NON_POSITIONNE && values_.argent() >= t.cout())
								    	   {
								    		   argent_temp = values_.argent();
								    		   values_.argent(values_.argent()-t.cout());
								    		   
								    		   Vector3 pos = new Vector3(values_.get_width()/2, values_.get_height()/2,0);//milieu de l'ecran
								    		   values_.camera().unproject(pos); //screen to world
								    		      
								    		   t.box().set_Collision_box((int)pos.x, (int)pos.x,t.size_H(),t.size_W());
								    		   t.position(pos.x, pos.y);
	
								    		   values_.setT_temporaire_(t);							    		
								    		   values_.status(Status.NON_POSITIONNE);
								    	   }
								    	   
							    	   }catch(Exception e){System.err.println("hudgame listner erro: "+e);}
							       }
							 });
						}
						else if(name.equals("slow"))
						{
							button.addListener(new ClickListener()
							{
							       @Override
							       public void clicked(InputEvent event, float x, float y) 
							       { 
							    	   try
							    	   {
							    		   TowerType t = new TowerSlow(values_.t_slow_modele_());
							    		   if(values_.status() != Status.NON_POSITIONNE && values_.argent() >= t.cout())
								    	   {
								    		   argent_temp = values_.argent();
								    		   values_.argent(values_.argent()-t.cout());
								    		   
								    		   Vector3 pos = new Vector3(values_.get_width()/2, values_.get_height()/2,0);//milieu de l'ecran
								    		   values_.camera().unproject(pos); //screen to world
								    		      
								    		   t.box().set_Collision_box((int)pos.x, (int)pos.x,t.size_H(),t.size_W());
								    		   t.position(pos.x, pos.y);
	
								    		   values_.setT_temporaire_(t);							    		
								    		   values_.status(Status.NON_POSITIONNE);
								    	   }
							    	   }catch(Exception e){System.err.println("hudgame listner erro: "+e);}
							       }
							 });
						}
						else if(name.equals("base"))
						{
							button.addListener(new ClickListener()
							{
							       @Override
							       public void clicked(InputEvent event, float x, float y) 
							       { 
							    	   try
							    	   {
							    		   TowerType t = new TowerBase(values_.t_base_modele_());
							    		   if(values_.status() != Status.NON_POSITIONNE && values_.argent() >= t.cout())
								    	   {
								    		   argent_temp = values_.argent();
								    		   values_.argent(values_.argent()-t.cout());
								    		   
								    		   Vector3 pos = new Vector3(values_.get_width()/2, values_.get_height()/2,0);//milieu de l'ecran
								    		   values_.camera().unproject(pos); //screen to world
								    		      
								    		   t.box().set_Collision_box((int)pos.x, (int)pos.x,t.size_H(),t.size_W());
								    		   t.position(pos.x, pos.y);
	
								    		   values_.setT_temporaire_(t);							    		
								    		   values_.status(Status.NON_POSITIONNE);
								    	   }
							    	   }catch(Exception e){System.err.println("hudgame listner erro: "+e);}
							       }
							 });
						}
					}
				}
	
				hud.table().add(button).pad(hud.pad()).height(hud.height()).width(hud.width());
				cpt++;
				hud.other(cpt);
				
				if(cpt%hud.column()==0)
					hud.table().row();
			}
		}
	}	
	

	public float pad(){return pad_;}
	public void pad(float p){pad_=p;}

	public StateJeuEnum getEtat_jeu_() {
		return etat_jeu_;
	}

	public void setEtat_jeu_(StateJeuEnum etat_jeu_) {
		this.etat_jeu_ = etat_jeu_;
	}

}
