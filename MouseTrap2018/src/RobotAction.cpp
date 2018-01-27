#include <WPILib.h>

#include "RobotAction.h"
#include "EntechRobot.h"

RobotAction::RobotAction(EntechRobot *pRobot, std::string name)
    : m_name(name)
{
    pRobot->RegisterAction(this);
    m_timeoutTimer = new frc::Timer();
    m_timeoutTimer->Stop();
    m_timeoutTimer->Reset();
}

RobotAction::~RobotAction() {}

const char* RobotAction::GetName()
{
    return m_name.c_str();
}

#if 0
std::string RobotAction::GetName()
{
  return m_name;
}
#endif

void RobotAction::Start(double timeout)
{
  m_timedout = false;
  m_timeout = timeout;
  if (m_timeout > 0.0) {
    m_timeoutTimer->Stop();
    m_timeoutTimer->Reset();
    m_timeoutTimer->Start();
  }
}

void RobotAction::DoAction(void)
{
  if ((m_timeout > 0.0) && (m_timeoutTimer->Get() > m_timeout)) {
    this->Stop();
    m_timedout = true;
    m_timeoutTimer->Stop();
  }
}
