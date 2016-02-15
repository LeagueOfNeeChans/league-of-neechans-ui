package com.trinary.ui.elements;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@SuppressWarnings("restriction")
public class ActorElement extends AnimatedElement {
	protected ActorLayout layout;
	protected ActorMoodLayout currentMoodLayout;
	protected ResourceElement element;

	public ActorElement(String layoutFile) {		
		JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance(ActorLayout.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		Unmarshaller jaxbUnmarshaller = null;
		try {
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		layout = null;
		try {
			layout = (ActorLayout)jaxbUnmarshaller.unmarshal(new File(layoutFile));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changeMood(String mood) {
		for (ActorMoodLayout moodLayout : layout.getMoods()) {
			if (moodLayout.getId().equals(mood)) {
				currentMoodLayout = moodLayout;
				changeResource(moodLayout.getResource(), true);
				return;
			}
		}
	}
	
	public void changeAnimation(String component, String animation) {
		for (ActorMoodComponent moodComponent : currentMoodLayout.getComponents()) {
			if (moodComponent.getId().equals(component)) {
				moodComponent.getSprite().setCurrentAnimation(animation);
				return;
			}
		}
	}

	@Override
	public BufferedImage render() {
		BufferedImage elementImage = super.render();
		
		Graphics2D g = (Graphics2D)surface.getGraphics();
		g.drawImage(elementImage, 0, 0, null);
		
		for (ActorMoodComponent moodComponent : currentMoodLayout.getComponents()) {
			g.drawImage(moodComponent.getSprite().getCurrentFrame(),
					moodComponent.getX(this),
					moodComponent.getY(this),
					null);
			moodComponent.getSprite().step();
		}
		
		return surface;
	}
}