import java.awt.Graphics2D;

public class Spring
{
	private Sphere sphere1, sphere2;
	public double stiff, damp, stablen, burst, limit;
	public boolean remove;
	public double oldlen;

	public Spring(Sphere sphere1, Sphere sphere2, double stiff, double damp, double burst, double limit)
	{
		this.sphere1 = sphere1;
		this.sphere2 = sphere2;
		this.stiff = stiff;
		this.damp = damp;
		this.burst = burst;
		this.remove = false;
		this.limit = limit;

		Vector oldpos = new Vector();
		Vector.sub(oldpos, sphere2.pos, sphere1.pos);

		this.stablen = oldpos.length();
		this.oldlen = stablen;
	}

	public void applySpringForce()
	{
		Vector dir = new Vector();
		Vector.sub(dir, sphere2.pos, sphere1.pos);
		double len = dir.length();
		dir.normalize();

		oldlen = len;

		//remove if to long
        double foo = (len - stablen) / stablen;
        //System.out.println(foo);
		if(foo > burst)
		{
			remove = true;
			return;
		}


		//elasticity
		Vector force = new Vector(dir);
		double f = ((stablen - len) * stiff);
		Vector.mul_num(force, force, f);
		Vector.sub(sphere1.force, sphere1.force, force);
		Vector.add(sphere2.force, sphere2.force, force);

		//damping
		force.assign(sphere1.vel);
		double coA = Vector.dot(force, dir);
		force.assign(sphere2.vel);
		double coB = Vector.dot(force, dir);

		force.assign(dir);
		Vector.mul_num(force, force, (coA-coB) * damp * sphere2.mass);
		Vector.add(sphere2.force, sphere2.force, force);

		force.assign(dir);
		Vector.mul_num(force, force, (coA-coB) * damp * sphere1.mass);
		Vector.sub(sphere1.force, sphere1.force, force);
	}

	public void iterateToConstraints()
	{
		Vector dir = new Vector();
		Vector.sub(dir, sphere2.pos, sphere1.pos);
		double len = dir.length();
		dir.normalize();

		double maxlen = stablen * limit;
		double minlen = stablen / limit;
		

		double diffMax = maxlen - len;
		double diffMin = len - minlen;

		

		if(len > maxlen)
		{
			double eps = 1.0*diffMax;
			if(sphere1.mass > 0)
			Vector.mulsub_num(sphere1.pos, dir, eps);
			if(sphere2.mass > 0)
			Vector.muladd_num(sphere2.pos, dir, eps);
		}
		else if(len < minlen)
		{
			double eps = 1.0*diffMin;
			if(sphere1.mass > 0)
			Vector.muladd_num(sphere1.pos, dir, eps);
			if(sphere2.mass > 0)
			Vector.mulsub_num(sphere2.pos, dir, eps);
		}
	}

	public void paint(Graphics2D g)
	{
		g.drawLine((int)Math.rint(sphere1.pos.x),
			(int)Math.rint(sphere1.pos.y),
			(int)Math.rint(sphere2.pos.x),
			(int)Math.rint(sphere2.pos.y));
	}

	public boolean equals(Spring other)
	{
		return (other.sphere1 == sphere1 || other.sphere2 == sphere1)
			&& (other.sphere1 == sphere2 || other.sphere2 == sphere2);
	}
}
