package object;

public class Count {
    private static int dupCount = 0;
    private static int lessCount = 0;
    private static int entireSize = 0;
    private static int InsertMovieCount = 0;
    private static int LocationInsertCount = 0;
    private static int TryInsertLocationCount = 0;
    private static int tryInsertMovieCount = 0;
    private static int lessLocationCount = 0;

    public static synchronized void setEntireSize(int entireSize) {
	Count.entireSize = entireSize;
    }

    public static int getLessLocatioCount() {
	return lessLocationCount;
    }

    public static int gettryInsertMovieCount() {
	return tryInsertMovieCount;
    }

    public static int getTryInsertLocationCount() {
	return TryInsertLocationCount;
    }

    public static int getLocationInsertCount() {
	return LocationInsertCount;
    }

    public static int getlessCount() {
	return lessCount;
    }

    public static int getDupCount() {
	return dupCount;
    }

    public static int getInsertMovieCount() {
	return InsertMovieCount;
    }

    public static int getEntireSize() {
	return entireSize;
    }

    public synchronized static void incrementLessLocationCount() {
	lessLocationCount++;
    }

    public synchronized static void incrementtryInsertMovieCount() {
	tryInsertMovieCount++;
    }

    public synchronized static void incrementLocationInsertCount() {
	LocationInsertCount++;
    }

    public synchronized static void incrementTryInsertLocationCount() {
	TryInsertLocationCount++;
    }

    public synchronized static void incrementInsertMovieCount() {
	InsertMovieCount++;
    }

    public synchronized static void incrementDupCount() {
	dupCount++;
    }

    public synchronized static void incrementlessCount() {
	lessCount++;
    }

}
