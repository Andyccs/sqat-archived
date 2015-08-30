package com.sqatntu;

import com.sqatntu.metrices.NumberOfLOC;
import com.sqatntu.services.AnalyzeClassElement;
import com.sqatntu.services.MainService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SQAT {

    private static List<String> filePathList = new ArrayList<>();

    public static void main(String[] args) {

        String path = "/Users/andyccs/Documents/sqat/src/main/java";
        final File folder = new File(path);
        listFilesForFolder(folder);

        MainService mainService = new MainService();

        mainService.AnalyzeJavaSourceCode(filePathList);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder = stringBuilder
                .append("Total number of Classes : ")
                .append(String.valueOf(AnalyzeClassElement.numberOfClasses))
                .append("\n")
                .append("Total number of Methods : ")
                .append(String.valueOf(AnalyzeClassElement.numberOfMethods))
                .append("\n")
                .append("Total number of Lines of Codes : ")
                .append(String.valueOf(NumberOfLOC.totalLoC))
                .append("\n")
                .append("Total number of Blank Lines : ")
                .append(String.valueOf(NumberOfLOC.totalBlankLines))
                .append("\n")
                .append("Total number of Comment Lines : ")
                .append(String.valueOf(NumberOfLOC.totalCommentLines))
                .append("\n")
                .append("Total number of Lines : ")
                .append(String.valueOf(NumberOfLOC.totalLines))
                .append("\n");

        System.out.println(stringBuilder.toString());
    }

    public static void listFilesForFolder(final File folder) {
        System.out.println("Java source files in the selected folder are : ");

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                String fileName = fileEntry.getName();
                if (!fileName.isEmpty()) {
                    if (fileName.matches(".*\\.java")) {
                        filePathList.add(fileEntry.getPath());
                        System.out.println(fileEntry.getPath());
                    }
                }
            }
        }
    }

}
