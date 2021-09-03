#include "stdafx.h"
#include "VXboxAxis.h"

VXboxAxis::VXboxAxis(BOOL(*setterX)(UINT, SHORT), BOOL(*setterY)(UINT, SHORT)) : VXboxAxis(setterX, setterY, 0) {

}

VXboxAxis::VXboxAxis(BOOL(*setterX)(UINT, SHORT), BOOL(*setterY)(UINT, SHORT), UINT userIndex) : setterPtrX(setterX), setterPtrY(setterY), valueX(0), valueY(0), userIndex(userIndex) {

}

SHORT VXboxAxis::GetValueX() const {
	return valueX;
}

SHORT VXboxAxis::GetValueY() const {
	return valueY;
}

bool VXboxAxis::SetValue(BOOL(*setterPtr)(UINT, SHORT), SHORT& out, SHORT val) {
	if (userIndex <= 0) return false;

	bool status = setterPtr(userIndex, val);
	if (status) {
		out = val;
	}
	return status;
}

bool VXboxAxis::SetValueX(SHORT val) {
	return SetValue(setterPtrX, valueX, val);

}


bool VXboxAxis::SetValueY(SHORT val) {
	return SetValue(setterPtrY, valueY, val);

}

void VXboxAxis::SetUserIndex(UINT userIndex) {
	this->userIndex = userIndex;
}
