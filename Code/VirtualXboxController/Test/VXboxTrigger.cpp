#include "stdafx.h"
#include "VXboxTrigger.h"

VXboxTrigger::VXboxTrigger(BOOL(*setter)(UINT, BYTE)) : VXboxTrigger(setter, 0) {

}

VXboxTrigger::VXboxTrigger(BOOL(*setter)(UINT, BYTE), UINT userIndex) : setterPtr(setter), value(false), userIndex(userIndex) {

}

BYTE VXboxTrigger::GetValue() {
	return value;
}

bool VXboxTrigger::SetValue(BYTE val) {
	if (userIndex <= 0) return false;

	bool status = setterPtr(userIndex, val);
	if (status) {
		value = val;
	}
	return status;

}

void VXboxTrigger::SetUserIndex(UINT userIndex) {
	this->userIndex = userIndex;
}
