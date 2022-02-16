import java.util.concurrent.Exchanger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();

        Thread t = new Thread(() -> {
            try {
                Thread.currentThread().setName("Alice");
                String receivedValue = exchanger.exchange("Hello Bob");
                System.out.println("Received: " + receivedValue + " in thread " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t1 = new Thread(()->{
             try{
                 Thread.currentThread().setName("Bob");
                 String receivedValue = exchanger.exchange("Hi Alice");
                 System.out.println("Received: "+ receivedValue + " in thread " + Thread.currentThread().getName());
             }
             catch(InterruptedException e){
                 e.printStackTrace();
             }
        });



        t.start();
        t1.start();

        String receivedValue = exchanger.exchange("Good morning classmates");
        System.out.println("Received: " + receivedValue + " in thread " + Thread.currentThread().getName());
    }
}
