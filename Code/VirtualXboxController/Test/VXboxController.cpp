#include "stdafx.h"
#include "VXboxController.h"


VXboxController::VXboxController() {
    PlugIn();

    if (!IsPluggedIn()) return;
    a = new VXboxButton(SetBtnA, userIndex);
    b = new VXboxButton(SetBtnB, userIndex);
    x = new VXboxButton(SetBtnX, userIndex);
    y = new VXboxButton(SetBtnY, userIndex);
    lb = new VXboxButton(SetBtnLB, userIndex);
    rb = new VXboxButton(SetBtnRB, userIndex);
    lThumb = new VXboxButton(SetBtnLT, userIndex);
    rThumb = new VXboxButton(SetBtnRT, userIndex);
    start  = new VXboxButton(SetBtnStart, userIndex);
    back = new VXboxButton(SetBtnBack, userIndex);

    lTrigger = new VXboxTrigger(SetTriggerL, userIndex);
    rTrigger = new VXboxTrigger(SetTriggerR, userIndex);

    lAxis = new VXboxAxis(SetAxisX, SetAxisY, userIndex);
    rAxis = new VXboxAxis(SetAxisRx, SetAxisRy, userIndex);

    dpad = new VXboxDpad(SetDpadUp, SetDpadDown, SetDpadLeft, SetDpadRight, SetDpadOff, userIndex);
    vibration = std::make_pair(0, 0);
}

VXboxController::~VXboxController() {
    UnPlugForce();

    delete a;
    delete b;
    delete x;
    delete y;
    delete lb;
    delete rb;
    delete lThumb; 
    delete rThumb;
    delete start;
    delete back;

    delete lTrigger;
    delete rTrigger;
    
    delete lAxis;
    delete rAxis;

    delete dpad;
}

bool VXboxController::IsVBusExists() {
    return isVBusExists();
}

UINT VXboxController::GetNumOfEmptyBusSlots() {
    UCHAR  numOfEmpty;
    GetNumEmptyBusSlots(&numOfEmpty);
    return numOfEmpty;
}

bool VXboxController::IsSlotOccupied(UINT slotIndex) {
    return isControllerExists(slotIndex);
}

void VXboxController::PlugIn() {
    if (IsPluggedIn()) return;

    for (UINT i = 1; i < 5; i++)
    {
        if (!IsSlotOccupied(i)) {
            if (!isControllerOwned(i)) {
                bool result = ::PlugIn(i);
                if (result) {
                    userIndex = i;
                    break;
                }
            }
        }

    }
}

BOOL VXboxController::IsPluggedIn() const {
    return (1 <= userIndex && userIndex <= 4) &&
            isControllerOwned(userIndex) && VXboxController::IsSlotOccupied(userIndex);
}

void VXboxController::UnPlug() {
    ::UnPlug(userIndex);
    userIndex = 0;
}

void VXboxController::UnPlugForce() {
    ::UnPlugForce(userIndex);
    userIndex = 0;
}

UINT VXboxController::GetUserIndex() const{
    return userIndex;
}


std::pair<WORD, WORD> VXboxController::GetVibration() {
    XINPUT_VIBRATION vib;
    ::GetVibration(userIndex, &vib);
    vibration.first = vib.wLeftMotorSpeed;
    vibration.second = vib.wRightMotorSpeed;
    return vibration;
}




