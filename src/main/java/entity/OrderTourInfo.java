package entity;

public class OrderTourInfo extends Order {

    private String name;
    private String pathImage;

    public OrderTourInfo() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPathImage() {
        return pathImage;
    }
    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }
}
