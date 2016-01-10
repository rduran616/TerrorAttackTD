package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import Utilitaires.Noeud;
import units.TowerType;

/*
 * Classe qui permet de donnée des renseignements sur une cellule de la carte
 * 
 * Chaque tableau contiens une liste fini de cellule
 * 
 * -1 = vide
 */

public final class CellMap 
{
	//propriété général de la cellulue
	private int n_=0; //position en n
	private int m_=0; //position en m
	private int size_cell_=0; //taille en px en n et m
	private int num_case_ =0; //numéro de la case
	
	//propriété des elements sur la cellule
	private int obstacle_size_	=0;
	private int chemin_size_	=0;
	private int units_size_		=0;
	private int mobs_size_		=0;

	private ArrayList<Integer> obstacle_;
	private ArrayList<TowerType> units_;
	private ArrayList<Integer> chemin_;
	private ArrayList<Integer> mobs_;
	
	private TypeObjet type;
	
	private Noeud noeud; //pour algorithme A*
	private Vector2 centre_;
	
	//une cellule de carte
	//map_x = taille de la carte en x en px
	public CellMap(int map_x, int map_y,int num_case,int n, int m, int size, ArrayList<Integer> ob,ArrayList<TowerType> u,ArrayList<Integer> c,ArrayList<Integer> mobs )
	{

		int col =m;
		int row =n;
		
		centre_ = new Vector2(((row*size)+size/2),((col*size)+size/2) ); //centre de la cellule en coordonée monde

		if(n>=0)
			setN_(n);
		if(m>=0)
			setM_(m);
		if(size>=0)
			setSize_cell_(size);
		
		if(ob!=null)
		{
			setObstacle_(ob);
			setObstacle_size_(ob.size());
		}
		else 
			setObstacle_(new ArrayList<Integer>());
		
		if(u!=null)
		{
			setUnits_(u);
			setUnits_size_(u.size());
		}
		else 
			setUnits_(new ArrayList<TowerType>());
		
		if(c!=null)
		{
			setChemin_(c);
			setChemin_size_(c.size());
		}
		else 
			setChemin_(new ArrayList<Integer>());
		
		if(c!=null)
		{
			setMobs_(mobs);
			setMobs_size_(mobs.size());
		}
		else 
			setMobs_(new ArrayList<Integer>());
		
		noeud = new Noeud();
		noeud.set_Case(num_case);
		num_case_ = num_case;
	}

	
	public void add_Obstacle(int index)
	{
		obstacle_size_++;
		obstacle_.add(index);
	} 
	
	public void add_Chemin(int index)
	{
		chemin_size_++;
		chemin_.add(index);
	} 
	
	public void add_Unit(TowerType index)
	{
		units_size_++;
		units_.add(index); 
	} 
	
	public void remove_Obstacle(int index)
	{
		obstacle_size_--;
		if(obstacle_size_ < 0)
			obstacle_size_=0;
		
		obstacle_.remove(index);
	}
	
	public void remove_Unit(int index)
	{
		units_size_--;
		if(units_size_ < 0)
			units_size_=0;
		
		units_.remove(index);
	}
	
	public void remove_Chemin(int index)
	{
		chemin_size_--;
		if(chemin_size_ < 0)
			chemin_size_=0;
		
		chemin_.remove(index);
	}
	
	
	
	public boolean est_Vide()
	{
		if(obstacle_size_ == 0 && chemin_size_ ==0 && units_size_ ==0)
			return true;
		else
			return false;
	}
	
	public TowerType index_Units(int index)
	{
		return units_.get(index);
	}
	
	public ArrayList<Integer> getObstacle_() {
		return obstacle_;
	}
	
	public void setObstacle_(ArrayList<Integer> obstacle_) {
		this.obstacle_ = obstacle_;
	}

	public int getChemin_size_() {
		return chemin_size_;
	}

	public void setChemin_size_(int chemin_size_) {
		this.chemin_size_ = chemin_size_;
	}

	public int getUnits_size_() {
		return units_size_;
	}

	public void setUnits_size_(int units_size_) {
		this.units_size_ = units_size_;
	}

	public int getSize_cell_() {
		return size_cell_;
	}

	public void setSize_cell_(int size_cell_) {
		this.size_cell_ = size_cell_;
	}

	public int getM_() {
		return m_;
	}

	public void setM_(int m_) {
		this.m_ = m_;
	}

	public int getN_() {
		return n_;
	}

	public void setN_(int n_) {
		this.n_ = n_;
	}

	public ArrayList<TowerType> getUnits_() {
		return units_;
	}

	public void setUnits_(ArrayList<TowerType> units_) {
		this.units_ = units_;
	}

	public ArrayList<Integer> getChemin_() {
		return chemin_;
	}

	public void setChemin_(ArrayList<Integer> chemin_) {
		this.chemin_ = chemin_;
	}

	public int getObstacle_size_() {
		return obstacle_size_;
	}
	
	public void setObstacle_size_(int obstacle_size_) {
		this.obstacle_size_ = obstacle_size_;
	}


	public ArrayList<Integer> getMobs_() {
		return mobs_;
	}


	public void setMobs_(ArrayList<Integer> mobs_) {
		this.mobs_ = mobs_;
	}


	public int getMobs_size_() {
		return mobs_size_;
	}


	public void setMobs_size_(int mobs_size_) {
		this.mobs_size_ = mobs_size_;
	}


	public TypeObjet getType() {
		return type;
	}


	public void setType(TypeObjet type) {
		this.type = type;
	}


	public Noeud noeud() {
		return noeud;
	}


	public void noeud(Noeud noeud) {
		this.noeud = noeud;
	}


	public int getNum_case_() {
		return num_case_;
	}


	public void setNum_case_(int num_case_) {
		this.num_case_ = num_case_;
	}
	
	public Vector2 getPosition(){return new Vector2(n_,m_);}
	public void setPosition(Vector2 p){n_=(int) p.x;m_=(int) p.y;}


	public Vector2 centre() {
		return centre_;
	}


	public void centre(Vector2 centre_) {
		this.centre_ = centre_;
	}
}
