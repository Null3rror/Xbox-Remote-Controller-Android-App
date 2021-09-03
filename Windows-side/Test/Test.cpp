#include "stdafx.h"
#include <winsock2.h>
#include <Ws2tcpip.h>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <thread>
#include <algorithm>
#include <windows.h>
#include <string>
#include "json.h"
#include "VXboxController.h"
#include "VXboxButton.h"
#include "Serial.h"
#include "Test.h"


//#define _WINSOCK_DEPRECATED_NO_WARNINGS

#pragma comment(lib, "ws2_32.lib") //Winsock Library

#define BUFLEN 512	//Max length of buffer
#define PORT 6970	//The port on which to listen for incoming data
int port_number[] = { 4000,5000,6000,7000 };
bool used_port[4] = {false, false, false, false};
std::thread ths[4] = {};

using nlohmann::json;


std::string ConvertToString(char* a)
{
	std::string s = a;
	return s;
}

LONG now = 0;
LONG now2;
SYSTEMTIME time_ms;

json ReadInputs(char* buf) {
	std::string data = ConvertToString(buf);
	if (now == 0) {
		GetSystemTime(&time_ms);
		now = (time_ms.wSecond * 1000) + time_ms.wMilliseconds;
	}
	else {
		GetSystemTime(&time_ms);
		now2 = (time_ms.wSecond * 1000) + time_ms.wMilliseconds;
		std::cout << (now2 - now) << std::endl;
		now = now2;
	}
	
	//std::cout << data << std::endl;
	json j = json::parse(data);
	return j;

	
}

int findFreeIndex()
{
	for (int i = 0; i < 4; i++) {
		if (!used_port[i])
		{
			used_port[i] = true;
			return i;
		}
	}
	return -1;
}

void ExtractInput(json j, short& joyX, short& joyY, short& mouseX, short& mouseY, int& buttons) {
	joyX = j["left_X"].get<short>();
	joyY = j["left_Y"].get<short>();
	mouseX = j["right_X"].get<short>();
	mouseY = j["right_Y"].get<short>();
	buttons = j["buttons"].get<int>();
}

bool isVXboxSideOk() {
	// Test if bus exists
	BOOL bus = VXboxController::IsVBusExists();
	if (bus)
		printf("Virtual Xbox bus exists\n\n");
	else
	{
		printf("Virtual Xbox bus does NOT exist - Aborting\n\n");
		
		return false;
	}


	UINT numOfEmptySlots = VXboxController::GetNumOfEmptyBusSlots();
	if (numOfEmptySlots)
		printf("\n\nNumber of Empty Slots: %d\n", numOfEmptySlots);
	else {
		printf("\n\nCannot determine Number of Empty Slots");
		return false;
	}


	printf("\n");
	return true;
}


bool isPortValid(int portIndex) {
	return portIndex >= 0;
}

BOOL shouldClose = false;


void run(int portIndex) {
	SOCKET s;
	struct sockaddr_in server, si_other;
	int slen, recv_len;
	char buf[BUFLEN];
	WSADATA wsa;

	slen = sizeof(si_other);


	//Initialise winsock
	printf("\nInitialising Winsock...");
	if (WSAStartup(MAKEWORD(2, 2), &wsa) != 0)
	{
		printf("Failed. Error Code : %d", WSAGetLastError());
		exit(EXIT_FAILURE);
	}
	printf("Initialised.\n");

	//Create a socket
	if ((s = socket(AF_INET, SOCK_DGRAM, 0)) == INVALID_SOCKET)
	{
		printf("Could not create socket : %d", WSAGetLastError());
	}
	printf("Socket created.\n");

	//Prepare the sockaddr_in structure
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;
	server.sin_port = htons(port_number[portIndex]);

	//Bind
	if (bind(s, (struct sockaddr*)&server, sizeof(server)) == SOCKET_ERROR)
	{
		printf("Bind failed with error code : %d", WSAGetLastError());
		exit(EXIT_FAILURE);
	}
	puts("Bind done");

	int timeout = 10000;
	if (setsockopt(s, SOL_SOCKET, SO_RCVTIMEO, (char*)&timeout, sizeof(int))) {
		wprintf(L"setsockopt for SO_RCVTIMEO failed with error: %u\n", WSAGetLastError());
	}
	else {
		wprintf(L"Set SO_RCVTIMEO: ON\n");
	}

	VXboxController controller;


	UINT numOfEmptySlots = VXboxController::GetNumOfEmptyBusSlots();
	if (numOfEmptySlots)
		printf("\n\nNumber of Empty Slots: %d\n", numOfEmptySlots);
	else
		printf("\n\nCannot determine Number of Empty Slots");


	//controller.PlugIn();


	printf("\n");
	controller.lTrigger->SetValue(0);
	controller.rTrigger->SetValue(0);




	short joyX = 0, joyY = 0, mouseX = 0, mouseY = 0;
	int buttons = 0;
	WORD LeftMotor, RightMotor;
	std::pair<WORD, WORD> vib;
	std::string message;
	while (!shouldClose) {
		//clear the buffer by filling null, it might have previously received data
		memset(buf, '\0', BUFLEN);

		//try to receive some data, this is a blocking call
		if ((recv_len = recvfrom(s, buf, BUFLEN, 0, (struct sockaddr*)&si_other, &slen)) == SOCKET_ERROR)
		{
			/*printf("recvfrom() failed with error code : %d", WSAGetLastError());
			exit(EXIT_FAILURE);*/
		}

		if ((buf != NULL) && (buf[0] == '\0')) {
			printf("c is empty\n");
			continue;
		}

		//terminate connection
		if (strcmp(buf, "end") == 0)
		{
			message = "bye";
			printf("About to say BYE\n");
			copy(message.begin(), message.end(), buf);
			if (sendto(s, buf, message.length(), 0, (struct sockaddr*)&si_other, slen) == SOCKET_ERROR)
			{
				printf("sendto() failed with error code : %d", WSAGetLastError());
				exit(EXIT_FAILURE);
			}
			break;
		}
		
		json j = ReadInputs(buf);
		ExtractInput(j, joyX, joyY, mouseX, mouseY, buttons);
		controller.a->SetValue(buttons & 1);
		controller.b->SetValue(buttons & 2);
		controller.x->SetValue(buttons & 4);
		controller.y->SetValue(buttons & 8);
		buttons & 16 ? controller.dpad->SetValueUp()    : controller.dpad->SetValueOffUp();
		buttons & 32 ? controller.dpad->SetValueDown()  : controller.dpad->SetValueOffDown();
		buttons & 64 ? controller.dpad->SetValueRight() : controller.dpad->SetValueOffRight();
		buttons & 128 ? controller.dpad->SetValueLeft() : controller.dpad->SetValueOffLeft();

		controller.start->SetValue(buttons & 256);
		controller.back->SetValue(buttons & 512);
		controller.rThumb->SetValue(buttons & 1024);
		controller.lThumb->SetValue(buttons & 2048);
		controller.rTrigger->SetValue(((buttons & 4096) == 4096) * 255);
		controller.rb->SetValue(buttons & 8192);
		controller.lTrigger->SetValue(((buttons & 16384) == 16384) * 255);
		controller.lb->SetValue(buttons & 32768);

		controller.rAxis->SetValueX(mouseX);
		controller.rAxis->SetValueY(mouseY);
		controller.lAxis->SetValueX(joyX);
		controller.lAxis->SetValueY(joyY);

		vib = controller.GetVibration();
		message = "{\"vib\":" + std::to_string(max(vib.first, vib.second)) + "}";
		copy(message.begin(), message.end(), buf);
		if (!shouldClose && sendto(s, buf, message.length(), 0, (struct sockaddr*)&si_other, slen) == SOCKET_ERROR)
		{
			printf("sendto() failed with error code : %d", WSAGetLastError());
			exit(EXIT_FAILURE);
		}
	}

	closesocket(s);
	WSACleanup();
	ths[portIndex] = {};
	used_port[portIndex] = false;
	controller.UnPlugForce();
	
	
}




BOOL WINAPI CtrlHandler(DWORD fdwCtrlType)
{
	if (fdwCtrlType == CTRL_C_EVENT ||
		fdwCtrlType == CTRL_CLOSE_EVENT ||
		fdwCtrlType == CTRL_SHUTDOWN_EVENT) 
	{
		Beep(750, 300);
		shouldClose = true;
		return TRUE;
	}
	else {
		return FALSE;
	}
	
}



int main()
{
	SOCKET s;
	struct sockaddr_in server, si_other;
	int slen, recv_len;
	char buf[BUFLEN];
	WSADATA wsa;
	slen = sizeof(si_other);
	printf("Press c to forcefuly unplug controllers!\n");
	if (getchar() == 'c') {
		for (UINT i = 1; i < 5; i++) {
			UnPlugForce(i);
		}
	}
	
	

	if (!SetConsoleCtrlHandler(CtrlHandler, TRUE))
	{
		printf("\nERROR: Could not set control handler");
		return 1;
	}

	//Initialise winsock
	printf("\nInitialising Winsock...");
	if (WSAStartup(MAKEWORD(2, 2), &wsa) != 0)
	{
		printf("Failed. Error Code : %d", WSAGetLastError());
		exit(EXIT_FAILURE);
	}
	printf("Initialised.\n");


	//Create a socket
	if ((s = socket(AF_INET, SOCK_DGRAM, 0)) == INVALID_SOCKET)
	{
		printf("Could not create socket : %d", WSAGetLastError());
	}
	printf("Socket created.\n");

	//Prepare the sockaddr_in structure
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;
	server.sin_port = htons(PORT);

	//Bind
	if (bind(s, (struct sockaddr*)&server, sizeof(server)) == SOCKET_ERROR)
	{
		printf("Bind failed with error code : %d", WSAGetLastError());
		exit(EXIT_FAILURE);
	}
	puts("Bind done");	
	
	int timeout = 1000;
	if (setsockopt(s, SOL_SOCKET, SO_RCVTIMEO, (char*)&timeout, sizeof(int))) {
		wprintf(L"setsockopt for SO_RCVTIMEO failed with error: %u\n", WSAGetLastError());
	}
	else {
		wprintf(L"Set SO_RCVTIMEO: ON\n");
	}



	if (!isVXboxSideOk())
		return -1;

	
	while (!shouldClose) {
		
		printf("Waiting for data...\n");
		//fflush(stdout);

		//clear the buffer by filling null, it might have previously received data
		memset(buf, '\0', BUFLEN);

		//try to receive some data, this is a blocking call
		if ((recv_len = recvfrom(s, buf, BUFLEN, 0, (struct sockaddr*)&si_other, &slen)) == SOCKET_ERROR)
		{
			//printf("recvfrom() failed with error code : %d", WSAGetLastError());
			//exit(EXIT_FAILURE);
		}

		
		if ((buf != NULL) && (buf[0] == '\0')) {
			//printf("c is empty\n");
			continue;
		}
		
		if (strcmp(buf, "connect") == 0)
		{
			int portIndex = findFreeIndex();
			std::string message = isPortValid(portIndex) ? 
				"{\"port\":" + std::to_string(port_number[portIndex]) + ",\"index\":" + std::to_string(portIndex) + "}" : 
				"{\"error\":\"not available free port\"";
				
			copy(message.begin(), message.end(), buf);
			if (sendto(s, buf, message.length(), 0, (struct sockaddr*)&si_other, slen) == SOCKET_ERROR)
			{
				printf("sendto() failed with error code : %d", WSAGetLastError());
				exit(EXIT_FAILURE);
			}

			if (isPortValid(portIndex))
			{
				ths[portIndex] = std::thread(run, portIndex);
				ths[portIndex].detach();
			}

		}
	}

	/*for (int i = 0; i < 4; i++)
	{
		if (used_port[i])
		{
			ths[i].join();
		}
	}*/


	closesocket(s);
	WSACleanup();

	return EXIT_SUCCESS;
}
