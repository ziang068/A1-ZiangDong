import java.util.ArrayList;
import java.util.List;

/**
 * Entry point of the assignment.
 *
 * Part 3 shows how health professional objects are created and printed.
 * Part 6 runs a complete clinic scenario, including the error cases required
 * by the brief: a double booking and a cancellation of a booking that does not
 * exist.
 */
public class AssignmentOne {

    public static void main(String[] args) {
        System.out.println("=============== PART 3: HEALTH PROFESSIONALS ===============");
        List<HealthProfessional> professionals = showHealthProfessionals();

        System.out.println();
        System.out.println("=============== PART 6: CLINIC APPOINTMENT DEMO ===============");
        runAppointmentDemo(professionals);
    }

    /**
     * Part 3 - creates several professionals of different subclasses and keeps
     * them in one collection of the parent type. The loop then prints each
     * object through polymorphism, so no duplicate printing code is needed.
     */
    private static List<HealthProfessional> showHealthProfessionals() {
        List<String> fullTime = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        List<String> partTime = List.of("Monday", "Wednesday", "Friday");

        HealthProfessional gpOne = new GeneralPractitioner(101, "Dr Alice Green", fullTime, "Room 3");
        HealthProfessional gpTwo = new GeneralPractitioner(102, "Dr Ben Carter", partTime, "Room 5");
        HealthProfessional gpThree = new GeneralPractitioner(103, "Dr Chloe Martin", fullTime, "Room 8");
        HealthProfessional specialist = new Specialist(201, "Dr David Nguyen", partTime, "Cardiology");
        HealthProfessional nurse = new Nurse(301, "Nurse Emma Walsh", fullTime, "Ward A", true);
        HealthProfessional dietitian = new Dietitian(401, "Ms Fiona Reed", partTime, "Diabetes nutrition");

        // One collection of the parent type holds objects of four subclasses.
        List<HealthProfessional> professionals = new ArrayList<>();
        professionals.add(gpOne);
        professionals.add(gpTwo);
        professionals.add(gpThree);
        professionals.add(specialist);
        professionals.add(nurse);
        professionals.add(dietitian);

        System.out.println("Registered health professionals: " + professionals.size());
        for (HealthProfessional professional : professionals) {
            // Polymorphism: the correct toString() of each subclass is called.
            System.out.println("  " + professional);
        }

        System.out.println();
        System.out.println("Role specific behaviour:");
        System.out.println("  " + ((GeneralPractitioner) gpOne).issueRepeatPrescription("Metformin"));
        System.out.println("  " + ((Specialist) specialist).orderDiagnosticTest("ECG"));
        System.out.println("  " + ((Nurse) nurse).takeBloodSample());
        System.out.println("  " + ((Dietitian) dietitian).designMealPlan("Mr Tom Baker"));

        System.out.println();
        System.out.println("Equality check on staff ID:");
        HealthProfessional sameIdAsGpOne = new GeneralPractitioner(101, "Dr A Green", fullTime, "Room 3");
        System.out.println("  gpOne.equals(sameIdAsGpOne) = " + gpOne.equals(sameIdAsGpOne));

        // The clinic uses these professionals in the appointment demo below.
        return professionals;
    }

    /**
     * Part 6 - runs the complete appointment scenario, including the error
     * cases required by the brief.
     *
     * @param professionals the list built in Part 3
     */
    private static void runAppointmentDemo(List<HealthProfessional> professionals) {
        HealthProfessional gpOne = professionals.get(0);
        HealthProfessional gpTwo = professionals.get(1);
        HealthProfessional specialist = professionals.get(3);
        HealthProfessional nurse = professionals.get(4);
        HealthProfessional dietitian = professionals.get(5);

        AppointmentManager manager = new AppointmentManager();
        System.out.println();
        System.out.println("Clinic opening hours: " + manager.getTimeSlots().get(0)
                + " to " + manager.getTimeSlots().get(manager.getTimeSlots().size() - 1)
                + " (30 minute slots)");

        System.out.println();
        System.out.println("--- 1. Add valid appointments ---");
        addAndReport(manager, new Appointment("John Smith", "0411222333", gpOne, "08:00"));
        addAndReport(manager, new Appointment("Mary Jones", "0422333444", specialist, "09:30"));
        addAndReport(manager, new Appointment("Peter Brown", "0433444555", nurse, "08:30"));
        addAndReport(manager, new Appointment("Lucy Chen", "0444555666", gpOne, "11:00"));
        addAndReport(manager, new Appointment("John Smith", "0411222333", dietitian, "13:00"));
        addAndReport(manager, new Appointment("Sam Wilson", "0455666777", gpTwo, "15:30"));

        System.out.println();
        System.out.println("--- 2. All appointments ---");
        manager.printAppointments(manager.getAllAppointments());

        System.out.println();
        System.out.println("--- 3. All appointments sorted by time ---");
        manager.printAppointments(manager.sortByTime(manager.getAllAppointments()));

        System.out.println();
        System.out.println("--- 4. Appointments for professional ID 101 (gpOne) ---");
        manager.printAppointments(manager.filterByProfessionalId(101));

        System.out.println();
        System.out.println("--- 5. Appointments for patient mobile 0411222333 ---");
        manager.printAppointments(manager.filterByPatientMobile("0411222333"));

        System.out.println();
        System.out.println("--- 6. ERROR CASE: double booking of gpOne at 08:00 ---");
        addAndReport(manager, new Appointment("Anna Lee", "0466777888", gpOne, "08:00"));

        System.out.println();
        System.out.println("--- 7. ERROR CASE: booking outside opening hours (17:00) ---");
        addAndReport(manager, new Appointment("Chris Hall", "0477888999", nurse, "17:00"));

        System.out.println();
        System.out.println("--- 8. Cancel appointment of professional 201 at 09:30 ---");
        boolean cancelled = manager.cancelByProfessionalAndTime(201, "09:30");
        System.out.println("  cancellation result: " + cancelled);

        System.out.println();
        System.out.println("--- 9. ERROR CASE: cancel a booking that does not exist ---");
        boolean cancelledNothing = manager.cancelByProfessionalAndTime(999, "10:00");
        System.out.println("  cancellation result: " + cancelledNothing);

        System.out.println();
        System.out.println("--- 10. Appointments after the cancellation ---");
        manager.printAppointments(manager.sortByTime(manager.getAllAppointments()));

        System.out.println();
        System.out.println("--- 11. Cancel every booking of patient mobile 0411222333 ---");
        int removed = manager.cancelByPatientMobile("0411222333");
        System.out.println("  cancelled appointments: " + removed);

        System.out.println();
        System.out.println("--- 12. ERROR CASE: cancel for an unknown mobile number ---");
        int removedNothing = manager.cancelByPatientMobile("0400000000");
        System.out.println("  cancelled appointments: " + removedNothing);

        System.out.println();
        System.out.println("--- 13. Final appointment list ---");
        manager.printAppointments(manager.sortByTime(manager.getAllAppointments()));
        System.out.println();
        System.out.println("=============== DEMO FINISHED ===============");
    }

    /**
     * Adds one appointment and prints the outcome, so the console clearly shows
     * which bookings were accepted and which were rejected.
     */
    private static void addAndReport(AppointmentManager manager, Appointment appointment) {
        boolean added = manager.addAppointment(appointment);
        if (added) {
            System.out.println("  ACCEPTED: " + appointment);
        } else {
            System.out.println("  REJECTED: " + appointment.getPatientName()
                    + " with professional ID " + appointment.getProfessional().getProfessionalId()
                    + " at " + appointment.getTimeSlot());
        }
    }
}
