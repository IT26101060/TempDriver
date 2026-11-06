package lk.drivingschool.util;
import java.util.UUID;
import java.util.regex.Pattern;

public final class ValidationUtil {
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private ValidationUtil() {}
    public static String newId() { return UUID.randomUUID().toString(); }
    public static String required(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " is required");
        return value.trim();
    }
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL.matcher(email.trim()).matches();
    }
    public static int positiveInt(String value, String field) {
        try { int n = Integer.parseInt(value); if (n > 0) return n; }
        catch (Exception ignored) {}
        throw new IllegalArgumentException(field + " must be a positive integer");
    }
    public static double positiveAmount(String value) {
        try { double n = Double.parseDouble(value); if (Double.isFinite(n) && n > 0) return n; }
        catch (Exception ignored) {}
        throw new IllegalArgumentException("Amount must be a positive number");
    }
}
