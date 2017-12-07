package com.joyfullkiwi.converterlab.Detail;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.joyfullkiwi.converterlab.R;

import java.util.ArrayList;

public class ShareFragment extends DialogFragment{

    private DetailInteractor interactor;
    private String orgId;

    public static ShareFragment newInstance(String orgId) {
        Bundle args = new Bundle();
        ShareFragment fragment = new ShareFragment();
        args.putString("orgId", orgId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interactor = DetailInteractor.init();
        orgId = getArguments().getString("orgId");
        interactor.loadDetailOrganization(orgId);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        BankView bankView = (BankView) view.findViewById(R.id.bankView);
        Button shareButton = (Button) view.findViewById(R.id.share);

        bankView.setData(interactor.getOrganization().getTitle(), interactor.getRegion(),
                interactor.getCity(), interactor.getModelList());

        shareButton.setOnClickListener(v -> {
            TedPermission.with(getContext()).setPermissionListener(new PermissionListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onPermissionGranted() {
                    Intent intent = new Intent(Intent.ACTION_SEND);

                    String imagePath = Media
                            .insertImage(getActivity().getContentResolver(), bankView.getResultBitmap(),
                                    interactor.getOrganization().getTitle(), null);

                    Uri uri = Uri.parse(imagePath);

                    intent.setType("image/png");

                    intent.putExtra(Intent.EXTRA_STREAM, uri);

                    startActivity(Intent.createChooser(intent, "Поделиться курсом"));
                }

                @Override
                public void onPermissionDenied(ArrayList<String> arrayList) {
                }
            })
                    .setRationaleConfirmText(getString(R.string.confirm))
                    .setDeniedCloseButtonText(getString(R.string.cancel))
                    .setGotoSettingButtonText(getString(R.string.settings))
                    .setDeniedMessage(getString(R.string.msg_denied))
                    .setPermissions(permission.WRITE_EXTERNAL_STORAGE)
                    .check();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, dialog.getWindow().getAttributes().height);
        }
    }
}
