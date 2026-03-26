package projCharacterClasses;

public abstract class UsableItem {
	private String name;

	public UsableItem(String name) {
		this.name = name;
	}

	public abstract void use(Combatant user, Combatant target);
}
