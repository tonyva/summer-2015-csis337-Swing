package lab3multithreading;

public class Simple {
	private static void process(int iterations){
		for (int i=1; i<=iterations; i++)
			System.out.printf("    process at iteration %04d%n", i);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		process( 1000 );
	}

}
