import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Part 5 - keeps every appointment of the clinic and enforces the booking
 * rules.
 *
 * The clinic is open from 08:00 to 16:00 with fixed 30 minute slots. The slots
 * live in one configurable list, so opening hours can be changed in a single
 * place later.
 */
public class AppointmentManager {

    private final List<Appointment> appointments = new ArrayList<>();
    private final List<String> timeSlots;

    /**
     * Builds the standard opening hours: 08:00 to 16:00, every 30 minutes.
     *
     * @return the list of time slots
     */
    public static List<String> buildDefaultTimeSlots() {
        List<String> slots = new ArrayList<>();
        int startMinutes = 8 * 60;   // 08:00
        int endMinutes = 16 * 60;    // 16:00
        for (int minutes = startMinutes; minutes <= endMinutes; minutes += 30) {
            slots.add(String.format("%02d:%02d", minutes / 60, minutes % 60));
        }
        return slots;
    }

    /**
     * Creates a manager with the default opening hours.
     */
    public AppointmentManager() {
        this(buildDefaultTimeSlots());
    }

    /**
     * Creates a manager with custom opening hours, so the clinic timetable can
     * be changed without touching the rest of the code.
     *
     * @param timeSlots allowed time slots
     */
    public AppointmentManager(List<String> timeSlots) {
        if (timeSlots == null || timeSlots.isEmpty()) {
            throw new IllegalArgumentException("At least one time slot is required.");
        }
        this.timeSlots = new ArrayList<>(timeSlots);
    }

    public List<String> getTimeSlots() {
        return new ArrayList<>(timeSlots);
    }

    /**
     * Adds an appointment. The booking is rejected with a warning when the slot
     * is outside opening hours or when that professional already has a booking
     * in the same slot.
     *
     * @param appointment new appointment
     * @return true when the appointment was stored
     */
    public boolean addAppointment(Appointment appointment) {
        if (appointment == null) {
            System.out.println("WARNING: appointment cannot be null.");
            return false;
        }
        if (!timeSlots.contains(appointment.getTimeSlot())) {
            System.out.println("WARNING: " + appointment.getTimeSlot()
                    + " is outside the clinic opening hours.");
            return false;
        }
        if (isProfessionalBusy(appointment.getProfessional(), appointment.getTimeSlot())) {
            System.out.println("WARNING: " + appointment.getProfessional().getFullName()
                    + " (ID " + appointment.getProfessional().getProfessionalId()
                    + ") is already booked at " + appointment.getTimeSlot() + ".");
            return false;
        }
        appointments.add(appointment);
        return true;
    }

    /**
     * @param professional health professional to check
     * @param timeSlot     time slot to check
     * @return true when the professional already has a booking in that slot
     */
    public boolean isProfessionalBusy(HealthProfessional professional, String timeSlot) {
        for (Appointment existing : appointments) {
            // equals() of HealthProfessional compares the staff ID
            if (existing.getProfessional().equals(professional)
                    && existing.getTimeSlot().equals(timeSlot)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return every stored appointment
     */
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    /**
     * @param professionalId numeric staff ID
     * @return all appointments of that professional, sorted by time
     */
    public List<Appointment> filterByProfessionalId(int professionalId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getProfessional().getProfessionalId() == professionalId) {
                result.add(appointment);
            }
        }
        return sortByTime(result);
    }

    /**
     * @param patientMobile mobile number of the patient
     * @return all appointments of that patient, sorted by time
     */
    public List<Appointment> filterByPatientMobile(String patientMobile) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientMobile().equals(patientMobile)) {
                result.add(appointment);
            }
        }
        return sortByTime(result);
    }

    /**
     * Sorts appointments from the earliest slot to the latest slot using the
     * Comparable implementation of Appointment.
     *
     * @param source appointments to sort
     * @return a new sorted list
     */
    public List<Appointment> sortByTime(List<Appointment> source) {
        List<Appointment> result = new ArrayList<>(source);
        Collections.sort(result);
        return result;
    }

    /**
     * Cancels one appointment identified by the professional and the slot.
     *
     * @param professionalId numeric staff ID
     * @param timeSlot       time slot of the booking
     * @return true when a matching appointment was removed
     */
    public boolean cancelByProfessionalAndTime(int professionalId, String timeSlot) {
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getProfessional().getProfessionalId() == professionalId
                    && appointment.getTimeSlot().equals(timeSlot)) {
                appointments.remove(i);
                return true;
            }
        }
        System.out.println("WARNING: no appointment found for professional ID "
                + professionalId + " at " + timeSlot + ".");
        return false;
    }

    /**
     * Cancels every appointment of one patient, identified by mobile number.
     *
     * @param patientMobile mobile number of the patient
     * @return the number of cancelled appointments
     */
    public int cancelByPatientMobile(String patientMobile) {
        List<Appointment> matching = filterByPatientMobile(patientMobile);
        if (matching.isEmpty()) {
            System.out.println("WARNING: no appointment found for mobile "
                    + patientMobile + ".");
            return 0;
        }
        appointments.removeAll(matching);
        return matching.size();
    }

    /**
     * Prints a list of appointments in a readable format.
     *
     * @param list appointments to print
     */
    public void printAppointments(List<Appointment> list) {
        if (list.isEmpty()) {
            System.out.println("  (no appointments)");
            return;
        }
        for (Appointment appointment : list) {
            System.out.println("  " + appointment);
        }
    }
}
