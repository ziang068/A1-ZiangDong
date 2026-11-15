import java.util.List;

/**
 * Part 2 - dietitian, a health professional who designs meal plans.
 */
public class Dietitian extends HealthProfessional {

    private final String mainExpertise;

    public Dietitian(int professionalId, String fullName,
                     List<String> workingDays, String mainExpertise) {
        super(professionalId, fullName, workingDays);
        if (mainExpertise == null || mainExpertise.isBlank()) {
            throw new IllegalArgumentException("Main expertise cannot be empty.");
        }
        this.mainExpertise = mainExpertise;
    }

    public String getMainExpertise() {
        return mainExpertise;
    }

    /**
     * Behaviour specific to a dietitian.
     *
     * @param patientName name of the patient
     * @return confirmation message
     */
    public String designMealPlan(String patientName) {
        return "Dietitian " + getFullName() + " designed a " + mainExpertise
                + " meal plan for " + patientName;
    }

    @Override
    public String describeRole() {
        return "Dietitian";
    }

    @Override
    public String toString() {
        return "[" + describeRole() + "] " + super.toString()
                + " | Main expertise: " + mainExpertise;
    }
}
