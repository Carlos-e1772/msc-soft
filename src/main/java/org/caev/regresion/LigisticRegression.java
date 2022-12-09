package org.caev.regresion;

import com.opencsv.bean.CsvToBeanBuilder;
import org.la4j.Matrix;
import org.la4j.Vector;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.exp;


public class LigisticRegression {

   public static void main(String[] args) {
       List<XYserializable> dataset = getDataset();
       int n = dataset.size();
       Matrix matrix = Matrix.from2DArray(
               dataset.stream().map(value -> new double[]{value.getX(), value.getY()}).toArray(double[][]::new)
       );
       Vector x = matrix.getColumn(0);
       Vector y = matrix.getColumn(1);
       Matrix sumproductxy = matrix.getColumn(0).toRowMatrix().multiply(matrix.getColumn(1).toColumnMatrix());
       Matrix sumproductxx = matrix.getColumn(0).toRowMatrix().multiply(matrix.getColumn(0).toColumnMatrix());
       double b1 = ((n * sumproductxy.sum()) - ((x.sum() * y.sum()))) / (n * sumproductxx.sum() - (x.sum() * x.sum()));
       double b0 = (y.sum() - (b1 * x.sum())) / n;
       //dataset.forEach(benetton -> System.out.println("Real y [" + benetton.getY() + "] with x [" + benetton.getX() + "] vs predicted y = " + (b0 + b1 * benetton.getX()) + ""));
       List<Double> tests = new ArrayList<>();
       tests.add(70d);
       tests.add(76d);
       tests.add(90d);
       tests.add(103d);
       tests.add(115d);
       tests.forEach(aDouble -> System.out.println("x = [" + aDouble + "] predicted y = " + sigmoidFunction(b0, b1, aDouble) + ""));
   }

    private static double sigmoidFunction(double b0, double b1, double x){
        double function = (b1 * x) + b0;
        return 1 / ( 1 + exp(-1 * function));
    }

   private static List<XYserializable> getDataset() {
       try {
           String fileName = "src/main/resources/datasets/slr.csv";
           return new CsvToBeanBuilder<XYserializable>(new FileReader(fileName)).withType(XYserializable.class).build().parse();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
       return new ArrayList<>();
    }

}
