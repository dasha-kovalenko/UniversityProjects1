/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import airline.Airline;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import planes.FreightPlane;
import planes.Plane;

/**
 *
 * @author Yuliya
 */
public class AirlineTest {
    
    private Plane plane;
    
    public AirlineTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void getPlaneTest(){
        plane = Airline.getPlane("freight", "IL-114-100T", 2, 500, 1000, 1000);
        ((FreightPlane) plane).setFreightType("food");
        
        assertEquals("Type doesn't match", ((FreightPlane)plane).getFreightType(), "food");
        assertEquals("Name doesn't match", plane.getName(), "IL-114-100T");
        assertEquals("Capacity doesn't match", plane.getCapacity(),2);
        assertEquals("Distance doesn't match", plane.getDistance(), 100);
        assertEquals("Speed doesn't match", plane.getSpeed(), 500);
        assertEquals("Tonnage doesn't match", plane.getTonnage(), 1000);
        
    }
    
    @Test
    public void countGeneralCapacityTest(){
        Airline a = new Airline();
        Plane plane1 = new FreightPlane ("IL-114-100T", 2, 500, 1000, 1000);
        a.add(plane1);
        assertEquals("Count doesn't match", a.countGeneralCapacity(), 2);
    }
    
    @Test
    public void countGeneralTonnageTest(){
        Airline a = new Airline();
        Plane plane1 = new FreightPlane ("IL-114-100T", 2, 500, 1000, 1000);
        a.add(plane1);
        assertEquals("Tonnage doesn't match", a.countGeneralTonnage(), 1000);
    }
    
    @Test
    public void findCapacitiesTest(){
        Airline a = new Airline();
        Plane plane1 = new FreightPlane ("IL-114-100T", 5, 500, 1000, 1000);
        a.add(plane1);
        Plane plane2 = new FreightPlane ("IL-114-100T", 2, 500, 1000, 1000);
        a.add(plane2);
        Plane plane3 = new FreightPlane ("IL-114-100T", 10, 500, 1000, 1000);
        a.add(plane3);
        assertEquals("Count of capacities doesn't match", a.findCapacities(1, 6).size(), 2);
    }
}
