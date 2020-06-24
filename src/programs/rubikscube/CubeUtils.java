package programs.rubikscube;

// import static programs.rubikscube.RubiksCube.*;
import java.util.*;
import java.io.*;
import javafx.scene.paint.*;


/**
 * Class for Utilities.
 * 
 * @author hasol
 */
public class CubeUtils
{
	/** Color Map from {@link javafx.scene.paint.Color.NamedColors */
	final static HashMap <String, Color> COLOR_NAMES;
	/** List containing all color names in alphabetical order. */
	final static String[] COLOR_LIST;
	
	static
	{
		// Define color map.
		COLOR_NAMES = new HashMap <String, Color> (256);
		COLOR_NAMES.put ("ALICEBLUE", Color.ALICEBLUE);
		COLOR_NAMES.put ("ANTIQUEWHITE", Color.ANTIQUEWHITE);
		COLOR_NAMES.put ("AQUA", Color.AQUA);
		COLOR_NAMES.put ("AQUAMARINE", Color.AQUAMARINE);
		COLOR_NAMES.put ("AZURE", Color.AZURE);
		COLOR_NAMES.put ("BEIGE", Color.BEIGE);
		COLOR_NAMES.put ("BISQUE", Color.BISQUE);
		COLOR_NAMES.put ("BLACK", Color.BLACK);
		COLOR_NAMES.put ("BLANCHEDALMOND", Color.BLANCHEDALMOND);
		COLOR_NAMES.put ("BLUE", Color.BLUE);
		COLOR_NAMES.put ("BLUEVIOLET", Color.BLUEVIOLET);
		COLOR_NAMES.put ("BROWN", Color.BROWN);
		COLOR_NAMES.put ("BURLYWOOD", Color.BURLYWOOD);
		COLOR_NAMES.put ("CADETBLUE", Color.CADETBLUE);
		COLOR_NAMES.put ("CHARTREUSE", Color.CHARTREUSE);
		COLOR_NAMES.put ("CHOCOLATE", Color.CHOCOLATE);
		COLOR_NAMES.put ("CORAL", Color.CORAL);
		COLOR_NAMES.put ("CORNFLOWERBLUE", Color.CORNFLOWERBLUE);
		COLOR_NAMES.put ("CORNSILK", Color.CORNSILK);
		COLOR_NAMES.put ("CRIMSON", Color.CRIMSON);
		COLOR_NAMES.put ("CYAN", Color.CYAN);
		COLOR_NAMES.put ("DARKBLUE", Color.DARKBLUE);
		COLOR_NAMES.put ("DARKCYAN", Color.DARKCYAN);
		COLOR_NAMES.put ("DARKGOLDENROD", Color.DARKGOLDENROD);
		COLOR_NAMES.put ("DARKGRAY", Color.DARKGRAY);
		COLOR_NAMES.put ("DARKGREEN", Color.DARKGREEN);
		COLOR_NAMES.put ("DARKGREY", Color.DARKGREY);
		COLOR_NAMES.put ("DARKKHAKI", Color.DARKKHAKI);
		COLOR_NAMES.put ("DARKMAGENTA", Color.DARKMAGENTA);
		COLOR_NAMES.put ("DARKOLIVEGREEN", Color.DARKOLIVEGREEN);
		COLOR_NAMES.put ("DARKORANGE", Color.DARKORANGE);
		COLOR_NAMES.put ("DARKORCHID", Color.DARKORCHID);
		COLOR_NAMES.put ("DARKRED", Color.DARKRED);
		COLOR_NAMES.put ("DARKSALMON", Color.DARKSALMON);
		COLOR_NAMES.put ("DARKSEAGREEN", Color.DARKSEAGREEN);
		COLOR_NAMES.put ("DARKSLATEBLUE", Color.DARKSLATEBLUE);
		COLOR_NAMES.put ("DARKSLATEGRAY", Color.DARKSLATEGRAY);
		COLOR_NAMES.put ("DARKSLATEGREY", Color.DARKSLATEGREY);
		COLOR_NAMES.put ("DARKTURQUOISE", Color.DARKTURQUOISE);
		COLOR_NAMES.put ("DARKVIOLET", Color.DARKVIOLET);
		COLOR_NAMES.put ("DEEPPINK", Color.DEEPPINK);
		COLOR_NAMES.put ("DEEPSKYBLUE", Color.DEEPSKYBLUE);
		COLOR_NAMES.put ("DIMGRAY", Color.DIMGRAY);
		COLOR_NAMES.put ("DIMGREY", Color.DIMGREY);
		COLOR_NAMES.put ("DODGERBLUE", Color.DODGERBLUE);
		COLOR_NAMES.put ("FIREBRICK", Color.FIREBRICK);
		COLOR_NAMES.put ("FLORALWHITE", Color.FLORALWHITE);
		COLOR_NAMES.put ("FORESTGREEN", Color.FORESTGREEN);
		COLOR_NAMES.put ("FUCHSIA", Color.FUCHSIA);
		COLOR_NAMES.put ("GAINSBORO", Color.GAINSBORO);
		COLOR_NAMES.put ("GHOSTWHITE", Color.GHOSTWHITE);
		COLOR_NAMES.put ("GOLD", Color.GOLD);
		COLOR_NAMES.put ("GOLDENROD", Color.GOLDENROD);
		COLOR_NAMES.put ("GRAY", Color.GRAY);
		COLOR_NAMES.put ("GREEN", Color.GREEN);
		COLOR_NAMES.put ("GREENYELLOW", Color.GREENYELLOW);
		COLOR_NAMES.put ("GREY", Color.GREY);
		COLOR_NAMES.put ("HONEYDEW", Color.HONEYDEW);
		COLOR_NAMES.put ("HOTPINK", Color.HOTPINK);
		COLOR_NAMES.put ("INDIANRED", Color.INDIANRED);
		COLOR_NAMES.put ("INDIGO", Color.INDIGO);
		COLOR_NAMES.put ("IVORY", Color.IVORY);
		COLOR_NAMES.put ("KHAKI", Color.KHAKI);
		COLOR_NAMES.put ("LAVENDER", Color.LAVENDER);
		COLOR_NAMES.put ("LAVENDERBLUSH", Color.LAVENDERBLUSH);
		COLOR_NAMES.put ("LAWNGREEN", Color.LAWNGREEN);
		COLOR_NAMES.put ("LEMONCHIFFON", Color.LEMONCHIFFON);
		COLOR_NAMES.put ("LIGHTBLUE", Color.LIGHTBLUE);
		COLOR_NAMES.put ("LIGHTCORAL", Color.LIGHTCORAL);
		COLOR_NAMES.put ("LIGHTCYAN", Color.LIGHTCYAN);
		COLOR_NAMES.put ("LIGHTGOLDENRODYELLOW", Color.LIGHTGOLDENRODYELLOW);
		COLOR_NAMES.put ("LIGHTGRAY", Color.LIGHTGRAY);
		COLOR_NAMES.put ("LIGHTGREEN", Color.LIGHTGREEN);
		COLOR_NAMES.put ("LIGHTGREY", Color.LIGHTGREY);
		COLOR_NAMES.put ("LIGHTPINK", Color.LIGHTPINK);
		COLOR_NAMES.put ("LIGHTSALMON", Color.LIGHTSALMON);
		COLOR_NAMES.put ("LIGHTSEAGREEN", Color.LIGHTSEAGREEN);
		COLOR_NAMES.put ("LIGHTSKYBLUE", Color.LIGHTSKYBLUE);
		COLOR_NAMES.put ("LIGHTSLATEGRAY", Color.LIGHTSLATEGRAY);
		COLOR_NAMES.put ("LIGHTSLATEGREY", Color.LIGHTSLATEGREY);
		COLOR_NAMES.put ("LIGHTSTEELBLUE", Color.LIGHTSTEELBLUE);
		COLOR_NAMES.put ("LIGHTYELLOW", Color.LIGHTYELLOW);
		COLOR_NAMES.put ("LIME", Color.LIME);
		COLOR_NAMES.put ("LIMEGREEN", Color.LIMEGREEN);
		COLOR_NAMES.put ("LINEN", Color.LINEN);
		COLOR_NAMES.put ("MAGENTA", Color.MAGENTA);
		COLOR_NAMES.put ("MAROON", Color.MAROON);
		COLOR_NAMES.put ("MEDIUMAQUAMARINE", Color.MEDIUMAQUAMARINE);
		COLOR_NAMES.put ("MEDIUMBLUE", Color.MEDIUMBLUE);
		COLOR_NAMES.put ("MEDIUMORCHID", Color.MEDIUMORCHID);
		COLOR_NAMES.put ("MEDIUMPURPLE", Color.MEDIUMPURPLE);
		COLOR_NAMES.put ("MEDIUMSEAGREEN", Color.MEDIUMSEAGREEN);
		COLOR_NAMES.put ("MEDIUMSLATEBLUE", Color.MEDIUMSLATEBLUE);
		COLOR_NAMES.put ("MEDIUMSPRINGGREEN", Color.MEDIUMSPRINGGREEN);
		COLOR_NAMES.put ("MEDIUMTURQUOISE", Color.MEDIUMTURQUOISE);
		COLOR_NAMES.put ("MEDIUMVIOLETRED", Color.MEDIUMVIOLETRED);
		COLOR_NAMES.put ("MIDNIGHTBLUE", Color.MIDNIGHTBLUE);
		COLOR_NAMES.put ("MINTCREAM", Color.MINTCREAM);
		COLOR_NAMES.put ("MISTYROSE", Color.MISTYROSE);
		COLOR_NAMES.put ("MOCCASIN", Color.MOCCASIN);
		COLOR_NAMES.put ("NAVAJOWHITE", Color.NAVAJOWHITE);
		COLOR_NAMES.put ("NAVY", Color.NAVY);
		COLOR_NAMES.put ("OLDLACE", Color.OLDLACE);
		COLOR_NAMES.put ("OLIVE", Color.OLIVE);
		COLOR_NAMES.put ("OLIVEDRAB", Color.OLIVEDRAB);
		COLOR_NAMES.put ("ORANGE", Color.ORANGE);
		COLOR_NAMES.put ("ORANGERED", Color.ORANGERED);
		COLOR_NAMES.put ("ORCHID", Color.ORCHID);
		COLOR_NAMES.put ("PALEGOLDENROD", Color.PALEGOLDENROD);
		COLOR_NAMES.put ("PALEGREEN", Color.PALEGREEN);
		COLOR_NAMES.put ("PALETURQUOISE", Color.PALETURQUOISE);
		COLOR_NAMES.put ("PALEVIOLETRED", Color.PALEVIOLETRED);
		COLOR_NAMES.put ("PAPAYAWHIP", Color.PAPAYAWHIP);
		COLOR_NAMES.put ("PEACHPUFF", Color.PEACHPUFF);
		COLOR_NAMES.put ("PERU", Color.PERU);
		COLOR_NAMES.put ("PINK", Color.PINK);
		COLOR_NAMES.put ("PLUM", Color.PLUM);
		COLOR_NAMES.put ("POWDERBLUE", Color.POWDERBLUE);
		COLOR_NAMES.put ("PURPLE", Color.PURPLE);
		COLOR_NAMES.put ("RED", Color.RED);
		COLOR_NAMES.put ("ROSYBROWN", Color.ROSYBROWN);
		COLOR_NAMES.put ("ROYALBLUE", Color.ROYALBLUE);
		COLOR_NAMES.put ("SADDLEBROWN", Color.SADDLEBROWN);
		COLOR_NAMES.put ("SALMON", Color.SALMON);
		COLOR_NAMES.put ("SANDYBROWN", Color.SANDYBROWN);
		COLOR_NAMES.put ("SEAGREEN", Color.SEAGREEN);
		COLOR_NAMES.put ("SEASHELL", Color.SEASHELL);
		COLOR_NAMES.put ("SIENNA", Color.SIENNA);
		COLOR_NAMES.put ("SILVER", Color.SILVER);
		COLOR_NAMES.put ("SKYBLUE", Color.SKYBLUE);
		COLOR_NAMES.put ("SLATEBLUE", Color.SLATEBLUE);
		COLOR_NAMES.put ("SLATEGRAY", Color.SLATEGRAY);
		COLOR_NAMES.put ("SLATEGREY", Color.SLATEGREY);
		COLOR_NAMES.put ("SNOW", Color.SNOW);
		COLOR_NAMES.put ("SPRINGGREEN", Color.SPRINGGREEN);
		COLOR_NAMES.put ("STEELBLUE", Color.STEELBLUE);
		COLOR_NAMES.put ("TAN", Color.TAN);
		COLOR_NAMES.put ("TEAL", Color.TEAL);
		COLOR_NAMES.put ("THISTLE", Color.THISTLE);
		COLOR_NAMES.put ("TOMATO", Color.TOMATO);
		COLOR_NAMES.put ("TRANSPARENT", Color.TRANSPARENT);
		COLOR_NAMES.put ("TURQUOISE", Color.TURQUOISE);
		COLOR_NAMES.put ("VIOLET", Color.VIOLET);
		COLOR_NAMES.put ("WHEAT", Color.WHEAT);
		COLOR_NAMES.put ("WHITE", Color.WHITE);
		COLOR_NAMES.put ("WHITESMOKE", Color.WHITESMOKE);
		COLOR_NAMES.put ("YELLOW", Color.YELLOW);
		COLOR_NAMES.put ("YELLOWGREEN", Color.YELLOWGREEN);
		
		COLOR_LIST = new String[]
		{
			"AliceBlue", "AntiqueWhite", "Aqua", "AquaMarine", "Azure", "Beige", "Bisque", "Black", "BlanchedAlmond",
			"Blue", "BlueViolet", "Brown", "BurlyWood", "CadetBlue", "Chartreuse", "Chocolate", "Coral",
			"CornflowerBlue", "CornSilk", "Crimson", "Cyan", "DarkBlue", "DarkCyan", "DarkGoldenRod", "DarkGray",
			"DarkGreen", "DarkGrey", "DarkKhaki", "DarkMagenta", "DarkOliveGreen", "DarkOrange", "DarkOrchid",
			"DarkRed", "DarkSalmon", "DarkSeaGreen", "DarkSlateBlue", "DarkSlateGray", "DarkSlateGrey", "DarkTurquoise",
			"DarkViolet", "DeepPink", "DeepSkyBlue", "DimGray", "DimGrey", "DodgerBlue", "FireBrick", "FloralWhite",
			"ForestGreen", "Fuchsia", "Gainsboro", "GhostWhite", "Gold", "GoldenRod", "Gray", "Green", "GreenYellow",
			"Grey", "Honeydew", "HotPink", "IndianRed", "Indigo", "Ivory", "Khaki", "Lavender", "LavenderBlush",
			"LawnGreen", "LemonChiffon", "LightBlue", "LightCoral", "LightCyan", "LightGoldenRodYellow", "LightGray",
			"LightGreen", "LightGrey", "LightPink", "LightSalmon", "LightSeaGreen", "LightSkyBlue", "LightSlateGray",
			"LightSlateGrey", "LightSteelBlue", "LightYellow", "Lime", "LimeGreen", "Linen", "Magenta", "Maroon",
			"MediumAquaMarine", "MediumBlue", "MediumOrchid", "MediumPurple", "MediumSeaGreen", "MediumSlateBlue",
			"MediumSpringGreen", "MediumTurquoise", "MediumVioletRed", "MidnightBlue", "MintCream", "MistyRose",
			"Moccasin", "NavajoWhite", "Navy", "OldLace", "Olive", "OliveDrab", "Orange", "OrangeRed", "Orchid",
			"PaleGoldenRod", "PaleGreen", "PaleTurquoise", "PaleVioletRed", "PapayaWhip", "PeachPuff", "Peru", "Pink",
			"Plum", "PowderBlue", "Purple", "Red", "RosyBrown", "RoyalBlue", "SaddleBrown", "Salmon", "SandyBrown",
			"SeaGreen", "SeaShell", "Sienna", "Silver", "SkyBlue", "SlateBlue", "SlateGray", "SlateGrey", "Snow",
			"SpringGreen", "SteelBlue", "Tan", "Teal", "Thistle", "Tomato", "Transparent", "Turquoise", "Violet",
			"Wheat", "White", "WhiteSmoke", "Yellow", "YellowGreen",
		};
	}
	
	/**
	 * Convert from string to color.
	 * 
	 * @param name
	 *        name of color.
	 * @return corresponding color.
	 */
	public static Color convertColor (String name)
	{
		name = name.toUpperCase ();
		if (name.length () == 1)
		{
			switch (name.charAt (0))
			{
				case 'B':
					return Color.BLUE;
				case 'C':
					return Color.CYAN;
				case 'G':
					return Color.GREEN;
				case 'K':
					return Color.BLACK;
				case 'M':
					return Color.MAGENTA;
				case 'O':
					return Color.ORANGE;
				case 'P':
					return Color.PURPLE;
				case 'R':
					return Color.RED;
				case 'W':
					return Color.WHITE;
				case 'Y':
					return Color.YELLOW;
				default:
					return null;
			}
		}
		else
		{
			Color color = COLOR_NAMES.get (name);
			if (color != null)
			{
				return color;
			}
			else
			{
				return Color.web (name);
			}
		}
	}
	
	/**
	 * Convert from color to string.
	 * 
	 * @param color
	 *        color object.
	 * @return corresponding name.
	 */
	public static String convertColor (Color color)
	{
		for (Map.Entry <String, Color> entry : COLOR_NAMES.entrySet ())
		{
			if (color.equals (entry.getValue ()))
			{
				return entry.getKey ();
			}
		}
		return null;
	}
	
	
	/**
	 * Reads color from text file, and sets color directly to manager.
	 * 
	 * @param file
	 *        text file to read.
	 * @param manager
	 *        CubeManager to update colors.
	 */
	public static void readFile (File file, CubeManager manager)
	{
		try
		{
			CubeMap <Color> map = new CubeMap <> ();
			Scanner scan = new Scanner (file);
			int count = 0;
			boolean flag;
			while (scan.hasNextLine ())
			{
				count++;
				String line = scan.nextLine ().trim ();
				// skip empty line and comments.
				if (line.length () == 0 || line.startsWith ("#") || line.startsWith ("//"))
				{
					continue;
				}
				// split into CubeAxis, and Color, and Color origin.
				String[] pairs = line.split (":");
				flag = pairs.length == 2 || pairs.length == 3;
				for (int i = 0; flag && i < pairs.length; i++)
				{
					pairs[i] = pairs[i].trim ();
					flag = pairs[i].length () > 0;
				}
				if (!flag)
				{
					scan.close ();
					throw new IOException ("Invalid colon on line " + count);
				}
				// split into Face and Position
				pairs[0] = pairs[0].replaceAll ("\\+", "+ ");
				pairs[0] = pairs[0].replaceAll ("-", "- ");
				String[] directions = pairs[0].split ("[, ]+");
				flag = directions.length > 0;
				for (int i = 0; flag && i < directions.length; i++)
				{
					directions[i] = directions[i].trim ();
					if (i == 0)
					{
						flag = directions[i].length () > 0;
					}
				}
				// convert face and position.
				CubeAxis dir = null;
				CubeAxis[] pos = null;
				if (flag)
				{
					dir = CubeAxis.getCubeAxis (directions[0]);
					flag = dir != null;
					pos = new CubeAxis[directions.length - 1];
					for (int i = 0; flag && i < pos.length; i++)
					{
						pos[i] = CubeAxis.getCubeAxis (directions[i + 1]);
						flag = pos[i] != null;
					}
				}
				if (!flag)
				{
					scan.close ();
					throw new IOException ("Invalid direction on line " + count);
				}
				// split into Colors
				String[] colors = pairs[1].split ("[, ]+");
				flag = colors.length == 1 || colors.length == 3 || colors.length == 4;
				for (int i = 0; flag && i < colors.length; i++)
				{
					colors[i] = colors[i].trim ();
					flag = colors[i].length () > 0;
				}
				Color color = null;
				if (flag)
				{
					if (colors.length == 1)
					{
						color = convertColor (colors[0]);
					}
					else
					{
						double[] values = new double[4];
						for (int i = 0; i < values.length; i++)
						{
							values[i] = i < colors.length ? Double.parseDouble (colors[i]) : 1.0;
						}
						color = new Color (values[0], values[1], values[2], values[3]);
					}
					flag = color != null;
				}
				if (!flag)
				{
					scan.close ();
					throw new IOException ("Invalid color on line " + count);
				}
				/*
				 * CubeAxis dir1 = null;
				 * CubeAxis[] pos1 = null;
				 * // get originating layer.
				 * if (pairs.length == 2)
				 * {
				 * dir1 = dir;
				 * pos1 = pos;
				 * }
				 * else
				 * {
				 * // split into origin Face and Position
				 * pairs[2] = pairs[2].replaceAll ("\\+", "+ ");
				 * pairs[2] = pairs[2].replaceAll ("-", "- ");
				 * String[] directions1 = pairs[2].split ("[, ]+");
				 * flag = directions1.length > 0;
				 * for (int i = 0; flag && i < directions1.length; i++)
				 * {
				 * directions1[i] = directions1[i].trim ();
				 * if (i == 0)
				 * {
				 * flag = directions1[i].length () > 0;
				 * }
				 * }
				 * // convert face and position.
				 * if (flag)
				 * {
				 * dir1 = CubeAxis.getCubeAxis (directions1[0]);
				 * flag = dir1 != null;
				 * pos1 = new CubeAxis[directions1.length - 1];
				 * for (int i = 0; flag && i < pos1.length; i++)
				 * {
				 * pos1[i] = CubeAxis.getCubeAxis (directions1[i + 1]);
				 * flag = pos1[i] != null;
				 * }
				 * }
				 * if (!flag)
				 * {
				 * scan.close ();
				 * throw new IOException ("Invalid origin on line " + count);
				 * }
				 * }
				 */
				// Build Map.
				if (!map.containsKey (dir))
				{
					map.put (dir, new HashMap <> ());
				}
				map.get (dir).put (CubeAxis.getValue (pos), color);
			}
			scan.close ();
			// update colors.
			manager.setColors (map);
		}
		catch (IOException e)
		{
			System.err.println (e);
		}
	}
	
	/**
	 * Writes color from text file.
	 * 
	 * @param file
	 *        text file to write.
	 * @param map
	 *        map of colors.
	 * @return {@code true} if successful. otherwise, {@code false}.
	 */
	public static boolean writeFile (File file, CubeManager manager)
	{
		try
		{
			CubeMap <Color> map = manager.getColors ();
			PrintWriter writer = new PrintWriter (file);
			writer.println ("// Generated cube file.");
			for (CubeAxis dir : map.keySet ())
			{
				HashMap <Integer, Color> face = map.get (dir);
				for (Integer key : face.keySet ())
				{
					CubeAxis[] pos = CubeAxis.getCubeAxis (key);
					Color color = face.get (key);
					String name = convertColor (color);
					writer.print (dir.toString () + ", ");
					for (int i = 0; i < pos.length; i++)
					{
						writer.print (pos[i].toString () + " ");
					}
					writer.println (": " + name);
					/*
					 * CubeAxis dir1 = color.getValue ().face;
					 * CubeAxis[] pos1 = CubeAxis.getCubeAxis (color.getValue ().position);
					 * if (dir != dir1 || key != color.getValue ().position)
					 * {
					 * writer.print (" : " + dir1.toString () + ", ");
					 * for (int i = 0; i < pos.length; i++)
					 * {
					 * writer.print (pos1[i].toString () + " ");
					 * }
					 * }
					 * writer.println ();
					 */
				}
			}
			writer.close ();
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Update colors of given map based on the information.
	 * 
	 * @param map
	 *        map to update.
	 * @param pair
	 *        rotation information.
	 * @return new CubeMap containing updated information.
	 */
	public static <T> CubeMap <T> updateMap (CubeMap <T> map, CubeDuo pair)
	{
		CubeAxis dir = pair.first;
		IntDuo other = dir.otherAxis ();
		CubeMap <T> copy = map.clone ();
		// update face.
		HashMap <Integer, T> face = map.get (dir), copyFace = copy.get (dir);
		for (int key : face.keySet ())
		{
			T item = face.get (key);
			int newkey = turnAxis (pair, other, key);
			copyFace.put (newkey, item);
		}
		// update shells.
		CubeAxis[] others = dir.others ();
		for (CubeAxis axis : others)
		{
			face = map.get (axis);
			CubeAxis newaxis = turnAxis (pair, other, axis);
			copyFace = copy.get (newaxis);
			for (int key : face.keySet ())
			{
				if ((key & dir.code) != 0)
				{
					T item = face.get (key);
					int newkey = turnAxis (pair, other, key & ~dir.code | axis.code) & ~newaxis.code | dir.code;
					copyFace.put (newkey, item);
				}
			}
		}
		return copy;
	}
	
	public static CubeMap <CubeTrio> updateTrioMap (CubeMap <CubeTrio> map, CubeDuo pair)
	{
		CubeDuo otherPair = new CubeDuo (pair.first, !pair.second);
		CubeAxis dir = pair.first;
		IntDuo other = dir.otherAxis ();
		CubeMap <CubeTrio> copy = map.clone ();
		// update face.
		HashMap <Integer, CubeTrio> face = map.get (dir), copyFace = copy.get (dir);
		for (int key : face.keySet ())
		{
			int nextkey = CubeUtils.turnAxis (pair, other, key), prevkey = CubeUtils.turnAxis (otherPair, other, key);
			CubeTrio nextitem = face.get (nextkey), previtem = face.get (prevkey);
			copyFace.put (key, new CubeTrio (previtem.first, previtem.second, nextitem.third));
		}
		// update shells.
		CubeAxis[] others = dir.others ();
		for (CubeAxis axis : others)
		{
			face = map.get (axis);
			CubeAxis nextaxis = CubeUtils.turnAxis (pair, other, axis),
				prevaxis = CubeUtils.turnAxis (otherPair, other, axis);
			HashMap <Integer, CubeTrio> nextface = map.get (nextaxis), prevface = map.get (prevaxis);
			copyFace = copy.get (axis);
			for (int key : face.keySet ())
			{
				if ((key & dir.code) != 0)
				{
					int nextkey =
						CubeUtils.turnAxis (pair, other, key & ~dir.code | axis.code) & ~nextaxis.code | dir.code,
						prevkey = CubeUtils.turnAxis (otherPair, other, key & ~dir.code | axis.code) & ~prevaxis.code |
							dir.code;
					CubeTrio nextitem = nextface.get (nextkey), previtem = prevface.get (prevkey);
					copyFace.put (key, new CubeTrio (previtem.first, previtem.second, nextitem.third));
				}
			}
		}
		return copy;
	}
	
	/**
	 * Determine new key value of given rotation.
	 * 
	 * @param pair
	 *        Pair of Direction of face, and rotation mode.
	 * @param other
	 *        Pair of other two axis.
	 * @param key
	 *        HashKey of position.
	 * @return new HashKey of position.
	 */
	public static int turnAxis (CubeDuo pair, IntDuo other, int key)
	{
		CubeAxis dir = pair.first;
		boolean mode = pair.second;
		CubeAxis[] keydir = CubeAxis.getCubeAxis (key);
		for (int i = 0; i < keydir.length; i++)
		{
			if (keydir[i].axis == other.first)
			{
				keydir[i] = CubeAxis.getCubeAxis (other.second, dir.sign * keydir[i].sign * (mode ? 1 : -1));
			}
			else if (keydir[i].axis == other.second)
			{
				keydir[i] = CubeAxis.getCubeAxis (other.first, dir.sign * keydir[i].sign * (mode ? -1 : 1));
			}
			else
			{
				assert false; // this should be unreachable.
			}
		}
		return CubeAxis.getValue (keydir);
	}
	
	/**
	 * Determine new axis value of given rotation.
	 * 
	 * @param pair
	 *        Pair of Direction of face, and rotation mode.
	 * @param other
	 *        Pair of other two axis.
	 * @param axis
	 *        Query axis.
	 * @return new axis rotated from query axis.
	 */
	public static CubeAxis turnAxis (CubeDuo pair, IntDuo other, CubeAxis axis)
	{
		CubeAxis dir = pair.first;
		boolean mode = pair.second;
		if (axis.axis == other.first)
		{
			axis = CubeAxis.getCubeAxis (other.second, dir.sign * axis.sign * (mode ? 1 : -1));
		}
		else if (axis.axis == other.second)
		{
			axis = CubeAxis.getCubeAxis (other.first, dir.sign * axis.sign * (mode ? -1 : 1));
		}
		else
		{
			assert false; // this should be unreachable.
		}
		return axis;
	}
	
	/**
	 * Generate report on relative position of each color layers.
	 * @param manager target cube manager.
	 * @return
	 */
	public static LinkedList<LinkedList<CubeLayer>> getLinks (CubeManager manager)
	{
		LinkedList<LinkedList<CubeLayer>> links = new LinkedList<> ();
		HashSet<CubeLayer> set = new HashSet<> ();
		for (CubeLayer layer : manager.shells)
		{
			if (!set.contains (layer))
			{
				set.add (layer);
				if (layer != layer.getDestination ())
				{
					LinkedList<CubeLayer> chain = new LinkedList<> ();
					CubeLayer current = layer;
					do
					{
						set.add (current);
						chain.add (current);
						current = current.getDestination ();
					}
					while (current != layer);
					links.add (chain);
				}
			}
		}
		return links;
	}
}
