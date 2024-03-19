import java.util.Arrays;
/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author Andrew Steidle
 * @version    2024.03.18
 */
public class LogAnalyzer
{
    private int[][] range;
    // Where to calculate the monthly access counts.
    private int[] monthCounts;
    // Where to calculate the daily access counts.
    private int[] dayCounts;
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
        monthCounts = new int[13];
        monthCounts[0] = -1;
        dayCounts = new int[32];
        dayCounts[0] = -1;
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader("weblog.txt");
        range = new int[2][5];
        range[0] = reader.getFirst();
        range[1] = reader.getLast();
    }
    
    /**
     * Create an object to analyze hourly web accesses.
     * @param filename The name of the file
     */
    public LogAnalyzer(String filename)
    { 
        // Create the array object to hold the hourly
        // access counts.
        monthCounts = new int[13];
        monthCounts[0] = -1;
        dayCounts = new int[32];
        dayCounts[0] = -1;
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(filename);
    }

    /**
     * Analyze the monthly access data from the log file.
     */
    public void analyzeMonthlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            monthCounts[month]++;
        }
    }
    
    /**
     * Analyze the daily access data from the log file.
     */
    public void analyzeDailyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day]++;
        }
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
     * @return int The hour that appears the most in the log, ties broken by whichever came first
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
    
    /**
     * Finds the quietest hour
     * @return int The hour that appears the least in the log, ties broken by whichever came first
     */
    public int quietestHour()
    {
        int quietest = 0;
        int amount = hourCounts[0];
        for(int hour = 1; hour < hourCounts.length; hour++)
        {
            if(hourCounts[hour] < amount)
            {
                quietest = hour;
                amount = hourCounts[hour];
            }
        }
        return quietest;
    }
    
    /**
     * Finds the busiest set of two consecutive hours
     * @return int[] The range of hours that appears most in the log, ties broken by whichever came first
     */
    public int[] busiestTwoHour()
    {
        int[] range = {23,0};
        int amount = hourCounts[23] + hourCounts[0];
        for(int hour = 1; hour < hourCounts.length; hour++)
        {
            if((hourCounts[hour-1] + hourCounts[hour]) > amount)
            {
                range[0] = hour - 1;
                range[1] = hour;
                amount = hourCounts[hour-1] + hourCounts[hour];
            }
        }
        return range;
    }
    
    /**
     * Finds the busiest day
     * @return int The day that appears the most in the log, ties broken by whichever came first
     */
    public int busiestDay()
    {
        int busiest = 0;
        int amount = 0;
        for(int day = 0; day < dayCounts.length; day++)
        {
            if(dayCounts[day] > amount)
            {
                busiest = day;
                amount = dayCounts[day];
            }
        }
        return busiest;
    }
    
    /**
     * Finds the quietest day
     * @return int The day that appears the least in the log, ties broken by whichever came first
     */
    public int quietestDay()
    {
        int quietest = 0;
        int amount = dayCounts[0];
        for(int day = 1; day < dayCounts.length; day++)
        {
            if(dayCounts[day] < amount)
            {
                quietest = day;
                amount = dayCounts[day];
            }
        }
        return quietest;
    }
    
    /**
     * Finds the busiest month
     * @return int The month that appears the most in the log, ties broken by whichever came first
     */
    public int busiestMonth()
    {
        int busiest = 0;
        int amount = 0;
        for(int month = 0; month < monthCounts.length; month++)
        {
            if(monthCounts[month] > amount)
            {
                busiest = month;
                amount = monthCounts[month];
            }
        }
        return busiest;
    }
    
    /**
     * Finds the quietest month
     * @return int The month that appears the least in the log, ties broken by whichever came first
     */
    public int quietestMonth()
    {
        int quietest = 0;
        int amount = monthCounts[0];
        for(int month = 1; month < monthCounts.length; month++)
        {
            if(monthCounts[month] < amount)
            {
                quietest = month;
                amount = monthCounts[month];
            }
        }
        return quietest;
    }
    
    /**
     * Prints the total number of times the site was accessed each month
     */
    public void totalAccessesPerMonth()
    {
        for(int month = 1; month < monthCounts.length; month++)
        {
            System.out.println(month + ": " + monthCounts[month]);
        }
    }
    
    /**
     * Finds the total number of times the site was accessed each month
     * @return double[] An array of the average number of accesses each month
     */
    public double[] averageAccessesPerMonth()
    {
        double[] result = new double[13];
        int years = 1 + range[1][0] - range[0][0];
        Arrays.fill(result,years);
        result[0] = -1;
        //This part checks the boundaries of the dataset.
        //for example: it would be inaccurate to divide by 2 for january if
        //january only shows up once, even if the other months show up twice
        for(int i = 1; i < 13; i++)
        {
            if(i < range[0][1])
            {
                result[i]--;
            }
            if(i > range[1][1])
            {
                result[i]--;
            }
        }
        for(int month = 1; month < monthCounts.length; month++)
        {
            if(result[month] > 0)
            {
                result[month] = monthCounts[month] / result[month];
            }
        }
        return result;
    }
}
