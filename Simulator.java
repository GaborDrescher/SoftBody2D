import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Simulator
{
	private List<Sphere> sphereList;
	private List<Spring> springList;
	private double x, y;
	private Grid grid;
	private Vector gravity;

	public Simulator(List<Sphere> sphereList, List<Spring> springList, int x, int y)
	{
		this.sphereList = sphereList;
		this.springList = springList;

		this.x = x;
		this.y = y;

		grid = new Grid(x, y, 10);
		gravity = new Vector(0, 10);
	}

	public void step(double dt, double restitution)
	{
		ListIterator<Spring> iter = springList.listIterator();
		while(iter.hasNext())
		{
			Spring current = iter.next();

			if(current.remove)
			{
				iter.remove();
			}
		}

		for(Spring spr : springList)
		{
			spr.applySpringForce();
		}

		for(Sphere s : sphereList)
		{
			if(s.mass <= 0.0)
			{
				continue;//fixed object
			}

			Vector.add(s.force, s.force, gravity);
			Vector.mul_num(s.force, s.force, dt);
			Vector.add(s.vel, s.vel, s.force);
			Vector.muladd_num(s.pos, s.vel, dt);
			s.force.reset();
		}

		//for(int i = 0; i < 10; i++)
		//{
		//	for(Spring spr : springList)
		//	{
		//		spr.iterateToConstraints();
		//	}
		//}

		handleCollisions(restitution);
	}

	private void handleCollisions(double restitution)
	{
		grid.fill(sphereList);

		for(Sphere a : sphereList)
		{
			List<ArrayList<Sphere>> list = a.list;

			for(ArrayList<Sphere> cellList : list)
			{
				for(Sphere b : cellList)
				{
					if(a != b)
					{
						Physics.collide(a, b, restitution);
					}
				}
			}

			Physics.collideWithWall(a, restitution, x, y);

			grid.clear(a);
		}
	}
}
