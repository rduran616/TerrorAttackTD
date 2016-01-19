package units;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GlobalValues;
import com.mygdx.game.Tir;

import Utilitaires.CollisionBox;
import Utilitaires.Spirale;
import Utilitaires.StructureEnnemi;
import Utilitaires.TickHorloge;


/*
 * classe mère de toutes les tours!
 * 
 * Gestion des principale caracteristiques de chaque tour
 */

public abstract class TowerType 
{
	
	//define flag
	public static final int ARGENT	=	1;
	public static final int SPEED	=	2;
	public static final int RANGE	=	4;
	public static final int DAMAGE =	8;
	public static final int AIR	=	16;
	
	public static final int PLUS = 0;
	public static final int FOIS = 1;
	
	private GlobalValues values_;
	
	//attribut commun à chaque tour
	private Vector2 position_;  	//position dans le monde
	private CollisionBox  bbox_; 	//boite de collision
	private int size_h_;		 	//taille du sprite en height
	private int size_w_; 			//taille du sprite en width
	private int index;				//index de la position de l'objet dans la cellule position/taille cellule
	
	private int num_texture_;	//numéro de la texture -> on accepte que le tableau de texture est parfaitement ordonnée
	private String _nom; 		//nom de la tour
	public int _cout; 		//son cout
	public float _attspeed; 	//vitesse d'attaque
	public float _range; 	//rayon d'action
	public int _damage; 		//domage
	public boolean _air;		// tire sur les ennemies volant?
	private Matrix3 mat_;		//Matrice de rotation
	
	protected TickHorloge countdown;		//compte a rebour pour tirer
	
	private ArrayList<Integer> cases_adj;//cases adjacentes en fonction du range
	
	public TowerType()
	{
		//status_ = Status.NON_POSITIONNE;
		position_= new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
		bbox_ = new CollisionBox();
	}
	
	
	public TowerType(int cout, int att_speed,int damage, int range,int h, int w,int air,int num_txt, String nom, CollisionBox box )
	{	
	//	position_= new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
		
		if(h>=0)
			size_h_=h;
		else
			size_h_=0;
		
		if(w>=0)
			size_w_=w;
		else
			size_w_=0;
			
		if(nom!=null)
			_nom=nom;
		else
			_nom="undefined";
		
		if(cout >=0)
			_cout = cout;
		else
			_cout =0;
		
		if(att_speed >=0)
			_attspeed = att_speed;
		else
			_attspeed =0;
			
		if(range >=0)
			_range = range;
		else
			_range =0;
		
		if(damage >=0)
			_damage = damage;
		else
			_damage =0;

		if(box != null)
			bbox_ = box;
		else
			bbox_ = new CollisionBox();
		
		if(air == 1)
			_air = true;
		else
			_air = false;
		
		if(num_txt >=0)
			num_texture_ = num_txt;
		else
			num_texture_ =0;	
		
		countdown = new TickHorloge(this._attspeed);//en ms
	}
	
		
	//methode appelée pour tirer sur les mobs (implémentée par les classes filles)
	public  StructureEnnemi onExecute(float vitesse_tir)
	{
		values_ = GlobalValues.getInstance();
		StructureEnnemi str;
		if(countdown==null)
			countdown = new TickHorloge(_attspeed);
		
		if(this.countdown.tick()) // on peux tirer
		{
			//recherche ennemi
			int size =0;
			Mobs cible = null;
			if(cases_adj!=null)
			{
				size =0;
				//pour chaque case adjacente faire
				/*for(int i=0;i<cases_adj.size();i++)
				{
					System.err.print("  "+cases_adj.get(i));
				}*/
				
			//	System.err.println("");
				for(int i=0;i<cases_adj.size();i++)
				{
					//recuperer la taille
					size = values_.carte()[cases_adj.get(i)].getMobs_size_();
				//	System.err.println(cases_adj.get(i)+"  "+size);
					//si case > 0, pour chaque ennemi present dans la case faire...
					for(int j=0;j<size;j++)
					{
						//System.err.println("tir1");
						// si il est vol on passe au suivant sinon on enregistre l'ennemi a viser
						if(values_.carte()[cases_adj.get(i)].getMobs_().get(j).getAir()==false) 
						{
							cible = values_.carte()[cases_adj.get(i)].getMobs_().get(j);
							
							//calcul vecteur direction
							str = new StructureEnnemi();
							str.degat_ = this._damage;
							str.position_cible_ = cible.getPosition_();
							str.position_tour_ = new Vector2(this.position());
							
							//calcul ditance cible-tour
							float distanceCT = str.position_cible_.dst(str.position_tour_);
							//calcul temps
							float tps_tir = distanceCT/(vitesse_tir*Gdx.graphics.getDeltaTime());
							float distance_parcouru_pd_tir = tps_tir * cible.getSpeed_()*Gdx.graphics.getDeltaTime();
							//calcul position à viser
							Vector2 Bprime = new Vector2();
							int dir = cible.getNum_direction_();
							//choix de la direction
							switch(dir)
							{
								//nouvelle position
								case 0:
									Bprime.x=cible.getPosition_().x+distance_parcouru_pd_tir/tps_tir;
									Bprime.y=cible.getPosition_().y;
								break;
								
								case 1:
									Bprime.x=cible.getPosition_().x+distance_parcouru_pd_tir/tps_tir;
									Bprime.y=cible.getPosition_().y;
								break;
								
								case 2:
									Bprime.x=cible.getPosition_().x;
									Bprime.y=cible.getPosition_().y-distance_parcouru_pd_tir/tps_tir;
								break;
								
								case 3:
									Bprime.x=cible.getPosition_().x;
									Bprime.y=cible.getPosition_().y+distance_parcouru_pd_tir/tps_tir;
								break;
							}
							
							
							Vector2 CT = new Vector2(this.position_);
							Bprime.sub(CT);
							Bprime.x*=Gdx.graphics.getDeltaTime();
							Bprime.y*=Gdx.graphics.getDeltaTime();
							str.vecteur_vitesse_ = new Vector2(Bprime);
							str.time_ = new Float(tps_tir);
							
							return str;
						}
					}
				}
			}
			else
			{
				Vector3 p = new Vector3(this.position().x,this.position().y,0);
				cases_adj = new ArrayList<Integer>( Spirale.adjacente2( values_.size_Px(), new Vector2(p.x,p.y), values_.size_n(), values_.size_m(), (int)this.get_range()));
			}
		}
		
		return null; // non trouvé
	}
	
	//méthode de rotation vers la première cible trouvé
	public void  onRotate(int index) 
	{
		
	}
	

	public TowerType update_Values(int flag,int operation, float factor)
	{
		if( (flag & ARGENT) != 0)
		{
			System.err.println("argent");
			if(operation == 0)
			{_cout+=factor;}
			else
			{_cout*=factor;}
		}
		
		if( (flag & SPEED) != 0)
		{
			if(operation == 0)
			{
				_attspeed-=factor;
				if(_attspeed<=0)
					_attspeed = 1;
			}
			else
			{_attspeed/=factor;}
		}
		
		if( (flag & RANGE) != 0)
		{
			if(operation == 0)
			{_range+=factor;}
			else
			{_range*=factor;}
		}
		
		if( (flag & DAMAGE) != 0)
		{
		//	System.err.println("DAMAGE1 = "+_damage);
			if(operation == 0)
			{_damage+=factor;}
			else
			{_damage*=factor;}
			//System.err.println("DAMAGE2 = "+_damage);
		}
		
		if( (flag & AIR) != 0)
		{
			if(_air == true)
				_air = false;
			else
			 _air = true;
		}
		
		return this;
	}
	
	
	public int cout(){return _cout;}

	//ascesseurs (get et set)
	public int size_H(){return size_h_;}
	public int size_W(){return size_w_;}
	public void size_H(int h){size_h_ = h;}
	public void size_W(int w){size_w_ = w;}
	
	public void num_Texture(int n){num_texture_=n;}
	public int num_Texture(){return num_texture_;}
	
	public Vector2 position(){return position_;}
	public Vector3 position3(){return new Vector3(position_.x,position_.y,0);}
	public void position(Vector2 position){position_=position; maj_box();}
	public void position(float x, float y){position_=new Vector2(x,y);maj_box();}
	public void position(int x, int y){position_=new Vector2(x,y);maj_box();}
	public void position_add(int x, int y){position_.x+=x;position_.y+=y;maj_box();} 
	public void position(Vector3 pos){position_.x=pos.x; position_.y=pos.y;}
	
	public float get_range(){return _range;}
	
	private void maj_box()
	{
		bbox_.set_X((int)position_.x);
		bbox_.set_Y((int)position_.y);
	}
	
	public CollisionBox box(){return bbox_;}
	public void CollisionBox (CollisionBox  s){bbox_ = s;}
	public boolean collision(int x, int y){return bbox_.collision(x, y);}
	public boolean collision(CollisionBox  b){return bbox_.collision(b);}
	
	public String nom() 
	{	
		return _nom;
	}
	public void nom(String n) { _nom=n;}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	//renvoi l'index de la cellule, correspondante aux coord de position, dans un tableau monodimentionnel
	//permet de savoir sur quelle cellule on pointe
	// changer la fonction de place et mettre dans une autre classe?
	public int get_Index_Cellule_Mono(int size_cellule_n, int size_cellule_m, int nb_case_n)
	{
		//calcul coordonnées dans matrice n*m via les coord du monde en px
		int n = (int) (position_.x / size_cellule_n);
		int m = (int) (position_.y / size_cellule_m);
		
		return (n * nb_case_n) + m ; 
	}


	public Matrix3 getMat() {
		return mat_;
	}


	public void setMat(Matrix3 mat) {
		this.mat_ = mat;
	}



	
	public static void main(String [ ] args)
	{
		
	}


	public ArrayList<Integer> getCases_adj() {
		return cases_adj;
	}


	public void setCases_adj(ArrayList<Integer> cases_adj) {
		this.cases_adj = cases_adj;
	}

}
