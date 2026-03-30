package assignment.core;

import java.util.*;
import assignment.effect.*;

public abstract class Combatant {

    protected int hp, maxHp, attack, defense, speed;
    protected boolean alive = true;
    private boolean stunned = false;

    private Map<Class<?>, Integer> cooldowns = new HashMap<>();
    private List<StatusEffect> effects = new ArrayList<>();

    public Combatant(int hp, int attack, int defense, int speed) {
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
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
        return this.getClass().getSimpleName();
    }
}