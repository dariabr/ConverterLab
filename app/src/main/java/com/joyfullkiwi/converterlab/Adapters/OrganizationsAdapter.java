package com.joyfullkiwi.converterlab.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joyfullkiwi.converterlab.Fragment.Catalog.CatalogInteractor;
import com.joyfullkiwi.converterlab.Fragment.Catalog.CatalogPresenter;
import com.joyfullkiwi.converterlab.Handlers.MenuClickHandler;
import com.joyfullkiwi.converterlab.Model.InfoModel;
import com.joyfullkiwi.converterlab.R;
import com.joyfullkiwi.converterlab.Utils.Constants;

import java.util.List;


public class OrganizationsAdapter extends RecyclerView.Adapter<OrganizationsAdapter.ViewHolder> {

    private final Context mContext;
    private List<InfoModel> mData;//list of organizations
    private InfoModel mCurrModel;
    private CatalogPresenter mCatalogPresenter;

    public OrganizationsAdapter(List<InfoModel> _data,Context _context, CatalogPresenter _catalogPresenter) {
        mContext = _context;
        mData = _data;
        mCatalogPresenter = _catalogPresenter;
    }
    public OrganizationsAdapter(Context _context) {
        mContext = _context;
    }

    public void setData(List<InfoModel> _data) {
        mData = _data;
        notifyDataSetChanged();
    }
    public void clearData(){
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.organization_card,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder _viewHolder, int _position) {

        _viewHolder.mTitle.setText(mData.get(_position).getTitle());
        _viewHolder.mRegionTitle.setText(mData.get(_position).getRegionTitle());
        if(!mCurrModel.getRegionTitle().equals(mCurrModel.getCityTitle())){
            _viewHolder.mCityTitle.setText(mData.get(_position).getCityTitle());
        } else {
            _viewHolder.mCityTitle.setText("");
        }
        _viewHolder.mPhone.setText(mData.get(_position).getPhone());
        _viewHolder.mAdress.setText(mData.get(_position).getAdress());

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle, mRegionTitle, mCityTitle, mPhone, mAdress;


        public ViewHolder(View view){
            super(view);
            mTitle = (TextView) view.findViewById(R.id.text_title_org_card);
            mRegionTitle = (TextView) view.findViewById(R.id.text_region_title_org_card);
            mCityTitle = (TextView) view.findViewById(R.id.text_city_title_org_card);
            mPhone = (TextView) view.findViewById(R.id.text_phone_org_card);
            mAdress = (TextView) view.findViewById(R.id.text_adress_org_card);

            view.findViewById(R.id.image_link_org_card).setOnClickListener(this);
            view.findViewById(R.id.image_phone_org_card).setOnClickListener(this);
            view.findViewById(R.id.image_map_org_card).setOnClickListener(this);
            view.findViewById(R.id.image_more_org_card).setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int item_ID = Constants.MENU_LINK;
            switch (v.getId()){
                case R.id.image_link_org_card:
                    item_ID = Constants.MENU_LINK;
                    break;
                case R.id.image_phone_org_card:
                    item_ID = Constants.MENU_PHONE;
                    break;
                case R.id.image_map_org_card:
                    item_ID = Constants.MENU_MAP;
                    break;
                case R.id.image_more_org_card:
                    item_ID = Constants.MENU_MORE;
                    break;
            }

            new MenuClickHandler(mContext).itemClick(item_ID,mCurrModel);

        }
    }
}
