package programs.rubikscube;

// import static programs.rubikscube.RubiksCube.*;
import java.util.*;


/**
 * Dedicated two-level nested Map class containing top EnumMap level and bottom HashMap level.
 * 
 * @author hasol
 * @param <T>
 *        Type of Value in bottom HashMap level
 */
public class CubeMap <T> extends EnumMap <CubeAxis, HashMap <Integer, T>> implements Cloneable
{
	
	/** Serial UUID for {@link java.io.Serializable} interface. */
	private static final long serialVersionUID = -676706012299236918L;
	
	/**
	 * Initialize this CubeMap on top EnumMap level only.
	 * 
	 * @see EnumMap#EnumMap(Class)
	 */
	public CubeMap ()
	{
		super (CubeAxis.class);
	}
	
	/**
	 * Initialize this CubeMap on top EnumMap level and bottom HashMap levels with given keys.
	 * 
	 * @param keys
	 *        Keys to initialize new HashMaps
	 * @see EnumMap#EnumMap(Class)
	 * @see HashMap#HashMap()
	 */
	public CubeMap (CubeAxis... keys)
	{
		super (CubeAxis.class);
		for (CubeAxis key : keys)
		{
			this.put (key, new HashMap <> ());
		}
	}
	
	/**
	 * Initialize this CubeMap and populate with given map.
	 * 
	 * @param map
	 *        existing map to clone.
	 * @see EnumMap#EnumMap(Map)
	 */
	public CubeMap (Map <CubeAxis, ? extends HashMap <Integer, T>> map)
	{
		super (map);
	}
	
	/**
	 * Perform two-level deep copy of this CubeMap.
	 * 
	 * @return new CubeMap containing new HashMaps with same key and value
	 */
	@Override
	public CubeMap <T> clone ()
	{
		CubeMap <T> copy = new CubeMap <> ();
		for (CubeAxis dir : CubeAxis.values ())
		{
			if (this.get (dir) != null)
			{
				copy.put (dir, new HashMap <> (this.get (dir)));
			}
		}
		return copy;
	}
}
