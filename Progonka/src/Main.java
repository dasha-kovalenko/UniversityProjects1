
public class Main {

	/**
	 * @param args
	 */
	private final static int SIZE = 11;
	private final static double h = 0.1;
	private final static double A = 0.0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] x = new double[SIZE];
		double[] y = new double[SIZE];
		double[] a = new double[SIZE];
		double[] b = new double[SIZE];
		double[] c = new double[SIZE];
		double[] d =  new double[SIZE];
		double[] ksi = new double[SIZE];
		double[] eta = new double[SIZE];
		double hPow2 = Math.pow(h, 2);
		double alpha0 = -1.95, alpha1 = 2.0;
		double A1 = 0.0, B1 = -6.02;
		double beta0 = -1.2, beta1 = 1.0;
		ksi[1] = -(alpha1/alpha0);
		eta[1] = -(A1/alpha0);
		for(int k = 1; k < SIZE; k++){
			a[k] = 1.0;
			x[k] = A + k*h;
			b[k] = 2 + hPow2*(x[k]+1);
			c[k] = 1.0;
			d[k] = hPow2*(Math.pow(x[k],5)+Math.pow(x[k],3)-6*x[k]);
			if(k!=SIZE-1){
				ksi[k+1] = (c[k])/(b[k]-ksi[k]*c[k]);
				eta[k+1] = (a[k]*eta[k]-d[k])/(b[k]-ksi[k]*c[k]);
			}
			
		}
		int n = SIZE - 1;
		y[n] = (B1-beta0*eta[n])/(beta1+beta0*ksi[n]);
		for(int k = n-1; k > 0; k--){
			y[k] = ksi[k+1]*y[k+1] + eta[k+1];
			System.out.print("ksi: " + ksi[k] + " eta: " + eta[k] + " y: "+ y[k]);
			System.out.println();
		}
	}

}
