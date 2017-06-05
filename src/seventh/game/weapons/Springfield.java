/*
 * see license.txt 
 */
package seventh.game.weapons;

import seventh.game.Game;
import seventh.game.entities.Entity;
import seventh.game.entities.Entity.Type;
import seventh.math.Vector2f;
import seventh.shared.SoundType;
import seventh.shared.TimeStep;
import seventh.shared.WeaponConstants;

/**
 * @author Tony
 *
 */
public class Springfield extends Weapon {

        
    protected boolean reloading;
    private boolean wasReloading;
    protected boolean endFire;
    
    /**
     * @param game
     * @param owner
     */
    public Springfield(Game game, Entity owner) {
        super(game, owner, Type.SPRINGFIELD);
    
        this.damage = 100;
        this.reloadTime = 1200;
        this.clipSize = 5;
        this.totalAmmo = 35;
        this.spread = 5;
        this.bulletsInClip = this.clipSize;
        
        this.weaponWeight = WeaponConstants.SPRINGFIELD_WEIGHT;        
        this.lineOfSight = WeaponConstants.SPRINGFIELD_LINE_OF_SIGHT;
        
        this.reloading = false;    
        this.endFire = true;
        
        applyScriptAttributes("springfield");
        setWeaponFire(new SpringfieldFire(game, owner));
    }

    /* (non-Javadoc)
     * @see palisma.game.Weapon#update(leola.live.TimeStep)
     */
    @Override
    public void update(TimeStep timeStep) {    
        super.update(timeStep);
        
        if (reloading && bulletsInClip < clipSize) {
            reload();
        }
        else {
            reloading = false;
            
            if(wasReloading) {
                game.emitSound(getOwnerId(), SoundType.SPRINGFIELD_RECHAMBER, getPos());
                wasReloading = false;
            }
        }
    }
    
    /* (non-Javadoc)
     * @see palisma.game.Weapon#reload()
     */
    @Override
    public boolean reload() {
        if (bulletsInClip < clipSize && weaponTime <= 0) {
            if ( totalAmmo > 0) {
                
                weaponTime = reloadTime;
                bulletsInClip++;
                totalAmmo--;
                                
                reloading = true;
                wasReloading = true;
                
                setReloadingState();
                game.emitSound(getOwnerId(), SoundType.SPRINGFIELD_RELOAD, getPos());
                
                return true;
            }
        }        
//        else {
//            setWaitingState();
//        }
        return false;
    }
    
    /* (non-Javadoc)
     * @see palisma.game.Weapon#beginFire()
     */
    @Override
    public boolean beginFire() {
        getWeaponFire().beginFire();
        return false;
    }
    
    /* (non-Javadoc)
     * @see palisma.game.Weapon#endFire()
     */
    @Override
    public boolean endFire() {    
    	getWeaponFire().endFire();
        return false;
    }
    
    @Override
    public boolean canFire() {    
    	return getWeaponFire().canFire();
    }
    
    /* (non-Javadoc)
     * @see palisma.game.Weapon#calculateVelocity(palisma.game.Direction)
     */
    @Override
    protected Vector2f calculateVelocity(Vector2f facing) {
        return spread(facing, spread);
    }
}
