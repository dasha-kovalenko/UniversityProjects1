package comparators;

import java.util.Comparator;
import planes.Plane;

public class SpeedComparator implements Comparator<Plane>{

    @Override
    public int compare(Plane o1, Plane o2) {
        
        if (o1.getSpeed() < o2.getSpeed()) {
            return -1;
        } else if (o1.getSpeed() == o2.getSpeed()) {
            return 0;
        } else {
            return 1;
        }
    }
}
