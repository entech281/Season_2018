GearDropAction::GearDropAction(EntechRobot *pRobot, std::string name)
    : RobotAction(pRobot, name)
    , m_drive(NULL)
    , m_dropper(NULL)
    , m_dropState(kDropDone)
    , m_dropTimer(NULL)
{
    pRobot->RegisterAction(this);
    m_drive = pRobot->GetDriveSubsystem();
    m_dropper = pRobot->GetDropperSubsystem();
    m_dropTimer = new frc::Timer();
    m_dropTimer->Stop();
    m_dropTimer->Reset();
}

GearDropAction::~GearDropAction()
{
    delete m_dropTimer;
}

void GearDropAction::Start(void)
{
    if (m_dropState == kDropStart)
        m_dropState = kDropWaitForGearRelease;
}

void GearDropAction::Stop(void)
{
    m_dropState = kDropDone;
    m_drive->AbortDriveToVisionTarget();
}

void GearDropAction::Reset(void)
{
    m_dropState = kDropStart;
}

bool GearDropAction::IsDone(void)
{
    return (m_dropState == kDropDone);
}

void GearDropAction::DoAction(void)
{
    if (m_dropState == kDropDone)
        return;

    switch (m_dropState) {
    case kDropStart:
    case kDropDone:
        break;
    case kDropWaitForGearRelease:
        if (m_dropper->IsGearDropped()) {
            m_dropState = kDropBackoff;
        }
        break;
    case kDropBackoff:
        m_drive->DriveHeading(m_drive->GetRobotYaw(),-0.55,0.18);
        m_dropState = kWaitForDropBackoff;
        break;
    case kWaitForDropBackoff:
        if (m_drive->Done()) {
            m_dropState = kDropRaise;
        }
        break;
    case kDropperRaise:
        m_dropper->SetMode(DropperSubsystem::kManual);
        m_dropper->SetPosition(DropperSubsystem::kUp);
        m_dropTimer->Stop();
        m_dropTimer->Reset();
        m_dropTimer->Start();
        m_dropState = kWaitForDropRaise;
        break;
    case kWaitForDropperRaise:
        if (m_dropTimer->Get() > 0.25) {
            m_dropState = kDropForward;
            m_dropTimer->Stop();
            m_dropTimer->Reset();
        }
        break;
    case kDropForward:
        m_drive->DriveHeading(m_drive->GetRobotYaw(),0.5,0.30);
        m_dropState = kWaitForDropForward;
        break;
    case kWaitForDropForward:
        if (m_drive->Done() || m_dropper->IsPinSensed()) {
            m_dropState = kDropBackup;
        }
        break;
    case kDropBackup:
        m_drive->DriveHeading(m_drive->GetRobotYaw(),-0.65,0.3);
        m_dropState = kWaitForDropBackup;
        break;
    case kWaitForDropBackup:
        if (m_drive->Done()) {
            m_dropState = kDropDone;
        }
        break;
    }
}
