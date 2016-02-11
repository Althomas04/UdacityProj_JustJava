package app.com.example.althomas04.justjava;

import android.app.Activity;
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

    public void hasWhippedCreamChecked(View view) {
        if (true) {
            yesWhippedCream = "\n+Whipped Cream";
        } else {
            yesWhippedCream = "";
        }
    }

    public void hasChocolateChecked(View view) {
        if (true) {
            yesChocolate = "\n+Chocolate";
        } else {
            yesChocolate = "";
        }
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
        } else {
            quantity = 0;
        }
        display(quantity);
    }

    /**
     * This method displays the updated quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
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
        return (quantity * pricePerCup);
    }

    /**
     * This method creates the final order summary.
     */

    String customerName = "";

    private String createOrderSummary(int priceOfOrder) {
        EditText nameInserted = (EditText) findViewById(R.id.customer_name);
        customerName = nameInserted.getText().toString();
        String totalCurrencyPrice = NumberFormat.getCurrencyInstance().format(priceOfOrder);
        return ("Name: " + customerName +
                yesWhippedCream + yesChocolate +
                "\nQuantity: " + quantity +
                "\nTotal: " + totalCurrencyPrice +
                "\nThank You");
    }

    /**
     * This method displays the final order summary on the screen.
     */

    private void displayOrderSummary(String message) {
        if (!message.equals("0")) {
            TextView summaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
            summaryTextView.setText(message);
        } else {
            TextView summaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
            summaryTextView.setText("$0");
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
        String resetSummary = "0";
        display(quantity);
        displayOrderSummary(resetSummary);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        whippedCreamCheckBox.setChecked(false);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        chocolateCheckBox.setChecked(false);
    }

}
