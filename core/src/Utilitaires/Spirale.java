package Utilitaires;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import units.TowerType;


/***
 * 
 * Permet de faires des spirales ronde ou carré dans un tableau
 * 
 * @author Florian
 *
 */


public class Spirale 
{
	enum Direction{BAS,HAUT,GAUCHE,DROITE;}
	
	//Retourne case adjacente suivant un cercle 
	public static ArrayList<Integer> adjacente(int n, int m, int size_case, Vector2 position, int range)
	{
		int nb_cases_adj =0;
		int nb = (int)(range + range + 1);
		nb_cases_adj = nb*nb;

		ArrayList<Integer> adj = new ArrayList<Integer>(); 

		float depart_x = position.x;
		float depart_y = position.y;
		
		//distance centre a centre
		float distance = size_case;
			
		int j=0;
		for(int i=2; i <= 2; i++)
		{
			float thetha = 0;
			float pas = 45/i;
			while(thetha<360)
			{
				double radian = Math.PI * (thetha) / 180;
				
				//position dans le monde
				double x;
				double y;

				x=depart_x +  i*(size_case)*Math.cos(radian);
				y=depart_y +  i*(size_case)*Math.sin(radian);
				
				
				int a = (int) (x/size_case);
				int b = (int) (y/size_case);

				//case associé
				int cell =(int) ((b * m) + a);
				

				System.err.println("cell= "+cell+"   "+x+" y="+y+"  a="+a+"  b="+b+"   thetha= "+thetha);
				
				if(cell<0 || cell >= n*m )
					cell = -1;
				else if(cell%m==0 && x>=0)
					cell = -1;
				else if(cell%m==m-1 && x<0)
					cell = -1;
				
				if(cell != -1)
				{
					adj.add(cell);
					j++;
				}
					
				thetha+=pas;
			}
		}
		
		return adj;
	}
	
	//retourne case adjs suivant une spirale carré
	public static ArrayList<Integer> adjacente2(int pas, int size_case, Vector2 depart, int n, int m, int num_couche)
	{
		ArrayList<Integer> adj = new ArrayList<Integer>();
		int jump = 1;
		Vector2 lastPoint = depart;
		Direction dir = Direction.DROITE;
		int x = (int) (lastPoint.x/size_case);
		int y = (int) (lastPoint.y/size_case);
		int cell =(int) ((y * m) + x);
		adj.add(cell);
		
		for (int a = 0; a < num_couche; a++)
		{     
			int nb=2;
			if(a==num_couche-1)
				nb=3;
			for (int avc = 0; avc < nb; avc++)
		    {
	           for (int jmp = 0; jmp < jump; jmp++)
	           {
	        	   //calcul nouveau point
	                Vector2 newPoint = new_Position(dir,lastPoint, pas);
	                //calcul case
	                x = (int) (newPoint.x/size_case);
					y = (int) (newPoint.y/size_case);
					cell =(int) ((y * m) + x);
					//test case
					cell  = test_cellule(cell, n,  m, dir);
					//si != -1 on ajoute
					if( cell != -1)
						adj.add(cell);
	                
	                lastPoint = newPoint;
	           }
	           dir = change_dir(dir);
		    }
		      jump++;
		}
		
		return adj;
	}
		
	
	//test si une cellule est dans le tableau
	private static int test_cellule(int cell, int n, int m, Direction dir)
	{
		if(cell<0 || cell >= n*m )
			cell = -1;
		else if(cell%m==0 && dir == Direction.DROITE)
			cell = -1;
		else if(cell%m==m-1 && dir == Direction.GAUCHE)
			cell = -1;
		
		return cell;
	}
	
	//cherche nouvelle position
	private static Vector2 new_Position(Direction dir, Vector2 last_p, int pas)
	{
		int x = (int) last_p.x;
		int y = (int) last_p.y;
		switch (dir)
		{
	       case HAUT:
	           return new Vector2(x,(y - pas));
	       case BAS:
	           return new Vector2(x,(y + pas));
	       case DROITE:
	           return new Vector2((x + pas), y);
	       case GAUCHE:
	           return new Vector2((x - pas), y);
	       default:
	           return new Vector2(x,y);
		}
	 }
	
	//Change de direction
	private static Direction change_dir(Direction origine)
	{
		if (origine == Direction.HAUT)
           return Direction.DROITE;
        else if (origine == Direction.BAS)
           return Direction.GAUCHE;
        else if (origine == Direction.DROITE)
           return Direction.BAS;
        else if (origine == Direction.GAUCHE)
           return Direction.HAUT;
        else
           return Direction.HAUT; 
	}
	
	
	
	public static void main(String [ ] args)
	{	    
		int[] tab=
			{
					00,01,02,03,04,05,06,07,
					8,9,10,11,12,13,14,15,
					16,17,18,19,20,21,22,23,
					24,25,26,27,28,29,30,31,
					32,33,34,35,36,37,38,39,
					40,41,42,43,44,45,46,47,
					48,49,50,51,52,53,54,55
			};
		
		int range = 2;
		int cell = 20;
		int n = 7;
		int m = 8;

		ArrayList<Integer> adj;
		//adj = tower.adjacente(n, m,32);
		adj = adjacente2(32, 32, new Vector2(128,64), n, m, range*2);
		for(int i=0; i < adj.size(); i++)
		{
			System.out.print(" "+adj.get(i));
		}
	}
	
}
