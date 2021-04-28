#ifndef VXBOXINTERFACE_VXBOXCONTROLLER_H
#define VXBOXINTERFACE_VXBOXCONTROLLER_H

#include <utility>
#include "windows.h"
#include "stdafx.h"
#include "DPad.h"
#include "..\API\vXboxInterface.h"
#include "VXboxButton.h"


#pragma comment(lib, "vXboxInterface")
//#pragma comment(lib, "vXboxInterface-Static")




class VXboxController {
public:
    VXboxController();
    ~VXboxController();


    static bool IsVBusExists();
    static UINT GetNumOfEmptyBusSlots();
    static bool IsSlotOccupied(UINT slotIndex);

    void PlugIn();
    BOOL IsPluggedIn() const;
    void UnPlug();
    void UnPlugForce();

    UINT GetUserIndex() const;

    VXboxButton* a;
    VXboxButton* b;
    VXboxButton* x;
    VXboxButton* y;
    VXboxButton* lb;
    VXboxButton* rb;
    VXboxButton* lThumb;
    VXboxButton* rThumb;
    VXboxButton* start;
    VXboxButton* back;

    void SetTriggerL(BYTE value);
    void SetTriggerR(BYTE value);

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


    BYTE lTrigger, rTrigger;
    std::pair<short, short> lAxis, rAxis;
    DPad dPad;

    std::pair<WORD, WORD> vibration;
};


#endif
