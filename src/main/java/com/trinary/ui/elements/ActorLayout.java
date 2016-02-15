package com.trinary.ui.elements;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement(name="actor")
@XmlAccessorType(XmlAccessType.FIELD)
public class ActorLayout {
	@XmlElement
	String name;
	
	@XmlElementWrapper(name="moods")
	@XmlElement(name="mood")
	List<ActorMoodLayout> moods;
	
	public ActorLayout() {
		// TODO Auto-generated constructor stub
	}

	public ActorLayout(String name, List<ActorMoodLayout> moods) {
		super();
		this.name = name;
		this.moods = moods;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ActorMoodLayout> getMoods() {
		return moods;
	}

	public void setMoods(List<ActorMoodLayout> moods) {
		this.moods = moods;
	}

	@Override
	public String toString() {
		return "ActorLayout [name=" + name + ", moods=" + moods + "]";
	}
}
