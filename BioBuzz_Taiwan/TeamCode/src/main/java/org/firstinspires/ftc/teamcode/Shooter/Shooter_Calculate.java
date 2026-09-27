package org.firstinspires.ftc.teamcode.Shooter;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import java.util.Arrays;

public class Shooter_Calculate {
    private Pose goal,currentPose;
    private Follower follower;
    private Shooter_Result result = new Shooter_Result();

    // {距離 , 角度 , 射球速度RPM}
    private double[][] table =
            {
                    {65.38172528, 45, 2600},
                    {76.12726187, 46, 2600},
                    {86.35930755, 45.5, 2600},
                    {93.43446901, 42, 2600},
                    {99.72462083, 40, 2600},
            };
    public Shooter_Calculate(Follower follower){
        this.follower = follower;
        sortTable(table);
    }
    public static class Shooter_Result{
        public double launchAngle =0;
        public double launchRPM = 0;
    }
    public void setGoal(Pose goal){
        this.goal = goal;
    }
    public void update(){
        currentPose =  follower.pose();
        if(goal != null && currentPose != null) {
            result = calculate();
        }
    }
    public Shooter_Result getResult(){
        return result;
    }
    public static void sortTable(double table[][]){
        Arrays.sort(table, (x, y) -> Double.compare(x[0],y[0]));
    }
    private Shooter_Result calculate(){
        int length = table.length;
        double distant = Math.hypot(currentPose.x() - goal.x(), currentPose.y() - goal.y());
        if (distant <= table[0][0]){
            result.launchAngle = table[0][1];
            result.launchRPM = table[0][2];
            return result;
        }
        if (distant >= table[length-1][0]){
            result.launchAngle = table[length-1][1];
            result.launchRPM = table[length-1][2];
            return result;
        }
        for(int i =0 ; i< length-1 ; i++){
            if(table[i][0] <= distant && table[i+1][0] >= distant){
                double weight = (distant - table[i][0]) / (table[i+1][0]-table[i][0]);
                result.launchAngle = table[i][1] + (table[i+1][1] - table[i][1]) * weight;
                result.launchRPM = table[i][2] + (table[i+1][2] - table[i][2]) * weight;
                return result;
            }
        }
        return result;
    }
}
