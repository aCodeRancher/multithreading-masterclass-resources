import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Exercise {
    private static final int NO_TRANSACTIONS = 1000;
    private static final int TRANSACTION_AMOUNT = 10;

    public static void main (String... args) throws InterruptedException{
         Exercise exercise = new Exercise();
         System.out.println("Final balance: "+ exercise.getFinalBalance());
    }

    public int getFinalBalance() throws InterruptedException {
        BankAccount account = new BankAccount();

        // Let's say we have 2 users which are using the same account
        User user1 = new User(account);
        User user2 = new User(account);

        Thread t1 = new Thread(() -> {
            // User 1 is adding money into the account
            for (int i = 0; i < NO_TRANSACTIONS; i++) {
                user1.getAccount().depositMoney(TRANSACTION_AMOUNT);
            }
        });

        Thread t2 = new Thread(() -> {
            // User 2 is withdrawing money into the account (in the same time)
            for (int i = 0; i < NO_TRANSACTIONS; i++) {
                user2.getAccount().withdrawMoney(TRANSACTION_AMOUNT);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        // At the end we should have the balance 0, right?
        return user1.getAccount().getBalance();
    }
}

class BankAccount {
    private int balance;
    private static int maxBalance = 1000 * 10;
    private int amountAvailableForWithdraw= 10;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public BankAccount() {
        balance = 0;
    }

    public void depositMoney(int amount) {
        lock.lock();
        //when the balance is max, wait
        if (balance==maxBalance){
            try {
                 condition.await();
            }
            catch (InterruptedException e) {}
        }
        balance += amount;
        System.out.println("Deposit: " + balance);
        //when the balance is bigger than available withdraw
        //amount $10, signal the withdrawing thread.
        if (balance >= amountAvailableForWithdraw)
            condition.signalAll();

        lock.unlock();


    }

    public void withdrawMoney(int amount) {
       lock.lock();
       //When there is no money, wait
       if (balance == 0){
           try {
               condition.await();
           }
           catch (InterruptedException e) {}
       }
       balance -= amount;
       System.out.println("Remaining: " + balance);
       //When the balance is less than max, signal the
        //depositing thread.
       if (balance < maxBalance)
            condition.signalAll();
       lock.unlock();
    }

    public int getBalance() {
        return balance;
    }
}

class User {
    private final BankAccount account;

    public User(BankAccount account) {
        this.account = account;
    }

    public BankAccount getAccount() {
        return account;
    }
}
