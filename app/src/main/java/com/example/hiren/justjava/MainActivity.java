/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.hiren.justjava;
 */

package com.example.hiren.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hiren.justjava.R;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked. The Intent sends the order summary to the email app.
     */
    public void submitOrder(View view) {
        CheckBox whippedcream = (CheckBox) findViewById(R.id.whipped_cream_box);
        boolean hasWhippedCream = whippedcream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();
        EditText personName = (EditText) findViewById(R.id.person_name);
        String name = personName.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order summary for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(name, price, hasWhippedCream, hasChocolate));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * @param name            The customer's name
     * @param price           The calculated price total of the order
     * @param hasWhippedCream Stores whether Whipped Cream checkbox is checked
     * @param hasChocolate    Stores wether Chocolate checkbox is checked
     * @return orderSummary in text form to inform the user the summary of their order
     */

    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {

        String orderSummary = "Name: " + name + "\n";
        orderSummary += "Add Whipped Cream ?" + hasWhippedCream + "\n";
        orderSummary += "Add Chocolate ?" + hasChocolate + "\n";
        orderSummary += "Quantity: " + quantity + "\n";
        orderSummary += "Total: $" + price + "\n";
        orderSummary += "Thank You!";
        return orderSummary;
    }

    /**
     * Calculates the price of the order.
     *
     * @param hasChocolate    checks if users wants  chocolate topping
     * @param hasWhippedCream checks if user wants Whipped Cream topping
     * @return returns the total price of coffee ordered
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {

        int basePrice = 5;
        //Adds $1 if user wants to add Whipped Cream topping
        if (hasWhippedCream) {
            basePrice = basePrice + 1;
        }
        //Adds $2 if user wants to add Chocolate topping
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    // private void displayMessage(String message) {
    //   TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
    //  orderSummaryTextView.setText(message);
    // }

    /**
     * This method increases the number of cups of coffee desired by the user, it is called
     * when the plus button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        if (quantity >= 100) {
            quantity = 100;
            Toast.makeText(this, "You cannot have more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method decreases the number of cups of coffee desired by the user, it is called
     * when the minus button is clicked.
     */
    public void decrement(View view) {
        quantity = quantity - 1;
        if (quantity <= 1) {
            quantity = 1;
            Toast.makeText(this, "You cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }
}