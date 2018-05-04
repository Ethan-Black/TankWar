import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author 995531914@qq.com
 * @version ����ʱ�䣺2017��6��3�� ����1:07:20
 * @project ǽ����
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

	// ��ײ���Ҫ��
	public Rectangle getRec()
	{
		return new Rectangle(x, y, w, h);
	}

}
