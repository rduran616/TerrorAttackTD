package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import Utilitaires.CollisionBox;

public class Tir 
{
	private int degat_;				//Degat infligé lors de la collisin
	
	private Vector2 texture_size_;	//taille en x et y de la texture ( px * px)
	private CollisionBox  bbox_; 	//boite de collision
	
	private Vector2 position_;  	//position dans le monde
	private Vector2 vitesse_;		//vecteur vitesse de deplcament
	public float vitesse_projectil_;
	
	private float time_changement_anim; //temps entre chaque animation (?)
	private float time_actu_;			//temps actuel (?)
	private float time_total_;			//temp total avant impact (?)
	private float time_;
	
	private int num_texture_;
	private Vector2 size_txt_;
	private int max_texture_;
	
	public Tir(String n, int h, int w, int n_txt, int degat, CollisionBox bbox, int nb_anim) 
	{
		//System.err.println("create tir");
		bbox_(bbox);
		degat_ = degat;
		num_Texture(n_txt);
		max_Texture(nb_anim);
		size_Txt(new Vector2(w,h));
		
		position(new Vector2(0,0));
		vitesse(new Vector2(0,0));
	}
	
	public Tir(Tir modele) 
	{
		degat_=modele.degat_;	
		texture_size_ = modele.texture_Size_();
		bbox_ = modele.bbox_;	
		position_ = modele.position_;
		vitesse_ = modele.vitesse_;	
		time_changement_anim = modele.time_changement_anim;
		time_actu_ = modele.time_actu_;
		time_total_ = modele.time_total_;
		num_texture_ = modele.num_texture_;
		size_txt_ = modele.size_txt_;
		max_texture_ = modele.max_texture_;
	}

	public Tir() {
		// TODO Auto-generated constructor stub
	}

	public void init(int degat, Vector2 position, Vector2 vitesse, float time, float time_destruction)
	{
		position(position);
		vitesse(vitesse);
		degat_ = degat;
		time_changement_anim = time;
		time_total_ = time_destruction;
		time_actu_ = 0;
	}

	
	public boolean onExectute()
	{
		//test collision
		
		//changement position
		position_.add(vitesse());
		time_total_ -=Gdx.graphics.getDeltaTime()*1000;//passage en ms
		System.err.println(time_total_);
		if(time_total_<0)
		{
			return true; //on supprime
		}
		
		return false;
	}
	
	
	
	
	public Vector2 texture_Size_() {
		return texture_size_;
	}

	public void texture_Size_(Vector2 texture_size_) {
		this.texture_size_ = texture_size_;
	}

	public CollisionBox bbox_() {
		return bbox_;
	}

	public void bbox_(CollisionBox bbox_) {
		this.bbox_ = bbox_;
	}

	public Vector2 position() {
		return position_;
	}

	public void position(Vector2 position_) {
		this.position_ = position_;
	}

	public Vector2 vitesse() {
		return vitesse_;
	}

	public void vitesse(Vector2 vitesse_) {
		this.vitesse_ = vitesse_;
	}

	public int num_Texture() {
		return num_texture_;
	}

	public void num_Texture(int num_texture_) {
		this.num_texture_ = num_texture_;
	}

	public Vector2 size_Txt() {
		return size_txt_;
	}

	public void size_Txt(Vector2 size_txt_) {
		this.size_txt_ = size_txt_;
	}

	public int max_Texture() {
		return max_texture_;
	}

	public void max_Texture(int max_texture_) {
		this.max_texture_ = max_texture_;
	}
	
	public float time(){return time_;}
	public void time(float t){ time_ = t;}
	

	public void add_Time(float deltaTime) {time_ += deltaTime;}
}
