import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * @author 995531914@qq.com
 * @version 创建时间：2017年6月3日 下午1:13:35
 */
public class TankClient extends Frame
{
	public static final int GAME_WIDTH = 800;// 游戏画面宽度
	public static final int GAME_HEIGHT = 600;// 游戏画面长度

	Image offScreenImage = null;
	/*
	 * int x = 50; int y = 50;
	 */
	Wall w1 = new Wall(100, 200, 20, 150, this);
	Wall w2 = new Wall(300, 100, 300, 20, this);
	Blood blood = new Blood();
	Tank myTank = new Tank(50, 50, true, Direction.STOP, this);
	ArrayList<Tank> tanks = new ArrayList<>();
	// Tank enemyTank = new Tank(100, 100, false, this);
	ArrayList<Missile> missiles = new ArrayList<>();
	ArrayList<Explode> explodes = new ArrayList<>();
	// Explode e = new Explode(70, 70, this);
	// Missile myMissile = null;

	@Override
	public void paint(Graphics g)
	{
		g.drawString("missiles count:" + missiles.size(), 10, 50);
		g.drawString("explodes count:" + explodes.size(), 10, 70);
		g.drawString("tanks    count:" + tanks.size(), 10, 90);
		g.drawString("life:" + myTank.getLife(), 10, 110);

		if (tanks.size() <= 0)
		{
			for (int i = 0; i < Integer.parseInt(PropertyMgr.getProperty("reProduceTankCount")); i++)
			{
				tanks.add(new Tank(50 + 40 * (i + 1), 50, false, Direction.D, this));
			}
		}

		w1.draw(g);
		w2.draw(g);
		blood.draw(g);
		myTank.draw(g);
		myTank.eat(blood);

		for (int i = 0; i < tanks.size(); i++)
		{
			Tank t = tanks.get(i);
			t.collideWithWall(w1);
			t.collideWithWall(w2);
			t.collideWithTanks(tanks);
			t.draw(g);
		}
		// enemyTank.draw(g);

		for (int i = 0; i < missiles.size(); i++)
		{
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			// m.hitTank(enemyTank);
			m.draw(g);
		}
		for (int i = 0; i < explodes.size(); i++)
		{
			Explode e = explodes.get(i);
			e.draw(g);
		}
		// e.draw(g);
		/*
		 * if (myMissile != null) { myMissile.draw(g); }
		 */

		// y += 5;
	}

	@Override
	public void update(Graphics g)
	{
		if (offScreenImage == null)
		{
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color color = gOffScreen.getColor();
		gOffScreen.setColor(Color.CYAN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(color);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void launchFrame()
	{
		/*
		 * Properties properties = new Properties(); try {
		 * properties.load(this.getClass().getClassLoader().getResourceAsStream(
		 * "config/tank.properties")); } catch (IOException e1) {
		 * e1.printStackTrace(); }
		 */
		int initTankCount = Integer.parseInt(PropertyMgr.getProperty("initTankCount"));

		for (int i = 0; i < initTankCount; i++)
		{
			tanks.add(new Tank(50 + 40 * (i + 1), 50, false, Direction.D, this));
		}
		this.setLocation(400, 300);
		this.setSize(800, 600);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setBackground(Color.BLACK);

		this.addKeyListener(new KeyMonitor());

		setVisible(true);

		new Thread(new PaintThread()).start();
	}

	private class PaintThread implements Runnable
	{
		@Override
		public void run()
		{
			while (true)
			{
				repaint(); // 调用外部类的repaint()方法
				try
				{
					Thread.sleep(50);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}

	}

	private class KeyMonitor extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			myTank.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			myTank.keyReleased(e);
		}
	}

	public static void main(String[] args)
	{
		TankClient tankClient = new TankClient();
		tankClient.launchFrame();
	}

}
