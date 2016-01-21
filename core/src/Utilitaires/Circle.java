package Utilitaires;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
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
	
	public static float[] make_Circle_Float(float rayon, Vector2 center, int pas)
	{
		int pas_degree = 360 / pas;
		float angle = 0; //angle en degré
		float radian = 0; //angle en radiant
		float[] vertex_array = null;
		Vector2 point = null;
		int j=0;
		
		vertex_array = new float[pas*3];
		for(int i=0; i<pas ; i++)
		{
			//conversion angle en radiant
			radian = (float) Math.toRadians(angle);
			
			float x = (float) (rayon * Math.cos(radian) + center.x);
			float y = (float) (rayon * Math.sin(radian) + center.y);
			
			angle +=pas_degree;
			vertex_array[j] = x;
			j++;
			vertex_array[j] =y;
			j++;
			vertex_array[j] =0;
			j++;
		}
		
		return vertex_array;
	}
	
	
	public static float[] make_Circle_Float_Color(float rayon, Vector2 center, int pas, Color color)
	{
		int pas_degree = 360 / pas;
		float angle = 0; //angle en degré
		float radian = 0; //angle en radiant
		float[] vertex_array = null;
		Vector2 point = null;
		int j=0;
		
		vertex_array = new float[pas*6+6];
		for(int i=0; i<pas ; i++)
		{
			//conversion angle en radiant
			radian = (float) Math.toRadians(angle);
			
			float x = (float) (rayon * Math.cos(radian) + center.x);
			float y = (float) (rayon * Math.sin(radian) + center.y);
			
			angle +=pas_degree;
			
			vertex_array[j] =x;
			vertex_array[j+1] =y;
			vertex_array[j+2] =0;
			vertex_array[j+3] =color.toFloatBits();
			
			if(i%3 == 0)
			{
				vertex_array[j+4] = 0;
				vertex_array[j+5] = 0;
			}
			else if(i%3 == 1)
			{
				vertex_array[j+4] = 1;
				vertex_array[j+5] = 0;
			}
			else 
			{
				vertex_array[j+4] = 1;
				vertex_array[j+5] = 1;
			}
			j+=6;
		}
		
		vertex_array[j] =vertex_array[0];
		vertex_array[j+1] =vertex_array[1];
		vertex_array[j+2] =vertex_array[2];
		vertex_array[j+3] =color.toFloatBits();
		
		
		return vertex_array;
	}
	
	
	public static short[] get_Indice(int pas)
	{
		short[] tab = new short[pas*3];
		int j=0;
		for(short i=0;i<pas;i++)
		{
			tab[j]=0;
			j++;
			tab[j]=(short) (i+1);
			j++;
			tab[j]=(short) (i+2);
			j++;
		}
		
		return tab;
	}
	
	
	public static void main(String [ ] args)
	{
	 
		ArrayList<Vector2> lst = new ArrayList<Vector2>();
		lst = make_Circle(5,new Vector2(1,1),4);
		for(int i=0; i < lst.size(); i++)
			System.out.println((int)lst.get(i).x+"  "+(int)lst.get(i).y);
	}
	
}
