package com.eformation.eformation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    OnFormationSelectedListener onFormationSelectedListener;

    public interface OnFormationSelectedListener {
        public void onFormationSelected(long formationid);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            onFormationSelectedListener = (OnFormationSelectedListener) activity;
        }
        catch (Exception e)
        {
            Log.e("e", "onAttach: "+e.getMessage(),e );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_listdvd,null);

        list = (ListView)view.findViewById(R.id.main_List);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(onFormationSelectedListener !=null)
                {
                    onFormationSelectedListener.onFormationSelected(id);
                }

            }
        });
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateFormationList();
    }

    public void updateFormationList()
    {
        ArrayList<Formation> formationList = Formation.getFormationList(getActivity());
        FormationAdapter formationAdapter = new FormationAdapter(getActivity(), formationList);
        list.setAdapter(formationAdapter);
    }
}
