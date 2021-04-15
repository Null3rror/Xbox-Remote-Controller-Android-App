#include <Windows.h>

void PressKey(char keyName) {
    short key = VkKeyScanA(keyName);
    unsigned int mappedKey = MapVirtualKeyA(LOBYTE(key), 0);;

    INPUT input = {0};

    input.type = INPUT_KEYBOARD;
    input.ki.dwFlags = KEYEVENTF_SCANCODE;
    input.ki.wScan   = mappedKey;

    SendInput(1, &input, sizeof(input));
    Sleep(10);

    input.ki.dwFlags = KEYEVENTF_SCANCODE | KEYEVENTF_KEYUP;
    SendInput(1, &input, sizeof(input));
}

int main() {
    while (true) {
        if (GetAsyncKeyState(VK_NUMPAD0)) {
            break;
        }
        if (GetAsyncKeyState(VK_NUMPAD8)) {
            PressKey('w');
        }
        if (GetAsyncKeyState(VK_NUMPAD4)) {
            PressKey('a');
        }
        if (GetAsyncKeyState(VK_NUMPAD2)) {
            PressKey('s');
        }
        if (GetAsyncKeyState(VK_NUMPAD6)) {
            PressKey('d');
        }
    }
    return 0;
}


