package com.example.ecommerceapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.CheckboxModel;

import java.util.List;


public class CatCheckboxAdapter extends BaseAdapter {

    private List<CheckboxModel> checkboxModelList;

    public CatCheckboxAdapter(List<CheckboxModel> checkboxModelList) {
        this.checkboxModelList = checkboxModelList;
    }

    @Override
    public int getCount() {
        return checkboxModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return checkboxModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_item_layout, null);

            CheckBox checkBox = view.findViewById(R.id.sort_checkbox);
            checkBox.setChecked(checkboxModelList.get(position).isSelected());
            checkBox.setText(checkboxModelList.get(position).getTitle());
        } else {
            view = convertView;
        }
        return view;
    }
}
