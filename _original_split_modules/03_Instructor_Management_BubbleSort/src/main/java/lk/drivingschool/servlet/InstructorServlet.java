package lk.drivingschool.servlet;
import jakarta.servlet.annotation.WebServlet;
@WebServlet("/instructor")
public class InstructorServlet extends AbstractFileServlet {
    protected String fileName() { return "instructors.txt"; }
    protected String title() { return "Instructor Management (sorted by experience)"; }
    protected String[] fields() { return new String[] {"name", "phone", "licenceType", "experience", "email"}; }
    protected String idPrefix() { return "INS"; }
    protected boolean useQueue() { return false; }
    protected boolean sortExperience() { return true; }
}
