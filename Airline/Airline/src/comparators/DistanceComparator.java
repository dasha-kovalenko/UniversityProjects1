package comparators;

import java.util.Comparator;
import planes.Plane;

public class DistanceComparator implements Comparator <Plane> {

    @Override
    public int compare(Plane o1, Plane o2) {
        
       if (o2.getDistance() < o1.getDistance()) {
           return -1;
       } else if (o2.getDistance() == o1.getDistance()) {
           return 0;
       } else {
           return 1;
       }
       
    }
}
