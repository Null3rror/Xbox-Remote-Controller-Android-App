#include "stdafx.h"
#include "VXboxDpad.h"

VXboxDpad::VXboxDpad(BOOL(*setterUp)(UINT), BOOL(*setterDown)(UINT), BOOL(*setterLeft)(UINT), BOOL(*setterRight)(UINT), BOOL(*setterOffUp)(UINT), BOOL(*setterOffDown)(UINT), BOOL(*setterOffLeft)(UINT), BOOL(*setterOffRight)(UINT), BOOL(*setterOff)(UINT)) : VXboxDpad(setterUp, setterDown, setterLeft, setterRight, setterOffUp, setterOffDown, setterOffLeft, setterOffRight, setterOff, 0) {

}

VXboxDpad::VXboxDpad(BOOL(*setterUp)(UINT), BOOL(*setterDown)(UINT), BOOL(*setterLeft)(UINT), BOOL(*setterRight)(UINT), BOOL(*setterOffUp)(UINT), BOOL(*setterOffDown)(UINT), BOOL(*setterOffLeft)(UINT), BOOL(*setterOffRight)(UINT), BOOL(*setterOff)(UINT), UINT userIndex) : 
	setterPtrUp(setterUp), setterPtrDown(setterDown), setterPtrLeft(setterLeft), setterPtrRight(setterRight), 
	setterPtrOffUp(setterOffUp), setterPtrOffDown(setterOffDown), setterPtrOffLeft(setterOffLeft), setterPtrOffRight(setterOffRight), setterPtrOff(setterOff),
	valueUp(0), valueDown(0), valueLeft(0), valueRight(0), userIndex(userIndex) {

}

INT VXboxDpad::GetValueUp() const {
	return valueUp;
}

INT VXboxDpad::GetValueDown() const {
	return valueDown;
}

INT VXboxDpad::GetValueLeft() const {
	return valueLeft;
}

INT VXboxDpad::GetValueRight() const {
	return valueRight;
}

bool VXboxDpad::SetValue(BOOL(*setterPtr)(UINT), BOOL out) {
	if (userIndex <= 0) return false;

	bool status = setterPtr(userIndex);
	/*if (status) {
		out = true;
	}*/
	return status;
}

bool VXboxDpad::SetValueOff() {
	return setterPtrOff(userIndex);
}

bool VXboxDpad::SetValueUp() {
	return SetValue(setterPtrUp, 1);
}

bool VXboxDpad::SetValueDown() {
	return SetValue(setterPtrDown, 1);
}

bool VXboxDpad::SetValueLeft() {
	return SetValue(setterPtrLeft, 1);
}

bool VXboxDpad::SetValueRight() {
	return SetValue(setterPtrRight, 1);
}

bool VXboxDpad::SetValueOffUp() {
	return SetValue(setterPtrOffUp, 0);
}

bool VXboxDpad::SetValueOffDown() {
	return SetValue(setterPtrOffDown, 0);
}

bool VXboxDpad::SetValueOffLeft() {
	return SetValue(setterPtrOffLeft, 0);
}

bool VXboxDpad::SetValueOffRight() {
	return SetValue(setterPtrOffRight, 0);
}

void VXboxDpad::SetUserIndex(UINT userIndex) {
	this->userIndex = userIndex;
}