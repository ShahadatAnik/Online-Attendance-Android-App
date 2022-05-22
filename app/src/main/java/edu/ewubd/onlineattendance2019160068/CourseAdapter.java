package edu.ewubd.onlineattendance2019160068;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseAdapter extends BaseAdapter {
    Context context;
    ArrayList<CourseList> arrayList;

    public CourseAdapter(Context context, ArrayList<CourseList> arrayList){
        this.context = context;
        this.arrayList = arrayList;

    }
    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView = inflater.inflate(R.layout.custom_grid_attendace, parent, false);

        TextView courseCode = gridView.findViewById(R.id.courseCode);
        TextView courseInstructor = gridView.findViewById(R.id.courseInstructor);
        TextView courseName = gridView.findViewById(R.id.courseName);

        String[] fieldValues = arrayList.get(position).getData().split(",");
        courseCode.setText(fieldValues[0]);
        courseName.setText(fieldValues[1]);
        courseInstructor.setText(fieldValues[2]);

        gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Attendance.class);
                context.startActivity(intent);
            }
        });

        return gridView;
    }
}

