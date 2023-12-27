public class Main {
    public static void main(String[] args) {
        int numThreads = 3;
        Barrier barrier = new Barrier(numThreads);

        for (int i = 1; i <= numThreads; i++) {
            final int threadId = i; // Create a unique thread ID for each thread
            new Thread(() -> {
                System.out.println("Thread " + threadId + " is running its code.");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    barrier.await2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread " + threadId + " passed the barrier.");
            }).start();
        }
    }
}
