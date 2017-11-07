package entities;

import java.util.HashMap;

import system.components.AbstractComponent;
import utils.FrogException;

public abstract class Entity 
{
	/**
	 * Composants système de l'entité
	 */
	private HashMap<String, AbstractComponent> components;
	
	public Entity()
	{
		this.components = new HashMap<String, AbstractComponent>();
	}
	
	/**
	 * Obtient un composant associé à l'entité
	 * @param componentKey La clé du composant recherché
	 * @return Le composant associé à l'entité
	 * @throws FrogException Exception jetée si le composant n'est pas associé à l'entité
	 */
	public AbstractComponent getComponent(String componentKey) throws FrogException
	{
		if(this.components.containsKey(componentKey))
			return this.components.get(componentKey);
		throw new FrogException("L'entité ne dispose pas du composant recherché");
	}
	
	/**
	 * Associe un nouveau composant à l'entité
	 * @param component Le composant à associer
	 * @throws FrogException Exception jetée si un composant du même type est déjà associé à l'entité
	 */
	public void addComponent(AbstractComponent component) throws FrogException
	{
		if(this.components.containsKey(component.getKey()))
			throw new FrogException("L'entité dispose déjà du composant");
		this.components.put(component.getKey(), component);
	}
	
	/**
	 * Retourne vrai si l'entité dispose du composant avec la clé donnée, Faux dans le cas contraire
	 * @param componentKey La clé du composant à tester
	 * @return Vrai si le composant est associé à l'entité, Faux dans le cas contraire
	 */
	public boolean containsComponent(String componentKey)
	{
		return this.components.containsKey(componentKey);
	}
}
