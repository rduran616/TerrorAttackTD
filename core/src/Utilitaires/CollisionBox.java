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
	private int x_;
	private int y_;
	private int w_;
	private int h_;
	
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
		if((box2.x() >= x_ + w_) || (box2.x() + box2.w() <= x_)|| (box2.y() >= y_ + h_)|| (box2.y() + box2.h() <= y_)) 
          return false; 
	   else
          return true; 
	}
	
	public void set_Collision_box(int x, int y, int w, int h)
	{
		if(x>=0 && y>=0 && h>=0 && w>=0)
		{
			x_=x;
			y_=y;
			h_=h;
			w_=w;
		}
		else
		{
			x_=0;
			y_=0;
			h_=0;
			w_=0;
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
	

}