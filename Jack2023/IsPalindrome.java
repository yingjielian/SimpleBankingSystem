package Jack2023;

public class IsPalindrome {
    public static void main(String[] args) {
        System.out.println(isPalindrome("Was it a car or a cat I saw?"));
    }

    public static boolean isPalindrome(String s)
    {
        if (s.length() <= 1)
        {
            return false;
        }

        int head = 0, tail = s.length() - 1;


        while(head <= tail)
        {
            if(!Character.isLetterOrDigit(s.charAt(head)))
            {
                head++;
            } else if (!Character.isLetterOrDigit(s.charAt(tail))) {
                tail--;
            }
            else{
                if(Character.toLowerCase(s.charAt(head)) != Character.toLowerCase(s.charAt(tail)))
                {
                    return false;
                }
                head++;
                tail--;
            }
        }

        return true;
    }
}
