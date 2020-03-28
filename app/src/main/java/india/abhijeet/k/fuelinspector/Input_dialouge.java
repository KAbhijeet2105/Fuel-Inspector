package india.abhijeet.k.fuelinspector;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class Input_dialouge extends AppCompatDialogFragment {

    EditText editRunConsumption,editRunCostFuel;

    InputDialougeListener inputDialougeListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater inflater =getActivity().getLayoutInflater();

        View view=inflater.inflate(R.layout.input_dialouge,null);

        builder.setView(view)
                .setTitle("Enter Following Data:")
                .setIcon(R.mipmap.ic_launcher)
                .setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Calculate Result", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String runConsum=editRunConsumption.getText().toString().trim();
                        String runFuelCost=editRunCostFuel.getText().toString().trim();

                       inputDialougeListener.applyData(runConsum,runFuelCost);

                    }
                });

        editRunConsumption=view.findViewById(R.id.runConsumption);
        editRunCostFuel=view.findViewById(R.id.runFuelCost);


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            inputDialougeListener =(InputDialougeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"Class must implement the InputDialouge Listener");
        }

    }

    public interface InputDialougeListener
    {

        void applyData(String runConsump,String runFuelCost);
    }


}
