package programs.rubikscube;

// import static programs.rubikscube.RubiksCube.*;
import javafx.scene.paint.*;


/**
 * Parent class for for container classes.
 * 
 * @author hasol
 */
public abstract class Tuple implements Cloneable
{
	/** Formatting string for toString methods. */
	public final static String OPEN = "<", CLOSE = ">", COMMA = ",";
	
	@Override
	public abstract Tuple clone ();
	
	@Override
	public abstract String toString ();
}

/**
 * Immutable container for two items.
 * 
 * @author hasol
 * @param <T>
 *        Type of first item.
 * @param <U>
 *        Type of second item.
 */
class Duo <T, U> extends Tuple
{
	final T first;
	final U second;
	
	public Duo (T first, U second)
	{
		this.first = first;
		this.second = second;
	}
	
	@Override
	public Duo <T, U> clone ()
	{
		return new Duo <> (first, second);
	}
	
	@Override
	public boolean equals (Object obj)
	{
		if (obj != null && obj instanceof Duo <?, ?>)
		{
			Duo <?, ?> item = (Duo <?, ?>) obj;
			return (first == null ? item.first == null : first.equals (item.first)) &&
				(second == null ? item.second == null : second.equals (item.second));
		}
		return false;
	}
	
	@Override
	public String toString ()
	{
		return OPEN + first.toString () + COMMA + second.toString () + CLOSE;
	}
}

/**
 * Immutable container for frequently used type pairs.
 * 
 * @author hasol
 */
class CubeDuo extends Duo <CubeAxis, Boolean>
{
	public CubeDuo (CubeAxis first, Boolean second)
	{
		super (first, second);
	}
}

/**
 * Immutable container for two integers.
 * 
 * @author hasol
 */
class IntDuo extends Duo <Integer, Integer>
{
	public IntDuo (Integer first, Integer second)
	{
		super (first, second);
	}
}

/**
 * Immutable container for three items.
 * 
 * @author hasol
 * @param <T>
 *        Type of first item.
 * @param <U>
 *        Type of second item.
 * @param <V>
 *        Type of third item.
 */
class Trio <T, U, V> extends Tuple
{
	final T first;
	final U second;
	final V third;
	
	public Trio (T first, U second, V third)
	{
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	@Override
	public Trio <T, U, V> clone ()
	{
		return new Trio <> (first, second, third);
	}
	
	@Override
	public boolean equals (Object obj)
	{
		if (obj != null && obj instanceof Trio <?, ?, ?>)
		{
			Trio <?, ?, ?> item = (Trio <?, ?, ?>) obj;
			return (first == null ? item.first == null : first.equals (item.first)) &&
				(second == null ? item.second == null : second.equals (item.second)) &&
				(third == null ? item.third == null : third.equals (item.third));
		}
		return false;
	}
	
	@Override
	public String toString ()
	{
		return OPEN + first.toString () + COMMA + second.toString () + COMMA + third.toString () + CLOSE;
	}
}

class CubeTrio extends Trio <Color, CubeLayer, CubeLayer>
{
	public CubeTrio (Color first, CubeLayer second, CubeLayer third)
	{
		super (first, second, third);
	}
}

/**
 * Immutable container for three integers.
 * 
 * @author hasol
 */
class IntTrio extends Trio <Integer, Integer, Integer>
{
	public IntTrio (Integer first, Integer second, Integer third)
	{
		super (first, second, third);
	}
}

