package lk.drivingschool.servlet;
import jakarta.servlet.annotation.WebServlet;
@WebServlet("/lesson")
public class LessonServlet extends AbstractFileServlet {
    protected String fileName() { return "lessons.txt"; }
    protected String title() { return "Lesson Scheduling & Queue"; }
    protected String[] fields() { return new String[] {"studentId", "lessonDate", "lessonTime", "notes"}; }
    protected String idPrefix() { return "LES"; }
    protected boolean useQueue() { return true; }
    protected boolean sortExperience() { return false; }
}
