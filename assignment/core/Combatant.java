package assignment.core;

import java.util.*;
import assignment.effect.*;

public abstract class Combatant {

    protected int hp, maxHp, attack, defense, speed;
    protected boolean alive = true;
    private boolean stunned = false;
    private boolean cooldownBypass = false;

    private Map<Class<?>, Integer> cooldowns = new HashMap<>();
    private List<StatusEffect> effects = new ArrayList<>();

    // ADD: name field
    private String name;

    public Combatant(int hp, int attack, int defense, int speed) {
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    // NAME GETTER & SETTER
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasCooldownBypass() { return cooldownBypass; }
    public void setCooldownBypass(boolean bypass) { this.cooldownBypass = bypass; }

    // Existing getters/setters
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getCooldownValue(Class<?> action) {
        return cooldowns.getOrDefault(action, 0);
    }
    public boolean isAlive() { return alive; }
    public boolean isStunned() { return stunned; }
    public void setStunned(boolean stunned) { this.stunned = stunned; }

    public void increaseAttack(int value) { attack += value; }
    public void increaseDefense(int value) { defense += value; }

    public void takeDamage(int dmg) {
        hp = Math.max(0, hp - dmg);
        if (hp == 0) alive = false;
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    public boolean isOnCooldown(Class<?> action) {
        return cooldowns.getOrDefault(action, 0) > 0;
    }

    public void setCooldown(Class<?> action, int turns) {
        cooldowns.put(action, turns);
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void reduceCooldowns() {
        cooldowns.replaceAll((action, turns) -> Math.max(0, turns - 1));
    }

    public void addEffect(StatusEffect effect) {
        effects.add(effect);
        effect.apply(this);
    }

    public void applyEffects() {
        Iterator<StatusEffect> it = effects.iterator();
        while (it.hasNext()) {
            StatusEffect e = it.next();
            e.reduceDuration();
            if (e.isExpired()) {
                e.onExpire(this);
                it.remove();
            }
        }
    }

    @Override
    public String toString() {
        // Return the name if assigned, otherwise default to class name
        return (name != null) ? name : this.getClass().getSimpleName();
    }
}