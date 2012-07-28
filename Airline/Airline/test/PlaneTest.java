/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.*;
import static org.junit.Assert.*;
import airline.*;
import planes.*;
/**
 *
 * @author Yuliya
 */
public class PlaneTest {
    
   private Plane plane;
    
    public PlaneTest() {
    }

    @Test
    public void toStringTest(){
        
        assertNotNull(plane.toString());
    }
    
    @Test
    public void testGet(){
        assertEquals("Type doesn't match", ((FreightPlane)plane).getFreightType(), null);
        assertEquals("Name doesn't match", plane.getName(), "Ty");
        assertEquals("Capacity doesn't match", plane.getCapacity(),140);
        assertEquals("Distance doesn't match", plane.getDistance(), 10000);
        assertEquals("Speed doesn't match", plane.getSpeed(), 1340);
        assertEquals("Tonnage doesn't match", plane.getTonnage(), 3400);
    }
    
    @Test
    public void testSet(){
        Plane p = new FreightPlane();
        ((FreightPlane)p).setFreightType("Product");
        p.setName("Ty");
        p.setDistance(1000);
        p.setCapacity(34);
        p.setSpeed(1800);
        p.setTonnage(1200);
        assertEquals("Type doesn't match", ((FreightPlane)p).getFreightType(), "Product");
        assertEquals("Name doesn't match", plane.getName(), "Ty");
        assertEquals("Capacity doesn't match", plane.getCapacity(),140);
        assertEquals("Distance doesn't match", plane.getDistance(), 10000);
        assertEquals("Speed doesn't match", plane.getSpeed(), 1340);
        assertEquals("Tonnage doesn't match", plane.getTonnage(), 3400);
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        plane = new FreightPlane("Ty", 140, 1340, 3400, 10000);
    }
    
    @After
    public void tearDown() {
        plane = null;
    }
    
}
