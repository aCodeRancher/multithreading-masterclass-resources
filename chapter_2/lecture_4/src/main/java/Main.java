public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new MyThread(5, 20), "Thread1");
        Thread thread2 = new Thread(new MyThread(1,1), "Thread2");

        thread1.setDaemon(true);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    static class MyThread implements Runnable {
        private final int numberOfSeconds;
        private final int childthreadwaittime;
        public MyThread(int numberOfSeconds, int childthreadwaittime) {

            this.numberOfSeconds = numberOfSeconds;
            this.childthreadwaittime = childthreadwaittime;
        }

        @Override
        public void run() {
            for (int i = 0; i < numberOfSeconds; i++) {
                try {
                    System.out.println("Sleeping for 1s, thread: " + Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName()+ " is daemon ? " +Thread.currentThread().isDaemon());
                    Thread.sleep(1000);
                    Thread childThread =
                             new Thread(new MyThread(childthreadwaittime,0),"child thread of " + Thread.currentThread().getName());
                    childThread.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
