package org.firstinspires.ftc.teamcode.BallPathPlaning;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

public class FindNearestBalls {
    private Follower follower;
    private double[][] Dist_Map = new double[144][144];
    private Pose[][] obstacle;
    private static final int OBSTACLE = -1;
    private static final int UNVISITED = Integer.MAX_VALUE;
    /* example
        {
            {new Pose(0,72), new Pose(144,144)},
        }
     */
    public void update(){

    }
    public FindNearestBalls(Follower follower){
        this.follower = follower;
    }
    public void setObstacle(Pose[][] obstacle){
        this.obstacle = obstacle;
    }
    public double[][] cal_Dist_Map(Pose OriginPose){
        for (int x=0; x< Dist_Map.length;x++){
            for(int y=0; y< Dist_Map[x].length; y++){
                Dist_Map[x][y] = isObstacleCell(x,y) ? OBSTACLE : UNVISITED;
            }
        }
        int startX = (int)Math.round(OriginPose.x());
        int startY = (int)Math.round(OriginPose.y());
        return null;
    }
    private boolean isObstacleCell(int x, int y) {
        for (Pose[] rect : obstacle) {
            double minX = Math.min(rect[0].x(), rect[1].x());
            double maxX = Math.max(rect[0].x(), rect[1].x());
            double minY = Math.min(rect[0].y(), rect[1].y());
            double maxY = Math.max(rect[0].y(), rect[1].y());
            if (x >= minX && x <= maxX && y >= minY && y <= maxY) return true;
        }
        return false;
    }


}
