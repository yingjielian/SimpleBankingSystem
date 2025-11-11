package Jack2025.Adobe;

public class MultithreadThing implements Runnable{

    private int threadNumber;

    public MultithreadThing(int threadNumber)
    {
        this.threadNumber = threadNumber;
    }
    @Override
    public void run() {
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
}
