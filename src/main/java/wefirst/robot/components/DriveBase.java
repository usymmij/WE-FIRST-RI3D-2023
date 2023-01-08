package wefirst.robot.components;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import wefirst.robot.Constants;

public class DriveBase {
    private static VictorSPX[] leftMotors;
    private static VictorSPX[] rightMotors;
    
    /**
     * takes id array and splits them between the two sides (left, right, left right ....)
     * first id of each side is the leader 
     * @param ids
     */
    public static void init(int[] ids) {

        for(int i=0; i<ids.length / 2;i++) {
            leftMotors[i] = new VictorSPX(ids[2*i]);
            rightMotors[i] = new VictorSPX(ids[2*i+1]);
        }

        boolean leaderFlag = true;
        /** configs for left side */
        for(VictorSPX motor : leftMotors) {
            /** configs for all motors */
            motor.configFactoryDefault();
            motor.setInverted(InvertType.InvertMotorOutput);

            if(leaderFlag) {
                leaderFlag = false;
                /** configs for lead motor */
                continue;
            }
            /** configs for follower motors */

            motor.follow(leftMotors[0]);
        }

        leaderFlag = true;
        /** configs for right side */
        for(VictorSPX motor : rightMotors) {
            /** configs for all motors */
            motor.configFactoryDefault();
            motor.setInverted(InvertType.None);

            if(leaderFlag) {
                leaderFlag = false;
                /** configs for lead motor*/
                continue;
            }
            /** configs for follower motors */

            motor.follow(rightMotors[0]);
        }
    }

    public static void rawDrive(double left, double right) {
        leftMotors[0].set(ControlMode.PercentOutput, left * Constants.DriveConstants.RAW_DRIVE_FULL_POWER);
        rightMotors[0].set(ControlMode.PercentOutput, right * Constants.DriveConstants.RAW_DRIVE_FULL_POWER);
    }
}
