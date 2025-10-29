package Jack2025.Adobe;

import java.util.LinkedList;
import java.util.Queue;

class TreeNode{
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) {val = x;}
}
public class SerializeandDeserializeBinaryTree_297 {

    // Encodes a tree to a single string
    public String serialize(TreeNode root)
    {
        if(root == null) return "";

        StringBuilder res = new StringBuilder();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while(!queue.isEmpty())
        {
            TreeNode node = queue.poll();
            if(node == null)
            {
                res.append("null ");
                continue;
            }

            res.append(node.val + " ");

            queue.offer(node.left);
            queue.offer(node.right);
        }

        return res.toString();
    }

    // Decodes your encoded data to Tree
    public TreeNode deserialize(String data)
    {
        if(data == null || data.length() == 0) return null;
        String[] str = data.split(" ");
        TreeNode root = new TreeNode(Integer.parseInt(str[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        for(int i = 1; i < str.length; i++)
        {
            TreeNode node = queue.poll();

            if(!str[i].equals("null"))
            {
                node.left = new TreeNode(Integer.parseInt(str[i]));
                queue.offer(node.left);
            }

            i++;

            if(!str[i].equals("null"))
            {
                node.right = new TreeNode(Integer.parseInt(str[i]));
                queue.offer(node.right);
            }
        }

        return root;

    }

}
