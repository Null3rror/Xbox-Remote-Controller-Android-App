#pragma once
#include "windows.h"
#include "stdafx.h"

class VXboxAxis {
public:
	VXboxAxis(BOOL(*setterX)(UINT, SHORT), BOOL(*setterY)(UINT, SHORT));
	VXboxAxis(BOOL(*setterX)(UINT, SHORT), BOOL(*setterY)(UINT, SHORT), UINT userIndex);
	SHORT GetValueX() const;
	SHORT GetValueY() const;
	bool SetValueX(SHORT val);
	bool SetValueY(SHORT val);
	
	void SetUserIndex(UINT userIndex);

private:
	SHORT valueX, valueY;
	UINT userIndex;
	BOOL(*setterPtrX)(UINT, SHORT);
	BOOL(*setterPtrY)(UINT, SHORT);

	bool SetValue(BOOL(*setterPtrX)(UINT, SHORT), SHORT& out, SHORT val);
};