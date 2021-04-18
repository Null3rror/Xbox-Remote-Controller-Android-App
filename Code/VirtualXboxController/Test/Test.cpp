#include "stdafx.h"
#include <stdio.h>
#include "VXboxController.h"

int main()
{

	VXboxController controller;

	// Test if bus exists
	BOOL bus = VXboxController::IsVBusExists();
	if (bus)
		printf("Virtual Xbox bus exists\n\n");
	else
	{
		printf("Virtual Xbox bus does NOT exist - Aborting\n\n");
		getchar();
		return -1;
	}





	UINT numOfEmptySlots = VXboxController::GetNumOfEmptyBusSlots();
	if (numOfEmptySlots)
		printf("\n\nNumber of Empty Slots: %d\n", numOfEmptySlots);
	else
		printf("\n\nCannot determine Number of Empty Slots");

	controller.PlugIn();


	printf("\n");
	controller.SetTriggerL(0);
	controller.SetTriggerR(0);

	for (int T = 0; T < 1000; T++)
	{
		controller.SetAxisX(T * 300);
		controller.SetAxisY(T * -300);
		controller.SetAxisRX(T * 100);
		controller.SetAxisRY(T * -100);
		if (T % 2) {
			controller.SetButtonA(TRUE);
		}
		else {
			controller.SetButtonA(FALSE);
		}

		switch (T)
		{
		case 0:
			controller.SetDPadUp();
			break;
		case 20:
			controller.SetDPadDown();
			controller.SetButtonB(FALSE);
			break;
		case 40:
			controller.SetDPadLeft();
			break;
		case 60:
			controller.SetDPadRight();
			break;
		case 80:
			break;
		case 99:
			controller.SetDPadOff();
			break;
		}

		controller.SetTriggerL(2 * T);
		controller.SetTriggerR(57 + (2 * T));
		printf("In loop %d\n", T);
		Sleep(1000);
	}
	controller.SetTriggerL(255);
	controller.SetTriggerR(255);

	printf("Press any key to detect device feedback \n");
	getchar();

	//WORD LeftMotor, RightMotor;
	UCHAR Led;
	BOOL Led_Ok;
	std::pair<WORD, WORD> vib;
	int iDev = 0;
	while (getchar() != 'q')
	{
		for (iDev = 1; iDev < 5; iDev++)
		{
			Led_Ok = GetLedNumber(iDev, &Led);
			vib = controller.GetVibration();
			printf("LED: %d; Left Motor: %d; Right Motor: %d  \n", Led, vib.first, vib.second);
		};
	}

	printf("Press any key to remove devices \n");
	getchar();
	// UnInstall Virtual Devices
	for (UINT i = 0; i < 6; i++)
	{
		controller.UnPlugForce();
		printf("UnPlug (Forced) device %d\n", i);
	}


	printf("Press any key to exit \n");
	getchar();

	return 0;

}
