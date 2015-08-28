package services;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.LineMap;
import com.sun.source.tree.MethodTree;
import com.sun.source.util.SourcePositions;

import java.io.File;

import entities.SQATEntity;

public class AnalyzeClassElement extends BaseClass {

    AnalyzeClassElement(CompilationUnitTree compilationUnitTree, SourcePositions sourcePositions, File fileName) {
        this.compilationUnitTree = compilationUnitTree;
        this.sourcePositions = sourcePositions;
        this.lineMap = compilationUnitTree.getLineMap();
        this.fileName = fileName.toString();
    }

    SQATEntity classEntity = new SQATEntity();
    private CompilationUnitTree compilationUnitTree;
    private SourcePositions sourcePositions;
    private LineMap lineMap;

    private String fileName;

    String className = "";
    String methodName = "";
    String classVariableName = "";
    String methodVariabaleName = "";
    int methodLineOfCode = 0;

    public static int numberOfClasses = 0;
    public static int numberOfMethods = 0;

    // Get Class Names
    @Override
    public Void visitClass(ClassTree classTree, Void p) {
        classEntity = new SQATEntity();
        className = classTree.getSimpleName().toString();
        classEntity.setClassName(className);

        long startPosition = sourcePositions.getStartPosition(compilationUnitTree, classTree);
        long startLine = lineMap.getLineNumber(startPosition);
        long endPosition = sourcePositions.getEndPosition(compilationUnitTree, classTree);
        long endLine = lineMap.getLineNumber(endPosition);
        // System.out.println(" Lines  " + endLine  + "  " + "  --- "  + startLine);
        classEntity.setMethodLineOfCode((int) (endLine - startLine));

        numberOfClasses++;

        analizeList.add(classEntity);
        return super.visitClass(classTree, p);
    }

    // Get Methods
    @Override
    public Void visitMethod(MethodTree methodTree, Void p) {
        methodName = methodTree.getName().toString();

        long startPosition = sourcePositions.getStartPosition(compilationUnitTree, methodTree);
        long startLine = lineMap.getLineNumber(startPosition);
        long endPosition = sourcePositions.getEndPosition(compilationUnitTree, methodTree);
        long endLine = lineMap.getLineNumber(endPosition);

        classEntity = new SQATEntity();
        classEntity.setClassName(className);
        classEntity.setMethodName(methodName);
        classEntity.setMethodLineOfCode((int) (endLine - startLine));
        // System.out.println(" Lines  " + endLine  + "  " + "  --- "  + startLine);

        numberOfMethods++;

        analizeList.add(classEntity);

        return super.visitMethod(methodTree, p);
    }
}
