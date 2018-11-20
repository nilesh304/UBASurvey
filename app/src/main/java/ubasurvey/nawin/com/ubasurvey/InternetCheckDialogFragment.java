package ubasurvey.nawin.com.ubasurvey;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


/**
 * A simple  subclass.
 */
public class InternetCheckDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//       String [] res= savedInstanceState.getStringArray("status");
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon
                .setIcon(R.drawable.uba)
                // Set Dialog Title
                .setTitle("Check Internet Conn")
                // Set Dialog Message
                .setMessage("Switch on WiFi/mobile data")

                // Positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }

}
