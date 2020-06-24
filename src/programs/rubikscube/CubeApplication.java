package programs.rubikscube;

import static programs.rubikscube.RubiksCube.*;
import java.util.*;
import java.io.*;
import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.util.*;
import javafx.util.converter.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;


/**
 * Class for user interface.
 * 
 * @author hasol
 */
public class CubeApplication extends Application
{
	/** Defines Length of one core unit. */
	final static double length = 100;
	/** Defines Length difference between unit length and layer length. */
	final static double offset = 5;
	
	/** Padding for nodes. */
	final static double padding = 12;
	/** side margin. */
	final static double margin_horiz = 100, margin_verti = 50;
	/** scene dimensions. */
	final static double width = 800, height = 700;
	/** Defines scale to translate camera along Z axis */
	final static double camera_factor = -4.0;
	
	/** Pointer to main stage. */
	private static Stage stage;
	/** Primary Cube Manager. */
	private static CubeManager manager;
	
	/**
	 * Main method for eclipse support.
	 * @param args
	 * @see RubiksCube#main(String...)
	 */
	public static void main (String... args)
	{
		RubiksCube.main (args);
	}
	
	/** Starts Application. */
	@Override
	public void start (Stage _stage) throws Exception
	{
		stage = _stage;
		// Divide into 5 regions.
		BorderPane border = new BorderPane ();
		// Inner boxes.
		manager = new CubeManager ();
		// Inner subscene.
		StackPane pane = new StackPane ();
		pane.setAlignment (Pos.CENTER);
		pane.getChildren ().addAll (manager.cubeGroup);
		SubScene subscene =
			new SubScene (pane, width - 2 * margin_horiz, height - 2 * margin_verti, true, SceneAntialiasing.BALANCED);
		PerspectiveCamera camera = new PerspectiveCamera ();
		camera.setTranslateZ (length * camera_factor);
		subscene.setCamera (camera);
		
		// Outer boxes.
		HBox top = new HBox (padding);
		HBox bottom = new HBox (padding);
		VBox left = new VBox (padding);
		VBox right = new VBox (padding);
		top.setPrefSize (width - 2 * padding, margin_verti);
		bottom.setPrefSize (width - 2 * padding, margin_verti);
		left.setPrefSize (margin_horiz, height - 2 * (padding + margin_verti));
		right.setPrefSize (margin_horiz, height - 2 * (padding + margin_verti));
		top.setAlignment (Pos.CENTER_LEFT);
		bottom.setAlignment (Pos.CENTER_LEFT);
		left.setAlignment (Pos.TOP_CENTER);
		right.setAlignment (Pos.TOP_CENTER);
		top.setPadding (new Insets (0, padding, 0, padding));
		bottom.setPadding (new Insets (0, padding, 0, padding));
		
		// Control features.
		FileAction.initialize ();
		top.getChildren ().addAll (FileAction.buttons);
		
		SetAction.initialize ();
		top.getChildren ().addAll (SetAction.box);
		
		MoveAction.initialize ();
		bottom.getChildren ().addAll (MoveAction.buttons);
		
		AnimationAction.initialize ();
		left.getChildren ().addAll (AnimationAction.box);
		
		TurnAction.initialize ();
		left.getChildren ().addAll (TurnAction.panes);
		
		Slider slider1 = new Slider (0, 20, 10);
		slider1.setBlockIncrement (1);
		slider1.setMajorTickUnit (5);
		slider1.setMinorTickCount (4);
		slider1.setShowTickMarks (true);
		slider1.setSnapToTicks (true);
		slider1.setShowTickLabels (true);
		Button button1 = new Button ("Test1");
		button1.setOnAction (event ->
		{
			TurnAction.randomize ((int) slider1.getValue (), 1);
		});
		Slider slider2 = new Slider (0, 20, 10);
		slider2.setBlockIncrement (1);
		slider2.setMajorTickUnit (5);
		slider2.setMinorTickCount (4);
		slider2.setShowTickMarks (true);
		slider2.setSnapToTicks (true);
		slider2.setShowTickLabels (true);
		Button button2 = new Button ("Test2");
		button2.setOnAction (event ->
		{
			CubeSolution.factor = slider2.getValue ();
			System.out.println (CubeSolution.factor);
			System.out.println (new CubeSolution ().apply (manager.getTrios ()));
		});
		Button button3 = new Button ("Test3");
		button3.setOnAction (event ->
		{
			System.out.println (new StepSolution.Step1Solution ().apply (manager.getTrios ()));
		});
		Button button4 = new Button ("Test4");
		button4.setOnAction (event ->
		{
			LinkedList<LinkedList<CubeLayer>> layers = CubeUtils.getLinks (manager);
			StringBuilder[] builder = new StringBuilder[2];
			int[] sum = new int[builder.length], count = new int[builder.length];
			for (int i = 0; i < builder.length; i++)
			{
				builder[i] = new StringBuilder ();
			}
			for (LinkedList<CubeLayer> chain : layers)
			{
				int index = Integer.bitCount (chain.get (0).position) == 1 ? 0 : 1;
				sum[index] += chain.size ();
				count[index]++;
				builder[index].append ("\t");
				builder[index].append (chain.size ());
				builder[index].append (": ");
				builder[index].append (chain);
				builder[index].append ("\n");
			}
			System.out.println (layers.size () + ".\t" + (sum[0] + sum[1]));
			for (int i = 0; i < builder.length; i++)
			{
				System.out.println ("   " + count[i] + ".   " + sum[i]);
				System.out.println (builder[i]);
			}
			System.out.println ();
			/*
			 * NOTE: experiment results.
			 * Odd number of moves may produce following results.
			 *     For Odd number of links, sum of all chains are Even.
			 *     For Even number of links, sum of all chains are Odd.
			 * Even number of moves may produce following results.
			 *     For Odd number of links, sum of all chains are Odd.
			 *     For Even number of links, sum of all chains are Even.
			 * Odd and Even property above are caused by all corner chains.
			 * Regardless of number of moves, all edge chains may produce following results.
			 *     Number of links are always Even.
			 *     Sum of all chains are always Even.
			 */
		});
		//ChoiceBox <String> choice = new ChoiceBox <> ();
		//choice.getItems ().addAll (CubeUtils.COLOR_LIST);
		//ComboBox <String> combo = new ComboBox <> ();
		//combo.getItems ().addAll (CubeUtils.COLOR_LIST);
		//ListView <String> view = new ListView <> ();
		//view.getItems ().addAll (CubeUtils.COLOR_LIST);
		right.getChildren ().addAll (slider1, button1, slider2, button2, button3, button4); //, combo, view);
		
		
		// add all regions to pane.
		border.setTop (top);
		border.setBottom (bottom);
		border.setLeft (left);
		border.setRight (right);
		border.setCenter (subscene);
		// add to scene.
		Scene scene = new Scene (border, width, height, Color.LIGHTGRAY);
		stage.setScene (scene);
		stage.setTitle ("Rubik's Cube");
		stage.getIcons ().add (new Image ("file:/rubiks_cube.png"));
		stage.show ();
	}
	
	/**
	 * Defines perspective movement controls.
	 * 
	 * @author hasol
	 */
	static class MoveAction implements EventHandler <ActionEvent>
	{
		/** Default amount to move. */
		final static double INTER = 10;
		/** Default fix amounts. */
		final static double[] DEFAULT = new double[]
		{
			20, 20, 0
		};
		
		/** All buttons. */
		static Button[] buttons;
		/** Text for each button. */
		private static String[] buttonTexts;
		/** Shared font between buttons. */
		private static Font buttonFont;
		/** Target to rotate. */
		private static Collection <? extends CubeCore> target;
		
		/**
		 * Initialize all buttons, and make buttons ready for use.
		 */
		static void initialize ()
		{
			target = manager.allItems;
			buttonTexts = new String[]
			{
				"\u21AF", "\u2191", "\u2193", "\u2192", "\u2190", "\u21BA", "\u21BB",
			};
			buttons = new Button[buttonTexts.length];
			buttonFont = Font.font (null, 16);
			for (int i = 0; i < buttons.length; i++)
			{
				buttons[i] = new Button (buttonTexts[i]);
				buttons[i].setFont (buttonFont);
				buttons[i].setPrefWidth (40);
				if (i == 0)
				{ // reset view button
					buttons[i].setOnAction (new MoveAction (true, X, DEFAULT[X], Y, DEFAULT[Y], Z, DEFAULT[Z]));
					buttons[i].fire ();
				}
				else
				{ // six camera move buttons
					buttons[i].setOnAction (new MoveAction (false, (i - 1) / 2, i % 2 == 1 ? -INTER : INTER));
				}
			}
		}
		
		/** Pair of rotation index and amount. */
		private Pair <Integer, Double>[] pairs;
		/** {@code true} to set at amount, {@code false} to increment by amount. */
		private boolean set;
		
		/**
		 * Create this EventHandler.
		 * 
		 * @param set
		 *        {@code true} to set at amount, {@code false} to increment by amount.
		 * @param pairs
		 *        Pair of rotation index and amount.
		 */
		@SafeVarargs
		MoveAction (boolean set, Pair <Integer, Double>... pairs)
		{
			this.set = set;
			this.pairs = pairs;
		}
		
		/**
		 * Create this EventHandler.
		 * 
		 * @param set
		 *        {@code true} to set at amount, {@code false} to increment by amount.
		 * @param numbers
		 *        Lists of rotation index and amount.
		 */
		MoveAction (boolean set, Number... numbers)
		{
			this (set, createPairs (numbers));
		}
		
		/**
		 * Convert given lists of numbers to pairs.
		 * 
		 * @param numbers
		 *        Lists of any numbers.
		 * @return Pairs of integer and double.
		 */
		@SuppressWarnings ("unchecked")
		static Pair <Integer, Double>[] createPairs (Number... numbers)
		{
			Pair <Integer, Double>[] pairs = new Pair[numbers.length / 2];
			for (int i = 0; i < pairs.length; i++)
			{
				pairs[i] = new Pair <> (numbers[2 * i].intValue (), numbers[2 * i + 1].doubleValue ());
			}
			return pairs;
		}
		
		@Override
		public void handle (ActionEvent event)
		{
			for (CubeCore cube : target)
			{
				for (Pair <Integer, Double> pair : pairs)
				{
					int index = pair.getKey ();
					cube.axis[index].setAngle ((set ? 0 : cube.axis[index].getAngle ()) + pair.getValue ());
				}
			}
		}
	}
	
	/**
	 * EventHandler prompting file chooser box to open/save file.
	 * 
	 * @author hasol
	 */
	static class FileAction implements EventHandler <ActionEvent>
	{
		/** All buttons. */
		static Button[] buttons;
		/** Text for each button. */
		private static String[] buttonTexts;
		/** Text for dialog box. */
		private static String[] titleTexts;
		/** Constructor arguments. */
		private static boolean[] buttonOptions;
		
		/**
		 * Initialize all buttons, and make buttons ready for use.
		 */
		static void initialize ()
		{
			buttonTexts = new String[]
			{
				"Open ...", "Save ..."
			};
			buttons = new Button[buttonTexts.length];
			titleTexts = new String[]
			{
				"Open file", "Save file"
			};
			buttonOptions = new boolean[]
			{
				false, true
			};
			for (int i = 0; i < buttons.length; i++)
			{
				buttons[i] = new Button (buttonTexts[i]);
				buttons[i].setOnAction (new FileAction (titleTexts[i], buttonOptions[i]));
			}
		}
		
		/** chooser object. */
		private FileChooser chooser;
		/** defines mode of operation. */
		private boolean write;
		
		/**
		 * Create this EventHandler.
		 * 
		 * @param title
		 *        title of dialog box.
		 * @param write
		 *        mode of operation.
		 */
		FileAction (String title, boolean write)
		{
			this.write = write;
			this.chooser = new FileChooser ();
			this.chooser.getExtensionFilters ().addAll (new FileChooser.ExtensionFilter ("Text Files", "*.txt"),
				new FileChooser.ExtensionFilter ("All Files", "*.*"));
			this.chooser.setTitle (title);
		}
		
		@Override
		public void handle (ActionEvent event)
		{
			if (this.write)
			{
				File file = this.chooser.showSaveDialog (stage);
				if (file != null)
				{
					CubeUtils.writeFile (file, manager);
				}
			}
			else
			{
				File file = this.chooser.showOpenDialog (stage);
				if (file != null)
				{
					CubeUtils.readFile (file, manager);
				}
			}
		}
	}
	
	/**
	 * EventHandler updating color of the cube.
	 * 
	 * @author hasol
	 */
	static abstract class SetAction implements EventHandler <ActionEvent>
	{
		/** Default String. */
		final static String def_color = "(DEFAULT)";
		/** Default number of moves. */
		final static int def_moves = 40;
		/** Preferred widths for Nodes. */
		final static double pref_width = 40.0;
		
		/** Main pane. */
		static HBox box;
		/** All buttons. */
		private static Button[] buttons;
		/** Text for each button. */
		private static String[] buttonTexts;
		/** Text for dialog box. */
		private static String[] titleTexts;
		/** TextField for randomize button. */
		private static TextField field;
		/** ChoiceBox for Fill button. */
		private static ComboBox <String> combo;
		
		/**
		 * Initialize all buttons, and make buttons ready for use.
		 */
		static void initialize ()
		{
			buttonTexts = new String[]
			{
				"Reset", "Randomize", "Fill"
			};
			buttons = new Button[buttonTexts.length];
			titleTexts = new String[]
			{
				"Reset cube?", "Randomize cube?", "Fill cube?"
			};
			box = new HBox (padding);
			box.setAlignment (Pos.CENTER_LEFT);
			for (int i = 0; i < buttons.length; i++)
			{
				buttons[i] = new Button (buttonTexts[i]);
				if (i == 0)
				{ // Reset Button.
					buttons[i].setOnAction (new SetAction (titleTexts[i])
					{
						@Override
						public void handle0 (ActionEvent event)
						{
							manager.resetState ();
							if (MoveAction.buttons[0] != null)
							{
								MoveAction.buttons[0].fire ();
							}
							TurnAction.updateColors ();
						}
					});
				}
				else if (i == 1)
				{ // Randomize Button.
					field = new TextField ();
					TextFormatter <Integer> formatter = new TextFormatter <> (new IntegerStringConverter ());
					field.setTextFormatter (formatter);
					formatter.setValue (def_moves);
					field.setPrefWidth (pref_width);
					buttons[i].setOnAction (new SetAction (titleTexts[i])
					{
						@Override
						public void handle0 (ActionEvent event)
						{
							TurnAction.randomize (formatter.getValue (), 1);
						}
					});
					box.getChildren ().add (field);
				}
				else if (i == 2)
				{ // Fill Button.
					combo = new ComboBox <> ();
					combo.getItems ().addAll (def_color);
					combo.getItems ().addAll (CubeUtils.COLOR_LIST);
					combo.setValue (def_color);
					buttons[i].setOnAction (new SetAction (titleTexts[i])
					{
						@Override
						public void handle0 (ActionEvent event)
						{
							String result = combo.getValue ();
							if (result.equals (def_color))
							{
								manager.setColors (CubeManager.DEFAULT_CUBE);
							}
							else
							{
								manager.setColors (CubeUtils.convertColor (combo.getValue ()));
							}
							TurnAction.updateColors ();
						}
					});
					box.getChildren ().add (combo);
				}
				box.getChildren ().add (buttons[i]);
			}
		}
		
		/** Dialog box to prompt user. */
		private Dialog <ButtonType> dialog;
		
		/**
		 * Create this EventHandler.
		 * 
		 * @param title
		 *        Title for the dialog box to prompt user.
		 */
		SetAction (String title)
		{
			dialog = new Alert (Alert.AlertType.CONFIRMATION, title);
			dialog.setHeaderText (title);
		}
		
		@Override
		public void handle (ActionEvent event)
		{
			Optional <ButtonType> result = dialog.showAndWait ();
			if (result.isPresent () && result.get () == ButtonType.OK)
			{
				handle0 (event);
			}
		}
		
		public abstract void handle0 (ActionEvent event);
	}
	
	/**
	 * EventHandler controlling duration of animation.
	 * 
	 * @author hasol
	 */
	static abstract class AnimationAction implements EventHandler <Event>
	{
		/** Conversion factor from milliseconds to seconds. */
		final static double factor = 1000;
		/** Defines slider bounds */
		final static double min = 0.0, max = 5.0, def = 1.0;
		/** Defines slider constants. */
		final static double major = 1.0, amt = 0.25;
		/** Defines slider constants. */
		final static int minor = (int) Math.round (major / amt - 1);
		/** Formatting strings. */
		final static String pre = "Duration: ", post = " sec";
		
		/** Main pane. */
		static VBox box;
		/** Description Text. */
		private static Text text;
		/** Control slider. */
		private static Slider slider;
		
		/**
		 * Initialize sliders, and make them ready for use.
		 */
		static void initialize ()
		{
			box = new VBox ();
			box.setAlignment (Pos.CENTER);
			text = new Text (formatText (def));
			slider = new Slider (min, max, def);
			slider.setBlockIncrement (amt);
			slider.setMajorTickUnit (major);
			slider.setMinorTickCount (minor);
			slider.setShowTickMarks (true);
			slider.setSnapToTicks (true);
			slider.setShowTickLabels (true);
			slider.setPrefWidth (margin_horiz);
			new AnimationAction (slider)
			{
				@Override
				public void handle (Event event)
				{
					double duration = slider.getValue ();
					text.setText (formatText (duration));
					manager.duration = duration == min ? 1 : duration * factor;
				}
			};
			box.getChildren ().addAll (text, slider);
		}
		
		/**
		 * Format value for the text label.
		 * 
		 * @param value
		 *        value obtained from slider.
		 * @return double converted to string and prefixed, suffixed.
		 */
		static String formatText (double value)
		{
			return pre + Double.toString (value) + post;
		}
		
		/**
		 * Create this EventHandler, instantly attaching to parent for press and release events.
		 * 
		 * @param parent
		 *        Parent to attach this EventHandler.
		 */
		AnimationAction (Node parent)
		{
			parent.setOnKeyPressed (this);
			parent.setOnKeyReleased (this);
			parent.setOnMouseClicked (this);
			parent.setOnMouseReleased (this);
			parent.setOnTouchPressed (this);
			parent.setOnTouchReleased (this);
		}
	}
	
	/**
	 * EventHandler rotating sides of cube.
	 * 
	 * @author hasol
	 */
	static class TurnAction implements EventHandler <ActionEvent>
	{
		/** Insets padding amounts. */
		final static double verti = 5, horiz = 10;
		/** Fixed rotation point for each faces. */
		final static Number[][] pairs =
		{
			{
				X, 0, Y, 90, Z, 0
			},
			{
				X, 0, Y, -90, Z, 0
			},
			{
				X, -90, Y, 0, Z, 0
			},
			{
				X, 90, Y, 0, Z, 0
			},
			{
				X, 180, Y, 0, Z, 0
			},
			{
				X, 0, Y, 0, Z, 0
			}
		};
		
		/** All panes. */
		static FlowPane[] panes;
		/** Axis and Turn buttons. */
		private static Button[] axisButtons, turnButtons;
		/** Button axis argument. */
		private static CubeAxis[] buttonOptions;
		/** Button text options */
		private static String[] textOptions;
		
		
		/**
		 * Initialize all buttons, and make buttons ready for use.
		 */
		static void initialize ()
		{
			buttonOptions = CubeAxis.directions;
			textOptions = new String[]
			{
				"CCW", "CW"
			};
			panes = new FlowPane[buttonOptions.length];
			axisButtons = new Button[buttonOptions.length];
			turnButtons = new Button[buttonOptions.length * textOptions.length];
			for (int i = 0; i < buttonOptions.length; i++)
			{
				panes[i] = new FlowPane ();
				panes[i].setAlignment (Pos.CENTER);
				axisButtons[i] = new Button (buttonOptions[i].toString ());
				axisButtons[i].setMnemonicParsing (false);
				axisButtons[i].setOnAction (new MoveAction (true, pairs[i]));
				panes[i].getChildren ().add (axisButtons[i]);
				FlowPane.setMargin (axisButtons[i], new Insets (verti * 2, horiz, verti, horiz));
				for (int j = 0; j < textOptions.length; j++)
				{
					int k = 2 * i + j;
					boolean mode = j == 0;
					turnButtons[k] = new Button (textOptions[j]);
					turnButtons[k].setOnAction (new TurnAction (buttonOptions[i], mode));
					FlowPane.setMargin (turnButtons[k], new Insets (0, mode ? 0 : horiz, verti * 2, mode ? horiz : 0));
					panes[i].getChildren ().add (turnButtons[k]);
				}
			}
			updateColors ();
		}
		
		/**
		 * Update colors of underlying pane to cube's faces.
		 */
		static void updateColors ()
		{
			EnumMap <CubeAxis, HashMap <Integer, Color>> map = manager.getColors ();
			for (int i = 0; i < buttonOptions.length; i++)
			{
				Color color = map.get (buttonOptions[i]).get (CubeAxis.CENTER.code);
				Background background = new Background (new BackgroundFill (color, null, null));
				panes[i].setBackground (background);
			}
		}
		
		/** Rotation information. */
		private CubeDuo pair;
		
		/**
		 * Create this EventHandler
		 * 
		 * @param dir
		 *        Direction of face.
		 * @param mode
		 *        {@code true} for counterclockwise rotation, {@code false} for clockwise rotation.
		 */
		TurnAction (CubeAxis dir, boolean mode)
		{
			this.pair = new CubeDuo (dir, mode);
		}
		
		/**
		 * Schedule random moves.
		 * 
		 * @param number
		 *        specify minimum number of moves.
		 * @param range
		 *        specify random variation of moves. Must be nonzero positive number.
		 */
		public static void randomize (int number, int range)
		{
			CubeAxis[] axis = CubeAxis.directions;
			Random rand = new Random ();
			int limit = rand.nextInt (range) + number;
			for (int i = 0; i < limit; i++)
			{
				int index = rand.nextInt (axis.length);
				boolean mode = rand.nextBoolean ();
				manager.turn (axis[index], mode);
			}
		}
		
		@Override
		public void handle (ActionEvent event)
		{
			manager.turn (pair);
		}
	}
	
	
}
