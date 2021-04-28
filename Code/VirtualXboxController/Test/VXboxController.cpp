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

    lTrigger = 0;
    rTrigger = 0;

    lAxis = std::make_pair(0, 0);
    rAxis = std::make_pair(0, 0);

    dPad = {false, false, false, false};
    vibration = std::make_pair(0, 0);
}

VXboxController::~VXboxController() {
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
    UnPlugForce();
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



void VXboxController::SetTriggerL(BYTE value) {
    lTrigger = value;
    ::SetTriggerL(userIndex, value);
}

void VXboxController::SetTriggerR(BYTE value) {
    rTrigger = value;
    ::SetTriggerR(userIndex, value);
}

void VXboxController::SetAxisX(short value) {
    lAxis.first = value;
    ::SetAxisX(userIndex, value);
}

void VXboxController::SetAxisY(short value) {
    lAxis.second = value;
    ::SetAxisY(userIndex, value);
}

void VXboxController::SetAxisRX(short value) {
    rAxis.first = value;
    SetAxisRx(userIndex, value);
}

void VXboxController::SetAxisRY(short value) {
    rAxis.second = value;
    SetAxisRy(userIndex, value);
}

void VXboxController::SetDPadUp() {
    dPad.up = true;
    ::SetDpadUp(userIndex);
}

void VXboxController::SetDPadDown() {
    dPad.down = true;
    ::SetDpadDown(userIndex);
}

void VXboxController::SetDPadLeft() {
    dPad.left = true;
    ::SetDpadLeft(userIndex);
}

void VXboxController::SetDPadRight() {
    dPad.right = true;
    ::SetDpadRight(userIndex);
}

void VXboxController::SetDPadOff() {
    dPad.up = false;
    dPad.down = false;
    dPad.left = false;
    dPad.right = false;
    ::SetDpadOff(userIndex);
}

std::pair<WORD, WORD> VXboxController::GetVibration() {
    XINPUT_VIBRATION vib;
    ::GetVibration(userIndex, &vib);
    vibration.first = vib.wLeftMotorSpeed;
    vibration.second = vib.wRightMotorSpeed;
    return vibration;
}




