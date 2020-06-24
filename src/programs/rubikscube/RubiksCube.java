package programs.rubikscube;

import javafx.application.*;


/**
 * Class containing main method and global variables.
 * <p>
 * All other classes in this package should have following static import line.
 * <p>
 * {@code import static programs.rubikscube.RubiksCube.*;}
 * 
 * @author hasol
 */
public class RubiksCube
{
	/** Denotes number of dimensions and number of axis. */
	public final static int DIM = 3;
	/** Denotes index of each axis. **/
	public final static int X = 0, Y = 1, Z = 2;
	
	/** Defines n-cube */
	public final static int CUBE_SIZE = 3;
	/** Defines number of sides in a cube */
	public final static int SIDE_SIZE = 6;
	/** Defines number of cores in a face. */
	public final static int FACE_SIZE = CUBE_SIZE * CUBE_SIZE;
	/** Defines number of individual core cubes. */
	public final static int CORE_SIZE = FACE_SIZE * CUBE_SIZE;
	/** Defines number of individual layers in whole shell. */
	public final static int LAYER_SIZE = FACE_SIZE * SIDE_SIZE;
	
	/**
	 * Sole purpose is to start the program.
	 * 
	 * @param args
	 *        JAVA program arguments.
	 * @see CubeApplication#main(String...)
	 * @see CubeApplication#start(javafx.stage.Stage)
	 */
	public static void main (String... args)
	{
		Application.launch (CubeApplication.class, args);
	}
}
