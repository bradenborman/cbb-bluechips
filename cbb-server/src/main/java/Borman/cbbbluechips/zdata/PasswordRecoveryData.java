package Borman.cbbbluechips.zdata;

public class PasswordRecoveryData {

    public static String GENERIC_SUCCESS_MESSAGE = "An Email containing the password for the requested account has been sent. " +
            "Try searching inbox for: cbb.bluechips.donotreply@gmail.com";

    public static String GENERIC_FAILED_MESSAGE = "Failed to recover password. Please try again and validate email.";

    public static String PASSWORD_RECOVERY_BODY = "<p style=\"font-size: 1.3em;\">As requested, here is your password: <i><b>${password}</b></i></p>" +
            "<br /><br /><p>Thank you again for playing and making this possible.</p><p>Braden Borman<br/>573 826-1903</p>";

    public static String DONATION_CONFIRMED_BODY = "<p style=\"font-size: 1.2em;\">Keep this email as a receipt.</p>" +
            "<p><b>Order ID:</b> ${order_id}<br />" +
            "<b>Description:</b> ${description}<br />" +
            "<b>Total:<b/> <span style=\"color: #0c4510;\">$${amount}</span></p>" +
            "<p>You will be awarded an extra $50,000 in in-game money.</p>";

}
