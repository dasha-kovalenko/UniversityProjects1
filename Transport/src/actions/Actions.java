package actions;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import transport.Transport;

public class Actions {
	
	 private List<Transport> transports = new ArrayList<Transport>();

	 public void setTransports(List<Transport> ts){
		 this.transports = ts;
	 }
	
	public List<Transport> getTransports() {
		return transports;
	}

	public Map<String, Integer> findRoutes(String a, String b){
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		List<Transport> ts = new ArrayList<Transport>();
		for(Transport t: transports){
			String route = t.getRoute();
			if(route.contains(a)&&route.contains(b)){
				int start = route.indexOf(a);
				int end = route.indexOf(b);
				StringBuilder tmp1 = new StringBuilder();
				if(start < end){
					tmp1.append(route.substring(start, end));
					tmp1.append(b);
				}
				else {
					tmp1.append(route.substring(end, start));
					tmp1.append(a);
					tmp1.reverse();
				}
				String foundRoute = tmp1.toString();
				int value = countValue(foundRoute,t);
				map.put(foundRoute, value);
				ts.add(t);
			}
		}
		transports.clear();
		transports.addAll(ts);
		return map;
			
	}
	
	public List<Transport> findValues(int minValue, int maxValue){
		List<Transport> arr = new ArrayList<Transport>();
		for(Transport t : transports){
			if(t.getValue() >= minValue && t.getValue() <= maxValue)
				arr.add(t);
		}
		return arr;
	}
	//стоимость поездки от начального до конечного пунктов
	public int countWholeValue(Transport t){
		return (t.getRoute().length()/2)*t.getValue();
	}
	
/*	public String toString(){
		return transports.toString();
	}
*/
	//стоимость поездки по опред. маршруту
	private int countValue(String route, Transport t) {
		return (route.length()/2)*t.getValue();
	}

}
