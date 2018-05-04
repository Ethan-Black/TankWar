import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 995531914@qq.com
 * @version 创建时间：2017年6月3日 下午1:02:08
 * @project 子弹类
 */
public class Missile
{
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;

	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;

	private int x, y;
	private Direction dir;// 方向
	private boolean live = true;// 是否生存
	private boolean good;// 判断好坏坦克
	private TankClient tankClient;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] missileImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();

	static
	{
		missileImages = new Image[]
		{ tk.getImage(Missile.class.getClassLoader().getResource("images/missileL.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileLU.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileU.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileRU.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileR.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileRD.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileD.gif")),
				tk.getImage(Missile.class.getClassLoader().getResource("images/missileLD.gif")) };

		imgs.put("L", missileImages[0]);
		imgs.put("LU", missileImages[1]);
		imgs.put("U", missileImages[2]);
		imgs.put("RU", missileImages[3]);
		imgs.put("R", missileImages[4]);
		imgs.put("RD", missileImages[5]);
		imgs.put("D", missileImages[6]);
		imgs.put("LD", missileImages[7]);
	}

	public Missile(int x, int y, Direction dir)
	{
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public Missile(int x, int y, Direction dir, boolean good, TankClient tankClient)
	{
		this(x, y, dir);
		this.good = good;
		this.tankClient = tankClient;
	}

	public boolean isLive()
	{
		return live;
	}

	public void setLive(boolean live)
	{
		this.live = live;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public Direction getDir()
	{
		return dir;
	}

	public void setDir(Direction dir)
	{
		this.dir = dir;
	}

	public void draw(Graphics g)
	{
		if (!live)
		{
			tankClient.missiles.remove(this);
			return;
		}
		/*
		 * Color color = g.getColor(); if (good) g.setColor(Color.RED); else
		 * g.setColor(Color.BLACK); g.fillOval(x, y, WIDTH, HEIGHT);
		 * g.setColor(color);
		 */

		switch (dir)
		{
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		}

		move();
	}

	private void move()
	{
		switch (dir)
		{
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		}
		if (x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT)
		{
			live = false;
			// tankClient.missiles.remove(this);
		}
	}

	public Rectangle getRec()
	{
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	// 打到坦克
	public boolean hitTank(Tank t)
	{
		if (this.live && this.getRec().intersects(t.getRec()) && (t.isLive()) && (this.good != t.isGood()))
		{
			if (t.isGood())
			{
				t.setLife(t.getLife() - 20);
				if (t.getLife() <= 0)
				{
					t.setLive(false);
				}
			} else
			{
				t.setLive(false);
			}
			this.live = false;
			// t.setLive(false);
			Explode explode = new Explode(x, y, tankClient);
			tankClient.explodes.add(explode);
			return true;
		}
		return false; // 可以不写else
	}

	public boolean hitTanks(ArrayList<Tank> tanks)
	{
		for (int i = 0; i < tanks.size(); i++)
		{
			if (hitTank(tanks.get(i)))
			{
				return true;
			}
		}
		return false;
	}

	// 打到墙
	public boolean hitWall(Wall w)
	{
		if (this.live && this.getRec().intersects(w.getRec()))
		{
			this.live = false;
			return true;
		}
		return false;
	}

}
