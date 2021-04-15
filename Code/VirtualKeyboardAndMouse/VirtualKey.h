#ifndef ANDROID_CONTROLLER_VIRTUALKEY_H
#define ANDROID_CONTROLLER_VIRTUALKEY_H

#include <Windows.h>

class VirtualKey {
    private:
        float keySleepAmount;
    public:
        VirtualKey();
        VirtualKey(float keySleepAmount);
        void PressKey(char keyName) const;

};



#endif //ANDROID_CONTROLLER_VIRTUALKEY_H
