package entity;


public enum Transport {

    PLANE("Plane"),
    BOAT("Ship"),
    TRAIN("Еrain"),
    BUS("Bus");

    private String name;

    Transport() {
    }

    Transport(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
