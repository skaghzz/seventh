/*
 * see license.txt 
 */
package seventh.game.weapons;

import leola.vm.types.LeoMap;
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
public class Shotgun extends Weapon {

    protected int numberOfPellets;
    protected int nextShotTime;    
    protected boolean reloading;
    
    protected boolean wasReloading;
    protected boolean endFire;
    
    /**
     * @param game
     * @param owner
     */
    public Shotgun(Game game, Entity owner) {
        super(game, owner, Type.SHOTGUN);
    
        this.numberOfPellets = 8;
        this.damage = 40;
        this.reloadTime = 1100;
        this.nextShotTime = 1300;
        this.clipSize = 5;
        this.totalAmmo = 35;
        this.spread = 20;
        this.bulletsInClip = this.clipSize;
        this.lineOfSight = WeaponConstants.SHOTGUN_LINE_OF_SIGHT;
        this.weaponWeight = WeaponConstants.SHOTGUN_WEIGHT;
        
        this.reloading =false;    
        this.endFire = true;
        
        applyScriptAttributes("shotgun");
        setWeaponFire(new ShotgunFire(game, owner));
    }
    

    /* (non-Javadoc)
     * @see seventh.game.weapons.Weapon#applyScriptAttributes(java.lang.String)
     */
    @Override
    protected LeoMap applyScriptAttributes(String weaponName) {
        LeoMap attributes = super.applyScriptAttributes(weaponName);
        if(attributes!=null) {
            this.numberOfPellets = attributes.getInt("number_of_pellets");
            this.nextShotTime = attributes.getInt("next_shot_time");
        }
        return attributes;
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
                game.emitSound(getOwnerId(), SoundType.SHOTGUN_PUMP, getPos());                                
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
                game.emitSound(getOwnerId(), SoundType.SHOTGUN_RELOAD, getPos());                                
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
    
    /* (non-Javadoc)
     * @see palisma.game.Weapon#calculateVelocity(palisma.game.Direction)
     */
    @Override
    protected Vector2f calculateVelocity(Vector2f facing) {
        return spread(facing, spread);
    }
}
