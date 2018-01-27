#pragma once

class EntechRobot;
#include <string>

class RobotAction {
public:
    RobotAction(EntechRobot *pRobot, std::string name);
    virtual ~RobotAction();
    const char* GetName();
#if 0
    std::string GetName();
#endif
    virtual void Start(double timeout = -1.0);
    virtual void Stop(void);
    virtual void Reset(void);
    virtual bool IsActive(void);
    virtual bool WasSuccessful(void);

    void DoAction(void);
    bool TimedOut(void);

    virtual void DoActualAction(void) = 0;
protected:
    std::string m_name;
    double m_timeout;
    frc::Timer *m_timeoutTimer;
    bool m_timedout;
};
