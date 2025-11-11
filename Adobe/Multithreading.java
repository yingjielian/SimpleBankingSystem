package Jack2025.Adobe;

public class Multithreading extends Thread{
    private int threadNumber;

    public Multithreading(int threadNumber)
    {
        this.threadNumber = threadNumber;
    }
    @Override
    public void run()
    {
        for(int i = 1; i <= 5; i++)
        {
            System.out.println(i + " from thread: " + threadNumber);
//            if(threadNumber == 3)
//            {
//                throw new RuntimeException();
//            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) throws RuntimeException, IllegalArgumentException
    {

        for(int i = 0; i < 5; i++)
        {
            Multithreading myThing = new Multithreading(i);
            MultithreadThing myThing2 = new MultithreadThing(i);
            Thread myThread =  new Thread(myThing2);
            try {
                myThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            myThread.start();
        }
//        Multithreading myThing = new Multithreading();
//        Multithreading myThing2 = new Multithreading();
//        myThing.start();
//        myThing2.start();
    }
}
