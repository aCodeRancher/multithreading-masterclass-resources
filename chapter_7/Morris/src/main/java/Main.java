import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static ReentrantLock lock = new ReentrantLock();
    private static int room1, room2;
    private static int counter;
    private static Semaphore s1 = new Semaphore(1);
    private static Semaphore s2 = new Semaphore (0);

    public static void main (String... args){

        for (int i=0;i<10;i++){
             new Thread(new Worker(i)).start();
        }
    }

    static class Worker implements Runnable{

        private int id;

        Worker(int id){
            this.id = id;
        }
        @Override
        public void run(){
            while (true) {
                enterRoom(room1);
                acquire(s1);
                    room2++;
                    leaveRoom(room1);
                    if (room1 == 0) {
                       s2.release();
                    } else {
                        s1.release();
                     }
                acquire(s2);
                room2--;
                //critical section
                addCounter();
                if (room2 == 0) {
                    s1.release();
                } else {
                    s2.release();
                }
            }
        }

        private void addCounter(){
            counter++;
            System.out.println("Thread "+ id + " count "+  counter);
            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        private void acquire(Semaphore semaphore){
            try {
                semaphore.acquire();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        private void enterRoom(int threadsInRoom){
            lock.lock();
           // System.out.println("Thread " + id + " enters waiting room ");
            threadsInRoom++;
            lock.unlock();
        }
        private void leaveRoom(int threadsInRoom){
            lock.lock();
           // System.out.println("Thread " + id + " leaves waiting room ");
            threadsInRoom--;
            lock.unlock();
        }
    }
}
