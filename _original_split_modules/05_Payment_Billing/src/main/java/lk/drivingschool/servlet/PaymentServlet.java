package lk.drivingschool.servlet;
import jakarta.servlet.annotation.WebServlet;
@WebServlet("/payment")
public class PaymentServlet extends AbstractFileServlet {
    protected String fileName() { return "payments.txt"; }
    protected String title() { return "Payment & Billing"; }
    protected String[] fields() { return new String[] {"studentId", "amount", "paymentDate", "method", "status"}; }
    protected String idPrefix() { return "PAY"; }
    protected boolean useQueue() { return false; }
    protected boolean sortExperience() { return false; }
}
