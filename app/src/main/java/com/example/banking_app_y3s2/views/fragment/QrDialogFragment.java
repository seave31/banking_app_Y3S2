package com.example.banking_app_y3s2.views.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.banking_app_y3s2.databinding.FragmentQrDialogBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QrDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QrDialogFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentQrDialogBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QrDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QrDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QrDialogFragment newInstance(String param1, String param2) {
        QrDialogFragment fragment = new QrDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentQrDialogBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        try {
            //get data from bundle
            String jsonStr = getArguments().getString("data");

            //get data
            JSONObject jsonObject = new JSONObject(jsonStr); //put data inside to read
            String accountNumber = jsonObject.getString("account_number");
            String username = jsonObject.getString("username");

            //show acc name in ui
            binding.userAccNameTv.setText(username);

            //put data(in the form of json) into qr code
            JSONObject qrObject = new JSONObject();
            qrObject.put("account_number", accountNumber);
            qrObject.put("username", username);

            //convert json data to string
            String qrData = qrObject.toString();

            //call the function to generate qr code
            Bitmap bitmap = generateQRCode(qrData);
            binding.qrIv.setImageBitmap(bitmap);
        }catch (JSONException e){
            e.printStackTrace();
        }






        return view;
    }

    //generate qr code
    private Bitmap generateQRCode(String qrData) {
        //zxing
        QRCodeWriter writer = new QRCodeWriter();

        try {
            int width = 512;
            int height = 512;

            //This converts text → QR structure (black/white grid data)
            BitMatrix bitMatrix = writer.encode(qrData, BarcodeFormat.QR_CODE, width, height);

            //Convert matrix → Bitmap (image)
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            return bmp;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}