package com.joyfullkiwi.converterlab.Home;


import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.joyfullkiwi.converterlab.Detail.DetailActivity;
import com.joyfullkiwi.converterlab.Map.MapActivity;
import com.joyfullkiwi.converterlab.Models.City;
import com.joyfullkiwi.converterlab.Models.Organization;
import com.joyfullkiwi.converterlab.Models.Region;
import com.joyfullkiwi.converterlab.R;
import com.joyfullkiwi.converterlab.databinding.OrganizationCardBinding;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    private final Activity context;
    private Resources res;
    private RealmList<Organization> organizations = new RealmList<>();
    private RealmList<Organization> filteredOrganizations = new RealmList<>();
    private LayoutInflater layoutInflater;
    private Realm realm = Realm.getDefaultInstance();

    public HomeAdapter(Activity context) {
        this.context = context;
        res = context.getResources();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOrganizations(RealmList<Organization> organizations) {
        this.organizations.clear();
        this.filteredOrganizations.clear();
        this.organizations.addAll(organizations);
        this.filteredOrganizations.addAll(organizations);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.organization_card, parent, false);
        return new ViewHolder(view);
    }

    public void filter(String filter) {
        if (checkIsEmptyFilter(filter)) {
            return;
        }
        realm.beginTransaction();
        filteredOrganizations.clear();
        for (int i = 0; i < organizations.size(); i++) {
            Organization organization = organizations.get(i);
            String title = organization.getTitle().toLowerCase();
            String region = realm.where(Region.class).equalTo("id", organization.getRegionId())
                    .findFirst().getName().toLowerCase();
            String city = realm.where(City.class).equalTo("id", organization.getCityId()).findFirst()
                    .getName().toLowerCase();
            if (title.startsWith(filter) || region.startsWith(filter) || city.startsWith(filter)) {
                filteredOrganizations.add(organization);
            }
        }
        notifyDataSetChanged();
        realm.commitTransaction();
    }

    private boolean checkIsEmptyFilter(String filter) {
        if (filter.equals("")) {
            filteredOrganizations.clear();
            filteredOrganizations.addAll(organizations);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    @Override
    public void onBindViewHolder(ViewHolder hold, int position) {
        Organization organization = filteredOrganizations.get(position);
        realm.beginTransaction();

        // animateView(hold);

        hold.view.textTitleOrgCard.setText(organization.getTitle());
        String region = realm.where(Region.class).equalTo("id", organization.getRegionId()).findFirst()
                .getName();
        hold.view.textRegionTitleOrgCard.setText(region);

        String city = realm.where(City.class).equalTo("id", organization.getCityId()).findFirst()
                .getName();
        hold.view.textCityTitleOrgCard.setText(city);

        hold.view.textPhoneOrgCard.setText(String.format("Тел.: %s", organization.getPhone()));
        hold.view.textAdressOrgCard.setText(String.format("Адрес : %s", organization.getAddress()));

        hold.view.imagePhoneOrgCard.setOnClickListener(view -> {
            String phone = filteredOrganizations.get(position).getPhone();
            TedPermission.with(context).setPermissionListener(new PermissionListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onPermissionGranted() {
                    context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone)));
                }

                @Override
                public void onPermissionDenied(ArrayList<String> arrayList) {
                }
            })
                    .setRationaleConfirmText(res.getString(R.string.confirm))
                    .setDeniedCloseButtonText(res.getString(R.string.cancel))
                    .setGotoSettingButtonText(res.getString(R.string.settings))
                    .setDeniedMessage(res.getString(R.string.msg_denied))
                    .setPermissions(permission.CALL_PHONE)
                    .check();
        });

        hold.view.imageMoreOrgCard.setOnClickListener(
                view -> startActivityWithOrganizationId(DetailActivity.class,
                        filteredOrganizations.get(position).getId()));

        hold.view.imageMapOrgCard.setOnClickListener(
                view -> startActivityWithOrganizationId(MapActivity.class,
                        filteredOrganizations.get(position).getId()));

        hold.view.imageLinkOrgCard.setOnClickListener(view -> {
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    .setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
                    .setExitAnimations(context, R.anim.slide_in_left, R.anim.slide_out_right)

                    .setToolbarColor(context.getResources().getColor(R.color.colorPrimary))
                    .build();
            Uri url = Uri.parse(filteredOrganizations.get(position).getLink());
            customTabsIntent.launchUrl(context, url);
        });
        realm.commitTransaction();
    }

    private void startActivityWithOrganizationId(Class activityClass, String id) {
        Intent intent = new Intent(context, activityClass);
        intent.putExtra("orgId", id);
        context.startActivity(intent);
    }




    @Override
    public int getItemCount() {
        return filteredOrganizations != null ? filteredOrganizations.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        OrganizationCardBinding view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = OrganizationCardBinding.bind(itemView);
        }
    }
}
