import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main
{
	private static void createScene(List<Sphere> sphereList, List<Spring> springList, int width, int height)
	{
		double stiff = 1000.0;
		double damp = 0.5;
		double burst = 0.4;
		double limit = 1.1;

		
		int n = 20;
		double rad = 2.0;
		double disp = 10.0;

		Sphere[][] spheres = new Sphere[n][n];

		sphereList.add(new Sphere(new Vector(800, 200), new Vector(-50, 0), 20, 200));


		for(int y = 0; y < n; y++)
		{
			for(int x = 0; x < n; x++)
			{
				double sign = 1;
				if((y == 0 && x == 0))
				{
                    //sign = -1;
				}
				spheres[y][x] = new Sphere(new Vector(x * disp + 200, y * disp + 100), new Vector(), rad, 1*sign);
				sphereList.add(spheres[y][x]);
			}
		}

		for(int y = 0; y < n; y ++)
		{
			for(int x = 0; x < n; x ++)
			{
				if(y > 0)
					springList.add(new Spring(spheres[y][x], spheres[y-1][x], stiff, damp, burst, limit));
				if(y > 0 && x > 0)
					springList.add(new Spring(spheres[y][x], spheres[y-1][x-1], stiff, damp, burst, limit));
				if(x > 0)
					springList.add(new Spring(spheres[y][x], spheres[y][x-1], stiff, damp, burst, limit));
				if(y > 0 && x < (n-1))
					springList.add(new Spring(spheres[y][x], spheres[y-1][x+1], stiff, damp, burst, limit));
			}
		}
		
		/*
		double cirRad = 100;
		double offset = 200;
		int edges = 6;
		double n = edges/2;
		double rad = 2.0;
		Sphere[] spheres = new Sphere[edges];
		int i = 0;
		for(double phi = Math.PI/2.0; i < spheres.length; phi+=Math.PI/n, ++i)
		{
		spheres[i] = new Sphere(new Vector(Math.sin(phi) * cirRad + offset, Math.cos(phi) * cirRad + offset), new Vector(0, 0), rad, 10);
		sphereList.add(spheres[i]);
		}

		for(int k = 0; k < spheres.length; k++)
		{
			//for(int m = 2; m <= 8; m*=2)
			{
				springList.add(new Spring(spheres[k], spheres[(k+1) % spheres.length], stiff, damp, burst));
				springList.add(new Spring(spheres[k], spheres[(k+2) % spheres.length], stiff, damp, burst));
			}
		}
		*/




		/*
		Sphere s1 = new Sphere(new Vector(100, 100), new Vector(0, 10), 20, 10);
		Sphere s2 = new Sphere(new Vector(100, 200), new Vector(0, 10), 20, 10);
		Sphere s3 = new Sphere(new Vector(200, 200), new Vector(0, 10), 20, 10);

		springList.add(new Spring(s1, s2, stiff, damp, burst));
		springList.add(new Spring(s1, s3, stiff, damp, burst));
		springList.add(new Spring(s2, s3, stiff, damp, burst));

		sphereList.add(s1);
		sphereList.add(s2);
		sphereList.add(s3);
		*/
		
		

		/*
		Random rand = new Random(System.currentTimeMillis());
		for(int i = 0; i < 2000; i++)
		{
			double bla = 5.0;//rand.nextDouble()*5.0+1.0;

			sphereList.add(new Sphere(
				new Vector(rand.nextInt((int)(width*2.0/3.0)) + width/6.0 + rand.nextDouble(),
					rand.nextInt((int)(height*2.0/3.0)) + height/6.0 + rand.nextDouble()),
				new Vector(),
				bla, bla*2.0*Math.PI));
		}
		*/
	}

	public static void main(String[] args)
	{
		double sphereRestitution = 0.92;
		double timestep = 0.01;
		int sleepTime = 30;
		int width = 1024;
		int height = 768;

		List<Sphere> sphereList = new ArrayList<Sphere>();
		List<Spring> springList = new LinkedList<Spring>();//new ArrayList<Spring>();

		createScene(sphereList, springList, width, height);

		Simulator sim = new Simulator(sphereList, springList, width, height);
		Painter painter = new Painter(sphereList, springList, width, height);

		int i = 0;

        long sumtime = 0;
		while(true)
		{
			long time = System.currentTimeMillis();
			sim.step(timestep, sphereRestitution);
			time = System.currentTimeMillis() - time;
            sumtime += time;

			if(sumtime >= sleepTime)
			{
				painter.paintScene();
			}
 
		}
	}
}
