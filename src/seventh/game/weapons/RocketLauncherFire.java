package seventh.game.weapons;

import seventh.game.Game;
import seventh.game.entities.Entity;

public class RocketLauncherFire extends RocketLauncher implements WeaponFire{

	public RocketLauncherFire(Game game, Entity owner) {
		super(game, owner);
		// TODO Auto-generated constructor stub
	}
	 @Override
	    public boolean beginFire() {
	        if(canFire()) {
	            newRocket();
	            emitFireSound();
	            
	            bulletsInClip--;
	            weaponTime = 1500;
	            
	            setFireState();
	            return true;
	        }
	        else if (bulletsInClip <= 0 ) {
	            setFireEmptyState();
	        }
//	        else {
//	            setWaitingState();
//	        }
	        return false;
	    }
	    
	    /* (non-Javadoc)
	     * @see palisma.game.Weapon#endFire()
	     */
	    @Override
	    public boolean endFire() {    
	        this.endFire = true;
	        return false;
	    }
	    
	    /*
	     * (non-Javadoc)
	     * @see seventh.game.weapons.Weapon#canFire()
	     */
	    @Override
	    public boolean canFire() {    
	        return super.canFire() && this.endFire;
	    }
}
