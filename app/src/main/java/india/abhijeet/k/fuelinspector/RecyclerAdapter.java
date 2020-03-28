package india.abhijeet.k.fuelinspector;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context mcontext;

    public RecyclerAdapter(Context context){

        mcontext=context;

    }

    private String[] titles = {"By Distance(Petrol/Diesel)",
            "Fuel Price",
            "By Distance(Electric)",
            "Calculate Trip(For Testing)",
           // "Journeys",
           // "Vehicle expenses",
            "Remove ads",
            };

    private String[] details = {"Calculate cost of fuel by distance.",
            "Price of fuel.\n (Note:-Only for India!)",
            "Calculate cost for Electric vehicle.",
            "Calculate Trip distance,cost,total fuel.",
           // "Track your Journeys.",
           // "Track your expenses here.",
            "Remove ads for free!",
            };

    private int[] images = { R.drawable.ic_navigate_next_black_24dp,
            R.drawable.ic_navigate_next_black_24dp,
            R.drawable.ic_navigate_next_black_24dp,
            R.drawable.ic_navigate_next_black_24dp,
          //  R.drawable.ic_navigate_next_black_24dp,
          //  R.drawable.ic_navigate_next_black_24dp,
            R.drawable.ic_navigate_next_black_24dp,
            };

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;
         public boolean isConnected;
        ConnectivityManager cm = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();






        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.imgvw);
            itemTitle = (TextView)itemView.findViewById(R.id.txtnm);
            itemDetail = (TextView)itemView.findViewById(R.id.txtdiscript);





            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

//                    Snackbar.make(v, "Click detected on item " + position,
//                            Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();

                    isConnected = activeNetwork != null &&
                            activeNetwork.isConnectedOrConnecting();


                    try {


                       switch (position)
                       {
                           case 0:
                               Intent intent = new Intent(mcontext, StaticDistance.class);
                               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                               mcontext.startActivity(intent);
                               break;
                           case 1:

                               if(isConnected) {
                                   intent = new Intent(mcontext, Fuel_Price.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                   mcontext.startActivity(intent);
                               }else {

                                   Snackbar.make(v, "Check your Internet connection! ",
                                           Snackbar.LENGTH_LONG)
                                           .setAction("", null).show();

                               }
                               break;


                           case 2:
                                intent = new Intent(mcontext, ElectricCalculator.class);
                               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                               mcontext.startActivity(intent);
                               break;


                           case 3:
                               intent = new Intent(mcontext, TripCounterGUI.class);
                               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                               mcontext.startActivity(intent);
                               break;


                           case 4:
                               intent = new Intent(mcontext, RewardVideoActivity.class);
                               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                               mcontext.startActivity(intent);
                               break;


                           case 5:
                               intent = new Intent(mcontext, AddVehicleExpenseData.class);
                               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                               mcontext.startActivity(intent);
                               break;

                               default:
                                   break;

                       }

                    }catch (Exception ec){}









                }
            });
        }
    }





    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.main_cards_disp, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try {
            viewHolder.itemTitle.setText(titles[i]);
            viewHolder.itemDetail.setText(details[i]);
            viewHolder.itemImage.setImageResource(images[i]);
        }
        catch (Exception exe)
        {
            exe.printStackTrace();

        }

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}