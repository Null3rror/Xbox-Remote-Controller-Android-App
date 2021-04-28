#include "stdafx.h"
#include "VXboxDpad.h"

VXboxDpad::VXboxDpad(BOOL(*setterUp)(UINT), BOOL(*setterDown)(UINT), BOOL(*setterLeft)(UINT), BOOL(*setterRight)(UINT), BOOL(*setterOff)(UINT)) : VXboxDpad(setterUp, setterDown, setterLeft, setterRight, setterOff, 0) {

}

VXboxDpad::VXboxDpad(BOOL(*setterUp)(UINT), BOOL(*setterDown)(UINT), BOOL(*setterLeft)(UINT), BOOL(*setterRight)(UINT), BOOL(*setterOff)(UINT), UINT userIndex) : setterPtrUp(setterUp), setterPtrDown(setterDown), setterPtrLeft(setterLeft), setterPtrRight(setterRight), setterPtrOff(setterOff),
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

bool VXboxDpad::SetValue(BOOL(*setterPtr)(UINT), BOOL& out) {
	if (userIndex <= 0) return false;

	bool status = setterPtr(userIndex);
	if (status) {
		out = true;
	}
	return status;
}

bool VXboxDpad::SetValueOff() {
	return setterPtrOff(userIndex);
}

bool VXboxDpad::SetValueUp() {
	return SetValue(setterPtrUp, valueUp);
}

bool VXboxDpad::SetValueDown() {
	return SetValue(setterPtrDown, valueDown);
}

bool VXboxDpad::SetValueLeft() {
	return SetValue(setterPtrLeft, valueLeft);
}


bool VXboxDpad::SetValueRight() {
	return SetValue(setterPtrRight, valueRight);
}

void VXboxDpad::SetUserIndex(UINT userIndex) {
	this->userIndex = userIndex;
}