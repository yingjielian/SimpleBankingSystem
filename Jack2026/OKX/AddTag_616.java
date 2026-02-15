package Jack2026.OKX;

//Input: s = "abcxyz123", words = ["abc","123"]
//        Output: "<b>abc</b>xyz<b>123</b>"
public class AddTag_616 {

    public static String addTag(String s, String[] words)
    {
        boolean[] bold = new boolean[s.length()];
        int boldEnd = 0;

        for(int i = 0; i < s.length(); i++)
        {
            for(String word : words)
            {
                if(s.startsWith(word, i))
                {
                    boldEnd = Math.max(boldEnd, i + word.length()); // KEY
                }
            }
            bold[i] = boldEnd > i; // KEY
        }

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            // Not false, original char
            if(!bold[i])
            {
                sb.append(c);
                continue;
            }

            if(i == 0 || bold[i] != bold[i - 1])
            {
                sb.append("<b>");
            }
            sb.append(c);

            if(i == s.length() - 1 || bold[i] != bold[i + 1])
            {
                sb.append("</b>");
            } //
        }
        return sb.toString();
    }

    public static void main(String[] args)
    {
        System.out.println(addTag("abcxyz123", new String[]{"abc","123"}));
    }
}
