package com.example.tan.tracnghiem.slide;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tan.tracnghiem.R;
import com.example.tan.tracnghiem.question.Question;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScreenSlidePageFragment extends Fragment {

    ArrayList<Question> arr_Ques;
    public static final String ARG_PAGE = "page";
    public static final String ARG_CHECKANSWER = "checkAnswer";
    public int mPageNumber; // vị trí trang hiện tại
    public int checkAns;   // biến kiểm tra ...

    TextView tvNum, tvQuestion;
    RadioGroup radioGroup;
    RadioButton radA, radB, radC, radD;
    ImageView imgIcon;
    Button btnKiemtra, btnKetthuc;


    public ScreenSlidePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        tvNum = (TextView) rootView.findViewById(R.id.tvNum);
        tvQuestion = (TextView) rootView.findViewById(R.id.tvQuestion);
        radA = (RadioButton) rootView.findViewById(R.id.radA);
        radB = (RadioButton) rootView.findViewById(R.id.radB);
        radC = (RadioButton) rootView.findViewById(R.id.radC);
        radD = (RadioButton) rootView.findViewById(R.id.radD);
        imgIcon = (ImageView) rootView.findViewById(R.id.ivIcon);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radGroup);
        btnKetthuc = (Button) rootView.findViewById(R.id.btnKetThuc);
        btnKiemtra = (Button) rootView.findViewById(R.id.btnKiemTra);
        final TextView txtCheck = (TextView) rootView.findViewById(R.id.txtCheck);


        return rootView;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arr_Ques = new ArrayList<Question>();
        ScreenSlideActivity slideActivity = (ScreenSlideActivity) getActivity();
        arr_Ques = slideActivity.getData();
        mPageNumber = getArguments().getInt(ARG_PAGE);
        checkAns = getArguments().getInt(ARG_CHECKANSWER);


    }

    public static ScreenSlidePageFragment create(int pageNumber, int checkAnswer) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putInt(ARG_CHECKANSWER, checkAnswer);
        fragment.setArguments(args);

        return fragment;
    }

//    public Question getItem(int position) {
//        return arr_Ques.get(position);
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvNum.setText("Câu " + (mPageNumber + 1));
        tvQuestion.setText(arr_Ques.get(mPageNumber).getQuestion());

        radA.setText(getItem(mPageNumber).getAns_a());
        radB.setText(getItem(mPageNumber).getAns_b());
        radC.setText(getItem(mPageNumber).getAns_c());
        radD.setText(getItem(mPageNumber).getAns_d());

        imgIcon.setImageResource(getResources().getIdentifier(getItem(mPageNumber).getImage() + "", "drawable", "com.example.tan.tracnghiem"));


        if (checkAns != 0) {
            checkClickRadio();
        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                getItem(mPageNumber).choiceID = checkedId;
                getItem(mPageNumber).setTraloi(getChoiceFromID(checkedId));

//                Toast.makeText(getActivity(),"Đây là đáp án "+ checkedId, Toast.LENGTH_SHORT).show();
            }
        });

        if (getItem(mPageNumber).getCheckTest() == 1) {
            checkClickRadio();
        }

        btnKiemtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkClickRadio();
                getItem(mPageNumber).setCheckTest(1);
                getCheckAns_Toast(getItem(mPageNumber).getTraloi().toString());
            }
        });

        btnKetthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();
                Intent intent1 = new Intent(getActivity(), TestDoneActivity.class);
                intent1.putExtra("arr_Ques", refresh());
                startActivity(intent1);

            }
        });


    }

    public Question getItem(int posotion) {
        return arr_Ques.get(posotion);
    }

    public void checkClickRadio() {
        radA.setClickable(false);
        radB.setClickable(false);
        radC.setClickable(false);
        radD.setClickable(false);
        getCheckAns(getItem(mPageNumber).getResult().toString());
    }

    //Lấy giá trị (vị trí) radiogroup chuyển thành đáp án A/B/C/D
    private String getChoiceFromID(int ID) {
        if (ID == R.id.radA) {
            return "A";
        } else if (ID == R.id.radB) {
            return "B";
        } else if (ID == R.id.radC) {
            return "C";
        } else if (ID == R.id.radD) {
            return "D";
        } else return "";
    }

    //Hàm kiểm tra câu đúng, nếu câu đúng thì đổi màu background radiobutton tương ứng
    private void getCheckAns(String ans) {
        if (ans.equals("A") == true) {
            radA.setBackgroundColor(Color.RED);
        } else if (ans.equals("B") == true) {
            radB.setBackgroundColor(Color.RED);
        } else if (ans.equals("C") == true) {
            radC.setBackgroundColor(Color.RED);
        } else if (ans.equals("D") == true) {
            radD.setBackgroundColor(Color.RED);
        } else ;
    }

    private void getCheckAns_Toast(String ans) {

        if (ans.equals(getItem(mPageNumber).getResult()) == true) {
            Toast.makeText(getActivity(), "Câu trả lời Đúng", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getActivity(), "Câu trả lời Sai", Toast.LENGTH_SHORT).show();
    }

    public ArrayList refresh(){
        for(int i=0; i<arr_Ques.size(); i++){
            arr_Ques.get(i).setCheckTest(0);
            arr_Ques.get(i).setTraloi("");
        }
        return arr_Ques;
    }


}
