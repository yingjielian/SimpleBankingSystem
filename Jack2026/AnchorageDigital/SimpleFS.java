package Jack2026.AnchorageDigital;

public class SimpleFS {

    private static final int BLOCK_DATA_SIZE = 8;

    public static class Block{
        private String data;
        private Block next;

        private Block(){};

        public boolean isEmpty()
        {
            return data == null || data.isEmpty();
        }

        public String getData()
        {
            return data;
        }

        public void setData(String data)
        {
            if(data == null)
            {
                this.data = null;
                return;
            }

            if(data.length() != BLOCK_DATA_SIZE)
            {
                throw new IllegalArgumentException(
                        "Block must contain exactly " + BLOCK_DATA_SIZE + " chars, got " + data.length());
            }
            this.data = data;
        }

        public void clear()
        {
            this.data = null;
        }
    }

    private final int size;
    private final Block head;

    public SimpleFS(int numBlocks)
    {
        if(numBlocks <= 0) throw new IllegalArgumentException("numBlocks must be > 0");
        this.size = numBlocks;

        Block first = new Block();
        Block cur = first;

        for(int i = 1; i< numBlocks; i++)
        {
            Block nxt = new Block();
            cur.next = nxt;
            cur = nxt;
        }
        this.head = first;
    }

    public void print()
    {
        StringBuilder sb = new StringBuilder();
        Block cur = head;

        while(cur != null)
        {
            if(cur.isEmpty()) sb.append("[]");
            else sb.append("[").append(cur.getData()).append("]");

            cur = cur.next;
            if(cur != null) sb.append(" -> ");
        }

        System.out.println(sb);
    }

    public Block getBlock(int index)
    {
        if(index < 0 || index >= size) throw new IndexOutOfBoundsException("index=" + index);
        Block cur = head;
        for(int i = 0; i < index; i++) cur = cur.next;

        return cur;
    }

    public static void main(String[] args) {
        SimpleFS fs = new SimpleFS(3);
        fs.print(); // [] -> [] -> []

        fs.getBlock(1).setData("ABCDEFGH");
        fs.print(); // [] -> [ABCDEFGH] -> []

        fs.getBlock(1).clear();
        fs.print(); // [] -> [] -> []
    }
}
