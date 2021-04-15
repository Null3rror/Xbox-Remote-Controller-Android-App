#include "VirtualKey.h"
#include "VirtualMouse.h"


int main() {
    VirtualKey vKey(10);
    VirtualMouse vMouse(500);
    while (true) {
        if (GetAsyncKeyState(VK_NUMPAD0)) {
            break;
        }
        if (GetAsyncKeyState(VK_NUMPAD8)) {
            vKey.PressKey('w');
        }
        if (GetAsyncKeyState(VK_NUMPAD4)) {
            vKey.PressKey('a');
        }
        if (GetAsyncKeyState(VK_NUMPAD2)) {
            vKey.PressKey('s');
        }
        if (GetAsyncKeyState(VK_NUMPAD6)) {
            vKey.PressKey('d');
        }
        if (GetAsyncKeyState(VK_SPACE)) {
            vMouse.PressMouse(true);
        }
        if (GetAsyncKeyState(VK_LSHIFT)) {
            vMouse.MoveCursor(5, 0 );
        }

    }
    return 0;
}


