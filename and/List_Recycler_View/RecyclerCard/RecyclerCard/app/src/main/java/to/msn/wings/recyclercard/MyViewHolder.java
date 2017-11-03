package to.msn.wings.recyclercard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    View view;
    TextView title;
    TextView tag;
    TextView desc;

    public MyViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        this.title = (TextView) view.findViewById(R.id.title);
        this.tag = (TextView) view.findViewById(R.id.tag);
        this.desc = (TextView) view.findViewById(R.id.desc);
    }
}