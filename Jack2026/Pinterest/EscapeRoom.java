package Jack2026.Pinterest;

import java.util.*;

/**
 * Real-time leaderboard for sequential escape rooms.
 *
 * Ordering for getTopParticipants(k):
 *  - Higher room index first (more progress)
 *  - If same room, earlier entry into that room first
 *
 * Time complexity (worst-case):
 *  - registerParticipant: O(log R)
 *  - increment: O(log R)
 *  - countParticipantsInRoom: O(1)
 *  - getTopParticipants(k): O((roomsVisited) * log R + k)
 *    where roomsVisited is the number of non-empty rooms you actually touch.
 */
class EscapeRoom {

    private static final class Node {
        final String id;
        int room;
        Node prev;
        Node next;

        Node(String id, int room) {
            this.id = id;
            this.room = room;
        }
    }

    private final int numRooms;
    private final int maxParticipants;

    private int totalParticipants = 0;

    // participantId -> node (node sits in exactly one room list)
    private final Map<String, Node> byId = new HashMap<>();

    // Doubly-linked list head/tail per room, in entry order
    private final Node[] head;
    private final Node[] tail;

    // Counts per room
    private final int[] roomCount;

    // Rooms that currently have >= 1 participant (for fast top queries)
    private final TreeSet<Integer> nonEmptyRooms = new TreeSet<>();

    public EscapeRoom(int numRooms, int maxParticipants) {
        this.numRooms = Math.max(0, numRooms);
        this.maxParticipants = Math.max(0, maxParticipants);

        this.head = new Node[this.numRooms];
        this.tail = new Node[this.numRooms];
        this.roomCount = new int[this.numRooms];
    }

    public void registerParticipant(String participantId) {
        if (participantId == null) return;
        if (byId.containsKey(participantId)) return;
        if (totalParticipants >= maxParticipants) return;
        if (numRooms <= 0) return; // no valid room 0

        Node node = new Node(participantId, 0);
        byId.put(participantId, node);
        totalParticipants++;

        appendToRoomList(0, node);
    }

    public boolean increment(String participantId) {
        if (participantId == null) return false;
        Node node = byId.get(participantId);
        if (node == null) return false;

        int cur = node.room;
        if (cur < 0 || cur >= numRooms) return false;
        if (cur == numRooms - 1) return false; // already in last room

        // Remove from current room list
        removeFromRoomList(cur, node);

        // Move to next room and append (entry time is now)
        int nextRoom = cur + 1;
        node.room = nextRoom;
        appendToRoomList(nextRoom, node);

        return true;
    }

    public int countParticipantsInRoom(int room) {
        if (room < 0 || room >= numRooms) return 0;
        return roomCount[room];
    }

    public String[] getTopParticipants(int k) {
        if (k <= 0 || totalParticipants == 0) return new String[0];

        int want = Math.min(k, totalParticipants);
        String[] res = new String[want];
        int idx = 0;

        for (Integer room : nonEmptyRooms.descendingSet()) {
            Node cur = head[room];
            while (cur != null && idx < want) {
                res[idx++] = cur.id;
                cur = cur.next;
            }
            if (idx >= want) break;
        }

        // idx should always reach want, but keep it safe
        if (idx == want) return res;
        return Arrays.copyOf(res, idx);
    }

    // ----------------- helpers -----------------

    private void appendToRoomList(int room, Node node) {
        // Update nonEmptyRooms if transitioning 0 -> 1
        if (roomCount[room] == 0) nonEmptyRooms.add(room);
        roomCount[room]++;

        node.prev = tail[room];
        node.next = null;

        if (tail[room] != null) {
            tail[room].next = node;
        } else {
            // empty list
            head[room] = node;
        }
        tail[room] = node;
    }

    private void removeFromRoomList(int room, Node node) {
        Node p = node.prev;
        Node n = node.next;

        if (p != null) p.next = n;
        else head[room] = n;

        if (n != null) n.prev = p;
        else tail[room] = p;

        node.prev = null;
        node.next = null;

        roomCount[room]--;
        if (roomCount[room] == 0) nonEmptyRooms.remove(room);
    }
}

