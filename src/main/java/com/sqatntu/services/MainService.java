package com.sqatntu.services;

import com.sqatntu.entities.SQATEntity;
import com.sqatntu.metrices.MetricsCalculator;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.Trees;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class MainService {

    List<File> fileList = new ArrayList<File>();
    List<String> classNames = new ArrayList<String>();

    public void AnalyzeJavaSourceCode(List<String> filePathList) {
        GetFileList(filePathList);
        System.out.println("");
        for (SQATEntity entity : BaseClass.analizeList) {
            if (!entity.getMethodName().equals("")) {
                System.out.println(" Class Name - " + entity.getClassName());
                classNames.add(entity.getClassName());
                System.out.println(" Method Name - " + entity.getMethodName());
            }
        }

        System.out.println("");

        int i = 0;
        for (String filePath : filePathList) {
            MetricsCalculator metricsCalculator = new MetricsCalculator();
            metricsCalculator.calculatingMatrices(filePath, classNames.get(i));
            i++;
        }

    }

    public void GetFileList(List<String> filePathList) {
        for (String path : filePathList) {
            File selectedFile = new File(path);
            fileList.add(selectedFile);

        }

        for (File file : fileList) {
            try {
                AnalyzeFiles(file);
            } catch (Exception ex) {
                System.out.println("File Analyze Failed ");
            }
        }
    }

    public void AnalyzeFiles(File javaSourceFile) throws Exception {
        try {

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            // The file manager locates your Java source file for the compiler. Null arguments indicate I am comfortable with its default behavior.
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            // These will be parsed by the compiler
            Iterable<? extends JavaFileObject> fileObjects = fileManager.getJavaFileObjects(javaSourceFile);

            // Creates a new compilation task. This doesn't actually start the compilation process.
            // Null arguments indicate I am comfortable with its default behavior.
            JavaCompiler.CompilationTask task = compiler.getTask(null, null, null, null, null, fileObjects);

            // Cast to the Sun-specific CompilationTask.
            com.sun.tools.javac.api.JavacTaskImpl javacTask = (com.sun.tools.javac.api.JavacTaskImpl) task;
            SourcePositions sourcePositions = Trees.instance(javacTask).getSourcePositions();
            // The Sun-specific JavacTaskImpl can parse the source file without compiling it, returning
            // one CompilationUnitTree for each JavaFileObject given to the compiler.getTask call (only one in our case).
            Iterable<? extends CompilationUnitTree> trees = javacTask.parse();
            CompilationUnitTree tree = trees.iterator().next();
            tree.accept(new AnalyzeClassElement(tree, sourcePositions, javaSourceFile), null);

            // tree.accept(new NumberOfMethods(), null);
            //////////////////////////

            // displayInformation(javaSourceFile);

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
