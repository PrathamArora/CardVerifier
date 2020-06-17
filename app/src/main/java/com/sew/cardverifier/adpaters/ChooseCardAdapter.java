package com.sew.cardverifier.adpaters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sew.cardverifier.R;
import com.sew.cardverifier.model.Card;

import java.util.HashSet;
import java.util.List;

public class ChooseCardAdapter extends RecyclerView.Adapter<ChooseCardAdapter.ViewHolder> {
    private Context context;
    private List<Card> cardList;
    public HashSet<Integer> selectedPositions;

    public ChooseCardAdapter(Context context, List<Card> cardList) {
        this.context = context;
        this.cardList = cardList;
        this.selectedPositions = new HashSet<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.payment_card_view, parent, false);
        return new ChooseCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.imgCardCompany.setImageResource(card.getCardImageID());
        holder.tvCardCompany.setText(card.getCardCompany());

        boolean containsPositions = selectedPositions.contains(position);

        holder.rbCardOption.setSelected(containsPositions);
        holder.itemView.setSelected(containsPositions);

        if (containsPositions) {
            holder.tvCardCompany.setTextColor(context.getResources().getColor(R.color.white, null));
            holder.rbCardOption.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
        } else {
            holder.tvCardCompany.setTextColor(context.getResources().getColor(R.color.black, null));
            holder.rbCardOption.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black)));
        }

    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCardCompany;
        private ImageView imgCardCompany;
        private RadioButton rbCardOption;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCardCompany = itemView.findViewById(R.id.tvCardCompany);
            imgCardCompany = itemView.findViewById(R.id.imgCardCompany);
            rbCardOption = itemView.findViewById(R.id.rbCardOption);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = ViewHolder.this.getLayoutPosition();

                    if(selectedPositions.contains(currentPosition)){
                        selectedPositions.remove(currentPosition);
//                        ViewHolder.this.rbCardOption.setSelected(false);
                    }else{
                        selectedPositions.add(currentPosition);
//                        ViewHolder.this.rbCardOption.setSelected(true);
                    }

                    notifyDataSetChanged();
                }
            });

        }
    }

}
