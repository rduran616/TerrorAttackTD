package Utilitaires;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Circle
{

	public Circle(){}
	
	public static ArrayList<Vector2> make_Circle(float rayon, Vector2 center, int pas)
	{
		int pas_degree = 360 / pas;
		float angle = 0; //angle en degré
		float radian = 0; //angle en radiant
		ArrayList<Vector2> vertex_array = null;
		Vector2 point = null;
		
		vertex_array = new ArrayList<Vector2>();
		for(int i=0; i<pas ; i++)
		{
			//conversion angle en radiant
			radian = (float) Math.toRadians(angle);
			
			float x = (float) (rayon * Math.cos(radian) + center.x);
			float y = (float) (rayon * Math.sin(radian) + center.y);
			
			angle +=pas_degree;
			point = new Vector2(x,y);
			vertex_array.add(point);
		}
		
		return vertex_array;
	}
		
	public static void main(String [ ] args)
	{
	 
		ArrayList<Vector2> lst = new ArrayList<Vector2>();
		lst = make_Circle(5,new Vector2(1,1),4);
		for(int i=0; i < lst.size(); i++)
			System.out.println((int)lst.get(i).x+"  "+(int)lst.get(i).y);
	}
	
}
