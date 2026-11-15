import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Part 1 - generic health professional working in the clinic.
 *
 * This class is declared abstract on purpose: a "plain" health professional
 * that has no specific role does not exist in the clinic, so the class must
 * never be instantiated directly. Every real staff member is created through
 * one of the concrete subclasses in Part 2.
 */
public abstract class HealthProfessional {

    private final int professionalId;
    private final String fullName;
    private final List<String> workingDays;

    /**
     * Creates a health professional with a numeric ID, a name and the days
     * of the week the person normally works.
     *
     * @param professionalId numeric staff ID, must be positive
     * @param fullName       full name of the professional
     * @param workingDays    days of the week the professional works
     */
    public HealthProfessional(int professionalId, String fullName, List<String> workingDays) {
        if (professionalId <= 0) {
            throw new IllegalArgumentException("Professional ID must be a positive number.");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name cannot be empty.");
        }
        if (workingDays == null || workingDays.isEmpty()) {
            throw new IllegalArgumentException("At least one working day is required.");
        }
        this.professionalId = professionalId;
        this.fullName = fullName;
        // Defensive copy: the caller cannot change the object state afterwards.
        this.workingDays = new ArrayList<>(workingDays);
    }

    /**
     * Overloaded constructor: uses the standard Monday to Friday work week.
     *
     * @param professionalId numeric staff ID
     * @param fullName       full name of the professional
     */
    public HealthProfessional(int professionalId, String fullName) {
        this(professionalId, fullName,
                List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"));
    }

    public int getProfessionalId() {
        return professionalId;
    }

    public String getFullName() {
        return fullName;
    }

    /**
     * @return a copy of the working days so the internal list stays protected
     */
    public List<String> getWorkingDays() {
        return new ArrayList<>(workingDays);
    }

    /**
     * @param day day of the week, for example "Monday"
     * @return true when the professional works on that day
     */
    public boolean worksOn(String day) {
        return day != null && workingDays.contains(day);
    }

    /**
     * Describes the specific role of the professional. It is abstract because
     * only a concrete subclass knows what role it represents.
     *
     * @return the role name
     */
    public abstract String describeRole();

    /**
     * Common information shared by every health professional. Subclasses call
     * this method through super.toString() and add their own details.
     */
    @Override
    public String toString() {
        return String.format("ID: %-4d | Name: %-24s | Working days: %s",
                professionalId, fullName, workingDays);
    }

    /**
     * Two professionals are considered the same person when they share the
     * same numeric staff ID. Used by the appointment manager to detect a
     * double booking.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HealthProfessional)) {
            return false;
        }
        HealthProfessional that = (HealthProfessional) other;
        return professionalId == that.professionalId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(professionalId);
    }
}
