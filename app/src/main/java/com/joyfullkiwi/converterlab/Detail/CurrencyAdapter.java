package com.joyfullkiwi.converterlab.Detail;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.joyfullkiwi.converterlab.databinding.CurrencyItemBinding;
import com.joyfullkiwi.converterlab.Models.Currencies;
import com.joyfullkiwi.converterlab.Models.CurrencyModel;
import com.joyfullkiwi.converterlab.Models.Price;
import com.joyfullkiwi.converterlab.R;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder>{

    private List<CurrencyModel>  modelList;
    private LayoutInflater layoutInflater;
    private Context context;

    public void setCurrencies(List<CurrencyModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    public CurrencyAdapter(Context context){
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.currency_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CurrencyModel cam = modelList.get(position);
        holder.bind.textCurrItemTitle.setText(getCurrencyNameById(cam.getCurrencyId()));
        RealmList<Price> prices = cam.getPrices();
        if (prices.size() >= 2) {
            Price first = prices.get(0);
            Price second = prices.get(1);
            compareAndSet(first.getAsk(), second.getAsk(), holder.bind.textAskValue, holder.bind.imageAsk);
            compareAndSet(first.getBid(), second.getBid(), holder.bind.textBidValue, holder.bind.imageBid);
        } else {
            Price first = prices.get(0);
            holder.bind.textAskValue.setText(String.valueOf(first.getAsk()));
            holder.bind.textBidValue.setText(String.valueOf(first.getBid()));
            holder.bind.imageAsk.setImageResource(R.mipmap.ic_arrow_up);
            holder.bind.imageBid.setImageResource(R.mipmap.ic_arrow_up);
            holder.bind.textAskValue.setTextColor(context.getResources().getColor(R.color.arrow_up));
            holder.bind.textBidValue.setTextColor(context.getResources().getColor(R.color.arrow_up));
        }
    }

    private String getCurrencyNameById(String currencyId) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Currencies currencies = realm.where(Currencies.class).equalTo("id", currencyId).findFirst();
        realm.commitTransaction();
        realm.close();
        return currencies.getName();
    }

    private void compareAndSet(double first, double second, TextView text, ImageView image) {
        int compareAsk = Double.valueOf(first).compareTo(second);
        switch (compareAsk) {
            case -1:
                text.setTextColor(context.getResources().getColor(R.color.arrow_down));
                image.setImageResource(R.mipmap.ic_arrow_down);
                break;
            case 0:
            case 1:
                text.setTextColor(context.getResources().getColor(R.color.arrow_up));
                image.setImageResource(R.mipmap.ic_arrow_up);
                break;
        }
        text.setText(String.valueOf(first));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CurrencyItemBinding bind;
        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

}
