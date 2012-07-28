package actions;
import java.util.ArrayList;
import transport.PassTransport;

public class Actions {
	
	 private ArrayList<PassTransport> transports = new ArrayList<PassTransport>();
	 private ArrayList<Integer> indexes = new ArrayList<Integer>();
	 private ArrayList<String> routes = new ArrayList<String>();
	private int n; 

	 public void setAutoPark(ArrayList<PassTransport> ts){
		 this.transports = ts;
	 }
		
		public ArrayList<Integer> getIndexes(){
			return this.indexes;
		}
	public ArrayList<String> findRoutes(String a, String b){
		setN(0);
		for(PassTransport t: transports){
			String route = t.getRoute();
			if(route.contains(a)&&route.contains(b)){
				setN(getN() + 1);
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
				routes.add(tmp1.toString());
				indexes.add(transports.indexOf(t));
			}
		}
		return routes;
			
	}
	
	public void showResults(){
		if(routes.isEmpty())
			System.out.println("No routes are found!");
		else{
			for(int i = 0; i < indexes.size(); i++){
				String r = routes.get(i);
				PassTransport t = transports.get(indexes.get(i));
				int res = (r.length()/2)*t.getValue();
				System.out.println(String.format("The route %s costs %d on %s",r,res,t.show()));
			}
		}
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

}
