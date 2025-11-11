package Jack2025.EvenUp;

import java.util.List;
import java.util.stream.Collectors;

public class CompressString_443 {

    public static String compressString(String input, String skipChar)
    {
        if(input == null || input.length() == 0) return "";
        if(input.length() == 1) return "1" + input;
        StringBuilder result = new StringBuilder();
        char pre = input.charAt(0);
        int count = 1;
        List<Character> charList = skipChar.chars()
                                            .mapToObj(c -> (char) c)
                                            .collect(Collectors.toList());



        for(int i = 1; i < input.length(); i++)
        {
            char cur = input.charAt(i);
            if(charList.contains(cur)) continue;
            if(pre != cur)
            {
                result.append(count + "" + pre + " ");
                count = 1;
                pre = cur;
                continue;
            }
            count++;
            pre = cur;
        }

        char lastChar = input.charAt(input.length() - 1);

        if(!charList.contains(lastChar))
        {
            result.append(count + "" + lastChar);
        }
        else {
            result.append(count + "" + pre);
        }


        return result.toString();
    }

    public static void main(String[] args)
    {
//        String input = "aabbb";
//        StringBuilder result = new StringBuilder();
//        result.append(1 + input.charAt(2));
        System.out.println(compressString("aaBBBC", "B"));
        System.out.println(compressString("", ""));
        System.out.println(compressString("66667799990", "90"));
        System.out.println(compressString("V", ""));
    }
}
