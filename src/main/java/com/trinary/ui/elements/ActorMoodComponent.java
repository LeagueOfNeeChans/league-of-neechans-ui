package com.trinary.ui.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.trinary.ui.commons.Sprite;
import com.trinary.ui.layout.Location;

// TODO Convert into a UIElement
@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ActorMoodComponent {
	@XmlAttribute(name="id")
	protected String id;
	
	@XmlElement
	protected Sprite sprite;
	
	@XmlElement
	protected Location position;
	
	public ActorMoodComponent() {
		// TODO Auto-generated constructor stub
	}

	public ActorMoodComponent(String id, Sprite sprite, Location position) {
		super();
		this.id = id;
		this.sprite = sprite;
		this.position = position;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Location getPosition() {
		return position;
	}

	public void setPosition(Location position) {
		this.position = position;
	}
	
	public int getX(UIElement relativeTo) {
		if (this.position.getLeft() != null) {
			return this.position.getLeftPx();
		} else if (this.position.getRight() != null) {
			return relativeTo.getWidth() - (this.sprite.getFrameAttributes().getWidth() + this.position.getRightPx());
		} else {
			return 0;
		}
	}
	
	public int getY(UIElement relativeTo) {
		if (this.position.getTop() != null) {
			return this.position.getTopPx();
		} else if (this.position.getBottom() != null) {
			return relativeTo.getHeight() - (this.sprite.getFrameAttributes().getHeight() + this.position.getBottomPx());
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "ActorMoodComponent [id=" + id + ", sprite=" + sprite
				+ ", position=" + position + "]";
	}
}