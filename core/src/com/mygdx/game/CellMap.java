package com.mygdx.game;

import java.util.ArrayList;

/*
 * Classe qui permet de donnée des renseignement sur une cellule de la carte
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
	
	//propriété des elements sur la cellule
	private int obstacle_size_	=0;
	private int chemin_size_	=0;
	private int units_size_		=0;

	private ArrayList<Integer> obstacle_;
	private ArrayList<Integer> units_;
	private ArrayList<Integer> chemin_;
	
	public CellMap(int n, int m, int size, ArrayList<Integer> ob,ArrayList<Integer> u,ArrayList<Integer> c )
	{

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
			setUnits_(new ArrayList<Integer>());
		
		if(c!=null)
		{
			setChemin_(c);
			setChemin_size_(c.size());
		}
		else 
			setChemin_(new ArrayList<Integer>());
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
	
	public void add_Unit(int index)
	{
		units_size_++;
		units_.add(index); //index = index du tableau de tower
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
	
	public int index_Units(int index)
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

	public ArrayList<Integer> getUnits_() {
		return units_;
	}

	public void setUnits_(ArrayList<Integer> units_) {
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
}
