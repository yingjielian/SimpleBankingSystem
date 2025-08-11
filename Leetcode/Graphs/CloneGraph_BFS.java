package Jack2025.Leetcode.Graphs;
import java.util.*;
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
public class CloneGraph_BFS {

    public Node cloneGraph(Node node) {
        if(node == null)
        {
            return null;
        }

        Node graph = new Node(node.val);

        Map<Node, Node> map = new HashMap<>();
        map.put(node, graph);

        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        while(!queue.isEmpty())
        {
            Node cur = queue.poll();
            for(Node curNeighbor : cur.neighbors)
            {
                if(!map.containsKey(curNeighbor))
                {
                    map.put(curNeighbor, new Node(curNeighbor.val));
                    queue.offer(curNeighbor);
                }
                map.get(cur).neighbors.add(map.get(curNeighbor));
            }
        }

        return graph;

    }
}
