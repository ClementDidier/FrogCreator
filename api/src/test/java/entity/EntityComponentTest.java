package entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import entities.Entity;
import entities.characters.Player;
import system.components.AbstractComponent;
import system.components.HealthComponent;
import utils.FrogException;

public class EntityComponentTest 
{
	private Entity entity;
	
	@Before
	public void initialize()
	{
		this.entity = new Player();
	}
	
	@Test
	public void addComponentTest() throws FrogException
	{
		AbstractComponent component = new HealthComponent(Integer.MAX_VALUE); 
		this.entity.addComponent(component);
		
		assertTrue("L'élément est censé contenir ce type de composant", this.entity.containsComponent(HealthComponent.COMPONENT_KEY));
	}
	
	@Test(expected=FrogException.class)
	public void getComponentNotExists() throws FrogException
	{
		assertFalse("L'élément n'est pas censé contenir ce type de composant", this.entity.containsComponent(HealthComponent.COMPONENT_KEY));
		
		this.entity.getComponent(HealthComponent.COMPONENT_KEY);
	}
	
	@Test
	public void healthComponentDecreaseLife() throws FrogException
	{
		int maxLife = 20;
		int minLife = 0;
		
		AbstractComponent component = new HealthComponent(maxLife); 
		this.entity.addComponent(component);
		
		if(this.entity.containsComponent(HealthComponent.COMPONENT_KEY))
		{
			HealthComponent c = (HealthComponent) this.entity.getComponent(HealthComponent.COMPONENT_KEY);
			assertEquals("La valeur du composant de vie l'entitée n'est pas valide", maxLife, c.getHealth());
			c.decreaseHealth(10);
			assertEquals("La valeur du composant de vie l'entitée n'est pas valide", maxLife - 10, c.getHealth());
			c.decreaseHealth(15);
			assertEquals("La valeur du composant de vie l'entitée n'est pas valide", minLife, c.getHealth());
		}
		else fail();
	}
	
	@Test
	public void healthComponentIncreaseLife() throws FrogException
	{
		int maxLife = 100;
		
		AbstractComponent component = new HealthComponent(maxLife); 
		this.entity.addComponent(component);
		
		if(this.entity.containsComponent(HealthComponent.COMPONENT_KEY))
		{
			HealthComponent c = (HealthComponent) this.entity.getComponent(HealthComponent.COMPONENT_KEY);
			assertEquals("La valeur du composant de vie l'entitée n'est pas valide", maxLife, c.getHealth());
			c.setHealth(90);
			assertEquals("La valeur du composant de vie l'entitée n'est pas valide", 90, c.getHealth());
			c.increaseHealth(5);
			assertEquals("La valeur du composant de vie l'entitée n'est pas valide", 95, c.getHealth());
			c.increaseHealth(15);
			assertEquals("La valeur du composant de vie l'entitée n'est pas valide", maxLife, c.getHealth());
		}
	}
}
