import java.util.List;

/**
 * Part 2 - specialist, a health professional focused on one area of medicine.
 */
public class Specialist extends HealthProfessional {

    private final String specialtyArea;

    public Specialist(int professionalId, String fullName,
                      List<String> workingDays, String specialtyArea) {
        super(professionalId, fullName, workingDays);
        if (specialtyArea == null || specialtyArea.isBlank()) {
            throw new IllegalArgumentException("Specialty area cannot be empty.");
        }
        this.specialtyArea = specialtyArea;
    }

    public String getSpecialtyArea() {
        return specialtyArea;
    }

    /**
     * A specialist can order a diagnostic test, behaviour that only this
     * subclass offers.
     *
     * @param testName name of the test
     * @return confirmation message
     */
    public String orderDiagnosticTest(String testName) {
        return "Specialist " + getFullName() + " ordered " + testName;
    }

    @Override
    public String describeRole() {
        return "Specialist";
    }

    @Override
    public String toString() {
        return "[" + describeRole() + "] " + super.toString()
                + " | Specialty: " + specialtyArea;
    }
}
