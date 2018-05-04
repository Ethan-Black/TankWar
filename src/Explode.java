import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * @author 995531914@qq.com
 * @version 创建时间：2017年6月3日 下午1:06:43
 * @project 爆炸类
 */
public class Explode
{
	private int x, y;
	private boolean live = true;
	/*
	 * private int[] diameter = { 4, 7, 12, 18, 26, 32, 49, 30, 14, 6 };
	 */

	private static Toolkit toolkit = Toolkit.getDefaultToolkit();

	private static Image[] imgs =
	{
			// 注意理解下面几个语句，用到反射
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
			toolkit.getImage(Explode.class.getClassLoader().getResource("images/9.gif")), };
	private int step = 0;
	private TankClient tankClient;
	private boolean init = false;

	public Explode(int x, int y, TankClient tankClient)
	{
		// super();
		this.x = x;
		this.y = y;
		this.tankClient = tankClient;
	}

	public void draw(Graphics g)
	{
		if (!init)
		{
			for (int i = 0; i < imgs.length; i++)
			{
				g.drawImage(imgs[i], -100, -100, null);
			}
			init = true;
		}

		if (!live)
		{
			tankClient.explodes.remove(this);
			return;
		}

		if (step == imgs.length)
		{
			live = false;
			step = 0;
			return;
		}

		/*
		 * Color color = g.getColor(); g.setColor(Color.ORANGE); g.fillOval(x,
		 * y, diameter[step], diameter[step]); g.setColor(color);
		 */

		g.drawImage(imgs[step], x, y, null);

		step++;
	}
}
