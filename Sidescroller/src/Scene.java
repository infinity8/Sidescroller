import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Lyeeedar
 *
 */
public class Scene {

	int zoomAmount = 0;
	float zoomStepX = 0.4f;
	float zoomStepY = 0.3f;

	ArrayList<SceneActor> actors = new ArrayList<SceneActor>();

	BufferedImage background;

	int[] screenPosition;
	int[] resolution = MainCanvas.resolution;

	/**
	 * 4 modes: <p>
	 * 0 = Inactive <p>
	 * 1 = Zoom in <p>
	 * 2 = normal <p>
	 * 3 = Zoom out
	 */
	int mode = 0;

	String parent;

	ArrayList<SceneAction> sceneActions = new ArrayList<SceneAction>();

	int sceneStage = 0;

	public Scene(String parent)
	{
		this.parent = parent;
		Entity e = Main.gamedata.getGameEntities().get(parent);
		actors.add(new SceneActor(e.spriteFile, new int[]{0, 0, e.pos[2]}, new int[]{e.collisionShape[0]+(e.collisionShape[2]/2), e.collisionShape[1]}, true, e.animateStage, e.animStages, e.animateStrip, e.totalAnimateStrip, (int)e.animateTime));

		screenPosition = new int[]{e.pos[0]-(resolution[0]/2), e.pos[1]-(resolution[1]/2)};
	}

	public void start()
	{	
		background = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = background.createGraphics();

		// Distant background, moves at half the speed of the rest
		g2d.drawImage(Main.gamedata.getBackground()[0],
				0, 0, resolution[0], resolution[1],
				screenPosition[0]/3, screenPosition[1], (screenPosition[0]/3)+(int)(resolution[0]), (screenPosition[1])+(int)(resolution[1]),
				null);

		// Far background
		g2d.drawImage(Main.gamedata.getBackground()[1],
				0, 0, resolution[0], resolution[1],
				screenPosition[0]/2, screenPosition[1], (screenPosition[0]/2)+resolution[0], screenPosition[1]+resolution[1],
				null);

		// Close Background layer
		g2d.drawImage(Main.gamedata.getBackground()[2],
				0, 0, resolution[0], resolution[1],
				screenPosition[0], screenPosition[1], screenPosition[0]+resolution[0], screenPosition[1]+resolution[1],
				null);

		// Collision layer
		g2d.drawImage(Main.gamedata.getBackground()[3],
				0, 0, resolution[0], resolution[1],
				screenPosition[0], screenPosition[1], screenPosition[0]+resolution[0], screenPosition[1]+resolution[1],
				null);

		mode = 1;

		g2d.dispose();

		Main.setState(5);
	}

	public void updateTime(long time)
	{
		if (mode == 1)
		{
			zoomAmount += time;

			if (zoomAmount > 1000)
			{
				zoomAmount = 1000;
				mode = 2;
			}
		}
		else if (mode == 3)
		{
			zoomAmount -= time;

			if (zoomAmount < 0)
			{
				zoomAmount = 0;
				end();
			}
		}

		for (SceneActor sa : actors)
		{
			sa.updateTime(time);
		}
	}

	public void end()
	{
		Main.setState(1);
		Main.gamedata.currentScene = null;
		sceneStage = 0;
		mode = 0;

	}

	public void draw(Graphics2D g2d)
	{
		BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();

		g.drawImage(background, 0, 0, null);
		for (SceneActor sa : actors)
		{
			if (!sa.visible)
				continue;

			if (sa.pos[2] == 1)
				g.drawImage(sa.spriteSheet, 400+sa.pos[0], 300+sa.pos[1], 400+sa.pos[0]+sa.spriteSize[0], 300+sa.pos[1]+sa.spriteSize[1], sa.spriteSize[0]*(sa.stage-1), sa.spriteSize[1]*(sa.strip-1), sa.spriteSize[0]*sa.stage, sa.spriteSize[1]*sa.strip, null);
			else
				g.drawImage(sa.spriteSheet, 400+sa.pos[0]+sa.spriteSize[0], 300+sa.pos[1], 400+sa.pos[0], 300+sa.pos[1]+sa.spriteSize[1], sa.spriteSize[0]*(sa.stage-1), sa.spriteSize[1]*(sa.strip-1), sa.spriteSize[0]*sa.stage, sa.spriteSize[1]*sa.strip, null);
		}

		if ((mode == 1) || (mode == 3))
		{
			g.drawImage(Main.gamedata.getBackground()[4],
					0, 0, resolution[0], resolution[1],
					screenPosition[0], screenPosition[1], screenPosition[0]+resolution[0], screenPosition[1]+resolution[1],
					null);

			g.dispose();

			zoom(g2d, image);
		}
		else if (mode == 2)
		{
			g.dispose();

			normalDraw(g2d, image);
		}
	}

	public void normalDraw(Graphics2D g2d, BufferedImage image)
	{
		g2d.drawImage(image, (int)(-zoomStepX*zoomAmount), (int)(-zoomStepY*zoomAmount), 800+(int)(zoomStepX*zoomAmount*2), 600+(int)(zoomStepY*zoomAmount*2), null);

		drawSpeech(g2d);

	}

	public void zoom(Graphics2D g2d, BufferedImage image)
	{
		g2d.drawImage(image, (int)(-zoomStepX*zoomAmount), (int)(-zoomStepY*zoomAmount), 800+(int)(zoomStepX*zoomAmount*2), 600+(int)(zoomStepY*zoomAmount*2), null);
	}

	public void drawSpeech(Graphics2D g2d)
	{
		for (SceneActor sa : actors)
		{
			if ((sa.dialogue == null) || (sa.dialogue.equals("")))
				continue;

			String text = sa.dialogue;

			// Create the colours used in the speech bubbles
			Color dark = new Color(0, 0, 0);
			Color pale = new Color(202, 255, 255);

			// Calculate the width and height of the dialogue bubble depending on how much text needs to be drawn
			int width = 20+text.length()*6;

			if (width > 230)
				width = 230;

			String[] textLines = MainCanvas.wrapText(text, 34);
			int height = textLines.length*25;

			int x = 400+sa.headPos[0]+35;
			int y = 300+sa.headPos[1]-height-20;

			int[] xp = {x+20, x+35, x-10+(sa.spriteSize[0]/4)};
			int[] yp = {y+height, y+height, y+height+30};

			g2d.setColor(pale);
			g2d.fillRoundRect(x, y, width, height, 30, 30);
			g2d.setColor(dark);
			g2d.drawRoundRect(x, y, width, height, 30, 30);
			g2d.setColor(pale);
			g2d.fillPolygon(xp, yp, 3);
			g2d.setColor(dark);
			g2d.drawLine(xp[0], yp[0], xp[2], yp[2]);
			g2d.drawLine(xp[1], yp[1], xp[2], yp[2]);

			g2d.setColor(dark);
			for (int i = 0; i < textLines.length; i++)
			{
				g2d.drawString(textLines[i], x+15, y+((i+1)*20));
			}
		}
	}

}

class SceneActor
{
	BufferedImage spriteSheet;
	int[] spriteSize;
	int[] headPos;
	int[] pos;
	boolean animate;
	int stage;
	int totalStage;
	int strip;
	int totalStrip;
	String dialogue = "test text is here to see how much can be seen on the screen lol woooooopts this is a big pie lol";
	int totalAnimateTime;
	int animateTime;
	boolean visible = true;

	public SceneActor(String image, int[] pos, int[] headPos, boolean animate, int stage, int totalStage, int strip, int totalStrip, int animateTime)
	{
		this.headPos = headPos;
		spriteSheet = GameData.getImage("Spritesheet", image);
		this.pos = pos;
		this.animate = animate;
		this.stage = stage;
		this.totalStage = totalStage;
		this.strip = strip;
		this.totalStrip = totalStrip;
		this.totalAnimateTime = animateTime;
		this.animateTime = animateTime;

		spriteSize = new int[]{spriteSheet.getWidth()/totalStage, spriteSheet.getHeight()/totalStrip};
	}

	public void updateTime(long time)
	{
		if (animate)
			animate(time);
	}

	public void animate(long time)
	{
		animateTime -= time;

		if (animateTime < 0)
		{
			animateTime = totalAnimateTime;

			stage++;

			if (stage > totalStage)
				stage = 1;
		}
	}
}

class SceneAction
{
	String type;
	ArrayList<String> arg = new ArrayList<String>();
	Scene parent;
	
	public SceneAction(String type, Scene parent)
	{
		this.type = type;
		this.parent = parent;
	}
}
