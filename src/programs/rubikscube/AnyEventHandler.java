package programs.rubikscube;

import javafx.event.*;
import javafx.scene.*;


/**
 * Class to allow bulk attachment of this handler to given node.
 * 
 * @author hasol
 */
@FunctionalInterface
public interface AnyEventHandler extends EventHandler <Event>
{
	@Override
	public void handle (Event event);
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnInputMethodTextChanged(EventHandler)
	 */
	public default void setOnChanged (Node target)
	{
		target.setOnInputMethodTextChanged (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnMouseClicked(EventHandler)
	 */
	public default void setOnClicked (Node target)
	{
		target.setOnMouseClicked (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnContextMenuRequested(EventHandler)
	 */
	public default void setOnContextMenu (Node target)
	{
		target.setOnContextMenuRequested (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnDragDetected(EventHandler)
	 */
	public default void setOnDetected (Node target)
	{
		target.setOnDragDetected (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnDragDone(EventHandler)
	 */
	public default void setOnDone (Node target)
	{
		target.setOnDragDone (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnSwipeDown(EventHandler)
	 */
	public default void setOnDown (Node target)
	{
		target.setOnSwipeDown (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnDragDetected(EventHandler)
	 * @see Node#setOnDragDone(EventHandler)
	 * @see Node#setOnDragDropped(EventHandler)
	 * @see Node#setOnDragEntered(EventHandler)
	 * @see Node#setOnDragExited(EventHandler)
	 * @see Node#setOnDragOver(EventHandler)
	 */
	public default void setOnDrag (Node target)
	{
		target.setOnDragDetected (this);
		target.setOnDragDone (this);
		target.setOnDragDropped (this);
		target.setOnDragEntered (this);
		target.setOnDragExited (this);
		target.setOnDragOver (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnMouseDragged(EventHandler)
	 */
	public default void setOnDragged (Node target)
	{
		target.setOnMouseDragged (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnDragDropped(EventHandler)
	 */
	public default void setOnDropped (Node target)
	{
		target.setOnDragDropped (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnDragEntered(EventHandler)
	 * @see Node#setOnMouseDragEntered(EventHandler)
	 * @see Node#setOnMouseEntered(EventHandler)
	 */
	public default void setOnEntered (Node target)
	{
		target.setOnDragEntered (this);
		target.setOnMouseDragEntered (this);
		target.setOnMouseEntered (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnDragExited(EventHandler)
	 * @see Node#setOnMouseDragExited(EventHandler)
	 * @see Node#setOnMouseExited(EventHandler)
	 */
	public default void setOnExited (Node target)
	{
		target.setOnDragExited (this);
		target.setOnMouseDragExited (this);
		target.setOnMouseExited (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnRotationFinished(EventHandler)
	 * @see Node#setOnScrollFinished(EventHandler)
	 * @see Node#setOnZoomFinished(EventHandler)
	 */
	public default void setOnFinished (Node target)
	{
		target.setOnRotationFinished (this);
		target.setOnScrollFinished (this);
		target.setOnZoomFinished (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnInputMethodTextChanged(EventHandler)
	 */
	public default void setOnInputMethodText (Node target)
	{
		target.setOnInputMethodTextChanged (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnKeyPressed(EventHandler)
	 * @see Node#setOnKeyReleased(EventHandler)
	 * @see Node#setOnKeyTyped(EventHandler)
	 */
	public default void setOnKey (Node target)
	{
		target.setOnKeyPressed (this);
		target.setOnKeyReleased (this);
		target.setOnKeyTyped (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnSwipeLeft(EventHandler)
	 */
	public default void setOnLeft (Node target)
	{
		target.setOnSwipeLeft (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnMouseClicked(EventHandler)
	 * @see Node#setOnMouseDragged(EventHandler)
	 * @see Node#setOnMouseEntered(EventHandler)
	 * @see Node#setOnMouseExited(EventHandler)
	 * @see Node#setOnMouseMoved(EventHandler)
	 * @see Node#setOnMousePressed(EventHandler)
	 * @see Node#setOnMouseReleased(EventHandler)
	 */
	public default void setOnMouse (Node target)
	{
		target.setOnMouseClicked (this);
		target.setOnMouseDragged (this);
		target.setOnMouseEntered (this);
		target.setOnMouseExited (this);
		target.setOnMouseMoved (this);
		target.setOnMousePressed (this);
		target.setOnMouseReleased (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnMouseDragEntered(EventHandler)
	 * @see Node#setOnMouseDragExited(EventHandler)
	 * @see Node#setOnMouseDragOver(EventHandler)
	 * @see Node#setOnMouseDragReleased(EventHandler)
	 */
	public default void setOnMouseDrag (Node target)
	{
		target.setOnMouseDragEntered (this);
		target.setOnMouseDragExited (this);
		target.setOnMouseDragOver (this);
		target.setOnMouseDragReleased (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnMouseMoved(EventHandler)
	 * @see Node#setOnTouchMoved(EventHandler)
	 */
	public default void setOnMoved (Node target)
	{
		target.setOnMouseMoved (this);
		target.setOnTouchMoved (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnDragOver(EventHandler)
	 * @see Node#setOnMouseDragOver(EventHandler)
	 */
	public default void setOnOver (Node target)
	{
		target.setOnDragOver (this);
		target.setOnMouseDragOver (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnKeyPressed(EventHandler)
	 * @see Node#setOnMousePressed(EventHandler)
	 * @see Node#setOnTouchPressed(EventHandler)
	 */
	public default void setOnPressed (Node target)
	{
		target.setOnKeyPressed (this);
		target.setOnMousePressed (this);
		target.setOnTouchPressed (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnKeyReleased(EventHandler)
	 * @see Node#setOnMouseDragReleased(EventHandler)
	 * @see Node#setOnMouseReleased(EventHandler)
	 * @see Node#setOnTouchReleased(EventHandler)
	 */
	public default void setOnReleased (Node target)
	{
		target.setOnKeyReleased (this);
		target.setOnMouseDragReleased (this);
		target.setOnMouseReleased (this);
		target.setOnTouchReleased (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnContextMenuRequested(EventHandler)
	 */
	public default void setOnRequested (Node target)
	{
		target.setOnContextMenuRequested (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnSwipeRight(EventHandler)
	 */
	public default void setOnRight (Node target)
	{
		target.setOnSwipeRight (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnRotate(EventHandler)
	 * @see Node#setOnRotationFinished(EventHandler)
	 * @see Node#setOnRotationStarted(EventHandler)
	 */
	public default void setOnRotation (Node target)
	{
		target.setOnRotate (this);
		target.setOnRotationFinished (this);
		target.setOnRotationStarted (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnScroll(EventHandler)
	 * @see Node#setOnScrollFinished(EventHandler)
	 * @see Node#setOnScrollStarted(EventHandler)
	 */
	public default void setOnScroll (Node target)
	{
		target.setOnScroll (this);
		target.setOnScrollFinished (this);
		target.setOnScrollStarted (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnRotationStarted(EventHandler)
	 * @see Node#setOnScrollStarted(EventHandler)
	 * @see Node#setOnZoomStarted(EventHandler)
	 */
	public default void setOnStarted (Node target)
	{
		target.setOnRotationStarted (this);
		target.setOnScrollStarted (this);
		target.setOnZoomStarted (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnTouchStationary(EventHandler)
	 */
	public default void setOnStationary (Node target)
	{
		target.setOnTouchStationary (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnSwipeDown(EventHandler)
	 * @see Node#setOnSwipeLeft(EventHandler)
	 * @see Node#setOnSwipeRight(EventHandler)
	 * @see Node#setOnSwipeUp(EventHandler)
	 */
	public default void setOnSwipe (Node target)
	{
		target.setOnSwipeDown (this);
		target.setOnSwipeLeft (this);
		target.setOnSwipeRight (this);
		target.setOnSwipeUp (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnTouchMoved(EventHandler)
	 * @see Node#setOnTouchPressed(EventHandler)
	 * @see Node#setOnTouchReleased(EventHandler)
	 * @see Node#setOnTouchStationary(EventHandler)
	 */
	public default void setOnTouch (Node target)
	{
		target.setOnTouchMoved (this);
		target.setOnTouchPressed (this);
		target.setOnTouchReleased (this);
		target.setOnTouchStationary (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnKeyTyped(EventHandler)
	 */
	public default void setOnTyped (Node target)
	{
		target.setOnKeyTyped (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnSwipeUp(EventHandler)
	 */
	public default void setOnUp (Node target)
	{
		target.setOnSwipeUp (this);
	}
	
	/**
	 * @param target
	 *        Node to attach this EventHandler.
	 * @see Node#setOnZoom(EventHandler)
	 * @see Node#setOnZoomFinished(EventHandler)
	 * @see Node#setOnZoomStarted(EventHandler)
	 */
	public default void setOnZoom (Node target)
	{
		target.setOnZoom (this);
		target.setOnZoomFinished (this);
		target.setOnZoomStarted (this);
	}
}
