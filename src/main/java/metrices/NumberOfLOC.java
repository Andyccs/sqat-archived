package metrices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import services.BaseClass;

public class NumberOfLOC extends BaseClass {

    public int numberOfLines = 0;
    public int numberOfLocMethods = 0;
    public int numberOfBlankLines = 0;

    //To display in UI as a summary
    public static int totalLoC = 0;
    public static int totalBlankLines = 0;
    public static int totalCommentLines = 0;
    public static int totalLines = 0;

    public void getLoC(String fileName, long startLine, long endLine) throws IOException {

        try {
            try (FileReader fileReader = new FileReader(new File(fileName));
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {

                numberOfLines = (int) (endLine - startLine) + 1;

                int i = 0;
                String currentLine;

                //Couting blank lines inside methods
                while ((currentLine = bufferedReader.readLine()) != null) {
                    i++;
                    if ((i >= startLine) && (i <= endLine) && "".equals(currentLine.trim())) {
                        numberOfBlankLines++;
                    }
                }
            }
        } catch (IOException e) {
        }

        int numberOfComments = getCommentLinesMethods(fileName, 0, (int) startLine, (int) endLine);
        numberOfLocMethods = numberOfLines - numberOfBlankLines - numberOfComments;

        System.out.println("Total number of source lines of the method " + numberOfLines);
        System.out.println("Total number of blank lines of the method " + numberOfBlankLines);
        System.out.println("Total number of comment lines of the method " + numberOfComments);
        System.out.println("Total number of Lines of Codes of the method " + numberOfLocMethods);
        System.out.println("************************************************************");

    }

    public int getCommentLinesMethods(String fileName, int numberOfComments, int start, int end) throws IOException {

        int numberOfBlockCommentLines = 0;
        try {
            try (FileReader fileReader = new FileReader(new File(fileName));
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {

                int i = 0;

                String currentLine;
                String nextLine;
                //Couting blank lines inside methods
                while ((currentLine = bufferedReader.readLine()) != null) {
                    i++;

                    if ((i >= start) && (i <= end)) {
                        //Counting Single Line comments
                        if (currentLine.trim().startsWith("//")) {
                            numberOfComments++;
                        }

                        //Counting Multi Lines comments
                        if (currentLine.trim().startsWith("/*")) {
                            numberOfBlockCommentLines++;

                            while ((nextLine = bufferedReader.readLine()) != null) {
                                if (!nextLine.trim().endsWith("*/")) {
                                    numberOfBlockCommentLines++;
                                } else {
                                    numberOfBlockCommentLines++;
                                    break;
                                }

                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
        }

        numberOfComments += numberOfBlockCommentLines;
        return numberOfComments;
    }

    // Assuming one class per file
    public int numberOfLocClasses(String fileName, String className) {

        int numberOfLocPerClass = 0;
        int totalLinesClasses = 0;
        int blankLinesClasses = 0;
        int totalCommentLinesClasses = 0;
        int startingLine = 0;
        int endingLine = 0;

        try {
            try (FileReader fileReader = new FileReader(new File(fileName));
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {

                int i = 0;
                String currentLine;

                //Couting lines to detect starting position
                while ((currentLine = bufferedReader.readLine()) != null) {
                    i++;

                    StringTokenizer defaultTokenizer = new StringTokenizer(currentLine.trim());
                    String[] tokensArray = new String[defaultTokenizer.countTokens()];

                    int j = 0;
                    while (defaultTokenizer.hasMoreTokens()) {
                        tokensArray[j] = defaultTokenizer.nextToken();
                        j++;
                    }

                    if (tokensArray.length > 1 && (tokensArray[0].equals("class") || tokensArray[1].equals("class"))) {
                        startingLine = i;
                    }
                }
                endingLine = i;
            }

            System.out.println("Found the class " + className + " from line " + startingLine + " to line " + endingLine);
            totalLinesClasses = (endingLine - startingLine) + 1;
            System.out.println("Total number of source lines of the class " + totalLinesClasses);

            // Couting blank lines in classes
            try {
                try (FileReader fileReader = new FileReader(new File(fileName));
                     BufferedReader bufferedReader = new BufferedReader(fileReader)) {

                    int i = 0;
                    String currentLine;

                    //Couting blank lines inside methods
                    while ((currentLine = bufferedReader.readLine()) != null) {
                        i++;
                        if ((i >= startingLine) && (i <= endingLine) && "".equals(currentLine.trim())) {
                            blankLinesClasses++;
                        }
                    }
                }

                System.out.println("Total number of blank lines of the class " + blankLinesClasses);
                totalCommentLinesClasses = getCommentLinesMethods(fileName, 0, startingLine, endingLine);
                System.out.println("Total number of comment lines of the class " + totalCommentLinesClasses);
                numberOfLocPerClass = totalLinesClasses - totalCommentLinesClasses - blankLinesClasses;
                System.out.println("Total number of Lines of Codes " + numberOfLocPerClass);
                System.out.println("----------------------------------------------------------");

                totalLoC += numberOfLocPerClass;
                totalCommentLines += totalCommentLinesClasses;
                totalBlankLines += blankLinesClasses;
                totalLines += totalLinesClasses;

            } catch (IOException e) {
            }

        } catch (IOException e) {
        }

        return numberOfLocPerClass;
    }
}
