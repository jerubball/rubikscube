package programs.rubikscube;

import java.util.*;
import java.util.function.*;


/**
 * Framework for AI solution.
 * This interface consists of four parts:
 * {@link #apply(T)} for main search problem.
 * {@link #apply(T, U) for step function.
 * {@link #applyAsDouble(T)} for heuristic function.
 * {@link #test(T)} for goal evaluation function.
 * 
 * @author hasol
 * @param <T>
 *        Type of Search Problem Model.
 * @param <U>
 *        Type of resulting actions.
 */
public interface Solution <T, U> extends Function <T, List <U>>, BiFunction <T, U, T>, ToDoubleFunction <T>,
	ToDoubleBiFunction <T, List <U>>, Predicate <T>
{
	/**
	 * Compute AI search problem.
	 */
	@Override
	public List <U> apply (T state);
	
	/**
	 * Generate next state according to the step.
	 */
	@Override
	public T apply (T state, U step);
	
	/**
	 * Perform heuristic function on state only.
	 */
	@Override
	public double applyAsDouble (T state);
	
	/**
	 * Perform heuristic function on state and steps.
	 */
	@Override
	public double applyAsDouble (T state, List <U> steps);
	
	/**
	 * Perform end state evaluation.
	 */
	@Override
	public boolean test (T state);
}
