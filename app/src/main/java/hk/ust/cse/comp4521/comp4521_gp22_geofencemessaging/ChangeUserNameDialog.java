package hk.ust.cse.comp4521.comp4521_gp22_geofencemessaging;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ChangeUserNameDialog extends DialogFragment{
    public interface ChangeUserNameDialogListener{
        public void onReturnUsername(String username);

    }

    public ChangeUserNameDialog() {}

    ChangeUserNameDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //Inflat and set layout,null because going in the dialog layout
        final View dialogView = inflater.inflate(R.layout.dialog_change_username,null);
        builder.setView(dialogView)
                .setPositiveButton("Send",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        //do the request

                        EditText username = (EditText) dialogView.findViewById(R.id.new_username);
                        mListener.onReturnUsername(username.getText().toString().trim());

                    }
                })
                .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        //((NewAccountActivity)getActivity()).onDialogDismissed();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ChangeUserNameDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ChangePasswordListener");
        }
    }
}
