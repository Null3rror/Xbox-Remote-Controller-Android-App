#pragma once
#include "windows.h"
#include "stdafx.h"

class VXboxButton {
public:
	VXboxButton(BOOL (*setter)(UINT, BOOL));
	VXboxButton(BOOL (*setter)(UINT, BOOL), UINT userIndex);
	bool GetValue() const;
	bool SetValue(bool val);
	void SetUserIndex(UINT userIndex);

private:
	bool value;
	UINT userIndex;
	BOOL(*setterPtr)(UINT, BOOL);
};