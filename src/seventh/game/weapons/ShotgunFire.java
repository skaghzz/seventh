package seventh.game.weapons;

import seventh.game.Game;
import seventh.game.entities.Entity;
import seventh.math.Vector2f;
import seventh.shared.SoundType;

public class ShotgunFire extends Shotgun implements WeaponFire {

	public ShotgunFire(Game game, Entity owner) {
		super(game, owner);
		// TODO Auto-generated constructor stub
	}
	
	 @Override
	    public boolean beginFire() {
	        if ( canFire() ) {
	            for(int i = 0; i < numberOfPellets; i++) {
	                newBullet();
	            }
	            
	            game.emitSound(getOwnerId(), SoundType.SHOTGUN_FIRE, getPos());
	            game.emitSound(getOwnerId(), SoundType.SHOTGUN_PUMP, getPos());
	                        
	            bulletsInClip--;
	            weaponTime = nextShotTime;
	                    
	            setFireState(); 
	            return true;
	        }
	        else if (reloading) {
	            reloading = false;
	        }
	        else if (bulletsInClip <= 0 ) {
	            setFireEmptyState();            
	        }
	                
	        this.endFire = false;
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
	    
	    @Override
	    public boolean canFire() {    
	        return super.canFire() && this.endFire;
	    }
	    
	    /**
	     * the buck-shot spray shouldn't get unweildly, we have to tame it a bit.
	     */
	    @Override
	    protected int calculateAccuracy(Vector2f facing, int standardAccuracy) {    
	        int newAccuracy = super.calculateAccuracy(facing, standardAccuracy);
	        if(newAccuracy>(spread*2)) {
	            newAccuracy=spread*2;
	        }
	        if(newAccuracy<spread) {
	            newAccuracy = spread;
	        }
	        return newAccuracy;
	    }
}
