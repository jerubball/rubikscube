package programs.rubikscube;

// import static programs.rubikscube.RubiksCube.*;
import java.util.*;
// import javafx.scene.paint.*;


/**
 * Instance of AI solution designed to solve Rubik's cube.
 * 
 * @author hasol
 */
public class CubeSolution implements Solution <CubeMap <CubeTrio>, CubeDuo>
{
	/**
	 * Wrapper class to contain result;
	 * 
	 * @author hasol
	 */
	static class Container extends Tuple implements Cloneable, Comparable <Container>
	{
		/** Comparator for natural ordering sorting. */
		final static Comparator <Container> comparator;
		
		static
		{
			comparator = new Comparator <Container> ()
			{
				@Override
				public int compare (Container o1, Container o2)
				{
					return o1.compareTo (o2);
				}
			};
		}
		
		private final CubeMap <CubeTrio> state;
		private final LinkedList <CubeDuo> list;
		private final double priority;
		
		Container (CubeMap <CubeTrio> state, LinkedList <CubeDuo> list, double priority)
		{
			this.state = state;
			this.list = list;
			this.priority = priority;
		}
		
		@Override
		public Container clone ()
		{
			return new Container (state, list, priority);
		}
		
		@Override
		public int compareTo (Container other)
		{
			if (this.priority > other.priority)
			{
				return 1;
			}
			else if (this.priority < other.priority)
			{
				return -1;
			}
			else
			{
				return 0;
			}
		}
		
		@Override
		public String toString ()
		{
			return OPEN + state.size () + COMMA + list.size () + COMMA + priority + CLOSE;
		}
		
		public static Comparator <Container> getComparator ()
		{
			return comparator;
		}
	}
	
	final static Boolean[] allBoolean =
	{
		Boolean.TRUE, Boolean.FALSE
	};
	final static CubeAxis[] allCubeAxis =
	{
		CubeAxis.X_POS, CubeAxis.X_NEG, CubeAxis.Y_POS, CubeAxis.Y_NEG, CubeAxis.Z_POS, CubeAxis.Z_NEG
	};
	
	static double factor = 10;
	
	@Override
	public List <CubeDuo> apply (CubeMap <CubeTrio> map)
	{
		PriorityQueue <Container> queue = new PriorityQueue <> (Container.getComparator ());
		queue.add (new Container (map, new LinkedList <> (), applyAsDouble (map)));
		// perform A* search.
		while (!queue.isEmpty ())
		{
			Container item = queue.remove ();
			// check for end state.
			if (test (item.state))
			{
				return item.list;
			}
			// schedule for next moves.
			for (CubeAxis axis : allCubeAxis)
			{
				for (Boolean bool : allBoolean)
				{
					CubeDuo pair = new CubeDuo (axis, bool);
					if (test (pair, item.list))
					{
						CubeMap <CubeTrio> next = apply (item.state, pair);
						LinkedList <CubeDuo> list = new LinkedList <> (item.list);
						list.add (pair);
						queue.add (new Container (next, list, applyAsDouble (next, list)));
					}
				}
			}
			System.out.println ("SIZE: " + queue.size ());
		}
		return null;
	}
	
	@Override
	public CubeMap <CubeTrio> apply (CubeMap <CubeTrio> state, CubeDuo step)
	{
		return CubeUtils.updateTrioMap (state, step);
	}
	
	@Override
	public double applyAsDouble (CubeMap <CubeTrio> state)
	{
		long result = 0;
		for (CubeAxis axis : state.keySet ())
		{
			HashMap <Integer, CubeTrio> face = state.get (axis);
			for (Integer key : face.keySet ())
			{
				CubeTrio value = face.get (key);
				CubeLayer layer = value.second;
				if (layer.face != axis)
				{
					result += 2;
					if (layer.face.opposite () == axis)
					{
						result += 1;
					}
				}
				else if (layer.position != key)
				{
					result += 1;
				}
			}
		}
		return new Long (result).doubleValue ();
	}
	
	@Override
	public double applyAsDouble (CubeMap <CubeTrio> state, List <CubeDuo> steps)
	{
		double result = applyAsDouble (state);
		result += steps.size () * factor;
		return result;
	}
	
	@Override
	public boolean test (CubeMap <CubeTrio> state)
	{
		for (CubeAxis axis : state.keySet ())
		{
			HashMap <Integer, CubeTrio> face = state.get (axis);
			for (Integer key : face.keySet ())
			{
				CubeTrio value = face.get (key);
				CubeLayer layer = value.second;
				if (layer.face != axis || layer.position != key) // || layer.color.equals (value.getKey ()))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Evaluate if next step is allowed as next step.
	 * 
	 * @param step
	 *        the first function argument
	 * @param steps
	 *        the second function argument
	 * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
	 */
	public boolean test (CubeDuo step, LinkedList <CubeDuo> steps)
	{
		int length = steps.size ();
		CubeAxis axis = step.first, oppositeAxis = step.first.opposite ();
		boolean bool = step.second;
		if (length == 0)
		{
			return true;
		}
		else
		{
			boolean firstMove = true;
			for (int i = length - 1; i >= 0; i--)
			{
				CubeDuo duo = steps.get (i);
				if (duo.first != axis && duo.first != oppositeAxis)
				{ // once side is moved, result will be different.
					return true;
				}
				else if (duo.first == axis)
				{
					if (firstMove)
					{ // consecutive same move is allowed.
						firstMove = false;
						if (bool != duo.second)
						{ // reverse operation is not allowed.
							return false;
						}
					}
					else
					{ // same move three times is not allowed.
						return false;
					}
				}
			}
			return true;
		}
	}
}

class SimpleSolution extends CubeSolution
{
	@Override
	public double applyAsDouble (CubeMap <CubeTrio> state)
	{
		return 0;
	}
}

class AlternateSolution extends CubeSolution
{
	static int minimum = 0, maximum = 10;
	
	@Override
	public List <CubeDuo> apply (CubeMap <CubeTrio> map)
	{
		for (int level = minimum; level < maximum; level++)
		{
			System.out.println (level);
			List <CubeDuo> list = apply (map, level);
			System.out.println ();
			if (list != null && list.size () > 0)
			{
				return list;
			}
		}
		return null;
	}
	
	public List <CubeDuo> apply (CubeMap <CubeTrio> map, int level)
	{
		System.out.print (level + " ");
		if (test (map))
		{
			return new LinkedList <> ();
		}
		else if (level < 0)
		{
			return null;
		}
		else
		{
			// schedule for next moves.
			for (CubeAxis axis : allCubeAxis)
			{
				for (Boolean bool : allBoolean)
				{
					CubeDuo pair = new CubeDuo (axis, bool);
					CubeMap <CubeTrio> next = apply (map, pair);
					List <CubeDuo> list = apply (next, level - 1);
					if (list != null)
					{
						list.add (0, pair);
						return list;
					}
				}
			}
			return null;
		}
	}
}

class StepSolution extends CubeSolution
{
	static CubeAxis base_step = CubeAxis.Y_NEG;
	
	static class Step1Solution extends CubeSolution
	{
		@Override
		public double applyAsDouble (CubeMap <CubeTrio> state)
		{
			long result = 0;
			
			HashMap <Integer, CubeTrio> face = state.get (base_step);
			for (Integer key : face.keySet ())
			{
				// TODO revise
				CubeTrio value = face.get (key);
				CubeLayer source = value.second;
				// CubeLayer destination = value.third;
				if (source.face != base_step)
				{
					result += 15;
				}
				else if (source.position != key)
				{
					if (Integer.bitCount (source.position) > 1)
					{
						result += 5;
					}
					else
					{
						result += 10;
					}
				}
			}
			
			System.out.println (result);
			return new Long (result).doubleValue ();
		}
		
		@Override
		public boolean test (CubeMap <CubeTrio> state)
		{
			HashMap <Integer, CubeTrio> face = state.get (base_step);
			for (Integer key : face.keySet ())
			{
				CubeTrio value = face.get (key);
				CubeLayer layer = value.second;
				if (layer.face != base_step || layer.position != key) // || layer.color.equals (value.getKey ()))
				{
					return false;
				}
			}
			return true;
		}
	}
	
}
