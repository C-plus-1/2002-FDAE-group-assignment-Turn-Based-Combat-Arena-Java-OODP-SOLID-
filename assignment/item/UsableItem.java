package assignment.item;
import assignment.core.Combatant;

public abstract class UsableItem {
	private String name;

	public UsableItem(String name) {
		this.name = name;
	}

    public String getName() {
		return name;
	}

	public abstract void use(Combatant target);
}
