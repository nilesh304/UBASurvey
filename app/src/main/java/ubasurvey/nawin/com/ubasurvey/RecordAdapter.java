package ubasurvey.nawin.com.ubasurvey;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View; import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private RecordsAdapterListener listener;

    private List<Record> RecordList;

    private static ClickListener clickListener;



    private List<Record> RecordListFiltered;
    private List<Record> RecordListFilteredCopy;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        RecordAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
    public interface RecordsAdapterListener {
        void onRecordSelected(Record record);
    }




    public RecordAdapter(Context context, List<Record> RecordList, RecordsAdapterListener listener) {
       //  RecordListCopy=new ArrayList<>();
        this.context = context;
        this.listener = listener;
        this.RecordList = RecordList;
        this.RecordListFiltered = RecordList;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    { View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.record_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Record Record = RecordListFiltered.get(position);
    holder.title.setText(Record.getTitle());
    holder.genre.setText(Record.getGenre());
    holder.year.setText(Record.getYear());
    }

    @Override
    public int getItemCount() {
        return RecordListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {


                String charString = charSequence.toString();
                //Toast.makeText(context, "in filter " +charString, Toast.LENGTH_SHORT).show();
                if (charString.isEmpty()) {
                       RecordListFiltered=RecordList;


                } else {
                    List<Record> filteredList = new ArrayList<>();


                   for (Record row : RecordList) {
                        //Toast.makeText(context, "in for " +charString, Toast.LENGTH_SHORT).show();
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())|| row.getGenre().contains(charSequence)|| row.getYear().contains(charSequence) ) {
                           // Toast.makeText(context,  charString, Toast.LENGTH_SHORT).show();
                            filteredList.add(row);
                        }

                      // Toast.makeText(context, String.valueOf(filteredList.size()), Toast.LENGTH_SHORT).show();
                    }

                   // RecordListFiltered.clear();
                    RecordListFiltered=filteredList;
                  /* for (Record row : filteredList) {

                        RecordListFiltered.add(row);//
                    }*/

                }

                FilterResults filterResults = new FilterResults();
                filterResults.count=RecordListFiltered.size();
                filterResults.values = RecordListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                RecordListFiltered = (ArrayList<Record>) filterResults.values;
                notifyDataSetChanged();


            }
        };
    }
    public List<Record> getRecordListFiltered() {
        return RecordListFiltered;
    }
    
}
