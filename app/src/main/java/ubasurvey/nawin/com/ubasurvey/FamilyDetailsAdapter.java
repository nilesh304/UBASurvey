package ubasurvey.nawin.com.ubasurvey;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View; import android.view.ViewGroup; import android.widget.TextView; import java.util.List;

public class FamilyDetailsAdapter extends RecyclerView.Adapter<FamilyDetailsAdapter.MyViewHolder> {

    private List<FamilyDetails> familyList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView familyMemberName, familyMemberID;

        public MyViewHolder(View view) {
            super(view);
            familyMemberName = (TextView) view.findViewById(R.id.familymemberdetail);
           // familyMemberID = (TextView) view.findViewById(R.id.familyid);
        }
    }


    public FamilyDetailsAdapter(List<FamilyDetails> familyList) {
        this.familyList = familyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { 
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.familydetails_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) { 
        FamilyDetails family = familyList.get(position); 
        holder.familyMemberName.setText(family.getUbaindid().toString()+" . "+family.getName());
        //holder.familyMemberID.setText(family.getUbaindid().toString());
        
    }

    @Override
    public int getItemCount() {
        return familyList.size();
    }
    
}
