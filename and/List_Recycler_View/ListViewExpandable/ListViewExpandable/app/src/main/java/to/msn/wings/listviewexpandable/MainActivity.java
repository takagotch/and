package to.msn.wings.listviewexpandable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] g_titles = {"金管", "木管", "弦"};
        String[][][] c_titles = {
                {
                        {"トランペット", "高音域の出る金管楽器"},
                        {"トロンボーン", "長いU字型の管を繋ぎ合わせた形の金管楽器"},
                        {"チューバ", "大型で低音域の出る金管楽器"}
                },
                {
                        {"クラリネット", "音域の広い木管楽器"},
                        {"ファゴット", "低音域の木管楽器"},
                        {"オーボエ", "2枚リードで高音域の木管楽器"}
                },
                {
                        {"バイオリン", "高音域の弦楽器"},
                        {"ビオラ", "中音域の弦楽器"},
                        {"チェロ", "大型の低音域の弦楽器"}
                },
        };

        ExpandableListView elv = (ExpandableListView) findViewById(R.id.elv);
        ArrayList<Map<String, String>> g_list = new ArrayList<>();
        ArrayList<List<Map<String, String>>> c_list = new ArrayList<>();

        for (int i = 0; i < g_titles.length; i++) {
            HashMap<String, String> group = new HashMap<>();
            group.put("group_title", g_titles[i]);
            g_list.add(group);
            ArrayList<Map<String, String>> childs = new ArrayList<>();
            for (int j = 0; j < c_titles.length; j++) {
                HashMap<String, String> child = new HashMap<>();
                child.put("child_title", c_titles[i][j][0]);
                child.put("child_text", c_titles[i][j][1]);
                childs.add(child);
            }
            c_list.add(childs);
        }

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                g_list, android.R.layout.simple_expandable_list_item_1,
                new String[]{"group_title"}, new int[]{android.R.id.text1},
                c_list, R.layout.list_sub, new String[]{
                "child_title", "child_text"}, new int[]{R.id.text1,
                R.id.text2}
        );
        elv.setAdapter(adapter);

        elv.setOnChildClickListener(
                new ExpandableListView.OnChildClickListener() {
                    public boolean onChildClick(ExpandableListView parent, View v,
                                                int groupPosition, int childPosition, long id) {
                        TextView txt = (TextView) v.findViewById(R.id.text1);
                        Toast.makeText(MainActivity.this, txt.getText(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
        );
    }

}
