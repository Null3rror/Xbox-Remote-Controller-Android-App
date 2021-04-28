#pragma once
#include "windows.h"
#include "stdafx.h"

class VXboxTrigger {
public:
	VXboxTrigger(BOOL(*setter)(UINT, BYTE));
	VXboxTrigger(BOOL(*setter)(UINT, BYTE), UINT userIndex);
	BYTE GetValue();
	bool SetValue(BYTE val);
	void SetUserIndex(UINT userIndex);

private:
	BYTE value;
	UINT userIndex;
	BOOL(*setterPtr)(UINT, BYTE);
};