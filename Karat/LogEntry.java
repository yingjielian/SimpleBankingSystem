package Jack2025.Karat;

import static org.junit.Assert.assertEquals;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/*
We are interested in how many people are using the highway, and so we would like to count how many complete journeys are taken in the log file.

A complete journey consists of:
* A driver entering the highway through an ENTRY toll booth.
* The driver passing through some number of MAINROAD toll booths (possibly 0).
* The driver exiting the highway through an EXIT toll booth.

For example, the following excerpt of log lines contains complete journeys for the cars with JOX304 and THX138:

.
.
.
90750.191 JOX304 250E ENTRY
91081.684 JOX304 260E MAINROAD
91082.101 THX138 110E ENTRY
91483.251 JOX304 270E MAINROAD
91873.920 THX138 120E MAINROAD
91874.493 JOX304 280E EXIT
.
.
91982.102 THX138 290E EXIT
.

You may assume that the log only contains complete journeys, and there are no missing entries.

2-1) Write a function in LogFile named countJourneys() that returns how many
     complete journeys there are in the given LogFile.
*/



/*
We are writing software to analyze logs for toll booths on a highway. This highway is a divided highway with limited access; the only way on to or off of the highway is through a toll booth.

There are three types of toll booths:
* ENTRY (E in the diagram) toll booths, where a car goes through a booth as it enters the highway.
* EXIT (X in the diagram) toll booths, where a car goes through a booth as it exits the highway.
* MAINROAD (M in the diagram), which have sensors that record a license plate as a car drives through at full speed.


        Exit Booth                         Entry Booth
            |                                   |
            X                                   E
             \                                 /
---<------------<---------M---------<-----------<---------<----
                                         (West-bound side)

===============================================================

                                         (East-bound side)
------>--------->---------M--------->--------->--------->------
             /                                 \
            E                                   X
            |                                   |
        Entry Booth                         Exit Booth

For our first task:
1-1) Read through and understand the code and comments below. Feel free to run the code and tests.
1-2) The tests are not passing due to a bug in the code. Make the necessary changes to LogEntry to fix the bug.
*/

class LogEntry {

    /**
     * Represents an entry from a single log line. Log lines look like this in the file:
     *
     * 34400.409 SXY288 210E ENTRY
     *
     * Where:
     * * 34400.409 is the timestamp in seconds since the software was started.
     * * SXY288 is the license plate of the vehicle passing through the toll booth.
     * * 210E is the location and traffic direction of the toll booth. Here, the toll
     *     booth is at 210 kilometers from the start of the tollway, and the E indicates
     *     that the toll booth was on the east-bound traffic side. Tollbooths are placed
     *     every ten kilometers.
     * * ENTRY indicates which type of toll booth the vehicle went through. This is one of
     *     "ENTRY", "EXIT", or "MAINROAD".
     **/

    private final float timestamp;
    private final String licensePlate;
    private final String boothType;
    private final int location;
    private final String direction;

    public LogEntry(String logLine) {
        String[] tokens = logLine.split(" ");
        this.timestamp = Float.parseFloat(tokens[0]);
        this.licensePlate = tokens[1];
        this.boothType = tokens[3];
        this.location =
                Integer.parseInt(tokens[2].substring(0, tokens[2].length() - 1));
        String directionLetter = tokens[2].substring(tokens[2].length() - 1);
        if (directionLetter.equals("E")) {
            this.direction = "EAST";
        } else if (directionLetter.equals("W")) {
            this.direction = "WEST";
        } else {
            throw new IllegalArgumentException();
        }
    }

    public float getTimestamp() {
        return timestamp;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getBoothType() {
        return boothType;
    }

    public int getLocation() {
        return location;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return String.format(
                "<LogEntry timestamp: %f  license: %s  location: %d  direction: %s  booth type: %s>",
                timestamp,
                licensePlate,
                location,
                direction,
                boothType
        );
    }
}

class LogFile {

    /*
     * Represents a file containing a number of log lines, converted to LogEntry
     * objects.
     */

    List<LogEntry> logEntries;

    public LogFile(BufferedReader reader) throws IOException {
        this.logEntries = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            LogEntry logEntry = new LogEntry(line.strip());
            this.logEntries.add(logEntry);
            line = reader.readLine();
        }
    }

    public LogEntry get(int index) {
        return this.logEntries.get(index);
    }

    public int size() {
        return this.logEntries.size();
    }

    public int countJourneys()
    {
        int count = 0;
        Map<String, List<String>> carMap = new HashMap<>();

        for(LogEntry log : logEntries)
        {
            carMap.putIfAbsent(log.getLicensePlate(), new ArrayList<>());
            carMap.get(log.getLicensePlate()).add(log.getBoothType());
        }

        for(List<String> list : carMap.values())
        {
            for(String s : list)
            {
                if(s.equals("EXIT"))
                {
                    count++;
                }
            }
        }

        return count;
    }

}

class Solution {

    public static void main(String[] argv) throws IOException {
        testLogFile();
        testLogEntry();
        testCountJourneys();
    }

    public static void testLogFile() throws IOException {
        System.out.println("Running testLogFile");
        try (
                BufferedReader reader = new BufferedReader(
                        new FileReader("/content/test/tollbooth_small.log")
                );
        ) {
            LogFile logFile = new LogFile(reader);
            assertEquals(13, logFile.size());
            for (LogEntry entry : logFile.logEntries) {
                assert (entry instanceof LogEntry);
            }
        }
    }

    public static void testLogEntry() {
        System.out.println("Running testLogEntry");
        String logLine = "44776.619 KTB918 310E MAINROAD";
        LogEntry logEntry = new LogEntry(logLine);
        assertEquals(44776.619f, logEntry.getTimestamp(), 0.0001);
        assertEquals("KTB918", logEntry.getLicensePlate());
        assertEquals(310, logEntry.getLocation());
        assertEquals("EAST", logEntry.getDirection());
        assertEquals("MAINROAD", logEntry.getBoothType());
        logLine = "52160.132 ABC123 400W ENTRY";
        logEntry = new LogEntry(logLine);
        assertEquals(52160.132f, logEntry.getTimestamp(), 0.0001);
        assertEquals("ABC123", logEntry.getLicensePlate());
        assertEquals(400, logEntry.getLocation());
        assertEquals("WEST", logEntry.getDirection());
        assertEquals("ENTRY", logEntry.getBoothType());
    }

    public static void testCountJourneys() throws IOException {
        System.out.println("Running testCountJourneys");
        try (BufferedReader reader = new BufferedReader(new FileReader("/content/test/tollbooth_small.log"))) {
            LogFile logFile = new LogFile(reader);
            assertEquals(3, logFile.countJourneys());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("/content/test/tollbooth_medium.log"))) {
            LogFile logFile = new LogFile(reader);
            assertEquals(63, logFile.countJourneys());
        }
    }

}