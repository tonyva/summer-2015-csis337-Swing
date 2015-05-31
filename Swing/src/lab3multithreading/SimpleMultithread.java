package lab3multithreading;

public class SimpleMultithread extends java.lang.Thread{
	private int id;
	public SimpleMultithread(int i){
		id = i;
	}
	private void process( int iterations){
		for (int i=1; i<=iterations; i++) {
			try {
				sleep( 100 /* milliseconds */ );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.printf("    process %d at iteration %04d%n", id, i);
		}
	}
	public void run(){
		process( 10 );
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread th0 = new SimpleMultithread( 0 );
		Thread th1 = new SimpleMultithread( 1 );		

		th0.start();
		th1.start();
	}

}
