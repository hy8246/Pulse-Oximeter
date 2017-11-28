// Only return value of a variable when we read from the board 
#define LIGHTWEIGHT 1
// Import required libraries 
#include <SPI.h>
#include "Adafruit_BLE_UART.h"
#include <aREST.h>
// Note: We don't define SPI pins of the BLUE module because they are defined in the module's libray
#define ADAFRUITBLE_REQ 10
#define ADAFRUITBLE_RDY 2 // interrupt pin
#define ADAFRUITBLE_RST 9
#define BLE_READPACKET_TIMEOUT 50
//Define Varibles
int pulsePin = A0;                 // Pulse Sensor purple wire connected to analog pin A0
// Volatile Variables, used in the interrupt service routine!
volatile int BPM;  // int that holds raw Analog in 0. updated every 2mS
volatile int Signal;// holds the incoming raw data
volatile int IBI = 600;             // int that holds the time interval between beats! Must be seeded! 
volatile boolean Pulse = false;     // "True" when User's live heartbeat is detected. "False" when not a "live beat". 
volatile boolean QS = false;    // becomes true when Arduoino finds a beat.
int fSignal;
int fBPM;
String toPhone;
Adafruit_BLE_UART BTLEserial = Adafruit_BLE_UART(ADAFRUITBLE_REQ, ADAFRUITBLE_RDY, ADAFRUITBLE_RST);
// Create aREST instance 
//aREST rest = aREST();
// BLE instance
//////////////////////////////////////////////////////////////////////
void setup() 
{
    Serial.begin(9600);
    pinMode(5, INPUT); // Setup for leads off detection LO +
    pinMode(6, INPUT); // Setup for leads off detection LO -
    pinMode(4,OUTPUT);
    interruptSetup();

    
   Serial.println(F("Demo"));
   BTLEserial.begin(); 
   Serial.println(F("Start BLE")); 
   BTLEserial.setDeviceName("Hao_BLE"); // Give name and ID to device 
}

aci_evt_opcode_t laststatus = ACI_EVT_DISCONNECTED;


void loop() 
{
//Tell the nRF8001 to do whatever it should do 
    
    BTLEserial.pollACI();
    // Ask what is our current status 
    
    aci_evt_opcode_t status = BTLEserial.getState();
    
      if(status != laststatus)
      {
       
            if(status == ACI_EVT_DEVICE_STARTED)
          {
             Serial.println(F("* Advertising started")); 
          }
          if(status == ACI_EVT_CONNECTED)
          { 
            Serial.println(F("* Connected!"));
          }
          if(status == ACI_EVT_DISCONNECTED)
          {
            Serial.println(F("* Disconnected or advertising timed out"));
          }
          // Set the last status
          laststatus = status; 
      }
    
      if (status == ACI_EVT_CONNECTED) 
      {
               
          if (QS == true)
          {     // A Heartbeat Was Found
            fBPM=BPM;
            QS = false;                      // reset the Quantified Self flag for next time    
          }   

             fSignal=analogRead(A0);
             toPhone=String(fSignal)+","+String(fBPM);
  
             // We need to convert the line to bytes, no more than 20 at this time
             Serial.setTimeout(100); 
             uint8_t sendbuffer[20];
             toPhone.getBytes(sendbuffer, 20);
             char sendbuffersize = min(20, toPhone.length()); 
             
             Serial.print(F("\n* Sending -> \"")); //display on Serial Monitor
             Serial.print((char *)sendbuffer); 
             Serial.println("\""); 
   
             BTLEserial.write(sendbuffer,sendbuffersize);  //send to Phone
             fBPM=0; //Reset BPM
             
        // rest.handle(BTLEserial);
      }
   
  delay(20); 
 
}

