package system.components;

import entities.Entity;
import system.objects.UpdatableObject;

public abstract class AbstractComponent implements UpdatableObject
{
	private Entity parent;
	
	/**
	 * Obtient le parent du composant
	 * @return Le parent du composant
	 */
	public Entity getParent() {
		return parent;
	}

	/**
	 * Défini le parent du composant
	 * @param parent Le nouveau parent du composant
	 */
	public void setParent(Entity parent) {
		this.parent = parent;
	}
	
	/**
	 * Obtient la valeur de la clé d'identification <u>unique</u> du composant
	 * @return La valeur <u>unique</u> de la clé d'identification du composant
	 */
	public abstract String getKey();
}
