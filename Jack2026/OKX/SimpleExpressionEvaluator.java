package Jack2026.OKX;
import java.util.*;
public class SimpleExpressionEvaluator {

    // # exprStr: "a > 10 && b == 0 || c < b"
    //
    //
    //# context: {"a": 12, "b": 0, "c": -1}
    //# return: true
    //
    //# context: {"a": 12, "b": 2, "c": 3}
    //# return: false
    //# Follow up 1:
    //# - support bracket
    //# "a > 10 && (b == 0 || c < b)"
    //
    //
    //# Follow up 2:
    //# - support NOT, BETWEEN
    //
    //# Follow up 3:
    //# - support different data types?
    public static boolean evaluate(String expr, Map<String, Integer> context)
    {
        // # Follow up 1-a:
        //# - what if there are no spaces between tokens?
        String normalized = expr
                .replace(">", " > ")
                .replace("<", " < ")
                .replace("==", " == ")
                .replace("&&", " && ")
                .replace("||", " || ");

        String[] allTokens = expr.split("\\s+");
        List<String> tokens = new ArrayList<>();

        for(String s : allTokens)
        {
            if(!s.isEmpty()) tokens.add(s);
        }

        boolean result = execCompare(tokens.get(0), tokens.get(1), tokens.get(2), context);

        for(int i = 3; i + 3 <= tokens.size(); i += 4)
        {
            String logicoP = tokens.get(i);

            boolean nextVal = execCompare(tokens.get(i + 1), tokens.get(i + 2), tokens.get(i + 3), context);

            if(logicoP.equals("&&"))
            {
                result = result && nextVal;
            } else if (logicoP.equals("||")) {
                result = result || nextVal;
            }
        }

        return result;
    }

    private static boolean execCompare(String left, String op, String right, Map<String, Integer> context)
    {
        int leftValue = getValue(left, context);
        int rightValue = getValue(right, context);

        if(">".equals(op))
        {
            return leftValue > rightValue;
        }
        else if("<".equals(op))
        {
            return leftValue < rightValue;
        } else if ("==".equals(op)) {
            return leftValue == rightValue;
        }
        return false;
    }

    private static int getValue(String token, Map<String, Integer> context)
    {
        try{
            return Integer.parseInt(token);
        } catch(NumberFormatException e)
        {
            Integer val = context.get(token);
            return (val == null) ? 0 : val;
        }
    }
}
