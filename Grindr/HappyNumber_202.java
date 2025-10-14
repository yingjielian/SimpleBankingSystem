package Jack2025.Grindr;

import java.util.*;

public class HappyNumber_202 {
    static public boolean isHappy(int n)
    {
        Set<Integer> set = new HashSet<>();

        while(n != 1)
        {
            int temp = 0;

            while(n != 0)
            {
                int digit = n % 10;

                temp += digit * digit;
                n /= 10;
            }

            if(!set.add(temp))
            {
                return false;
            }
            n = temp;
        }

        return true;
    }

    public static void main(String[] args)
    {
        isHappy(19);
    }
}
