package metrices;

import javax.swing.JTable;
import services.BaseClass;

public class NumberOfClasses extends BaseClass {


    private void numberofClasses() {


        String column[] = {"FileName", "Class", "Method"};
        String dataValues[][] =
                {
                        {"12", "234", "67"},
                        {"-123", "43", "853"},
                        {"93", "89.2", "109"},
                        {"279", "9033", "3092"}
                };
        JTable resultData = new JTable(dataValues, column);

    }

}
