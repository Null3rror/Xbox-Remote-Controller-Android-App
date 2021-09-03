#pragma once
#include "windows.h"
#include "stdafx.h"

class VXboxDpad {
public:
	VXboxDpad(BOOL(*setterUp)(UINT), BOOL(*setterDown)(UINT), BOOL(*setterLeft)(UINT), BOOL(*setterRight)(UINT), BOOL(*setterOffUp)(UINT), BOOL(*setterOffDown)(UINT), BOOL(*setterOffLeft)(UINT), BOOL(*setterOffRight)(UINT), BOOL(*setterOff)(UINT));
	VXboxDpad(BOOL(*setterUp)(UINT), BOOL(*setterDown)(UINT), BOOL(*setterLeft)(UINT), BOOL(*setterRight)(UINT), BOOL(*setterOffUp)(UINT), BOOL(*setterOffDown)(UINT), BOOL(*setterOffLeft)(UINT), BOOL(*setterOffRight)(UINT), BOOL(*setterOff)(UINT), UINT userIndex);
	BOOL GetValueUp() const;
	BOOL GetValueDown() const;
	BOOL GetValueLeft() const;
	BOOL GetValueRight() const;
	bool SetValueUp();
	bool SetValueDown();
	bool SetValueLeft();
	bool SetValueRight();
	bool SetValueOffUp();
	bool SetValueOffDown();
	bool SetValueOffLeft();
	bool SetValueOffRight();
	bool SetValueOff();
	void SetUserIndex(UINT userIndex);

private:
	BOOL valueUp, valueDown, valueLeft, valueRight;
	UINT userIndex;
	BOOL(*setterPtrUp)(UINT);
	BOOL(*setterPtrDown)(UINT);
	BOOL(*setterPtrLeft)(UINT);
	BOOL(*setterPtrRight)(UINT);
	BOOL(*setterPtrOffUp)(UINT);
	BOOL(*setterPtrOffDown)(UINT);
	BOOL(*setterPtrOffLeft)(UINT);
	BOOL(*setterPtrOffRight)(UINT);
	BOOL(*setterPtrOff)(UINT);

	bool SetValue(BOOL(*setterPtr)(UINT), BOOL out);

};