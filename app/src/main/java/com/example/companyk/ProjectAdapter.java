package com.example.companyk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class ProjectAdapter  extends RecyclerView.Adapter<ProjectAdapter.ViewHolder>  {
    private Context mContext;
    private List<Project> projectList;

    public ProjectAdapter(Context mContext, List<Project> projectList) {
        this.mContext = mContext;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View projectview = LayoutInflater.from(mContext).inflate(R.layout.project, parent, false);
        ViewHolder viewHolder = new ViewHolder(projectview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                Project project = projectList.get(position);
                holder.projectDesc.setText(project.getPdesc());
                holder.ProjectName.setText(project.getPname());
                ViewPagerAdapter adapter = new ViewPagerAdapter(mContext, project.getPhotos());
                holder.flipper.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewPager flipper;
        public TextView ProjectName;
        public TextView projectDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext=itemView.getContext();
            flipper = itemView.findViewById(R.id.viewFlipper);
            projectDesc = itemView.findViewById(R.id.desc);
            ProjectName = itemView.findViewById(R.id.PNameRe);
        }
    }
}
