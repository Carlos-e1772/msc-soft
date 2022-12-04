package org.caev.gradiente;

import com.opencsv.bean.CsvToBeanBuilder;
import org.caev.simple.XYserializable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class GradientDescent {

    public static void main(String[] args) {
        List<XYserializable> dataset = getDataset();
        int n = dataset.size();
        double b0 = 0;
        double b1 = 0;
        double alfa = 0.003;
        double error;

        do {
            double finalB2 = b0;
            double finalB3 = b1;
            error = (1d / n) * dataset.stream().mapToDouble(benetton -> Math.pow(benetton.getY() - (finalB2 + (finalB3 * benetton.getX())), 2)).sum();
            b0 = newBeta0(b0, b1, dataset, n);
            b1 = newBeta1(b0, b1, dataset, n);
        } while (Math.abs(error) < alfa);
    }


    private static double newBeta0(double beta0, double beta1, List<XYserializable> dataset, int n) {
        return beta0 + ((2d / n) * 0.003 * dataset.stream().mapToDouble(benetton -> benetton.getY() - (beta0 + (beta1 * benetton.getX()))).sum());
    }

    private static double newBeta1(double beta0, double beta1, List<XYserializable> dataset, int n) {
        return beta1 + ((2d / n) * 0.003 * dataset.stream().mapToDouble(benetton -> benetton.getX() * (benetton.getY() - (beta0 + (beta1 * benetton.getX())))).sum());
    }

    private static List<XYserializable> getDataset() {
        try {
            String fileName = "src/main/resources/datasets/slr.csv";
            return new CsvToBeanBuilder<XYserializable>(new FileReader(fileName))
                    .withType(XYserializable.class)
                    .build()
                    .parse();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

}
