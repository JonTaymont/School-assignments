// Jonathan Taymont
// 2/27/23
// CEN 3024C
//
// Module 5 Assignment
// Part I.
// Implement the Fibonacci function in both a recursive and iterative fashion. What’s the runtime efficiency of each?
// You can look up sample programs - there are many out there - but for your own experience, please type yours
// from scratch. Turn in a chart of the results, with time on the Y axis, and input on the X axis, Please use
// nanosecond, long startTime = System.nanoTime(); This chart must not be handwritten.
// Part II.
// Create documentation for your program. Use the best practices that you have learned in this module.

public class Main
{
    public static class RecursiveFibonacci extends Thread
    {
        public Integer n = 0;
        int fibonacci(int n)
        {
            if (n == 0)
            {
                return 0;
            }
            if (n == 1)
            {
                return 1;
            }
            return fibonacci(n - 1) + fibonacci(n - 2);
        }

        public void run()
        {
            int m = (int) System.nanoTime();

            n = fibonacci(40);

            int nt = (int) System.nanoTime();

            int nanoSeconds = nt - m;

            System.out.println("Recursion Thread found the answer: " + n + " in " + nanoSeconds + " ns");
        }
    }

    public static class IterativeFibonacci extends Thread
    {
        public Integer n = 0;
        public int fibonacci(int n)
        {
            int v1 = 0;
            int v2 = 1;
            int v3 = 0;

            for(int i = 2; i <= n; i++)
            {
                v3 = v1 + v2;
                v1 = v2;
                v2 = v3;
            }
            return v3;
        }

        public void run()
        {
            int m = (int) System.nanoTime();

            n = fibonacci(40);

            int ns = (int) System.nanoTime();

            int nanoSeconds = ns - m;

            System.out.println("Iterative Thread found the answer: " + n + " in " + nanoSeconds + " ns");
        }
    }

    public static void main(String[] args)
    {
        (new RecursiveFibonacci()).start();
        (new IterativeFibonacci()).start();
    }
}
