package by.it.ikavalenka.jd02_02;

/**
 * Created by USER on 31.03.2017.
 */
public class Cashier implements Runnable{
        private String name;

    public Cashier(int number) {
        name ="Cashier #"+number+" ";
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void run() {
        Buyer b;
        System.out.println(this+"Select case");
        while ((b=BuyerQueue.extract())!=null){

            System.out.println(this+"Start of service"+b);
            int timeout  = Helper.getRandom(2000, 5000);
            Helper.sleep(timeout);
            synchronized (b){
                b.notify();
            }
            System.out.println(this+"Complete service for customer"+b);
        }
        System.out.println(this+"closed case");
        Dispitcher.cashierOne.remove(Thread.currentThread());
        System.out.println("Leaved cashier"+Dispitcher.cashierOne.size());

    }
}
