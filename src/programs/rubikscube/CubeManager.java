package programs.rubikscube;

import static programs.rubikscube.RubiksCube.*;
import static programs.rubikscube.CubeApplication.*;
import java.util.*;
import javafx.animation.*;
import javafx.animation.Animation.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.util.*;


/**
 * Class to contain references and to manage Rubik's cube units.
 * 
 * @author hasol
 */
public class CubeManager
{
	/** Default color scheme of the Rubik's cube. */
	final static CubeMap <Color> DEFAULT_CUBE;
	/** Defines scale to translate all objects along Z axis */
	final static double translate_factor = -2.5;
	/** Defines scale of background box. */
	final static double background_factor = 8.0;
	
	static
	{
		// define default color scheme.
		DEFAULT_CUBE = new CubeMap <> ();
		for (CubeAxis dir : CubeAxis.directions)
		{
			HashMap <Integer, Color> colors = new HashMap <> ();
			DEFAULT_CUBE.put (dir, colors);
			Color color;
			switch (dir)
			{
				case CENTER:
					color = Color.BLACK;
					break;
				case X_NEG:
					color = Color.ORANGE;
					break;
				case X_POS:
					color = Color.RED;
					break;
				case Y_NEG:
					color = Color.YELLOW;
					break;
				case Y_POS:
					color = Color.WHITE;
					break;
				case Z_NEG:
					color = Color.BLUE;
					break;
				case Z_POS:
					color = Color.GREEN;
					break;
				default:
					color = null;
					break;
			}
			IntDuo axis = dir.otherAxis ();
			for (int i = 0; i < DIM; i++)
			{
				CubeAxis d1 = CubeAxis.getCubeAxis (axis.first, i - 1);
				for (int j = 0; j < DIM; j++)
				{
					CubeAxis d2 = CubeAxis.getCubeAxis (axis.second, j - 1);
					int value = CubeAxis.getValue (d1, d2);
					DEFAULT_CUBE.get (dir).put (value, color);
				}
			}
		}
	}
	
	/** JavaFX group to be added to pane/scene. */
	Group cubeGroup;
	/** Transparent background stage. */
	CubeCore background;
	/** Collection of all 81 boxes. */
	ArrayList <CubeCore> allItems;
	/** Collection of all 27 cores. */
	ArrayList <CubeCore> cores;
	/** Collection of all 54 shells. */
	ArrayList <CubeLayer> shells;
	/** Nested map for cores. May contain duplicate references. */
	CubeMap <CubeCore> sides;
	/** Nested map for shells. */
	CubeMap <CubeLayer> faces;
	
	/** Responsible for animation of object. */
	volatile Timeline timeline;
	/** Planning of timeline moves. */
	volatile ArrayList <CubeDuo> actionQueue;
	/** Slider to control animation duration. */
	double duration;
	
	/**
	 * Construct cube with given color as faces.
	 * 
	 * @param color
	 *        color to fill shells.
	 */
	public CubeManager (Color color)
	{
		this.cubeGroup = new Group ();
		this.allItems = new ArrayList <> (CORE_SIZE + LAYER_SIZE);
		this.cores = new ArrayList <> (CORE_SIZE);
		this.shells = new ArrayList <> (LAYER_SIZE);
		this.sides = new CubeMap <> (CubeAxis.values ());
		this.faces = new CubeMap <> (CubeAxis.directions);
		this.timeline = new Timeline ();
		this.actionQueue = new ArrayList <> ();
		this.duration = AnimationAction.def * AnimationAction.factor;
		this.init (color);
	}
	
	/**
	 * Construct cube with default color as faces.
	 */
	public CubeManager ()
	{
		this (Color.BLACK);
		this.resetState ();
	}
	
	/**
	 * Reset cube's face and underlying information as default values.
	 */
	public void resetState ()
	{
		this.setColors (DEFAULT_CUBE);
		synchronized (actionQueue)
		{
			this.actionQueue.clear ();
			this.timeline.stop ();
		}
		for (CubeLayer layer : shells)
		{
			layer.setLinkThis ();
			layer.resetTransform ();
		}
		for (CubeCore core : cores)
		{
			core.resetTransform ();
		}
	}
	
	/**
	 * Initialize all items.
	 * 
	 * @param color
	 *        color to fill shells.
	 */
	private void init (Color color)
	{
		// Populate cores.
		for (int i = 0; i < DIM; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				for (int k = 0; k < DIM; k++)
				{
					int value = CubeAxis.getValue (i - 1, j - 1, k - 1);
					CubeCore cube = new CubeCore (length, value);
					allItems.add (cube);
					cores.add (cube);
					if (value == CubeAxis.CENTER.code)
					{
						sides.get (CubeAxis.CENTER).put (value, cube);
					}
					else
					{
						for (CubeAxis dir : CubeAxis.getCubeAxis (value))
						{
							if (value == dir.code || (value & dir.code) != 0)
							{
								int key = value & ~dir.code;
								sides.get (dir).put (key, cube);
							}
						}
					}
				}
			}
		}
		// Populate shells.
		for (CubeAxis dir : CubeAxis.directions)
		{
			IntDuo axis = dir.otherAxis ();
			for (int i = 0; i < DIM; i++)
			{
				CubeAxis d1 = CubeAxis.getCubeAxis (axis.first, i - 1);
				for (int j = 0; j < DIM; j++)
				{
					CubeAxis d2 = CubeAxis.getCubeAxis (axis.second, j - 1);
					// Generate branching direction for given face.
					int value = CubeAxis.getValue (d1, d2);
					CubeLayer layer = new CubeLayer (length, offset, color, dir, value);
					allItems.add (layer);
					shells.add (layer);
					faces.get (dir).put (value, layer);
				}
			}
		}
		// Prepare background stage.
		this.background = new CubeCore (length * background_factor, 0);
		this.background.setMaterial (new PhongMaterial (new Color (0, 0, 0, 0.1)));
		this.allItems.add (background);
		// Transfer items.
		this.cubeGroup.getChildren ().setAll (allItems);
		// Translate all.
		this.cubeGroup.setTranslateZ (cubeGroup.getTranslateZ () + length * translate_factor);
	}
	
	/**
	 * Set colors for each faces.
	 * 
	 * @param map
	 *        Map specifying colors to fill.
	 */
	public void setColors (CubeMap <Color> map)
	{
		for (CubeAxis dir : CubeAxis.values ())
		{
			if (map.containsKey (dir) && faces.containsKey (dir))
			{
				HashMap <Integer, Color> colors = map.get (dir);
				HashMap <Integer, CubeLayer> face = faces.get (dir);
				for (Integer val : colors.keySet ())
				{
					if (face.containsKey (val))
					{
						face.get (val).setColor (colors.get (val));
					}
				}
			}
		}
	}
	
	/**
	 * Set colors for all faces.
	 * 
	 * @param color
	 *        Color to fill shells.
	 */
	public void setColors (Color color)
	{
		for (CubeLayer layer : shells)
		{
			layer.setColor (color);
		}
	}
	
	/**
	 * Get colors for each faces.
	 * 
	 * @return Map specifying colors filled.
	 */
	public CubeMap <Color> getColors ()
	{
		CubeMap <Color> map = new CubeMap <> ();
		for (CubeAxis dir : CubeAxis.directions)
		{
			HashMap <Integer, Color> colors = new HashMap <> ();
			HashMap <Integer, CubeLayer> face = faces.get (dir);
			for (Integer val : face.keySet ())
			{
				colors.put (val, face.get (val).getColor ());
			}
			map.put (dir, colors);
		}
		return map;
	}
	
	/**
	 * Set CubeLayer data from 3-tuple.
	 * 
	 * @param map
	 *        Map specifying color and source/destination pointers.
	 */
	public void setTrios (CubeMap <CubeTrio> map)
	{
		for (CubeAxis dir : CubeAxis.values ())
		{
			if (map.containsKey (dir) && faces.containsKey (dir))
			{
				HashMap <Integer, CubeTrio> colors = map.get (dir);
				HashMap <Integer, CubeLayer> face = faces.get (dir);
				for (Integer val : colors.keySet ())
				{
					if (face.containsKey (val))
					{
						face.get (val).setTrio (colors.get (val));
					}
				}
			}
		}
	}
	
	/**
	 * Get CubeLayer data as 3-tuple.
	 * 
	 * @return Map specifying color and source/desitnation pointers.
	 */
	public CubeMap <CubeTrio> getTrios ()
	{
		CubeMap <CubeTrio> map = new CubeMap <> ();
		for (CubeAxis dir : CubeAxis.directions)
		{
			HashMap <Integer, CubeTrio> colors = new HashMap <> ();
			HashMap <Integer, CubeLayer> face = faces.get (dir);
			for (Integer val : face.keySet ())
			{
				colors.put (val, face.get (val).getTrio ());
			}
			map.put (dir, colors);
		}
		return map;
	}
	
	/**
	 * Check integrity of all CubeLayer's pointers.
	 * 
	 * @return {@code true} if underlying CubeLayer structure are valid, otherwise {@code false}.
	 */
	public boolean checkLinks ()
	{
		for (CubeLayer layer : shells)
		{
			if (!layer.checkLink ())
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Try to infer and re-structure correct source and destination links from CubeLayers.
	 * @return {@code true} if successful, otherwise, {@code false}.
	 */
	public boolean inferLinks ()
	{
		// TODO complete.
		HashMap <Color, CubeAxis> index = new HashMap <> ();
		for (CubeAxis key : faces.keySet ())
		{
			CubeLayer layer = faces.get (key).get (0);
			Color color = layer.getColor ();
			if (index.containsKey (color))
			{ // Duplicate center piece.
				return false;
			}
			index.put (color, key);
			layer.setLinkThis ();
		}
		HashSet <Integer> set = new HashSet <> ();
		CubeAxis[] axis = new CubeAxis[DIM];
		for (int i = 0; i < DIM; i++)
		{
			axis[X] = CubeAxis.getCubeAxis (X, i - 1);
			for (int j = 0; j < DIM; j++)
			{
				axis[Y] = CubeAxis.getCubeAxis (Y, j - 1);
				for (int k = 0; k < DIM; k++)
				{
					axis[Z] = CubeAxis.getCubeAxis (Z, k - 1);
					// Generate all X, Y, Z combination, once for each axis.
					int value = CubeAxis.getValue (axis);
					if (Integer.bitCount (value) > 1)
					{
						int sourcevalue = 0;
						// Compute source position value.
						for (int l = 0; l < DIM; l++)
						{
							if (axis[l] != null)
							{
								int newvalue = value & ~axis[l].code;
								CubeLayer layer = faces.get (axis[l]).get (newvalue);
								sourcevalue |= index.get (layer.getColor ()).code;
							}
						}
						if (set.contains (sourcevalue))
						{ // Duplicate source piece.
							return false;
						}
						set.add (sourcevalue);
						
					}
				}
			}
		}
		return true;
	}
	
	/*
	 * public void turn (CubeAxis dir, boolean mode)
	 * {
	 * final double angle = 90;
	 * CubeAxis[] others = dir.others ();
	 * IntDuo other = dir.otherAxis ();
	 * assert others[0].axis == other.getKey () : dir; // assert correct ordering of two results.
	 * // re-structure face
	 * HashMap <Integer, CubeLayer> face = faces.get (dir), copyFace = new HashMap <> (face);
	 * for (CubeCore cube : face.values ())
	 * {
	 * cube.rotate (dir.axis, mode ? dir.sign * angle : -dir.sign * angle);
	 * }
	 * for (int key : copyFace.keySet ())
	 * {
	 * CubeLayer layer = copyFace.get (key);
	 * int newkey = turnDir (dir, other, key, mode);
	 * layer.direction = dir.code | newkey;
	 * layer.position = newkey;
	 * face.put (newkey, layer);
	 * }
	 * // re-structure side
	 * HashMap <Integer, CubeCore> side = sides.get (dir), copySide = new HashMap <> (side);
	 * for (CubeCore cube : side.values ())
	 * {
	 * cube.rotate (dir.axis, mode ? dir.sign * angle : -dir.sign * angle);
	 * }
	 * for (int key : copySide.keySet ())
	 * {
	 * CubeCore cube = copySide.get (key);
	 * int newkey = turnDir (dir, other, key, mode);
	 * cube.direction = dir.code | newkey;
	 * side.put (newkey, cube);
	 * for (CubeAxis d : CubeAxis.getCubeAxis (newkey))
	 * {
	 * int tempkey = cube.direction & ~d.code;
	 * sides.get (d).put (tempkey, cube);
	 * }
	 * }
	 * // re-structure shell
	 * }
	 * public void turn2 (CubeAxis dir, boolean mode)
	 * {
	 * final double angle = 90;
	 * double rotation = mode ? dir.sign * angle : -dir.sign * angle;
	 * HashMap <Integer, CubeLayer> face = faces.get (dir);
	 * ArrayList <KeyValue> frames = new ArrayList <> ();
	 * for (CubeCore cube : face.values ())
	 * {
	 * cube.resetTransform ();
	 * frames.add (new KeyValue (cube.rotate (dir.axis), rotation));
	 * }
	 * HashMap <Integer, CubeCore> side = sides.get (dir);
	 * for (CubeCore cube : side.values ())
	 * {
	 * cube.resetTransform ();
	 * frames.add (new KeyValue (cube.rotate (dir.axis), rotation));
	 * }
	 * timeline.stop ();
	 * timeline.getKeyFrames ().setAll (new KeyFrame (new Duration (5000), null, null, frames));
	 * timeline.play ();
	 * }
	 ** Determine new key value of given rotation.
	 * @param dir
	 * Direction of face.
	 * @param other
	 * Pair of other two axis.
	 * @param key
	 * HashKey of position.
	 * @param mode
	 * rotation mode.
	 * @return new HashKey of position.
	 * public static int turnDir (CubeAxis dir, IntDuo other, int key, boolean mode)
	 * {
	 * CubeAxis[] keydir = CubeAxis.getCubeAxis (key);
	 * for (int i = 0; i < keydir.length; i++)
	 * {
	 * if (keydir[i].axis == other.getKey ())
	 * {
	 * keydir[i] = CubeAxis.getCubeAxis (other.getValue (), dir.sign * keydir[i].sign * (mode ? 1 : -1));
	 * }
	 * else if (keydir[i].axis == other.getValue ())
	 * {
	 * keydir[i] = CubeAxis.getCubeAxis (other.getKey (), dir.sign * keydir[i].sign * (mode ? -1 : 1));
	 * }
	 * else
	 * {
	 * assert false; // this should be unreachable.
	 * }
	 * }
	 * return CubeAxis.getValue (keydir);
	 * }
	 ** Determine new key value of given rotation.
	 * @param dir
	 * Direction of face.
	 * @param key
	 * HashKey of position.
	 * @param mode
	 * rotation mode.
	 * @return new HashKey of position.
	 * public static int turnDir (CubeAxis dir, int key, boolean mode)
	 * {
	 * return turnDir (dir, dir.otherAxis (), key, mode);
	 * }
	 */
	
	/**
	 * Schedule next move to turn cube's face on given axis direction with rotation mode.
	 * 
	 * @param action
	 *        Pair of Direction of face and rotation mode.
	 *        {@code true} for counterclockwise rotation, {@code false} for clockwise rotation.
	 */
	public void turn (CubeDuo action)
	{
		synchronized (actionQueue)
		{
			actionQueue.add (action);
			if (timeline.getStatus () == Status.STOPPED)
			{
				startTurn ();
			}
		}
	}
	
	/**
	 * Schedule next move to turn cube's face on given axis direction with rotation mode.
	 * 
	 * @param dir
	 *        Direction of face
	 * @param mode
	 *        {@code true} for counterclockwise rotation, {@code false} for clockwise rotation.
	 */
	public void turn (CubeAxis dir, boolean mode)
	{
		turn (new CubeDuo (dir, mode));
	}
	
	/**
	 * Start next scheduled turn.
	 * 
	 * @return {@code true} if successful, otherwise {@code false}.
	 */
	private boolean startTurn ()
	{
		synchronized (actionQueue)
		{
			if (actionQueue.size () == 0)
			{
				return false;
			}
			synchronized (timeline)
			{
				CubeDuo pair = actionQueue.remove (0);
				CubeAxis dir = pair.first;
				ArrayList <CubeCore> items = new ArrayList <> ();
				ArrayList <KeyValue> frames = new ArrayList <> ();
				// determine rotation.
				final double angle = 90, rotation = pair.second ? dir.sign * angle : -dir.sign * angle;
				// fill lists.
				for (CubeCore cube : sides.get (dir).values ())
				{ // cores.
					items.add (cube);
					frames.add (new KeyValue (cube.rotate (dir.axis), rotation));
				}
				for (CubeLayer cube : faces.get (pair.first).values ())
				{ // faces.
					items.add (cube);
					frames.add (new KeyValue (cube.rotate (dir.axis), rotation));
				}
				for (CubeAxis other : dir.others ())
				{ // side faces.
					for (CubeLayer cube : faces.get (other).values ())
					{
						if ((cube.position & dir.code) != 0)
						{
							items.add (cube);
							frames.add (new KeyValue (cube.rotate (dir.axis), rotation));
						}
					}
				}
				// start animation.
				TimelineAction actionHandler = new TimelineAction (pair, items);
				timeline.getKeyFrames ().setAll (new KeyFrame (new Duration (duration), null, actionHandler, frames));
				timeline.playFromStart ();
			}
		}
		return true;
	}
	
	/**
	 * Update colors of this cube based on the information.
	 * 
	 * @param action
	 *        rotation information.
	 */
	private void updateColors (CubeDuo action)
	{
		setTrios (CubeUtils.updateTrioMap (getTrios (), action));
	}
	
	/**
	 * Instance of EventHandler for expiration of Timeline event.
	 * Instance of this class belongs to each instance of CubeManager class.
	 * 
	 * @author hasol
	 */
	public class TimelineAction implements EventHandler <ActionEvent>
	{
		/** Action applied for animation. */
		private CubeDuo action;
		/** Targets applied for animation. */
		private ArrayList <CubeCore> items;
		
		/**
		 * Create new instance of this event handler.
		 * 
		 * @param action
		 *        applied action.
		 * @param items
		 *        applied targets.
		 */
		public TimelineAction (CubeDuo action, ArrayList <CubeCore> items)
		{
			this.action = action;
			this.items = items;
		}
		
		/**
		 * Upon the end of the animation, reset all transformation and swap all colors.
		 * This will make cube to be appear as if it is actually rotated.
		 * If available, this will automatically start next move.
		 */
		@Override
		public void handle (ActionEvent event)
		{
			for (CubeCore core : items)
			{
				core.resetTransform ();
			}
			updateColors (action);
			synchronized (actionQueue)
			{
				if (actionQueue.size () > 0)
				{
					startTurn ();
				}
			}
		}
	}
}
