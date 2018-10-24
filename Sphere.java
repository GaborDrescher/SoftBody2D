import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Sphere
{
	public Vector pos;
	public Vector vel;
	public Vector force;
	public double radius;
	public double mass;
	public List<ArrayList<Sphere>> list;


	public Sphere(Vector pos, Vector vel, double radius, double mass)
	{
		this.pos = pos;
		this.vel = vel;
		this.force = new Vector(0.0, 0.0);
		this.radius = radius;
		this.mass = mass;

		list = new ArrayList<ArrayList<Sphere>>();
	}

	public void paint(Graphics2D g)
	{
		g.drawOval((int)Math.rint(pos.x - radius),
			(int)Math.rint(pos.y - radius),
			(int)Math.rint(radius * 2.0),
			(int)Math.rint(radius * 2.0));
	}

	public String toString()
	{
		return "pos: "+pos+" vel: "+vel;
	}
}
