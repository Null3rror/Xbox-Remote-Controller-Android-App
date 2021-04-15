#include <Windows.h>

#ifndef ANDROID_CONTROLLER_VIRTUALMOUSE_H
#define ANDROID_CONTROLLER_VIRTUALMOUSE_H
class VirtualMouse {
    private:
        float mouseSleepAmount;
        POINT cursorPosition;
    public:
        VirtualMouse();
        VirtualMouse(float mouseSleepAmount);
        void PressMouse(bool shouldPressLeft) const;
        void PressMouseDown(bool shouldPressLeft) const;
        void PressMouseUp(bool shouldPressLeft) const;
        void MoveCursor(int deltaX, int deltaY);
};

#endif //ANDROID_CONTROLLER_VIRTUALMOUSE_H
