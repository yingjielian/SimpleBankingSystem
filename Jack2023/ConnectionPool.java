package Jack2023;

import java.util.ArrayList;
import java.util.List;

class Database {
    String name;
    String ip;
    int id;

    public Database(String name, String ip) {
        this.name = name;
        this.ip = ip;
        this.id = 0;
    }
}
public class ConnectionPool {
    private List<Database> pool;
    private int idCounter;
    private int at;

    public ConnectionPool() {
        pool = new ArrayList<>();
        idCounter = 1;
        at = 0;
    }

    public static void main(String[] args) {
        ConnectionPool test = new ConnectionPool();
        test.addDB(new Database("hackerrank", "127.0.0.1"));
        test.addDB(new Database("tempSite", "127.0.0.1"));
        test.connect("tempSite");
        test.connect("");
        test.disconnect("tempSite");
        test.connect("");
    }

    public void addDB(Database newDB) {
        newDB.id = idCounter;
        idCounter++;
        pool.add(newDB);
    }

    public Database connect(String serviceName) {
        Database current = pool.get(at);
        at = (at + 1) % pool.size();
        return current;
    }

    public void disconnect(String serviceName) {
        // You can implement disconnect logic here if needed
    }
}
