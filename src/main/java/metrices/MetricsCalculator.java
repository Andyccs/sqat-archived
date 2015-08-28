package metrices;

public class MetricsCalculator {

    public void calculatingMatrices(String fileName, String className) {

        DetectingMethods detectingMethods = new DetectingMethods();
        detectingMethods.findSurrondingMethods(fileName);

        NumberOfLOC numberOfLoc = new NumberOfLOC();
        numberOfLoc.numberOfLocClasses(fileName, className);

    }

}
