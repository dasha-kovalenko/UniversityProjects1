
public class Runner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*String saltDasha = BCrypt.gensalt();
		String dashaHashed = BCrypt.hashpw("dasha", saltDasha);
		System.out.println(dashaHashed);
		System.out.println(saltDasha);
		String saltYana = BCrypt.gensalt();
		String yanaHashed = BCrypt.hashpw("yana", saltYana);
		System.out.println(yanaHashed);
		System.out.println(saltYana);*/
		
		String saltAdmin = BCrypt.gensalt();
		String adminHashed = BCrypt.hashpw("1234", saltAdmin);
		System.out.println(adminHashed);
		System.out.println(saltAdmin);
		
	}

}
