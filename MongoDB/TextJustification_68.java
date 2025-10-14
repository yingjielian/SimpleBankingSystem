package Jack2025.MongoDB;
import java.util.*;
public class TextJustification_68 {
    public static List<String> fullJustify(String[] words, int maxWidth)
    {
        List<String> res = new ArrayList<>();
        List<String> currLine = new ArrayList<>();

        int length = 0, i = 0;

        while(i < words.length)
        {
            // If the current word can fit in the line
            if(length + words[i].length() + currLine.size() <= maxWidth)
            {
                currLine.add(words[i]);
                length += words[i].length();
                i++;
            }
            else
            {
                // Line complete
                int extra_space = maxWidth - length;
                int reminder = extra_space % Math.max(1, currLine.size() - 1);
                int space = extra_space / Math.max(1, currLine.size() - 1);

                for(int j = 0; j < Math.max(1, currLine.size() - 1); j++)
                {
                    currLine.set(j, currLine.get(j) + " ".repeat(space));
                    if(reminder > 0)
                    {
                        currLine.set(j, currLine.get(j) + " ");
                        reminder--;
                    }
                }

                res.add(String.join("", currLine));
                currLine.clear();
                length = 0;
            }
        }

        // Handling last line
        String last_line = String.join(" ", currLine);
        int trail_space = maxWidth - last_line.length();
        res.add(last_line + " ".repeat(trail_space));

        return res;
    }

    public static void main(String[] args) {
        // Example 1
        String[] words1 = {"This", "is", "an", "example", "of", "text", "justification."};
        int maxWidth1 = 16;
        List<String> result1 = fullJustify(words1, maxWidth1);
        System.out.println("Example 1 Output:");
        for (String line : result1) {
            System.out.println("\"" + line + "\"");
        }

        System.out.println();

        // Example 2
        String[] words2 = {"What","must","be","acknowledgment","shall","be"};
        int maxWidth2 = 16;
        List<String> result2 = fullJustify(words2, maxWidth2);
        System.out.println("Example 2 Output:");
        for (String line : result2) {
            System.out.println("\"" + line + "\"");
        }
    }
}
