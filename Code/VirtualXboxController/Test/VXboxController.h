#ifndef VXBOXINTERFACE_VXBOXCONTROLLER_H
#define VXBOXINTERFACE_VXBOXCONTROLLER_H

#include <utility>
#include "DPad.h"
#include "..\API\vXboxInterface.h"


#pragma comment(lib, "vXboxInterface")
//#pragma comment(lib, "vXboxInterface-Static")




class VXboxController {
public:
    VXboxController();

    static bool IsVBusExists();
    static UINT GetNumOfEmptyBusSlots();
    static bool IsSlotOccupied(UINT slotIndex);

    void PlugIn();
    BOOL IsPlugedIn();
    void UnPlug();
    void UnPlugForce();

    UINT GetUserIndex();

    void SetButtonA(bool isPressed);
    void SetButtonB(bool isPressed);
    void SetButtonX(bool isPressed);
    void SetButtonY(bool isPressed);
    void SetButtonLb(bool isPressed);
    void SetButtonRb(bool isPressed);
    void SetButtonLThumb(bool isPressed);
    void SetButtonRThumb(bool isPressed);
    void SetButtonStart(bool isPressed);
    void SetButtonBack(bool isPressed);

    void SetTriggerL(byte value);
    void SetTriggerR(byte value);

    void SetAxisX(short value);
    void SetAxisY(short value);
    void SetAxisRX(short value);
    void SetAxisRY(short value);

    void SetDPadUp();
    void SetDPadDown();
    void SetDPadLeft();
    void SetDPadRight();
    void SetDPadOff();

    std::pair<WORD, WORD> GetVibration();

private:
    UINT userIndex;

    bool a, b, x, y, lb, rb, lThumb, rThumb, start, back;
    byte lTrigger, rTrigger;
    std::pair<short, short> lAxis, rAxis;
    DPad dPad;

    std::pair<WORD, WORD> vibration;
};


#endif
