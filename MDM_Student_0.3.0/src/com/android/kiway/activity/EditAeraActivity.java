package com.android.kiway.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.kiway.widget.OnWheelChangedListener;
import com.android.kiway.widget.WheelView;
import com.android.kiway.widget.adapters.ArrayWheelAdapter;
import com.android.kiway.widget.model.CityModel;
import com.android.kiway.widget.model.DistrictModel;
import com.android.kiway.widget.model.ProvinceModel;
import com.android.kiway.widget.service.XmlParserHandler;
import com.android.launcher3.R;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class EditAeraActivity extends BaseActivity implements
        OnWheelChangedListener, View.OnClickListener {
    /**
     * 省选择列表
     */
    private WheelView mViewProvince;
    /**
     * 城市选择列表
     */
    private WheelView mViewCity;
    /**
     * 区域选择列表
     **/
    private WheelView mViewDistrict;
    /**
     * 城市数据
     */
    protected String[] mProvinceDatas;
    /**
     * 城市数据
     **/
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * 区域数据
     **/
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    /**
     * 城市的Id
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();
    /**
     * 当前选择的省
     */
    protected String mCurrentProviceName;
    /**
     * 当前选择的市
     */
    protected String mCurrentCityName;
    /**
     * 当前选择的区域
     */
    protected String mCurrentDistrictName = "";
    /**
     * 当前选择的城市Id
     **/
    protected String mCurrentZipCode = "";

    private EditText nameET;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_area);
        setUpViews();
        setUpListener();
        setUpData();
    }

    private void setUpViews() {
        ll = (LinearLayout) findViewById(R.id.ll);
        nameET = (EditText) findViewById(R.id.nameET);
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
    }

    private void setUpListener() {
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
        findViewById(R.id.previos).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        nameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = nameET.getText().toString().trim();
                if (name.equals("")) {
                    ll.setVisibility(View.VISIBLE);
                } else {
                    ll.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(
                EditAeraActivity.this, mProvinceDatas));
        mViewProvince.setVisibleItems(15);
        mViewCity.setVisibleItems(15);
        mViewDistrict.setVisibleItems(15);
        mViewProvince.setCurrentItem(0);
        mViewCity.setCurrentItem(0);
        mViewDistrict.setCurrentItem(0);
        updateCities();
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
        } else if (wheel == mViewCity) {
            updateAreas();
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
        }
    }

    /**
     * 更新地区
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict
                .setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }

    /**
     * 更新城市
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                String area = mCurrentProviceName + "*" + mCurrentCityName
                        + "*" + mCurrentDistrictName;
                String name = nameET.getText().toString().trim();
                //获取学校列表
                startActivityForResult(new Intent(EditAeraActivity.this, SchoolListActivity.class).putExtra("name", name).putExtra("area", area), 999);
                break;
            case R.id.previos:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == 999) {
            setResult(999, data);
            finish();
        }
    }

    /**
     * 读取数据
     **/
    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0)
                            .getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j)
                            .getDistrictList();
                    String[] distrinctNameArray = new String[districtList
                            .size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList
                            .size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        DistrictModel districtModel = new DistrictModel(
                                districtList.get(k).getName(), districtList
                                .get(k).getZipcode());
                        mZipcodeDatasMap.put(districtList.get(k).getName(),
                                districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
        }
    }
}
