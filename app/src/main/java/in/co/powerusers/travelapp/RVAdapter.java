package in.co.powerusers.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Powerusers on 25-07-2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> implements Filterable {
    private List<String> _PKS;
    private List<String> filterdPack;
    private PackFilter packFilter;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView _title,_price;
        CardView cv;
        LinearLayout ccll;
        CoordinatorLayout cndl;
        ImageView imgVw;
        public ViewHolder(View v){
            super(v);
            cv = (CardView)v.findViewById(R.id.cv);
            _title = (TextView)v.findViewById(R.id.pkTitle);
            _price = (TextView)v.findViewById(R.id.pkPrice);
            ccll = (LinearLayout)v.findViewById(R.id.ccLL);
            cndl = (CoordinatorLayout)v.findViewById(R.id.cndl);
            imgVw = (ImageView)v.findViewById(R.id.imgVw);
        }
    }

    public RVAdapter(List<String> _pk){
        _PKS = _pk;
        filterdPack = _pk;
    }

    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.package_list,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,int position){
        if(filterdPack.size()!=0) {
            int i = (int)(Math.random()*10+1);
            int j = R.drawable.img4;
            switch (i)
            {
                case 1: j = R.drawable.img1;
                        break;
                case 2: j = R.drawable.img2;
                        break;
                case 3: j = R.drawable.img3;
                        break;
                case 4: j = R.drawable.img4;
                        break;
                case 5: j = R.drawable.img5;
                        break;
                case 6: j = R.drawable.img6;
                        break;
                case 7: j = R.drawable.img7;
                        break;
                case 8: j = R.drawable.img8;
                        break;
                case 9: j = R.drawable.img9;
                        break;
                case 10: j = R.drawable.img10;
                        break;
            }
            holder.imgVw.setBackgroundResource(j);
            holder._title.setText(filterdPack.get(position).split("\\|")[0]);
            if(holder._title.getText().equals("Sorry, No Package Available"))
                holder._price.setText(filterdPack.get(position).split("\\|")[1]);
            else
                holder._price.setText("\u20B9 " + filterdPack.get(position).split("\\|")[1]);
        }
        holder.ccll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(holder._title.getText().toString().equals("Sorry, No Package Available"))
                    intent = new Intent(view.getContext().getApplicationContext(),InquiryActivity.class);
                else
                    intent = new Intent(view.getContext().getApplicationContext(),PackageDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("pack_title",holder._title.getText().toString());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return filterdPack.size();
    }

    @Override
    public Filter getFilter() {
        if(packFilter == null)
            packFilter = new PackFilter();
        return packFilter;
    }

    private class PackFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults filterResults = new FilterResults();
            if(constraint != null && constraint.length()>0)
            {
                ArrayList<String> tempList = new ArrayList<String>();

                for(String s : _PKS)
                {
                    if(s.toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        tempList.add(s);
                    }
                }
                if(tempList.size() == 0)
                    tempList.add("Sorry, No Package Available|Click to send inquiry!");
                filterResults.count = tempList.size();
                filterResults.values = tempList;
            }else
            {
                filterResults.count = _PKS.size();
                filterResults.values = _PKS;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            filterdPack = (ArrayList<String>)results.values;
            notifyDataSetChanged();
        }
    }
}
