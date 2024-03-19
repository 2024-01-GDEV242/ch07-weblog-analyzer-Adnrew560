/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author Andrew Steidle
 * @version    2024.03.18
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader("weblog.txt");
    }
    
    /**
     * Create an object to analyze hourly web accesses.
     * @param filename The name of the file
     */
    public LogAnalyzer(String filename)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(filename);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    /**
     * Read the number of times the site has been accessed
     * @return int The number of times the site has been accessed
     */
    public int numberOfAccesses()
    {
        int total = 0;
        for(int hour = 0; hour < hourCounts.length; hour++)
        {
            total += hourCounts[hour];
        }
        return total;
    }
    
    /**
     * Finds the busiest hour
     * @return int The hour at which the site is the busiest
     */
    
    public int busiestHour()
    {
        int busiest = 0;
        int amount = 0;
        for(int hour = 0; hour < hourCounts.length; hour++)
        {
            if(hourCounts[hour] > amount)
            {
                busiest = hour;
                amount = hourCounts[hour];
            }
        }
        return busiest;
    }
}
