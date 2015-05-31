package lab3multithreading;

public class SimpleRunnable implements Runnable{
	private int id;
	public SimpleRunnable(int i){ 
		id = i;
	}
	private void process( int iterations){
		for (int i=1; i<=iterations; i++)
			System.out.printf("    process %d at iteration %04d%n", id, i);
	}
	public void run(){
		process( 1000 );
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread th0 = new Thread( new SimpleRunnable(0) );
		Thread th1 = new Thread( new SimpleRunnable(1) );
		th0.start();
		th1.start();
	}

}
