package lk.drivingschool.servlet;
import jakarta.servlet.annotation.WebServlet;
@WebServlet("/student")
public class StudentServlet extends AbstractFileServlet {
    protected String fileName() { return "students.txt"; }
    protected String title() { return "Student Management"; }
    protected String[] fields() { return new String[] {"name", "email", "phone", "licenceType", "address"}; }
    protected String idPrefix() { return "STU"; }
    protected boolean useQueue() { return false; }
    protected boolean sortExperience() { return false; }
}
