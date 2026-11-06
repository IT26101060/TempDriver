package lk.drivingschool.model;
public abstract class Person {
    private final String id;
    private String name, email, phone;
    protected Person(String id, String name, String email, String phone) {
        this.id=id; this.name=name; this.email=email; this.phone=phone;
    }
    public String getId(){return id;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public abstract String getRole();
}
