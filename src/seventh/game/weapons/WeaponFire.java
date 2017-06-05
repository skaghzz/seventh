package seventh.game.weapons;

public interface WeaponFire {
	/**
     * Invoked for when the trigger is held down
     * @return true if the weapon discharged
     */
   
    abstract public boolean beginFire();
    
    /**
     * Invoked when the trigger is done being pulled.
     * @return true if the weapon discharged
     */
    abstract public boolean endFire() ;
    
    /**
     * @return true if this weapon is loaded and ready to fire
     */
    abstract public boolean canFire() ;
    
}
