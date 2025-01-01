#include <iostream>
#include <stdlib.h> 
#include <algorithm>
#include <pthread.h>
#include <string.h>
#include <math.h>
#include <chrono>
#include <fstream>
#include <iomanip>

int mini;
int maxi;
int rangi;
int modi;
double mediani;
int sumi;
double ameani;
double hmeani;
double devi;
double irangi;

/*result values*/

int size; //size of array declared

void* minimum(void* list) { //find least element of array
    int* aList=(int*)list;
    mini=aList[0];
    for (int i=0; i<size; i++) {
        if (aList[i]<mini) {
            mini=aList[i];
        }
    }
}
void* maximum(void* list) { //find greatest element of array
    int* aList=(int*)list;
    maxi=aList[0];
    for (int i=0; i<size; i++) {
        if (aList[i]>maxi) {
            maxi=aList[i];
        }
    }
}
void* range(void* list) { //find range of array
    int* aList=(int*)list;
    int max1=aList[0];
    int min1=aList[0];
    for (int i=0; i<size; i++) {
        if (aList[i]<min1) {
            min1=aList[i];
        }
        else if (aList[i]>max1) {
            max1=aList[i];
        }
    }
    rangi=max1-min1;
}

void* mode(void* list) { //find mode element of array
    int aList[size];
    memcpy(aList,list , size*4);
    std::sort(aList, aList+size);
    modi=aList[0];
    int modeNum=1;
    int curr=aList[0];
    int currNum=0;
    for (int i=0; i<size; i++) {
        if (aList[i]==curr) {
            currNum+=1;
        }
        else {
            curr=aList[i];
            currNum=1;
        }
        if (modeNum<currNum) {
            modeNum=currNum;
            modi=curr;
        }
    }
}

void* median(void* list) { //find median element of array
    int aList[size];
    memcpy(aList,list , size*4);
    std::sort(aList, aList+size);
    if (size%2==0) {
        mediani=double(aList[size/2]+aList[(size/2)-1]);
        mediani=mediani/2;
    }
    else {
        mediani=aList[(size-1)/2];
    }
}

void* sum(void* list) { //find sum of elements of array
    sumi=0;
    int* aList=(int*)list;
    for (int i=0; i<size; i++) {
        sumi=sumi+aList[i];
    }
}
void* amean(void* list) { //find arithmetic mean of array
    ameani=0;
    int* aList=(int*)list;
    for (int i=0; i<size; i++) {
        ameani+=aList[i];
    }
    ameani/=size;
}
void* hmean(void* list) { //find harmonic mean of array
    hmeani=0;
    int* aList=(int*)list;
    for (int i=0; i<size; i++) {
        double result = pow(aList[i], -1);
        hmeani+=result;
    }
    hmeani=size/hmeani;
}
void* deviation(void* list) { //find standard deviation of array
    double jameani=0;
    int* aList=(int*)list;
    for (int i=0; i<size; i++) {
        jameani+=aList[i];
    }
    jameani/=size;
    for (int i=0; i<size; i++) {
        devi+=(aList[i]-jameani)*(aList[i]-jameani);
    }
    devi/=size-1;
    devi = pow(devi, 0.5);

}

void* irange(void* list) { //find interquartile range of array
    int aList[size];
    memcpy(aList,list , size*4);
    std::sort(aList, aList+size);
    if (size%4==0) {
        irangi=aList[3*size/4]+aList[3*size/4-1]-aList[size/4]-aList[size/4-1];
        irangi/=2;
    }
    else if (size%4==1) {
        irangi=aList[(3*size-3)/4]+aList[(3*size+1)/4]-aList[(size-5)/4]-aList[(size-1)/4];
        irangi/=2;
    }
    else if (size%4==2) {
        irangi=aList[((size-2)*3/4)+1]-aList[(size-2)/4];
    }
    else {
        irangi=aList[((size-3)*3/4)+2]-aList[(size-3)/4];
    }
}

int main(int argc, char** argv) {
    
    size=std::stoi(argv[1]);
    int myList[size];
    
    for(int i=0; i<size; i++) { //generating random array
      myList[i]=rand()%9001+1000;
    }

    std::chrono::steady_clock::time_point begin = std::chrono::steady_clock::now(); //timer begins
    pthread_t thread1;
    pthread_t thread2;
    pthread_t thread3;
    pthread_t thread4;
    pthread_t thread5;
    pthread_t thread6;
    pthread_t thread7;
    pthread_t thread8;
    pthread_t thread9;
    pthread_t thread10;
    pthread_create(&thread1, NULL, minimum, (void*) myList);
    pthread_create(&thread2, NULL, maximum, (void*) myList);
    pthread_create(&thread3, NULL, range, (void*) myList);
    pthread_create(&thread4, NULL, mode, (void*) myList);
    pthread_create(&thread5, NULL, median, (void*) myList);
    pthread_create(&thread6, NULL, sum, (void*) myList);
    pthread_create(&thread7, NULL, amean, (void*) myList);
    pthread_create(&thread8, NULL, hmean, (void*) myList);
    pthread_create(&thread9, NULL, deviation, (void*) myList);
    pthread_create(&thread10, NULL, irange, (void*) myList);
    pthread_join(thread1, NULL);
    pthread_join(thread2, NULL);
    pthread_join(thread3, NULL);
    pthread_join(thread4, NULL);
    pthread_join(thread5, NULL);
    pthread_join(thread6, NULL);
    pthread_join(thread7, NULL);
    pthread_join(thread8, NULL);
    pthread_join(thread9, NULL);
    pthread_join(thread10, NULL);
    std::chrono::steady_clock::time_point end = std::chrono::steady_clock::now(); //timer ends

    double time=std::chrono::duration_cast<std::chrono::nanoseconds> (end - begin).count();
    time/=1000000000;

    std::ofstream MyFile("output3.txt");

    // Write to the file
    MyFile << std::fixed<<std::setprecision(5);
    MyFile << mini<<"\n";
    MyFile << maxi<<"\n";
    MyFile << rangi<<"\n";
    MyFile << modi<<"\n";
    MyFile << mediani<<"\n";
    MyFile << sumi<<"\n";
    MyFile << ameani<<"\n";
    MyFile << hmeani<<"\n";
    MyFile << devi<<"\n";
    MyFile << irangi<<"\n";
    MyFile << time<<"\n";

     // Close the file
    MyFile.close();

    return 0;
}
