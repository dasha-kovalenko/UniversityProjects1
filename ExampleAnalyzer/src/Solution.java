
public class Solution {
	public static int random_small = 0;
	public static int random_large = 0;
	public static void main(String[] args) {
		int i = 0;
		int result = 0;
		while(i != 1000){
			result = (int)(Math.random()*1000) + 20;
			if( 0 < result && result < 500)
				random_small++;
			else 
				random_large++;
			i++;
		}
		System.out.println(String.format("random_small = %d, random_large = %d", random_small, random_large));
	}
}
