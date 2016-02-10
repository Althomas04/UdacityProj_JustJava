package app.com.example.althomas04.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
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
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice();
        if (price > 0){
            String priceMessage = createOrderSummary(price);
        displayMessage(priceMessage);}
        else reset(view);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        String finalQuantity = "" + number;
        quantityTextView.setText(finalQuantity);
    }

    /**
     * This method displays the given text on the screen.
     */

    private void displayMessage(String message) {
        if (!message.equals("0")) {
            TextView summaryTextView = (TextView) findViewById(R.id.summary_text_view);
            summaryTextView.setText(message);
        } else {
            TextView summaryTextView = (TextView) findViewById(R.id.summary_text_view);
            summaryTextView.setText("$0");
        }
    }

    private int calculatePrice(){
        int pricePerCup = 4;
        return(quantity * pricePerCup);
    }

    String yesWhippedCream = "";

    public void hasWhippedCreamChecked(View view) {
        if (true) {
            yesWhippedCream = "\n+Whipped Cream";
        } else {
            yesWhippedCream = "";
        }
    }

    private String createOrderSummary(int priceOfOrder) {
        String totalCurrencyPrice = NumberFormat.getCurrencyInstance().format(priceOfOrder);
        String orderSummary = "Name: Alpha Albert" +
                yesWhippedCream +
                "\nQuantity: " + quantity +
                "\nTotal: " + totalCurrencyPrice +
                "\nThank You";
        return orderSummary;
    }

    int quantity = 0;

    public void increment(View view) {
        quantity = quantity + 1;
        display(quantity);
    }
    public void decrement(View view) {
        if (quantity>=1) {
            quantity = quantity - 1;
        }
        else {
            quantity = 0;
        }
        display(quantity);
    }
    public void reset(View view) {
        quantity = 0;
        String resetMessage = "0";
        display(quantity);
        displayMessage(resetMessage);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        whippedCreamCheckBox.setChecked(false);
    }
}
