package com.sqatntu.metrices;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.LineMap;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreeScanner;
import com.sun.source.util.Trees;

import java.io.IOException;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class DetectingMethods {

    private long startPosition = 0;
    private long endPosition = 0;
    private long startLine = 0;
    private long endLine = 0;

    public void findSurroundingMethods(String fileName) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticsCollector = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticsCollector, null, null);
        Iterable<? extends JavaFileObject> fileObjects = fileManager.getJavaFileObjects(fileName);
        CompilationTask task = compiler.getTask(null, fileManager, diagnosticsCollector, null, null, fileObjects);

        // Here we switch to Sun-specific APIs
        JavacTask javacTask = (JavacTask) task;
        SourcePositions sourcePositions = Trees.instance(javacTask).getSourcePositions();
        Iterable<? extends CompilationUnitTree> parseResult = null;
        try {
            parseResult = javacTask.parse();
        } catch (IOException e) {

            // Parsing failed
            e.printStackTrace();
            System.exit(0);
        }
        for (CompilationUnitTree compilationUnitTree : parseResult) {
            compilationUnitTree.accept(new DetectingMethods.MethodLineLogger(compilationUnitTree, sourcePositions, fileName), null);
        }
    }

    private class MethodLineLogger extends TreeScanner<Void, Void> {
        private final CompilationUnitTree compilationUnitTree;
        private final SourcePositions sourcePositions;
        private final LineMap lineMap;
        private String fileName;

        private MethodLineLogger(CompilationUnitTree compilationUnitTree, SourcePositions sourcePositions, String fileName) {
            this.compilationUnitTree = compilationUnitTree;
            this.sourcePositions = sourcePositions;
            this.lineMap = compilationUnitTree.getLineMap();
            this.fileName = fileName;
        }

        @Override
        public Void visitMethod(MethodTree arg0, Void arg1) {
            startPosition = sourcePositions.getStartPosition(compilationUnitTree, arg0);
            startLine = lineMap.getLineNumber(startPosition);
            endPosition = sourcePositions.getEndPosition(compilationUnitTree, arg0);
            endLine = lineMap.getLineNumber(endPosition);

            System.out.println("Found the method " + arg0.getName() + " from line " + startLine + " to line " + endLine + ".");

            // splitting methods to files
            try {
                // Avoid constructors
                if (!arg0.getName().toString().equalsIgnoreCase("<init>")) {
                    // TODO(andyccs): removed
//                    File file = new File("src\\MethodFiles\\" + arg0.getName() + ".txt");
//                    BufferedWriter output = new BufferedWriter(new FileWriter(file));
//                    output.write(arg0.getBody().toString());
//                    output.close();

                    NumberOfLOC numberOfLoc = new NumberOfLOC();
                    numberOfLoc.getLoC(fileName, startLine, endLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return super.visitMethod(arg0, arg1);
        }


    }
}

