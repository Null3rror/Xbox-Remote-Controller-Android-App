#include "VirtualKey.h"

VirtualKey::VirtualKey() : VirtualKey(10) {

}

VirtualKey::VirtualKey(float keySleepAmount) : keySleepAmount(keySleepAmount) {
}


void VirtualKey::PressKey(char keyName) const {
    short key = VkKeyScanA(keyName);
    unsigned int mappedKey = MapVirtualKeyA(LOBYTE(key), 0);;

    INPUT input = {0};

    input.type = INPUT_KEYBOARD;
    input.ki.dwFlags = KEYEVENTF_SCANCODE;
    input.ki.wScan   = mappedKey;

    SendInput(1, &input, sizeof(input));
    Sleep(keySleepAmount);

    input.ki.dwFlags = KEYEVENTF_SCANCODE | KEYEVENTF_KEYUP;
    SendInput(1, &input, sizeof(input));
}

