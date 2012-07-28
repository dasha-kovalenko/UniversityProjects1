package by.kdv.clientrmi;


public class ClientRunner {

	private static ClientFrame clientFrame;

	public static void main(String[] args) {
		setClientFrame(new ClientFrame("localhost","1099"));
	}

	public static ClientFrame getClientFrame() {
		return clientFrame;
	}

	public static void setClientFrame(ClientFrame clientFrame) {
		ClientRunner.clientFrame = clientFrame;
	}
}
