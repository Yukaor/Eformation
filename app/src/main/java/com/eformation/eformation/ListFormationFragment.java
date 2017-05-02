package com.eformation.eformation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Benjamin on 25/04/2017.
 */

public class ListFormationFragment extends Fragment {

    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_listdvd, null);

        list = (ListView)view.findViewById(R.id.main_List);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                startViewFormationActivity(id+1);
            }
        });
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ArrayList<Formation> formationList = Formation.getFormationList(getActivity());
        FormationAdapter formationAdapter = new FormationAdapter(getActivity(),formationList);
        list.setAdapter(formationAdapter);
    }

    private void startViewFormationActivity(long formationid)
    {
        Intent intent = new Intent(getActivity(),ViewFormationActivity.class);
        intent.putExtra("formationId",formationid);
        startActivity(intent);
    }
}
