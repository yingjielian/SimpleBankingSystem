package Jack2025.OpenAI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Timeline-based simulation: for getBalance(t) we replay events (grants & subs)
 * up to t in chronological order, applying subtractions to earliest-expiring active grants.
 */
public class GrantBalance {

    static class Grant {
        String id;
        int amount;
        int grantTime;
        int expireTime;

        Grant(String id, int amount, int grantTime, int expireTime) {
            this.id = id;
            this.amount = amount;
            this.grantTime = grantTime;
            this.expireTime = expireTime;
        }
    }

    static class Sub {
        int amount;
        int time;

        Sub(int amount, int time) {
            this.amount = amount;
            this.time = time;
        }
    }

    private final List<Grant> grants = new ArrayList<>();
    private final List<Sub> subs = new ArrayList<>();

    public int getBalance(int timeStamp) {
        // Build list of events up to timeStamp
        class Event {
            int time;
            int type; // 0 = grant, 1 = sub (so grants at same time processed before subs — not critical but safe)
            Grant grant; // for type 0
            Sub sub;     // for type 1

            Event(int time, int type, Grant grant, Sub sub) {
                this.time = time;
                this.type = type;
                this.grant = grant;
                this.sub = sub;
            }
        }

        List<Event> events = new ArrayList<>();
        for (Grant g : grants) {
            if (g.grantTime <= timeStamp) events.add(new Event(g.grantTime, 0, g, null));
        }
        for (Sub s : subs) {
            if (s.time <= timeStamp) events.add(new Event(s.time, 1, null, s));
        }

        // sort events by time asc, and grants before subs at same time
        events.sort(Comparator.comparingInt((Event e) -> e.time).thenComparingInt(e -> e.type));

        // active grants: we will keep mutable copies with remaining amounts
        class Active {
            String id;
            int remain;
            int expire;

            Active(String id, int remain, int expire) {
                this.id = id;
                this.remain = remain;
                this.expire = expire;
            }
        }

        List<Active> active = new ArrayList<>();

        for (Event e : events) {
            int now = e.time;
            // remove expired grants at or before 'now' (expire <= now are not active)
            Iterator<Active> it = active.iterator();
            while (it.hasNext()) {
                Active a = it.next();
                if (a.expire <= now) it.remove();
            }

            if (e.type == 0) {
                // grant event: add to active (note: grant.expire might be <= now, in which case it is immediately expired)
                Grant g = e.grant;
                if (g.expireTime > now) {
                    active.add(new Active(g.id, g.amount, g.expireTime));
                }
            } else {
                // subtraction event: consume from active grants ordered by earliest expiration
                int remain = e.sub.amount;
                // sort active by expire asc
                active.sort(Comparator.comparingInt(a -> a.expire));
                for (Active a : active) {
                    if (remain <= 0) break;
                    if (a.remain <= 0) continue;
                    int take = Math.min(a.remain, remain);
                    a.remain -= take;
                    remain -= take;
                }
                // if remain > 0, that is effectively negative balance stored for future grants
                // Represent negative remainder as an artificial "debt" that will be applied by later subtractions handling:
                // We implement that by keeping a pseudo Active with very large expire (so it will be consumed by future grants),
                // but easier: store a pendingSub record so that future grants (when processed) will be considered in order.
                // However simpler and correct: create a "debt" entry that has expire = Integer.MAX_VALUE and negative remain.
                if (remain > 0) {
                    // Represent debt as negative remaining amount with infinite expiry so future grants can be applied
                    // when next grants are processed (they will be added and available to get consumed by subsequent subs if necessary).
                    // To ensure debt is considered earliest expiring, we set expire=Integer.MAX_VALUE.
                    // But we must keep debt as separate entry that will not be counted toward balance at final sum.
                    active.add(new Active("__DEBT__", -remain, Integer.MAX_VALUE));
                    // Note: when future grants are added, the algorithm (processing events in chronological order)
                    // will not automatically move debt to reduce them. To make debt consume future grants we must
                    // process all events in chronological order which we already do; the debt entry will be present
                    // and, when later a grant event arrives (at time G), we previously removed expired entries at that time,
                    // but debt has expire=Integer.MAX_VALUE so it stays. However, we also must ensure when a grant arrives,
                    // that we immediately apply existing debts to it. The loop handles events sequentially; after adding a grant
                    // we don't auto-consume existing negative debt unless a sub event happens. To handle this correctly,
                    // we will instead apply any existing debt immediately after a grant is added below.
                }
            }

            // After processing each event, if there exists any debt entries (remain < 0) and there exist positive active grants,
            // apply debt to newly added grants now (so debt consumes newly added grants that were active at debt time).
            // Find total debt and apply to positive grants (again by earliest expiry).
            // Compute totalDebt (positive number)
            int totalDebt = 0;
            for (Active a : active) if (a.remain < 0) totalDebt += -a.remain;
            if (totalDebt > 0) {
                // apply debt to positive grants ordered by expire asc
                active.sort(Comparator.comparingInt(a -> a.expire));
                for (Active a : active) {
                    if (totalDebt <= 0) break;
                    if (a.remain <= 0) continue; // skip debt or zero entries
                    int take = Math.min(a.remain, totalDebt);
                    a.remain -= take;
                    totalDebt -= take;
                }
                // remove or update debt entries to reflect remaining debt
                // first remove all existing debt entries
                int remainingDebt = totalDebt;
                Iterator<Active> it2 = active.iterator();
                List<Active> newDebts = new ArrayList<>();
                while (it2.hasNext()) {
                    Active a = it2.next();
                    if (a.remain < 0) it2.remove();
                }
                if (remainingDebt > 0) {
                    active.add(new Active("__DEBT__", -remainingDebt, Integer.MAX_VALUE));
                }
            }
        }

        // Final cleanup: remove expired at timeStamp (expire <= timeStamp)
        Iterator<Active> itFinal = active.iterator();
        while (itFinal.hasNext()) {
            Active a = itFinal.next();
            if (a.expire <= timeStamp) itFinal.remove();
        }

        // Sum positive remains only (ignore any debt negative entries)
        int sum = 0;
        for (Active a : active) {
            if (a.remain > 0) sum += a.remain;
        }

        return Math.max(sum, 0);
    }

    public void substract(int amount, int timeStamp) {
        subs.add(new Sub(amount, timeStamp));
    }

    public void grant(String id, int amount, int timeStamp, int expirationTimeStamp) {
        grants.add(new Grant(id, amount, timeStamp, expirationTimeStamp));
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        System.out.println("✅ All tests passed!");
    }

    public static void test1()
    {
        GrantBalance g = new GrantBalance();
        g.substract(1, 30);
        g.grant("a", 2, 20, 100);

        assertEquals(2, g.getBalance(20));
        assertEquals(1, g.getBalance(30));
        assertEquals(0, g.getBalance(100));
    }

    public static void test2()
    {
        GrantBalance g = new GrantBalance();
        g.substract(1, 10);
        g.grant("a", 2, 30, 100);

        // Even though the subtraction happens before grant, we will show the user
        // non-negative balance. However, the did want to store the negative number
        // for future computation.
        assertEquals(0, g.getBalance(20));
        assertEquals(1, g.getBalance(30));
        assertEquals(0, g.getBalance(100));
    }

    public static void test3()
    {
        GrantBalance g = new GrantBalance();
        g.substract(1, 30);
        g.grant("a", 3, 20, 60);
        g.grant("b", 2, 10, 40);

        assertEquals(5, g.getBalance(20));
        // Subtract the amount from "b" first, because the granted amount will
        // expire earlier than "a".
        assertEquals(4, g.getBalance(30));

        // Since "b" amount expires at 40, we only have 3 available from "a"
        assertEquals(3, g.getBalance(40));
        assertEquals(0, g.getBalance(60));
        assertEquals(0, g.getBalance(100));
    }

    public static void test4()
    {
        GrantBalance g = new GrantBalance();
        g.substract(1, 30);
        g.substract(2, 50);
        g.substract(6, 80);
        g.grant("a", 3, 20, 60);
        g.grant("b", 2, 10, 40);
        g.grant("c", 5, 70, 100);

        assertEquals(5, g.getBalance(20));
        // Subtract the amount from "b" first, because the granted amount will
        // expire earlier than "a".
        assertEquals(4, g.getBalance(30));

        // Since "b" amount expires at 40, we only have 3 available from "a"
        assertEquals(3, g.getBalance(40));
        assertEquals(1, g.getBalance(50));
        assertEquals(0, g.getBalance(60));

        assertEquals(5, g.getBalance(70));
        assertEquals(0, g.getBalance(80));
        assertEquals(0, g.getBalance(100));

    }

}
