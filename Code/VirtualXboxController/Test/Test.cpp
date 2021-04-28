#include "stdafx.h"
#include <stdio.h>
#include "VXboxController.h"
#include "VXboxButton.h"

int main()
{

	VXboxController controller;
	//VXboxButton a(SetBtnA, 1);
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
	controller.lTrigger->SetValue(0);
	controller.rTrigger->SetValue(0);

	for (int T = 0; T < 1000; T++)
	{
		controller.lAxis->SetValueX(T * 300);
		controller.lAxis->SetValueY(T * -300);
		controller.rAxis->SetValueX(T * 100);
		controller.rAxis->SetValueY(T * -100);
		if (T % 2) {
			controller.a->SetValue(true);
			
		}
		else {
			controller.a->SetValue(false);
		}

		switch (T)
		{
		case 0:
			controller.SetDPadUp();
			break;
		case 20:
			controller.SetDPadDown();
			controller.b->SetValue(FALSE);
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

		controller.lTrigger->SetValue(2 * T);
		controller.rTrigger->SetValue(57 + (2 * T));
		printf("In loop %d\n", T);
		Sleep(1000);
	}
	controller.lTrigger->SetValue(255);
	controller.rTrigger->SetValue(255);
	

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
