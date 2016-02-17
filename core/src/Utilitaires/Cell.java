package Utilitaires;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import units.Mobs;

public class Cell 
{
	private boolean occupe;
	private Noeud noeud;
	
	private int n_=0; //position en n
	private int m_=0; //position en m
	private int size_cell_=0; //taille en px en n et m
	private int num_case_ =0; //numéro de la case
	
	private Vector2 centre_;
	
	public Cell(boolean b, Noeud node, int n, int m, int px, int num) 
	{

		occupe = b;
		if(node==null)
		{
			noeud = new Noeud();
			noeud.set_Case(num);
			num_case_ = num;
		}
		else
		{
			noeud = node;
			num_case_ = num;
		}
		
		centre_ = new Vector2(((n*px)+px/2),((m*px)+px/2) ); //centre de la cellule en coordonée monde
		
	}

	public Noeud noeud() {
		return noeud;
	}

	public void noeud(Noeud noeud) {
		this.noeud = noeud;
	}


	
	public boolean isOccupe() {
		return occupe;
	}


	
	public void setOccupe(boolean occupe) {
		this.occupe = occupe;
	}

	public int getN_() {
		return n_;
	}

	public void setN_(int n_) {
		this.n_ = n_;
	}
	public int getM_() {
			return m_;
		}
	public void setM_(int m_) {
			this.m_ = m_;
		}
	public int getSize_cell_() {
		return size_cell_;
	}
	public void setSize_cell_(int size_cell_) {
		this.size_cell_ = size_cell_;
	}
	public int getNum_case_() {
		return num_case_;
	}
	public void setNum_case_(int num_case_) {
		this.num_case_ = num_case_;
	}

	
	public Vector2 centre() 
	{
		return centre_;
	}
}
