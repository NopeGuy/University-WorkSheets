class Increment implements Runnable {
  private int threadNumber;

  public Increment(int threadNumber) {
    this.threadNumber = threadNumber;
  }

  @Override
  public void run() {
    final long I = 100;

    for (long i = 1; i <= I; i++) {
      System.out.println(i + " from thread " + threadNumber);
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

  }
}