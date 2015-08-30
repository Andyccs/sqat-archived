package com.sqatntu.services;

import com.sqatntu.entities.SQATEntity;
import com.sun.source.util.TreeScanner;

import java.util.ArrayList;
import java.util.List;

public class BaseClass extends TreeScanner<Void, Void> {
    public static List<SQATEntity> analyzeList = new ArrayList<>();
}
