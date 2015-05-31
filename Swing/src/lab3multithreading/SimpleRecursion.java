package lab3multithreading;

public class SimpleRecursion {
	private static void process(int iteration){
		if (iteration > 1)
			process(iteration-1);
		System.out.printf("    process at iteration %04d%n", iteration);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		process( 1000 );
	}

}
