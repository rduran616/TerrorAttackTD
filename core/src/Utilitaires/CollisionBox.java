package Utilitaires;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;



/****
 * 
 *     
 *    	0,0 ****************** w,0
 *    	*						*
 * 		*						*
 * 		*						*
 * 		*						*
 * 		0,h ******************* w,h
 *   
 * @author Florian
 *
 */


public final class CollisionBox 
{
	private int x_; //position en x
	private int y_; //position en y
	private int w_; //taille en width
	private int h_; //taille en height
	
	public CollisionBox()
	{
		x_=0;
		y_=0;
		h_=0;
		w_=0;
	}
	
	public CollisionBox(int x, int y, int w, int h)
	{
		set_Collision_box(x,y,w,h);
	} 
	
	public boolean collision(int curseur_x, int curseur_y)
	{
		System.err.println(x_+"  "+y_);
		
	   if (curseur_x >= x_ && curseur_x < x_ + w_  && curseur_y >= y_ && curseur_y < y_ + h_)
	   {
		 System.err.println("collision");
		 return true;
	   }
	   else
	      return false;
	}
	
	public boolean collision(CollisionBox box2)
	{
		if((box2.x() >= x_ + w_) || 
			(box2.x() + box2.w() <= x_)||
			(box2.y() >= y_ + h_)||
			(box2.y() + box2.h() <= y_)) 
          return false; 
	   else
          return true; 
	}
	
	public void set_Collision_box(int x, int y, int w, int h)
	{
		x_=x;
		y_=y;
		h_=h;
		w_=w;
	}
	
	public void set_Collision_box(float x, float y, float w, float h)
	{
		x_=(int)x;
		y_=(int)y;
		h_=(int)h;
		w_=(int)w;
	}
	
	public void set_Collision_box(CollisionBox box2)
	{
		if(box2!= null)
		{
			x_=box2.get_X();
			y_=box2.get_Y();
			h_=box2.get_H();
			w_=box2.get_W();
		}
	}
	
	
	public void show()
	{
		//System.err.println(x_+" "+y_+" "+w_+" "+h_);
		SpriteBatch sb_ = new SpriteBatch();
		ShapeRenderer  shapeRenderer = new ShapeRenderer();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.identity();
		shapeRenderer.rect(x_, y_, w_, h_);
		shapeRenderer.end();
	
	}
	
	public void set_X(int x){x_=x;}
	public void set_Y(int y){y_=y;}
	public int get_X(){return x_;}
	public int get_Y(){return y_;}
	public void set_H(int h){h_=h;}
	public void set_W(int w){w_=w;}
	public int get_H(){return h_;}
	public int get_W(){return w_;}
	
	
	private int x(){return x_;}
	private int y(){return y_;}
	private int h(){return h_;}
	private int w(){return w_;}
	
	
	public static void main(String [ ] args)
	{
	      CollisionBox box1;
	      CollisionBox box2;
	      
	      box1 = new CollisionBox(10,10,32,32);
	      box2 = new CollisionBox(41,41,32,32);
	      
	      if(box1.collision(box2)==true)
	    	  System.err.println("collision");
	      else
	    	  System.err.println("no collision");
	}
	
	
	public void print()
	{
		System.out.println("x= "+x_+" y= "+y_+" w= "+w_+" h="+h_);
	}

}
