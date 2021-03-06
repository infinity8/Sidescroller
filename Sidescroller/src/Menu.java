import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Lyeeedar
 *
 */
public class Menu {
	/**
	 * Menu types: <p>
	 * "Menu" - Layout = <p>
	 * 
	 * ################ <p>
	 * ##-0-######-1-## <p>
	 * ################ <p>
	 * ##-2-######-3-## <p>
	 * ################ <p>
	 * ##-4-######-5-## <p>
	 * ################ <p>
	 *  <p> <p>
	 * "Skill" - Layout = 
	 *
	 *		0			|	12	O
	 *	1		2		|		|	\
	 *	3		4		|		O	O
	 *					|		|	/
	 *	5	6	7	 	|		O
	 *	8	9	10		|		/	\
	 *		11
	 */
	MenuScreen menu = new MainMenu(this);

	public void drawMenus(Graphics2D g2d)
	{
		menu.draw(g2d);
	}

	public void evaluateButtons()
	{
		menu.evaluateButtons();
	}

	public void changeMenu(String menu)
	{
		if (menu.equals("Game"))
		{
			this.menu = new GameMenu(this);
		}
		else if (menu.equals("Spells"))
		{
			this.menu = new SpellMenu(this);
		}
		else if (menu.equals("Char"))
		{
			this.menu = new CharacterMenu(this);
		}
		else if (menu.equals("Save"))
		{
			this.menu = new SaveMenu(this);
		}
		else if (menu.equals("Load"))
		{
			this.menu = new LoadMenu(this);
		}
		else if (menu.equals("Main"))
		{
			this.menu = new MainMenu(this);
		}
		else if (menu.equals("MainLoad"))
		{
			this.menu = new MainLoadMenu(this);
		}
		else if (menu.equals("Credits"))
		{
			this.menu = new CreditsMenu(this);
		}
		else if (menu.equals("Options"))
		{
			this.menu = new OptionsMenu(this);
		}
		else if (menu.equals("GameOptions"))
		{
			this.menu = new GameOptionsMenu(this);
		}
	}


}
// End Menu

abstract class MenuScreen
{
	Menu menu;
	BufferedImage backImage;

	public MenuScreen(Menu menu)
	{
		this.menu = menu;

		backImage = GameData.getImage("GUI", "SpellbookBack.png");
	}

	public void draw(Graphics2D g2d)
	{
		drawBackground(g2d);
		drawLeft(g2d);
		drawRight(g2d);
	}

	protected void drawBackground(Graphics2D g2d)
	{
		g2d.drawImage(backImage, 0, 0, null);
	}

	abstract void evaluateButtons();
	abstract protected void drawLeft(Graphics2D g2d);
	abstract protected void  drawRight(Graphics2D g2d);
}

class GameMenu extends MenuScreen
{
	BufferedImage[] images = new BufferedImage[3];

	/**
	 * @param menu
	 */
	public GameMenu(Menu menu) {
		super(menu);

		images[0] = GameData.getImage("GUI", "spellbookGameText.png");
		images[1] = GameData.getImage("GUI", "spellbookButton.png");
		images[2] = GameData.getImage("GUI", "spellbookButtonSelected.png");
	}

	int selectedIndex;

	@Override
	protected void drawBackground(Graphics2D g2d)
	{
		super.drawBackground(g2d);

		g2d.drawImage(images[0], 0, 0, null);
	}

	protected void drawLeft(Graphics2D g2d)
	{
		//110 , 65
		Color normal = new Color(0, 0, 0, 170);
		Color selected = new Color(54, 4, 89, 190);

		g2d.setFont(g2d.getFont().deriveFont((float) 20));

		if (selectedIndex == 0)
		{
			g2d.drawImage(images[2], 90, 90, null);
			g2d.setColor(selected);
			g2d.drawString("Resume", 200, 155);
		}
		else
		{
			g2d.drawImage(images[1], 90, 90, null);
			g2d.setColor(normal);
			g2d.drawString("Resume", 200, 155);
		}

		if (selectedIndex == 1)
		{
			g2d.drawImage(images[2], 490, 50, null);
			g2d.setColor(selected);
			g2d.drawString("Save", 600, 115);
		}
		else
		{
			g2d.drawImage(images[1], 490, 50, null);
			g2d.setColor(normal);
			g2d.drawString("Save", 600, 115);
		}

		if (selectedIndex == 2)
		{
			g2d.drawImage(images[2], 90, 220, null);
			g2d.setColor(selected);
			g2d.drawString("Spells", 200, 285);
		}
		else
		{
			g2d.drawImage(images[1], 90, 220, null);
			g2d.setColor(normal);
			g2d.drawString("Spells", 200, 285);
		}

		if (selectedIndex == 3)
		{
			g2d.drawImage(images[2], 490, 180, null);
			g2d.setColor(selected);
			g2d.drawString("Load", 600, 245);
		}
		else
		{
			g2d.drawImage(images[1], 490, 180, null);
			g2d.setColor(normal);
			g2d.drawString("Load", 600, 245);
		}

		if (selectedIndex == 4)
		{
			g2d.drawImage(images[2], 90, 350, null);
			g2d.setColor(selected);
			g2d.drawString("Character", 200, 415);
		}
		else
		{
			g2d.drawImage(images[1], 90, 350, null);
			g2d.setColor(normal);
			g2d.drawString("Character", 200, 415);
		}

		if (selectedIndex == 5)
		{
			g2d.drawImage(images[2], 490, 310, null);
			g2d.setColor(selected);
			g2d.drawString("Options", 600, 375);
		}
		else
		{
			g2d.drawImage(images[1], 490, 310, null);
			g2d.setColor(normal);
			g2d.drawString("Options", 600, 375);
		}
		
		if (selectedIndex == 6)
		{
			g2d.drawImage(images[2], 490, 440, null);
			g2d.setColor(selected);
			g2d.drawString("Main Menu", 600, 505);
		}
		else
		{
			g2d.drawImage(images[1], 490, 440, null);
			g2d.setColor(normal);
			g2d.drawString("Main Menu", 600, 505);
		}
	}

	/* (non-Javadoc)
	 * @see Menu#evaluateButtons()
	 */
	@Override
	void evaluateButtons() {
		if (MainCanvas.up)
		{
			selectedIndex -= 2;
			MainCanvas.up = false;
		}
		else if (MainCanvas.right)
		{
			selectedIndex++;
			MainCanvas.right = false;
		}
		else if (MainCanvas.left)
		{
			selectedIndex--;
			MainCanvas.left = false;
		}
		else if (MainCanvas.down)
		{
			selectedIndex += 2;
			MainCanvas.down = false;
		}
		else if (MainCanvas.esc)
		{
			Main.setState(1);
			MainCanvas.esc = false;
		}
		else if (MainCanvas.enter)
		{
			if (selectedIndex == 0)
			{
				Main.setState(1);
			}
			else if (selectedIndex == 1)
			{
				menu.changeMenu("Save");
			}
			else if (selectedIndex == 2)
			{
				menu.changeMenu("Spells");
			}
			else if (selectedIndex == 3)
			{
				menu.changeMenu("Load");
			}
			else if (selectedIndex == 4)
			{
				menu.changeMenu("Char");
			}
			else if (selectedIndex == 5)
			{
				menu.changeMenu("GameOptions");
			}
			else if (selectedIndex == 6)
			{
				menu.changeMenu("Main");
				Main.gamedata.clearGame();
			}

			MainCanvas.enter = false;
		}

		if (selectedIndex < 0)
		{
			selectedIndex = 0;
		}
		else if (selectedIndex > 6)
		{
			selectedIndex = 6;
		}

	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawRight(java.awt.Graphics2D)
	 */
	@Override
	protected void drawRight(Graphics2D g2d) {
		// TODO Auto-generated method stub

	}

}

class SpellMenu extends MenuScreen
{

	BufferedImage[] images = new BufferedImage[5];

	public SpellMenu(Menu menu) {
		super(menu);

		images[0] = GameData.getImage("GUI", "spellIconBase.png");
		images[1] = GameData.getImage("GUI", "spellIconBaseSelected.png");
		images[2] = GameData.getImage("GUI", "spellbookSpellText.png");
		images[3] = GameData.getImage("GUI", "spellbookSpellTree.png");
		images[4] = GameData.getImage("GUI", "spellbookSpellTreeSelected.png");
	}

	@Override
	protected void drawBackground(Graphics2D g2d)
	{
		super.drawBackground(g2d);
		g2d.drawImage(images[2], 0, 0, null);
	}

	boolean treeSelected = false;
	int selectedIndex = 0;

	int spellStage = 0;
	int spellIndex = 0;

	String element = Entity.FIRE;

	final int[] size = {240, 302};

	final String[] sockets = {"Shout", "Punch", "Strike", "Kick", "Stomp"};

	private BufferedImage getSpellTreeImage(ArrayList<SpellsStage> spells, int stage, int selected)
	{
		BufferedImage im = new BufferedImage(1000, 100+(spells.size()*100), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = im.createGraphics();

		g2d.setColor(Color.BLACK);

		for (int i = 0; i < spells.size(); i++)
		{
			SpellsStage ss = spells.get(i);

			for (int ii = 0; ii < ss.spells.size(); ii++)
			{
				SpellsStageEntry sse = ss.spells.get(ii);
				if (sse.unlocked > 0)
				{
					if ((i == stage) && (ii == selected))
					{
						g2d.drawImage(images[1], sse.pos[0]-28, sse.pos[1]-28, null);
					}
					else
					{
						g2d.drawImage(images[0], sse.pos[0]-28, sse.pos[1]-28, null);
					}
				}

				if (sse.unlocked == 3)
				{
					g2d.drawImage(sse.images[1], sse.pos[0]-28, sse.pos[1]-28, null);
				}
				
				if (sse.unlocked > 1)
				{
					g2d.drawImage(sse.images[0], sse.pos[0]-28, sse.pos[1]-28, null);
				}
				else if (sse.unlocked == 0)
				{
					continue;
				}

				for (String p : sse.parents)
				{
					SpellsStageEntry parent = spells.get(i-1).getSpellStageEntry(p);

					g2d.drawLine(sse.pos[0], sse.pos[1], parent.pos[0], parent.pos[1]);
				}
			}
		}

		g2d.dispose();


		return im;
	}

	protected void drawRight(Graphics2D g2d)
	{
		BufferedImage im = getSpellTreeImage(Character.getSpell(element), spellStage, spellIndex);

		int minX = Character.getSpell(element).get(spellStage).spells.get(spellIndex).pos[0]-(size[0]/2);
		int maxX = Character.getSpell(element).get(spellStage).spells.get(spellIndex).pos[0]+(size[0]/2);

		int minY = Character.getSpell(element).get(spellStage).spells.get(spellIndex).pos[1]-(size[1]/2);
		int maxY = Character.getSpell(element).get(spellStage).spells.get(spellIndex).pos[1]+(size[1]/2);

		if (minX < 0)
		{
			minX = 0;
			maxX = size[0];
		}
		else if (maxX > im.getWidth())
		{
			maxX = im.getWidth();
			minX = im.getWidth()-size[0];
		}

		if (minY < 0)
		{
			minY = 0;
			maxY = size[1];
		}
		else if (maxY > im.getHeight())
		{
			maxY = im.getHeight();
			minY = im.getHeight()-size[1];
		}

		if (treeSelected)
		{
			g2d.drawImage(images[4], 0, 0, null);
		}
		else if (selectedIndex == 12)
		{
			g2d.drawImage(images[3], 0, 0, null);
		}

		g2d.drawImage(im, 478, 70, 478+size[0], 70+size[1], minX, minY, maxX, maxY, null);

		SpellsStageEntry sse = Character.getSpell(element).get(spellStage).spells.get(spellIndex);

		if (selectedIndex < 5)
		{
			sse = Character.socketedSpells[selectedIndex];
		}

		if (sse.unlocked < 2)
		{
			return;
		}
		
		g2d.setColor(Color.BLACK);

		g2d.drawString(sse.name, 485, 100+size[1]+15);

		String[] description = MainCanvas.wrapText(sse.description, 35);

		int i;
		for (i = 0 ; i < description.length; i++)
		{
			g2d.drawString(description[i], 495, 100+size[1]+35+(20*i));
		}

		i++;

		if (sse.unlocked == 1)
		{
			g2d.drawString("You know that this spell exists.", 495, 100+size[1]+35+(20*i));
		}
		else if (sse.unlocked == 2)
		{
			g2d.drawString("You have learnt this spell.", 495, 100+size[1]+35+(20*i));
		}
		else if (sse.unlocked == 3)
		{
			g2d.drawString("You have mastered this spell.", 495, 100+size[1]+35+(20*i));
		}

		i++;
		
		g2d.drawString(sse.currentEXP+" / "+sse.maxEXP, 495, 100+size[1]+35+(20*i));
	}

	protected void drawLeft(Graphics2D g2d)
	{		
		g2d.setColor(Color.BLACK);
		g2d.drawLine(195, 100, 145, 160);
		g2d.drawLine(195, 100, 245, 160);

		g2d.drawLine(195, 100, 195, 200);

		g2d.drawLine(195, 200, 145, 280);
		g2d.drawLine(195, 200, 245, 280);

		SpellsStageEntry sse = null;

		// Draw Socket 0
		
		if (selectedIndex == 0)
		{
			g2d.drawImage(images[1], 167, 53, null);
		}
		else
		{
			g2d.drawImage(images[0], 167, 53, null);
		}
		
		sse = Character.socketedSpells[0];

		if (sse.unlocked == 3)
		{
			g2d.drawImage(sse.images[1], 167, 53, null);
		}
		
		if (sse.unlocked > 1)
		{
			g2d.drawImage(sse.images[0], 167, 53, null);
		}
		
		// End Draw Socket 0

		// Draw Socket 1
		
		if (selectedIndex == 1)
		{
			g2d.drawImage(images[1], 117, 132, null);
		}
		else
		{
			g2d.drawImage(images[0], 117, 132, null);
		}

		sse = Character.socketedSpells[1];

		if (sse.unlocked == 3)
		{
			g2d.drawImage(sse.images[1], 117, 132, null);
		}
		
		if (sse.unlocked > 1)
		{
			g2d.drawImage(sse.images[0], 117, 132,null);
		}
		
		// End Draw Socket 1
		
		//Draw Socket 2

		if (selectedIndex == 2)
		{
			g2d.drawImage(images[1], 217, 132, null);
		}
		else
		{
			g2d.drawImage(images[0], 217, 132, null);
		}
		sse = Character.socketedSpells[2];

		if (sse.unlocked == 3)
		{
			g2d.drawImage(sse.images[1], 217, 132, null);
		}
		
		if (sse.unlocked > 1)
		{
			g2d.drawImage(sse.images[0], 217, 132, null);
		}

		// End Draw Socket 2
		
		// Draw Socket 3

		if (selectedIndex == 3)
		{
			g2d.drawImage(images[1], 117, 252, null);
		}
		else
		{
			g2d.drawImage(images[0], 117, 252, null);
		}
		sse = Character.socketedSpells[3];

		if (sse.unlocked == 3)
		{
			g2d.drawImage(sse.images[1], 117, 252, null);
		}
		
		if (sse.unlocked > 1)
		{
			g2d.drawImage(sse.images[0], 117, 252, null);
		}

		// End Draw Socket 3
		
		// Draw Socket 4

		if (selectedIndex == 4)
		{
			g2d.drawImage(images[1], 217, 252, null);
		}
		else
		{
			g2d.drawImage(images[0], 217, 252, null);
		}
		sse = Character.socketedSpells[4];

		if (sse.unlocked == 3)
		{
			g2d.drawImage(sse.images[1], 217, 252, null);
		}
		
		if (sse.unlocked > 1)
		{
			g2d.drawImage(sse.images[0], 217, 252, null);
		}
		
		// End Draw Socket 4



		if (selectedIndex == 5)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		g2d.drawRect(60, 400, 80, 30);
		g2d.drawString("Fire", 70, 420);

		if (selectedIndex == 6)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		g2d.drawRect(155, 400, 80, 30);
		g2d.drawString("Air", 165, 420);

		if (selectedIndex == 7)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		g2d.drawRect(250, 400, 80, 30);
		g2d.drawString("Earth", 260, 420);

		if (selectedIndex == 8)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		g2d.drawRect(60, 450, 80, 30);
		g2d.drawString("Water", 70, 470);

		if (selectedIndex == 9)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		g2d.drawRect(155, 450, 80, 30);
		g2d.drawString("Death", 165, 470);

		if (selectedIndex == 10)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		g2d.drawRect(250, 450, 80, 30);
		g2d.drawString("Life", 260, 470);

		if (selectedIndex == 11)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		g2d.drawRect(155, 500, 80, 30);
		g2d.drawString("Back", 165, 520);
	}

	void evaluateButtons() {

		if (MainCanvas.esc)
		{
			if (treeSelected)
			{
				treeSelected = false;
			}
			else
			{
				menu.changeMenu("Game");
			}
			MainCanvas.esc = false;
		}

		if (!treeSelected)
		{
			if (MainCanvas.up)
			{
				if (selectedIndex < 7)
				{
					selectedIndex -= 2;
				}
				else if (selectedIndex == 12)
				{

				}
				else if (selectedIndex == 11)
				{
					selectedIndex -= 2;
				}
				else
				{
					selectedIndex -= 3;
				}
				MainCanvas.up = false;
			}
			else if (MainCanvas.down)
			{
				if (selectedIndex == 0)
				{
					selectedIndex++;
				}
				else if (selectedIndex < 5)
				{
					selectedIndex += 2;
				}
				else if (selectedIndex > 10)
				{

				}
				else
				{
					selectedIndex += 3;
					if (selectedIndex > 11)
					{
						selectedIndex = 11;
					}
				}

				MainCanvas.down = false;
			}
			else if (MainCanvas.left)
			{

				if (selectedIndex == 12)
				{
					selectedIndex = 4;
				}
				else
				{
					selectedIndex--;
				}

				MainCanvas.left = false;
			}
			else if (MainCanvas.right)
			{
				if ((selectedIndex == 0) || (selectedIndex == 2) || (selectedIndex == 4) || (selectedIndex == 7) || (selectedIndex == 10) || (selectedIndex == 11))
				{
					selectedIndex = 12;
				}
				else
				{
					selectedIndex++;
				}

				MainCanvas.right = false;
			}
			else if (MainCanvas.enter)
			{
				if (selectedIndex == 5)
				{
					if (element.equals(Entity.FIRE))
					{

					}
					else
					{
						element = Entity.FIRE;
						spellIndex = 0;
						spellStage = 0;
					}
				}
				else if (selectedIndex == 6)
				{
					if (element.equals(Entity.AIR))
					{

					}
					else
					{
						element = Entity.AIR;
						spellIndex = 0;
						spellStage = 0;
					}
				}
				else if (selectedIndex == 7)
				{
					if (element.equals(Entity.EARTH))
					{

					}
					else
					{
						element = Entity.EARTH;
						spellIndex = 0;
						spellStage = 0;
					}
				}
				else if (selectedIndex == 8)
				{
					if (element.equals(Entity.WATER))
					{

					}
					else
					{
						element = Entity.WATER;
						spellIndex = 0;
						spellStage = 0;
					}
				}
				else if (selectedIndex == 9)
				{
					if (element.equals(Entity.DEATH))
					{

					}
					else
					{
						element = Entity.DEATH;
						spellIndex = 0;
						spellStage = 0;
					}
				}
				else if (selectedIndex == 10)
				{
					if (element.equals(Entity.LIFE))
					{

					}
					else
					{
						element = Entity.LIFE;
						spellIndex = 0;
						spellStage = 0;
					}
				}
				else if (selectedIndex == 11)
				{
					menu.changeMenu("Game");
				}
				else if (selectedIndex == 12)
				{
					treeSelected = true;
				}

				MainCanvas.enter = false;
			}
		}
		else
		{
			if (MainCanvas.up)
			{
				spellStage--;

				if (spellStage < 0)
				{
					spellStage = 0;
				}

				int newIndex = spellIndex;

				if (newIndex > Character.getSpell(element).get(spellStage).spells.size()-1)
				{
					newIndex = Character.getSpell(element).get(spellStage).spells.size()-1;
				}

				SpellsStageEntry sse = Character.getSpell(element).get(spellStage).spells.get(newIndex);

				boolean found = false;

				if (sse.unlocked != 0)
				{
					found = true;
				}

				for (int i = 1; i < Character.getSpell(element).get(spellStage).spells.size(); i++)
				{
					if (found)
					{
						break;
					}

					int lower = newIndex - i;

					if (lower > -1)
					{
						sse = Character.getSpell(element).get(spellStage).spells.get(lower);

						if (sse.unlocked != 0)
						{
							newIndex = lower;
							found = true;
							break;
						}
					}

					int higher = newIndex + i;

					if (higher < Character.getSpell(element).get(spellStage).spells.size())
					{
						sse = Character.getSpell(element).get(spellStage).spells.get(higher);

						if (sse.unlocked != 0)
						{
							newIndex = higher;
							found = true;
							break;
						}
					}
				}

				if (found)
					spellIndex = newIndex;

				MainCanvas.up = false;
			}
			else if (MainCanvas.down)
			{
				if (spellStage == Character.getSpell(element).size()-1)
				{

				}
				else
				{
					int newStage = spellStage + 1;

					int newIndex = spellIndex;

					if (newIndex > Character.getSpell(element).get(newStage).spells.size()-1)
					{
						newIndex = Character.getSpell(element).get(newStage).spells.size()-1;
					}

					if (newIndex < 0)
					{
						return;
					}

					SpellsStageEntry sse = Character.getSpell(element).get(newStage).spells.get(newIndex);
					boolean found = false;

					if (sse.unlocked != 0)
					{
						found = true;
					}

					for (int i = 1; i < Character.getSpell(element).get(newStage).spells.size(); i++)
					{
						if (found)
						{
							break;
						}

						int lower = newIndex - i;

						if (lower > -1)
						{
							sse = Character.getSpell(element).get(newStage).spells.get(lower);

							if (sse.unlocked != 0)
							{
								newIndex = lower;
								found = true;
								break;
							}
						}

						int higher = newIndex + i;

						if (higher < Character.getSpell(element).get(newStage).spells.size())
						{
							sse = Character.getSpell(element).get(newStage).spells.get(higher);

							if (sse.unlocked != 0)
							{
								newIndex = higher;
								found = true;
								break;
							}
						}
					}

					if (found)
					{
						spellIndex = newIndex;
						spellStage = newStage;
					}
				}

				MainCanvas.down = false;
			}
			else if (MainCanvas.left)
			{
				if (spellIndex == 0)
				{

				}
				else
				{
					int newIndex = spellIndex - 1;

					SpellsStageEntry sse = Character.getSpell(element).get(spellStage).spells.get(newIndex);

					while (sse.unlocked == 0)
					{
						newIndex--;
						if (newIndex < 0)
						{
							newIndex = spellIndex;
							break;
						}

						sse =  Character.getSpell(element).get(spellStage).spells.get(newIndex);
					}

					spellIndex = newIndex;
				}

				MainCanvas.left = false;
			}
			else if (MainCanvas.right)
			{
				if (spellIndex == Character.getSpell(element).get(spellStage).spells.size()-1)
				{

				}
				else
				{
					int newIndex = spellIndex + 1;

					SpellsStageEntry sse = Character.getSpell(element).get(spellStage).spells.get(newIndex);

					while (sse.unlocked == 0)
					{
						newIndex++;
						if (newIndex == Character.getSpell(element).get(spellStage).spells.size())
						{
							newIndex = spellIndex;
							break;
						}

						sse =  Character.getSpell(element).get(spellStage).spells.get(newIndex);
					}

					spellIndex = newIndex;
				}

				MainCanvas.right = false;
			}

			if (Character.getSpell(element).get(spellStage).spells.get(spellIndex).unlocked > 1)
			{
				if (MainCanvas.key1)
				{
					Character.socketedSpells[0] = Character.getSpell(element).get(spellStage).spells.get(spellIndex);
				}
				else if (MainCanvas.key2)
				{
					Character.socketedSpells[1] = Character.getSpell(element).get(spellStage).spells.get(spellIndex);
				}
				else if (MainCanvas.key3)
				{
					Character.socketedSpells[2] = Character.getSpell(element).get(spellStage).spells.get(spellIndex);
				}
				else if (MainCanvas.key4)
				{
					Character.socketedSpells[3] = Character.getSpell(element).get(spellStage).spells.get(spellIndex);
				}
				else if (MainCanvas.key5)
				{
					Character.socketedSpells[4] = Character.getSpell(element).get(spellStage).spells.get(spellIndex);
				}
			}



			if (spellStage < 0)
			{
				spellStage = 0;
			}
			else if (spellStage == Character.getSpell(element).size())
			{
				spellStage = Character.getSpell(element).size()-1;
			}

			if (spellIndex < 0)
			{
				spellIndex = 0;
			}
			else if (spellIndex == Character.getSpell(element).get(spellStage).spells.size())
			{
				spellIndex = Character.getSpell(element).get(spellStage).spells.size()-1;
			}

		}

		if (selectedIndex < 0)
		{
			selectedIndex = 0;
		}
		else if (selectedIndex > 12)
		{
			selectedIndex = 12;
		}

	}

}


class SaveMenu extends MenuScreen
{

	int selectedIndex = 0;
	File[] files;
	SaveGame[] saves;
	BufferedImage[] images = new BufferedImage[1];
	ArrayList<Integer> saveIndexes = new ArrayList<Integer>();

	public SaveMenu(Menu menu) {
		super(menu);

		File directory = new File("Data/Saves");
		files = directory.listFiles();
		Arrays.sort(files, new Comparator<File>(){
			public int compare(File f1, File f2)
			{
				return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
			} });

		saves = new SaveGame[files.length];

		for (int i = 0; i < files.length; i++)
		{
			saves[i] = loadFile(files[i]);
		}
		
		for (int i = 0; i < files.length; i++)
		{
			if (GameData.gameSessionID.equals(saves[i].sessionID))
			{
				saveIndexes.add(i);
			}
		}

		images[0] = GameData.getImage("GUI", "spellbookFilesText.png");
	}

	@Override
	protected void drawBackground(Graphics2D g2d)
	{
		super.drawBackground(g2d);

		g2d.drawImage(images[0], 0, 0, null);
	}

	/* (non-Javadoc)
	 * @see MenuScreen#evaluateButtons()
	 */
	@Override
	void evaluateButtons() {
		if (MainCanvas.up)
		{
			selectedIndex--;

			MainCanvas.up = false;
		}
		else if (MainCanvas.down)
		{
			selectedIndex++;

			MainCanvas.down = false;
		}
		else if (MainCanvas.left)
		{
			selectedIndex--;

			MainCanvas.left = false;
		}
		else if (MainCanvas.right)
		{
			selectedIndex++;

			MainCanvas.right = false;
		}
		else if (MainCanvas.esc)
		{
			menu.changeMenu("Game");
			MainCanvas.esc = false;
		}
		else if (MainCanvas.enter)
		{
			if (selectedIndex < saveIndexes.size())
			{
				Main.gamedata.saveGame(files[saveIndexes.get(selectedIndex)]);
				menu.changeMenu("Game");
			}
			else if (selectedIndex == saveIndexes.size())
			{
				File file = new File("Data/Saves/"+System.currentTimeMillis()+".sav");
				Main.gamedata.saveGame(file);
				menu.changeMenu("Game");
			}
			else if (selectedIndex == saveIndexes.size()+1)
			{
				menu.changeMenu("Game");
			}

			MainCanvas.enter = false;
		}

		if (selectedIndex < 0)
		{
			selectedIndex = 0;
		}
		if (selectedIndex > saveIndexes.size()+1)
		{
			selectedIndex = saveIndexes.size()+1;
		}
	}

	public SaveGame loadFile(File file)
	{
		SaveGame save = null;

		try{
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream oin = new ObjectInputStream(fin);
			save = (SaveGame) oin.readObject();
			oin.close();
			fin.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return save;
	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawLeft(java.awt.Graphics2D)
	 */
	@Override
	protected void drawLeft(Graphics2D g2d) {
		if (selectedIndex < saveIndexes.size())
		{
			int i = saveIndexes.get(selectedIndex);
			
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(files[i].lastModified());

			String timePlayed = String.format("%d min, %d sec", 
					TimeUnit.MILLISECONDS.toMinutes(saves[i].timePlayed),
					TimeUnit.MILLISECONDS.toSeconds(saves[i].timePlayed) - 
					TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(saves[i].timePlayed))
					);

			g2d.setColor(Color.BLACK);
			g2d.drawString("Filename:", 70, 190);
			g2d.drawString(files[i].getName(), 200, 190);
			g2d.drawString("Last Saved:", 70, 210);
			g2d.drawString(formatter.format(calendar.getTime()), 200, 210);
			g2d.drawString("Current Level:", 70, 240);
			g2d.drawString(saves[i].currentLevel, 200, 240);
			g2d.drawString("Time Played:", 70, 270);
			g2d.drawString(timePlayed, 200, 270);
		}

	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawRight(java.awt.Graphics2D)
	 */
	@Override
	protected void drawRight(Graphics2D g2d) {

		int filePos = selectedIndex;

		if (filePos >= saveIndexes.size())
		{
			filePos = saveIndexes.size()-1;
		}
		
		g2d.setColor(Color.BLACK);
		
		g2d.drawString("Save Name: ", 520, 110);
		
		g2d.drawString(GameData.gameSessionID, 620, 110);

		g2d.drawImage(getImage(), 500, 140, 695, 485, 0, -100+(filePos*20), 195, 245+(filePos*20), null);

		if (selectedIndex == saveIndexes.size())
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}

		g2d.drawString("New Save", 495, 545);

		if (selectedIndex == saveIndexes.size()+1)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}

		g2d.drawString("Back", 610, 545);
	}

	private BufferedImage getImage()
	{
		BufferedImage im = new BufferedImage(195, 30+(saveIndexes.size()*20), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = im.createGraphics();

		for (int i = 0; i < saveIndexes.size(); i++)
		{
			if (selectedIndex == i)
			{
				g2d.setColor(Color.BLUE);
			}
			else
			{
				g2d.setColor(Color.BLACK);
			}

			g2d.drawString(files[saveIndexes.get(i)].getName(), 15, 30+(i*20));
		}

		g2d.dispose();

		return im;
	}

}

class SaveBlock
{
	String name;
	long latestSave;
	ArrayList<Integer> saveIndexes;
	
	public SaveBlock(String name, ArrayList<Integer> saveIndexes, long latestSave)
	{
		this.name = name;
		this.saveIndexes = saveIndexes;
		this.latestSave = latestSave;
	}
}

class LoadMenu extends MenuScreen
{

	int selectedIndex = 0;
	File[] files;
	SaveGame[] saves;
	BufferedImage[] images = new BufferedImage[1];
	SaveBlock[] saveBlocks;

	public LoadMenu(Menu menu) {
		super(menu);
		
		HashMap<String, ArrayList<Integer>> saveIndexes = new HashMap<String, ArrayList<Integer>>();

		File directory = new File("Data/Saves");
		files = directory.listFiles();
		Arrays.sort(files, new Comparator<File>(){
			public int compare(File f1, File f2)
			{
				return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
			} });

		saves = new SaveGame[files.length];

		for (int i = 0; i < files.length; i++)
		{
			saves[i] = loadFile(files[i]);
		}
		
		for (int i = 0; i < files.length; i++)
		{
			if (saveIndexes.containsKey(saves[i].sessionID))
			{
				ArrayList<Integer> saveblock = saveIndexes.get(saves[i].sessionID);
				saveblock.add(i);
			}
			else
			{
				ArrayList<Integer> saveblock = new ArrayList<Integer>();
				saveblock.add(i);
				saveIndexes.put(saves[i].sessionID, saveblock);
			}
		}
		
		saveBlocks = new SaveBlock[saveIndexes.size()];
		
		int i = 0;
		for (Map.Entry<String, ArrayList<Integer>> entry : saveIndexes.entrySet())
		{
			saveBlocks[i] = new SaveBlock(entry.getKey(), entry.getValue(), files[entry.getValue().get(0)].lastModified());
			
			i++;
		}
		
		Arrays.sort(saveBlocks, new Comparator<SaveBlock>(){
			public int compare(SaveBlock sb1, SaveBlock sb2)
			{
				return Long.valueOf(sb2.latestSave).compareTo(sb1.latestSave);
			} });
		
		images[0] = GameData.getImage("GUI", "spellbookFilesLoadText.png");
	}

	@Override
	protected void drawBackground(Graphics2D g2d)
	{
		super.drawBackground(g2d);

		g2d.drawImage(images[0], 0, 0, null);
	}
	/* (non-Javadoc)
	 * @see MenuScreen#evaluateButtons()
	 */
	@Override
	void evaluateButtons() {
		if (MainCanvas.up)
		{
			selectedIndex--;

			MainCanvas.up = false;
		}
		else if (MainCanvas.down)
		{
			selectedIndex++;

			MainCanvas.down = false;
		}
		else if (MainCanvas.left)
		{
			selectedIndex--;

			MainCanvas.left = false;
		}
		else if (MainCanvas.right)
		{
			selectedIndex++;

			MainCanvas.right = false;
		}
		else if (MainCanvas.esc)
		{
			menu.changeMenu("Game");
			MainCanvas.esc = false;
		}
		else if (MainCanvas.enter)
		{
			if (selectedIndex < files.length)
			{
				Main.setState(1);
				Main.gamedata.loadGame(files[getSelectedFileIndex()]);
			}
			else if (selectedIndex == files.length)
			{
				menu.changeMenu("Game");
			}

			MainCanvas.enter = false;
		}

		if (selectedIndex < 0)
		{
			selectedIndex = 0;
		}
		if (selectedIndex > files.length)
		{
			selectedIndex = files.length;
		}
	}
	
	public Integer getSelectedFileIndex()
	{
		int i = 0;
		for (SaveBlock sb : saveBlocks)
		{
			ArrayList<Integer> saveblock = sb.saveIndexes;
			
			for (Integer saveI : saveblock)
			{
				if (selectedIndex == i)
				{
					return saveI;
				}
				
				i++;
			}
		}

		return 0;
	}
	
	public Integer getSelectedFilePosition()
	{
		int selectedI = selectedIndex;
		int i = 0;
		for (SaveBlock sb : saveBlocks)
		{
			ArrayList<Integer> saveblock = sb.saveIndexes;
			
			i++;
			
			if (selectedI-saveblock.size() <= 0)
			{
				i += selectedI;
				break;
			}
			else
			{
				selectedI -= saveblock.size();
				i += saveblock.size();
			}
		}

		return i;
	}

	public SaveGame loadFile(File file)
	{
		SaveGame save = null;

		try{
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream oin = new ObjectInputStream(fin);
			save = (SaveGame) oin.readObject();
			oin.close();
			fin.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return save;
	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawLeft(java.awt.Graphics2D)
	 */
	@Override
	protected void drawLeft(Graphics2D g2d) {
		if (selectedIndex < files.length)
		{
			int i = getSelectedFileIndex();
			
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(files[i].lastModified());

			String timePlayed = String.format("%d min, %d sec", 
					TimeUnit.MILLISECONDS.toMinutes(saves[i].timePlayed),
					TimeUnit.MILLISECONDS.toSeconds(saves[i].timePlayed) - 
					TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(saves[i].timePlayed))
					);

			g2d.setColor(Color.BLACK);
			g2d.drawString("Filename:", 70, 190);
			g2d.drawString(files[i].getName(), 200, 190);
			g2d.drawString("Last Saved:", 70, 210);
			g2d.drawString(formatter.format(calendar.getTime()), 200, 210);
			g2d.drawString("Current Level:", 70, 240);
			g2d.drawString(saves[i].currentLevel, 200, 240);
			g2d.drawString("Time Played:", 70, 270);
			g2d.drawString(timePlayed, 200, 270);
		}

	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawRight(java.awt.Graphics2D)
	 */
	@Override
	protected void drawRight(Graphics2D g2d) {

		int filePos = selectedIndex;

		if (filePos >= files.length)
		{
			filePos = files.length-1;
		}
		
		int i = getSelectedFilePosition();
		g2d.drawImage(getImage(), 500, 90, 695, 485, 0, -100+(i*20), 195, 295+(i*20), null);


		if (selectedIndex == files.length)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}

		g2d.drawString("Back", 610, 545);
	}

	private BufferedImage getImage()
	{
		BufferedImage im = new BufferedImage(270, 80+(files.length*20), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = im.createGraphics();

		int i = 0;
		int selectedI = 0;
		for (SaveBlock sb : saveBlocks)
		{
			ArrayList<Integer> saveblock = sb.saveIndexes;
			
			g2d.setColor(Color.BLACK);
			g2d.drawString(sb.name, 5, 30+(i*20));
			
			i++;
			
			int savenum = 0;
			for (Integer saveI : saveblock)
			{
				if (selectedIndex == selectedI)
				{
					g2d.setColor(Color.BLUE);
				}
				else
				{
					g2d.setColor(Color.BLACK);
				}

				String name = "Save "+savenum;
				if (files[saveI].getName().equals("autosave.sav"))
				{
					name = "Autosave";
				}
				else
				{
					savenum++;
				}
				g2d.drawString(name, 15, 30+(i*20));
				
				i++;
				selectedI++;				
			}
		}

		g2d.dispose();

		return im;
	}

}


class CharacterMenu extends MenuScreen
{

	int selectedIndex = 0;
	int selectedType = 0;
	boolean itemSelected = false;
	int itemIndex = 0;

	ArrayList<Sigil> sigils = new ArrayList<Sigil>();
	ArrayList<Upgrade> scrolls = new ArrayList<Upgrade>();
	ArrayList<Item> misc = new ArrayList<Item>();

	ArrayList<Sigil> fire = new ArrayList<Sigil>();
	ArrayList<Sigil> air = new ArrayList<Sigil>();
	ArrayList<Sigil> earth = new ArrayList<Sigil>();
	ArrayList<Sigil> water = new ArrayList<Sigil>();
	ArrayList<Sigil> death = new ArrayList<Sigil>();
	ArrayList<Sigil> life = new ArrayList<Sigil>();

	BufferedImage[] images = new BufferedImage[3];

	public CharacterMenu(Menu menu) {
		super(menu);

		images[0] = GameData.getImage("GUI", "spellbookCharText.png");
		images[1] = GameData.getImage("GUI", "spellbookCharItems.png");
		images[2] = GameData.getImage("GUI", "spellbookCharItemsSelected.png");

		for (Map.Entry<String, Item> entry : Character.inventory.get(0).entrySet())
		{
			Sigil item = (Sigil) entry.getValue();

			sigils.add(item);
		}

		for (Map.Entry<String, Item> entry : Character.inventory.get(1).entrySet())
		{
			Upgrade item = (Upgrade) entry.getValue();

			scrolls.add(item);
		}

		for (Map.Entry<String, Item> entry : Character.inventory.get(2).entrySet())
		{
			Item item = entry.getValue();

			misc.add(item);
		}

		for (Sigil sigil : sigils)
		{
			if (sigil.element.equals(Entity.FIRE))
			{
				fire.add(sigil);
			}
			else if (sigil.element.equals(Entity.AIR))
			{
				air.add(sigil);
			}
			else if (sigil.element.equals(Entity.EARTH))
			{
				earth.add(sigil);
			}
			else if (sigil.element.equals(Entity.WATER))
			{
				water.add(sigil);
			}
			else if (sigil.element.equals(Entity.DEATH))
			{
				death.add(sigil);
			}
			else if (sigil.element.equals(Entity.LIFE))
			{
				life.add(sigil);
			}
		}
	}

	/* (non-Javadoc)
	 * @see MenuScreen#evaluateButtons()
	 */
	@Override
	void evaluateButtons() {
		if (MainCanvas.up)
		{
			if (itemSelected)
			{
				itemIndex--;
			}
			else
			{
				if (selectedIndex == 3)
				{
					selectedIndex = 1;
				}
				else
					selectedIndex--;
			}

			MainCanvas.up = false;
		}
		else if (MainCanvas.down)
		{
			if (itemSelected)
			{
				itemIndex++;
			}
			else
			{
				selectedIndex = 3;
			}

			MainCanvas.down = false;
		}
		else if (MainCanvas.left)
		{
			if (itemSelected)
			{
				itemIndex--;
			}
			else
			{
				selectedIndex--;
			}

			MainCanvas.left = false;
		}
		else if (MainCanvas.right)
		{
			if (itemSelected)
			{
				itemIndex++;
			}
			else
			{
				selectedIndex++;
			}

			MainCanvas.right = false;
		}
		else if (MainCanvas.esc)
		{
			if (itemSelected)
			{
				itemSelected = false;
			}
			else
			{
				menu.changeMenu("Game");
			}
			MainCanvas.esc = false;
		}
		else if (MainCanvas.enter)
		{
			if (selectedIndex < 3)
			{
				selectedType = selectedIndex;
				itemIndex = 0;
			}
			else if (selectedIndex == 3)
			{
				if (itemSelected)
				{
					if (selectedType == 0)
					{
						Sigil sigil = this.getSelectedSigil();

						if (sigil == null)
						{

						}
						else if (sigil.equipped)
						{
							Character.unequipSigil(sigil);
						}
						else
						{
							Character.equipSigil(sigil);
						}
					}
					else if (selectedType == 1)
					{
						scrolls.get(itemIndex).use();
					}
				}
				else
				{
					itemSelected = true;
				}
			}

			MainCanvas.enter = false;
		}

		if (selectedIndex < 0)
			selectedIndex = 0;
		else if (selectedIndex > 3)
			selectedIndex = 3;

		if (itemIndex < 0)
			itemIndex = 0;

		if (selectedType == 0)
		{
			if (itemIndex > sigils.size()-1)
				itemIndex = sigils.size()-1;
		}
		else if (selectedType == 1)
		{
			if (itemIndex > scrolls.size()-1)
				itemIndex = scrolls.size()-1;
		}
		else if (selectedType == 2)
		{
			if (itemIndex > misc.size()-1)
				itemIndex = misc.size()-1;
		}
	}

	protected void drawBackground(Graphics2D g2d)
	{
		super.drawBackground(g2d);
		g2d.drawImage(images[0], 0, 0, null);
	}

	protected Sigil getSelectedSigil()
	{
		int itemValue = itemIndex;

		if (itemIndex == -1)
		{
			return null;
		}

		if (itemValue < fire.size())
		{
			return fire.get(itemValue);
		}
		itemValue -= fire.size();

		if (itemValue < air.size())
		{
			return air.get(itemValue);
		}
		itemValue -= air.size();

		if (itemValue < earth.size())
		{
			return earth.get(itemValue);
		}
		itemValue -= earth.size();

		if (itemValue < water.size())
		{
			return water.get(itemValue);
		}
		itemValue -= water.size();

		if (itemValue < death.size())
		{
			return death.get(itemValue);
		}
		itemValue -= death.size();

		if (itemValue < life.size())
		{
			return life.get(itemValue);
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawLeft(java.awt.Graphics2D)
	 */
	@Override
	protected void drawLeft(Graphics2D g2d) {
		//140 350
		Entity p = Main.gamedata.getGameEntities().get("Player");

		g2d.setColor(Color.BLACK);
		int y = 350;

		g2d.drawString("Health: ", 90, y);
		g2d.drawString(Double.toString(p.health), 150, y);
		g2d.drawString("/", 210, y);
		g2d.drawString(Double.toString(p.maxHealth), 230, y);

		y += 25;

		g2d.drawString("Speed: ", 90, y);
		g2d.drawString(Integer.toString(p.speed), 150, y);

		y += 25;

		g2d.drawString("Defenses: ", 90, y);

		y += 25;

		g2d.drawString(Double.toString(p.defense.get(Entity.PHYSICAL))+ " PDEF", 190, y);

		y += 25;

		g2d.drawString(Double.toString(p.defense.get(Entity.FIRE))+ " FDEF", 110, y);
		g2d.drawString(Double.toString(p.defense.get(Entity.AIR))+ " ADEF", 190, y);
		g2d.drawString(Double.toString(p.defense.get(Entity.EARTH))+ " EDEF", 270, y);

		y += 25;

		g2d.drawString(Double.toString(p.defense.get(Entity.WATER))+ " WDEF", 110, y);
		g2d.drawString(Double.toString(p.defense.get(Entity.DEATH))+ " DDEF", 190, y);
		g2d.drawString(Double.toString(p.defense.get(Entity.LIFE))+ " LDEF", 270, y);

	}


	protected int getSigilDistance()
	{
		int itemValue = itemIndex;

		if (itemValue < fire.size())
		{
			return itemValue;
		}
		itemValue -= fire.size();
		itemValue++;

		if (itemValue < air.size())
		{
			return itemValue;
		}
		itemValue -= air.size();
		itemValue++;

		if (itemValue < earth.size())
		{
			return itemValue;
		}
		itemValue -= earth.size();
		itemValue++;

		if (itemValue < water.size())
		{
			return itemValue;
		}
		itemValue -= water.size();
		itemValue++;

		if (itemValue < death.size())
		{
			return itemValue;
		}
		itemValue -= death.size();
		itemValue++;

		if (itemValue < life.size())
		{
			return itemValue;
		}
		itemValue -= life.size();
		itemValue++;

		return itemValue;
	}
	/* (non-Javadoc)
	 * @see MenuScreen#drawRight(java.awt.Graphics2D)
	 */
	@Override
	protected void drawRight(Graphics2D g2d) {
		if (selectedIndex == 0)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		g2d.drawString("Sigil", 490, 70);

		if (selectedIndex == 1)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		g2d.drawString("Scrolls", 575, 70);

		if (selectedIndex == 2)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		g2d.drawString("Misc", 660, 70);

		if (selectedIndex == 3)
		{
			if (itemSelected)
			{
				g2d.drawImage(images[2], 0, 0, null);
			}
			else
			{
				g2d.drawImage(images[1], 0, 0, null);
			}
		}

		int dist = itemIndex;
		if (selectedType == 0)
		{
			dist = this.getSigilDistance();
		}
		g2d.drawImage(getImage(), 500, 138, 709, 295, 0, -30+(dist*20), 209, -30+(dist*20)+157, null);


		g2d.setColor(Color.BLACK);

		Item item = null;

		if (selectedType == 0)
		{
			if (sigils.size() > 0)
				item = this.getSelectedSigil();
		}
		else if (selectedType == 1)
		{
			if (scrolls.size() > 0)
				item = scrolls.get(itemIndex);
		}
		else if (selectedType == 2)
		{
			if (misc.size() > 0)
				item = misc.get(itemIndex);
		}

		if (item == null)
			return;

		int y = 320;

		g2d.drawString(item.getName(), 480, y);

		y += 25;

		String[] desc = MainCanvas.wrapText(item.description, 40);

		for (int i = 0; i < desc.length; i++)
		{
			g2d.drawString(desc[i], 485, y);
			y += 25;
		}

		if (selectedType == 0)
		{
			Sigil sigil = (Sigil) item;

			g2d.drawString("Effects:", 485, y);
			y += 25;

			if (sigil.values[0] != 0)
			{
				g2d.drawString(Integer.toString(sigil.values[0])+" HP", 485, y);
			}

			if (sigil.values[1] != 0)
			{
				g2d.drawString(Integer.toString(sigil.values[1])+" SPD", 545, y);
			}

			if (sigil.values[2] != 0)
			{
				g2d.drawString(Integer.toString(sigil.values[2])+" PDEF", 605, y);
			}
			y += 25;

			if (sigil.values[3] != 0)
			{
				g2d.drawString(Integer.toString(sigil.values[3])+" FDEF", 485, y);
			}

			if (sigil.values[4] != 0)
			{
				g2d.drawString(Integer.toString(sigil.values[4])+" ADEF", 545, y);
			}

			if (sigil.values[5] != 0)
			{
				g2d.drawString(Integer.toString(sigil.values[5])+" EDEF", 605, y);
			}
			y += 25;

			if (sigil.values[6] != 0)
			{
				g2d.drawString(Integer.toString(sigil.values[6])+" WDEF", 485, y);
			}

			if (sigil.values[7] != 0)
			{
				g2d.drawString(Integer.toString(sigil.values[7])+" DDEF", 545, y);
			}

			if (sigil.values[8] != 0)
			{
				g2d.drawString(Integer.toString(sigil.values[8])+" LDEF", 605, y);
			}
			y += 25;}
	}

	private BufferedImage getImage()
	{
		BufferedImage im = new BufferedImage(209, 240+(Character.inventory.get(selectedType).size()*20), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = im.createGraphics();

		if (selectedType == 0)
		{
			int i = 0;
			int ip = 0;

			g2d.setColor(Color.BLACK);
			g2d.drawString("Fire Sigils", 0, 20+(ip*20));
			ip++;
			for (Sigil sigil : fire)
			{

				if (i == itemIndex)
				{
					g2d.setColor(Color.BLUE);
				}
				else
				{
					g2d.setColor(Color.BLACK);
				}
				g2d.drawString(sigil.name, 10, 20+(ip*20));

				if (sigil.equipped)
					g2d.drawString("Equipped", 120, 20+(ip*20));


				i++;
				ip++;
			}
			g2d.setColor(Color.BLACK);
			g2d.drawString("Air Sigils", 0, 20+(ip*20));
			ip++;
			for (Sigil sigil : air)
			{

				if (i == itemIndex)
				{
					g2d.setColor(Color.BLUE);
				}
				else
				{
					g2d.setColor(Color.BLACK);
				}
				g2d.drawString(sigil.name, 10, 20+(ip*20));

				if (sigil.equipped)
					g2d.drawString("Equipped", 120, 20+(ip*20));


				i++;
				ip++;
			}
			g2d.setColor(Color.BLACK);
			g2d.drawString("Earth Sigils", 0, 20+(ip*20));
			ip++;
			for (Sigil sigil : earth)
			{

				if (i == itemIndex)
				{
					g2d.setColor(Color.BLUE);
				}
				else
				{
					g2d.setColor(Color.BLACK);
				}
				g2d.drawString(sigil.name, 10, 20+(ip*20));

				if (sigil.equipped)
					g2d.drawString("Equipped", 120, 20+(ip*20));


				i++;
				ip++;
			}
			g2d.setColor(Color.BLACK);
			g2d.drawString("Water Sigils", 0, 20+(ip*20));
			ip++;
			for (Sigil sigil : water)
			{

				if (i == itemIndex)
				{
					g2d.setColor(Color.BLUE);
				}
				else
				{
					g2d.setColor(Color.BLACK);
				}
				g2d.drawString(sigil.name, 10, 20+(ip*20));

				if (sigil.equipped)
					g2d.drawString("Equipped", 120, 20+(ip*20));


				i++;
				ip++;
			}
			g2d.setColor(Color.BLACK);
			g2d.drawString("Death Sigils", 0, 20+(ip*20));
			ip++;
			for (Sigil sigil : death)
			{

				if (i == itemIndex)
				{
					g2d.setColor(Color.BLUE);
				}
				else
				{
					g2d.setColor(Color.BLACK);
				}
				g2d.drawString(sigil.name, 10, 20+(ip*20));

				if (sigil.equipped)
					g2d.drawString("Equipped", 120, 20+(ip*20));


				i++;
				ip++;
			}
			g2d.setColor(Color.BLACK);
			g2d.drawString("Life Sigils", 0, 20+(ip*20));
			ip++;
			for (Sigil sigil : life)
			{

				if (i == itemIndex)
				{
					g2d.setColor(Color.BLUE);
				}
				else
				{
					g2d.setColor(Color.BLACK);
				}
				g2d.drawString(sigil.name, 10, 20+(ip*20));

				if (sigil.equipped)
					g2d.drawString("Equipped", 120, 20+(ip*20));


				i++;
				ip++;
			}
		}
		else if (selectedType == 1)
		{
			ArrayList<Upgrade> upgrades = null;
			upgrades = scrolls;
			
			int i = 0;
			for (Upgrade upgrade : upgrades)
			{

				if (i == itemIndex)
				{
					g2d.setColor(Color.BLUE);
				}
				else
				{
					g2d.setColor(Color.BLACK);
				}
				g2d.drawString(upgrade.name, 10, 20+(i*20));
				
				if (upgrade.used)
					g2d.drawString("Used", 100, 20+(i*20));

				i++;
			}
		}
		else
		{
			ArrayList<Item> items = null;
			items = misc;

			int i = 0;
			for (Item item : items)
			{

				if (i == itemIndex)
				{
					g2d.setColor(Color.BLUE);
				}
				else
				{
					g2d.setColor(Color.BLACK);
				}
				g2d.drawString(item.name, 10, 20+(i*20));
				g2d.drawString(Integer.toString(item.number), 100, 20+(i*20));


				i++;
			}
		}

		return im;
	}

}

class MainMenu extends MenuScreen
{
	BufferedImage[] images = new BufferedImage[3];

	/**
	 * @param menu
	 */
	public MainMenu(Menu menu) {
		super(menu);

		images[0] = GameData.getImage("GUI", "spellbookGameText.png");
		images[1] = GameData.getImage("GUI", "spellbookButton.png");
		images[2] = GameData.getImage("GUI", "spellbookButtonSelected.png");
		
		OggClip bgm = null;
		try {
			bgm = new OggClip("MainMenu.ogg");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Main.gamedata.changeSong(bgm);
	}

	int selectedIndex;

	@Override
	protected void drawBackground(Graphics2D g2d)
	{
		super.drawBackground(g2d);

		g2d.drawImage(images[0], 0, 0, null);
	}

	protected void drawLeft(Graphics2D g2d)
	{
		//110 , 65
		Color normal = new Color(0, 0, 0, 170);
		Color selected = new Color(54, 4, 89, 190);

		g2d.setFont(g2d.getFont().deriveFont((float) 20));

		if (selectedIndex == 0)
		{
			g2d.drawImage(images[2], 90, 90, null);
			g2d.setColor(selected);
			g2d.drawString("Continue", 200, 155);
		}
		else
		{
			g2d.drawImage(images[1], 90, 90, null);
			g2d.setColor(normal);
			g2d.drawString("Continue", 200, 155);
		}

		if (selectedIndex == 1)
		{
			g2d.drawImage(images[2], 490, 90, null);
			g2d.setColor(selected);
			g2d.drawString("Credits", 600, 155);
		}
		else
		{
			g2d.drawImage(images[1], 490, 90, null);
			g2d.setColor(normal);
			g2d.drawString("Credits", 600, 155);
		}

		if (selectedIndex == 2)
		{
			g2d.drawImage(images[2], 90, 220, null);
			g2d.setColor(selected);
			g2d.drawString("New Game", 200, 285);
		}
		else
		{
			g2d.drawImage(images[1], 90, 220, null);
			g2d.setColor(normal);
			g2d.drawString("New Game", 200, 285);
		}

		if (selectedIndex == 3)
		{
			g2d.drawImage(images[2], 490, 220, null);
			g2d.setColor(selected);
			g2d.drawString("Options", 600, 285);
		}
		else
		{
			g2d.drawImage(images[1], 490, 220, null);
			g2d.setColor(normal);
			g2d.drawString("Options", 600, 285);
		}

		if (selectedIndex == 4)
		{
			g2d.drawImage(images[2], 90, 350, null);
			g2d.setColor(selected);
			g2d.drawString("Load Game", 200, 415);
		}
		else
		{
			g2d.drawImage(images[1], 90, 350, null);
			g2d.setColor(normal);
			g2d.drawString("Load Game", 200, 415);
		}

		if (selectedIndex == 5)
		{
			g2d.drawImage(images[2], 490, 350, null);
			g2d.setColor(selected);
			g2d.drawString("Quit", 600, 415);
		}
		else
		{
			g2d.drawImage(images[1], 490, 350, null);
			g2d.setColor(normal);
			g2d.drawString("Quit", 600, 415);
		}
	}

	/* (non-Javadoc)
	 * @see Menu#evaluateButtons()
	 */
	@Override
	void evaluateButtons() {
		if (MainCanvas.up)
		{
			selectedIndex -= 2;
			MainCanvas.up = false;
		}
		else if (MainCanvas.right)
		{
			selectedIndex++;
			MainCanvas.right = false;
		}
		else if (MainCanvas.left)
		{
			selectedIndex--;
			MainCanvas.left = false;
		}
		else if (MainCanvas.down)
		{
			selectedIndex += 2;
			MainCanvas.down = false;
		}
		else if (MainCanvas.enter)
		{
			if (selectedIndex == 0)
			{
				File directory = new File("Data/Saves");
				File[] files = directory.listFiles();
				Arrays.sort(files, new Comparator<File>(){
					public int compare(File f1, File f2)
					{
						return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
					} });

				Main.gamedata.loadGame(files[0]);
				Main.setState(1);
			}
			else if (selectedIndex == 1)
			{
				menu.changeMenu("Credits");
			}
			else if (selectedIndex == 2)
			{
				Main.setState(1);
				Main.gamedata.newGame();
			}
			else if (selectedIndex == 3)
			{
				menu.changeMenu("Options");
			}
			else if (selectedIndex == 4)
			{
				menu.changeMenu("MainLoad");
			}
			else if (selectedIndex == 5)
			{
				Main.setState(0);
			}

			MainCanvas.enter = false;
		}

		if (selectedIndex < 0)
		{
			selectedIndex = 0;
		}
		else if (selectedIndex > 5)
		{
			selectedIndex = 5;
		}

	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawRight(java.awt.Graphics2D)
	 */
	@Override
	protected void drawRight(Graphics2D g2d) {
		// TODO Auto-generated method stub

	}

}

class MainLoadMenu extends MenuScreen
{

	int selectedIndex = 0;
	File[] files;
	SaveGame[] saves;
	BufferedImage[] images = new BufferedImage[1];
	SaveBlock[] saveBlocks;

	public MainLoadMenu(Menu menu) {
		super(menu);
		
		HashMap<String, ArrayList<Integer>> saveIndexes = new HashMap<String, ArrayList<Integer>>();

		File directory = new File("Data/Saves");
		files = directory.listFiles();
		Arrays.sort(files, new Comparator<File>(){
			public int compare(File f1, File f2)
			{
				return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
			} });

		saves = new SaveGame[files.length];

		for (int i = 0; i < files.length; i++)
		{
			saves[i] = loadFile(files[i]);
		}
		
		for (int i = 0; i < files.length; i++)
		{
			if (saveIndexes.containsKey(saves[i].sessionID))
			{
				ArrayList<Integer> saveblock = saveIndexes.get(saves[i].sessionID);
				saveblock.add(i);
			}
			else
			{
				ArrayList<Integer> saveblock = new ArrayList<Integer>();
				saveblock.add(i);
				saveIndexes.put(saves[i].sessionID, saveblock);
			}
		}
		
		saveBlocks = new SaveBlock[saveIndexes.size()];
		
		int i = 0;
		for (Map.Entry<String, ArrayList<Integer>> entry : saveIndexes.entrySet())
		{
			saveBlocks[i] = new SaveBlock(entry.getKey(), entry.getValue(), files[entry.getValue().get(0)].lastModified());
			
			i++;
		}
		
		Arrays.sort(saveBlocks, new Comparator<SaveBlock>(){
			public int compare(SaveBlock sb1, SaveBlock sb2)
			{
				return Long.valueOf(sb2.latestSave).compareTo(sb1.latestSave);
			} });
		
		images[0] = GameData.getImage("GUI", "spellbookFilesLoadText.png");
	}

	@Override
	protected void drawBackground(Graphics2D g2d)
	{
		super.drawBackground(g2d);

		g2d.drawImage(images[0], 0, 0, null);
	}
	/* (non-Javadoc)
	 * @see MenuScreen#evaluateButtons()
	 */
	@Override
	void evaluateButtons() {
		if (MainCanvas.up)
		{
			selectedIndex--;

			MainCanvas.up = false;
		}
		else if (MainCanvas.down)
		{
			selectedIndex++;

			MainCanvas.down = false;
		}
		else if (MainCanvas.left)
		{
			selectedIndex--;

			MainCanvas.left = false;
		}
		else if (MainCanvas.right)
		{
			selectedIndex++;

			MainCanvas.right = false;
		}
		else if (MainCanvas.esc)
		{
			menu.changeMenu("Main");
			MainCanvas.esc = false;
		}
		else if (MainCanvas.enter)
		{
			if (selectedIndex < files.length)
			{
				Main.setState(1);
				Main.gamedata.loadGame(files[getSelectedFileIndex()]);
			}
			else if (selectedIndex == files.length)
			{
				menu.changeMenu("Main");
			}

			MainCanvas.enter = false;
		}

		if (selectedIndex < 0)
		{
			selectedIndex = 0;
		}
		if (selectedIndex > files.length)
		{
			selectedIndex = files.length;
		}
	}
	
	public Integer getSelectedFileIndex()
	{
		int i = 0;
		for (SaveBlock sb : saveBlocks)
		{
			ArrayList<Integer> saveblock = sb.saveIndexes;
			
			for (Integer saveI : saveblock)
			{
				if (selectedIndex == i)
				{
					return saveI;
				}
				
				i++;
			}
		}

		return 0;
	}
	
	public Integer getSelectedFilePosition()
	{
		int selectedI = selectedIndex;
		int i = 0;
		for (SaveBlock sb : saveBlocks)
		{
			ArrayList<Integer> saveblock = sb.saveIndexes;
			
			i++;
			
			if (selectedI-saveblock.size() <= 0)
			{
				i += selectedI;
				break;
			}
			else
			{
				selectedI -= saveblock.size();
				i += saveblock.size();
			}
		}

		return i;
	}

	public SaveGame loadFile(File file)
	{
		SaveGame save = null;

		try{
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream oin = new ObjectInputStream(fin);
			save = (SaveGame) oin.readObject();
			oin.close();
			fin.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return save;
	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawLeft(java.awt.Graphics2D)
	 */
	@Override
	protected void drawLeft(Graphics2D g2d) {
		if (selectedIndex < files.length)
		{
			int i = getSelectedFileIndex();
			
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(files[i].lastModified());

			String timePlayed = String.format("%d min, %d sec", 
					TimeUnit.MILLISECONDS.toMinutes(saves[i].timePlayed),
					TimeUnit.MILLISECONDS.toSeconds(saves[i].timePlayed) - 
					TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(saves[i].timePlayed))
					);

			g2d.setColor(Color.BLACK);
			g2d.drawString("Filename:", 70, 190);
			g2d.drawString(files[i].getName(), 200, 190);
			g2d.drawString("Last Saved:", 70, 210);
			g2d.drawString(formatter.format(calendar.getTime()), 200, 210);
			g2d.drawString("Current Level:", 70, 240);
			g2d.drawString(saves[i].currentLevel, 200, 240);
			g2d.drawString("Time Played:", 70, 270);
			g2d.drawString(timePlayed, 200, 270);
		}

	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawRight(java.awt.Graphics2D)
	 */
	@Override
	protected void drawRight(Graphics2D g2d) {

		int filePos = selectedIndex;

		if (filePos >= files.length)
		{
			filePos = files.length-1;
		}
		
		int i = getSelectedFilePosition();
		g2d.drawImage(getImage(), 500, 90, 695, 485, 0, -100+(i*20), 195, 295+(i*20), null);


		if (selectedIndex == files.length)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}

		g2d.drawString("Back", 610, 545);
	}

	private BufferedImage getImage()
	{
		BufferedImage im = new BufferedImage(270, 80+(files.length*20), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = im.createGraphics();

		int i = 0;
		int selectedI = 0;
		for (SaveBlock sb : saveBlocks)
		{
			ArrayList<Integer> saveblock = sb.saveIndexes;
			
			g2d.setColor(Color.BLACK);
			g2d.drawString(sb.name, 5, 30+(i*20));
			
			i++;
			
			int savenum = 0;
			for (Integer saveI : saveblock)
			{
				if (selectedIndex == selectedI)
				{
					g2d.setColor(Color.BLUE);
				}
				else
				{
					g2d.setColor(Color.BLACK);
				}

				String name = "Save "+savenum;
				if (files[saveI].getName().equals("autosave.sav"))
				{
					name = "Autosave";
				}
				else
				{
					savenum++;
				}
				g2d.drawString(name, 15, 30+(i*20));
				
				i++;
				selectedI++;				
			}
		}

		g2d.dispose();

		return im;
	}

}

class CreditsMenu extends MenuScreen
{

	/**
	 * @param menu
	 */
	public CreditsMenu(Menu menu) {
		super(menu);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see MenuScreen#evaluateButtons()
	 */
	@Override
	void evaluateButtons() {
		if ((MainCanvas.esc) || (MainCanvas.enter))
		{
			menu.changeMenu("Main");
			MainCanvas.esc = false;
			MainCanvas.enter = false;
		}

	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawLeft(java.awt.Graphics2D)
	 */
	@Override
	protected void drawLeft(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);

		g2d.drawString("Kevin Glass - easyOgg Library", 100, 80);

		g2d.drawString("SFX by www.freesfx.co.uk", 100, 180);
		
		g2d.drawString("Kevin MacLeod - Music", 100, 280);
		g2d.drawString("www.incompetech.com", 100, 300);

		g2d.drawString("Me - The Rest Of The Game :D", 100, 380);

	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawRight(java.awt.Graphics2D)
	 */
	@Override
	protected void drawRight(Graphics2D g2d) {
		g2d.setColor(Color.BLUE);
		g2d.drawString("Back", 570, 480);

	}

}

class OptionsMenu extends MenuScreen
{
	int selectedIndex = 0;

	/**
	 * @param menu
	 */
	public OptionsMenu(Menu menu) {
		super(menu);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see MenuScreen#evaluateButtons()
	 */
	@Override
	void evaluateButtons() {
		// TODO Auto-generated method stub
		if (MainCanvas.esc)
		{
			menu.changeMenu("Main");

			MainCanvas.esc = false;
		}
		else if (MainCanvas.up)
		{
			selectedIndex--;

			MainCanvas.up = false;
		}
		else if (MainCanvas.down)
		{
			selectedIndex++;

			MainCanvas.down = false;
		}
		else if (MainCanvas.right)
		{
			if (selectedIndex == 2)
			{
				GameData.gain += 0.05f;
				if (GameData.gain > 1)
					GameData.gain = 1.0f;
				Main.gamedata.BGM.setGain(GameData.gain);
			}
			
			MainCanvas.right = false;
		}
		else if (MainCanvas.left)
		{
			if (selectedIndex == 2)
			{
				GameData.gain -= 0.05f;
				if (GameData.gain < 0)
					GameData.gain = 0.0f;
				Main.gamedata.BGM.setGain(GameData.gain);
			}
			
			MainCanvas.left = false;
		}
		else if (MainCanvas.enter)
		{
			if (selectedIndex == 0)
			{
				Main.toggleFullscreen();
			}
			else if (selectedIndex == 1)
			{
				if (SoundEffect.volume == SoundEffect.Volume.MUTE)
				{
					SoundEffect.volume = SoundEffect.Volume.UNMUTE;
				}
				else
				{
					SoundEffect.volume = SoundEffect.Volume.MUTE;
				}
			}
			else if (selectedIndex == 3)
			{
				if (Main.debug)
					Main.debug = false;
				else
					Main.debug = true;
			}
			else if (selectedIndex == 4)
			{
				if (Main.preloadCollisionMap)
					Main.preloadCollisionMap = false;
				else
					Main.preloadCollisionMap = true;
			}
			else if (selectedIndex == 5)
			{
				menu.changeMenu("Main");
			}

			MainCanvas.enter = false;
		}

		if (selectedIndex < 0)
			selectedIndex = 0;
		else if (selectedIndex > 5)
			selectedIndex = 5;
	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawLeft(java.awt.Graphics2D)
	 */
	@Override
	protected void drawLeft(Graphics2D g2d) {
		if (selectedIndex == 0)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}

		g2d.drawString("Fullscreen:", 80, 100);
		g2d.drawString(""+Main.fullscreen, 250, 100);

		if (selectedIndex == 1)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}

		g2d.drawString("Sound Effect Muted:", 80, 200);

		if (SoundEffect.volume == SoundEffect.Volume.MUTE)
		{
			g2d.drawString("true", 250, 200);
		}
		else
		{
			g2d.drawString("false", 250, 200);
		}
		
		if (selectedIndex == 2)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		
		g2d.drawString("BGM volume:", 80, 300);

		g2d.drawString(""+(int)(GameData.gain*100), 250, 300);
		
		if (selectedIndex == 3)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		
		g2d.drawString("Debug Info:", 80, 400);

		g2d.drawString(""+(Main.debug), 250, 400);
		
		if (selectedIndex == 4)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		
		g2d.drawString("Preload Collision:", 80, 500);

		g2d.drawString(""+(Main.preloadCollisionMap), 250, 500);
		
		if (selectedIndex == 5)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		
		g2d.drawString("Main Menu", 480, 500);
	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawRight(java.awt.Graphics2D)
	 */
	@Override
	protected void drawRight(Graphics2D g2d) {
		// TODO Auto-generated method stub

	}

}

class GameOptionsMenu extends MenuScreen
{
	int selectedIndex = 0;

	/**
	 * @param menu
	 */
	public GameOptionsMenu(Menu menu) {
		super(menu);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see MenuScreen#evaluateButtons()
	 */
	@Override
	void evaluateButtons() {
		// TODO Auto-generated method stub
		if (MainCanvas.esc)
		{
			menu.changeMenu("Game");

			MainCanvas.esc = false;
		}
		else if (MainCanvas.up)
		{
			selectedIndex--;

			MainCanvas.up = false;
		}
		else if (MainCanvas.down)
		{
			selectedIndex++;

			MainCanvas.down = false;
		}
		else if (MainCanvas.right)
		{
			if (selectedIndex == 2)
			{
				GameData.gain += 0.05f;
				if (GameData.gain > 1)
					GameData.gain = 1.0f;
				Main.gamedata.BGM.setGain(GameData.gain);
			}
			
			MainCanvas.right = false;
		}
		else if (MainCanvas.left)
		{
			if (selectedIndex == 2)
			{
				GameData.gain -= 0.05f;
				if (GameData.gain < 0)
					GameData.gain = 0.0f;
				Main.gamedata.BGM.setGain(GameData.gain);
			}
			
			MainCanvas.left = false;
		}
		else if (MainCanvas.enter)
		{
			if (selectedIndex == 0)
			{
				Main.toggleFullscreen();
			}
			else if (selectedIndex == 1)
			{
				if (SoundEffect.volume == SoundEffect.Volume.MUTE)
				{
					SoundEffect.volume = SoundEffect.Volume.UNMUTE;
				}
				else
				{
					SoundEffect.volume = SoundEffect.Volume.MUTE;
				}
			}
			else if (selectedIndex == 3)
			{
				if (Main.debug)
					Main.debug = false;
				else
					Main.debug = true;
			}
			else if (selectedIndex == 4)
			{
				if (Main.preloadCollisionMap)
					Main.preloadCollisionMap = false;
				else
					Main.preloadCollisionMap = true;
			}
			else if (selectedIndex == 5)
			{
				menu.changeMenu("Game");
			}

			MainCanvas.enter = false;
		}

		if (selectedIndex < 0)
			selectedIndex = 0;
		else if (selectedIndex > 5)
			selectedIndex = 5;
	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawLeft(java.awt.Graphics2D)
	 */
	@Override
	protected void drawLeft(Graphics2D g2d) {
		if (selectedIndex == 0)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}

		g2d.drawString("Fullscreen:", 80, 100);
		g2d.drawString(""+Main.fullscreen, 250, 100);

		if (selectedIndex == 1)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}

		g2d.drawString("Sound Effect Muted:", 80, 200);

		if (SoundEffect.volume == SoundEffect.Volume.MUTE)
		{
			g2d.drawString("true", 250, 200);
		}
		else
		{
			g2d.drawString("false", 250, 200);
		}
		
		if (selectedIndex == 2)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		
		g2d.drawString("BGM volume:", 80, 300);

		g2d.drawString(""+(int)(GameData.gain*100), 250, 300);
		
		if (selectedIndex == 3)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		
		g2d.drawString("Debug Info:", 80, 400);

		g2d.drawString(""+(Main.debug), 250, 400);
		
		if (selectedIndex == 4)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		
		g2d.drawString("Preload Collision:", 80, 500);

		g2d.drawString(""+(Main.preloadCollisionMap), 250, 500);
		
		if (selectedIndex == 5)
		{
			g2d.setColor(Color.BLUE);
		}
		else
		{
			g2d.setColor(Color.BLACK);
		}
		
		g2d.drawString("Main Menu", 480, 500);
	}

	/* (non-Javadoc)
	 * @see MenuScreen#drawRight(java.awt.Graphics2D)
	 */
	@Override
	protected void drawRight(Graphics2D g2d) {
		// TODO Auto-generated method stub

	}

}