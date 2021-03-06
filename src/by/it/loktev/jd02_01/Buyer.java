package by.it.loktev.jd02_01;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Buyer extends Thread implements IBuyer, IUseBacket {

    private int num;
    public int queueNum;

    public boolean isPensioner;
    private double timeMul; // коэффициент увеличения времени на все операции

    private Map<Good,Double> backet;

    public Buyer(int num) {
        super("Покупатель №"+num);
        this.num = num;
        this.isPensioner=(Helper.getRandom(3)==0);
        this.timeMul=this.isPensioner?1.5:1;
        backet=new HashMap<>();
    }

    @Override
    public void enterToMarket() {
        System.out.println(this+" зашёл в магазин");
        Helper.sleep(Helper.getRandom(400,700)*timeMul);
    }

    @Override
    public void goToOut() {
        Helper.sleep(Helper.getRandom(400,700)*timeMul);
        System.out.println(this+" вышел из магазина");
    }

    @Override
    public void chooseGoods() {
        System.out.println(this+" вошёл в зал");
        int goodsCount=Helper.getRandom(1,4);
        for (int g=1; g<=goodsCount; g++){
            Helper.sleep(Helper.getRandom(400,700)*timeMul);
            Good good=Goods.getRandomGood();
            double quantity=Helper.getRandom(1,3);
            putGoodToBacket(good,quantity);
            System.out.println(this+" выбрал товар "+good.getName()+" ценой "+good.getPrice()+" "+quantity+" штук");
        }
        Helper.sleep(Helper.getRandom(400,700)*timeMul);
        /*
        double backetPrice= getBacketPrice();
        synchronized (Shop.class) {
            Shop.totalPrice2 += backetPrice;
        }
        */
        System.out.println(this+" пошёл на кассу");
    }

    @Override
    public void takeBacket() {
        Helper.sleep(Helper.getRandom(100,200)*timeMul);
        System.out.println(this+" взял корзину");
    }

    @Override
    public void backBacket() {
        Helper.sleep(Helper.getRandom(100,200)*timeMul);
        System.out.println(this+" вернул корзину");
    }

    @Override
    public void putGoodToBacket(Good good, double quantity) {
        if ( backet.containsKey(good) ){
            quantity+=backet.get(good);
        }
        backet.put(good,quantity);
    }

    @Override
    public double getBacketPrice() {
        double price=0;
        for ( Map.Entry<Good,Double> goodEntry : backet.entrySet() ){
           price+=goodEntry.getKey().getPrice()*goodEntry.getValue();
        }
        return price;
    }

    public Set<Map.Entry<Good,Double>> getBucket(){
        return backet.entrySet();
    }

    @Override
    public void gotoQueue() {
        BuyersQueue.add(this);
        System.out.println(this+" стал в очередь");
        synchronized (this){
           try {
               this.wait();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
       System.out.println(this+" завершил обслуживание в очереди");
    }

    @Override
    public void run() {
        synchronized (Shop.class) {
            Shop.buyersCount2++;
        }
        enterToMarket();
        takeBacket();
        chooseGoods();
        gotoQueue();
        backBacket();
        goToOut();
        synchronized (Shop.class) {
            Shop.buyersCount2--;
        }
    }

    @Override
    public String toString() {
        if (isPensioner)
            return "Покуп/ПЕНС №"+num;
        else
            return "Покупатель №"+num;
    }
}
