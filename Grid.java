import java.util.ArrayList;
import java.util.List;

public class Grid
{
	private ArrayList<Sphere>[][] grid;
	private double cellsize;


	public Grid(double width, double height, double cellsize)
	{
		reSet(width, height, cellsize);
	}

	public void reSet(double width, double height, double cellsize)
	{
		this.cellsize = cellsize;

		final int he = (int)Math.ceil(height / cellsize);
		final int wi = (int)Math.ceil(width / cellsize);

		grid = new ArrayList[he][wi];

		for(int i = 0; i < grid.length; i++)
		{
			for(int k = 0; k < grid[0].length; k++)
			{
				grid[i][k] = new ArrayList<Sphere>();
			}
		}
	}

	public void clear(Sphere s)
	{
		for(ArrayList<Sphere> list : s.list)
		{
			list.remove(s);
		}

		s.list.clear();
	}

	public void fill(List<Sphere> slist)
	{
		final Vector pos = new Vector();

		for(final Sphere s : slist)
		{
			Vector.sub_num(pos, s.pos, s.radius);

			final int beginX = Math.max((int)(pos.x / cellsize), 0);
			final int beginY = Math.max((int)(pos.y / cellsize), 0);

			Vector.add_num(pos, pos, s.radius * 2.0);

			final int endX = Math.min((int)(pos.x / cellsize), grid[0].length-1);
			final int endY = Math.min((int)(pos.y / cellsize), grid.length-1);

			for(int y = beginY; y <= endY; ++y)
			{
				for(int x = beginX; x <= endX; ++x)
				{
					ArrayList<Sphere> cell = grid[y][x];
					cell.add(s);
					s.list.add(cell);
				}
			}
		}
	}
}