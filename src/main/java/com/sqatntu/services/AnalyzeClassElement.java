package com.sqatntu.services;

import com.sqatntu.entities.SQATEntity;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.LineMap;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.SourcePositions;

import java.io.File;

public class AnalyzeClassElement extends BaseClass {

    public static int numberOfClasses = 0;
    public static int numberOfMethods = 0;
    private CompilationUnitTree compilationUnitTree;
    private SourcePositions sourcePositions;
    private LineMap lineMap;
    private String fileName;
    private String className;
    private String methodName;

    AnalyzeClassElement(CompilationUnitTree compilationUnitTree, SourcePositions sourcePositions, File fileName) {
        this.compilationUnitTree = compilationUnitTree;
        this.sourcePositions = sourcePositions;
        this.lineMap = compilationUnitTree.getLineMap();
        this.fileName = fileName.toString();
    }

    @Override
    public Void visitClass(ClassTree classTree, Void p) {
        className = classTree.getSimpleName().toString();

        long startPosition = sourcePositions.getStartPosition(compilationUnitTree, classTree);
        long startLine = lineMap.getLineNumber(startPosition);

        long endPosition = sourcePositions.getEndPosition(compilationUnitTree, classTree);
        long endLine = lineMap.getLineNumber(endPosition);

        // TODO(andyccs: change method line of code to long
        SQATEntity classEntity = new SQATEntity();
        classEntity.setClassName(className);
        classEntity.setMethodLineOfCode((int) (endLine - startLine));

        numberOfClasses++;

        analizeList.add(classEntity);

        return super.visitClass(classTree, p);
    }

    @Override
    public Void visitMethod(MethodTree methodTree, Void p) {
        methodName = methodTree.getName().toString();

        long startPosition = sourcePositions.getStartPosition(compilationUnitTree, methodTree);
        long startLine = lineMap.getLineNumber(startPosition);

        long endPosition = sourcePositions.getEndPosition(compilationUnitTree, methodTree);
        long endLine = lineMap.getLineNumber(endPosition);

        SQATEntity classEntity = new SQATEntity();
        classEntity.setClassName(className);
        classEntity.setMethodName(methodName);
        classEntity.setMethodLineOfCode((int) (endLine - startLine));

        numberOfMethods++;

        analizeList.add(classEntity);

        return super.visitMethod(methodTree, p);
    }
}
