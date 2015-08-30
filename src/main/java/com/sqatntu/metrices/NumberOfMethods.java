package com.sqatntu.metrices;

import com.sqatntu.services.BaseClass;
import com.sun.source.tree.MethodTree;

class NumberOfMethods extends BaseClass {

    @Override
    public Void visitMethod(MethodTree methodTree, Void p) {
        System.out.println("Class Name  " + methodTree.getClass() + "Method Name   " + methodTree.getName());
        return super.visitMethod(methodTree, p);
    }
}
