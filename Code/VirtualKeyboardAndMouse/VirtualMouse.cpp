#include "VirtualMouse.h"


VirtualMouse::VirtualMouse() : VirtualMouse(50) {

}

VirtualMouse::VirtualMouse(float mouseSleepAmount) : mouseSleepAmount(mouseSleepAmount) {

}


void VirtualMouse::PressMouse(bool shouldPressLeft) const {
    PressMouseDown(shouldPressLeft);
    PressMouseUp(shouldPressLeft);
}

void VirtualMouse::PressMouseDown(bool shouldPressLeft) const {
    INPUT input = {0};

    input.type = INPUT_MOUSE;
    input.mi.dwFlags = MOUSEEVENTF_RIGHTDOWN;
    if (shouldPressLeft)
        input.mi.dwFlags = MOUSEEVENTF_LEFTDOWN;

    SendInput(1, &input, sizeof(input));
    Sleep(mouseSleepAmount);
}

void VirtualMouse::PressMouseUp(bool shouldPressLeft) const {
    INPUT input = {0};

    input.type = INPUT_MOUSE;
    input.mi.dwFlags = MOUSEEVENTF_RIGHTUP;
    if (shouldPressLeft)
        input.mi.dwFlags = MOUSEEVENTF_LEFTUP;

    SendInput(1, &input, sizeof(input));
}

void VirtualMouse::MoveCursor(int deltaX, int deltaY) {
    cursorPosition.x += deltaX;
    cursorPosition.y += deltaY;

    SetCursorPos(cursorPosition.x, cursorPosition.y);
    GetCursorPos(&cursorPosition);
    Sleep(10);
}
