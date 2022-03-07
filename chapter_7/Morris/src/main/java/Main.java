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
                 prepareForCS();
                //critical section
                addCounter();
                finishCS();
            }
        }

        private void prepareForCS(){
            //one or multiple threads enter room 1
            enterRoom(room1);
            //current thread acquires the semaphore s1
            //other threads should wait
            acquire(s1);
            //leave room 1 and enter room 2
            room2++;
            leaveRoom(room1);
            if (room1 == 0) {
                //if waiting room1 is empty, current thread releases s2
                //At this moment, current thread still holds s1. Current
                //thread can acquire s2 right away.
                s2.release();
            } else {
                //when other threads are waiting in room 1, current thread releases s1
                //another thread can acquire s1, enter room 2
                 s1.release();
            }
            //acquire s2 only when room 1 is empty.
            //when s2 is being acquired by a thread , others wait here.
            acquire(s2);
            //leave room 2 and go to critical session
            //at this moment, only one thread can go to critical session
            //other threads are waiting
            room2--;
        }

        private void finishCS(){
            //when room 2 is empty, current thread releases s1.
            //It is because current thread needs to
            //release s1 to finish.
            //when room 2 still have other threads waiting, current thread releases s2
            //once s2 is released, other threads in prepareForCS() can acquire s2 and leave s2
            if (room2 == 0) {
                s1.release();
            } else {
                s2.release();
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

            //only one thread can enter room at a time
            lock.lock();
            threadsInRoom++;
            lock.unlock();
        }
        private void leaveRoom(int threadsInRoom){
            //only one thread can leave the room at a time
            lock.lock();
           threadsInRoom--;
            lock.unlock();
        }
    }
}
