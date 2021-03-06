package by.it.ikavalenka.jd02_02;

class Buyer extends Thread implements IBuyer {

    private int sin;

    Buyer(int sin) {
        super("Customer # " + sin);
        this.sin = sin;
    }

    @Override
    public String toString() {
        return getName() + " ";
    }

    @Override
    public void run() {
        enterToMarket();
        chooseGoods();
        goToQueue();
        goToOut();

    }

    @Override
    public void enterToMarket() {
        System.out.println(this + "entered in the market");
    }

    @Override
    public void chooseGoods() {
        System.out.println(this + " entered in the  shooping mall");
        int max = Helper.getRandom(1, 9);
        System.out.println(this + "  want to choose: " + max + " ex.");
        for (int i = 1; i <= max; i++) {
            int timeout = Helper.getRandom(100, 200);
            Helper.sleep(timeout);
            Good good = Goods.getRandomGood();
            System.out.println(
                    String.format("%s choose goods %s  price:%s r.",
                            this,
                            good.getName(),
                            good.getPrice())
            );
        }
        System.out.println(this + "  choosing goods was completed");
    }

    @Override
    public void goToOut() {
        System.out.println(this + "out from market");
        synchronized (Dispitcher.monitorCount) {
            Dispitcher.countComplete++;
        }
    }

    @Override
    public void goToQueue() {
        System.out.println(this + "Customer  stay in  the queue");
        BuyerQueue.add(this);
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this + "Service is completed");
        }
    }
}
