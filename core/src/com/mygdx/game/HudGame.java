package com.mygdx.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Utilitaires.ConfigHud;
import Utilitaires.ReadXml;
import Utilitaires.TypeFlag;
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
	
	
	private int size_hud_ = 20;				//valeur en % de la taille du hud par rapport a l'ecran
	
	private Stage stage_game_;				//stage du jeu qd on est spectateur
//	private Layout main_layout_game_;		//layout du hud du jeu ( layout du mode jeu)
	private Table main_table_game_;			//layout principale du hud du jeu
	private Label label_tour_;				//label pour placement tour
	private Label label_bonus_;				//label pour placement tour
	private Label label_amelioration_;		//label pour amelioratin générale des tours
	private Label vie_;
	private Label argent_;
	
//	private Stage stage_game_2;				//stage du jeu qd on place un objet
	private Table main_layout_game_2;		//layout du mode placement 
	private Label txt_info_;
	private TextButton validate_;
	private TextButton cancel_;
	
//	private Stage stage_game_3;				//stage du jeu qd on place un objet
	private Table main_layout_game_3;		//layout du mode placement 
	private Label txt_info_2;
	private TextButton validate_2;
	private TextButton cancel_2;
	
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
			
			System.err.println("nb_tower= "+nb_towers_);
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
		
		label_tour_				= new Label("Tours :", values_.get_Skin());
		label_bonus_			= new Label("Bonus :", values_.get_Skin());
		label_amelioration_		= new Label("Upgra :", values_.get_Skin());
		stage_game_				= new Stage(new ScreenViewport());
				
		//placement des boutons
		ConfigHud hud = new ConfigHud();
		hud.column(2);
		hud.height(10);
		hud.width(((values_.get_width()*size_hud_/100)/2) - 2*pad_ );
		hud.nb_button(nb_towers_);
		hud.pad(2);
		
		
		if(Gdx.app.getType() == ApplicationType.Android)
			hud.xml(xml_unit_file_internal_);
		else
			hud.xml(xml_unit_file_);
			
		hud.table(main_table_game_);
		
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
		vie_ = new Label("Vie :", values_.get_Skin());
		vie_.setPosition(0, values_.get_height()-20);
		argent_ = new Label("Argent :", values_.get_Skin());
		argent_.setPosition(0, values_.get_height()-40);
		
		
		//deuxieme hud				
		main_layout_game_2 = new Table();		
		txt_info_ = new Label("Placement :", values_.get_Skin());
		cancel_ = new TextButton("Annuler", values_.get_Skin());
		validate_ = new TextButton("Valider", values_.get_Skin());
		
		cancel_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       { 
		    	   values_.argent(argent_temp);
		    	   values_.status(Status.POSITIONNE);
		    	   values_.tower().remove(values_.tower().size()-1); 
		       }
		 });
		
		//placement definitif de la tour et mise en route de celle ci
		validate_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       { 
		    	   //enregistrement de la position dans le repere carte
		    	   
		    	   
		    	   //recuperation de la cellule correspondante a la position
		    	   int cellule = values_.last_tower().get_Index_Cellule_Mono(values_.size_Px(), values_.size_Px(),  values_.size_n());
		    	   //verification collision avec autre objet
		    	   
		    	   boolean en_collision = false;
		    	   if(values_.carte()[cellule].getUnits_size_()>0)//si il y a qqun
				   {
						for(int i=0;i< values_.carte()[cellule].getUnits_().size();i++)
			    	    {
			    		   if(values_.last_tower().collision(values_.tower().get(values_.carte()[cellule].index_Units(i)).box())==true)
		    			   {
		    			   		en_collision =true;
		    			   		break;
		    			   }
			    	    }
	
			    	   if(en_collision == false)
			    	   {
				    	   //placement sur la carte
				    	   values_.carte()[cellule].add_Unit(values_.tower().size()-1);
				    	   //mise a jour de l'index dans cellule dans towertype ->  index = index dans le units de cellmap
				    	   values_.last_tower().setIndex( values_.carte()[cellule].getUnits_().size()-1); 
				    	   //changement d'etat
				    	   values_.status(Status.POSITIONNE);
				    	   
				    	   System.err.println("cellule sprite ="+cellule);
			    	   }
			    	   else
			    	   {
			    		   System.err.println("il y a deja qqun");
			    	   }
					}
					else
			 	   	{
						System.err.println("cellule sprite ="+cellule);
				    	   //placement sur la carte
				    	   values_.carte()[cellule].add_Unit(values_.tower().size()-1);
				    	   //mise a jour de l'index dans cellule dans towertype ->  index = index dans le units de cellmap
				    	   values_.last_tower().setIndex( values_.carte()[cellule].getUnits_().size()-1); 
				    	   //changement d'etat
				    	   values_.status(Status.POSITIONNE);
			 	   	}
		       }
		 });

		
		main_layout_game_2.setSize(values_.get_width()*size_hud_/100,values_.get_height());
		main_layout_game_2.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
		main_layout_game_2.add(txt_info_).pad(pad_).row();
		main_layout_game_2.add(validate_).pad(pad_).row();
		main_layout_game_2.add(cancel_).pad(pad_).row();
		

		//troisième hud
	//	stage_game_3 = new Stage();				
		main_layout_game_3 = new Table();		
		txt_info_2 = new Label("Vendre?", values_.get_Skin());
		cancel_2 = new TextButton("Annuler", values_.get_Skin());
		validate_2 = new TextButton("Valider", values_.get_Skin());

		cancel_2.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y)
		       {	System.err.println("clicked1");
		    	   	values_.status(Status.POSITIONNE);
		       }
		 });
		
		//placement definitif de la tour et mise en route de celle ci
		validate_2.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) 
		       { 
		    	   //enregistrement de la position dans le repere carte
		    	   System.err.println("clicked2");
		    	   values_.status(Status.POSITIONNE);
		    	 
		       }
		 });

		
		main_layout_game_3.setSize(values_.get_width()*size_hud_/100,values_.get_height());
		main_layout_game_3.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm1))));
		main_layout_game_3.add(txt_info_2).pad(pad_).row();
		main_layout_game_3.add(validate_2).pad(pad_).row();
		main_layout_game_3.add(cancel_2).pad(pad_).row();
		
		//stage_game_3.addActor(main_layout_game_3);
		
		stage_game_.addActor(argent_);
		stage_game_.addActor(vie_);
		stage_game_.addActor(main_table_game_);
		stage_game_.addActor(main_layout_game_2);
		stage_game_.addActor(main_layout_game_3);
	}
	
	
	public void argent(){argent_.setText("Argent :"+Integer.toString(values_.argent()));}
	public void vie(){vie_.setText("Vie :"+Integer.toString(values_.vie()));}
	
	
	public Stage stage()
	{
		if(values_.status() == Status.POSITIONNE)
		{
			main_table_game_.setVisible(true);
			main_layout_game_2.setVisible(false);
			main_layout_game_3.setVisible(false);
			//return stage_game_;
		}
		else if(values_.status() == Status.NON_POSITIONNE)
		{
			main_table_game_.setVisible(false);
			main_layout_game_2.setVisible(true);
			main_layout_game_3.setVisible(false);
			//return stage_game_2;
		}
		else
		{
			main_table_game_.setVisible(false);
			main_layout_game_2.setVisible(false);
			main_layout_game_3.setVisible(true);
		}
			
			return stage_game_;
	} 
//	public Stage stage2(){return stage_game_2;}
//	public Stage stage3(){return stage_game_3;}
	
	
	//création des boutons de l'ui principal
	private void creation_Hud_Objects(ConfigHud hud)
	{
		int cpt=0;
		hud.other(0);
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
				
				if(name!=null)
				{
					if(name.equals("air"))
					{
						button.addListener(new ClickListener()
						{
						       @Override
						       public void clicked(InputEvent event, float x, float y) 
						       { 
						    	   if(values_.status() != Status.NON_POSITIONNE && values_.argent() >= values_.t_air_modele_().cout())
						    	   {
						    		   argent_temp = values_.argent();
						    		   values_.argent(values_.argent()-values_.t_air_modele_().cout());
						    		   System.err.println("TowerAir");
						    		   //creation de la tour et ajout dans le tableau "list_tower" en mode non positionner
						    		   values_.tower().add(new TowerAir(values_.t_air_modele_()));	
						    		   
						    		   Vector3 pos = new Vector3(values_.get_width()/2, values_.get_height()/2,0);//milieu de l'ecran
						    		   values_.camera().unproject(pos); //screen to world
						    		      
						    		   values_.last_tower().box().set_Collision_box((int)pos.x, (int)pos.x, 64,64);
						    		   values_.last_tower().position(pos.x, pos.y);

						    		   values_.status(Status.NON_POSITIONNE);
						    	   }
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
						    	   System.err.println("TowerZone");
						    	   /*if(values_.status() != Status.NON_POSITIONNE)
						    	   {
						    		   // values_.tower().add(new TowerAir(values_.t_zone_modele_()));
							    	   values_.status(Status.NON_POSITIONNE);
						    	   }*/
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
						    	   System.err.println("TowerSlow");
						    	   //values_.tower().add(new TowerAir(values_.t_slow_modele_());
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
						    	   System.err.println("TowerBase");
						    	   //values_.tower().add(new TowerAir(values_.t_base_modele_()));
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
	

	public float pad(){return pad_;}
	public void pad(float p){pad_=p;}
}
