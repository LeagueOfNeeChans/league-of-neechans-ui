package com.trinary.ui.elements;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement(name="mood")
@XmlAccessorType(XmlAccessType.FIELD)
public class ActorMoodLayout {
	@XmlAttribute(name="id")
	protected String id;
	
	@XmlElement
	String resource;
	
	@XmlElement(name="component")
	protected List<ActorMoodComponent> components;
	
	public ActorMoodLayout() {
		// TODO Auto-generated constructor stub
	}

	public ActorMoodLayout(String id, List<ActorMoodComponent> components) {
		super();
		this.id = id;
		this.components = components;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public List<ActorMoodComponent> getComponents() {
		return components;
	}

	public void setComponents(List<ActorMoodComponent> components) {
		this.components = components;
	}

	@Override
	public String toString() {
		return "ActorMoodLayout [id=" + id + ", resource=" + resource
				+ ", components=" + components + "]";
	}
	
	
}