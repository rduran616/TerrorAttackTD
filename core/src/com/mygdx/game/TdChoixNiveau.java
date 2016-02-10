package com.mygdx.game;


import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/*
 * 
 * attention, lien absolu
 * 
 * 
 */


public class TdChoixNiveau extends StateJeu
{
	GlobalValues values_;
	StateJeuEnum selection_;
	String carte_name_;
	Array<String> lst;
	
	//widgets
	
	List<String> lvl_list_;
	Stage menu_stage;
	Table layout_menu;
	TextButton retour_;
	TextButton jouer_;
	Label erreur_nb_map_;
	
	
	
	public TdChoixNiveau()
	{
		//initialisation des variables
		selection_ = StateJeuEnum.CHOIX;
		values_ = GlobalValues.getInstance();
		
		//initialisation des widgets
		
		retour_=new TextButton("Retour", values_.get_Skin());//init du bouton Jeu 
		
		layout_menu = new Table();
		layout_menu.setSize(values_.get_width(),values_.get_height());
		
		
		//creation zone affichage du menu
		menu_stage = new Stage(new ScreenViewport()); 
		
		lvl_list_ = new List<String>(values_.get_Skin());
		lst = new Array<String>();
		
		//recherche des cartes
		FileHandle dirHandle;
		if(Gdx.app.getType() == ApplicationType.Android)
			dirHandle = Gdx.files.internal("map/");
		else
			dirHandle = Gdx.files.internal("../android/assets/map");
			
		for (FileHandle entry: dirHandle.list()) 
		{
			String file = entry.path();
			String[] str_extension_file = file.split("\\.");
			if(str_extension_file[str_extension_file.length-1].toString().equals("tmx") == true)
			{
				lst.add(entry.path());
			}
		}
		
			
		if(lst.size == 0)
		{
			erreur_nb_map_ = new Label("Erreur: aucune carte trouvee",values_.get_Skin());
			layout_menu.add(erreur_nb_map_).pad(10);
		}
		else
		{
			lvl_list_.setItems(lst);
			layout_menu.add(lvl_list_);
		}
		
		layout_menu.row();
		
		if(lst.size > 0)
		{
			jouer_=new TextButton("Jouer", values_.get_Skin());//init du bouton Option 
			layout_menu.add(jouer_).pad(10);
		}
		
		
		layout_menu.row();
		layout_menu.add(retour_).pad(10);
		menu_stage.addActor(layout_menu);
		
		
		
		
		retour_.addListener(new ClickListener()
		{
		       @Override
		       public void clicked(InputEvent event, float x, float y) {
		    	   selection_ = StateJeuEnum.RETOUR;
		       }
		 });
		
		
		if(jouer_ != null)
		{	jouer_.addListener(new ClickListener()
			{
			       @Override
			       public void clicked(InputEvent event, float x, float y) 
			       {
			    	   values_.map_name(lst.get(lvl_list_.getSelectedIndex()));	
					   values_.init_tile_map();
			    	   selection_ = StateJeuEnum.INTRO;
			       }
			 });
		}
	}
	
	public String carte_name(){return carte_name_;}

	@Override
	public StateJeuEnum exectute() 
	{
		
		//afficher menu de selection
		if(lst.size == 1 )
		{
			selection_ = StateJeuEnum.INTRO;
			values_.map_name(lst.get(0));	
			values_.init_tile_map();
			values_.carte_Init();	

		}
		
			
		menu_stage.draw();
		Gdx.input.setInputProcessor(menu_stage);

		
		//Changer de menu
		if(selection_ != StateJeuEnum.CHOIX)
		{
			StateJeuEnum tps = selection_;
			selection_ = StateJeuEnum.CHOIX;
			return  tps;
		}
		else
			return selection_;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
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
