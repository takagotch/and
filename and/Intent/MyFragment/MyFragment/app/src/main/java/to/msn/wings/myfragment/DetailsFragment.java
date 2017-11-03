package to.msn.wings.myfragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

public class DetailsFragment extends Fragment {
    private boolean isTwoPane = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity().findViewById(R.id.detailsFrame) != null) {
            isTwoPane = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        Bundle bundle;
        if(isTwoPane) {
            bundle = getArguments();
        } else {
            Intent intent = getActivity().getIntent();
            bundle = intent.getExtras();
        }
        if (bundle != null) {
            Map<String, String> item = ListDataSource.getInfoByName(
                    bundle.getString("name"));
            ((TextView)view.findViewById(R.id.name)).setText(String.format(
                    "%s（%s）",bundle.getString("name"), item.get("alias")));
            ((TextView) view.findViewById(R.id.info)).setText(item.get("info"));
        }
        return view;
    }
}
