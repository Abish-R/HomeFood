package abish.veettusorudemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SubFoodListActivity extends AppCompatActivity {
    Button btContinue;
    RecyclerView rvSubFoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_food_list);

        btContinue= (Button)findViewById(R.id.bt_continue);
        rvSubFoodList= (RecyclerView)findViewById(R.id.rv_sub_food_list);

        rvSubFoodList.setLayoutManager(new LinearLayoutManager(this));
        SubFoodListAdapter mAdapter = new SubFoodListAdapter(this);
        rvSubFoodList.setAdapter(mAdapter);

        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SubFoodListActivity.this,"Ok",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
