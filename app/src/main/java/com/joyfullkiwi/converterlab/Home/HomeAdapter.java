package com.joyfullkiwi.converterlab.Home;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joyfullkiwi.converterlab.Models.City;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.Models.Region;
import com.joyfullkiwi.converterlab.R;
import com.joyfullkiwi.converterlab.databinding.OrganizationCardBinding;

import io.realm.Realm;
import io.realm.RealmResults;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    private Context context;
    private RealmResults<Organization> organizations;
    private LayoutInflater layoutInflater;
    private Realm realm = Realm.getDefaultInstance();

    public HomeAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOrganizations(
            RealmResults<Organization> organizations) {
        this.organizations = organizations;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.organization_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder hold, int position) {
        Organization organization = organizations.get(position);
        realm.beginTransaction();
        hold.view.textTitleOrgCard.setText(organization.getTitle());
        String region = realm.where(Region.class).equalTo("id", organization.getRegionId()).findFirst().getName();
        hold.view.textRegionTitleOrgCard.setText(region);

        String city = realm.where(City.class).equalTo("id", organization.getCityId()).findFirst().getName();
        hold.view.textCityTitleOrgCard.setText(city);

        hold.view.textPhoneOrgCard.setText(String.format("Тел.: %s", organization.getPhone()));
        hold.view.textAdressOrgCard.setText(String.format("Адрес : %s", organization.getAddress()));
        realm.commitTransaction();
    }

    @Override
    public int getItemCount() {
        return organizations != null ? organizations.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        OrganizationCardBinding view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = OrganizationCardBinding.bind(itemView);
        }
    }
}
