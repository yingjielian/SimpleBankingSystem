package Jack2025.Adobe;

public class ExceptionHandling {

    public static void main(String[] args)
    {
        try{
            int myInt = Integer.parseInt("12");
            System.out.println(myInt);
            return;
        }
        catch(NumberFormatException | NullPointerException | NegativeArraySizeException e)
        {
            System.out.println(e.fillInStackTrace());
        }
        finally {
            System.out.println("Haha");
        }

        System.out.println("Test it works");
    }
}
