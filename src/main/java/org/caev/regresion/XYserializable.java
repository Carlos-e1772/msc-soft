package org.caev.regresion;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class XYserializable implements Serializable {

    private static final long serialVersionUID = -358122967787687380L;

    @CsvBindByPosition(position = 0)
    private Double x;

    @CsvBindByPosition(position = 1)
    private Double y;

}
