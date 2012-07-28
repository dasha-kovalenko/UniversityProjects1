
public class Main {

	private final static Integer N = 10;
	public static void main(String[] args) {
		Double a = 0.0;
		Double b = 1.0;
		Double h = (b - a)/10;
		
		Double[]x = new Double[10];
		Double[]y = new Double[11];
		Double[]d = new Double[11];
		y[0] = 1.0;
		
		//€вный метод Ёйлера
		for(int i = 0; i < N; i++){
			x[i] = a + i*h;
			y[i+1]=y[i]+h*(Math.cos(y[i])-Math.sin(x[i])-Math.cos(Math.cos(x[i])));
			System.out.println(y[i].toString());
		}
		
		Double[][]z = new Double[100][100];

		for(int i = 0; i <10; i++)
			z[i][0] = y[i];
		Double e = 0.0001;
		int n = 0; 
		int k = 0;
		System.out.print("\n");
		
		//не€вный метод Ёйлера с реализацией метода Ќьютона
		while( k < 99 && n < N-1 ){
			//k++;
			Double c = Math.cos(z[n+1][k])-Math.sin(x[n+1])-Math.cos(Math.cos(x[n+1]));
			z[n+1][k+1] = z[n+1][k] - ((z[n+1][k]-y[n]-h*c)/(1-h*c*Math.sin(z[n+1][k])));
			if(Math.abs(z[n+1][k+1]-z[n+1][k])<e){
				d[n+1] = z[n+1][k+1];
				System.out.println(k+"\t\t"+d[n+1]);
				n++;
				k = 0;
				continue;
			}
			k++;
				
		}
		System.out.print("\n");
		
		
		//метод трапеций с реализацией метода Ќьютона
		n = 0; 
		k = 0;
		Double[] g = new Double[11];
		while( k < 99 && n < N-1 ){
			Double f = h* 0.5*(Math.cos(y[n])-Math.sin(x[n])-Math.cos(Math.cos(x[n]))+ 
					+ Math.cos(z[n+1][k])-Math.sin(x[n+1])-Math.cos(Math.cos(x[n+1])));
			z[n+1][k+1] = z[n+1][k] - ((z[n+1][k]-y[n]-f)/(1-f*Math.sin(z[n+1][k])));
			if(Math.abs(z[n+1][k+1]-z[n+1][k])<e){
				g[n+1] = z[n+1][k+1];
				System.out.println(k+"\t\t"+g[n+1]);
				n++;
				k = 0;
				continue;
			}
			k++;
		}
		System.out.print("\n");

		Double fi0 = 0.0;
		Double fi1 = 0.0;
		Double fi2 = 0.0;
		Double fi3 = 0.0;
		Double[] w = new Double[11];
		
		n = 0;
		w[0] = y[0];
		//метод –унге- утты
		while(n < N)
		{
			fi0 = h*(Math.cos(w[n])-Math.sin(x[n])-Math.cos(Math.cos(x[n])));
			double q = h/2;
			fi1 = h*(Math.cos(w[n]+fi0/2)-Math.sin(x[n]+q)-Math.cos(Math.cos(x[n]+q)));
			fi2 = h*(Math.cos(w[n]+fi1/2)-Math.sin(x[n]+q)-Math.cos(Math.cos(x[n]+q)));
			fi3 = h*(Math.cos(w[n]+fi2)-Math.sin(x[n]+h)-Math.cos(Math.cos(x[n]+h)));
			w[n+1] = w[n]+(fi0+2*fi1+2*fi2+fi3)/6;
			System.out.println(w[n]);
			n++;
		}
		System.out.print("\n");
//вывод точного решени€
		for(int i = 0; i < N; i++)
			System.out.println(Math.cos(x[i]));
	}

}
