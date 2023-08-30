package log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

public class ExceptionHandler {

    private static String jdbcURL;
//	private static String path= "src/log/log4.txt";

    private static String path = makePath();

    public String getPath() {
	return path;
    }

    public static String makePath() {

	int i = 1;
	while (true) {

	    String logPath = "src/log";

	    File logPathFile = new File(System.getProperty("user,dir"), logPath);

	    if (logPathFile.exists()) {

		String path = "src/log/log" + i + ".txt";

		File logFile = new File(System.getProperty("user.dir"), path);
		if (!logFile.exists()) {
		    return path;
		}

		i++;

	    }
	}

    }

    static {

	Properties properties = new Properties();

	try {
//            FileInputStream fileInputStream = new FileInputStream("dbutil/mysql.properties");
	    InputStream inputStream = ExceptionHandler.class.getClassLoader()
		    .getResourceAsStream("dbutil/mysql.properties");

	    properties.load(inputStream);
	    inputStream.close();

	    jdbcURL = properties.getProperty("jdbc.URL");

	    File logFile = new File(System.getProperty("user.dir"), path);
	    try (FileWriter fileWriter = new FileWriter(logFile, true);
		    PrintWriter printWriter = new PrintWriter(fileWriter)) {

		printWriter.println(jdbcURL);

	    } catch (IOException ioException) {
		ioException.printStackTrace();
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    public synchronized static void HttpStatusExceptionToFile(org.jsoup.HttpStatusException e) {

	File logFile = new File(System.getProperty("user.dir"), path);
	try (FileWriter fileWriter = new FileWriter(logFile, true);
		PrintWriter printWriter = new PrintWriter(fileWriter)) {

	    printWriter.println();
	    printWriter.println();
	    printWriter.println("---------------------------------------------------");
//			printWriter.println(e.getMessage());

	    e.printStackTrace(printWriter);
	    printWriter.println("---------------------------------------------------");
	} catch (IOException ioException) {
	    ioException.printStackTrace();
	}

    }

    public synchronized static void ElementNotFoundExceptionToFile(ElementNotFoundException e) {

	File logFile = new File(System.getProperty("user.dir"), path);
	try (FileWriter fileWriter = new FileWriter(logFile, true);
		PrintWriter printWriter = new PrintWriter(fileWriter)) {

	    printWriter.println();
	    printWriter.println();
	    printWriter.println("---------------------------------------------------");
//			printWriter.println(e.getMessage());

	    e.printStackTrace(printWriter);
	    printWriter.println("---------------------------------------------------");

	} catch (IOException ioException) {
	    ioException.printStackTrace();
	}
    }

    public synchronized static void GeocodingExceptionToFile(GeocodingException e) {

	File logFile = new File(System.getProperty("user.dir"), path);
	try (FileWriter fileWriter = new FileWriter(logFile, true);
		PrintWriter printWriter = new PrintWriter(fileWriter)) {

	    printWriter.println();
	    printWriter.println();
	    printWriter.println("---------------------------------------------------");
//			printWriter.println(e.getMessage());

	    e.printStackTrace(printWriter);
	    printWriter.println("---------------------------------------------------");

	} catch (IOException ioException) {
	    ioException.printStackTrace();
	}
    }

}
