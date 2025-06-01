package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MatchAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Match> matchList;

    public MatchAdapter(Context context, ArrayList<Match> matchList) {
        this.context = context;
        this.matchList = matchList;
    }

    @Override
    public int getCount() {
        return matchList.size();
    }

    @Override
    public Object getItem(int position) {
        return matchList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView player1Name, player1Result, vs, player2Result, player2Name;
    }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.match_list, parent, false);
                holder = new ViewHolder();
                holder.player1Name = convertView.findViewById(R.id.player1Name);
                holder.player1Result = convertView.findViewById(R.id.player1Result);
                holder.vs = convertView.findViewById(R.id.vs);
                holder.player2Result = convertView.findViewById(R.id.player2Result);
                holder.player2Name = convertView.findViewById(R.id.player2Name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Match match = matchList.get(position);

            holder.player1Name.setText(match.player1);
            holder.player1Result.setText(match.player1Result);
            holder.player2Name.setText(match.player2);
            holder.player2Result.setText(match.player2Result);
            holder.vs.setText("VS");

            return convertView;
        }
    }
