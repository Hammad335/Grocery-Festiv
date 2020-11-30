package com.codewithhamad.onlinestore;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.codewithhamad.onlinestore.GroceryItemActivity.GROCERY_ITEM_KEY;

public class AddReviewDialog extends DialogFragment {
    private static final String TAG = "AddReviewDialog";

    public interface AddReview {
        void onAddReviewResult(Review review);
    }

    private AddReview addReview;

    private TextView txtItemName, txtWarning;
    private EditText edtTxtUserName;
    private EditText edtTxtReview;
    private Button btnAddReview;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_review, null);
        initViews(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            final GroceryItem item = bundle.getParcelable(GROCERY_ITEM_KEY);
            if (item != null) {
                txtItemName.setText(item.getName());
                Log.d(TAG, "onCreateDialog: before btnAddReview onClick");
                btnAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: started");
                        String userName = edtTxtUserName.getText().toString();
                        String review = edtTxtReview.getText().toString();
//                        String date = getCurrentDate();
                        // TODO: 9/8/2020 set current date here
                        String date= "12-9-2020";
                        Log.d(TAG, "onClick: user name: " + userName);
                        Log.d(TAG, "onClick: review: " + review);

                        if (userName.equals("") || review.equals("")) {
                            txtWarning.setText("Fill all the blanks");
                            txtWarning.setVisibility(View.VISIBLE);
                        }
                        else {
                            txtWarning.setVisibility(View.GONE);
                            try {
                                addReview = (AddReview) getActivity();
                                addReview.onAddReviewResult(new Review(item.getId(), userName, review, date));
                                dismiss();
                            }catch (ClassCastException e) {
                                Log.d(TAG, "onClick: catch body started");
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }

        return builder.create();
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YYYY");
        return sdf.format(calendar.getTime());
    }

    private void initViews(View view) {
        txtItemName = view.findViewById(R.id.txtItemName);
        txtWarning = view.findViewById(R.id.txtWarning);
        edtTxtUserName = view.findViewById(R.id.edtTxtUserName);
        edtTxtReview = view.findViewById(R.id.edtTxtReview);
        btnAddReview = view.findViewById(R.id.btnAddReview);
    }
}
