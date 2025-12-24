package Jack2025.UpStart;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeTheString {

    public static List<String> timeTheString(List<String> list, int multiplier)
    {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");

        for(String s : list)
        {
            Matcher matcher = pattern.matcher(s);
            StringBuilder sb = new StringBuilder();

            while(matcher.find())
            {
                int num = Integer.parseInt(matcher.group());
                int newNum = num * multiplier;
                matcher.appendReplacement(sb, String.valueOf(newNum));
            }
            matcher.appendTail(sb);
            result.add(sb.toString());
        }
        return result;
    }

    public static void main(String[] agrs)
    {
        List<String> test = new ArrayList<>();
        test.add("I have 3 apples and 4 oranges.");
        test.add("He has 2 bananas.");
        System.out.println(test);
        System.out.println(timeTheString(test, 3));

        System.out.println(Integer.parseInt("3"));
    }
}
