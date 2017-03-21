package com.eformation.eformation;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.*;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Benjamin on 20/03/2017.
 */

public class FormationAdapter extends ArrayAdapter<Formation> {

    Context context;

    public FormationAdapter (Context context, List<Formation> objects)
    {
        super(context, -1, objects);
        this.context = context;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        View view;
        if(convertView==null) {
            LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listitem_dvd, parent);
        }
        else {
            view = convertView;
        }
        Formation formation = getItem(pos);

        TextView titre = (TextView) view.findViewById(R.id.listItemFormation_titre);
        TextView annee = (TextView) view.findViewById(R.id.listItemFormation_annee);
        TextView resume = (TextView) view.findViewById(R.id.listItem_resume);


        try {
            titre.setText(formation.getTitre());
            annee.setText(formation.getAnnee());
            resume.setText(formation.getResume());
        }


        return view;

    }
}
