/***
 * @author Юлия Тарасенко
 */

package airline;

import java.util.ArrayList;
import planes.FreightPlane;
import planes.MilitaryPlane;
import planes.PassengerPlane;
import planes.Plane;

public class Airline extends ArrayList<Plane>  {

   private enum Types {MILITARY, PASSENGER, FREIGHT}
    
   public static Plane getPlane(String id, String name, int capacity, int speed, int tonnage, int distance){
       Types type = Types.valueOf(id.toUpperCase());
       
       switch (type) {    
       case MILITARY : 
           return new MilitaryPlane(name, capacity, speed, tonnage, distance);
       case PASSENGER : 
           return new PassengerPlane(name, capacity, speed, tonnage, distance);
       case FREIGHT : 
           return new FreightPlane(name, capacity, speed, tonnage, distance);
       default: 
           throw new EnumConstantNotPresentException(Types.class, type.name());
       }
       
   }
   
    public int countGeneralCapacity() {
        int count = 0;
        for (int i = 0; i < this.size(); i++) {
            count += this.get(i).getCapacity();
        }
        return count;
    }
    
    public int countGeneralTonnage(){
        int count = 0;
        for (int i = 0; i < this.size(); i++) {
            count += this.get(i).getTonnage();
        }
        return count;
    }

    public void showCapacity() {
        System.out.println("Общая вместимость самолётов: " + countGeneralCapacity());
    }
    
    public ArrayList<Plane> findCapacities(int minValue,int maxValue) {
        ArrayList<Plane> planes = new ArrayList<Plane>(); 
        for (Plane p : this) {
            if ((p.getCapacity() >= minValue) && (p.getCapacity() <= maxValue)) {
                planes.add(p); 
            }
        }
        return planes;
    }  
}
