package programs.rubikscube;

import static programs.rubikscube.RubiksCube.*;
import javafx.beans.property.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;


/**
 * Enhanced 3D Box with rotational pivot point.
 * Instance of CubeCore class are used as core units.
 * 
 * @author hasol
 */
public class CubeCore extends Box
{
	/** Default core cube color. */
	public final static Color DEFAULT = Color.DARKGRAY;
	/** Multiplier for layer translation. */
	public final static double layer_factor = 1.5;
	
	/** x, y, z offset of rotational pivot. */
	private double[] pivot;
	/** x, y, z axis of rotation. */
	Rotate[] axis;
	/** Bitwise OR value of Directions. */
	protected int direction;
	/** Cube's surface render object. */
	protected PhongMaterial material;
	
	/**
	 * Create new core unit.
	 * 
	 * @param length
	 *        size of this box.
	 * @param direction
	 *        directional offset.
	 */
	public CubeCore (double length, int direction)
	{
		super (length, length, length);
		this.init (length, direction);
		this.init (DEFAULT);
	}
	
	/**
	 * Create new core unit.
	 * 
	 * @param length
	 *        size of this box.
	 * @param direction
	 *        directional offset.
	 */
	public CubeCore (double length, CubeAxis... direction)
	{
		this (length, CubeAxis.getValue (direction));
	}
	
	/**
	 * Create new surface unit.
	 * All child objects using this constructor must initialize direction,
	 * and must invoke {@link #init(double[])} to initialize pivot and axis.
	 * 
	 * @param width
	 *        x-length.
	 * @param height
	 *        y-length.
	 * @param depth
	 *        z-length.
	 * @param color
	 *        surface color.
	 */
	protected CubeCore (double width, double height, double depth, Color color)
	{
		super (width, height, depth);
		this.init (color);
	}
	
	/**
	 * Create new surface unit.
	 * All child objects using this constructor must initialize direction,
	 * and must invoke {@link #init(double[])} to initialize pivot and axis.
	 * 
	 * @param lengths
	 *        array of width, height, and depth.
	 * @param color
	 *        surface color.
	 */
	protected CubeCore (double[] lengths, Color color)
	{
		this (lengths[X], lengths[Y], lengths[Z], color);
	}
	
	/**
	 * Initialize as core unit.
	 * 
	 * @param length
	 *        size of this box.
	 * @param direction
	 *        directional offset.
	 */
	private void init (double length, int direction)
	{
		this.direction = direction;
		double[] pivot = new double[DIM];
		int[] signum = CubeAxis.sigNum (direction);
		for (int i = 0; i < DIM; i++)
		{
			pivot[i] = -signum[i] * length;
		}
		this.init (pivot);
	}
	
	/**
	 * Initialize pivot and axis.
	 * 
	 * @param pivot
	 *        array of x, y, and z pivot offset.
	 */
	protected void init (double[] pivot)
	{
		this.pivot = pivot;
		this.setTranslateX (-pivot[X]);
		this.setTranslateY (-pivot[Y]);
		this.setTranslateZ (-pivot[Z]);
		this.axis = new Rotate[DIM];
		for (int i = 0; i < DIM; i++)
		{
			this.axis[i] = new Rotate (0, pivot[X], pivot[Y], pivot[Z], CubeAxis.AXIS[i]);
		}
		this.getTransforms ().addAll (axis);
	}
	
	/**
	 * Initialize color of this box.
	 * 
	 * @param color
	 *        Color to paint.
	 */
	protected void init (Color color)
	{
		this.material = new PhongMaterial (color);
		this.setMaterial (material);
	}
	
	/**
	 * Add new rotational transformation.
	 * 
	 * @param axis
	 *        axis index X, Y, or Z.
	 * @param amount
	 *        degrees to rotate.
	 */
	public void rotate (int axis, double amount)
	{
		this.getTransforms ().add (new Rotate (amount, pivot[X], pivot[Y], pivot[Z], CubeAxis.AXIS[axis]));
	}
	
	/**
	 * Create new rotation at given axis.
	 * 
	 * @param axis
	 *        axis index X, Y, or Z.
	 * @return Property of newly applied rotation.
	 */
	public DoubleProperty rotate (int axis)
	{
		Rotate rotate = new Rotate (0, pivot[X], pivot[Y], pivot[Z], CubeAxis.AXIS[axis]);
		this.getTransforms ().add (rotate);
		return rotate.angleProperty ();
	}
	
	/**
	 * Reset all transforms to three base axis transforms.
	 */
	public void resetTransform ()
	{
		this.getTransforms ().setAll (axis);
	}
	
	@Override
	public String toString ()
	{
		final int radix = 4, append = 6;
		StringBuilder sb = new StringBuilder ();
		sb.append (Integer.toHexString (System.identityHashCode (this)));
		sb.append ('#');
		String str = Integer.toString (hashCode (), radix);
		for (int i = str.length (); i < append; i++)
		{
			sb.append ('0');
		}
		sb.append (str);
		return sb.toString ();
	}
	
	@Override
	public int hashCode ()
	{
		return this.direction;
	}
}
