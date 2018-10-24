
public final class Vector//here a 2D vector
{
	public double x;
	public double y;

	public Vector()
	{
		this(0,0);
	}

	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public Vector(Vector other)
	{
		x = other.x;
		y = other.y;
	}

	public final void reset()
	{
		x = 0.0;
		y = 0.0;
	}

	public final void assign(Vector other)
	{
		x = other.x;
		y = other.y;
	}

	public final double length()
	{
		return Math.sqrt(x*x + y*y);
	}

	public final double lengthquad()
	{
		return x*x + y*y;
	}

	public final void normalize()
	{
		double len = length();

		//if(len == 0.0)  throw new RuntimeException("div by zero");
		if(len == 0.0) return;

		x /= len;
		y /= len;
	}

	public final String toString()
	{
		return "("+x+", "+y+")";
	}

	public final boolean equals(Vector other)
	{
		return (x == other.x) && (y == other.y);
	}

	public static final void add(Vector dest, final Vector a, final Vector b)
	{
		dest.x = a.x + b.x;
		dest.y = a.y + b.y;
	}

	public static final void sub(Vector dest, final Vector a, final Vector b)
	{
		dest.x = a.x - b.x;
		dest.y = a.y - b.y;
	}

	public static final double dot(final Vector a, final Vector b)
	{
		return a.x * b.x + a.y * b.y;
	}

	public static final void mul(Vector dest, final Vector a, final Vector b)
	{
		dest.x = a.x * b.x;
		dest.y = a.y * b.y;
	}

	public static final void div(Vector dest, final Vector a, final Vector b)
	{
		if(b.x == 0.0 || b.y == 0.0) throw new RuntimeException("div by zero");

		dest.x = a.x / b.x;
		dest.y = a.y / b.y;
	}


	public static final void add_num(Vector dest, final Vector a, final double b)
	{
		dest.x = a.x + b;
		dest.y = a.y + b;
	}

	public static final void sub_num(Vector dest, final Vector a, final double b)
	{
		dest.x = a.x - b;
		dest.y = a.y - b;
	}

	public static final void mul_num(Vector dest, final Vector a, final double b)
	{
		dest.x = a.x * b;
		dest.y = a.y * b;
	}

	public static final void div_num(Vector dest, final Vector a, final double b)
	{
		//if(b == 0.0) throw new RuntimeException("div by zero");

		dest.x = a.x / b;
		dest.y = a.y / b;
	}

	public static final void muladd(Vector dest, final Vector a, final Vector b)
	{
		dest.x += a.x * b.x;
		dest.y += a.y * b.y;
	}

	public static final void muladd_num(Vector dest, final Vector a, final double b)
	{
		dest.x += a.x * b;
		dest.y += a.y * b;
	}

	public static final void mulsub(Vector dest, final Vector a, final Vector b)
	{
		dest.x -= a.x * b.x;
		dest.y -= a.y * b.y;
	}

	public static final void mulsub_num(Vector dest, final Vector a, final double b)
	{
		dest.x -= a.x * b;
		dest.y -= a.y * b;
	}

	public Vector copy()
	{
		Vector out = new Vector(this);
		return out;
	}
}