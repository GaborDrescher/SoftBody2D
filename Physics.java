
public class Physics
{
	public static void collideWithWall(Sphere a, double restitution, double width, double height)
	{
		Vector pos = a.pos;
		Vector vel = a.vel;

		if(pos.x < a.radius)
		{
			pos.x = a.radius;
			if(vel.x < 0.0)
			{
				vel.x *= -restitution;
			}
		}
		
        if(pos.x > width - a.radius)
		{
			pos.x = width - a.radius;
			if(vel.x > 0.0)
			{
				vel.x *= -restitution;
			}
		}

		if(pos.y < a.radius)
		{
			pos.y = a.radius;
			if(vel.y < 0.0)
			{
				vel.y *= -restitution;
			}
		}
		
        if(pos.y > height - a.radius)
		{
			pos.y = height - a.radius;
			if(vel.y > 0.0)
			{
				vel.y *= -restitution;
			}
		}
	}

	public static void collide(Sphere a, Sphere b, double restitution)
	{
		Vector d = new Vector();
		Vector.sub(d, b.pos, a.pos);

		// distance between circle centres, squared
		double distance_squared = d.lengthquad();

		// combined radius squared
		double radius = b.radius + a.radius;
		double radius_squared = radius * radius;

		// circles too far apart
		if(distance_squared <= radius_squared)
		{
			// distance between circle centres
			double distance = Math.sqrt(distance_squared);

			// normal of collision
			Vector ncoll = new Vector();
			Vector.div_num(ncoll, d, distance);

			// penetration distance
			double dcoll = (radius - distance);

			// inverse masses (0 means, infinite mass, object is static).
			double ima = (a.mass > 0.0) ? (1.0 / a.mass) : 0.0;
			double imb = (b.mass > 0.0) ? (1.0 / b.mass) : 0.0;

			// separation vector
			Vector separation_vector = new Vector();
			Vector.mul_num(separation_vector, ncoll, dcoll / (ima + imb));

			// separate the circles
			Vector.mulsub_num(a.pos, separation_vector, ima);
			Vector.muladd_num(b.pos, separation_vector, imb);

			// combines velocity
			Vector vcoll = new Vector();
			Vector.sub(vcoll, b.vel, a.vel);

			// impact speed
			double vn = Vector.dot(vcoll, ncoll);


			// obejcts are moving away. dont reflect velocity
			if(vn > 0.0)
			{
				return;
			}

			// collision impulse
			double j = -(1.0 + restitution) * (vn) / (ima + imb);

			// collision impusle vector
			Vector impulse = new Vector();
			Vector.mul_num(impulse, ncoll, j);

			// change momentum of the circles
			Vector.mulsub_num(a.vel, impulse, ima);
			Vector.muladd_num(b.vel, impulse, imb);
		}
	}

}
