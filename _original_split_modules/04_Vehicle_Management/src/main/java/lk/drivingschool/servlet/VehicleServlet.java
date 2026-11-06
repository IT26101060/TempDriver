package lk.drivingschool.servlet;
import jakarta.servlet.annotation.WebServlet;
@WebServlet("/vehicle")
public class VehicleServlet extends AbstractFileServlet {
    protected String fileName() { return "vehicles.txt"; }
    protected String title() { return "Vehicle Management"; }
    protected String[] fields() { return new String[] {"registrationNo", "model", "category", "status"}; }
    protected String idPrefix() { return "VEH"; }
    protected boolean useQueue() { return false; }
    protected boolean sortExperience() { return false; }
}
