package app.com.example.althomas04.justjava;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the EditText view is clicked.
     */

    public void onClickEditName(View view) {
        View parentView = findViewById(R.id.parent_view);
        parentView.setFocusableInTouchMode(true);
        editName(view);
    }

    /**
     * This method displays the cursor in edit mode,
     * and calls on the hideKeyboard method when the user taps on another part of the screen.
     */

    public void editName(View view) {
        final EditText nameEditText = (EditText) findViewById(R.id.customer_name);
        if (view.getId() == nameEditText.getId()) {
            nameEditText.setCursorVisible(true);
        }
        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(view);
                }
            }
        });
    }

    /**
     * This method hides the keyboard.
     */

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * The following two methods add toppings to the order summary if checked.
     */

    String yesWhippedCream = "";
    String yesChocolate = "";
    int whippedCreamPrice = 0;
    int chocolatePrice = 0;

    public void hasWhippedCreamChecked(View view) {
        yesWhippedCream = getString(R.string.yes_whipped_cream_text);
        whippedCreamPrice = 1;
    }

    public void hasChocolateChecked(View view) {
        yesChocolate = getString(R.string.yes_chocolate_text);
        chocolatePrice = 2;
    }

    /**
     * The following two methods update the quantity displayed when + or - buttons are clicked.
     * Additionally, the decrement method prevents the user from ordering a negative quantity.
     */

    int quantity = 0;

    public void increment(View view) {
        quantity = quantity + 1;
        display(quantity);
    }
    public void decrement(View view) {
        if (quantity >= 1) {
            quantity = quantity - 1;
        } else return;
        display(quantity);
    }

    /**
     * This method displays the updated quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_number_text_view);
        String finalQuantity = "" + number;
        quantityTextView.setText(finalQuantity);
    }

    /**
     * This method is called when the order button is clicked.
     * It calls on other methods to calculate price, create an order summary, and display the order summary when an item is ordered.
     */

    public void submitOrder(View view) {
        int finalPrice = calculateFinalPrice();
        if (finalPrice > 0) {
            String orderSummary = createOrderSummary(finalPrice);
            displayOrderSummary(orderSummary);
        } else reset(view);
    }

    /**
     * This method calculates the final price.
     */

    private int calculateFinalPrice() {
        int pricePerCup = 4;
        return (quantity * (pricePerCup + whippedCreamPrice + chocolatePrice));
    }

    /**
     * This method creates the final order summary.
     */

    String customerName = "";

    private String createOrderSummary(int priceOfOrder) {
        EditText nameInserted = (EditText) findViewById(R.id.customer_name);
        customerName = nameInserted.getText().toString();
        String totalCurrencyPrice = NumberFormat.getCurrencyInstance().format(priceOfOrder);
        return (getString(R.string.name_field_indicator) + " " + customerName +
                "\n" + yesWhippedCream +
                "\n" + yesChocolate +
                "\n" + getString(R.string.quantity_field_indicator) + " " + quantity +
                "\n" + getString(R.string.total_field_indicator) + " " + totalCurrencyPrice +
                "\n" + getString(R.string.thank_you_text));
    }

    /**
     * This method displays the final order summary on the screen.
     */

    private void displayOrderSummary(String message) {
        if (!message.equals("0")) {
            TextView summaryTextView = (TextView) findViewById(R.id.display_order_summary_text_view);
            summaryTextView.setText(message);
        } else {
            TextView summaryTextView = (TextView) findViewById(R.id.display_order_summary_text_view);
            summaryTextView.setText("");
        }
    }

    /**
     * The following method resets the app when the reset button is clicked.
     */

    public void reset(View view) {
        customerName = "";
        quantity = 0;
        yesWhippedCream = "";
        yesChocolate = "";
        whippedCreamPrice = 0;
        chocolatePrice = 0;
        String resetSummary = "0";
        display(quantity);
        displayOrderSummary(resetSummary);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        whippedCreamCheckBox.setChecked(false);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        chocolateCheckBox.setChecked(false);
    }

    /**
     * The following two methods are called on when email order is clicked.
     * They compose an email subject and a order summary text, and sends an intent to an email app.
     */
    public void emailOrder(View view) {
        TextView summaryTextView = (TextView) findViewById(R.id.display_order_summary_text_view);
        String emailSubject = customerName + getString(R.string.subject_message);
        String emailText = summaryTextView.getText().toString();
        composeEmail(emailSubject, emailText);
    }

    public void composeEmail(String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
