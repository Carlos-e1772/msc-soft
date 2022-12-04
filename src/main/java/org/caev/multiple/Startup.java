package org.caev.multiple;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Startup implements Serializable {

    private static final long serialVersionUID = -4050927960680127579L;

    @CsvBindByPosition(position = 0)
    private Double rndSpend;

    @CsvBindByPosition(position = 1)
    private Double administration;

    @CsvBindByPosition(position = 2)
    private Double marketingSpend;

    @CsvBindByPosition(position = 4)
    private Double profit;

}
