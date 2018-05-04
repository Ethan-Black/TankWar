import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author 995531914@qq.com
 * @version 创建时间：2017年6月3日 下午1:11:32
 * @project 坦克类
 */
public class Tank
{
	public static final int XSPEED = 5;// 移动速度
	public static final int YSPEED = 5;

	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;

	private int x, y;// 当前坐标
	private int oldX, oldY;// 之前坐标
	private boolean bL = false, bU = false, bR = false, bD = false;// 移动方向标志位
	private TankClient tankClient;
	private boolean good;// 区分好坏坦克
	private boolean live = true;// 坦克是否死亡
	private int life = 100;// 血量

	private static Random random = new Random();

	/*
	 * enum Direction { L, LU, U, RU, R, RD, D, LD, STOP };
	 */

	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;

	private int step = (random.nextInt(12) + 3);
	private BloodBar bloodBar = new BloodBar();

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImages = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	// 静态块加载（注意此方法的优点）
	static
	{
		tankImages = new Image[]
		{ tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankLU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankRU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankRD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankLD.gif")) };

		imgs.put("L", tankImages[0]);
		imgs.put("LU", tankImages[1]);
		imgs.put("U", tankImages[2]);
		imgs.put("RU", tankImages[3]);
		imgs.put("R", tankImages[4]);
		imgs.put("RD", tankImages[5]);
		imgs.put("D", tankImages[6]);
		imgs.put("LD", tankImages[7]);

	}

	public Tank(int x, int y, boolean good)
	{
		super();
		this.x = x;
		this.y = y;
		this.good = good;
	}

	public Tank(int x, int y, boolean good, Direction dir, TankClient tankClient)
	{
		this(x, y, good);
		this.dir = dir;
		this.tankClient = tankClient;
	}

	public int getLife()
	{
		return life;
	}

	public void setLife(int life)
	{
		this.life = life;
	}

	public boolean isGood()
	{
		return good;
	}

	public void setGood(boolean good)
	{
		this.good = good;
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

	public void draw(Graphics g)
	{
		if (!live)
		{
			if (!good)
			{
				tankClient.tanks.remove(this);
			}
			return;
		}
		/*
		 * Color color = g.getColor(); if (good) { g.setColor(Color.RED); } else
		 * { g.setColor(Color.GRAY); } g.fillOval(x, y, WIDTH, HEIGHT);
		 * g.setColor(color);
		 */
		if (good)
		{
			bloodBar.draw(g);
		}

		switch (ptDir)
		{
		case L:
			// g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y +
			// Tank.HEIGHT / 2);
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			// g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y);
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			// g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x +
			// Tank.WIDTH / 2, y);
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			// g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x +
			// Tank.WIDTH, y);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			// g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x +
			// Tank.WIDTH, y + Tank.HEIGHT / 2);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			// g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x +
			// Tank.WIDTH, y + Tank.HEIGHT);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			// g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x +
			// Tank.WIDTH / 2, y + Tank.HEIGHT);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			// g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y +
			// Tank.HEIGHT);
			break;
		}

		move();
	}

	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();// 得到键盘输入
		switch (key)
		{
		case KeyEvent.VK_F2:// F2坦克复活
			if (!this.live)
			{
				this.live = true;
				this.life = 100;
			}
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirection();
	}

	// 键盘弹起时确定所按下的键盘
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		switch (key)
		{
		case KeyEvent.VK_CONTROL:
			// tankClient.myMissile = fire();
			fire();
			break;
		case KeyEvent.VK_A:
			superFire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		locateDirection();
	}

	public void locateDirection()
	{
		if (bL && !bU && !bR && !bD)
			dir = Direction.L;
		else if (bL && bU && !bR && !bD)
			dir = Direction.LU;
		else if (!bL && bU && !bR && !bD)
			dir = Direction.U;
		else if (!bL && bU && bR && !bD)
			dir = Direction.RU;
		else if (!bL && !bU && bR && !bD)
			dir = Direction.R;
		else if (!bL && !bU && bR && bD)
			dir = Direction.RD;
		else if (!bL && !bU && !bR && bD)
			dir = Direction.D;
		else if (bL && !bU && !bR && bD)
			dir = Direction.LD;
		else if (!bL && !bU && !bR && !bD)
			dir = Direction.STOP;
	}

	public void move()
	{
		this.oldX = x;
		this.oldY = y;

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
		case STOP:
			break;
		}
		if (dir != Direction.STOP)
		{
			ptDir = dir;
		}
		if (x < 0)
			x = 0;
		if (y < 30)
			y = 30;
		if (x + Tank.WIDTH > TankClient.GAME_WIDTH)
			x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT)
			y = TankClient.GAME_HEIGHT - Tank.HEIGHT;

		if (!good)
		{
			Direction[] directions = Direction.values();
			if (step == 0)
			{
				step = random.nextInt(12) + 3;
				int rn = random.nextInt(directions.length);
				dir = directions[rn];
			}
			step--;
			if (random.nextInt(40) > 38)
			{
				this.fire();
			}
		}
	}

	// 开火
	public Missile fire()
	{
		if (!live)
		{
			return null;
		}
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile missile = new Missile(x, y, ptDir, good, this.tankClient);
		tankClient.missiles.add(missile);
		return missile;
	}

	public Missile fire(Direction direction)
	{
		if (!live)
		{
			return null;
		}
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile missile = new Missile(x, y, direction, good, this.tankClient);
		tankClient.missiles.add(missile);
		return missile;
	}

	// 放大招
	public void superFire()
	{
		Direction[] directions = Direction.values();
		for (int i = 0; i < 8; i++)
		{
			fire(directions[i]);
		}
	}

	public Rectangle getRec()
	{
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public void stay()
	{
		x = oldX;
		y = oldY;
	}

	// 与墙体的碰撞检测
	public boolean collideWithWall(Wall w)
	{
		if (this.live && this.getRec().intersects(w.getRec()))
		{
			this.stay();
			return true;
		}
		return false;
	}

	// 与坦克的碰撞检测
	public boolean collideWithTanks(ArrayList<Tank> tanks)
	{
		for (int i = 0; i < tanks.size(); i++)
		{
			Tank tank = tanks.get(i);
			if (this != tank)
			{
				if (this.live && tank.isLive() && this.getRec().intersects(tank.getRec()))
				{
					this.stay();
					tank.stay();
					return true;
				}
			}
		}
		return false;
	}

	// 吃血块
	public boolean eat(Blood b)
	{
		if (this.live && b.isLive() && this.getRec().intersects(b.getRect()))
		{
			this.life = 100;
			b.setLive(false);
			return true;
		}
		return false;
	}

	private class BloodBar
	{
		public void draw(Graphics g)
		{
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y - 10, WIDTH, 10);
			int w = WIDTH * life / 100;
			g.fillRect(x, y - 10, w, 10);
			g.setColor(c);
		}
	}

}
