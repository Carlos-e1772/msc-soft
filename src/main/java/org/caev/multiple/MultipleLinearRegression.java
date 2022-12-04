package org.caev.multiple;

import com.opencsv.bean.CsvToBeanBuilder;
import org.la4j.LinearAlgebra;
import org.la4j.Matrix;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class MultipleLinearRegression {

    public static void main(String[] args) {
        List<Startup> dataset = getDataset();
        Matrix x = Matrix.from2DArray(
                dataset.stream().map(value -> new double[]{1d, value.getRndSpend(),value.getAdministration()})
                        .toArray(double[][]::new)
        );
        Matrix y = Matrix.from2DArray(dataset.stream().map(value -> new double[]{value.getProfit()})
                .toArray(double[][]::new)
        );
        Matrix transposeX = x.transpose();
        Matrix inverted = transposeX.multiply(x).withInverter(LinearAlgebra.INVERTER).inverse();
        Matrix res = (inverted).multiply(transposeX.multiply(y));
        double b0 = res.get(0, 0);
        double b1 = res.get(1, 0);
        double b2 = res.get(2, 0);
        dataset.forEach(startup -> {
            System.out.println("Real profit = [" + startup.getProfit() + "], R&D spend = [" + startup.getRndSpend() + "], Administration = [" + startup.getAdministration() + "], predicted profit = " + (b0 + (startup.getRndSpend() * b1) + (startup.getAdministration() * b2)));
        });

    }

    private static List<Startup> getDataset() {
        try {
            String fileName = "src/main/resources/datasets/mlr.csv";
            return new CsvToBeanBuilder<Startup>(new FileReader(fileName)).withSkipLines(1).withType(Startup.class).build().parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
