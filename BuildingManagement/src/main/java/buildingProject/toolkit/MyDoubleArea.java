package buildingProject.toolkit;

import lombok.Getter;

@Getter
public class MyDoubleArea{
    private final Long id;
    private final String area;

    public MyDoubleArea(Long id, double doubleArea) {
        this.id = id;
        area = String.format("%.2f", doubleArea);
    }
}