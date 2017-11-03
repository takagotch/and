package to.msn.wings.myfragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListFragment extends Fragment {
    private boolean isTwoPane = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.detailsFrame) != null) {
            isTwoPane = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            getActivity(), android.R.layout.simple_list_item_1,
            ListDataSource.getAllNames());
        ListView list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // タブレットだけの場合
            /*@Override
            public void onItemClick(
                    AdapterView<?> parent, View view, int i, long id) {

                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                DetailsFragment fragment = new DetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name", (String) parent.getItemAtPosition(i));
                fragment.setArguments(bundle);
                transaction.replace(R.id.detailsFrame, fragment);
                transaction.commit();
            }*/

            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int i, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("name", (String) parent.getItemAtPosition(i));
                if (isTwoPane) {
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    DetailsFragment fragment = new DetailsFragment();
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.detailsFrame, fragment);
                    transaction.commit();
                } else {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

        });
        return view;
    }
}