package com.arctouch.floripapublictransportation.classes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.arctouch.floripapublictransportation.R;

import java.util.List;

/**
 * Created by GabrielPacheco on 14/01/2016.
 */
public class ExpandableListViewDetailAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<StopItemExpandableListView> items;
    private List<DepartureSubItemExpandableListView> subItems;

    public ExpandableListViewDetailAdapter(Context context, List<StopItemExpandableListView> items, List<DepartureSubItemExpandableListView> subItems) {
        this.context = context;
        this.items = items;
        this.subItems = subItems;
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return subItems.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_list_stop, null);
        }
        TextView textViewStopName = (TextView) convertView.findViewById(R.id.textViewStopName);
        //TextView horario = (TextView) convertView.findViewById(R.id.horario);
        StopItemExpandableListView stopItemExpandableListView = items.get(groupPosition);
        textViewStopName.setText(stopItemExpandableListView.getStopName());
        //horario.setText(programaResumido.Horario);
        return convertView;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.subitem_list_departure, null);
        }
        //CheckBox favorito = (CheckBox) convertView.findViewById(R.id.favorito);
        TextView textViewDeparture = (TextView) convertView.findViewById(R.id.textViewDeparture);
        //TextView horario = (TextView) convertView.findViewById(R.id.horario);
        //ImageView imagem = (ImageView) convertView.findViewById(R.id.imagem);
        DepartureSubItemExpandableListView departureSubItemExpandableListView = subItems.get(groupPosition);
        //favorito.setChecked(programaDetalhado.Favorito);
        textViewDeparture.setText(departureSubItemExpandableListView.getDepartureTime());
        //horario.setText(programaDetalhado.Horario);
        //imagem.setImageBitmap(programaDetalhado.Imagem);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
