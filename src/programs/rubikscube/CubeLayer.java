package programs.rubikscube;

import static programs.rubikscube.RubiksCube.*;
import javafx.scene.paint.*;


/**
 * Enhanced 3D Box with rotational pivot point.
 * Instance of CubeLayer class are used as shell units.
 * 
 * @author hasol
 */
public class CubeLayer extends CubeCore
{
	/** Direction of the face this layer is located. */
	CubeAxis face;
	/** Direction respect to the face this layer is located. */
	int position;
	/** Color of this layer. */
	private Color color;
	/**
	 * Source and Destination of this color layer.
	 * Source points to CubeLayer where currently applied color came from.
	 * Destination points to CubeLayer where originally applied color moved to.
	 */
	private CubeLayer source, destination;
	
	/**
	 * Create new surface unit.
	 * 
	 * @param length
	 *        size of underlying box.
	 * @param offset
	 *        size difference.
	 * @param color
	 *        surface color.
	 * @param face
	 *        directional offset respect to cube.
	 * @param position
	 *        directional offset respect to face.
	 */
	public CubeLayer (double length, double offset, Color color, CubeAxis face, int position)
	{
		super (getLengths (length, offset, face), color);
		this.color = color;
		this.init (length, face, position);
		this.source = null;
		this.destination = null;
	}
	
	/**
	 * Create new surface unit.
	 * 
	 * @param length
	 *        size of underlying box.
	 * @param offset
	 *        size difference.
	 * @param color
	 *        surface color.
	 * @param face
	 *        directional offset respect to cube.
	 * @param direction
	 *        directional offset respect to face.
	 */
	public CubeLayer (double length, double offset, Color color, CubeAxis face, CubeAxis... direction)
	{
		this (length, offset, color, face, CubeAxis.getValue (direction));
	}
	
	/**
	 * Initialize as surface unit.
	 * 
	 * @param length
	 * @param face
	 * @param position
	 */
	private void init (double length, CubeAxis face, int position)
	{
		this.face = face;
		this.position = position;
		this.direction = face.code | position;
		double[] pivot = new double[DIM];
		int[] facesig = CubeAxis.sigNum (face);
		int[] possig = CubeAxis.sigNum (position);
		for (int i = 0; i < DIM; i++)
		{
			pivot[i] = -(facesig[i] * layer_factor + possig[i]) * length;
		}
		this.init (pivot);
	}
	
	/**
	 * Returns x, y, z length for given face layer.
	 * 
	 * @param length
	 *        size of underlying box.
	 * @param offset
	 *        size difference.
	 * @param face
	 *        directional offset.
	 * @return array of width, height, and depth.
	 */
	private static double[] getLengths (double length, double offset, CubeAxis face)
	{
		double[] values = new double[DIM];
		if (face != CubeAxis.CENTER)
		{
			for (int i = 0; i < DIM; i++)
			{
				values[i] = i == face.axis ? offset : length - offset;
			}
		}
		return values;
	}
	
	/**
	 * Sets color of this box. This will set source and destination information to null.
	 * 
	 * @param color
	 *        Color to set this box.
	 * @return current information of color, source, and destination.
	 */
	public CubeTrio setColor (Color color)
	{
		return setTrio (new CubeTrio (color, null, null));
	}
	
	/**
	 * Gets color of this box.
	 * 
	 * @return current information of color.
	 */
	public Color getColor ()
	{
		return this.color;
	}
	
	/**
	 * Sets 3-tuple of color, source, and destination of this box.
	 * 
	 * @param trio
	 *        information of set color, source, and destination.
	 * @return previous information of color, source, and destination.
	 */
	public CubeTrio setTrio (CubeTrio trio)
	{
		CubeTrio current = new CubeTrio (color, source, destination);
		this.color = trio.first;
		this.material.setDiffuseColor (trio.first);
		this.source = trio.second;
		this.destination = trio.third;
		return current;
	}
	
	/**
	 * Gets 3-tuple of color, source, and destination of this box.
	 * 
	 * @return current information of color, source, and destination.
	 */
	public CubeTrio getTrio ()
	{
		return new CubeTrio (color, source, destination);
	}
	
	/**
	 * Get pointer for color source of this box.
	 * 
	 * @return information of source layer.
	 */
	public CubeLayer getSource ()
	{
		return this.source;
	}
	
	/**
	 * Get pointer for color destination of this box.
	 * 
	 * @return information of destination layer.
	 */
	public CubeLayer getDestination ()
	{
		return this.destination;
	}
	
	/**
	 * Set source pointer of this box. New pointer must not be null.
	 * If source is not null, and currently established link is correct,
	 * this method will set current source's destination to null.
	 * If such destination is not null, and currently established link is correct,
	 * this method will set target's destination's source to null.
	 * Finally, this method will also set target's destination to this box.
	 * Any information relating to pointers mentioned above must be retrieved prior to this method call.
	 * 
	 * @param target
	 *        new pointer information.
	 */
	public void setSource (CubeLayer target)
	{
		if (source != null && source.destination == this)
		{
			source.destination = null;
		}
		if (target.destination != null && target.destination.source == target)
		{
			target.destination.source = null;
		}
		this.source = target;
		target.destination = this;
	}
	
	/**
	 * Set destination pointer of this box. New pointer must not be null.
	 * If destination is not null, and currently established link is correct,
	 * this method will set current destination's source to null.
	 * If such source is not null, and currently established link is correct,
	 * this method will set target's source's destination to null.
	 * Finally, this method will also set target's source to this box.
	 * Any information relating to pointers mentioned above must be retrieved prior to this method call.
	 * 
	 * @param target
	 *        new pointer information.
	 */
	public void setDestination (CubeLayer target)
	{
		if (destination != null && destination.source == this)
		{
			destination.source = null;
		}
		if (target.source != null && target.source.destination == target)
		{
			target.source.destination = null;
		}
		this.destination = target;
		target.source = this;
	}
	
	/**
	 * Check integrity of source and destination pointer.
	 * 
	 * @return {@code true} if two pointers are valid, otherwise {@code false}.
	 */
	public boolean checkLink ()
	{
		if (source == null || destination == null)
		{
			return source == null && destination == null;
		}
		else if (source == this || destination == this)
		{
			return source == this && destination == this;
		}
		else
		{
			return source.destination == this && destination.source == this;
		}
	}
	
	/**
	 * Reset source and destination information to null.
	 * If source or destination is not null, and currently established link is correct,
	 * this method will set respective pointer to this to null.
	 * 
	 * @return Current CubeLayer information.
	 */
	public CubeTrio setLinkNull ()
	{
		CubeTrio current = new CubeTrio (color, source, destination);
		if (source != null && source.destination == this)
		{
			source.destination = null;
		}
		if (destination != null && destination.source == this)
		{
			destination.source = null;
		}
		this.source = this.destination = null;
		return current;
	}
	
	/**
	 * Reset source and destination information to this.
	 * If source or destination is not null, and currently established link is correct,
	 * this method will set respective pointer to this to null.
	 * 
	 * @return Current CubeLayer information.
	 */
	public CubeTrio setLinkThis ()
	{
		CubeTrio current = new CubeTrio (color, source, destination);
		if (source != null && source.destination == this)
		{
			source.destination = null;
		}
		if (destination != null && destination.source == this)
		{
			destination.source = null;
		}
		this.source = this.destination = this;
		return current;
	}
	
	@Override
	public int hashCode ()
	{
		return this.face.code * CubeAxis.CODE_LIMIT + this.position;
	}
}
