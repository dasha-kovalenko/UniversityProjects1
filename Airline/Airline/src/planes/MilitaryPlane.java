package planes;

public class MilitaryPlane extends Plane {

    private int calibreWeight;

    public MilitaryPlane(String name, int capacity, int speed, int tonnage, int distance) {
        super(name, capacity, speed, tonnage, distance);
        this.calibreWeight = 0;
    }

    public int getCalibreWeight() {
        return this.calibreWeight;
    }

    public void setCalibreWeight(int calibreWeight) {
        this.calibreWeight = calibreWeight;
    }

    @Override
    public String toString() {
        return super.toString() + ", масса калибра: " + getCalibreWeight();
    }
}
