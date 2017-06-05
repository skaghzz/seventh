
package seventh.ai.basic;

import seventh.shared.TimeStep;
import java.util.ArrayList;
import java.util.List;

abstract public class SensorSubject {
   private List<Sensor> sensors = new ArrayList<Sensor>();
   public void attach(Sensor sensor){
	   sensers.add(sensor);
   }
   public void detach(Sensor sensor){
	   sensers.remove(sensor);
   }
   public void notifySensors(TimeStep timeStep){
      for(Sensor sensor : sensors) sensor.update(timeStep);
   }
}
 