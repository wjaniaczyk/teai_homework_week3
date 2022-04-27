package pl.bykowski.carshomeworkweek3.entity;

import javax.validation.constraints.NotBlank;

public class CarRequestDTO {

    @NotBlank(message = "Mark cannot be null")
    private String mark;

    @NotBlank(message = "Model cannot be null")
    private String model;

    @NotBlank(message = "Color cannot be null")
    private String color;

    public CarRequestDTO() {
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
