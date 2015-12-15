package units;

public class AirTower extends Tower {

	String nom = "Air Tower";
	int cout = 50;
	float attspeed = 5;
	int damage = 10;
	float dps;
	float dot = 0;
	int slow = 0;
	float zone = 0;
	boolean air = true;
	
	public AirTower(int posX, int posY) {
		super(posX, posY);
		// TODO Auto-generated constructor stub
	}

}
