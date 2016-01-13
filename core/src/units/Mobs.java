package units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;


import Utilitaires.CollisionBox;


public abstract class Mobs
{

	private int life_;
	private int speed_;
	private int money_;
	private int degat_;
	protected boolean _air;	
	private String nom_;
	private Matrix3 mat_;	//Matrice de translation et rotation?
	
	//attribut commun à chaque tour
	private Vector2 position_;  	//position dans le monde
	private CollisionBox  bbox_; 	//boite de collision
	private int size_h_;		 	//taille du sprite en height
	private int size_w_; 			//taille du sprite en width
	private int index;				//index de la position de l'objet dans la cellule position/taille cellule
	private int num_texture_;		//numero de la texture
	private int num_direction_;		//direction que prend le mob 
	private float time_;			//tps pour jouer animation
	
	public int index_chemin_;
	
	
	
	
	public Mobs(int vie, int vitesse, int money, int degat, int h, int w,int air,int num_txt, String nom, CollisionBox box )
	{
		setPosition_(new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2));
		num_direction_=0;
		
		if(h>=0)
			setSize_h_(h);
		else
			setSize_h_(0);
		
		if(w>=0)
			setSize_w_(w);
		else
			setSize_w_(0);
			
		if(nom!=null)
			setNom_(nom);
		else
			setNom_("undefined");
		
		if(money >=0)
			setMoney_(money);
		else
			setMoney_(0);
		
		if(vitesse >=0)
			setSpeed_(vitesse);
		else
			setSpeed_(0);
			
		if(vie >=0)
			setLife_(vie);
		else
			setLife_(10);
		
		if(degat >=0)
			setDegat_(degat);
		else
			setDegat_(1);

		if(box != null)
			setBbox_(box);
		else
			setBbox_(new CollisionBox());
		
		if(air == 1)
			_air = true;
		else
			_air = false;
		
		if(num_txt >=0)
			setNum_texture_(num_txt);
		else
			setNum_texture_(0);
		
		time_ = 0;
	}

	
	public Mobs(Mobs another)
	{
		life_=another.getLife_();
		speed_=another.getSpeed_();
		money_=another.getMoney_();
		degat_=another.getDegat_();
		_air=another.getAir();	
		nom_=another.getNom_();
		mat_=another.getMat();	
		
		time_ = 0;
		
		//attribut commun à chaque tour
		 position_=another.getPosition_();  			//position dans le monde
		 bbox_=another.getBbox_(); 						//boite de collision
		 size_h_=another.getSize_h_();		 			//taille du sprite en height
		 size_w_=another.getSize_w_(); 					//taille du sprite en width
		 index=another.getIndex();						//index de la position de l'objet dans la cellule position/taille cellule
		 num_texture_=another.getNum_texture_();		//numero de la texture
		 num_direction_=another.getNum_direction_();
		
	}
	
	
	//fonction de perte de vie. test: EnVie()? si oui return true sinon faux
	public boolean subir_Degat(int d)
	{
		degat_ -= d;
		if(degat_ <= 0)
			return false;
		
		return true;
	}
	
	
	public int[] get_adjacentes()
	{
		
		return null;
	}
	
	public abstract void execute();
	
	public abstract void destruction();


	public int getLife_() {
		return life_;
	}


	public void setLife_(int life_) {
		this.life_ = life_;
	}


	public int getSpeed_() {
		return speed_;
	}


	public void setSpeed_(int speed_) {
		this.speed_ = speed_;
	}


	public int getMoney_() {
		return money_;
	}


	public void setMoney_(int money_) {
		this.money_ = money_;
	}


	public int getDegat_() {
		return degat_;
	}


	public void setDegat_(int degat_) {
		this.degat_ = degat_;
	}


	public String getNom_() {
		return nom_;
	}


	public void setNom_(String nom_) {
		this.nom_ = nom_;
	}


	public Vector2 getPosition_() {
		return position_;
	}


	public void setPosition_(Vector2 position_) {
		this.position_ = position_;
	}


	public CollisionBox getBbox_() {
		return bbox_;
	}


	public void setBbox_(CollisionBox bbox_) {
		this.bbox_ = bbox_;
	}


	public int getSize_h_() {
		return size_h_;
	}


	public void setSize_h_(int size_h_) {
		this.size_h_ = size_h_;
	}


	public int getSize_w_() {
		return size_w_;
	}


	public void setSize_w_(int size_w_) {
		this.size_w_ = size_w_;
	}


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public int getNum_texture_() {
		return num_texture_;
	}


	public void setNum_texture_(int num_texture_) {
		this.num_texture_ = num_texture_;
	}



	public Matrix3 getMat() {
		return mat_;
	}


	public void setMat(Matrix3 mat) {
		this.mat_ = mat;
	}

	
	public boolean getAir(){return _air;}


	public int getNum_direction_() {
		return num_direction_;
	}


	public void setNum_direction_(int num_direction_) {
		this.num_direction_ = num_direction_;
	}


	public float getTime_() {
		return time_;
	}


	public void setTime_(float time_) {
		this.time_ = time_;
	}
	
	public void add_Time(float time){time_+=time;}

}
