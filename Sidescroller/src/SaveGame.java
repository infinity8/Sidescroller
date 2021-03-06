import java.awt.GraphicsConfiguration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lyeeedar
 *
 */
public class SaveGame implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 566737083548919063L;
	String currentLevel = "";
	HashMap<String, HashMap<String, Entity>> gameEntities = new HashMap<String, HashMap<String, Entity>>();
	Entity player = null;
	ArrayList<HashMap<String, Item>> inventory = new ArrayList<HashMap<String, Item>>();
	SpellsStageEntry[] socketedSpells = new SpellsStageEntry[5];
	ArrayList<ArrayList<SpellsStage>> spellTrees = new ArrayList<ArrayList<SpellsStage>>();
	long timePlayed = 0;
	int gender = 0;
	String sessionID;

	public static boolean save(GameData gamedata, File file)
	{
		SaveGame save = null;
		
		File dir = new File("Data/Saves");
		dir.mkdirs();

		if (file.exists())
		{
			try{
				FileInputStream fin = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fin);
				save = (SaveGame) in.readObject();
				in.close();
				fin.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else
		{
			save = new SaveGame();
			save.sessionID = GameData.gameSessionID;
			save.gameEntities = new HashMap<String, HashMap<String, Entity>>();
		}
		
		if (save.sessionID.equals(GameData.gameSessionID))
		{
			
		}
		else
		{
			save = new SaveGame();
			save.gameEntities = new HashMap<String, HashMap<String, Entity>>();
		}

		HashMap<String, Entity> saveMap = new HashMap<String, Entity>();

		for (Map.Entry<String, Entity> entry : gamedata.getGameEntities().entrySet()){
			Entity e = entry.getValue();

			if (e instanceof Spell)
			{
				continue;
			}
			else
			{
				entry.getValue().setTalking(false);
				saveMap.put(entry.getKey(), entry.getValue());
			}
		}

		if (save.gameEntities.containsKey(gamedata.getLevelName()))
		{
			save.gameEntities.remove(gamedata.getLevelName());
		}

		save.gameEntities.put(gamedata.getLevelName(), saveMap);
		
		save.currentLevel = gamedata.getLevelName();
		
		save.player = saveMap.get("Player");
		
		save.inventory = Character.inventory;
		
		save.socketedSpells = Character.socketedSpells;
		
		save.spellTrees.add(Character.fireSpells);
		save.spellTrees.add(Character.airSpells);
		save.spellTrees.add(Character.earthSpells);
		save.spellTrees.add(Character.waterSpells);
		save.spellTrees.add(Character.deathSpells);
		save.spellTrees.add(Character.lifeSpells);
		
		save.timePlayed = Character.timePlayed;
		save.gender = Character.gender;
		
		save.sessionID = GameData.gameSessionID;
		
		try
		{
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(save);
			out.close();
			fileOut.close();
		}
		catch(IOException i)
		{
			i.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean loadGame(File file, GameData gamedata)
	{
		
		GraphicsConfiguration gc = Main.gc;
		
		SaveGame save = null;
		
		File dir = new File("Data/Saves");
		dir.mkdirs();
		
		if (file == null)
			return false;
		
		Main.gamedata.loadStage = 1;
		Main.gamedata.loadText = "Searching The Past";
		Main.maincanvas.paintLoad(gc);
		if (file.exists())
		{
			try {
				FileInputStream fin = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fin);
				save = (SaveGame) in.readObject();
				in.close();
				fin.close();
			}
			catch (Exception ioe)
			{
				ioe.printStackTrace();
				return false;
			}
		}
		else
		{
			return false;
		}
		
		Main.gamedata.loadStage = 2;
		Main.gamedata.loadText = "Creating The World";
		Main.maincanvas.paintLoad(gc);
		Level level = Level.load(save.currentLevel+".data");
		if (level == null)
			return false;
		
		HashMap<String, Entity> gameEntities = level.gameEntities;
		
		Main.gamedata.loadStage = 3;
		Main.gamedata.loadText = "Replacing All Living Things";
		Main.maincanvas.paintLoad(gc);
		if (save.gameEntities.containsKey(level.name))
		{
			gameEntities = save.gameEntities.get(level.name);
		}
		gamedata.levelName = level.name;
		
		Main.gamedata.loadStage = 4;
		Main.gamedata.loadText = "Opening Your Eyes";
		Main.maincanvas.paintLoad(gc);
		for (Map.Entry<String, Entity> entry : gameEntities.entrySet())
		{
			Entity ent = entry.getValue();
			ent.processSpritesheet();
			ent.infoText  = new ArrayList<SystemMessage>();
		}
		
		Main.gamedata.loadStage = 5;
		Main.gamedata.loadText = "Painting The Land";
		Main.maincanvas.paintLoad(gc);
		gamedata.setGameEntities(gameEntities);
		gamedata.loadLevelImages(level.name);
		
		Main.gamedata.loadStage = 6;
		Main.gamedata.loadText = "Finalising Geology";
		Main.maincanvas.paintLoad(gc);
		
		Main.gamedata.loadStage = 7;
		Main.gamedata.loadText = "Filling Spellbook";
		Main.maincanvas.paintLoad(gc);
		Character.inventory = save.inventory;
		Character.socketedSpells = save.socketedSpells;
		
		Character.fireSpells = save.spellTrees.get(0);
		Character.airSpells = save.spellTrees.get(1);
		Character.earthSpells = save.spellTrees.get(2);
		Character.waterSpells = save.spellTrees.get(3);
		Character.deathSpells = save.spellTrees.get(4);
		Character.lifeSpells = save.spellTrees.get(5);
		
		Character.timePlayed = save.timePlayed;
		Character.gender = save.gender;
		
		Main.gamedata.loadStage = 8;
		Main.gamedata.loadText = "Creating Magic";
		Main.gamedata.transformAllowed = level.transformAllowed;
		Main.maincanvas.paintLoad(gc);
		Character.reloadAllImages();
		
		gamedata.systemMessages.clear();
		
		gamedata.changeSong(level.getBGM());
		
		GameData.gameSessionID = save.sessionID;
		
		return true;
	}
	
	public static boolean loadLevel(String levelName, GameData gamedata)
	{
		GraphicsConfiguration gc = Main.gc;
		SaveGame save = null;
		
		File dir = new File("Data/Saves");
		dir.mkdirs();
		
		File file = getMostRecentFile(GameData.gameSessionID);
		
		Main.gamedata.loadStage = 1;
		Main.gamedata.loadText = "Checking The Past";
		Main.maincanvas.paintLoad(gc);
		if ((file != null) && (file.exists()))
		{
			try {
				FileInputStream fin = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fin);
				save = (SaveGame) in.readObject();
				in.close();
				fin.close();
			}
			catch (Exception ioe)
			{
				ioe.printStackTrace();
				return false;
			}
		}
		else
		{
			save = new SaveGame();
		}
		
		Main.gamedata.loadStage = 2;
		Main.gamedata.loadText = "Creating The Land";
		Main.maincanvas.paintLoad(gc);
		Level level = Level.load(levelName+".data");
		
		HashMap<String, Entity> gameEntities = level.gameEntities;
		
		Main.gamedata.loadStage = 3;
		Main.gamedata.loadText = "Annihalting Native Inhabitants";
		Main.maincanvas.paintLoad(gc);
		if (save.gameEntities.containsKey(level.name))
		{
			gameEntities = save.gameEntities.get(level.name);
		}
		gamedata.levelName = level.name;
		
		Main.gamedata.loadStage = 4;
		Main.gamedata.loadText = "Powering You Up";
		Main.maincanvas.paintLoad(gc);
		
		if (save.player != null)
		{
			Entity oldPlayer = level.gameEntities.get("Player");
			level.gameEntities.remove("Player");

			if (oldPlayer != null)
				save.player.setPos(oldPlayer.getPos());

			gameEntities.put("Player", save.player);
			
			save.player.spriteFile = "male.png";
			save.player.setFaction("Player");
		}
		else
		{
			Entity oldPlayer = level.gameEntities.get("Player");
			level.gameEntities.remove("Player");
			
			Entity e = new Entity("Player", 80, 8, 8, new int[]{20, 20, 0}, 8, "male.png", new int[]{46, 18, 27, 69}, new boolean[]{true, true, false, false}, null);
			
			e.setPos(oldPlayer.getPos());
			e.setFaction("Player");
			
			gameEntities.put("Player", e);
		}
		
		Main.gamedata.loadStage = 5;
		Main.gamedata.loadText = "Opening Your Eyes";
		Main.maincanvas.paintLoad(gc);
		for (Map.Entry<String, Entity> entry : gameEntities.entrySet())
		{
			Entity ent = entry.getValue();
			
			ent.processSpritesheet();
			
			ent.infoText  = new ArrayList<SystemMessage>();
		}
		
		Main.gamedata.loadStage = 6;
		Main.gamedata.loadText = "Finalising Geology";
		Main.maincanvas.paintLoad(gc);
		gamedata.setGameEntities(gameEntities);
		gamedata.loadLevelImages(level.name);
		
		Main.gamedata.loadStage = 7;
		Main.gamedata.loadText = "Solidfying Ground";
		Main.maincanvas.paintLoad(gc);
		Main.gamedata.transformAllowed = level.transformAllowed;
		
		gamedata.changeSong(level.getBGM());
		
		return true;
	}
	
	private static File getMostRecentFile(String sessionID)
	{		
		File file = null;
		
		File files[] = null;
		File directory = new File("Data/Saves");
		files = directory.listFiles();
		Arrays.sort(files, new Comparator<File>(){
			public int compare(File f1, File f2)
			{
				return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
			} });

		for (int i = 0; i < files.length; i++)
		{
			SaveGame save = loadFile(files[i]);
			
			if (save == null)
				continue;
			
			if (save.sessionID.equals(sessionID))
			{
				file = files[i];
				break;
			}
		}
		
		return file;
	}

	private static SaveGame loadFile(File file)
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

}
