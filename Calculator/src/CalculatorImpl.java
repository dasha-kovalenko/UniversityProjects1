public class CalculatorImpl extends java.rmi.server. UnicastRemoteObject implements Calculator {
 // –еализации должны иметь
 // €вный конструктор дл€
 // того, чтобы объ€вить
 // исключительную ситуацию RemoteException
	public CalculatorImpl() throws java.rmi. RemoteException {
		super ();
	}
	public long add (long a, long b) throws java.rmi. RemoteException {
		return a + b;
	}
	public long sub (long a, long b)
	throws java.rmi. RemoteException {
		return a - b;
	}
	public long mul (long a, long b) throws java.rmi. RemoteException {
		return a * b;
	}
	public long div (long a, long b) throws java.rmi. RemoteException {
		return a / b;
	}
}
