package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view){

        if (quantity == 100){
            Toast.makeText(getApplicationContext(),getString(R.string.over_order_coffee), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity ++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement (View view){

        if (quantity == 1){
            Toast.makeText(getApplicationContext(),getString(R.string.less_order_coffee), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText nameEditText = findViewById(R.id.name_edit_text);
        String nameInput = nameEditText.getText().toString();

        CheckBox whippedCreamCheckboxState = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckboxState.isChecked();

        CheckBox chocolateCheckBoxState = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBoxState.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummary= createOrderSummary(price, hasWhippedCream, hasChocolate, nameInput);


        //displayMessage(orderSummary);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject_for_order) + nameInput);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * This method displays the order summary on the screen.
     * @param price - Total Price
     * @param hasWhippedCream - does the order has whipped cram as topping or not
     * @param hasChocolate - does the order has chocolate as topping or not
     * @param nameInput - user name as input from editText
     * @return Order Summary
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String nameInput){
        String orderMessage =getString(R.string.order_summary_name)+ nameInput;
        orderMessage += "\n" + getString(R.string.order_summary_whipped_cream) + hasWhippedCream;
        orderMessage += "\n" +getString(R.string.order_summary_chocolate) +hasChocolate;
        orderMessage += "\n"+ getString(R.string.order_summary_quantity)+quantity;
        orderMessage += "\n"+ getString(R.string.order_summary_total)+price;
        orderMessage += "\n"+ getString(R.string.thank_you);
        return orderMessage;
    }

    /**
     * This method calculates coffee price.
     *
     * @param hasCreamTopping does the user want to add Cream Topping or not.
     * @param hasChocolteTopping does the user want to add Chocolate Topping or not.
     * @return total price for order.
     */
    private int calculatePrice(boolean hasCreamTopping, boolean hasChocolteTopping){

        int coffeePrice = 12;

        //add £1 if the user add Cream
        if (hasCreamTopping){coffeePrice+=1;}

        //add £2 if the user add chocolate
        if (hasChocolteTopping) {coffeePrice+=2;}

        return quantity*coffeePrice;
    }


    /**
     * This method displays the given quantity value on the screen.
     * @params numQuantity - Quantity of Cups of Coffee.
     */
    private void displayQuantity(int numQuantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+numQuantity);
    }

    /**
     * This method displays the string message on the screen.
     * @param message - Order Summary Text.
     */
    private void displayMessage (String message){
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}