// Jonathan Taymont
// 4/2/23
// CEN-3024C
// Module 8 | Concurrency Assignment
// Implement a parallel array sum, and compare performance with single thread sum.
// Make an array of 200 million random numbers between 1 and 10.
// Compute the sum in parallel using multiple threads.
// Then compute the sum with only one thread, and display the sum and times for both cases.

import java.util.Random;

public class ConcurrencyExample
{
    // Compute the sum in parallel using multiple threads.
    public static class MultipleThreads extends Thread
    {
        int arraySize = 200000000;
        int numThreads = 8;
        int numbersPerThread = arraySize / numThreads;
        int[] numArray = new int[arraySize];
        int[] sums = new int[numThreads];

        public void run()
        {
            // Fill array with random numbers.
            Random random = new Random();

            for (int i = 0; i < arraySize; i++)
            {
                numArray[i] = random.nextInt(10) + 1;
            }

            long s = System.nanoTime();

            // Define worker class.
            class Worker implements Runnable
            {
                private final int startIdx;
                private final int endIdx;
                private final int threadNum;

                public Worker(int startIdx, int endIdx, int threadNum)
                {
                    this.startIdx = startIdx;
                    this.endIdx = endIdx;
                    this.threadNum = threadNum;
                }

                public void run()
                {
                    int sum = 0;

                    for (int i = startIdx; i < endIdx; i++)
                    {
                        sum += numArray[i];
                    }
                    sums[threadNum] = sum;
                }
            }

            // Create and start threads.
            Thread[] threads = new Thread[numThreads];

            for (int i = 0; i < numThreads; i++)
            {
                int startIdx = i * numbersPerThread;
                int endIdx = (i + 1) * numbersPerThread;
                threads[i] = new Thread(new Worker(startIdx, endIdx, i));
                threads[i].start();
            }

            // Wait for threads to finish.
            for (Thread thread : threads)
            {
                try
                {
                    thread.join();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            // Compute final sum.
            int finalSum = 0;

            for (int i = 0; i < numThreads; i++)
            {
                finalSum += sums[i];
            }
            long e = System.nanoTime();
            long nanoSec = e - s;

            // Display the sum and time.
            System.out.println("Using multiple threads the sum is: " + finalSum + " in " + nanoSec + " ns");
        }
    }

    // Compute the sum with only one thread.
    public static class SingleThread extends Thread
    {
        int sum = 0;
        int arraySize = 200000000;
        int[] numArray = new int[arraySize];

        public void run()
        {
            long s = System.nanoTime();
            Random random = new Random();

            for (int i = 0; i < arraySize; i++)
            {
                numArray[i] = random.nextInt(10) + 1;
                sum += numArray[i];
            }
            long e = System.nanoTime();
            long nanoSec = e - s;

            // Display the sum and time.
            System.out.println("Using a single thread the sum is: " + sum + " in " + nanoSec + " ns");
        }
    }

    public static void main(String[] args)
    {
        (new MultipleThreads()).start();
        (new SingleThread()).start();
    }
}
