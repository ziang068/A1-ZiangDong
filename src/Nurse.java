import java.util.List;

/**
 * Part 2 - nurse, a health professional who assists in a ward.
 */
public class Nurse extends HealthProfessional {

    private final String ward;
    private final boolean qualifiedToTakeBlood;

    public Nurse(int professionalId, String fullName, List<String> workingDays,
                 String ward, boolean qualifiedToTakeBlood) {
        super(professionalId, fullName, workingDays);
        if (ward == null || ward.isBlank()) {
            throw new IllegalArgumentException("Ward cannot be empty.");
        }
        this.ward = ward;
        this.qualifiedToTakeBlood = qualifiedToTakeBlood;
    }

    public String getWard() {
        return ward;
    }

    public boolean isQualifiedToTakeBlood() {
        return qualifiedToTakeBlood;
    }

    /**
     * Behaviour specific to a nurse.
     *
     * @return confirmation message, or a warning when not qualified
     */
    public String takeBloodSample() {
        if (!qualifiedToTakeBlood) {
            return "Nurse " + getFullName() + " is not qualified to take blood samples.";
        }
        return "Nurse " + getFullName() + " collected a blood sample in " + ward;
    }

    @Override
    public String describeRole() {
        return "Nurse";
    }

    @Override
    public String toString() {
        return "[" + describeRole() + "] " + super.toString()
                + " | Ward: " + ward + " | Qualified for blood sampling: " + qualifiedToTakeBlood;
    }
}
