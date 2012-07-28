import java.rmi.Naming;
public class CalculatorServer {

	/**
	 * @param args
	 */
	public CalculatorServer () {
		try {
			Calculator c = new CalculatorImpl();
			//Naming.rebind ("rmi://localhost:1099/CalculatorService", c);
			Naming.rebind("//microsof-eb58cc/CalculatorService", c);
		} catch (Exception e) {
			System.out.println("Trouble:" + e);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CalculatorServer();
	}

}
