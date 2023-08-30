package log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import object.Count;

public class Write {

	public static void WriteLogResult() {

		ExceptionHandler handler = new ExceptionHandler();

		File logFile = new File(System.getProperty("user.dir"), handler.getPath());
		try (FileWriter fileWriter = new FileWriter(logFile, true);
				PrintWriter printWriter = new PrintWriter(fileWriter)) {

			printWriter.println();
			printWriter.println();
			printWriter.println("---------------------------------------------------");
			

			printWriter.println("전체 영화 개수:	" + Count.getEntireSize());
			printWriter.println("중복 영화 개수:	" + Count.getDupCount());
			printWriter.println("6개 미만 영화 개수:	" + Count.getlessCount());
			printWriter.println("Location 테이블 추가 시도 횟수:	" + Count.getTryInsertLocationCount());
			printWriter.println("Location 테이블 추가 성공 횟수:	" + Count.getLocationInsertCount());
			printWriter.println("movie 테이블 추가 시도 횟수:	" + Count.gettryInsertMovieCount());
			printWriter.println("movie 테이블 추가 성공 횟수:	" + Count.getInsertMovieCount());
			printWriter.println("---------------------------------------------------");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

	}

}
