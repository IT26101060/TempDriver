package lk.drivingschool.model;
public class Student extends Person {
    private String licenceType, address;
    public Student(String id,String name,String email,String phone,String licenceType,String address){
        super(id,name,email,phone); this.licenceType=licenceType; this.address=address;
    }
    public String getLicenceType(){return licenceType;} public void setLicenceType(String v){licenceType=v;}
    public String getAddress(){return address;} public void setAddress(String v){address=v;}
    @Override public String getRole(){return "STUDENT";}
}
