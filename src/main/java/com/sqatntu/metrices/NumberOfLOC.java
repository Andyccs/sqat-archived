package com.sqatntu.metrices;

import com.sqatntu.services.BaseClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class NumberOfLOC extends BaseClass {

    // To display in UI as a summary
    public static int totalLoC;
    public static int totalBlankLines;
    public static int totalCommentLines;
    public static int totalLines;
    private int numberOfLines;
    private int numberOfLocMethods;
    private int numberOfBlankLines;

    public void getLoC(String fileName, long startLine, long endLine) {
        try {
            try (FileReader fileReader = new FileReader(new File(fileName));
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {

                numberOfLines = (int) (endLine - startLine) + 1;

                int i = 0;
                String currentLine;

                // Counting blank lines inside methods
                while ((currentLine = bufferedReader.readLine()) != null) {
                    i++;
                    if ((i >= startLine) && (i <= endLine) && "".equals(currentLine.trim())) {
                        numberOfBlankLines++;
                    }
                }
            }
        } catch (IOException e) {
            // TODO(andyccs): Fill in the blank
        }

        int numberOfComments = getCommentLinesMethods(fileName, 0, (int) startLine, (int) endLine);
        numberOfLocMethods = numberOfLines - numberOfBlankLines - numberOfComments;

        System.out.println("Total number of source lines of the method " + numberOfLines);
        System.out.println("Total number of blank lines of the method " + numberOfBlankLines);
        System.out.println("Total number of comment lines of the method " + numberOfComments);
        System.out.println("Total number of Lines of Codes of the method " + numberOfLocMethods);
        System.out.println("************************************************************");

    }

    private int getCommentLinesMethods(String fileName, int numberOfComments, int start, int end) {
        int numberOfBlockCommentLines = 0;
        try {
            try (FileReader fileReader = new FileReader(new File(fileName));
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {

                int i = 0;

                String currentLine;
                String nextLine;
                // Counting blank lines inside methods
                while ((currentLine = bufferedReader.readLine()) != null) {
                    i++;

                    if ((i >= start) && (i <= end)) {
                        // Counting Single Line comments
                        if (currentLine.trim().startsWith("//")) {
                            numberOfComments++;
                        }

                        // Counting Multi Lines comments
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
            // TODO(andyccs): Fill in the blank
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

                // Counting lines to detect starting position
                while ((currentLine = bufferedReader.readLine()) != null) {
                    i++;

                    StringTokenizer defaultTokenizer = new StringTokenizer(currentLine.trim());
                    String[] tokensArray = new String[defaultTokenizer.countTokens()];

                    int j = 0;
                    while (defaultTokenizer.hasMoreTokens()) {
                        tokensArray[j] = defaultTokenizer.nextToken();
                        j++;
                    }

                    if (tokensArray.length > 1
                            && (tokensArray[0].equals("class")
                            || tokensArray[1].equals("class"))) {
                        startingLine = i;
                    }
                }
                endingLine = i;
            }

            System.out.printf(
                    "Found the class %s from line %d to line %d. \n",
                    className,
                    startingLine,
                    endingLine);

            totalLinesClasses = (endingLine - startingLine) + 1;
            System.out.println("Total number of source lines of the class " + totalLinesClasses);

            // Counting blank lines in classes
            try {
                try (FileReader fileReader = new FileReader(new File(fileName));
                     BufferedReader bufferedReader = new BufferedReader(fileReader)) {

                    int i = 0;
                    String currentLine;

                    // Counting blank lines inside methods
                    while ((currentLine = bufferedReader.readLine()) != null) {
                        i++;
                        if ((i >= startingLine)
                                && (i <= endingLine)
                                && "".equals(currentLine.trim())) {
                            blankLinesClasses++;
                        }
                    }
                }

                System.out.println("Total number of blank lines of the class " + blankLinesClasses);

                totalCommentLinesClasses =
                        getCommentLinesMethods(fileName, 0, startingLine, endingLine);
                System.out.println(
                        "Total number of comment lines of the class "
                        + totalCommentLinesClasses);

                numberOfLocPerClass =
                        totalLinesClasses - totalCommentLinesClasses - blankLinesClasses;
                System.out.println("Total number of Lines of Codes " + numberOfLocPerClass);

                System.out.println("----------------------------------------------------------");

                totalLoC += numberOfLocPerClass;
                totalCommentLines += totalCommentLinesClasses;
                totalBlankLines += blankLinesClasses;
                totalLines += totalLinesClasses;

            } catch (IOException e) {
                // TODO(andyccs): Fill in the blank
            }

        } catch (IOException e) {
            // TODO(andyccs): Fill in the blank
        }

        return numberOfLocPerClass;
    }
}
