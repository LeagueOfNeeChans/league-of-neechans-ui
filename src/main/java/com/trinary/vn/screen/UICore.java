package com.trinary.vn.screen;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.trinary.text.PositionedElement;
import com.trinary.ui.elements.AnimatedElement;
import com.trinary.ui.elements.ChoiceBox;
import com.trinary.ui.elements.ContainerElement;
import com.trinary.ui.elements.FormattedTextElement;
import com.trinary.ui.elements.ResourceElement;
import com.trinary.ui.simple.UI;
import com.trinary.ui.transitions.FadeOut;
import com.trinary.ui.transitions.FadeOutIn;
import com.trinary.util.EventCallback;
import com.trinary.util.Location;

public class UICore extends UI {
	// State variables
	private boolean paused = false;
	
	// Trinary elements
	private ResourceElement curtain;
	private AnimatedElement scene;
	private FormattedTextElement textBox;
	private ChoiceBox choiceBox;
	private HashMap<String, AnimatedElement> actors;
	
	// Actor positions
	private HashMap<String, ActorPosition> actorPositions;
	
	/**
	 * Setup all custom content in this UI before the thread starts.
	 */
	@Override
	protected void customSetup() {
		// Initialize actors
		actors = new HashMap<>();
		actorPositions = new HashMap<>();
		
		// Mark UI elements with class attributes
		scene     = (AnimatedElement)ContainerElement.getElementByName("scene");
		curtain   = (ResourceElement)ContainerElement.getElementByName("curtain");
		textBox   = (FormattedTextElement)ContainerElement.getElementByName("textBox");
		choiceBox = (ChoiceBox)ContainerElement.getElementByName("choiceBox");
		
		// Set up actor positions
		actorPositions.put("right", new ActorPosition("right: 100%, bottom: 100%", 1, 1.0));
		actorPositions.put("left",  new ActorPosition("left:  0%, bottom: 100%", 1, 1.0));
	}

	/**
	 * Change the scene to the named scene
	 * 
	 * @param sceneName
	 */
	public void changeScene(String sceneName) {
		String resName = String.format("vn.scenes.%s", sceneName);
		scene.setTransition(new FadeOutIn(resName, false));
		lastMonitorable = scene;
	}
	
	/**
	 * Add a named actor in the named position
	 * 
	 * @param name
	 * @param position
	 */
	public void addActor(String name, String position) {
		System.out.println("ADDING ACTOR " + name + " TO POSITION " + position);
		
		ActorPosition pos = actorPositions.get(position);
		if (pos == null) {
			return;
		}
		
		AnimatedElement actor = scene.addChild(AnimatedElement.class);
		actor.move(pos.getPosition());
		actor.setzIndex(pos.getzIndex());
		//actor.scale(new Float(pos.getScale()));
		
		actors.put(name, actor);
	}
	
	/**
	 * Add a named actor with the specified mood in the named position
	 * 
	 * @param name
	 * @param mood
	 * @param position
	 */
	public void addActor(String name, String mood, String position) {
		addActor(name, position);
		
		AnimatedElement actor = container.addChild(AnimatedElement.class);
		String resName = String.format("vn.actors.%s.%s", name, mood);
		actor.changeResource(resName, true);
		
		actors.put(name, actor);
	}
	
	/**
	 * Remove the named actor
	 * 
	 * @param name
	 */
	public void removeActor(String name) {
		System.out.println("ATTEMPTING TO REMOVE ACTOR " + name);
		
		AnimatedElement actor = actors.get(name);
		if (actor == null) {
			System.out.println("UNABLE TO FIND ACTOR WITH NAME: " + name);
			return;
		}
		
		// Fade out
		actor.setTransition(new FadeOut(true));
		
		// Wait for fade out
		while (actor.isBusy()) {
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Mark for deletion
		actor.markForDeletion();
	}
	
	/**
	 * Set the text in the text box
	 * 
	 * @param text
	 */
	public void setText(String actor, String text) {
		textBox.setText(String.format("<b>%s</b>: %s", capitalize(actor), text), true);
		lastMonitorable = textBox;
	}
	
	/**
	 * Change the mood of the named actor
	 * 
	 * @param actor
	 * @param mood
	 */
	public void changeActorMood(String actor, String mood) {
		String resName = String.format("vn.actors.%s.%s", actor, mood);
		AnimatedElement e = actors.get(actor);
		e.setTransition(new FadeOutIn(resName, true));
		lastMonitorable = e;
	}
	
	/**
	 * Move the named actor to this x, y coordinate
	 * 
	 * @param actor
	 * @param x
	 * @param y
	 */
	public void moveActor(String actor, int x, int y) {
		actors.get(actor).move(x, y);
	}
	
	/**
	 * Move the named actor to this relative location
	 * 
	 * @param actor
	 * @param location
	 */
	public void moveActor(String actor, String location) {
		actors.get(actor).move(new Location(location));
	}
	
	/**
	 * Add a choice to the choice box
	 */
	public void addChoice(String id, String text) {
		System.out.println("CHOICEBOX IS: " + choiceBox);
		choiceBox.addChoice(id, "<img src='vn.skin.black-icon'/>" + text);
	}
	
	/**
	 * Show the choice box
	 */
	public void showChoices() {
		choiceBox.finish();
		darken();
		choiceBox.setTransparency(1.0f);
	}
	
	/**
	 * Skips by pulling the next UI message from the bus
	 */
	public void skip() {
		if (textBox.isSkipped()) { // If the textBox is skipped...
			
			// Set the textBox to done
			textBox.setDone();
					
			return;
		}
		
		// Set the textBox as skipped
		textBox.setSkipped();
	}
	
	/**
	 * Pause the UI
	 */
	public void pause() {
		textBox.togglePause();
		paused = !paused;
		
		if (paused) {
			darken();
		} else {
			undarken();
		}
	}
	
	/**
	 * Kill rendering loop
	 */
	public void stop() {
		running = false;
	}
	
	/**
	 * Pause the UI and open the menu
	 */
	public void openMenu() {
		pause();
		
		// TODO write menu UI
	}
	
	/**
	 * Darken the screen
	 */
	public void darken() {
		curtain.setTransparency(0.5f);
	}
	
	/**
	 * Undarken the screen
	 */
	public void undarken() {
		curtain.setTransparency(0.0f);
	}
	
	/**
	 * Check the status of the last command
	 * @return
	 */
	public boolean isDrawing() {
		return lastMonitorable.isBusy();
	}
	
	/**
	 * Set the callback function for mouse click events
	 */
	public void setCallback(EventCallback c) {
		callback = c;
	}

	/**
	 * Monitor the keyboard for activity
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyChar()) {
		case KeyEvent.VK_ESCAPE:
			System.out.println("ESCAPE PRESSED!");
			this.pause();
			break;
		case KeyEvent.VK_ENTER:
			System.out.println("ENTER PRESSED!");
			this.skip();
			break;
		}
	}

	/**
	 * Monitor for clicks on marked elements
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("CLICKED AT: " + (int)Math.round(e.getX()/currentScaleX) + ", " + (int)Math.round(e.getY()/currentScaleY));
		
		CopyOnWriteArrayList<PositionedElement> marked = PositionedElement.getMarked();
		
		synchronized(marked) {
			for (PositionedElement element : marked) {
				if (element.rectangleContains((int)Math.round(e.getX()/currentScaleX), (int)Math.round(e.getY()/currentScaleY))) {
					System.out.println(String.format("\t\tCLICKED ON MONITORED ELEMENT %s!", element.getId()));
					
					choiceBox.setTransparency(0.0f);
					choiceBox.clear();
					undarken();
					
					// Send choice_made message with id back to SE core
					if (callback != null) {
						callback.run(element.getId());
					}
				}
			}
		}
	}
	
	/**
	 * Highlight marked elements when the mouse hovers over them
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		CopyOnWriteArrayList<PositionedElement> marked = PositionedElement.getMarked();
		synchronized(marked) {
			for (PositionedElement element : marked) {
				if (element.rectangleContains((int)Math.round(e.getX()/currentScaleX), (int)Math.round(e.getY()/currentScaleY))) {
					choiceBox.highlight(element.getId());
				}
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}
	
	private String capitalize(String line) {
	  return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
}