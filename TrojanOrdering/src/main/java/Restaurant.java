public class Restaurant {
    private int id;
    private String name;
    private String address;
    private String url;

    public Restaurant(int id, String name, String address, String url) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.url = url;
    }

    public int getID(){
        return id;
    }
    
    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getURL(){
        return url;
    }

}
