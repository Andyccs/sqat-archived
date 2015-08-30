package com.sqatntu.metrices;

import com.sqatntu.services.BaseClass;
import com.sun.source.tree.MethodTree;

class NumberOfMethods extends BaseClass {

    @Override
    public Void visitMethod(MethodTree methodTree, Void p) {
        System.out.printf(
                "Class Name %s Method Name %s\n",
                methodTree.getClass(),
                methodTree.getName());
        return super.visitMethod(methodTree, p);
    }
}
