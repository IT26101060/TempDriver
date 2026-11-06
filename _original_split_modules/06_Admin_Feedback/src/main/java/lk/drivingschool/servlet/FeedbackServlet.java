package lk.drivingschool.servlet;
import jakarta.servlet.annotation.WebServlet;
@WebServlet("/feedback")
public class FeedbackServlet extends AbstractFileServlet {
    protected String fileName() { return "feedback.txt"; }
    protected String title() { return "Admin & Feedback Management"; }
    protected String[] fields() { return new String[] {"studentId", "rating", "comments", "status"}; }
    protected String idPrefix() { return "FDB"; }
    protected boolean useQueue() { return false; }
    protected boolean sortExperience() { return false; }
}
