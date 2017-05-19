package com.example.tan.tracnghiem.monhoc;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.tan.tracnghiem.MainActivity;
import com.example.tan.tracnghiem.R;
import com.example.tan.tracnghiem.slide.ScreenSlideActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HoaFragment extends Fragment {
    ExamAdapter examAdapter;
    GridView gvHoa;
    ArrayList<Exam> arr_exam= new ArrayList<Exam>();
    public HoaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Môn Hóa");
        return inflater.inflate(R.layout.fragment_hoa, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        gvHoa=(GridView) getActivity().findViewById(R.id.gvHoa);
        arr_exam.add(new Exam("Đề số 1"));
        arr_exam.add(new Exam("Đề số 2"));

        examAdapter=new ExamAdapter(getActivity(),arr_exam);
        gvHoa.setAdapter(examAdapter);
        gvHoa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ScreenSlideActivity.class);
                intent.putExtra("num_exam", i + 1);
                intent.putExtra("subject", "hoa");
                intent.putExtra("test", "yes");
                startActivity(intent);
            }
        });
    }
}
