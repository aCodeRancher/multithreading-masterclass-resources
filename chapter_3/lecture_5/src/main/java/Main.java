import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();

    private static List<Integer> list = new ArrayList<>();
    private static  Logger logger = Logger.getLogger("ReadWriteLock");
    private static FileHandler handler ;

    public static void main(String[] args) {

       try {
            handler = new FileHandler("write.log", true);
            logger.addHandler(handler);
        }
        catch (Exception e){
               e.printStackTrace();
        }
        Thread writer = new Thread(new WriterThread());

        Thread reader1 = new Thread(new ReaderThread());
        Thread reader2 = new Thread(new ReaderThread());
        Thread reader3 = new Thread(new ReaderThread());
        Thread reader4 = new Thread(new ReaderThread());

        writer.start();
        reader1.start();
        reader2.start();
        reader3.start();
        reader4.start();
    }

    static class WriterThread implements Runnable {

       @Override
        public void run() {

            while(true) {
                try {
                    writeData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void writeData() throws InterruptedException {
            Thread.sleep(10000);

            boolean acquired = writeLock.tryLock();
            while (true) {
                if (acquired) {
                     break;
                }
                else{
                    logger.log(Level.INFO,"Producer waits for write lock.");
                }
            }
            int value = (int) (Math.random() * 10);
            logger.log(Level.INFO, "Producing data: "+ value);
            Thread.sleep(3000);

            list.add(value);

            writeLock.unlock();
        }
    }

    static class ReaderThread implements Runnable {

        @Override
        public void run() {

            while(true) {
                try {
                    readData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void readData() throws InterruptedException {
            Thread.sleep(3000);

            while (true) {
                boolean acquired = readLock.tryLock();
                if (acquired) {
                    break;
                } else {
                     logger.log(Level.INFO,"Waiting for read lock...");
                }
            }

            logger.log(Level.INFO, "List is: "+ list);
            readLock.unlock();
        }
    }
}
