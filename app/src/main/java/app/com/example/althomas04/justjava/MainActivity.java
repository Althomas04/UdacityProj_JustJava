package app.com.example.althomas04.justjava;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
        String priceMessage = "Total " + NumberFormat.getCurrencyInstance().format(price);
        priceMessage = priceMessage + "\n Thank You!";
        displayMessage(priceMessage);}
        else reset(view);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */

    private void displayMessage(String message) {
        if (message != "0") {
            TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
            priceTextView.setText(message);
        } else {
            TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
            priceTextView.setText("$0");
        }
    }

    private int calculatePrice(){
        int pricePerCup = 4;
        return(quantity * pricePerCup);
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
    }
}
