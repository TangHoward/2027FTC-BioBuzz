package org.firstinspires.ftc.teamcode.Turret;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.Shooter.Shooter_Calculate;

public class PredictGoalPoint {
    Follower follower;
    Pose goalPose, predictGoal; // 目標點位、預測點位
    double shooter_radius = 0.036; //m
    double gravity = 9.8;// 重力m/s^2
    double efficiency = 0.8; // 效率 0~1
    double robotShooterHeight = 0; //砲台高度 m
    double goalHeight = 0; // 目標高度 m
    int iteration_time = 3; //預測位置的收斂次數
    Shooter_Calculate shooterCalculator;
    public PredictGoalPoint(Follower follower, Pose goalPose){
        this.follower = follower;
        this.goalPose = goalPose;
        this.shooterCalculator = new Shooter_Calculate(follower);
    }
    public Pose getPredictGoal(){
        return predictGoal;
    }
    public void update(){
        predictGoal = computeVirtualGoal();
    }
    private Pose computeVirtualGoal(){
        Pose predictGoal = goalPose;
        for(int i = 0; i < iteration_time ; i++ ){
            shooterCalculator.setGoal(predictGoal);
            shooterCalculator.update();
            Shooter_Calculate.Shooter_Result result = shooterCalculator.getResult();
            predictGoal = predictEstimatedGoal(calculate_fly_time(result));
        }
        return predictGoal;
    }
    private Pose predictEstimatedGoal(double second){
        return new Pose(goalPose.x() - follower.velocity().vx * second,
                goalPose.y() - follower.velocity().vy * second);
    }
    //這裡預設射球都是高拋 不低拋
    private double calculate_fly_time(Shooter_Calculate.Shooter_Result shooterResult){
        double launchRadian = Math.toRadians(shooterResult.launchAngle);
        double vt = (shooter_radius*2*Math.PI*shooterResult.launchRPM / 60) * efficiency; // 球的切線速度
        double time = (vt* Math.sin(launchRadian) + Math.sqrt(Math.pow(vt* Math.sin(launchRadian),2)- 2*gravity * (goalHeight - robotShooterHeight)))/gravity;
        if (Double.isNaN(time)) {
            time =0;
        }
        return time;
    }
}
