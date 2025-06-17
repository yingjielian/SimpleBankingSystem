package Jack2025.Tiktok;

import java.math.BigInteger;

public class FactorialTrailingZeroes_172 {

    public int trailingZeroes(int n)
    {
        //        if(n == 0)
//        {
//            return 0;
//        }
//
//        long number = 1;
//
//        for(int i = 1; i <= n; i++)
//        {
//            number *= i;
//        }
//
//        int zeros = 0;
//
//        while(number % 10 == 0 )
//        {
//            number /= 10;
//            zeros++;
//        }

        BigInteger factorial = BigInteger.ONE;

        for(int i = 2; i <= n; i++)
        {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }

        int count = 0;

        while(factorial.mod(BigInteger.TEN) == BigInteger.ZERO)
        {
            factorial = factorial.divide(BigInteger.TEN);
            count++;
        }
        return count;
    }
}
