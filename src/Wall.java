import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author 995531914@qq.com
 * @version 创建时间：2017年6月3日 下午1:07:20
 * @project 墙体类
 */
public class Wall
{
	private int x, y, w, h;
	private TankClient tankClient;

	public Wall(int x, int y, int w, int h, TankClient tankClient)
	{
		// super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tankClient = tankClient;
	}

	public void draw(Graphics g)
	{
		Color color = g.getColor();
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, w, h);
		g.setColor(color);
	}

	// 碰撞检测要用
	public Rectangle getRec()
	{
		return new Rectangle(x, y, w, h);
	}

}
