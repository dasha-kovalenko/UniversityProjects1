package planes;

public class FreightPlane extends Plane{
    
    private String freightType;
    
    public FreightPlane(String name, int capacity, int speed, int tonnage, int distance){
        super(name, capacity, speed, tonnage, distance);
        this.freightType = null;
    }
    
    public FreightPlane(){
        super();
    }
    
    public String getFreightType(){
        return this.freightType;
    }
    
    public void setFreightType(String freightType){
        this.freightType = freightType;
    }
    
    @Override
    public String toString(){
       return super.toString() + ", тип груза: " + getFreightType();  
    }
}
