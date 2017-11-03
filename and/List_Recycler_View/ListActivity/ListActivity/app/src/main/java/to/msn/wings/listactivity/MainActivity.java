package to.msn.wings.listactivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        final ArrayList<String> data = new ArrayList<>();
        data.add("胡椒");
        data.add("ターメリック");
        data.add("コリアンダー");
        data.add("生姜");
        data.add("ニンニク");
        data.add("サフラン");
        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, data);
        this.setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        adapter.remove((String) ((TextView) v).getText());
    }
}
