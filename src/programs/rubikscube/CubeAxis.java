package programs.rubikscube;

import static programs.rubikscube.RubiksCube.*;
import javafx.geometry.*;
import javafx.scene.transform.*;


/**
 * This class represents axis direction with assigned binary bit mask.
 * 
 * @author hasol
 */
public enum CubeAxis
{
	CENTER (-1, 0, 0),
	X_POS (X, +1, 0b100000),
	X_NEG (X, -1, 0b010000),
	Y_POS (Y, +1, 0b001000),
	Y_NEG (Y, -1, 0b000100),
	Z_POS (Z, +1, 0b000010),
	Z_NEG (Z, -1, 0b000001);
	
	final static CubeAxis ORIGIN = CENTER;
	final static CubeAxis X_PLUS = X_POS, X_MINUS = X_NEG;
	final static CubeAxis Y_PLUS = Y_POS, Y_MINUS = Y_NEG;
	final static CubeAxis Z_PLUS = Z_POS, Z_MINUS = Z_NEG;
	
	/** Defines Point3D objects to be used for Rotational axis. */
	public final static Point3D[] AXIS = new Point3D[]
	{
		Rotate.X_AXIS, Rotate.Y_AXIS, Rotate.Z_AXIS
	};
	/** Get 6 directional axis in array. */
	public final static CubeAxis[] directions = new CubeAxis[]
	{
		X_POS, X_NEG, Y_POS, Y_NEG, Z_POS, Z_NEG
	};
	
	/** LIMIT of code value. */
	public final static int CODE_LIMIT = 0b1000000;
	/** Indicates binary mask of axis direction. */
	public final int code;
	/** Indicates index of this axis. */
	public final int axis;
	/** Indicates sign of this axis. */
	public final int sign;
	
	/**
	 * Create enum constant with given binary mask.
	 * 
	 * @param c
	 *        binary code mask.
	 */
	private CubeAxis (int a, int s, int c)
	{
		this.axis = a;
		this.sign = s;
		this.code = c;
	}
	
	/**
	 * Returns opposite direction of same axis.
	 * 
	 * @return opposite of this enum constant.
	 */
	public CubeAxis opposite ()
	{
		switch (this)
		{
			case CENTER:
				return CENTER;
			case X_NEG:
				return X_POS;
			case X_POS:
				return X_NEG;
			case Y_NEG:
				return Y_POS;
			case Y_POS:
				return Y_NEG;
			case Z_NEG:
				return Z_POS;
			case Z_POS:
				return Z_NEG;
			default:
				return null;
		}
	}
	
	/**
	 * Returns other 4 perpendicular axis.
	 * If CENTER, return all 6 axis.
	 * 
	 * @return Array of enum constant.
	 */
	public CubeAxis[] others ()
	{
		if (this == CENTER)
		{
			return directions;
		}
		else
		{
			CubeAxis[] dir = new CubeAxis[4];
			int index = 0;
			for (CubeAxis d : values ())
			{
				if (d != CENTER && this.axis != d.axis)
				{
					dir[index++] = d;
				}
			}
			return dir;
		}
	}
	
	/**
	 * Gets Axis pair for other two axis.
	 * 
	 * @param axis
	 *        X, Y, or Z.
	 * @return Pair of other two axis. If -1, Pair of -1.
	 */
	public IntDuo otherAxis ()
	{
		switch (axis)
		{
			case X:
				return new IntDuo (Y, Z);
			case Y:
				return new IntDuo (Z, X);
			case Z:
				return new IntDuo (X, Y);
			default:
				return new IntDuo (-1, -1);
		}
	}
	
	/**
	 * Returns CubeAxis that matches axis and sign.
	 * 
	 * @param axis
	 *        axis index.
	 * @param sign
	 *        sign number.
	 * @return CubeAxis enum constant.
	 */
	public static CubeAxis getCubeAxis (int axis, int sign)
	{
		for (CubeAxis d : CubeAxis.values ())
		{
			if (d.axis == axis && d.sign == sign)
			{
				return d;
			}
		}
		return null;
	}
	
	/**
	 * Returns CubeAxis that matches description.
	 * 
	 * @param str
	 *        description of axis.
	 * @return CubeAxis enum constant.
	 */
	public static CubeAxis getCubeAxis (String str)
	{
		switch (str.toUpperCase ())
		{
			case "C":
			case "O":
			case "CENTER":
			case "ORIGIN":
				return CENTER;
			case "X+":
			case "XP":
			case "X_POS":
			case "XPOS":
			case "X_PLUS":
			case "XPLUS":
				return X_POS;
			case "X-":
			case "XM":
			case "XN":
			case "X_NEG":
			case "XNEG":
			case "X_MINUS":
			case "XMINUS":
				return X_NEG;
			case "Y+":
			case "YP":
			case "Y_POS":
			case "YPOS":
			case "Y_PLUS":
			case "YPLUS":
				return Y_POS;
			case "Y-":
			case "YM":
			case "YN":
			case "Y_NEG":
			case "YNEG":
			case "Y_MINUS":
			case "YMINUS":
				return Y_NEG;
			case "Z+":
			case "ZP":
			case "Z_POS":
			case "ZPOS":
			case "Z_PLUS":
			case "ZPLUS":
				return Z_POS;
			case "Z-":
			case "ZM":
			case "ZN":
			case "Z_NEG":
			case "ZNEG":
			case "Z_MINUS":
			case "ZMINUS":
				return Z_NEG;
			default:
				return null;
		}
	}
	
	/**
	 * Returns all Directions that matches bit mask.
	 * Returns {@code null} if input is out of bound.
	 * Returns 0-size array if input is 0.
	 * 
	 * @param val
	 *        integer direction.
	 * @return CubeAxis array.
	 */
	public static CubeAxis[] getCubeAxis (int val)
	{
		if (val >= CODE_LIMIT || val < 0)
		{
			return null;
		}
		else
		{
			int bits = Integer.bitCount (val);
			CubeAxis[] dirs = new CubeAxis[bits];
			int index = 0;
			for (CubeAxis d : values ())
			{
				if ((d.code & val) != 0)
				{
					dirs[index++] = d;
				}
			}
			return dirs;
		}
	}
	
	/**
	 * Computes joined code of listed directions.
	 * Repeated values are ignored.
	 * 
	 * @param dirs
	 *        array of x, y, and z signums to query.
	 * @return resulting from bitwise OR operation of requested directions.
	 */
	public static int getValue (int... dirs)
	{
		int value = 0;
		for (CubeAxis d : values ())
		{
			value = (d != CENTER && Integer.signum (dirs[d.axis]) == d.sign) ? value | d.code : value;
		}
		return value;
	}
	
	/**
	 * Computes joined code of listed directions.
	 * Repeated values are ignored.
	 * 
	 * @param dirs
	 *        directions to query.
	 * @return int resulting from bitwise OR operation of all codes.
	 */
	public static int getValue (CubeAxis... dirs)
	{
		int value = 0;
		for (CubeAxis d : dirs)
		{
			value = d == null ? value : value | d.code;
		}
		return value;
	}
	
	/**
	 * Gets 3-dimensional sign number for given direction list.
	 * 
	 * @param val
	 *        directions to query.
	 * @return int array of size 3, each element containing number for x, y, and z axis.
	 */
	public static int[] sigNum (int val)
	{
		return sigNum (getCubeAxis (val));
	}
	
	/**
	 * Gets 3-dimensional sign number for given direction list.
	 * If elements are repeated, it will further increase/decrease returned value.
	 * 
	 * @param dirs
	 *        directions to query.
	 * @return int array of size 3, each element containing number for x, y, and z axis.
	 */
	public static int[] sigNum (CubeAxis... dirs)
	{
		int[] arr = new int[DIM];
		for (CubeAxis d : dirs)
		{
			arr[d.axis] += d != CENTER ? d.sign : 0;
		}
		return arr;
	}
}
