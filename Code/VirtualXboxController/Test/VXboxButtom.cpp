#include "stdafx.h"
#include "VXboxButton.h"

VXboxButton::VXboxButton(BOOL(*setter)(UINT, BOOL)) : VXboxButton(setter, 0) {
	
}

VXboxButton::VXboxButton(BOOL(*setter)(UINT, BOOL), UINT userIndex) : setterPtr(setter), value(false), userIndex(userIndex) {

}

bool VXboxButton::GetValue() const {
	return value;
}

bool VXboxButton::SetValue(bool val) {
	if (userIndex <= 0) return false;
	
	bool status = setterPtr(userIndex, val);
	if (status) {
		value = val;
	}
	return status;

}

void VXboxButton::SetUserIndex(UINT userIndex) {
	this->userIndex = userIndex;
}
