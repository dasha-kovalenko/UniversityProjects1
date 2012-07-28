package factory;

import transport.*;

public class TransportFactory {
	private enum  Signs {BUS, TROLLEYBUS}
	  public static Transport getClassFromFactory(String id,int capacity, int number, int startHour, int startMinute,
				int finishHour, int finishMinute, String route, int value, int interval) {
	        Signs sign = Signs.valueOf(id.toUpperCase());
	        switch(sign){
	         case BUS  : {
	        	 return new Bus(capacity, number, startHour, startMinute,
							finishHour, finishMinute, route, value, interval);
	         }
	         case TROLLEYBUS : {
	        	 return new Trolleybus(capacity, number, startHour, startMinute,
							finishHour, finishMinute, route, value, interval);
	         }
	         default : throw new EnumConstantNotPresentException(
	             Signs.class, sign.name());
	        }
	   }

}
