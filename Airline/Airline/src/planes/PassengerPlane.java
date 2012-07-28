package planes;

public class PassengerPlane extends Plane{
    
    private int price;
    
    public PassengerPlane(String name, int capacity, int speed, int tonnage, int distance){
        super(name, capacity, speed, tonnage, distance);
        this.price = 0;
    }
    
    public int getPrice(){
        return this.price;
    }
    
    public void setPrice(int price){
        this.price = price;
    }
    
    @Override
    public String toString(){
        return super.toString() + ", стоимость билета: " + getPrice();
    }
}
