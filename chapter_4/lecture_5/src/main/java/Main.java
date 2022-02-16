import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {

        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();
        Thread t1 = new Thread(new Player(lock1, lock2));
        Thread t2 = new Thread(new Player(lock2, lock1));
        t1.start();
        t2.start();

    }
}
    class Player implements Runnable{
        ReentrantLock locka;
        ReentrantLock lockb;

        public Player(ReentrantLock lock1, ReentrantLock lock2){
             this.locka = lock1;
             this.lockb = lock2;
        }

        @Override
        public void run(){
            Random rand = new Random();
            int interval = 1000;
            locka.lock();
            System.out.println(Thread.currentThread().getName() + " gain its lock.");
            while (lockb.isLocked()) {
                System.out.println(Thread.currentThread().getName() + " release its lock to step aside.");
                locka.unlock();
                try {
                    Thread.sleep(rand.nextInt(interval));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " gain its lock again.");
                locka.lock();

            }
            System.out.println(Thread.currentThread().getName() + " release another lock if it is locked.");
            if (lockb.isLocked())
                 lockb.unlock();
        }
    }

