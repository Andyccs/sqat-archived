package services;

import com.sun.source.util.TreeScanner;

import java.util.ArrayList;
import java.util.List;

import entities.SQATEntity;

public class BaseClass extends TreeScanner<Void, Void> {
    public static List<SQATEntity> analizeList = new ArrayList<SQATEntity>();
}
