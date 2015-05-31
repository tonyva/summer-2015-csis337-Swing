package lab3J;

/**
 * Java2D_21
 * An example using animation 
 * 
 * @author Anthony Varghese
 *
 */
public class Java2D_21 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int NUMBER_OF_REPETITIONS = 2000;
		int[] intervals = new int[ NUMBER_OF_REPETITIONS ];

		int min, max, sum;
		double average;
		
		// Loop 1: testing System millisecond timer
		{
			for (int i=0; i<NUMBER_OF_REPETITIONS; i++) {
				long start = System.currentTimeMillis();
				long end;
				do	end   = System.currentTimeMillis();	while (end==start);
					
				intervals[i] = (int) (end-start);
			}
			
			// Analysis:
			min = Integer.MAX_VALUE;
			max = Integer.MIN_VALUE;
			sum = 0;
			
			for (int t : intervals){
				sum += t;
				if (t > max)
					max = t;
				if (t < min)
					min = t;
			}
			average = (double) sum / NUMBER_OF_REPETITIONS;

			System.out.println("Analysis for System millisecond timer" );
			System.out.println("  min interval: " + min + " milliseconds.");
			System.out.println("  max interval: " + max + " milliseconds.");
			System.out.println("  average interval: " + average + " milliseconds.");
			System.out.println();
		}

		// Loop 2: testing System nanosecond timer
		{
			for (int i=0; i<NUMBER_OF_REPETITIONS; i++) {
				long start = System.nanoTime();
				long end;
				do end = System.nanoTime(); while (end==start);
				
				intervals[i] = (int) (end-start);
			}
			
			// Analysis:
			min = Integer.MAX_VALUE;
			max = Integer.MIN_VALUE;
			sum = 0;
			
			for (int t : intervals){
				sum += t;
				if (t > max)
					max = t;
				if (t < min)
					min = t;
			}
			average = (double) sum / NUMBER_OF_REPETITIONS;
			System.out.println("Analysis for System nano timer" );
			System.out.println("  min interval: " + min + " nanoseconds == " + ((double) min / 1e6 ) + " milliseconds." );
			System.out.println("  max interval: " + max + " nanoseconds == " + ((double) max / 1e6 ) + " milliseconds." );
			System.out.println("  average interval: " + average  + " nanoseconds == " + ((double) average / 1e6 ) + " milliseconds." );
			System.out.println();
		}
		
		// Loop 3: testing sleep accuracy
		{
			int[] sleeptimes = {0,1,2,5,10,20,50,100,200,500,1000,2000}; // in milliseconds
			final int SLEEP_REPETITIONS = 10;
			
			System.out.printf("Checking accuracy of sleep - times are for average of %d sleep repetitions%n", SLEEP_REPETITIONS );
			for (int target : sleeptimes){
				double[] measured = new double[SLEEP_REPETITIONS];
				System.out.printf("    for %.3f sec ----> ", target/1000.0);
				System.out.flush();
				for (int i=0; i<SLEEP_REPETITIONS; i++){
					long start = System.nanoTime();
					try {
						Thread.sleep( target );
					} catch(InterruptedException e){
						
					}
					long end   = System.nanoTime();
					
					/* 
					 * The measured time interval includes the overhead for the nano timer
					 */
					measured[i] = (end - start) * 1.0e-9; // convert to seconds
				}
				double total=0;
				for (double m : measured)
					total += m;
				double av = total/SLEEP_REPETITIONS;
				System.out.printf(" measured: %.4f sec%n", av);
			}
		}
		System.out.println("Done!");
	}
}