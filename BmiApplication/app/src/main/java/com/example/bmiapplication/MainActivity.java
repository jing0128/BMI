package com.example.bmiapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bmiapplication.utils.Bmi;
import com.example.bmiapplication.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import static java.lang.Double.parseDouble;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private EditText etName;
    private EditText etHeight;
    private EditText etWeight;
    private Button btnCal;

    private TextView tvName;
    private TextView tvHeight;
    private TextView tvWeight;
    private TextView tvBmi;

    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.d(TAG, "onCreate");
        jsonArray = new JSONArray();
        initView();
        btnCal();
    }

    private void initView() {
        etName = findViewById(R.id.main_et_name);
        etHeight = findViewById(R.id.main_et_height);
        etWeight = findViewById(R.id.main_et_weight);
        btnCal = findViewById(R.id.main_btn_cal);

        tvName = findViewById(R.id.main_tv_name);
        tvHeight = findViewById(R.id.main_tv_height);
        tvWeight = findViewById(R.id.main_tv_weight);
        tvBmi = findViewById(R.id.main_tv_bmi);
    }

    private void btnCal() {
        Logger.d(TAG, "btnCal");
        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().equals("") ||
                        etHeight.getText().toString().equals("") ||
                        etWeight.getText().toString().equals("")) {
                    showToast(R.string.data_blank);
                    return;
                }
                jsonArray.put(new Bmi(etName.getText().toString(), parseDouble(etHeight.getText().toString()), parseDouble(etWeight.getText().toString())).getJsonObject());
                showTvData();
                etName.setText(null);
                etHeight.setText(null);
                etWeight.setText(null);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {//先判斷是否null避免異常
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

    }

    private void showTvData() {
        String name = "";
        String height = "";
        String weight = "";
        String bmi = "";

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                name += "使用者：" + jsonObject.getString("name") + "\n";
                height += "身高：" + jsonObject.getInt("height") + "\n";
                weight += "體重：" + jsonObject.getInt("weight") + "\n";

//                DecimalFormat df = new DecimalFormat("#.##");//#是一個數字，不包括0
//                bmi += "BMI：" + df.format(jsonObject.getDouble("bmi")) + "\n";

                String strTmp = String.format("%.2f", jsonObject.getDouble("bmi"));
                bmi += "BMI：" + strTmp + "\n";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        tvName.setText(name);
        tvHeight.setText(height);
        tvWeight.setText(weight);
        tvBmi.setText(bmi);
    }

    private void showToast(int msgResId) {
        Toast.makeText(this, msgResId, Toast.LENGTH_SHORT).show();
    }
}
