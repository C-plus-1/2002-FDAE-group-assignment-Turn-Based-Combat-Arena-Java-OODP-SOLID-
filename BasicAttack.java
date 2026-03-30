package assignment.action;


import assignment.core.*;


public class BasicAttack implements Action{

    public void execute(Combatant actor, Combatant target, BattleContext context){
        int damage = DamageCalculator.calculate(actor,target);
        target.takeDamage(damage);
        System.out.println(actor + "-> BasicAttack ->" + target);

    }

   


    
}
