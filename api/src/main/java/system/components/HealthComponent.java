package system.components;

import system.GameSystem;
import system.events.GameEvent;
import system.events.GameEventType;

public class HealthComponent extends AbstractComponent 
{
	public static final String COMPONENT_KEY = "HealthComponentKey";
	private int health;
	private int maxHealth;
	
	public HealthComponent(int maxHealth) 
	{
		this.maxHealth = maxHealth;
		this.setHealth(maxHealth);
	}
	
	@Override
	public void update(float delta) 
	{
		if(this.health == 0)
			GameSystem.instance.pushEvent(new GameEvent(GameEventType.DEATH, this.getParent()));
		// TODO : Attention requise : Event réenvoyé en cas de non prise en charge de l'event => flag ?
	}

	/**
	 * Obtient le montant courant de vie de l'entité parent
	 * @return Le montant de vie courant de l'entité
	 */
	public int getHealth() 
	{
		return this.health;
	}

	/**
	 * Met à jour le montant de vie de l'entité parent
	 * @param health Le nouveau montant de vie de l'entité
	 */
	public void setHealth(int health) 
	{
		if(health < 0)
		{
			this.health = 0;
		}
		else if(health > this.maxHealth)
		{
			this.health = this.maxHealth;
		}
		else
		{
			this.health = health;
		}
	}
	
	/**
	 * Diminue le montant de vie courant de l'entité parent
	 * @param value Le montant de vie à réduire au montant de vie courant de l'entité
	 */
	public void decreaseHealth(int value)
	{
		this.setHealth(this.getHealth() - value);
	}
	
	/**
	 * Augmente le montant de vie courant de l'entité parent
	 * @param value Le montant de vie à ajouter au montant de vie courant de l'entité
	 */
	public void increaseHealth(int value)
	{
		this.setHealth(this.getHealth() + value);
	}

	@Override
	public String getKey() {
		return COMPONENT_KEY;
	}
}
