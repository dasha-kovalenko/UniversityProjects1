package third;

public class Threads2  {   
  
	 void waitForSignal() {   
		 Object obj = new Object();  
		synchronized (Thread.currentThread()) {   
		 try {
			obj.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 obj.notify();  
		}  
	 } 
 } 
