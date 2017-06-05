/*
 * see license.txt 
 */
package seventh.ai.basic;

import seventh.shared.TimeStep;

/**
 * Just a container for all of our sensory inputs
 * 
 * @author Tony
 *
 */
public class Sensors extends SensorSubject{

    private List<Sensor> sensors = new ArrayList<Sensor>();
    
    
    
    public Sensors(Brain brain) {
        sensors.add(new SightSensor(brain));
        sensors.add(new SoundSensor(brain));
        sensors.add(new FeelSensor(brain));
    }
    
    
    /**
     * Resets each {@link Sensor}
     * 
     * @param brain
     */
    public void reset(Brain brain) {
        sensers.clear();
    }
    
    /**
     * @return the feelSensor
     */
    public FeelSensor getFeelSensor() {
        return (FeelSensor)sensers.get(2);
    }
    
    /**
     * @return the sightSensor
     */
    public SightSensor getSightSensor() {
        return (SightSensor)sensers.get(0);
    }
    
    /**
     * @return the soundSensor
     */
    public SoundSensor getSoundSensor() {
        return (SoundSensor)sensers.get(1);
    }
    
    /**
     * Poll each sensor
     * @param timeStep
     */
    public void update(TimeStep timeStep) {
    	notifySensors(timeStep);
     
    }
        
}
