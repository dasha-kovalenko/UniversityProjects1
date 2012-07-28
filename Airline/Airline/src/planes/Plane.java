package planes;

public abstract class Plane {

    private String name;
    private int capacity;
    private int speed;
    private int tonnage;
    private int distance;

    protected Plane(String name, int capacity, int speed, int tonnage, int distance) {
        this.name = name;
        this.capacity = capacity;
        this.speed = speed;
        this.tonnage = tonnage;
        this.distance = distance;
    }

    protected Plane() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getTonnage() {
        return this.tonnage;
    }

    public void setTonnage(int tonnage) {
        this.tonnage = tonnage;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Тип: " + this.getClass().getSimpleName() + ", название: "
                + name + ", вместимость: " + capacity
                + ", скорость: " + speed + ", грузоподъёмность: " + getTonnage() + ", дальность полёта: " + getDistance();
    }
}
