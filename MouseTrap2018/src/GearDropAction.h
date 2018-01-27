#pragma once

#include "RobotAction.h"
class EntechRobot;
class DriveSubsystem;
class DropperSubsystem;

class GearDropAction : public RobotAction {
public:
    GearDropAction(EntechRobot *pRobot, std::string name);
    virtual ~GearDropAction();
    virtual void DoAction(void);

    virtual void Start(void);
    virtual void Stop(void);
    virtual void Reset(void);
    virtual bool IsActive(void);
    virtual bool IsDone(void);

private:
    DriveSubsystem *m_drive;
    DropperSubsystem *m_dropper;
    enum AutoDropState { kDropStart, kDropWaitForGearRelease,
                         kDropBackoff, kWaitForDropBackoff,
                         kDropperRaise, kWaitForDropperRaise,
                         kDropForward, kWaitForDropForward,
                         kDropBackup, kWaitForDropBackup, kDropDone
    };
    AutoDropState m_dropState;
    frc::Timer *m_dropTimer;
};
