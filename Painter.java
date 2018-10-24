import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JFrame;

public class Painter extends JFrame
{
	private List<Sphere> sphereList;
	private List<Spring> springList;
	private double x, y;
	private PaintCanvas pc;

	public Painter(List<Sphere> sphereList, List<Spring> springList, int x, int y)
	{
		this.sphereList = sphereList;
		this.springList = springList;

		this.x = x;
		this.y = y;

		pc = new PaintCanvas();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(pc);
		pack();
		setVisible(true);
		pc.init();
	}

	public void paintScene()
	{
		pc.paint();
	}

	private class PaintCanvas extends Canvas
	{
		private BufferStrategy buffer;
		private BufferedImage bi;
		private Graphics2D g2d;
		private Graphics g;

		public PaintCanvas()
		{
			this.setPreferredSize(new Dimension((int) x, (int) y));
		}

		public void init()
		{
			createBufferStrategy(2);
			buffer = getBufferStrategy();
			bi = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage((int) x, (int) y);
		}

		public void paint()
		{
			//clear back buffer
			g2d = bi.createGraphics();
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, getWidth(), getHeight());

			//paint HERE on g2d
			for(Sphere s : sphereList)
			{
				g2d.setColor(Color.BLACK);
				s.paint(g2d);
			}
			for(Spring sp : springList)
			{
				g2d.setColor(new Color(Math.min((int) Math.rint(Math.abs(((sp.oldlen - sp.stablen) / sp.stablen) * 4000)), 255), 0, 0));
				sp.paint(g2d);
			}

			//blit image and flip
			g = buffer.getDrawGraphics();
			g.drawImage(bi, 0, 0, null);
			if(!buffer.contentsLost())//delete this if?
			{
				buffer.show();
			}
		}
	}
}
